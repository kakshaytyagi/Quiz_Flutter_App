import 'package:flutter/material.dart';


class Quiz {
  final String title;
  final String logo;
  final String type;
  final bool attempted;
  final String by;

  Quiz({
    required this.title,
    required this.logo,
    required this.type,
    required this.attempted,
    required this.by,
  });
}
