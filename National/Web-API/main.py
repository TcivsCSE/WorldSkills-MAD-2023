import json, datetime, uuid

from fastapi import FastAPI, Body, Request, Response, status


app = FastAPI()


@app.post("/guide/account/signup")
def guide_account_signup(response: Response, payload: dict = Body(...)):
  with open("guide_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if payload["email"] in db_data:
    response.status_code = status.HTTP_409_CONFLICT
    return {"err": "Account exists."}
  
  db_data[payload["email"]] = {
    "id": str(uuid.uuid4()),
    "email": payload["email"],
    "password": payload["password"],
    "name": payload["name"],
    "posts": {},
    "tickets": {}
  }
  
  with open("guide_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  response.status_code = status.HTTP_201_CREATED
  return db_data[payload["email"]]

@app.post("/guide/account/login")
def guide_account_login(response: Response, payload: dict = Body(...)):
  with open("guide_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
    
  if payload["email"] not in db_data:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}
  
  account_data = db_data[payload["email"]]
  
  if account_data["password"] != payload["password"]:
    response.status_code = status.HTTP_401_UNAUTHORIZED
    return {"err": "Wrong password."}
  
  response.status_code = status.HTTP_200_OK
  return db_data[payload["email"]]

@app.post("/guide/account/edit")
def guide_account_edit(response: Response, payload: dict = Body(...)):
  with open("guide_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
    
  if payload["email"] not in db_data:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}
  
  for k, v in payload["data"].items():
    db_data[payload["email"]][k] = v
  
  with open("guide_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  response.status_code = status.HTTP_202_ACCEPTED
  return db_data[payload["email"]]

@app.get("/guide/news/posts")
@app.post("/guide/news/posts")
async def guide_news_posts(request: Request, response: Response):
  with open("guide_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if request.method == "GET":
    posts = {}
    for user_account in db_data:
      for v in db_data[user_account]["posts"].values():
        posts[v["id"]] = v
    return posts
  
  if request.method == "POST":
    payload = await request.json()
  
    if "email" not in payload or "password" not in payload:
      response.status_code = status.HTTP_400_BAD_REQUEST
      return {"err": "Account info missing."}
    
    if payload["email"] not in db_data:
      response.status_code = status.HTTP_404_NOT_FOUND
      return {"err": "Account not found."}
  
    account_data = db_data[payload["email"]]
  
    if account_data["password"] != payload["password"]:
      response.status_code = status.HTTP_401_UNAUTHORIZED
      return {"err": "Wrong password."}
  
    post_id = str(uuid.uuid4())
    db_data[payload["email"]]["posts"][post_id] = {
      "id": post_id,
      "title": payload["data"]["title"],
      "content": payload["data"]["content"]
    }

    with open("guide_db.json", "w", encoding = "utf-8") as f:
      json.dump(db_data, f, indent = 2, ensure_ascii = False)

    response.status_code = status.HTTP_201_CREATED
    return db_data[payload["email"]]["posts"][post_id]

@app.post("/guide/tickets")
def guide_tickets(response: Response, payload: dict = Body(...)):
  with open("guide_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
  
  if payload["email"] not in db_data:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}

  account_data = db_data[payload["email"]]

  if account_data["password"] != payload["password"]:
    response.status_code = status.HTTP_401_UNAUTHORIZED
    return {"err": "Wrong password."}
  
  if "data" not in payload:
    response.status_code = status.HTTP_200_OK
    return account_data["tickets"]
  
  ticket_id = str(uuid.uuid4())
  
  db_data[payload["email"]]["tickets"][ticket_id] = {
    "id": ticket_id,
    "name": payload["data"]["name"],
    "purchased_at": round(datetime.datetime.now().timestamp()),
    "expired_at": payload["data"]["expired_at"],
    "type": payload["data"]["type"]
  }
  
  with open("guide_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  return account_data["tickets"][ticket_id]

@app.get("/guide/jobs")
def guide_jobs():
  # TODO: Impl jobs content
  return {}


@app.post("/forum/account/signup")
def forum_account_signup(response: Response, payload: dict = Body(...)):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if payload["email"] in db_data["accounts"]:
    response.status_code = status.HTTP_409_CONFLICT
    return {"err": "Account exists."}
  
  db_data["accounts"][payload["email"]] = {
    "id": str(uuid.uuid4()),
    "email": payload["email"],
    "password": payload["password"],
    "nickname": payload["nickname"],
    "posts": {},
    "comments": {},
    "favorites": {
      "discussions": [],
      "posts": [],
    }
  }
  
  with open("forum_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  response.status_code = status.HTTP_201_CREATED
  return db_data["accounts"][payload["email"]]

@app.post("/forum/account/login")
def forum_account_login(response: Response, payload: dict = Body(...)):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
    
  if payload["email"] not in db_data["accounts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}
  
  account_data = db_data["accounts"][payload["email"]]
  
  if account_data["password"] != payload["password"]:
    response.status_code = status.HTTP_401_UNAUTHORIZED
    return {"err": "Wrong password."}
  
  response.status_code = status.HTTP_200_OK
  return db_data["accounts"][payload["email"]]

@app.post("/forum/account/edit")
def forum_account_edit(response: Response, payload: dict = Body(...)):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
    
  if payload["email"] not in db_data["accounts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}
  
  for k, v in payload["data"].items():
    db_data["accounts"][payload["email"]][k] = v
  
  with open("forum_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  response.status_code = status.HTTP_202_ACCEPTED
  return db_data["accounts"][payload["email"]]

@app.get("/forum/account/{user_id}")
def forum_account_get_by_id(response: Response, user_id: str):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  target_user = list(filter(lambda x: x["id"] == user_id, db_data["accounts"].values()))
  
  if len(target_user) == 0:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}
  
  target_user[0].pop("email")
  target_user[0].pop("password")
  target_user[0].pop("favorites")
  
  return target_user[0]

@app.get("/forum/discussions")
def forum_discussions():
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  return db_data["discussions"]

@app.get("/forum/discussions/{discussion_id}/posts")
@app.post("/forum/discussions/{discussion_id}/posts")
async def forum_discussion_posts(request: Request, response: Response, discussion_id: str):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
    
  if discussion_id not in db_data["discussions"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Discussion not found."}
    
  if request.method == "GET":
    return db_data["discussions"][discussion_id]["posts"]
  
  if request.method == "POST":
    payload = await request.json()
  
    if "email" not in payload or "password" not in payload:
      response.status_code = status.HTTP_400_BAD_REQUEST
      return {"err": "Account info missing."}
    
    if payload["email"] not in db_data["accounts"]:
      response.status_code = status.HTTP_404_NOT_FOUND
      return {"err": "Account not found."}
    
    account_data = db_data["accounts"][payload["email"]]
    
    if account_data["password"] != payload["password"]:
      response.status_code = status.HTTP_401_UNAUTHORIZED
      return {"err": "Wrong password."}
    
    post_id = str(uuid.uuid4())
    
    db_data["discussions"][discussion_id]["posts"][post_id] = {
      "id": post_id,
      "author_id": account_data["id"],
      "created_at": round(datetime.datetime.now().timestamp()),
      "title": payload["data"]["title"],
      "content": payload["data"]["content"],
      "comments": {}
    }
    if discussion_id not in db_data["accounts"][payload["email"]]["posts"]:
      db_data["accounts"][payload["email"]]["posts"][discussion_id] = []
    db_data["accounts"][payload["email"]]["posts"][discussion_id].append(post_id)
    
    with open("forum_db.json", "w", encoding = "utf-8") as f:
      json.dump(db_data, f, indent = 2, ensure_ascii = False)
    
    response.status_code = status.HTTP_201_CREATED
    return db_data["discussions"][discussion_id]["posts"][post_id]

@app.get("/forum/discussions/{discussion_id}/posts/{post_id}")
def forum_discussion_post(response: Response, discussion_id: str, post_id: str):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if discussion_id not in db_data["discussions"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Discussion not found."}
  
  if post_id not in db_data["discussions"][discussion_id]["posts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Post not found."}
    
  return db_data["discussions"][discussion_id]["posts"][post_id]

@app.get("/forum/discussions/{discussion_id}/posts/{post_id}/comments")
@app.post("/forum/discussions/{discussion_id}/posts/{post_id}/comments")
async def forum_discussion_post_comment(request: Request, response: Response, discussion_id: str, post_id: str):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if discussion_id not in db_data["discussions"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Discussion not found."}

  if post_id not in db_data["discussions"][discussion_id]["posts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Post not found."}
  
  if request.method == "GET":
    return db_data["discussions"][discussion_id]["posts"][post_id]["comments"]
  
  if request.method == "POST":
    payload = await request.json()
  
    if "email" not in payload or "password" not in payload:
      response.status_code = status.HTTP_400_BAD_REQUEST
      return {"err": "Account info missing."}
    
    if payload["email"] not in db_data["accounts"]:
      response.status_code = status.HTTP_404_NOT_FOUND
      return {"err": "Account not found."}
    
    account_data = db_data["accounts"][payload["email"]]
    
    if account_data["password"] != payload["password"]:
      response.status_code = status.HTTP_401_UNAUTHORIZED
      return {"err": "Wrong password."}
    
    comment_id = str(uuid.uuid4())
    
    db_data["discussions"][discussion_id]["posts"][post_id]["comments"][comment_id] = {
      "id": comment_id,
      "author_id": account_data["id"],
      "created_at": round(datetime.datetime.now().timestamp()),
      "content": payload["data"]["content"]
    }
    if discussion_id not in db_data["accounts"][payload["email"]]["comments"]:
      db_data["accounts"][payload["email"]]["comments"][discussion_id] = {}
    if post_id not in db_data["accounts"][payload["email"]]["comments"][discussion_id]:
      db_data["accounts"][payload["email"]]["comments"][discussion_id][post_id] = []
    db_data["accounts"][payload["email"]]["comments"][discussion_id][post_id].append(comment_id)
    
    with open("forum_db.json", "w", encoding = "utf-8") as f:
      json.dump(db_data, f, indent = 2, ensure_ascii = False)
    
    response.status_code = status.HTTP_201_CREATED
    return db_data["discussions"][discussion_id]["posts"][post_id]["comments"][comment_id]

@app.get("/forum/discussions/{discussion_id}/posts/{post_id}/comments/{comment_id}")
def forum_discussion_post(response: Response, discussion_id: str, post_id: str, comment_id: str):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
    
  if discussion_id not in db_data["discussions"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Discussion not found."}
  
  if post_id not in db_data["discussions"][discussion_id]["posts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Post not found."}
  
  if comment_id not in db_data["discussions"][discussion_id]["posts"][post_id]["comments"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Comment not found."}
    
  return db_data["discussions"][discussion_id]["posts"][post_id]["comments"][comment_id]

@app.post("/forum/account/favorites")
def forum_discussion_favorites(response: Response, payload: dict = Body(...)):
  with open("forum_db.json", "r", encoding = "utf-8") as f:
    db_data = json.load(f)
  
  if "email" not in payload or "password" not in payload:
    response.status_code = status.HTTP_400_BAD_REQUEST
    return {"err": "Account info missing."}
  
  if payload["email"] not in db_data["accounts"]:
    response.status_code = status.HTTP_404_NOT_FOUND
    return {"err": "Account not found."}

  account_data = db_data["accounts"][payload["email"]]

  if account_data["password"] != payload["password"]:
    response.status_code = status.HTTP_401_UNAUTHORIZED
    return {"err": "Wrong password."}
  
  if "data" not in payload:
    response.status_code = status.HTTP_200_OK
    return account_data["favorites"]
  
  if payload["data"]["id"] not in db_data["accounts"][payload["email"]]["favorites"][payload["data"]["type"]]:
      db_data["accounts"][payload["email"]]["favorites"][payload["data"]["type"]].append(payload["data"]["id"])
  
  if payload["data"]["is_remove"]:
    try:
      db_data["accounts"][payload["email"]]["favorites"][payload["data"]["type"]].remove(
        payload["data"]["id"]
      )
    except:
      pass
  else:
    if payload["data"]["id"] not in account_data["favorites"][payload["data"]["type"]]:
      db_data["accounts"][payload["email"]]["favorites"][payload["data"]["type"]].append(
        payload["data"]["id"]
      )

  with open("forum_db.json", "w", encoding = "utf-8") as f:
    json.dump(db_data, f, indent = 2, ensure_ascii = False)
  
  return db_data["accounts"][payload["email"]]["favorites"]
