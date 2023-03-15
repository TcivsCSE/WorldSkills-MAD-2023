
import 'package:flutter/material.dart';
import 'package:speedrun1/page/about.dart';
import 'package:speedrun1/page/job.dart';
import 'package:speedrun1/page/news.dart';
import 'package:speedrun1/widget/top_nav_bar.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  Widget _buildNavButton(String title, Widget target, BuildContext context) {
    return FilledButton(
      style: ButtonStyle(
        shape: MaterialStatePropertyAll(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10)
          )
        ),
        fixedSize: const MaterialStatePropertyAll(
          Size(260, 60)
        )
      ),
      onPressed: () {
        Navigator.of(context).push(MaterialPageRoute(builder: (context) => target));
      },
      child: Text(
        title,
        style: const TextStyle(
          fontSize: 20
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SizedBox(
          width: double.infinity,
          height: 500,
          child: Column(
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Text(
                "2023 全國技能競賽",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 28
                ),
              ),
              const Text(
                "分區技能競賽",
                style: TextStyle(
                  fontWeight: FontWeight.w500,
                  fontSize: 22
                ),
              ),
              const SizedBox(height: 200,),
              _buildNavButton(
                "最新消息",
                const NewsPage(),
                context
              ),
              _buildNavButton(
                "關於技能競賽",
                const AboutPage(),
                context
              ),
              _buildNavButton(
                "職類介紹",
                const JobPage(),
                context
              )
            ],
          ),
        ),
      ),
    );
  }
}