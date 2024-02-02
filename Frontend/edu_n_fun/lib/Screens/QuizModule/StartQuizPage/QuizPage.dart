import 'package:flutter/material.dart';
import 'dart:async';

import '../AddQuiz/QuizManager.dart';
import '../QuizApiLink/Question.dart';
import '../QuizApiLink/QuizApiClient.dart';
import '../quizContentMenu.dart';

class QuizPage extends StatefulWidget {
  final String quizTitle;
  final int questionIndex;
  final VoidCallback refreshCallback;

  QuizPage({
    required this.quizTitle,
    required this.questionIndex,
    required this.refreshCallback,
  });

  @override
  _QuizPageState createState() => _QuizPageState();
}

class _QuizPageState extends State<QuizPage> {
  final QuizManager quizManager = QuizManager();
  late DateTime startTime;
  String submissionTime = '00:00:0000';
  int? selectedOption;
  late Timer? stopwatchTimer;
  int elapsedTime = 0;
  bool isTimerRunning = false;
  int countdown = 60;
  late Future<Question> questionFuture;
  TextEditingController textEditingController = TextEditingController();


  @override
  void initState() {
    super.initState();
    startTime = DateTime.now();
    startTimer();
    startCountdownTimer();
    questionFuture = fetchQuestionByIndex();
  }

  @override
  void dispose() {
    textEditingController.dispose(); // Dispose of the TextEditingController
    stopwatchTimer?.cancel();
    super.dispose();
  }

  Future<Question> fetchQuestionByIndex() async {
    try {
      final questions = await QuizApiClient.searchQuestions();
      if (widget.questionIndex >= 0 && widget.questionIndex < questions.length) {
        return questions[widget.questionIndex];
      } else {
        throw Exception('Question index is out of bounds.');
      }
    } catch (e) {
      throw e;
    }
  }

  void startTimer() {
    if (!isTimerRunning) {
      isTimerRunning = true;
      stopwatchTimer = Timer.periodic(Duration(milliseconds: 100), (timer) {
        final now = DateTime.now();
        final difference = now.difference(startTime);
        setState(() {
          elapsedTime = difference.inMilliseconds;
        });
      });
    }
  }

  void _handleOptionSelected(int optionIndex) {
    setState(() {
      selectedOption = optionIndex;
    });
  }

  void startCountdownTimer() {
    Timer.periodic(Duration(seconds: 1), (timer) {
      if (mounted && countdown > 0) {
        setState(() {
          countdown--;
        });
        if (countdown == 0) {
          timer.cancel();
        }
      } else {
        timer.cancel();
      }
    });
  }

  String formatDuration(int milliseconds) {
    int minutes = (milliseconds / 60000).floor();
    int seconds = ((milliseconds % 60000) / 1000).floor();
    int remainingMilliseconds = milliseconds % 1000;
    return "${minutes.toString().padLeft(2, '0')}:${(seconds % 60).toString().padLeft(2, '0')}.${(remainingMilliseconds).toString().padLeft(3, '0')}";
  }


  void _handleSubmitButtonPressed() async {
    if (selectedOption == null) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Please select an option before submitting.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.refreshCallback(); // Call the refreshCallback here
                },
              ),
            ],
          );
        },
      );
      return; // Exit the function
    }

    if (stopwatchTimer != null && stopwatchTimer!.isActive) {
      stopwatchTimer!.cancel();
      submissionTime = formatDuration(elapsedTime);
    }

    final selectedQuestion = await questionFuture;
    final isCorrect = selectedOption == selectedQuestion.answer;

    final success = await QuizApiClient.submitAnswer(
      selectedQuestion.questionId,
      submissionTime,
      isCorrect ? 1 : 0,
    );

    if (success) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Thank You!'),
            content: Text('Your quiz has been submitted.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.refreshCallback(); // Call the refreshCallback here
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    } else {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Failed to submit your answer. Please try again later.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: WillPopScope(
        onWillPop: _onWillPop,
        child: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              colors: [Color(0xFF42A5F5), Color(0xFF00E676)],
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
            ),
          ),
          child: ListView(
            children: [
              _buildTimerDisplay(),
              SizedBox(height: 3),
              _buildQuizImage(),
              SizedBox(height: 5),
              _buildQuestionDisplay(),
              SizedBox(height: 30),
              _buildSubmitButton(),
              SizedBox(height: MediaQuery.of(context).viewInsets.bottom), // Ensure the content extends to the keyboard
            ],
          ),
        ),
      ),
    );
  }


  Widget _buildTimerDisplay() {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 30, horizontal: 16),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Image.asset(
            'assets/icons/clock.png',
            width: 48,
            height: 48,
          ),
          SizedBox(width: 10),
          Text(
            formatDuration(elapsedTime),
            style: TextStyle(
              fontSize: 24,
              color: Colors.white,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildQuizImage() {
    return SizedBox(
      height: 240,
      child: Container(
        decoration: BoxDecoration(
          border: Border.all(color: Colors.white, width: 8),
        ),
        child: Image.asset(
          'assets/icons/vLearning.png',
          width: 340,
          height: 240,
        ),
      ),
    );
  }

  Widget _buildQuestionDisplay() {
    return SingleChildScrollView(
      child: Column(
        children: [
          Text(
            'Question:',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
          SizedBox(height: 10),
          Container(
            width: 340,
            child: Column(
              children: [
                Container(
                  height: 100,
                  child: FutureBuilder<Question>(
                    future: questionFuture,
                    builder: (context, snapshot) {
                      if (snapshot.connectionState == ConnectionState.waiting) {
                        return Center(child: CircularProgressIndicator());
                      } else if (snapshot.hasError) {
                        return Center(
                          child: Text("Error: ${snapshot.error}"),
                        );
                      } else if (snapshot.hasData) {
                        final selectedQuestion = snapshot.data!;
                        return Text(
                          selectedQuestion.question,
                          style: TextStyle(
                            fontSize: 20,
                            color: Colors.white,
                          ),
                        );
                      } else {
                        return Center(
                          child: Text("No question available."),
                        );
                      }
                    },
                  ),
                ),
                SizedBox(height: 5),
                _buildOptionsField(),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildMultipleChoiceOptions(Question question) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            _buildOptionButton(question.option1, 1),
            SizedBox(width: 30),
            _buildOptionButton(question.option2, 2),
          ],
        ),
        SizedBox(height: 30),
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            _buildOptionButton(question.option3, 3),
            SizedBox(width: 30),
            _buildOptionButton(question.option4, 4),
          ],
        ),
      ],
    );
  }

  Widget _buildOptionButton(String? text, int optionIndex) {
    return SizedBox(
      width: 155,
      height: 75,
      child: ElevatedButton(
        onPressed: () {
          _handleOptionSelected(optionIndex);
        },
        style: ElevatedButton.styleFrom(
          primary: selectedOption == optionIndex
              ? Colors.yellow
              : Color(0xFF4CAF50),
          padding: EdgeInsets.symmetric(vertical: 20, horizontal: 39),
          textStyle: TextStyle(fontSize: 21),
        ),
        child: Text(
          '$text',
          style: TextStyle(
            fontSize: 20,
            color: Colors.white,
          ),
        ),
      ),
    );
  }

  Future<bool> _onWillPop() async {
    if (selectedOption == null) {
      // If no option is selected, show the confirmation dialog
      final shouldExit = await showDialog<bool>(
        context: context,
        builder: (context) => AlertDialog(
          title: Text('Are you sure?'),
          content: Text('Do you want to exit the quiz without submitting your answer?'),
          actions: <Widget>[
            TextButton(
              onPressed: () async {
                // Save the submission time and status as 0
                final selectedQuestion = await questionFuture;
                await QuizApiClient.submitAnswer(
                  selectedQuestion.questionId,
                  formatDuration(elapsedTime),
                  0, // Submit with status 0 (wrong answer)
                );

                Navigator.of(context).pop(true); // Confirm
                widget.refreshCallback(); // Call the refreshCallback here
                Navigator.of(context).pop(); // Go back
              },
              child: Text('Yes'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(false); // Cancel
              },
              child: Text('No'),
            ),
          ],
        ),
      );

      return shouldExit ?? false;
    } else {
      final selectedQuestion = await questionFuture;
      final success = await QuizApiClient.submitAnswer(
        selectedQuestion.questionId,
        submissionTime,
        0,
      );

      if (success) {
        Navigator.of(context).pop(true); // Navigate back
      } else {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return AlertDialog(
              title: Text('Error'),
              content: Text('Failed to submit your answer. Please try again later.'),
              actions: <Widget>[
                TextButton(
                  child: Text('OK'),
                  onPressed: () {
                    Navigator.of(context).pop(false); // Stay on the current page
                  },
                ),
              ],
            );
          },
        );
      }
      return false; // Return false to indicate that we handled the back button press.
    }
  }

  Widget _buildSubmitButton() {
    return SizedBox(
      width: 170,
      height: 90,
      child: ElevatedButton(
        onPressed: () {
          if (selectedOption != null) {
            _handleSubmitButtonPressed();
          } else {
            _handleSubmitButton(textEditingController.text);
          }
        },
        style: ElevatedButton.styleFrom(
          primary: Color(0xFF42A5F5),
          padding: EdgeInsets.symmetric(vertical: 20, horizontal: 39),
          textStyle: TextStyle(fontSize: 21),
        ),
        child: Text(
          'Submit',
          style: TextStyle(
            fontSize: 20,
            color: Colors.white,
          ),
        ),
      ),
    );
  }

  Widget _buildOptionsField() {
    return FutureBuilder<Question>(
      future: questionFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(
            child: Text("Error: ${snapshot.error}"),
          );
        } else if (snapshot.hasData) {
          final selectedQuestion = snapshot.data!;
          return selectedQuestion.isOption == 1
              ? TextFormField(
            controller: textEditingController, // Use a TextEditingController to capture user input
            decoration: InputDecoration(
              labelText: 'Enter your answer',
              labelStyle: TextStyle(
                color: Colors.white,
              ),
              enabledBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Colors.white,
                ),
              ),
              focusedBorder: OutlineInputBorder(
                borderSide: BorderSide(
                  color: Colors.yellow,
                ),
              ),
            ),
            onChanged: (value) {
            },
            onFieldSubmitted: (_) {
              // _handleSubmitButton(textEditingController.text);
              _buildSubmitButton();
            },
          )
              : _buildMultipleChoiceOptions(selectedQuestion);
        } else {
          return Center(
            child: Text("No question available."),
          );
        }
      },
    );
  }


  void _handleSubmitButton(String value) async {
    if (value.isEmpty) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Please enter an answer before submitting.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.refreshCallback(); // Call the refreshCallback here
                },
              ),
            ],
          );
        },
      );
      return; // Exit the function
    }

    if (stopwatchTimer != null && stopwatchTimer!.isActive) {
      stopwatchTimer!.cancel();
      submissionTime = formatDuration(elapsedTime);
    }

    final selectedQuestion = await questionFuture;
    final isCorrect = isStatementCorrect(value, selectedQuestion);

    final success = await QuizApiClient.submitAnswer(
      selectedQuestion.questionId,
      submissionTime,
      isCorrect ? 1 : 0,
    );

    if (success) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Thank You!'),
            content: Text('Your quiz has been submitted.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                  widget.refreshCallback(); // Call the refreshCallback here
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    } else {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Error'),
            content: Text('Failed to submit your answer. Please try again later.'),
            actions: <Widget>[
              TextButton(
                child: Text('OK'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    }
  }

  bool isStatementCorrect(String answer, Question question) {
    if (question != null) {
      if (answer == question.statement1 ||
          answer == question.statement2 ||
          answer == question.statement3) {
        return true;
      }
    }
    return false;
  }

}
