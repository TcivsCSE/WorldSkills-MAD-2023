import 'package:flutter/material.dart';
import 'package:speedrun1/service/news_favorite.dart';
import 'package:speedrun1/widget/top_nav_bar.dart';

final newsDataService = NewsFavoriteService.instance;

class NewsPage extends StatefulWidget {
  const NewsPage({super.key});

  @override
  State<StatefulWidget> createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {
  static final List<NewsData> _newsData = [
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
    NewsData("test", "test author", " test datetime", "test content"),
  ];

  @override
  void initState() {
    super.initState();
    newsDataService.addListener(() {mounted ? setState(() {}) : null;});
  }

  @override
  void dispose() {
    super.dispose();
    newsDataService.removeListener(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "最新消息"),
      body: SingleChildScrollView(
        child: Column(
          children: _newsData.map(
            (e) {
              return ListTile(
                title: Text(e.title),
                onTap: () {
                  Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: e)));
                },
                trailing: IconButton(
                  onPressed: () {
                    newsDataService.favoriteNews.contains(e.id) ?
                      newsDataService.removeFavorite(e.id) :
                      newsDataService.addFavorite(e.id);
                  },
                  icon: Icon(
                    newsDataService.favoriteNews.contains(e.id) ?
                      Icons.star :
                      Icons.star_border
                  ),
                )
              );
            }
          ).toList(),
        ),
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
    newsDataService.addListener(() {mounted ? setState(() {}) : null;});
  }

  @override
  void dispose() {
    super.dispose();
    newsDataService.removeListener(() {});
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
                newsDataService.favoriteNews.contains(widget.data.id) ?
                  newsDataService.removeFavorite(widget.data.id) :
                  newsDataService.addFavorite(widget.data.id);
              },
              icon: Icon(
                newsDataService.favoriteNews.contains(widget.data.id) ?
                  Icons.star :
                  Icons.star_border
              ),
            )
          ],
        ),
      ),
    );
  }
}