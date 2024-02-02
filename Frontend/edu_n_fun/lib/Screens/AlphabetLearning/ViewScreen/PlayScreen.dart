import 'package:flutter/material.dart';

import '../../../Widgets/SoundManager.dart';
import 'letterScreen.dart';

class PlayScreen extends StatefulWidget {
  final String optionTitle;

  PlayScreen({required this.optionTitle});

  @override
  _PlayScreenState createState() => _PlayScreenState();
}

class _PlayScreenState extends State<PlayScreen> with SingleTickerProviderStateMixin {
  late AnimationController _animationController;
  late Animation<double> _scaleAnimation;
  int _currentAlphabetIndex = 0;

  final List<String> alphabets = [
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
  ];

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: Duration(seconds: 2),
    );
    _scaleAnimation = Tween<double>(begin: 0.0, end: 1.0).animate(_animationController);
    _animationController.forward();
    SoundManager.playSound(alphabets[0]);
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  void _playNextAlphabet() {
    if (_currentAlphabetIndex < alphabets.length - 1) {
      setState(() {
        _currentAlphabetIndex++;
        _animationController.reset();
        _animationController.forward();
        SoundManager.playSound(alphabets[_currentAlphabetIndex]);
      });
    } else {
      setState(() {
        _currentAlphabetIndex = 0; // Go back to letter A
        SoundManager.playSound(alphabets[_currentAlphabetIndex]);
      });
    }
  }

  void _playPreviousAlphabet() {
    if (_currentAlphabetIndex > 0) {
      setState(() {
        _currentAlphabetIndex--;
        _animationController.reset();
        _animationController.forward();
        SoundManager.playSound(alphabets[_currentAlphabetIndex]);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.optionTitle,
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),
        centerTitle: true,
        backgroundColor: Color(0xFFecedf1),
      ),
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [
                  Color(0x033168),
                  // Colors.black,
                  Color(0x065693),
                  Color(0x0f7ab2),
                ],
              ),
              borderRadius: BorderRadius.circular(10),
            ),
          ),
          Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                for (int alphabetIndex = 0; alphabetIndex < alphabets.length; alphabetIndex++)
                  Visibility(
                    visible: _currentAlphabetIndex == alphabetIndex,
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        transform: Matrix4.translationValues(0, -30, 0),
                        child: LetterCode(alphabet: alphabets[alphabetIndex]),
                      ),
                    ),
                  ),
                SizedBox(height: 24),
              ],
            ),
          ),
          Positioned(
            bottom: 16,
            left: 16,
            right: 16,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                if (_currentAlphabetIndex > 0)
                  SizedBox(
                    width: 170,
                    child: ElevatedButton(
                      onPressed: () {
                        setState(() {
                          _currentAlphabetIndex--;
                          _animationController.reset();
                          _animationController.forward();
                          SoundManager.playSound(alphabets[_currentAlphabetIndex]);
                        });
                      },
                      child: Text('Back'),
                      style: ElevatedButton.styleFrom(
                        padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                        textStyle: TextStyle(fontSize: 18),
                      ),
                    ),
                  ),
                Expanded(child: Container()), // Add an empty expanded widget to center the next button
                if (_currentAlphabetIndex < alphabets.length - 1)
                  SizedBox(
                    width: 170,
                    child: ElevatedButton(
                      onPressed: _playNextAlphabet,
                      child: Text('Next'),
                      style: ElevatedButton.styleFrom(
                        padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                        textStyle: TextStyle(fontSize: 18),
                      ),
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
