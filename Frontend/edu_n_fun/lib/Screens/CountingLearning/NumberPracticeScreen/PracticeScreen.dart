import 'package:flutter/material.dart';
import '../../../Widgets/SoundManager.dart';
import 'NumberDisplay.dart';
import 'BackgroundGradient.dart';
import 'CustomAppBar.dart';
import 'Whiteboard.dart';

class NumberPracticeScreen extends StatefulWidget {
  final String optionTitle;

  NumberPracticeScreen({required this.optionTitle});

  @override
  _PracticeScreenState createState() => _PracticeScreenState();
}

class _PracticeScreenState extends State<NumberPracticeScreen> with SingleTickerProviderStateMixin {
  late AnimationController highlightController;
  late Animation<double> highlightAnimation;
  int _currentNumberIndex = 0;

  final List<String> numbers = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
  ];

  String selectedNumber = '0';

  @override
  void initState() {
    super.initState();
    highlightController = AnimationController(
      duration: const Duration(milliseconds: 500),
      vsync: this,
    );
    highlightAnimation = Tween<double>(begin: 1.0, end: 1.2).animate(
      CurvedAnimation(
        parent: highlightController,
        curve: Curves.easeInOut,
      ),
    );
  }

  @override
  void dispose() {
    highlightController.dispose();
    super.dispose();
  }

  void _playNextNumber() {
    if (_currentNumberIndex < numbers.length - 1) {
      setState(() {
        _currentNumberIndex++;
        SoundManager.playSound(numbers[_currentNumberIndex]);

        selectedNumber = '0';
      });
    }
  }

  void _playPreviousNumber() {
    if (_currentNumberIndex > 0) {
      setState(() {
        _currentNumberIndex--;
        SoundManager.playSound(numbers[_currentNumberIndex]);

        // Reset the selected number in the Whiteboard widget
        selectedNumber = '0';
      });
    }
  }

  void selectNumber(String number) {
    setState(() {
      if (selectedNumber == number) {
        selectedNumber = '0';
      } else {
        selectedNumber = number;
        highlightController.forward(from: 0.0);

        // Play the audio for the selected number
        playAudio(number);
      }
    });
  }

  void playAudio(String number) async {
    try {
      await SoundManager.playSound(number);
    } catch (e) {
      print('Error playing audio: $e');
    }
  }

  void clearCanvas() {
    setState(() {
      print('Clearing the canvas');
    });
    // Logic to clear the canvas
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(optionTitle: widget.optionTitle),
      body: Stack(
        children: [
          BackgroundGradient(),
          Column(
            children: [
              Expanded(
                child: NumberDisplay(numbers: numbers, currentNumberIndex: _currentNumberIndex),
              ),
              Whiteboard(
                currentIndex: _currentNumberIndex,
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    if (_currentNumberIndex > 0)
                      SizedBox(
                        width: 170,
                        child: ElevatedButton(
                          onPressed: () {
                            _playPreviousNumber();
                            clearCanvas();
                          },
                          child: Text('Back'),
                          style: ElevatedButton.styleFrom(
                            padding: EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                            textStyle: TextStyle(fontSize: 18),
                          ),
                        ),
                      ),
                    Spacer(), // Add a spacer to center the next button
                    if (_currentNumberIndex < numbers.length - 1)
                      SizedBox(
                        width: 170,
                        child: ElevatedButton(
                          onPressed: () {
                            _playNextNumber();
                            clearCanvas();
                          },
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
        ],
      ),
    );
  }
}
