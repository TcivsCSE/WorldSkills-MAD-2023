import 'package:flutter/material.dart';

class NewsFavoriteSerivce extends ChangeNotifier {
  NewsFavoriteSerivce._();

  static NewsFavoriteSerivce? _instance;
  late List<int> favorites;

  void addFavorite(int id) {
    favorites.add(id);
    notifyListeners();
  }

  void removeFavorite(int id) {
    favorites.remove(id);
    notifyListeners();
  }

  static NewsFavoriteSerivce get instance {
    if (_instance == null) {
      _instance = NewsFavoriteSerivce._();
      _instance!.favorites = [];
    }
    return _instance!;
  }
}