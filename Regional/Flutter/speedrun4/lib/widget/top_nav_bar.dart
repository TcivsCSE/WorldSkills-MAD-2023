import 'package:flutter/material.dart';

class TopNavBar extends StatelessWidget with PreferredSizeWidget {
  final String title;

  const TopNavBar({super.key, required this.title});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          icon: const Icon(Icons.arrow_back),
        ),
        Text(title)
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(60);
}