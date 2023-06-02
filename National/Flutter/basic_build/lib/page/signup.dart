import 'dart:convert';

import 'package:basic_build/constants.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:http/http.dart' as http;

import 'home.dart';

class SignupPage extends StatefulWidget {
  const SignupPage({super.key});

  @override
  State<StatefulWidget> createState() => _SignupPageState();
}

class _SignupPageState extends State<SignupPage> {
  late final TextEditingController _nicknameController;
  late final TextEditingController _emailController;
  late final TextEditingController _passwordController;

  @override
  void initState() {
    _nicknameController = TextEditingController();
    _emailController = TextEditingController();
    _passwordController = TextEditingController();
    super.initState();
  }

  @override
  void dispose() {
    _nicknameController.dispose();
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
              controller: _nicknameController,
              keyboardType: TextInputType.name,
              maxLines: 1,
              decoration: const InputDecoration(
                hintText: "暱稱",
                icon: Icon(Icons.account_circle),
              ),
            ),
          ),
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
              if (_nicknameController.text.isEmpty || _emailController.text.isEmpty || _passwordController.text.isEmpty) return;
              http.Response res = await http.post(
                Uri.parse("${Constants.baseUrl}/forum/account/signup"),
                headers: {"Content-Type": "application/json"},
                body: jsonEncode({
                  "nickname": _nicknameController.text,
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
              "註冊",
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
              const Text("已經有帳號了？"),
              InkWell(
                onTap: () {
                  Navigator.of(context).pop();
                },
                child: const Text("登入"),
              )
            ],
          ),
          const Spacer(flex: 5,),
        ],
      ),
    );
  }
}
