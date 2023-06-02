

import 'dart:convert';

import 'package:basic_build/page/post.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

import '../constants.dart';

class FavoritePostsPage extends StatefulWidget {
  const FavoritePostsPage({super.key, required this.discussionData});

  final Map<String, dynamic> discussionData;

  @override
  State<StatefulWidget> createState() => _FavoritePostsPage();

}

class _FavoritePostsPage extends State<FavoritePostsPage> {
  final _userDataBox = Hive.box("userData");

  Future<Map<String, dynamic>> _fetchPosts() async {
    http.Response res = await http.get(Uri.parse("${Constants.baseUrl}/forum/discussions/${widget.discussionData['id']}/posts"));
    return jsonDecode(utf8.decode(res.bodyBytes));
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

  PreferredSizeWidget _buildAppBar() => AppBar(
    title: const Text(
      "最愛文章"
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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: FutureBuilder(
        future: _fetchPosts(),
        builder: (context, postSnapshot) {
          return FutureBuilder(
            future: _fetchPostFavorites(),
            builder: (context, favoriteSnapshot) {
              if (!postSnapshot.hasData || !favoriteSnapshot.hasData) {
                return const Center(
                  child: CircularProgressIndicator(),
                );
              } else {
                return ListView(
                  padding: const EdgeInsets.symmetric(vertical: 4),
                  children: postSnapshot.data!.values
                  .where((element) => favoriteSnapshot.data!.contains(element["id"]))
                  .map((data) {
                    return _buildPostCard(favoriteSnapshot, data);
                  })
                  .toList()
                );
              }
            },
          );
        },
      ),
    );
  }
}