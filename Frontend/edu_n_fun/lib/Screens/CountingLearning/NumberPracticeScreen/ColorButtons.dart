import 'package:flutter/material.dart';

import '../../../Widgets/SoundManager.dart';

class ColorButtons extends StatefulWidget {
  final bool isSpeakerSelected;
  final Function(Color) selectColor;
  final bool isColorSelected;
  final int currentNumberIndex;

  ColorButtons({
    required this.isSpeakerSelected,
    required this.selectColor,
    required this.isColorSelected,
    required this.currentNumberIndex,
  });

  @override
  _ColorButtonsState createState() => _ColorButtonsState();
}

class _ColorButtonsState extends State<ColorButtons>
    with SingleTickerProviderStateMixin {
  Color? selectedColor;
  bool isRaised = false;
  late AnimationController _animationController;
  final Duration animationDuration = Duration(milliseconds: 500);


  final List<String> numbers = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
  ];

  @override
  void initState() {
    super.initState();
    selectedColor = Colors.black;
    _animationController = AnimationController(
      vsync: this,
      duration: animationDuration,
    );
  }

  void handleColorSelection(Color color) {
    setState(() {
      if (!widget.isSpeakerSelected) {
        selectedColor = color;
        widget.selectColor(color);
      }
    });
  }


  void animateButton() {
    setState(() {
      isRaised = true;
    });

    _animationController.forward();

    Future.delayed(Duration(seconds: 1), () {
      setState(() {
        isRaised = false;
      });
      _animationController.reverse();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        SizedBox(width: 5),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Container(
            height: 30,
            width: 122,
            decoration: BoxDecoration(
              border: Border.all(color: Colors.white),
              borderRadius: BorderRadius.circular(15),
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.1),
                  blurRadius: 2,
                  spreadRadius: 1,
                ),
              ],
            ),
            child: Row(
              children: [
                InkWell(
                  onTap: () {
                    handleColorSelection(Colors.black);
                  },
                  borderRadius: BorderRadius.circular(20),
                  child: AnimatedContainer(
                    duration: animationDuration,
                    width: 40,
                    decoration: BoxDecoration(
                      color: selectedColor == Colors.black
                          ? Colors.black
                          : Colors.transparent,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.white, width: 2),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withOpacity(0.2),
                          blurRadius: 2,
                          spreadRadius: 1,
                        ),
                      ],
                    ),
                  ),
                ),
                InkWell(
                  onTap: () {
                    handleColorSelection(Colors.red);
                  },
                  borderRadius: BorderRadius.circular(20),
                  child: AnimatedContainer(
                    duration: animationDuration,
                    width: 40,
                    decoration: BoxDecoration(
                      color: selectedColor == Colors.red
                          ? Colors.red
                          : Colors.transparent,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.white, width: 2),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.red.withOpacity(0.2),
                          blurRadius: 2,
                          spreadRadius: 1,
                        ),
                      ],
                    ),
                  ),
                ),
                InkWell(
                  onTap: () {
                    handleColorSelection(Colors.blue);
                  },
                  borderRadius: BorderRadius.circular(20),
                  child: AnimatedContainer(
                    duration: animationDuration,
                    width: 40,
                    decoration: BoxDecoration(
                      color: selectedColor == Colors.blue
                          ? Colors.blue
                          : Colors.transparent,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.white, width: 2),
                      boxShadow: [
                        BoxShadow(
                          color: Colors.blue.withOpacity(0.2),
                          blurRadius: 2,
                          spreadRadius: 1,
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        InkWell(
          onTap: () {
            SoundManager.playSound(numbers[widget.currentNumberIndex]);
            animateButton();
          },
          borderRadius: BorderRadius.circular(15),
          child: AnimatedContainer(
            duration: animationDuration,
            height: 30,
            width: 30,
            decoration: BoxDecoration(

              color: isRaised ? Colors.black : Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.1),
                  blurRadius: 2,
                  spreadRadius: 1,
                ),
              ],
            ),
            child: Image.asset(
              'assets/icons/speaker.png',
              width: 20,
              height: 20,
              color: widget.isSpeakerSelected ? Colors.black : Colors.grey,
            ),
          ),
        ),
      ],
    );
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
}
