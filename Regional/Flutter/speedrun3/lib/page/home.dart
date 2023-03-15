import 'package:flutter/material.dart';
import 'package:speedrun3/page/about.dart';
import 'package:speedrun3/page/job.dart';
import 'package:speedrun3/page/news.dart';
import 'package:speedrun3/widget/top_nav_bar.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  Widget _buildNavButton(String title, Widget navTarget, BuildContext context) {
    return FilledButton(
      onPressed: () {
        Navigator.of(context).push(MaterialPageRoute(builder: (context) => navTarget));
      },
      style: ButtonStyle(
        shape: MaterialStatePropertyAll(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10)
          )
        ),
        fixedSize: const MaterialStatePropertyAll(
          Size(400, 60)
        ),
        textStyle: const MaterialStatePropertyAll(
          TextStyle(
            fontSize: 18
          )
        )
      ),
      child: Text(
        title
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(80),
          child: SizedBox(
            height: 800,
            child: Column(
              mainAxisSize: MainAxisSize.max,
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                const Text(
                  "第 53 屆全國技能競賽",
                  style: TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.bold
                  ),
                ),
                const Text(
                  "分區技能競賽",
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold
                  ),
                ),
                const SizedBox(height: 200,),
                _buildNavButton("最新消息", const NewsPage(), context),
                _buildNavButton("職類介紹", const JobPage(), context),
                _buildNavButton("關於技能競賽", const AboutPage(), context),
              ],
            ),
          ),
        ),
      ),
    );
  }
}