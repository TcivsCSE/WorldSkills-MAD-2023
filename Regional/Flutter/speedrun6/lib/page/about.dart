import 'package:flutter/material.dart';
import 'package:speedrun6/widget/top_nav_bar.dart';

class AboutData {
  final String title;
  final String imageSource;
  final String content;

  const AboutData(this.title, this.imageSource, this.content);
}

class AboutPage extends StatefulWidget {
  const AboutPage({super.key});

  @override
  State<StatefulWidget> createState() => _AboutPageState();
}

class _AboutPageState extends State<AboutPage> with SingleTickerProviderStateMixin {
  late TabController _tabController;

  static const List<AboutData> _aboutData = [
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
    AboutData("test title", "assets/mol.png", "asdsahjdsifbhuiajfhbgdhijasnshuijknuhiadssad"),
  ];

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: _aboutData.length, vsync: this);
  }

  @override
  void dispose() {
    super.dispose();
    _tabController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "關於技能競賽"),
      body: Column(
        children: [
          TabBar(
            controller: _tabController,
            isScrollable: true,
            labelColor: Colors.black,
            unselectedLabelColor: Colors.black12,
            tabs: _aboutData.map((e) {
              return Tab(
                text: e.title,
              );
            }).toList()
          ),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: _aboutData.map((e) {
                return SingleChildScrollView(
                  child: Column(
                    children: [
                      Image.asset(e.imageSource),
                      Text(e.content * 10)
                    ],
                  ),
                );
              }).toList(),
            ),
          )
        ],
      ),
    );
  }
}
