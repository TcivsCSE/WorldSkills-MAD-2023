import 'package:basic_build/page/login.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';

import 'home.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<StatefulWidget> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  void _checkLoginStatus() async {
    await Future.delayed(const Duration(seconds: 1));
    if (Hive.box("userData").isNotEmpty) {
      if (mounted) Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const HomePage()));
    } else {
      if (mounted) Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const LoginPage()));
    }
  }
  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Image.asset("assets/skillsweek_icon.png"),
      )
    );
  }
}