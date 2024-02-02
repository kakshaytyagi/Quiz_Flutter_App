class Submission {
  final int? submissionId;
  final int questionId;
  final int? userId;
  final String submissionTime;
  final String? dt;
  final int status;

  Submission({
    this.submissionId,
    required this.questionId,
    this.userId,
    required this.submissionTime,
    this.dt,
    required this.status,
  });

  Map<String, dynamic> toJson() {
    return {
      'submissionId': submissionId,  // Corrected field name
      'questionId': questionId,      // Corrected field name
      'userId': userId,              // Corrected field name
      'submissionTime': submissionTime,
      'dt': dt,
      'status': status,
    };
  }

  factory Submission.fromJson(Map<String, dynamic> json) {
    return Submission(
      submissionId: json['submissionId'],
      questionId: json['questionId'],
      userId: json['userId'],
      submissionTime: json['submissionTime'],
      dt: json['dt'],
      status: json['status'],
    );
  }
}