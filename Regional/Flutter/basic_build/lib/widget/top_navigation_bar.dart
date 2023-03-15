import 'package:flutter/material.dart';

class TopNavigationBar extends StatelessWidget with PreferredSizeWidget {
  const TopNavigationBar({super.key, required this.title});

  final String title;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        IconButton(
          onPressed: () {
            Navigator.of(context).pop();
          }, 
          icon: const Icon(Icons.arrow_back)
        ),
        Text(
          title,
          style: const TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.normal
          ),
        )
      ],
    );
  }
  
  @override
  Size get preferredSize => const Size.fromHeight(80);
}