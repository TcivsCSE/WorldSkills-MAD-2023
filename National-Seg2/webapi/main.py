import json, datetime, uuid

from fastapi import FastAPI, Body, Request, Response, status


app = FastAPI()

m2_events = [
  {
    "id": str(uuid.uuid4()),
    "image": "https://media.discordapp.net/attachments/886902378291421195/1142126855705591868/FB_IMG_1688423465367.jpg",
    "title": "Test1",
    "content": "saahsfdduhajfnuadh",
    "is_readed": True
  },
  {
    "id": str(uuid.uuid4()),
    "image": "https://media.discordapp.net/attachments/886902378291421195/1142126855705591868/FB_IMG_1688423465367.jpg",
    "title": "Test2",
    "content": "saahsfddusadfhajfnuadh",
    "is_readed": True
  },
  {
    "id": str(uuid.uuid4()),
    "image": "https://media.discordapp.net/attachments/886902378291421195/1142126855705591868/FB_IMG_1688423465367.jpg",
    "title": "Test3",
    "content": "saaasdfhsfdduhsadfajfnuadh",
    "is_readed": False
  },
  {
    "id": str(uuid.uuid4()),
    "image": "https://media.discordapp.net/attachments/886902378291421195/1142126855705591868/FB_IMG_1688423465367.jpg",
    "title": "Test4",
    "content": "saahsfdduasdfsadfasdfhajfnuadh",
    "is_readed": False
  },
  {
    "id": str(uuid.uuid4()),
    "image": "https://media.discordapp.net/attachments/886902378291421195/1142126855705591868/FB_IMG_1688423465367.jpg",
    "title": "Test5",
    "content": "saahsfdduhajasdfsadfasdfasdfasdffnuadh",
    "is_readed": False
  },
]

m2_tickets = [
  {
    "id": str(uuid.uuid4()),
    "type": "opening",
    "name": "Jack",
    "position": "A7 Row8 Column3"
  },
  {
    "id": str(uuid.uuid4()),
    "type": "opening",
    "name": "Rose",
    "position": "B2 Row1 Column6"
  },
  {
    "id": str(uuid.uuid4()),
    "type": "closing",
    "name": "Nick",
    "position": "A3 Row2 Column5"
  },
  {
    "id": str(uuid.uuid4()),
    "type": "closing",
    "name": "Jeff",
    "position": "A6 Row7 Column6"
  },
]

@app.get("/m2/events")
async def m2_get_events():
  return m2_events

@app.post("/m2/events/{event_id}")
async def m2_get_event_by_id(event_id: str):
  global m2_events
  
  def mk_read(x):
    if x["id"] == event_id:
      x["is_readed"] = True
      
    return x
  
  m2_events = list(map(mk_read, m2_events))
  
  return m2_events

@app.get("/m2/tickets")
@app.post("/m2/tickets")
async def m2_get_tickets(request: Request):
  global m2_tickets
  
  if request.method == "POST":
    payload = await request.json()
    
    m2_tickets.append(
      {
        "id": str(uuid.uuid4()),
        "type": payload["type"],
        "name": payload["name"],
        "position": payload["position"],
      }
    )
  
  return m2_tickets