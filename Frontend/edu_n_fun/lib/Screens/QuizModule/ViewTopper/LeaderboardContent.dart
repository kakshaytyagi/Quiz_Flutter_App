import 'package:flutter/material.dart';
import '../QuizApiLink/Question.dart';
import '../QuizApiLink/QuestionTopper.dart';
import '../QuizApiLink/QuizApiClient.dart';
import 'leaderboardEntryWidget.dart';

class LeaderboardContent extends StatefulWidget {
  final int? questionId; // Pass questionId from the previous screen

  LeaderboardContent({Key? key, this.questionId}) : super(key: key);

  @override
  _LeaderboardContentState createState() => _LeaderboardContentState();
}

class _LeaderboardContentState extends State<LeaderboardContent> {
  List<LeaderboardEntry> leaderboardData = [];

  @override
  void initState() {
    super.initState();
    fetchQuestionTopper(widget.questionId);
  }

  Future<void> fetchQuestionTopper(int? questionId) async {
    try {
      final toppers = await QuizApiClient.fetchQuestionToppers(questionId);
      setState(() {
        leaderboardData = convertToLeaderboardEntries(toppers);
      });
    } catch (e) {
      print('Error: $e');
    }
  }

  List<LeaderboardEntry> convertToLeaderboardEntries(List<QuestionTopper> toppers) {
    return toppers.map((topper) {
      return LeaderboardEntry(
        name: topper.name,
        avatarUrl: topper.avatar,
        time: topper.submissionTime,
      );
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    final List<LeaderboardEntry> topThree = leaderboardData.take(3).toList();
    final List<LeaderboardEntry> restEntries = leaderboardData.skip(3).toList();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        buildTopPerformersHeader(),
        Expanded(
          child: ClipRRect(
            borderRadius: BorderRadius.vertical(top: Radius.circular(50)),
            child: Container(
              decoration: BoxDecoration(
                color: Colors.white,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  TopPerformersWidget(topPerformers: topThree),
                  buildRestOfEntriesList(restEntries),
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }

  Future<Question> fetchQuestionByIndex() async {
    try {
      final questions = await QuizApiClient.searchQuestions();
      if (widget.questionId != null && widget.questionId! >= 0 && widget.questionId! < questions.length) {
        return questions[widget.questionId!];
      } else {
        throw Exception('Question index is out of bounds.');
      }
    } catch (e) {
      throw e;
    }
  }


  Future<Map<String, String?>> Questionfetching() async {
    try {
      final questions = await fetchQuestionByIndex();
      String q = questions.question;
      int ans = questions.answer;
      String? answer;

      if (ans == 1) {
        answer = questions.option1;
      } else if (ans == 2) {
        answer = questions.option2;
      } else if (ans == 3) {
        answer = questions.option3;
      } else if (ans == 4) {
        answer = questions.option4;
      } else {
        answer = (questions.statement1 ?? "") + ", " + (questions.statement2 ?? "") + ", " + (questions.statement3 ?? "");
      }

      return {'question': q, 'answer': answer};
    } catch (e) {
      throw 'Failed to fetch question: $e';
    }
  }

  Widget buildTopPerformersHeader() {
    return FutureBuilder<Map<String, String?>>(
      future: Questionfetching(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          // Show a loading indicator while fetching data.
          return Center(
            child: CircularProgressIndicator(),
          );
        }
        if (snapshot.hasError) {
          // Handle the error state.
          return Center(
            child: Text(
              'Error: ${snapshot.error}',
              style: TextStyle(
                fontSize: 18,
                color: Colors.red,
              ),
            ),
          );
        }
        final Map<String, String?>? data = snapshot.data;
        if (data == null) {
          // Handle the case where the data is null.
          return Center(
            child: Text(
              'Data is null',
              style: TextStyle(
                fontSize: 18,
                color: Colors.red,
              ),
            ),
          );
        }

        final String question = data['question'] ?? '';
        final String answer = data['answer'] ?? '';

        return Container(
          padding: EdgeInsets.all(16.0),
          decoration: BoxDecoration(
            color: Color(0xFFF6D77E),
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(15),
              topRight: Radius.circular(15),
            ),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Top Performers',
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
              SizedBox(height: 16), // Add spacing between titles
              Card(
                elevation: 3,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(15),
                ),
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Question:',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: Colors.blue,
                        ),
                      ),
                      SizedBox(height: 8),
                      Text(
                        question,
                        style: TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                        ),
                        maxLines: 3,
                      ),
                      SizedBox(height: 16),
                      Text(
                        'Answer:',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: Colors.green,
                        ),
                      ),
                      SizedBox(height: 8),
                      Text(
                        answer,
                        style: TextStyle(
                          fontSize: 18,
                          color: Colors.black,
                        ),
                        maxLines: 3,
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget buildRestOfEntriesList(List<LeaderboardEntry> restEntries) {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.all(17.0),
        child: ListView.builder(
          itemCount: restEntries.length,
          itemBuilder: (context, index) {
            final entry = restEntries[index];
            final rank = index + 4; // Start numbering from 4, assuming the top 3 are already displayed
            return LeaderboardEntryListTile(entry: entry, rank: rank);
          },
        ),
      ),
    );
  }
}

class TopPerformersWidget extends StatelessWidget {
  final List<LeaderboardEntry> topPerformers;

  TopPerformersWidget({required this.topPerformers});

  @override
  Widget build(BuildContext context) {
    if (topPerformers.length >= 3) {
      return Container(
        color: Colors.white,
        padding: EdgeInsets.symmetric(vertical: 16.0),
        child: Stack(
          alignment: Alignment.center,
          children: [
            Positioned(
              left: 30,
              child: Column(
                children: [
                  CircleAvatar(
                    radius: 36,
                    backgroundImage: AssetImage(topPerformers[1].avatarUrl),
                    backgroundColor: Color(0xFF42A5F5),
                  ),
                  SizedBox(height: 8),
                  Text(
                    topPerformers[1].name,
                    style: TextStyle(
                      fontFamily: 'Comic Sans MS',
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.black,
                    ),
                  ),
                  SizedBox(height: 4),
                  Text(
                    topPerformers[1].time,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.green,
                    ),
                  ),
                ],
              ),
            ),
            Column(
              children: [
                CircleAvatar(
                  radius: 47,
                  backgroundImage: AssetImage(topPerformers[0].avatarUrl),
                  backgroundColor: Color(0xFF42A5F5),
                ),
                SizedBox(height: 8),
                Text(
                  topPerformers[0].name,
                  style: TextStyle(
                    fontFamily: 'Comic Sans MS',
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 4),
                Text(
                  topPerformers[0].time,
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.green,
                  ),
                ),
              ],
            ),
            Positioned(
              right: 30,
              child: Column(
                children: [
                  CircleAvatar(
                    radius: 36,
                    backgroundImage: AssetImage(topPerformers[2].avatarUrl),
                    backgroundColor: Color(0xFF42A5F5),
                  ),
                  SizedBox(height: 8),
                  Text(
                    topPerformers[2].name,
                    style: TextStyle(
                      fontFamily: 'Comic Sans MS',
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.black,
                    ),
                  ),
                  SizedBox(height: 4),
                  Text(
                    topPerformers[2].time,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.green,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      );
    } else if (topPerformers.isNotEmpty) {
      return Container(
        color: Colors.white,
        padding: EdgeInsets.symmetric(vertical: 16.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: topPerformers.map((performer) {
            return Column(
              children: [
                CircleAvatar(
                  radius: 36,
                  backgroundImage: AssetImage(performer.avatarUrl),
                  backgroundColor: Color(0xFF42A5F5),
                ),
                SizedBox(height: 8),
                Text(
                  performer.name,
                  style: TextStyle(
                    fontFamily: 'Comic Sans MS',
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 4),
                Text(
                  performer.time,
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.green,
                  ),
                ),
              ],
            );
          }).toList(),
        ),
      );
    } else {
      return Center(
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.symmetric(vertical: 16.0),
          child: Column(
            children: [
              Image.asset(
                'assets/icons/no_performers.gif', // Replace with your image asset path
                height: 120, // Adjust the height as needed
              ),
              SizedBox(height: 16),
              Text(
                "Oops!",
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),
              SizedBox(height: 8),
              Text(
                'No top performers to display',
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.black,
                ),
              ),
            ],
          ),
        ),
      );
    }

  }
}

