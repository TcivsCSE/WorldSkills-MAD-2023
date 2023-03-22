import 'dart:math';

import 'package:flutter/material.dart';
import 'package:speedrun7/service/news_favorite.dart';
import 'package:speedrun7/widget/top_nav_bat.dart';

final NewsFavoriteService _newsFavoriteService = NewsFavoriteService.instance;

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
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
    NewsData("test title", "assets/mol.png", "asdjkhasdhjkashdjkasjkdjkjhjkhkjhjkhasd"),
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
          final NewsData data = _newsData[index];
          return ListTile(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: data)));
            },
            title: Text(data.title),
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
            Image.asset(widget.data.imageSource,),
            Text(widget.data.content * 10)
          ],
        ),
      ),
    );
  }
}