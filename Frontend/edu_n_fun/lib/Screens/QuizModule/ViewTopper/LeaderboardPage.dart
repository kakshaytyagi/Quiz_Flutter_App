import 'package:flutter/material.dart';

import '../QuizApiLink/Question.dart';
import '../QuizApiLink/QuizApiClient.dart';
import 'LeaderboardContent.dart';


class LeaderboardPage extends StatelessWidget {
  final String quizTitle;
  final bool attempted;
  final int questionIndex;

  LeaderboardPage({required this.quizTitle, required this.attempted, required this.questionIndex});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Color(0xFFF6D77E),
      appBar: buildAppBar(context),
      body: LeaderboardContent(questionId: questionIndex),
    );
  }

  AppBar buildAppBar(BuildContext context) {
    return AppBar(
      elevation: 0,
      backgroundColor: Colors.transparent,
      leading: IconButton(
        onPressed: () {
          Navigator.pop(context); // Handle the back button action
        },
        icon: Icon(Icons.arrow_back, color: Colors.black),
      ),
      actions: [
        Padding(
          padding: const EdgeInsets.only(right: 20.0),
          child: CircleAvatar(
            backgroundImage: AssetImage('assets/avatar/Avatar3.png'),
          ),
        ),
      ],
    );
  }
}