import 'package:flutter/material.dart';
import 'package:speedrun4/page/about.dart';
import 'package:speedrun4/page/job.dart';
import 'package:speedrun4/page/news.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  Widget _buildNavButton(String title, Widget navTarget, BuildContext context) {
    return FilledButton(
      onPressed: () {
        Navigator.of(context).push(MaterialPageRoute(builder: (context) => navTarget));
      },
      style: ButtonStyle(
        fixedSize: const MaterialStatePropertyAll(
          Size(200, 60)
        ),
        shape: MaterialStatePropertyAll(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10)
          )
        )
      ),
      child: Text(
        title,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 140),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Text(
                "第 53 屆全國技能競賽",
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold
                ),
              ),
              const Text(
                "分區技能競賽",
                style: TextStyle(
                  fontSize: 22,
                  fontWeight: FontWeight.bold
                ),
              ),
              const SizedBox(height: 140,),
              _buildNavButton("最新消息", const NewsPage(), context),
              _buildNavButton("關於技能競賽", const AboutPage(), context),
              _buildNavButton("職類介紹", const JobPage(), context),
            ],
          ),
        ),
      ),
    );
  }
}