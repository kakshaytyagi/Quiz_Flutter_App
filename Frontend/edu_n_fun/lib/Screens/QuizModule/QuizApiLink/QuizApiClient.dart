import 'dart:convert';
import 'package:http/http.dart' as http;
import 'Question.dart';
import 'QuestionTopper.dart';
import 'Submission.dart';
import 'TopPerformers.dart';

class QuizApiClient {
  static const baseUrl = 'http://10.0.2.2:8087';
  static String YOUR_AUTH_TOKEN = '';

  static Future<void> signIn(String email, String password) async {
    final headers = {
      'Content-Type': 'application/json',
    };


    Map<String, dynamic> requestBody = {
      'email' : email,
      'password': password,
    };


    print(requestBody.toString());

    final body = jsonEncode(requestBody);

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/login/doAnyLogin'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      YOUR_AUTH_TOKEN = jsonResponse['token'] as String; // Assign the token
    } else {
      throw Exception('Failed to add user');
    }
  }

  static Future<void> signUp(String username, String emailOrContact, String password) async {
    final headers = {
      'Content-Type': 'application/json',
    };

    String email = '';
    String contact = '';

    // Use regular expressions to determine if it's an email or contact
    if (RegExp(r'^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$').hasMatch(emailOrContact)) {
      email = emailOrContact;
    } else {
      contact = emailOrContact;
    }

    // Create a map with the fields you want to include in the JSON body
    Map<String, dynamic> requestBody = {
      'username': username,
      'password': password,
    };

    // Conditionally add 'email' and 'contact' to the request body
    if (email.isNotEmpty) {
      requestBody['email'] = email;
    }

    if (contact.isNotEmpty) {
      requestBody['contact'] = contact;
    }

    final body = jsonEncode(requestBody);

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/User/add/User'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
    } else {
      throw Exception('Failed to add user');
    }
  }

  static Future<List<Question>> searchAllQuestions() async {
    print("Empty token : " + YOUR_AUTH_TOKEN);
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
    };

    final body = jsonEncode({});

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Questions/Innersearch/question'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final body = jsonResponse['body'] as List<dynamic>;

      final questions = body
          .map((data) => Question.fromJson(data as Map<String, dynamic>))
          .toList();
      return questions;
    } else {
      throw Exception('Failed to load questions');
    }
  }

  static Future<List<Question>> searchQuestions() async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
    };

    final body = jsonEncode({});

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Questions/search/question'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final body = jsonResponse['body'] as List<dynamic>;

      final questions = body
          .map((data) => Question.fromJson(data as Map<String, dynamic>))
          .toList();
      return questions;
    } else {
      throw Exception('Failed to load questions');
    }
  }

  // static Future<List<Question>> searchQuestionById(int id) async {
  //   final headers = {
  //     'Content-Type': 'application/json',
  //     'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
  //   };
  //
  //   final body = jsonEncode({
  //     'question_id' : id,
  //   });
  //
  //   final response = await http.post(
  //     Uri.parse('$baseUrl/EduFun/Questions/search/question'),
  //     headers: headers,
  //     body: body,
  //   );
  //
  //   print('Response status code: ${response.statusCode}');
  //
  //   if (response.statusCode == 200) {
  //     final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
  //     final body = jsonResponse['body'] as List<dynamic>;
  //
  //     final questions = body
  //         .map((data) => Question.fromJson(data as Map<String, dynamic>))
  //         .toList();
  //     return questions;
  //   } else {
  //     throw Exception('Failed to load questions');
  //   }
  // }

  static Future<Question> addQuiz(Question question) async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
    };

    final body = jsonEncode(question.toJson()); // Convert the Question object to JSON

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Questions/add/question'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final addedQuestion = Question.fromJson(jsonResponse); // Parse the response as a Question object
      return addedQuestion;
    } else {
      throw Exception('Failed to add quiz');
    }
  }

  static Future<bool> updateUser() async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
    };

    final body = jsonEncode({
      'count': 1,
    });

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/User/update/User'),
      headers: headers,
      body: body,
    );

    if (response.statusCode == 200) {
      return true; // update successful
    } else {
      return false; // update failed
    }
  }

  static Future<bool> submitAnswer(int? questionId, String submissionTime, int status) async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}',
    };

    final body = jsonEncode({
      'question_id': questionId,
      'submissionTime': submissionTime,
      'status': status,
    });

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Submission/add/Submission'),
      headers: headers,
      body: body,
    );

    if (response.statusCode == 200) {
      return true; // Submission successful
    } else {
      return false; // Submission failed
    }
  }

  static Future<List<Submission>> searchSubmission() async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${YOUR_AUTH_TOKEN}', // Replace with your actual token
    };

    final body = jsonEncode({});

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Submission/add/Submission'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final body = jsonResponse['body'] as List<dynamic>;

      final submissions = body
          .map((data) => Submission.fromJson(data as Map<String, dynamic>))
          .toList();
      return submissions;
    } else {
      throw Exception('Failed to load submissions');
    }
  }

  static Future<List<QuestionTopper>> fetchQuestionToppers(int? questionId) async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $YOUR_AUTH_TOKEN', // Replace with your actual token
    };

    final body = jsonEncode({
      'question_id': questionId,
    });

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Submission/Question/Topper'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final body = jsonResponse['body'] as List<dynamic>;

      final questionToppers = body
          .map((data) => QuestionTopper.fromJson(data as Map<String, dynamic>))
          .toList();
      return questionToppers;
    } else {
      throw Exception('Failed to load Topper');
    }
  }

  static Future<List<TopPerformers>> fetchTopPerformers() async {
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $YOUR_AUTH_TOKEN', // Replace with your actual token
    };

    // An empty request body
    final body = jsonEncode({});

    final response = await http.post(
      Uri.parse('$baseUrl/EduFun/Submission/Top/Performer'),
      headers: headers,
      body: body,
    );

    print('Response status code: ${response.statusCode}');

    if (response.statusCode == 200) {
      final jsonResponse = json.decode(response.body) as Map<String, dynamic>;
      final body = jsonResponse['body'] as List<dynamic>;

      final topPerformers = body
          .map((data) => TopPerformers.fromJson(data as Map<String, dynamic>))
          .toList();

      for (TopPerformers topPerformer in topPerformers) {
        print('Name: ${topPerformer.name}');
        print('Avatar: ${topPerformer.avatar}');
        print('Question: ${topPerformer.question}');
      }

      return topPerformers;
    } else {
      throw Exception('Failed to load Topper');
    }
  }

}

