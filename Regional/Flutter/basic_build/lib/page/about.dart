import 'package:basic_build/widget/top_navigation_bar.dart';
import 'package:flutter/material.dart';

class AboutData {
  final String title;

  const AboutData(this.title);
}

class AboutPage extends StatefulWidget {
  const AboutPage({super.key});

  @override
  State<StatefulWidget> createState() => _AboutPageState();
}

class _AboutPageState extends State<AboutPage> with SingleTickerProviderStateMixin {
  static const List<AboutData> _data = [
    AboutData("最新消息"),
    AboutData("關於競賽"),
    AboutData("職類介紹"),
    AboutData("適性測驗"),
    AboutData("技能競賽吉祥物"),
    AboutData("主視覺"),
    AboutData("歷史簡介"),
    AboutData("職類展示館"),
  ];

  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: _data.length, vsync: this);
  }

  @override
  void dispose() {
    super.dispose();
    _tabController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavigationBar(title: "關於技能競賽"),
      body: Column(
        children: [
          SizedBox(
            width: double.infinity,
            height: 50,
            child: TabBar(
              controller: _tabController,
              isScrollable: true,
              labelColor: Colors.black,
              unselectedLabelColor: Colors.black26,
              tabs: _data.map((data) {
                return Tab(
                  text: data.title,
                );
              }).toList(),
            ),
          ),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: _data.map((data) {
                return Card(
                  child: AboutDetailPage(content: data.title),
                );
              }).toList(),
            ),
          )
        ],
      ),
    );
  }
}

class AboutDetailPage extends StatelessWidget {

  final String content;

  const AboutDetailPage({super.key, required this.content});

  @override
  Widget build(BuildContext context) {
    return Text(content);
  }
}
