import 'package:flutter/material.dart';

class NavigationButtons extends StatelessWidget {
  final VoidCallback previousImage;
  final VoidCallback nextImage;
  final bool showPrevious;
  final bool showNext;

  const NavigationButtons({
    required this.previousImage,
    required this.nextImage,
    this.showPrevious = true,
    this.showNext = true,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.vertical(
          top: Radius.circular(16),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 16),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            if (showPrevious)
              SizedBox(
                width: 170,
                child: ElevatedButton(
                  onPressed: previousImage,
                  style: ElevatedButton.styleFrom(
                    primary: Colors.blue,
                    shape: CircleBorder(),
                    padding: EdgeInsets.all(16),
                    elevation: 4,
                  ),
                  child: Icon(
                    Icons.arrow_back,
                    color: Colors.white,
                  ),
                ),
              ),
            if (showNext)
              SizedBox(
                width: 170,
                child: ElevatedButton(
                  onPressed: nextImage,
                  style: ElevatedButton.styleFrom(
                    primary: Colors.blue,
                    shape: CircleBorder(),
                    padding: EdgeInsets.all(16),
                    elevation: 4,
                  ),
                  child: Icon(
                    Icons.arrow_forward,
                    color: Colors.white,
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
