import 'package:flutter/material.dart';
import 'package:speedrun2/widget/top_nav_bar.dart';

class JobData {
  final String name;
  final String imageSource;
  final String content;

  const JobData(this.name, this.imageSource, this.content);
}

class JobPage extends StatelessWidget {
  const JobPage({super.key});

  static const List<JobData> _jobData = [
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
    JobData("test name", "assets/ROC_Ministry_of_Labor_Logo.png", "fhjdkshguijshuijsisdfadsfasdfadsfasdfjsk"),
  ];


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "職類介紹"),
      body: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
        itemCount: _jobData.length,
        itemBuilder: (context, index) {
          return InkWell(
            onTap: () {
              Navigator.of(context).push(MaterialPageRoute(builder: (context) => JobDetailPage(data: _jobData[index])));
            },
            child: Card(
              child: Stack(
                children: [
                  Image.asset(
                    _jobData[index].imageSource
                  ),
                  Text(
                    _jobData[index].name
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