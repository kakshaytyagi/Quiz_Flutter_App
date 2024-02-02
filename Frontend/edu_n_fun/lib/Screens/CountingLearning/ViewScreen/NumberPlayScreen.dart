import 'package:flutter/material.dart';

import '../../../Widgets/SoundManager.dart';
import 'NumberScreen.dart';

class NumberPlayScreen extends StatefulWidget {
  final String optionTitle;

  NumberPlayScreen({required this.optionTitle});

  @override
  _PlayScreenState createState() => _PlayScreenState();
}

class _PlayScreenState extends State<NumberPlayScreen> with SingleTickerProviderStateMixin {
  late AnimationController _animationController;
  late Animation<double> _scaleAnimation;
  int _currentNumberIndex = 0;

  final List<String> numbers = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
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
    SoundManager.playSound(numbers[0]);
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  void _playNextNumber() {
    if (_currentNumberIndex < numbers.length - 1) {
      setState(() {
        _currentNumberIndex++;
        _animationController.reset();
        _animationController.forward();
        SoundManager.playSound(numbers[_currentNumberIndex]);
      });
    } else {
      setState(() {
        _currentNumberIndex = 0; // Go back to number 0
        SoundManager.playSound(numbers[_currentNumberIndex]);
      });
    }
  }

  void _playPreviousNumber() {
    if (_currentNumberIndex > 0) {
      setState(() {
        _currentNumberIndex--;
        _animationController.reset();
        _animationController.forward();
        SoundManager.playSound(numbers[_currentNumberIndex]);
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
                for (int numberIndex = 0; numberIndex < numbers.length; numberIndex++)
                  Visibility(
                    visible: _currentNumberIndex == numberIndex,
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Container(
                        transform: Matrix4.translationValues(0, -30, 0),
                        child: NumberCode(number: numbers[numberIndex]),
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
                if (_currentNumberIndex > 0)
                  SizedBox(
                    width: 170,
                    child: ElevatedButton(
                      onPressed: () {
                        setState(() {
                          _currentNumberIndex--;
                          _animationController.reset();
                          _animationController.forward();
                          SoundManager.playSound(numbers[_currentNumberIndex]);
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
                if (_currentNumberIndex < numbers.length - 1)
                  SizedBox(
                    width: 170,
                    child: ElevatedButton(
                      onPressed: _playNextNumber,
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
