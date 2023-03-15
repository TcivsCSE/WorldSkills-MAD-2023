import 'dart:math';

import 'package:flutter/material.dart';
import 'package:speedrun2/service/news_favorite.dart';
import 'package:speedrun2/widget/top_nav_bar.dart';

class NewsData {
  late final int id;
  final String title;
  final String author;
  final String datetime;
  final String content;

  NewsData(this.title, this.author, this.datetime, this.content) {
    id = (Random().nextInt(1<<32) * Random().nextDouble()).ceil();
  }
}

final NewsFavoriteService newsFavoriteService = NewsFavoriteService.instance;

class NewsPage extends StatefulWidget {
  const NewsPage({super.key});

  @override
  State<StatefulWidget> createState() => _NewsPageState();
}

class _NewsPageState extends State<NewsPage> {
  static final List<NewsData> _newsData = [
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
    NewsData("test title", "test author", "test datetime", "bkhadslihfjhsdusajkbhajlkhjhlfhkbhjlkfja"),
  ];

  @override
  void initState() {
    super.initState();
    newsFavoriteService.addListener(() => setState(() {}));
  }

  @override
  void dispose() {
    super.dispose();
    newsFavoriteService.removeListener(() { });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "最新消息"),
      body: ListView.builder(
        itemCount: _newsData.length,
        itemBuilder: (context, index) {
          return ListTile(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => NewsDetailPage(data: _newsData[index])));
            },
            title: Text(
              _newsData[index].title
            ),
            trailing: IconButton(
              onPressed: () {
                newsFavoriteService.favorites.contains(_newsData[index].id) ?
                  newsFavoriteService.removeFavorite(_newsData[index].id) :
                  newsFavoriteService.addFavorite(_newsData[index].id);
              },
              icon: Icon(
                newsFavoriteService.favorites.contains(_newsData[index].id) ?
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
    print("init");
    newsFavoriteService.addListener(() {
      print(mounted);
      if (mounted) setState(() {});
    });
  }

  @override
  void dispose() {
    super.dispose();
    print("dispose");
    newsFavoriteService.removeListener(() { });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavBar(title: widget.data.title,),
      body: SingleChildScrollView(
        child: Column(
          children: [
            IconButton(
              onPressed: () {
                newsFavoriteService.favorites.contains(widget.data.id) ?
                  newsFavoriteService.removeFavorite(widget.data.id) :
                  newsFavoriteService.addFavorite(widget.data.id);
              },
              icon: Icon(
                newsFavoriteService.favorites.contains(widget.data.id) ?
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
