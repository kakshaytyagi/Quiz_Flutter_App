import 'package:flutter/material.dart';

import '../../Widgets/CustomListTile.dart';
import 'LearningScreen/LearningScreen.dart';


class PictureContentMenu extends StatelessWidget {
  final String title;
  final List<Color> listColors;

  PictureContentMenu({required this.title, required this.listColors});

  final List<String> options = [
    'Animal Learning',
    'Fruits Learning',
  ];

  final List<String> imagePaths = [
    'assets/icons/animalLogo.png',
    'assets/icons/fruitsLogo.png',
    'assets/icons/DrawLetter.png',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFecedf1),
      appBar: AppBar(
        title: Text(
          'Content Menu',
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),
        centerTitle: true,
        backgroundColor: Color(0xFFecedf1),
      ),
      body: ListView.builder(
        itemCount: options.length,
        itemBuilder: (context, index) {
          return CustomListTile(
            tileColor: listColors[index % listColors.length],
            title: options[index],
            imagePath: imagePaths[index],
            onTap: () {
              if (index == 0) {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => LearningScreen(
                      backgroundImage: 'assets/icons/forestScreen.jpg',
                      title: 'assets/icons/whoamI.png',
                      imageList: [
                        'assets/icons/Dog.png',
                        'assets/icons/Lion.png',
                        'assets/icons/Monkey.png',
                        'assets/icons/Bear.png',
                        'assets/icons/Fox.png',
                        'assets/icons/Cow.png',
                        'assets/icons/Deer.png',
                        'assets/icons/Elephent.png',
                        'assets/icons/Giraff.png',
                        'assets/icons/Hen.png',
                        'assets/icons/Horse.png',
                        'assets/icons/Panda.png',
                        'assets/icons/Pig.png',
                        'assets/icons/Rabbit.png',
                        'assets/icons/Sheep.png',
                        'assets/icons/Snake.png',
                        'assets/icons/Tiger.png',
                        'assets/icons/Zebra.png',
                      ],
                    ),
                  ),
                );
              } else {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => LearningScreen(
                      backgroundImage: 'assets/icons/fruitScreen.jpg',
                      title: 'assets/icons/whoamI.png',
                      imageList: [
                        'assets/icons/Apple.png',
                        'assets/icons/Banana.png',
                        'assets/icons/Orange.png',
                        'assets/icons/Grapes.png',
                        'assets/icons/PineApple.png',
                        'assets/icons/Pear.png',
                        'assets/icons/Pomegranate.png',
                        'assets/icons/Watermelon.png',
                        'assets/icons/Strawberry.png',
                      ],
                    ),
                  ),
                );
              }
            },
          );
        },
      ),
    );
  }
}


