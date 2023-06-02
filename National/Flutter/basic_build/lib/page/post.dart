import 'dart:convert';

import 'package:basic_build/constants.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

class PostPage extends StatefulWidget {
  const PostPage({super.key, required this.discussionData, required this.postData});

  final Map<String, dynamic> discussionData;
  final Map<String, dynamic> postData;

  @override
  State<StatefulWidget> createState() => _PostPageState();

}

class _PostPageState extends State<PostPage> {
  bool _isFavorite = false;
  final _userDataBox = Hive.box("userData");
  late final TextEditingController _commentController;

  void _fetchIsFavorite() async {
    var userDataBox = Hive.box("userData");
    http.Response res = await http.post(
      Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
      headers: Constants.contentTypeJson,
      body: jsonEncode({
        "email": userDataBox.get("email"),
        "password": userDataBox.get("password"),
      })
    );
    Map<String, dynamic> data = jsonDecode(utf8.decode(res.bodyBytes));
    setState(() {
      _isFavorite = data["posts"]!.contains(widget.postData['id']);
    });
  }

  Future<Map<String, dynamic>> _fetchUser(String userId) async {
    http.Response res = await http.get(Uri.parse("${Constants.baseUrl}/forum/account/$userId"));
    return jsonDecode(utf8.decode(res.bodyBytes));
  }

  PreferredSizeWidget _buildTopAppBar() => AppBar(
    title: Text(
      widget.postData['title'],
      overflow: TextOverflow.ellipsis,
    ),
    leading: IconButton(
      onPressed: () {
        Navigator.of(context).pop();
      },
      icon: const Icon(Icons.arrow_back),
    ),
    actions: [
      IconButton(
        onPressed: () async {
          var userDataBox = Hive.box("userData");
          http.Response res = await http.post(
            Uri.parse("${Constants.baseUrl}/forum/account/favorites"),
            headers: Constants.contentTypeJson,
            body: jsonEncode({
              "email": userDataBox.get("email"),
              "password": userDataBox.get("password"),
              "data": {
                "id": widget.postData['id'],
                "type": "posts",
                "is_remove": _isFavorite
              }
            })
          );
          Map<String, dynamic> favorites = jsonDecode(utf8.decode(res.bodyBytes));
          setState(() {
            _isFavorite = favorites["posts"]!.contains(widget.postData['id']);
          });
        },
        icon: Icon(_isFavorite ? Icons.star : Icons.star_outline),
      )
    ],
  );

  Widget _buildPostContent() => Column(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: [
      Row(
        children: [
          const Icon(Icons.account_circle, size: 40,),
          const SizedBox(width: 10,),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              FutureBuilder(
                future: _fetchUser(widget.postData['author_id']),
                builder: (context, snapshot) {
                  if (!snapshot.hasData) {
                    return const Text(
                      "...",
                      style: TextStyle(
                        fontSize: 20
                      ),
                    );
                  } else {
                    return Text(
                      snapshot.data!["nickname"],
                      style: const TextStyle(
                        fontSize: 20
                      ),
                    );
                  }
                },
              ),
              Text(
                DateTime.fromMillisecondsSinceEpoch(widget.postData["created_at"] * 1000).toString().substring(0, 16),
                style: const TextStyle(
                  fontSize: 12
                ),
              )
            ],
          )
        ],
      ),
      const SizedBox(height: 14,),
      Text(
        widget.postData["title"],
        style: const TextStyle(
          fontSize: 26,
          fontWeight: FontWeight.bold
        ),
      ),
      const SizedBox(height: 14,),
      Text(
        widget.postData["content"],
        style: const TextStyle(
          fontSize: 16
        ),
      )
    ],
  );

  Future<Map<String, dynamic>> _fetchComments() async {
    http.Response res = await http.get(Uri.parse("${Constants.baseUrl}/forum/discussions/${widget.discussionData['id']}/posts/${widget.postData['id']}/comments"));
    Map<String, dynamic> commentsData = jsonDecode(utf8.decode(res.bodyBytes));
    return commentsData;
  }

  @override
  void initState() {
    _commentController = TextEditingController();
    super.initState();
    _fetchIsFavorite();
  }

  @override
  void dispose() {
    _commentController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildTopAppBar(),
      body: ListView(
        padding: const EdgeInsets.symmetric(vertical: 14, horizontal: 12),
        children: [
          _buildPostContent(),
          const SizedBox(height: 10,),
          Container(
            height: 1.2,
            width: double.infinity,
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(100)
            ),
          ),
          const SizedBox(height: 10,),
          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 4),
            child: Text(
              "留言區",
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold
              ),
            ),
          ),
          FutureBuilder(
            future: _fetchComments(),
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                return const Center(child: CircularProgressIndicator(),);
              } else {
                return Column(
                  children: snapshot.data!.values.map<Widget>((e) {
                    String datetime = DateTime.fromMillisecondsSinceEpoch(e["created_at"] * 1000).toString().substring(0, 16);
                    return Column(
                      children: [
                        const SizedBox(height: 2.2,),
                        ListTile(
                          contentPadding: const EdgeInsets.symmetric(horizontal: 6),
                          title: Text(
                            e["content"],
                            style: const TextStyle(
                              fontSize: 15
                            ),
                          ),
                          subtitle: FutureBuilder(
                            future: _fetchUser(e["author_id"]),
                            builder: (context, snapshot) {
                              if (!snapshot.hasData) {
                                return const Text(
                                  "...",
                                  style: TextStyle(
                                    fontSize: 14
                                  ),
                                );
                              } else {
                                return Text(
                                  "${snapshot.data!['nickname']} - $datetime",
                                  style: const TextStyle(
                                    fontSize: 14
                                  ),
                                );
                              }
                            },
                          )
                        ),
                        const SizedBox(height: 2.2,),
                        Container(
                          padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 4),
                          height: 0.6,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: Colors.white.withAlpha(200),
                            borderRadius: BorderRadius.circular(100)
                          ),
                        ),
                      ],
                    );
                  }).followedBy([
                    const SizedBox(height: 2.2,),
                    SizedBox(
                      width: double.infinity,
                      height: 70,
                      child: Row(
                        children: [
                          Expanded(
                            child: TextField(
                              controller: _commentController,
                              maxLines: 1,
                              decoration: const InputDecoration(
                                border: OutlineInputBorder(),
                                hintText: "發表留言"
                              ),
                            ),
                          ),
                          IconButton(
                            onPressed: () async {
                              if (_commentController.text.isEmpty) return;
                              await http.post(
                                Uri.parse("${Constants.baseUrl}/forum/discussions/${widget.discussionData['id']}/posts/${widget.postData['id']}/comments"),
                                headers: Constants.contentTypeJson,
                                body: jsonEncode({
                                  "email": _userDataBox.get("email"),
                                  "password": _userDataBox.get("password"),
                                  "data": {
                                    "content": _commentController.text
                                  }
                                })
                              );
                              _commentController.clear();
                              setState(() {
                                
                              });
                            },
                            icon: const Icon(Icons.send),
                          )
                        ],
                      ),
                    )
                  ]).toList(),
                );
              }
            },
          )
        ].toList(),
      )
    );
  }
}