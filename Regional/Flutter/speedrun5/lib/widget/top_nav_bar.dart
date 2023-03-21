import 'package:flutter/material.dart';

class TopNavBar extends StatelessWidget with PreferredSizeWidget {
  const TopNavBar({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.white,
      leading: IconButton(
        onPressed: () {
          Navigator.of(context).pop();
        },
        icon: const Icon(
          Icons.arrow_back,
          color: Colors.black,
        ),
      ),
      title: Text(
        title,
        style: const TextStyle(
          fontSize: 18,
          color: Colors.black
        ),
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(55);
}
