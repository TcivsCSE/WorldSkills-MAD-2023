import 'dart:convert';

import 'package:basic_build/constants.dart';
import 'package:basic_build/page/signup.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

import 'home.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  late final TextEditingController _emailController;
  late final TextEditingController _passwordController;

  @override
  void initState() {
    _emailController = TextEditingController();
    _passwordController = TextEditingController();
    super.initState();
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          const Spacer(flex: 2,),
          Center(
            child: Image.asset(
              "assets/skillsweek_icon.png",
              height: 200,
              width: 200,
            ),
          ),
          const Spacer(flex: 2,),
          SizedBox(
            width: 300,
            height: 60,
            child: TextField(
              controller: _emailController,
              keyboardType: TextInputType.emailAddress,
              maxLines: 1,
              decoration: const InputDecoration(
                hintText: "電子郵箱",
                icon: Icon(Icons.email),
              ),
            ),
          ),
          SizedBox(
            width: 300,
            height: 60,
            child: TextField(
              controller: _passwordController,
              obscureText: true,
              maxLines: 1,
              decoration: const InputDecoration(
                hintText: "密碼",
                icon: Icon(Icons.password),
              ),
            ),
          ),
          const Spacer(),
          ElevatedButton(
            style: ButtonStyle(
              fixedSize: const MaterialStatePropertyAll(
                Size(200, 48)
              ),
              shape: MaterialStatePropertyAll(
                RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10)
                )
              )
            ),
            onPressed: () async {
              if (_emailController.text.isEmpty || _passwordController.text.isEmpty) return;
              http.Response res = await http.post(
                Uri.parse("${Constants.baseUrl}/forum/account/login"),
                headers: {"Content-Type": "application/json"},
                body: jsonEncode({
                  "email": _emailController.text,
                  "password": _passwordController.text
                })
              );
              Map<String, dynamic> data = jsonDecode(utf8.decode(res.bodyBytes));
              if (data.containsKey("err")) return;
              Hive.box("userData").putAll(data);
              if (mounted) Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (context) => const HomePage()));
            },
            child: const Text(
              "登入",
              style: TextStyle(
                fontSize: 20
              ),
            ),
          ),
          const SizedBox(height: 10,),
          Row(
            mainAxisSize: MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text("沒有帳號？"),
              InkWell(
                onTap: () {
                  Navigator.of(context).push(MaterialPageRoute(builder: (context) => const SignupPage()));
                },
                child: const Text("註冊"),
              )
            ],
          ),
          const Spacer(flex: 5,),
        ],
      ),
    );
  }
}
