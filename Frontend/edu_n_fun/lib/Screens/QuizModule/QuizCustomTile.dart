import 'package:flutter/material.dart';
import 'StartQuizPage/QuizPage.dart';
import 'ViewTopper/LeaderboardPage.dart';

class QuizCustomTile extends StatelessWidget {
  final String title;
  final String logo;
  final String type;
  final String by;
  final int attempted;
  final int questionIndex;
  final int questionId;
  final VoidCallback refreshCallback; // Callback function

  QuizCustomTile({
    required this.title,
    required this.logo,
    required this.type,
    required this.by,
    required this.attempted,
    required this.questionId,
    required this.questionIndex,
    required this.refreshCallback,
  });

  String truncateString(String text) {
    return text.length <= 7 ? text : text.substring(0, 7) + "...";
  }

  Future<void> doRefresh(BuildContext context) async {
    refreshCallback();
  }

  @override
  Widget build(BuildContext context) {
    bool hasAttempted = attempted == 0 ? true : false;
    return Card(
      elevation: 4,
      color: Color(0xFFf0e8f8),
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
      ),
      child: Container(
        height: 150, // Adjust the height as needed
        padding: EdgeInsets.all(16),
        child: Row(
          children: [
            Row(
              children: [
                Image.asset(
                  logo,
                  width: 60,
                  height: 60,
                ),
                SizedBox(width: 16),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      truncateString(title),
                      style: TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 8),
                    Flexible(
                      child: Text(
                        'Type: ${truncateString(type)}',
                        style: TextStyle(
                          fontSize: 15,
                          color: Colors.grey,
                        ),
                      ),
                    ),
                    SizedBox(height: 4),
                    Flexible(
                      child: Text(
                        'By: ${truncateString(by)}',
                        style: TextStyle(
                          fontSize: 16,
                          color: Colors.grey,
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
            SizedBox(width: 34), // Add some space between the text and button
            SizedBox(
              width: 128,
              child: ElevatedButton(
                onPressed: () {
                  if (!hasAttempted) {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => LeaderboardPage(quizTitle: title, attempted: true, questionIndex: questionId),
                      ),
                    );
                  } else {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => QuizPage(quizTitle: title, questionIndex: questionIndex, refreshCallback: () => doRefresh(context)),
                      ),
                    );
                  }
                },
                style: ElevatedButton.styleFrom(
                  primary: hasAttempted ? Colors.green : Colors.grey,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10.0),
                  ),
                ),
                child: Text(
                  hasAttempted ? 'Start Quiz' : 'View Topper' ,
                  style: TextStyle(
                    fontFamily: 'Comic Sans MS',
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
