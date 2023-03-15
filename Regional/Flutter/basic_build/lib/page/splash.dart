import 'package:basic_build/page/home.dart';
import 'package:flutter/material.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<StatefulWidget> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  Future _jumpToHomePage() async {
    await Future.delayed(const Duration(milliseconds: 0));

    if (context.mounted) {
      Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const HomePage()));
    }
  }

  @override
  void initState() {
    super.initState();
    _jumpToHomePage();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(125),
          child: Image.asset(
            "assets/ROC_Ministry_of_Labor_Logo.png",
          ),
        ),
      ),
    );
  }
}
