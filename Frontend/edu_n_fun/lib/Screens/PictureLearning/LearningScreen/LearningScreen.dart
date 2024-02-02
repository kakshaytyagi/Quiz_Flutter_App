import 'package:flutter/material.dart';
import 'dart:math';
import 'package:path/path.dart' as p;

import '../../../Widgets/SoundManager.dart';
import 'NavigationButtons.dart';

class LearningScreen extends StatefulWidget {
  final String backgroundImage;
  final String title;
  final List<String> imageList;

  LearningScreen({
    required this.backgroundImage,
    required this.title,
    required this.imageList,
  });

  @override
  _LearningScreenState createState() => _LearningScreenState();
}

class _LearningScreenState extends State<LearningScreen>
    with SingleTickerProviderStateMixin {
  int currentIndex = 0;
  bool correctGuess = false;
  bool wrongGuess = false;
  bool answerSubmitted = false; // Track if an answer has been submitted

  String randomAnimal = ''; // Store the random animal name

  AnimationController? _animationController;
  Animation<double>? _animation;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: Duration(milliseconds: 500),
    );
    _animation = Tween<double>(begin: 0, end: 1).animate(_animationController!);
    currentIndex = 0; // Set currentIndex to 0 by default
    randomAnimal = _getRandomAnimal(); // Initialize random animal name
    _playAnimation(); // Call _playAnimation() to show the first image
  }

  @override
  void dispose() {
    _animationController?.dispose();
    super.dispose();
  }

  void _playAnimation() {
    _animationController?.forward(from: 0);
  }

  void _previousImage() {
    setState(() {
      if (currentIndex > 0) {
        currentIndex--;
        correctGuess = false;
        wrongGuess = false;
        answerSubmitted = false; // Reset answerSubmitted when changing the image
        _playAnimation();
      }
    });
  }

  void _nextImage() {
    setState(() {
      if (currentIndex < widget.imageList.length - 1) {
        currentIndex++;
        correctGuess = false;
        wrongGuess = false;
        answerSubmitted = false; // Reset answerSubmitted when changing the image
        randomAnimal = _getRandomAnimal(); // Update random animal name for the new image
        _playAnimation();
      }
    });
  }

  String _getCorrectAnimal() {
    String imagePath = widget.imageList[currentIndex];
    List<String> pathSegments = imagePath.split('/');
    String imageNameWithExtension = pathSegments.last;
    String imageName = imageNameWithExtension.split('.').first;
    return imageName;
  }

  String _getRandomAnimal() {
    String correctAnimal = _getCorrectAnimal();
    Random random = Random();
    int randomIndex;

    // Generate a random index until it's different from the correct animal's index
    do {
      randomIndex = random.nextInt(widget.imageList.length);
    } while (randomIndex == currentIndex);

    String imagePath = widget.imageList[randomIndex];
    return p.basenameWithoutExtension(imagePath);
  }

  void _correct() {
    SoundManager.playSound("happy");
  }

  void _wrong() {
    SoundManager.playSound("wrong");
  }

  void _checkAnswer(String animalName) {
    String correctAnimal = _getCorrectAnimal();
    setState(() {
      answerSubmitted = true;
      if (animalName == correctAnimal) {
        correctGuess = true;
        wrongGuess = false;
        _correct();
      } else {
        correctGuess = false;
        wrongGuess = true;
        _wrong();
      }
    });
  }

  Widget _buildChoiceButton(String animalName, bool selected) {
    List<Color> gradientColors;

    if (!answerSubmitted) {
      gradientColors = [Colors.blueAccent, Colors.blue.withOpacity(1)];
    } else {
      gradientColors = selected
          ? animalName == _getCorrectAnimal()
          ? [Colors.green, Colors.green.withOpacity(1)]
          : [Colors.red, Colors.red.withOpacity(1)]
          : [Colors.blueAccent, Colors.blue.withOpacity(1)];
    }

    return Expanded(
      child: SizedBox(
        width: 170,
        child: ElevatedButton(
          onPressed: () {
            if (!answerSubmitted) {
              _checkAnswer(animalName);
            }
          },
          style: ElevatedButton.styleFrom(
            primary: Colors.transparent,
            elevation: 0,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          child: Ink(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20),
              gradient: LinearGradient(
                colors: gradientColors,
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
              ),
            ),
            child: Container(
              constraints: BoxConstraints(
                minHeight: 80,
              ),
              alignment: Alignment.center,
              child: Text(
                animalName,
                style: TextStyle(fontSize: 25.6, color: Colors.white),
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    String currentAnimal = _getCorrectAnimal();

    return Container(
      decoration: BoxDecoration(
        image: DecorationImage(
          image: AssetImage(widget.backgroundImage),
          fit: BoxFit.cover,
        ),
      ),
      child: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          backgroundColor: Colors.transparent,
          elevation: 0,
        ),
        backgroundColor: Colors.transparent,
        body: Column(
          children: [
            Image.asset(
              widget.title,
              fit: BoxFit.cover,
              height: MediaQuery.of(context).size.height * 0.14,
            ),
            Expanded(
              child: Stack(
                fit: StackFit.expand,
                children: [
                  FadeTransition(
                    opacity: _animation!,
                    child: Container(
                      alignment: Alignment.topCenter,
                      child: Padding(
                        padding: EdgeInsets.all(40),
                        child: Image.asset(
                          widget.imageList[currentIndex],
                          fit: BoxFit.cover,
                          height: MediaQuery.of(context).size.height * 0.35,
                        ),
                      ),
                    ),
                  ),
                  Positioned(
                    bottom: 140,
                    left: 0,
                    right: 0,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        _buildChoiceButton(currentAnimal, correctGuess),
                        _buildChoiceButton(randomAnimal, wrongGuess),
                      ],
                    ),
                  ),
                  Positioned(
                    bottom: 0,
                    left: 0,
                    right: 0,
                    child: NavigationButtons(
                      previousImage: _previousImage,
                      nextImage: _nextImage,
                      showPrevious: currentIndex > 0,
                      showNext: currentIndex < widget.imageList.length - 1,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
