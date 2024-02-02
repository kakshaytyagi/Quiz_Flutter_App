import 'package:flutter/material.dart';

import '../../../Widgets/SoundManager.dart';

class NumberSounds extends StatefulWidget {
  final String optionTitle;
  NumberSounds({required this.optionTitle});

  @override
  State<NumberSounds> createState() => _NumberSounds();
}

List<Color> colorArray = [
  Color(0xFF303F9F), // Dark Blue
  Color(0xFF388E3C), // Dark Green
  Color(0xFFC2185B), // Dark Pink
  Color(0xFF512DA8), // Dark Purple
  Color(0xFFD32F2F), // Dark Red
  Color(0xFFE64A19), // Dark Orange
  Color(0xFF9E9D24), // Dark Yellow
  Color(0xFF1B5E20), // Darker Green
  Color(0xFF4A148C), // Darker Purple
  Color(0xFFAD1457), // Darker Pink
  Color(0xFF5D4037), // Dark Brown
  Color(0xFF006064), // Darker Cyan
  Color(0xFFC62828), // Darker Red
  Color(0xFF689F38), // Darker Green
  Color(0xFF8C0032), // Darker Maroon
  Color(0xFF1A237E), // Darker Indigo
  Color(0xFFB71C1C), // Darker Red
  Color(0xFFE65100), // Darker Orange
  Color(0xFF827717), // Darker Yellow
  Color(0xFF004D40), // Darker Teal
  Color(0xFF424242), // Darker Grey
  Color(0xFF455A64), // Darker Blue Grey
  Color(0xFF0D47A1), // Darkest Blue
];


class _NumberSounds extends State<NumberSounds> with SingleTickerProviderStateMixin {
  late AnimationController _animationController;
  final Duration _animationDuration = Duration(milliseconds: 200);
  final double _scaleFactor = 0.95;
  int _clickedIndex = -1; // Track the index of the currently clicked button

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: _animationDuration,
    );
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
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
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: GridView.builder(
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 2,
            crossAxisSpacing: 16.0,
            mainAxisSpacing: 16.0,
            childAspectRatio: 1.0,
          ),
          itemCount: 10,
          itemBuilder: (context, index) {
            int number = index % 10;
            String Number = number.toString();

            return GestureDetector(
              onTap: () async {
                setState(() {
                  _clickedIndex = index; // Update the clicked index
                });
                print('Button $Number clicked!');
                SoundManager.playSound(Number.toLowerCase());
                _animationController.reverse();
                Future.delayed(Duration(milliseconds: 100), () {
                  setState(() {
                    _clickedIndex = -1; // Reset the clicked index
                  });
                });
              },
              onTapDown: (_) {
                _animationController.forward();
              },
              child: AnimatedBuilder(
                animation: _animationController,
                builder: (context, child) {
                  final animationValue = _animationController.value;
                  final scaleFactor = 1.0 + (_scaleFactor - 1.0) * animationValue;
                  final isClicked = _clickedIndex == index;

                  return Transform.scale(
                    scale: isClicked ? scaleFactor : 1.0,
                    child: child,
                  );
                },
                child: Material(
                  elevation: 2,
                  borderRadius: BorderRadius.circular(8.0),
                  color: Colors.transparent,
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(8.0),
                    child: Container(
                      decoration: BoxDecoration(
                        gradient: LinearGradient(
                          begin: Alignment.topCenter,
                          end: Alignment.bottomCenter,
                          colors: [
                            colorArray[index], // Use the color from the colorArray
                            Color(0xFF00C2E3),
                          ],
                        ),
                        boxShadow: [
                          BoxShadow(
                            color: Colors.grey.withOpacity(0.2),
                            spreadRadius: 2,
                            blurRadius: 5,
                            offset: Offset(0, 3),
                          ),
                        ],
                      ),
                      child: Center(
                        child: Text(
                          Number,
                          style: TextStyle(
                            fontSize: 44,
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}
