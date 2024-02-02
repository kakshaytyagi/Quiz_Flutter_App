import 'package:flutter/material.dart';

import 'DrawingPainter.dart';
import 'ColorButtons.dart';


class Whiteboard extends StatefulWidget {
  final int currentAlphabetIndex;


  Whiteboard({required this.currentAlphabetIndex});

  @override
  _WhiteboardState createState() => _WhiteboardState();
}


class _WhiteboardState extends State<Whiteboard> {
  bool isSpeakerSelected = false;
  bool isColorSelected = true;

  List<Offset> points = [];
  Color selectedColor = Colors.black;
  double strokeWidth = 15.0;


  void sColor(Color color) {
    setState(() {
      isSpeakerSelected = false;
      selectedColor = color;
    });
  }


  void clearCanvas() {
    setState(() {
      points.clear();
    });
  }


  @override
  void didUpdateWidget(Whiteboard oldWidget) {
    if (widget.currentAlphabetIndex != oldWidget.currentAlphabetIndex) {
      clearCanvas();
    }
    super.didUpdateWidget(oldWidget);
  }

  @override
  Widget build(BuildContext context) {

    return Container(
      height: 475.0,
      width: 380.0,
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey[300]!),
        borderRadius: BorderRadius.circular(10.0),
        color: Colors.white,
      ),
      child: Stack(
        children: [
          GestureDetector(
            onPanUpdate: (details) {
              RenderBox object = context.findRenderObject() as RenderBox;
              Offset localPosition = object.globalToLocal(details.globalPosition);

              // Check if the touch position is within the whiteboard area
              if (localPosition.dx >= 0 &&
                  localPosition.dx <= 380 &&
                  localPosition.dy >= 0 &&
                  localPosition.dy <= 475) {
                setState(() {
                  points = List.from(points)..add(localPosition);
                });
              }
            },
            onPanEnd: (details) {
              setState(() {
                points.add(Offset(-1, -1)); // Add sentinel value to represent end of drawing
              });
            },
            child: Container(
              width: double.infinity,
              height: double.infinity,
              color: Colors.white,
              child: CustomPaint(
                painter: DrawingPainter(points, selectedColor, strokeWidth),
              ),
            ),
          ),
          ClearButton(
            onPressed: clearCanvas,
          ),
          Positioned(
            top: 10,
            right: 10,
            child: ColorButtons(
              isSpeakerSelected: isSpeakerSelected,
              isColorSelected: isColorSelected,
              selectColor: sColor,
              currentAlphabetIndex: widget.currentAlphabetIndex,
            ),
          ),
        ],
      ),
    );
  }
}



class ClearButton extends StatelessWidget {
  final VoidCallback onPressed;

  const ClearButton({required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return Positioned(
      top: 10,
      left: 10,
      child: GestureDetector(
        onTap: onPressed,
        child: Container(
          height: 30,
          width: 30,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            color: Colors.red,
          ),
          child: Icon(
            Icons.clear,
            color: Colors.white,
          ),
        ),
      ),
    );
  }
}

