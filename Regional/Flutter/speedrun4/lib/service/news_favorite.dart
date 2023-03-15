import 'package:flutter/material.dart';

class NewsFavoriteService extends ChangeNotifier {
  NewsFavoriteService._();

  static NewsFavoriteService? _instance;
  late List<int> favorites;

  void addFavorite(int id) {
    favorites.add(id);
    notifyListeners();
  }

  void removeFavorite(int id) {
    favorites.remove(id);
    notifyListeners();
  }

  static NewsFavoriteService get instance {
    if (_instance == null) {
      _instance = NewsFavoriteService._();
      _instance!.favorites = [];
    }
    return _instance!;
  }
}
