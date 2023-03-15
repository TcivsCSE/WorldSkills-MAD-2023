import 'package:basic_build/widget/top_navigation_bar.dart';
import 'package:flutter/material.dart';

class CategoryData {
  final String title;
  final String imageSource;
  final String content;

  const CategoryData(this.title, this.imageSource, this.content);
}

class CategoryPage extends StatelessWidget {
  const CategoryPage({super.key});

  static const List<CategoryData> _data = [
    CategoryData("工業機械", "assets/166219055245230.jpeg", """01 工業機械

職類介紹
工業機械是運用人員參與工廠中安裝、保養、維修及移除機械和設備，並了解用於各種機械的工業規定及標準。

競賽方式
一、競賽試題不公開。
二、競賽採模組化進行：1.模組一氣壓。2.模組二銲接。3.模組三車床。4.模組四銑床。5.模組五設計及組裝。競賽時間：18小時。進入競賽場即需配穿著工作服及安全鞋，不得配戴領帶及戒子等影響工作安全之物，且操作迴轉機器不得穿戴手套。並依標準作業程序(S.O.P)及操作注意事項來操作相關機具。

未來發展
精密機械工程師
製造工程師
機電自動化工程師
電控工程師             
機械類（如車床、銑床、磨床、CNC車床、CNC銑床等）
維修與組裝人員
控制類維修與組裝人員"""),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: const TopNavigationBar(title: "職類介紹"),
        body: GridView.builder(
          gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 3),
          itemCount: _data.length,
          itemBuilder: (context, index) {
            return InkWell(
              onTap: () {
                Navigator.of(context).push(MaterialPageRoute(builder: (context) => CategoryDetailPage(title: _data[index].title, imageSource: _data[index].imageSource, content: _data[index].content)));
              },
              child: Card(
                child: Stack(
                  alignment: Alignment.bottomLeft,
                  fit: StackFit.expand,
                  children: [
                    Image.asset(
                      _data[index].imageSource,
                      fit: BoxFit.cover,
                      color: Colors.black.withOpacity(0.2),
                      colorBlendMode: BlendMode.darken,
                    ),
                    Align(
                      alignment: Alignment.bottomLeft,
                      child: Padding(
                        padding: const EdgeInsets.all(10),
                        child: Text(
                          _data[index].title,
                          overflow: TextOverflow.ellipsis,
                          textAlign: TextAlign.start,
                          style: const TextStyle(letterSpacing: 1.2, color: Colors.white, fontWeight: FontWeight.bold, fontSize: 18),
                        ),
                      ),
                    )
                  ],
                ),
              ),
            );
          },
        ));
  }
}

class CategoryDetailPage extends StatelessWidget {
  const CategoryDetailPage({super.key, required this.title, required this.imageSource, required this.content});

  final String title;
  final String imageSource;
  final String content;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: TopNavigationBar(title: title),
      body: Column(
        children: [
          Image.asset(imageSource),
          Expanded(
            child: SingleChildScrollView(
              child: Text(
                content + content,
                style: const TextStyle(fontSize: 17),
              ),
            ),
          )
        ],
      ),
    );
  }
}
