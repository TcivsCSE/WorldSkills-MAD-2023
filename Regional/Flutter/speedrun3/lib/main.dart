import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:speedrun3/page/home.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const SplashPage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<SplashPage> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  Future _delayNavToHome() async {
    await Future.delayed(const Duration(milliseconds: 350));
    if (mounted) Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const HomePage()));
  }

  @override
  void initState() {
    super.initState();
    _delayNavToHome();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(60),
          child: Image.asset(
            "assets/mol.png"
          ),
        )
      ),
    );
  }
}
