class Question {
  final int? questionId;
  final int? userId;
  final String category;
  final String question;
  final String? option1;
  final String? option2;
  final String? option3;
  final String? option4;
  final int answer;
  final int isOption;
  final String? statement1;
  final String? statement2;
  final String? statement3;
  final String? image;
  final String? createdBy;
  final String? dt;
  final int? status;

  Question({
    this.questionId,
    this.userId,
    required this.category,
    required this.question,
    required this.option1,
    required this.option2,
    required this.option3,
    required this.option4,
    required this.answer,
    required this.isOption,
    required this.statement1,
    required this.statement2,
    required this.statement3,
    required this.image,
    this.createdBy,
    this.dt,
    this.status,
  });

  factory Question.fromJson(Map<String, dynamic> json) {
    return Question(
      questionId: json['question_id'] as int,
      userId: json['user_id'] as int,
      category: json['category'] as String,
      question: json['question'] as String,
      option1: json['option1'] as String?,
      option2: json['option2'] as String?,
      option3: json['option3'] as String?,
      option4: json['option4'] as String?,
      answer: json['answer'] as int? ?? 0,
      isOption: json['isoption'] as int,
      statement1: json['statement1'] as String?,
      statement2: json['statement2'] as String?,
      statement3: json['statement3'] as String?,
      image: json['image'] as String?,
      createdBy: json['created_by'] as String,
      dt: json['dt'] as String,
      status: json['status'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'category': category,
      'question': question,
      'option1': option1,
      'option2': option2,
      'option3': option3,
      'option4': option4,
      'answer': answer,
      'isoption': isOption,
      'statement1': statement1,
      'statement2': statement2,
      'statement3': statement3,
      'image': image,
    };
  }
}
