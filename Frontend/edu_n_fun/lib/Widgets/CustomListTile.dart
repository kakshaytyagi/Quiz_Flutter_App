import 'package:flutter/material.dart';

class CustomListTile extends StatelessWidget {
  final Color tileColor;
  final String title;
  final String imagePath;
  final VoidCallback onTap;
  final Key? key;

  CustomListTile({
    required this.tileColor,
    required this.title,
    required this.imagePath,
    required this.onTap,
    this.key,
  });

  Color _getTextColor(Color backgroundColor) {
    final double luminance = (0.299 * backgroundColor.red +
        0.587 * backgroundColor.green +
        0.114 * backgroundColor.blue) /
        255;
    return luminance > 0.5 ? Colors.black : Colors.white;
  }

  @override
  Widget build(BuildContext context) {
    final textColor = _getTextColor(tileColor);

    return GestureDetector(
      onTap: onTap,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Transform(
          transform: Matrix4.identity()
            ..setEntry(3, 2, 0.001) // Add a perspective to the transform
            ..rotateY(0), // Adjust the rotation angle as needed
          child: AspectRatio(
            aspectRatio: 2 / 1, // Adjust the aspect ratio as needed
            child: Container(
              padding: EdgeInsets.symmetric(horizontal: 8.0),
              // Add horizontal padding
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  colors: [
                    tileColor.withOpacity(0.8),
                    tileColor.withOpacity(0.9),
                    tileColor,
                  ],
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                ),
                boxShadow: [
                  BoxShadow(
                    color: tileColor.withOpacity(0.5),
                    blurRadius: 4,
                    offset: Offset(0, 2),
                  ),
                ],
                borderRadius: BorderRadius.circular(12),
              ),
              child: Row(
                children: [
                  Expanded(
                    flex: 2,
                    child: ListTile(
                      tileColor: Colors.transparent,
                      title: Text(
                        title,
                        style: TextStyle(
                          fontSize: 30,
                          fontWeight: FontWeight.w400,
                          color: Colors.white,
                        ),
                      ),
                      onTap: onTap,
                    ),
                  ),
                  Expanded(
                    flex: 2,
                    child: Image.asset(
                      imagePath,
                      fit: BoxFit.cover,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
