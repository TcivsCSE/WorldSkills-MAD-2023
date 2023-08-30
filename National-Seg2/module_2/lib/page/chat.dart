import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:uuid/uuid.dart';

class ChatMessage {
  final String username;
  final String content;
  final bool isSelf;

  ChatMessage(this.username, this.content, this.isSelf);
}

class ChatPage extends StatefulWidget {
  const ChatPage({super.key});

  @override
  State<StatefulWidget> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  final List<ChatMessage> _chatMessages = [];
  final String _currentUuid = const Uuid().v4();
  late WebSocket _chatSocket;
  late TextEditingController _chatInputController;
  late ScrollController _chatScrollController;

  void _connectSocket() async {
    _chatSocket = await WebSocket.connect("ws://10.0.2.2:8765");
    _chatSocket.asBroadcastStream().listen(
      (event) {
        var message = jsonDecode(event);
        
        setState(() {
          _chatMessages.add(
            ChatMessage(
              message["username"],
              message["content"],
              message["uuid"] == _currentUuid
            )
          );
          
        });

        _chatScrollController.animateTo(
          0.0,
          duration: const Duration(milliseconds: 200),
          curve: Curves.easeIn
        ); 
      }
    );
  }

  @override
  void initState() {
    _chatMessages.clear();
    _chatInputController = TextEditingController();
    _chatScrollController = ScrollController();
    _connectSocket();
    super.initState();
  }

  @override
  void dispose() {
    _chatSocket.close();
    _chatInputController.dispose();
    _chatScrollController.dispose();
    _chatMessages.clear();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
            child: ListView(
              reverse: true,
              controller: _chatScrollController,
              children: _chatMessages.reversed.map(
                (data) {
                  return Row(
                    mainAxisAlignment: data.isSelf ? MainAxisAlignment.end : MainAxisAlignment.start,
                    children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 0.8),
                        child: Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(12),
                            color: data.isSelf ? Colors.blueAccent.shade200 : Colors.grey.shade300,
                          ),
                          child: Padding(
                            padding: const EdgeInsets.all(12),
                            child: Text(
                              data.content
                            ),
                          ),
                        ),
                      )
                      
                    ],
                  );
                }
              ).toList(),
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 12),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _chatInputController,
                  ),
                ),
                SizedBox(
                  width: 60,
                  height: 60,
                  child: ElevatedButton(
                    onPressed: () {
                      _chatSocket.add(
                        jsonEncode(
                          {
                            "username": "Lucy",
                            "content": _chatInputController.text,
                            "uuid": _currentUuid
                          }
                        )
                      );
                    },
                    style: const ButtonStyle(
                      backgroundColor: MaterialStatePropertyAll(
                        Colors.transparent
                      ),
                      shadowColor: MaterialStatePropertyAll(
                        Colors.transparent
                      ),
                      foregroundColor: MaterialStatePropertyAll(
                        Colors.blue
                      )
                    ),
                    child: const Icon(Icons.send)
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
