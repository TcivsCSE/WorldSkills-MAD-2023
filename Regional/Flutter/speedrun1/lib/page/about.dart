import 'package:flutter/material.dart';
import 'package:speedrun1/widget/top_nav_bar.dart';

class AboutData {
  final String title;
  final String content;
  final String imageSource;

  const AboutData(this.title, this.content, this.imageSource);
}

class AboutPage extends StatefulWidget {
  const AboutPage({super.key});
  
  @override
  State<StatefulWidget> createState() => _AboutPageState();
}

class _AboutPageState extends State<AboutPage> with SingleTickerProviderStateMixin {
  late TabController tabController;

  static const List<AboutData> _aboutData = [
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
    AboutData("test 1", "jhfgdkashguiajkvhbfisuwjlksbvhfijoewskb", "assets/ROC_Ministry_of_Labor_Logo.png"),
  ];

  @override
  void initState() {
    super.initState();
    tabController = TabController(length: _aboutData.length, vsync: this);
  }

  @override
  void dispose() {
    super.dispose();
    tabController.dispose();
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
            isScrollable: true,
            controller: tabController,
            tabs: _aboutData.map(
              (e) {
                return Tab(
                  text: e.title,
                );
              }
            ).toList()
          ),
          Expanded(
            child: TabBarView(
              controller: tabController,
              children: _aboutData.map(
                (e) {
                  return SingleChildScrollView(
                    child: Column(
                      children: [
                        Image.asset(e.imageSource),
                        Text(e.title)
                      ],
                    ),
                  );
                }
              ).toList(),
            ),
          )
        ],
      )
    );
  }
}