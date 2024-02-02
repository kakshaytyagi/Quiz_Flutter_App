class QuestionTopper {
  final String name;
  final String avatar;
  final String submissionTime;

  QuestionTopper({
    required this.name,
    required this.avatar,
    required this.submissionTime,
  });

  factory QuestionTopper.fromJson(Map<String, dynamic> json) {
    return QuestionTopper(
      name: json['username'] as String,
      avatar: json['avatar'] as String,
      submissionTime: json['submissionTime'] as String,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'avatar': avatar,
      'submissionTime': submissionTime,
    };
  }
}
