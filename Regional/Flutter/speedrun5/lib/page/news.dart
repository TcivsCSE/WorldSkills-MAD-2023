import 'dart:math';

import 'package:flutter/material.dart';
import 'package:speedrun5/service/news_favorite.dart';
import 'package:speedrun5/widget/top_nav_bar.dart';

class NewsData {
  late final int id;
  final String title;
  final String author;
  final String datetime;
  final String content;

  NewsData(this.title, this.author, this.datetime, this.content) {
    id = (Random().nextInt(1 << 32) * Random().nextDouble()).ceil();
  }
}

final _newsFavoriteService = NewsFavoriteService.instance;

class NewsPage extends StatefulWidget {
  const NewsPage({super.key});

  @override
  State<StatefulWidget> createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {
  static final List<NewsData> _newsData = [
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
    NewsData("test title", "test author", "test datetime", "dfjnafhsbshgusjankhjbausojlnkfhbujnkhjujsnkhbhujnkjhbhujnbkhjhujnhkjbk"),
  ];

  @override
  void initState() {
    super.initState();
    _newsFavoriteService.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    _newsFavoriteService.removeListener(() { });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "最新消息"),
      body: ListView.builder(
        itemCount: _newsData.length,
        itemBuilder: (context, index) {
          final data = _newsData[index];
          return ListTile(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: data)));
            },
            title: Text(
              data.title
            ),
            trailing: IconButton(
              onPressed: () {
                _newsFavoriteService.favorites.contains(data.id) ?
                  _newsFavoriteService.removeFavorite(data.id) :
                  _newsFavoriteService.addFavorite(data.id);
              },
              icon: Icon(
                _newsFavoriteService.favorites.contains(data.id) ?
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
    _newsFavoriteService.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    _newsFavoriteService.removeListener(() { });
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
                _newsFavoriteService.favorites.contains(widget.data.id) ?
                  _newsFavoriteService.removeFavorite(widget.data.id) :
                  _newsFavoriteService.addFavorite(widget.data.id);
              },
              icon: Icon(
                _newsFavoriteService.favorites.contains(widget.data.id) ?
                  Icons.star :
                  Icons.star_border
              ),
            ),
            Text(
              widget.data.content * 10
            )
          ],
        ),
      ),
    );
  }
}
