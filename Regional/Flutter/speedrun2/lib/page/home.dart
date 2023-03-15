import 'package:flutter/material.dart';
import 'package:speedrun2/page/job.dart';
import 'package:speedrun2/page/news.dart';

import 'about.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  Widget _buildNavButton(String title, Widget target, BuildContext context) {
    return FilledButton(
      onPressed: () {
        Navigator.of(context).push(MaterialPageRoute(builder: (context) => target));
      },
      style: ButtonStyle(
        shape: MaterialStatePropertyAll(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10)
          ),
        ),
        fixedSize: const MaterialStatePropertyAll(
          Size(240, 60)
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
        child: SizedBox(
          height: 500,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            mainAxisSize: MainAxisSize.max,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const Text(
                "第 53 屆全國技能競賽",
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold
                )
              ),
              const Text(
                "分區技能競賽",
                style: TextStyle(
                  fontSize: 22,
                )
              ),
              const SizedBox(height: 200,),
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