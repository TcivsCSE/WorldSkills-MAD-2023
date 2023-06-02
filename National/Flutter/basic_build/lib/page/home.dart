import 'dart:convert';

import 'package:basic_build/constants.dart';
import 'package:basic_build/page/discussion.dart';
import 'package:basic_build/page/login.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<StatefulWidget> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final _userDataBox = Hive.box("userData");
  
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

  Future<Map<String, dynamic>> _fetchDiscussions() async {
    http.Response res = await http.get(Uri.parse("${Constants.baseUrl}/forum/discussions"));
    return jsonDecode(utf8.decode(res.bodyBytes));
  }

  Widget _buildDrawer() => Drawer(
    child: Padding(
      padding: const EdgeInsets.symmetric(vertical: 60),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Icon(
            Icons.account_circle,
            size: 120,
          ),
          Text(
            _userDataBox.get("nickname"),
            style: const TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold
            ),
          ),
          Text(
            _userDataBox.get("email"),
            style: const TextStyle(
              fontSize: 14
            ),
          ),
          const SizedBox(height: 20,),
          Expanded(
            child: FutureBuilder(
              future: _fetchDiscussions(),
              builder: (context, discussionSnapshot) {
                return FutureBuilder(
                  future: _fetchDiscussionFavorites(),
                  builder: (context, favoriteSnapshot) {
                    if (!discussionSnapshot.hasData || !favoriteSnapshot.hasData) {
                      return const Center(child: CircularProgressIndicator(),);
                    } else {
                      return ListView(
                        padding: const EdgeInsets.symmetric(horizontal: 40),
                        children: discussionSnapshot.data!.values
                          .where((element) => favoriteSnapshot.data!.contains(element["id"]))
                          .map((e) {
                            return SizedBox(
                              height: 60,
                              child: InkWell(
                                onTap: () {
                                  Navigator.of(context).push(MaterialPageRoute(builder: (context) => DiscussionPage(discussionData: e)));
                                },
                                child: Card(
                                  child: Center(
                                    child: Text(
                                      e["name"],
                                      style: const TextStyle(
                                        fontSize: 16
                                      ),
                                    ),
                                  ),
                                ),
                              ),
                            );
                          })
                          .toList(),
                      );
                    }
                  },
                );
              },
            )
          ),
          const SizedBox(height: 20,),
          SizedBox(
            width: 180,
            child: ElevatedButton(
              child: Row(
                children: const [
                  Spacer(flex: 2,),
                  Icon(Icons.logout),
                  Spacer(flex: 1,),
                  Text(
                    "登出",
                    style: TextStyle(
                      fontSize: 16
                    ),
                  ),
                  Spacer(flex: 2,),
                ],
              ),
              onPressed: () async {
                await _userDataBox.clear();
                if (mounted) Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const LoginPage()));
              },
            ),
          ),
        ],
      ),
    ),
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: _buildDrawer(),
      appBar: AppBar(
        title: const Text("首頁"),
      ),
      body: FutureBuilder(
        future: _fetchDiscussions(),
        builder: (context, discussionSnapshot) {
          return FutureBuilder(
            future: _fetchDiscussionFavorites(),
            builder: (context, favoriteSnapshot) {
              if (!discussionSnapshot.hasData || !favoriteSnapshot.hasData) {
                return const Center(child: CircularProgressIndicator(),);
              } else {
                return ListView(
                  padding: const EdgeInsets.symmetric(vertical: 4),
                  children: discussionSnapshot.data!.values.map((data) {
                    return SizedBox(
                      width: double.infinity,
                      height: 100,
                      child: Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 2),
                        child: InkWell(
                          onTap: () async {
                            await Navigator.of(context).push(MaterialPageRoute(builder: (context) => DiscussionPage(discussionData: data)));
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
                                          "type": "discussions",
                                          "is_remove": isFavorite
                                        }
                                      })
                                    );
                                    setState(() {
                                      
                                    });
                                  },
                                  icon: Icon(favoriteSnapshot.data!.contains(data["id"]) ? Icons.star : Icons.star_outline),
                                ),
                                Center(
                                  child: Text(
                                    data["name"],
                                    style: const TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 24
                                    ),
                                  ),
                                )
                              ],
                            ),
                          ),
                        )
                      ),
                    );
                  }).toList()
                );
              }
            },
          );
        },
      )
    );
  }
}