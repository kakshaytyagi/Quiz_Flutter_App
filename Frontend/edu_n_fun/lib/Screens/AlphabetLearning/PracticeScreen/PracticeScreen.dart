import 'package:flutter/material.dart';
import '../../../Widgets/SoundManager.dart';
import 'AlphabetDisplay.dart';
import 'BackgroundGradient.dart';
import 'CustomAppBar.dart';
import 'Whiteboard.dart';

class PracticeScreen extends StatefulWidget {
  final String optionTitle;

  PracticeScreen({required this.optionTitle});

  @override
  _PracticeScreenState createState() => _PracticeScreenState();
}

class _PracticeScreenState extends State<PracticeScreen> with SingleTickerProviderStateMixin {
  late AnimationController highlightController;
  late Animation<double> highlightAnimation;
  int _currentAlphabetIndex = 0;

  final List<String> alphabets = [
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
  ];

  String selectedAlphabet = '';


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

  void _playNextAlphabet() {
    if (_currentAlphabetIndex < alphabets.length - 1) {
      setState(() {
        _currentAlphabetIndex++;
        SoundManager.playSound(alphabets[_currentAlphabetIndex]);

        selectedAlphabet = '';
      });
    }
  }




  void _playPreviousAlphabet() {
    if (_currentAlphabetIndex > 0) {
      setState(() {
        _currentAlphabetIndex--;
        SoundManager.playSound(alphabets[_currentAlphabetIndex]);

        // Reset the selected alphabet in the Whiteboard widget
        selectedAlphabet = '';
      });
    }
  }

  void selectAlphabet(String alphabet) {
    setState(() {
      if (selectedAlphabet == alphabet) {
        selectedAlphabet = '';
      } else {
        selectedAlphabet = alphabet;
        highlightController.forward(from: 0.0);

        // Play the audio for the selected alphabet
        playAudio(alphabet);
      }
    });
  }

  void playAudio(String alphabet) async {
    try {
      await SoundManager.playSound(alphabet);
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
                child: AlphabetDisplay(alphabets: alphabets, currentAlphabetIndex: _currentAlphabetIndex),
              ),
              Whiteboard(
                currentAlphabetIndex: _currentAlphabetIndex,
              ),

              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    if (_currentAlphabetIndex > 0)
                      SizedBox(
                        width: 170,
                        child: ElevatedButton(
                          onPressed: () {
                            _playPreviousAlphabet();
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
                    if (_currentAlphabetIndex < alphabets.length - 1)
                      SizedBox(
                        width: 170,
                        child: ElevatedButton(
                          onPressed: () {
                            _playNextAlphabet();
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
