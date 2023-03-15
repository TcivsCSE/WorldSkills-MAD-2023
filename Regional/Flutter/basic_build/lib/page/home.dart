import 'package:basic_build/page/about.dart';
import 'package:basic_build/page/category.dart';
import 'package:basic_build/page/news.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<StatefulWidget> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  Widget _buildButton(String title, Widget navigationTarget) {
    return FilledButton(
      onPressed: () {
        Navigator.of(context).push(
          MaterialPageRoute(builder: (context) => navigationTarget)
        );
      },
      style: ButtonStyle(
        shape: MaterialStatePropertyAll(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12)
          )
        ),
        fixedSize: const MaterialStatePropertyAll(
          Size(260, 60)
        ),
        textStyle: const MaterialStatePropertyAll(
          TextStyle(
            fontSize: 20
          )
        )
      ),
      child: Text(title),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SizedBox(
          width: double.infinity,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            children: [
              const Text(
                "第 53 屆全國技能競賽",
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 30
                ),
              ),
              const Text(
                "分區技能競賽",
                style: TextStyle(
                  fontWeight: FontWeight.w500,
                  fontSize: 28
                ),
              ),
              const SizedBox(height: 180,),
              _buildButton("最新消息", const NewsPage()),
              const SizedBox(height: 40,),
              _buildButton("關於技能競賽", const AboutPage()),
              const SizedBox(height: 40,),
              _buildButton("職類介紹", const CategoryPage()),
            ],
          ),
        ),
      );
  }
}
