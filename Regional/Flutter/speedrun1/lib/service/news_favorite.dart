import 'dart:math';

import 'package:flutter/material.dart';

class NewsData {
  late final int id;
  final String title;
  final String author;
  final String datetime;
  final String content;

  NewsData(this.title, this.author, this.datetime, this.content) {
    id = (Random().nextInt(4294967296) * Random().nextDouble()).ceil();
  }
}

class NewsFavoriteService extends ChangeNotifier {
  NewsFavoriteService._();

  static NewsFavoriteService? _instance;
  late List<int> favoriteNews;

  void addFavorite(int newsId) {
    favoriteNews.add(newsId);
    notifyListeners();
  }

  void removeFavorite(int newsId) {
    favoriteNews.remove(newsId);
    notifyListeners();
  }

  static NewsFavoriteService get instance {
    if (_instance == null) {
      _instance = NewsFavoriteService._();
      _instance!.favoriteNews = [];
    }
    return _instance!;
  }
}