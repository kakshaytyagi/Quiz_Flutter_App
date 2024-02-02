import 'package:flutter/material.dart';


class BackgroundGradient extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [
            Color(0xFF08171c),
            Colors.black,
            Color(0xFF08171c),
            Color(0xFF08171c),
          ],
        ),
      ),
    );
  }
}