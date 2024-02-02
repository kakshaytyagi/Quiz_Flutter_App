import 'package:flutter/material.dart';
import 'AddQuizPage.dart';
import 'QuizManager.dart';

class QuizDialog extends StatefulWidget {
  final Function addQuiz;

  QuizDialog({required this.addQuiz});

  @override
  _QuizDialogState createState() => _QuizDialogState();
}

class _QuizDialogState extends State<QuizDialog> {
  String selectedCategory = 'Puzzle'; // Default category

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text(
        'Add New Quiz',
        style: TextStyle(
          fontWeight: FontWeight.bold,
          fontSize: 21,
          color: Colors.blue, // Playful title color
        ),
      ),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Select Quiz Category:',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 18, // Larger font size
            ),
          ),
          SizedBox(height: 10),
          DropdownButton<String>(
            value: selectedCategory,
            onChanged: (String? newValue) {
              setState(() {
                selectedCategory = newValue!;
              });
            },
            items: <String>[
              'Puzzle',
              'General Knowledge',
              'Maths',
              'Aptitude',
              'Science',
              'Technical',
              'Others',
            ].map<DropdownMenuItem<String>>((String value) {
              return DropdownMenuItem<String>(
                value: value,
                child: Text(
                  value,
                  style: TextStyle(fontSize: 16), // Smaller font size
                ),
              );
            }).toList(),

          ),
          SizedBox(height: 4),
        ],
      ),
      actions: [
        TextButton(
          onPressed: () {
            // Close the dialog
            Navigator.pop(context);

            // Show the input page
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => AddQuizPage(
                  selectedCategory: selectedCategory,
                  addQuiz: widget.addQuiz,
                ),
              ),
            );
          },
          child: Text(
            'Next',
            style: TextStyle(
              color: Colors.blue, // Playful button color
              fontWeight: FontWeight.bold,
              fontSize: 16, // Larger font size
            ),
          ),
        ),
        TextButton(
          onPressed: () {
            // Close the dialog
            Navigator.pop(context);
          },
          child: Text(
            'Cancel',
            style: TextStyle(
              color: Colors.grey, // Playful button color
              fontSize: 16, // Larger font size
            ),
          ),
        ),
      ],
    );
  }
}
