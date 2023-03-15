import 'package:flutter/material.dart';
import 'package:speedrun1/widget/top_nav_bar.dart';

class JobData {
  final String name;
  final String imageSource;
  final String content;

  const JobData(this.name, this.imageSource, this.content);
}

class JobPage extends StatelessWidget {
  const JobPage({super.key});

  static const List<JobData> _jobData = [
    JobData("test 1", "assets/ROC_Ministry_of_Labor_Logo.png", "fghdjskashgsekwdnbhiweokdbhijlskhdjisklnbvfhijlksvbhkdfuijl"),
    JobData("test 2", "assets/ROC_Ministry_of_Labor_Logo.png", "fghdjskashgsekwdnbhiweokdbhijlskhdjisklnbvfhijlksvbhkdfuijl"),
    JobData("test 3", "assets/ROC_Ministry_of_Labor_Logo.png", "fghdjskashgsekwdnbhiweokdbhijlskhdjisklnbvfhijlksvbhkdfuijl"),
    JobData("test 4", "assets/ROC_Ministry_of_Labor_Logo.png", "fghdjskashgsekwdnbhiweokdbhijlskhdjisklnbvfhijlksvbhkdfuijl"),
    JobData("test 5", "assets/ROC_Ministry_of_Labor_Logo.png", "fghdjskashgsekwdnbhiweokdbhijlskhdjisklnbvfhijlksvbhkdfuijl"),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const TopNavBar(title: "職類介紹"),
      body: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
        itemCount: _jobData.length,
        itemBuilder: (context, index) {
          return Padding(
            padding: const EdgeInsets.all(10),
            child: InkWell(
              onTap: () {
                Navigator.of(context).push(MaterialPageRoute(builder: (context) => JobDetailPage(jobData: _jobData[index])));
              },
              child: Stack(
                alignment: Alignment.bottomLeft,
                children: [
                  Image.asset(_jobData[index].imageSource),
                  Text(_jobData[index].name)
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
  const JobDetailPage({super.key, required this.jobData});

  final JobData jobData;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavBar(title: jobData.name),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Image.asset(jobData.imageSource),
            Text(
              jobData.content * 10,
              style: const TextStyle(
                fontSize: 16
              ),
            )
          ],
        ),
      ),
    );
  }
}