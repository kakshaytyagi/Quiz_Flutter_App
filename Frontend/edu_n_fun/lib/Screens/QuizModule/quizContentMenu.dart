import 'package:flutter/material.dart';

import 'AddQuiz/QuizDialog.dart';
import 'AddQuiz/QuizManager.dart';
import 'QuizApiLink/QuizApiClient.dart';
import 'QuizApiLink/TopPerformers.dart';
import 'QuizCustomTile.dart';

class QuizContentMenu extends StatefulWidget {
  final String title;
  final List<Color> listColors;

  QuizContentMenu({required this.title, required this.listColors});

  @override
  _QuizContentMenuState createState() => _QuizContentMenuState();
}

class _QuizContentMenuState extends State<QuizContentMenu> {
  final QuizManager quizManager = QuizManager();
  List<TopLeaderboardEntry> leaderboardData = [];
  TextEditingController searchController = TextEditingController();
  bool isSearchExpanded = false;


  @override
  void initState() {
    super.initState();
    fetchQuestionsAndTopPerformers();
  }

  Future<void> fetchQuestionsAndTopPerformers() async {
    await fetchQuestions();
    await fetchTopPerformers();
  }

  Future<void> fetchTopPerformers() async {
    try {
      final topPerformers = await QuizApiClient.fetchTopPerformers();
      setState(() {
        leaderboardData = convertToTopLeaderboardEntries(topPerformers);
      });
    } catch (e) {
      print('Error fetching top performers: $e');
    }
  }

  List<TopLeaderboardEntry> convertToTopLeaderboardEntries(List<TopPerformers> toppers) {
    return toppers.map((topper) {
      return TopLeaderboardEntry(
        name: topper.name,
        avatarUrl: topper.avatar,
        question: topper.question,
      );
    }).toList();
  }

  Future<void> fetchQuestions() async {
    try {
      await quizManager.fetchQuestionsFromApi();
      setState(() {});
    } catch (e) {
      print('Error fetching questions: $e');
    }
  }

  Future<void> handleRefresh() async {
    await fetchQuestionsAndTopPerformers();
  }

  bool showAllTopPerformers = false;

  List<Map<String, dynamic>> filterQuestions(String query) {
    return quizManager.quizList.where((question) {
      final title = question['title'].toLowerCase();
      final type = question['type'].toLowerCase();
      final by = question['by'].toLowerCase();

      // Check if the title, type, or by field contains the query
      return title.contains(query.toLowerCase()) ||
          type.contains(query.toLowerCase()) ||
          by.contains(query.toLowerCase());
    }).toList();
  }



  @override
  Widget build(BuildContext context) {
    List<TopLeaderboardEntry> displayedData = showAllTopPerformers
        ? leaderboardData
        : leaderboardData.take(10).toList();

    List<Map<String, dynamic>> filteredQuestions = filterQuestions(searchController.text);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          widget.title,
          style: TextStyle(
            fontSize: 28,
            fontWeight: FontWeight.bold,
            color: Colors.red, // Change text color to your desired color
            fontFamily: 'Comic Sans MS',
          ),
        ),
        backgroundColor: Color(0xFFf0e8f8),
        elevation: 0,
        centerTitle: true,
        actions: [
          IconButton(
            color: Colors.black,
            onPressed: () {
              setState(() {
                isSearchExpanded = !isSearchExpanded;
                if (!isSearchExpanded) {
                  searchController.clear();
                }
              });
            },
            icon: Icon(Icons.search),
          ),
        ],
      ),
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                colors: [Color(0xFFE0FFFF), Color(0xFF87CEEB)],
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
              ),
            ),
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Container(
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.vertical(bottom: Radius.circular(32.0)),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Row(
                      children: [
                        Text(
                          "Top Performers",
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                            color: Colors.black,
                          ),
                        ),
                        Spacer(),
                        IconButton(
                          onPressed: () {
                            showModalBottomSheet<void>(
                              context: context,
                              builder: (BuildContext context) {
                                return TopPerformersPopup(entries: leaderboardData);
                              },
                            );
                          },
                          icon: Icon(
                            Icons.leaderboard,
                            color: Colors.blue,
                            size: 26,
                          ),
                        ),
                      ],
                    ),
                  ),
                  SizedBox(
                    height: 160,
                    child: ListView.builder(
                      scrollDirection: Axis.horizontal,
                      itemCount: displayedData.length > 10 ? 10 : displayedData.length,
                      itemBuilder: (context, index) {
                        final entry = displayedData[index];
                        final ranking = (index + 1).toString();
                        return buildTopPerformer(entry, ranking);
                      },
                    ),
                  ),
                ],
              ),
            ),
          ),
          Positioned.fill(
            top: 210,
            child: Align(
              alignment: Alignment.bottomCenter,
              child: Container(
                height: 800,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.vertical(top: Radius.circular(32.0)),
                ),
                child: RefreshIndicator(
                  onRefresh: handleRefresh,
                  child: Stack(
                    children: [
                      ListView.builder(
                        itemCount: filteredQuestions.length,
                        itemBuilder: (context, index) {
                          final question = filteredQuestions[index];
                          return QuizCustomTile(
                            questionIndex: index ?? 0,
                            title: question['title'],
                            questionId: question['questionId'] as int? ?? 0,
                            logo: question['logo'],
                            type: question['type'],
                            attempted: question['attempted'],
                            by: question['by'],
                            refreshCallback: handleRefresh,
                          );
                        },
                      ),
                      Align(
                        alignment: Alignment.topRight,
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: PopupMenuButton<String>(
                            onSelected: (selectedItem) {
                              if (selectedItem == 'sort_by_submission_status') {
                                quizManager.quizList.sort((a, b) => b['attempted'].compareTo(a['attempted']));
                              } else if (selectedItem == 'sort_by_unattempted') {
                                quizManager.quizList.sort((a, b) => a['attempted'].compareTo(b['attempted']));
                              } else if (selectedItem == 'search_by_type') {
                                setState(() {
                                  isSearchExpanded = true;
                                });
                              } else if (selectedItem == 'search_by_name') {
                                setState(() {
                                  isSearchExpanded = true;
                                });
                              }
                              setState(() {});
                            },
                            itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
                              PopupMenuItem<String>(
                                value: 'sort_by_submission_status',
                                child: Text('attempted'),
                              ),
                              PopupMenuItem<String>(
                                value: 'sort_by_unattempted',
                                child: Text('unattempted'),
                              ),
                              PopupMenuItem<String>(
                                value: 'search_by_type',
                                child: Text('by Type'),
                              ),
                              PopupMenuItem<String>(
                                value: 'search_by_name',
                                child: Text('by Name'),
                              ),
                            ],
                            child: Image.asset(
                              'assets/icons/sort.png',
                              width: 40,
                              height: 30,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
          if (isSearchExpanded)
            Positioned(
              top: 80,
              right: 20,
              left: 20,
              child: AnimatedContainer(
                duration: Duration(milliseconds: 300),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(32),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withOpacity(0.2),
                      spreadRadius: 2,
                      blurRadius: 5,
                      offset: Offset(0, 2),
                    ),
                  ],
                ),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 16),
                  child: Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: searchController,
                          decoration: InputDecoration(
                            hintText: "Search...",
                            border: InputBorder.none,
                          ),
                          onChanged: (searchText) {
                            // When the search text changes, update the filtered questions.
                            setState(() {
                              filteredQuestions = filterQuestions(searchText);
                            });
                          },
                        ),
                      ),
                      IconButton(
                        onPressed: () {
                          setState(() {
                            isSearchExpanded = false;
                            searchController.clear();
                            filteredQuestions = quizManager.quizList; // Reset filtered questions when search is cleared
                          });
                        },
                        icon: Icon(Icons.close),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          Positioned(
            bottom: 20,
            right: 20,
            child: FloatingActionButton(
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return QuizDialog(
                      addQuiz: (String title, String logo, String type, bool attempted, String correctAnswer) {
                        quizManager.addQuizz(
                          title: title,
                          logo: logo,
                          type: type,
                          attempted: attempted,
                          correctAnswer: correctAnswer,
                        );
                        fetchQuestions();
                      },
                    );
                  },
                );
              },
              child: Icon(Icons.add),
            ),
          ),
        ],
      ),
    );
  }

  Widget buildTopPerformer(TopLeaderboardEntry entry, String ranking) {
    return Column(
      children: [
        GestureDetector(
          onTap: () {
            _showFloatingMessage();
          },
          child: Container(
            margin: EdgeInsets.all(8.0),
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.2),
                  spreadRadius: 2,
                  blurRadius: 5,
                  offset: Offset(0, 2),
                ),
              ],
            ),
            child: Stack(
              alignment: Alignment.topRight,
              children: [
                CircleAvatar(
                  radius: 45,
                  backgroundImage: AssetImage(entry.avatarUrl),
                  backgroundColor: Color(0xFFFFD700),
                ),
                Container(
                  padding: EdgeInsets.symmetric(vertical: 4, horizontal: 8),
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Text(
                    '#' + ranking,
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        Text(
          entry.name,
          style: TextStyle(
            fontFamily: 'Comic Sans MS',
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),
      ],
    );
  }

  void _showFloatingMessage() {
    final snackBar = SnackBar(
      content: Text("This section displays the top performers."),
      duration: Duration(seconds: 2),
    );

    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}

class TopPerformersPopup extends StatelessWidget {
  final List<TopLeaderboardEntry> entries;

  TopPerformersPopup({required this.entries});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(16.0),
      child: Column(
        children: [
          Text(
            "Top Performers",
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
          SizedBox(height: 16),
          Expanded(
            child: ListView.builder(
              itemCount: entries.length,
              itemBuilder: (context, index) {
                final entry = entries[index];
                return TopLeaderboardEntryListTile(entry: entry, rank: index + 1);
              },
            ),
          ),
        ],
      ),
    );
  }
}

class TopLeaderboardEntryListTile extends StatelessWidget {
  final TopLeaderboardEntry entry;
  final int rank;

  TopLeaderboardEntryListTile({required this.entry, required this.rank});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            width: 35,
            height: 35,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: Colors.orange,
            ),
            child: Center(
              child: Text(
                '$rank',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 16,
                  color: Colors.white,
                ),
              ),
            ),
          ),
          SizedBox(width: 12),
          CircleAvatar(
            radius: 30,
            backgroundImage: AssetImage(entry.avatarUrl),
          ),
        ],
      ),
      title: Text(
        entry.name,
        style: TextStyle(
          fontFamily: 'Comic Sans MS',
          fontSize: 20,
          fontWeight: FontWeight.bold,
          color: Colors.black,
        ),
      ),
      trailing: Text(
        entry.question.toString(),
        style: TextStyle(
          fontSize: 17,
          fontWeight: FontWeight.bold,
          color: Colors.green,
        ),
      ),
      contentPadding: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
    );
  }
}

class TopLeaderboardEntry {
  final String name;
  final int question;
  final String avatarUrl;

  TopLeaderboardEntry({required this.name, required this.question, required this.avatarUrl});
}
