import 'dart:math';

import 'package:basic_build/service/news_favorite.dart';
import 'package:basic_build/widget/top_navigation_bar.dart';
import 'package:flutter/material.dart';

class NewsData {
  late final int id;
  final String title;
  final String author;
  final String date;
  final String content;

  NewsData(this.title, this.author, this.date, this.content) {
    id = (DateTime.now().millisecondsSinceEpoch * Random().nextDouble()).round() ^ Random().nextInt(1);
  }
}


class NewsPage extends StatefulWidget {
  const NewsPage({super.key});

  @override
  State<StatefulWidget> createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {

  static final List<NewsData> _data = [
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
    NewsData("test title", "test author", "test date", "test content"),
  ];

  @override
  void initState() {
    super.initState();
    NewsFavoriteService.instance.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    NewsFavoriteService.instance.removeListener(() {});
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavigationBar(title: "最新消息"),
      body: ListView.builder(
        itemCount: _data.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(_data[index].title),
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: _data[index])));
            },
            trailing: IconButton(
              onPressed: () {
                setState(() {
                  if (NewsFavoriteService.instance.favorites.contains(_data[index].id)) {
                    NewsFavoriteService.instance.remove(_data[index].id);
                  } else {
                    NewsFavoriteService.instance.add(_data[index].id);
                  }
                });
              },
              icon: Icon(NewsFavoriteService.instance.favorites.contains(_data[index].id) ? Icons.star : Icons.star_border),
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
  late bool isFavorite;

  @override
  void initState() {
    super.initState();
    isFavorite = NewsFavoriteService.instance.favorites.contains(widget.data.id);
    NewsFavoriteService.instance.addListener(() => mounted ? setState(() {}) : null);
  }

  @override
  void dispose() {
    super.dispose();
    NewsFavoriteService.instance.removeListener(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavigationBar(title: widget.data.title),
      body: Column(
        children: [
          Text(widget.data.content),
          IconButton(
            onPressed: () {
              setState(() {
                if (isFavorite) {
                  NewsFavoriteService.instance.remove(widget.data.id);
                } else {
                  NewsFavoriteService.instance.add(widget.data.id);
                }
                isFavorite = !isFavorite;
              });
            },
            icon: Icon(isFavorite ? Icons.star : Icons.star_border),
          )
        ],
      ),
    );
  }
}
