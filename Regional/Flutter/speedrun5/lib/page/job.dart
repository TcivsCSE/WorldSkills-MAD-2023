import 'package:flutter/material.dart';
import 'package:speedrun5/widget/top_nav_bar.dart';

class JobData {
  final String name;
  final String imageSource;
  final String content;

  const JobData(this.name, this.imageSource, this.content);
}

class JobPage extends StatelessWidget {
  const JobPage({super.key});

  static const List<JobData> _jobData = [
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
    JobData("test name", "assets/mol.png", "fjghdkslnbkghuojidfslnkbhdfudijoklfsjnkbfjsoiklmjknbhjsoisklnfhsknjlsdkjndfssdasdasdasd"),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "職類介紹"),
      body: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
        itemCount: _jobData.length,
        itemBuilder: (context, index) {
          final data = _jobData[index];
          return InkWell(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => JobDetailPage(data: data)));
            },
            child: Card(
              child: Stack(
                children: [
                  Image.asset(
                    data.imageSource,
                    fit: BoxFit.cover,
                  ),
                  Text(
                    data.name
                  )
                ],
              ),
            ),
          );
        },
      ),
    );
  }
}

class JobDetailPage extends StatelessWidget {
  const JobDetailPage({super.key, required this.data});

  final JobData data;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavBar(title: data.name),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Image.asset(
              data.imageSource
            ),
            Text(
              data.content * 10
            )
          ],
        ),
      ),
    );
  }
}