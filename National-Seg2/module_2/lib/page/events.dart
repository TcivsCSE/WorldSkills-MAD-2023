import 'dart:convert';

import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;

class EventData {
  final String id;
  final String imageSource;
  final String title;
  final String content;
  final bool isReaded;

  EventData(this.id, this.imageSource, this.title, this.content, this.isReaded);
}

class EventPage extends StatefulWidget {
  const EventPage({super.key});

  @override
  State<StatefulWidget> createState() => _EventPageState();
}

class _EventPageState extends State<EventPage> {
  List<EventData> events = [];

  void _fetchEvents() async {
    http.Response res = await http.get(Uri.parse("http://10.0.2.2:8000/m2/events"));
    dynamic data = jsonDecode(utf8.decode(res.bodyBytes));
    events.clear();
    for (dynamic event in data) {
      events.add(
        EventData(
          event["id"],
          event["image"],
          event["title"],
          event["content"],
          event["is_readed"],
        )
      );
    }
    setState(() {
      
    });
  }

  @override
  void initState() {
    _fetchEvents();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        floatingActionButton: IconButton(
          onPressed: _fetchEvents,
          icon: const Icon(Icons.refresh),
        ),
        appBar: AppBar(
          title: const Text("Events", style: TextStyle(color: Colors.black),),
          backgroundColor: Colors.transparent,
          shadowColor: Colors.transparent,
          bottom: const TabBar(
            labelColor: Colors.black,
            unselectedLabelColor: Colors.black38,
            tabs: [
              Tab(text: "All",),
              Tab(text: "Unread",),
              Tab(text: "Read",),
            ],
          ),
        ),
        body: TabBarView(
          children: [
            ListView(
              children: events.map((e) => EventCard(data: e)).toList(),
            ),
            ListView(
              children: events.where((e) => !e.isReaded).map((e) => EventCard(data: e)).toList(),
            ),
            ListView(
              children: events.where((e) => e.isReaded).map((e) => EventCard(data: e)).toList(),
            ),
          ],
        ),
      ),
    );
  }
}

class EventCard extends StatelessWidget {
  const EventCard({super.key, required this.data});

  final EventData data;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 6, horizontal: 12),
      child: Row(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          ClipRRect(
            borderRadius: BorderRadius.circular(6),
            child: Image.network(
              data.imageSource,
              height: 100,
              width: 100,
              fit: BoxFit.cover,
            ),
          ),
          const SizedBox(width: 12,),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(
                width: 240,
                child: Text(
                  data.title,
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16
                  ),
                ),
              ),
              SizedBox(
                width: 240,
                height: 50,
                child: Text(
                  data.content,
                  overflow: TextOverflow.ellipsis,
                  maxLines: 3,
                ),
              ),
              Text(data.isReaded ? "Read" : "Unread")
            ],
          ),
        ],
      ),
    );
  }
}
