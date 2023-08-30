import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:module_2/page/chat.dart';
import 'package:module_2/page/events.dart';
import 'package:module_2/page/records.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
  await SystemChrome.setEnabledSystemUIMode(SystemUiMode.leanBack);
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Module 2',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MainPage(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  int navigationIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      bottomNavigationBar: NavigationBar(
        onDestinationSelected: (index) {
          setState(() {
            navigationIndex = index;
          });
        },
        selectedIndex: navigationIndex,
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.calendar_month),
            label: "Events",
          ),
          NavigationDestination(
            icon: Icon(Icons.airplane_ticket),
            label: "Tickets",
          ),
          NavigationDestination(
            icon: Icon(Icons.record_voice_over),
            label: "Records",
          ),
          NavigationDestination(
            icon: Icon(Icons.chat),
            label: "Chat",
          ),
        ],
      ),
      body: IndexedStack(
        index: navigationIndex,
        children: [
          const EventPage(),
          Container(
            color: Colors.blue,
            alignment: Alignment.center,
            child: const Text('Tickets'),
          ),
          const RecordPage(),
          const ChatPage(),
        ],
      )
    );
  }
}
