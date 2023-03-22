import 'dart:math';

import 'package:flutter/material.dart';
import 'package:speedrun6/service/news_favorite.dart';
import 'package:speedrun6/widget/top_nav_bar.dart';

final NewsFavoriteSerivce _newsFavoriteSerivce = NewsFavoriteSerivce.instance;

class NewsData {
  late final int id;
  final String title;
  final String imageSource;
  final String content;

  NewsData(this.title, this.imageSource, this.content) {
    id = (Random().nextInt(1 << 32) * Random().nextDouble()).ceil();
  }
}

class NewsPage extends StatefulWidget {
  const NewsPage({super.key});

  @override
  State<StatefulWidget> createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {
  final List<NewsData> _newsData = [
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
    NewsData("test title", "assets/mol.png", "dshfsguajfgyhsuajbhsyghajbhhsjanhdhasdasdsadsa"),
  ];

  @override
  void initState() {
    super.initState();
    _newsFavoriteSerivce.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    _newsFavoriteSerivce.removeListener(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "最新消息"),
      body: ListView.builder(
        itemCount: _newsData.length,
        itemBuilder: (context, index) {
          final NewsData data = _newsData[index];
          return ListTile(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: data)));
            },
            title: Text(
              data.title
            ),
            trailing: IconButton(
              onPressed: () {
                _newsFavoriteSerivce.favorites.contains(data.id) ?
                  _newsFavoriteSerivce.removeFavorite(data.id) :
                  _newsFavoriteSerivce.addFavorite(data.id);
              },
              icon: Icon(
                _newsFavoriteSerivce.favorites.contains(data.id) ?
                  Icons.star :
                  Icons.star_border
              ),
            ),
          );
        },
      ),
    );
  }
}

class NewsDetailPage extends StatefulWidget {
  const NewsDetailPage({super.key, required this.data});

  final NewsData data;

  @override
  State<StatefulWidget> createState() => _NewsDetailPageState();
}

class _NewsDetailPageState extends State<NewsDetailPage> {
  @override
  void initState() {
    super.initState();
    _newsFavoriteSerivce.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    _newsFavoriteSerivce.removeListener(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavBar(title: widget.data.title),
      body: SingleChildScrollView(
        child: Column(
          children: [
            IconButton(
              onPressed: () {
                _newsFavoriteSerivce.favorites.contains(widget.data.id) ?
                  _newsFavoriteSerivce.removeFavorite(widget.data.id) :
                  _newsFavoriteSerivce.addFavorite(widget.data.id);
              },
              icon: Icon(
                _newsFavoriteSerivce.favorites.contains(widget.data.id) ?
                  Icons.star :
                  Icons.star_border
              ),
            ),
            Text(widget.data.content * 10),
            Image.asset(widget.data.imageSource)
          ],
        ),
      ),
    );
  }
}