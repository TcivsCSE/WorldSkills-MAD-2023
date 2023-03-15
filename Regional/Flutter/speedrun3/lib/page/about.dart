import 'package:flutter/material.dart';
import 'package:speedrun3/widget/top_nav_bar.dart';

class AboutData {
  final String title;
  final String content;

  const AboutData(this.title, this.content);
}

class AboutPage extends StatefulWidget {
  const AboutPage({super.key});

  @override
  State<StatefulWidget> createState() => _AboutPageState();
}

class _AboutPageState extends State<AboutPage> with SingleTickerProviderStateMixin {
  static const List<AboutData> _aboutData = [
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
    AboutData("test title", "fjhdkasbhjakbfhsjiekfsdjskassadfasfadsfadsfa"),
  ]; 

  late TabController _tabController;

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
            labelColor: Colors.black,
            unselectedLabelColor: Colors.black26,
            controller: _tabController,
            isScrollable: true,
            tabs: _aboutData.map((e) {
              return Tab(
                text: e.title,
              );
            }).toList(),
          ),
          Expanded(
            child: TabBarView(
              controller: _tabController,
              children: _aboutData.map((e) {
                return AboutDetailPage(data: e);
              }).toList(),
            ),
          )
        ],
      ),
    );
  }
}

class AboutDetailPage extends StatelessWidget {
  final AboutData data;

  const AboutDetailPage({super.key, required this.data});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Text(data.content * 10)
        ],
      ),
    );
  }
}