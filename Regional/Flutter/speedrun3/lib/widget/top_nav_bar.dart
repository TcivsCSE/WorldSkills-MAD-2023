import 'package:flutter/material.dart';

class TopNavBar extends StatelessWidget with PreferredSizeWidget {
  const TopNavBar({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.white,
      foregroundColor: Colors.black,
      leading: IconButton(
        onPressed: () {
          Navigator.of(context).pop();
        },
        icon: const Icon(Icons.arrow_back),
      ),
      title: Text(
        title
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(50);
}