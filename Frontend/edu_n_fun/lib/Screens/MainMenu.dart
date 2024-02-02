import 'package:flutter/material.dart';
import 'package:edu_n_fun/Screens/AlphabetLearning/ContentMenu.dart';
import '../Widgets/CustomListTile.dart';
import 'CountingLearning/NumberContentMenu.dart';
import 'PictureLearning/PictureContentMenu.dart';
import 'QuizModule/quizContentMenu.dart';
import 'VideoLearning/VideoContentMenu.dart';

class MainMenu extends StatefulWidget { // Change to StatefulWidget
  @override
  _MainMenuState createState() => _MainMenuState();
}

class _MainMenuState extends State<MainMenu> {
  final List<Color> listColors = [
    Color(0x33bb8b),
    Color(0x5381f9),
    Color(0xd4b139),
    Color(0xd95d5d),
  ];

  final List<String> options = [
    'Alphabet Learning',
    'Counting Learning',
    'Picture Learning',
    'Video Learning',
    'QuizTion',
  ];

  final List<String> imagePaths = [
    'assets/icons/ABCLearning.png',
    'assets/icons/CountingLearning.png',
    'assets/icons/picturelearning.png',
    'assets/icons/vLearning.png',
    'assets/icons/quiz.png',
  ];


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFecedf1), // Set the background color
      appBar: AppBar(
        title: Text(
          'Main Menu',
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),
        centerTitle: true,
        backgroundColor: Color(0xFFecedf1), // Set the header color
      ),
      body: ListView.builder(
        itemCount: options.length,
        itemBuilder: (context, index) {
          return AnimatedSwitcher(
            duration: Duration(milliseconds: 500),
            transitionBuilder: (child, animation) {
              final offsetAnimation = Tween<Offset>(
                begin: Offset(1.0, 0.0),
                end: Offset.zero,
              ).animate(animation);
              return SlideTransition(
                position: Tween<Offset>(
                  begin: Offset(-1.0, 0.0),
                  end: Offset.zero,
                ).animate(animation),
                child: SlideTransition(
                  position: offsetAnimation,
                  child: child,
                ),
              );
            },
            child: CustomListTile(
              key: ValueKey(options[index]),
              tileColor: listColors[index % listColors.length],
              title: options[index],
              imagePath: imagePaths[index],
              onTap: () {
                Navigator.push(
                  context,
                  PageRouteBuilder(
                    pageBuilder: (context, animation, secondaryAnimation) {
                      if (options[index] == 'Alphabet Learning') {
                        return ContentMenuAlphabet(
                          title: options[index],
                          listColors: listColors,
                        );
                      } else if (options[index] == 'Counting Learning') {
                        return NumberContentMenu(
                          title: options[index],
                          listColors: listColors,
                        );
                      }else if (options[index] == 'Picture Learning') {
                        return PictureContentMenu(
                          title: options[index],
                          listColors: listColors,
                        );
                      } else if (options[index] == 'Video Learning') {
                        return videoContentMenu(
                          title: options[index],
                          listColors: listColors,
                        );
                      } else if (options[index] == 'QuizTion') {
                        return QuizContentMenu(
                          title: options[index],
                          listColors: listColors,
                        );
                      } else {
                        return QuizContentMenu(
                          title: options[index],
                          listColors: listColors,
                        );
                      };
                    },
                    transitionsBuilder: (context, animation, secondaryAnimation, child) {
                      return SlideTransition(
                        position: Tween<Offset>(
                          begin: Offset(1.0, 0.0),
                          end: Offset.zero,
                        ).animate(animation),
                        child: child,
                      );
                    },
                  ),
                );
              },
            ),
          );
        },
      ),
    );
  }
}