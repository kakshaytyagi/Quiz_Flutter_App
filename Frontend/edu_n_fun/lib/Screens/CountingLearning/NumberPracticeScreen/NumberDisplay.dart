import 'package:flutter/material.dart';

class NumberDisplay extends StatelessWidget {
  final List<String> numbers;
  final int currentNumberIndex;

  NumberDisplay({required this.numbers, required this.currentNumberIndex});

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.topCenter,
      padding: EdgeInsets.only(top: 16, right: 16),
      child: Stack(
        children: [
          for (int numberIndex = 0; numberIndex < numbers.length; numberIndex++)
            Visibility(
              visible: currentNumberIndex == numberIndex,
              child: Container(
                transform: Matrix4.translationValues(0, -40, 0),
                child: Text(
                  numbers[numberIndex],
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