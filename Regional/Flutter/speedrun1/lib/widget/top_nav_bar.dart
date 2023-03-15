import 'package:flutter/material.dart';

class TopNavBar extends StatelessWidget with PreferredSizeWidget {

  final String title;

  const TopNavBar({super.key, required this.title});

  @override
  Widget build(BuildContext context) {
    return PhysicalModel(
      color: Colors.white,
      shadowColor: Colors.black,
      elevation: 1,
      child: Row(
        children: [
          IconButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            icon: const Icon(
              Icons.arrow_back,
              size: 30,
            ),
          ),
          Text(
            title,
            overflow: TextOverflow.ellipsis,
            style: const TextStyle(
              fontSize: 20
            ),
          )
        ],
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(80);
  
}