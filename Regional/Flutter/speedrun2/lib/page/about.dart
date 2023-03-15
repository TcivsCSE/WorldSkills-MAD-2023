import 'package:flutter/material.dart';
import 'package:speedrun2/widget/top_nav_bar.dart';

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
  late TabController _tabController;

  static const List<AboutData> _aboutData = [
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
    AboutData("test title", "dshfgkjakfgjajkhjsueojdskhbhuojiesvfujoiskvssadfsdfasdf"),
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
            isScrollable: true,
            controller: _tabController,
            labelColor: Colors.black,
            unselectedLabelColor: Colors.black26,
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
                      Text(
                        e.content * 10
                      )
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