import 'package:flutter/material.dart';

import '../../Widgets/CustomListTile.dart';
import 'AlphabetSounds/LetterSounds.dart';
import 'PracticeScreen/PracticeScreen.dart';
import 'ViewScreen/PlayScreen.dart';

class ContentMenuAlphabet extends StatelessWidget {
  final String title;
  final List<Color> listColors;

  ContentMenuAlphabet({required this.title, required this.listColors});

  final List<String> options = [
    'Letters with Sound',
    'View & Listen',
    'Draw & Practice',
  ];

  final List<String> imagePaths = [
    'assets/icons/LetterSounds.png',
    'assets/icons/LetterSound.png',
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
                    builder: (context) => LetterSounds(optionTitle: options[index]),
                  ),
                );
              } else if (index == 2) {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => PracticeScreen(optionTitle: options[index]),
                  ),
                );
              } else {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => PlayScreen(optionTitle: options[index]),
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


