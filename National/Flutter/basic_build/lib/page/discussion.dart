import 'dart:convert';

import 'package:basic_build/page/favorite_posts.dart';
import 'package:basic_build/page/post.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

import '../constants.dart';

class DiscussionPage extends StatefulWidget {
  const DiscussionPage({super.key, required this.discussionData});

  final Map<String, dynamic> discussionData;

  @override
  State<StatefulWidget> createState() => _DiscussionPageState();
}

class _DiscussionPageState extends State<DiscussionPage> {
  final _userDataBox = Hive.box("userData");

  late final TextEditingController _titleController;
  late final TextEditingController _contentController;

  @override
  void initState() {
    _titleController = TextEditingController();
    _contentController = TextEditingController();
    super.initState();
  }

  @override
  void dispose() {
    _titleController.dispose();
    _contentController.dispose();
    super.dispose();
  }

  Future<List<dynamic>> _fetchPostFavorites() async {
    http.Response res = await http.post(
      Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
      headers: Constants.contentTypeJson,
      body: jsonEncode({
        "email": _userDataBox.get("email"),
        "password": _userDataBox.get("password"),
      })
    );
    return jsonDecode(utf8.decode(res.bodyBytes))["posts"];
  }

  Future<List<dynamic>> _fetchDiscussionFavorites() async {
    http.Response res = await http.post(
      Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
      headers: Constants.contentTypeJson,
      body: jsonEncode({
        "email": _userDataBox.get("email"),
        "password": _userDataBox.get("password"),
      })
    );
    return jsonDecode(utf8.decode(res.bodyBytes))["discussions"];
  }

  Future<Map<String, dynamic>> _fetchPosts() async {
    http.Response res = await http.get(Uri.parse("${Constants.baseUrl}/forum/discussions/${widget.discussionData['id']}/posts"));
    return jsonDecode(utf8.decode(res.bodyBytes));
  }

  AlertDialog _buildCreatePostDialog() => AlertDialog(
    content: Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        TextField(
          controller: _titleController,
          maxLines: 1,
          decoration: const InputDecoration(
            hintText: "標題"
          ),
        ),
        SizedBox(
          height: 100,
          child: TextField(
            controller: _contentController,
            maxLength: TextField.noMaxLength,
            decoration: const InputDecoration(
              hintText: "內文"
            ),
          ),
        ),
      ],
    ),
    actions: [
      ElevatedButton(
        onPressed: () {
          Navigator.of(context).pop(null);
        },
        child: const Text("取消"),
      ),
      ElevatedButton(
        onPressed: () {
          if (_titleController.text.isEmpty || _contentController.text.isEmpty) return;
          Navigator.of(context).pop({
            "title": _titleController.text,
            "content": _contentController.text,
          });
        },
        child: const Text("發布"),
      ),
    ],
  );

  PreferredSizeWidget _buildAppBar() => AppBar(
    title: Text(
      widget.discussionData["name"]
    ),
    leading: IconButton(
      onPressed: () {
        Navigator.of(context).pop();
      },
      icon: const Icon(Icons.arrow_back),
    )
  );


  Widget _buildPostCard(AsyncSnapshot favoriteSnapshot, Map<String, dynamic> data) => SizedBox(
    height: 100,
    child: Padding(
      padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
      child: InkWell(
        onTap: () async {
          await Navigator.of(context).push(MaterialPageRoute(builder: (context) => PostPage(discussionData: widget.discussionData, postData: data)));
          setState(() {
            
          });
        },
        child: Card(
          child: Row(
            children: [
              IconButton(
                onPressed: () async {
                  var isFavorite = favoriteSnapshot.data!.contains(data["id"]);
                  var userDataBox = Hive.box("userData");
                  await http.post(
                    Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
                    headers: Constants.contentTypeJson,
                    body: jsonEncode({
                      "email": userDataBox.get("email"),
                      "password": userDataBox.get("password"),
                      "data": {
                        "id": data['id'],
                        "type": "posts",
                        "is_remove": isFavorite
                      }
                    })
                  );
                  setState(() {
                    
                  });
                },
                icon: Icon(favoriteSnapshot.data!.contains(data["id"]) ? Icons.star : Icons.star_outline),
              ),
              SizedBox(
                width: 300,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      data["title"],
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 24
                      ),
                    ),
                    const SizedBox(height: 6,),
                    Text(
                      data["content"],
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(
                        fontWeight: FontWeight.normal,
                        fontSize: 16
                      ),
                    ),
                  ],
                )
              )
            ],
          ),
        ),
      ),
    ),
  );

  Widget _buildActionButton(String title, IconData icon, void Function() actionFn) => SizedBox(
    height: 63,
    width: 126,
    child: Padding(
      padding: const EdgeInsets.symmetric(horizontal: 0, vertical: 0),
      child: InkWell(
        onTap: actionFn,
        child: Card(
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Spacer(flex: 2,),
              Icon(icon),
              const Spacer(),
              Text(
                title,
                style: const TextStyle(
                  fontSize: 16
                ),
              ),
              const Spacer(flex: 2,),
            ],
          ),
        ),
      ),
    ),
  );

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: _buildAppBar(),
      body: FutureBuilder(
        future: _fetchPosts(),
        builder: (context, postSnapshot) {
          return FutureBuilder(
            future: _fetchPostFavorites(),
            builder: (context, postFavoriteSnapshot) {
              if (!postSnapshot.hasData || !postFavoriteSnapshot.hasData) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else {
                return ListView(
                  padding: const EdgeInsets.symmetric(vertical: 4),
                  children: (<Widget>[
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          _buildActionButton(
                            "發布文章",
                            Icons.add,
                            () async {
                              var postContent = await showDialog<Map<String, String>?>(
                                context: context,
                                barrierDismissible: false,
                                builder: (context) => _buildCreatePostDialog()
                              );
                              if (postContent == null) return;
                              await http.post(
                                Uri.parse("${Constants.baseUrl}/forum/discussions/${widget.discussionData['id']}/posts"),
                                headers: {"Content-Type": "application/json"},
                                body: jsonEncode({
                                  "email": _userDataBox.get("email"),
                                  "password": _userDataBox.get("password"),
                                  "data": {
                                    "title": postContent["title"],
                                    "content": postContent["content"]
                                  }
                                })
                              );
                              setState(() {});
                            }
                          ),
                          _buildActionButton(
                            "最愛文章",
                            Icons.list,
                            () async {
                              await Navigator.of(context).push(MaterialPageRoute(builder: (context) => FavoritePostsPage(discussionData: widget.discussionData)));
                              setState(() {
                                
                              });
                            }
                          ),
                          FutureBuilder(
                            future: _fetchDiscussionFavorites(),
                            builder: (context, snapshot) {
                              if (!snapshot.hasData) {
                                return _buildActionButton(
                                  "載入中...",
                                  Icons.refresh,
                                  () {}
                                );
                              } else {
                                return _buildActionButton(
                                  snapshot.data!.contains(widget.discussionData["id"]) ? "移除最愛" : "加入最愛",
                                  snapshot.data!.contains(widget.discussionData["id"]) ? Icons.star : Icons.star_border,
                                  () async {
                                    await http.post(
                                      Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
                                      headers: Constants.contentTypeJson,
                                      body: jsonEncode({
                                        "email": _userDataBox.get("email"),
                                        "password": _userDataBox.get("password"),
                                        "data": {
                                          "id": widget.discussionData['id'],
                                          "type": "discussions",
                                          "is_remove": snapshot.data!.contains(widget.discussionData["id"])
                                        }
                                      })
                                    );
                                    setState(() {
                                      
                                    });
                                  }
                                );

                              }
                            },
                          )
                        ],
                      ),
                    )
                  ].followedBy(
                    postSnapshot.data!.values.map((data) {
                      return _buildPostCard(postFavoriteSnapshot, data);
                    })
                  )).toList()
                );
              }
            },
          );
        },
      ),
    );
  }
}


