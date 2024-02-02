import 'package:flutter/material.dart';

class ColorButton extends StatefulWidget {
  final Color color;
  final bool isEraserSelected;
  final String selectedAlphabet;
  final Function(Color) onTap;

  ColorButton({
    required this.color,
    required this.isEraserSelected,
    required this.selectedAlphabet,
    required this.onTap,
  });

  @override
  _ColorButtonState createState() => _ColorButtonState();
}

class _ColorButtonState extends State<ColorButton> {
  bool isSelected = false;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        setState(() {
          isSelected = !isSelected;
          if (isSelected) {
            widget.onTap(widget.color);
          }
        });
      },
      child: Container(
        height: 30,
        width: 30,
        decoration: BoxDecoration(
          shape: BoxShape.circle,
          color: isSelected ? widget.color.withOpacity(0.8) : widget.color,
          border: Border.all(
            color: isSelected ? Colors.white : Colors.transparent,
            width: isSelected ? 2.0 : 0.0,
          ),
        ),
      ),
    );
  }
}
