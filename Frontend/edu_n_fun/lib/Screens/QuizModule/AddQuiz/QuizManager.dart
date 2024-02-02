import '../QuizApiLink/Question.dart';
import '../QuizApiLink/QuizApiClient.dart';
import 'Quiz.dart';

class QuizManager {
  List<Map<String, dynamic>> quizList = [];

  QuizManager() {
    fetchQuestionsFromApi();
  }

  Future<void> fetchQuestionsFromApi() async {
    try {
      List<Question> fetchedQuestions = await QuizApiClient.searchAllQuestions();
      quizList = fetchedQuestions.map((question) {
        return {
          'title': question.question,
          'questionId': question.questionId,
          'attempted': question.status,
          'by': question.createdBy,
          'logo': 'assets/icons/picturelearning.png',
          'type': question.category == 'GENERAL_KNOWLEDGE'? 'GK' : question.category ,
        };
      }).toList();
    } catch (e) {
      print('Error fetching questions: $e');
    }
  }

  void addQuizz({
  required String title,
  required String logo,
  required String type,
  required bool attempted,
  required String correctAnswer,
}) {
  final newQuiz = {
    'title': title,
    'logo': logo,
    'type': type,
    'attempted': attempted,
    'correctAnswer': correctAnswer,
  };

  // Assuming you have a quizList where you want to add the new quiz
  quizList.insert(0, newQuiz);
}



  Future<void> addQuiz(Question question) async {
    try {
      await QuizApiClient.addQuiz(question);
    } catch (e) {
      print('Error adding question: $e');
    }
  }
}





// List<Map<String, dynamic>> quizList = [
//   {
//     'title': 'Question 1',
//     'logo': 'assets/icons/picturelearning.png',
//     'type': 'puzzle',
//     'attempted': false,
//     'by': "Akshay Tyagiwertfhyujikol",
//   },
//   {
//     'title': 'Question 2',
//     'logo': 'assets/icons/vLearning.png',
//     'type': 'Gernal Knowledge',
//     'attempted': false,
//     'by': "Akshay Tyagirtfybhunjmk",
//   },
//   // Add more quiz data as needed
//   {
//     'title': 'Question 3',
//     'logo': 'assets/icons/picturelearning.png',
//     'type': 'puzzle',
//     'attempted': true,
//     'by': "Akshay Tyagi",
//   },
//
// ];