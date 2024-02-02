import 'package:flutter/material.dart';

class AlphabetDisplay extends StatelessWidget {
  final List<String> alphabets;
  final int currentAlphabetIndex;

  AlphabetDisplay({required this.alphabets, required this.currentAlphabetIndex});

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.topCenter,
      padding: EdgeInsets.only(top: 16, right: 16),
      child: Stack(
        children: [
          for (int alphabetIndex = 0; alphabetIndex < alphabets.length; alphabetIndex++)
            Visibility(
              visible: currentAlphabetIndex == alphabetIndex,
              child: Container(
                transform: Matrix4.translationValues(0, -40, 0),
                child: Text(
                  alphabets[alphabetIndex],
                  style: TextStyle(
                    fontFamily: 'Raleway',
                    fontWeight: FontWeight.w900,
                    fontSize: 120, // Adjust the font size as desired
                    color: Colors.white, // Set the text color
                  ),
                ),
              ),
            ),
        ],
      ),
    );
  }
}