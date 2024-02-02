class TopPerformers {
  final String name;
  final String avatar;
  final int question;

  TopPerformers({
    required this.name,
    required this.avatar,
    required this.question,
  });

  factory TopPerformers.fromJson(Map<String, dynamic> json) {
    return TopPerformers(
      name: json['username'] ?? '',
      avatar: json['avatar'] ?? '',
      question: json['question'] ?? 0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'avatar': avatar,
      'question': question,
    };
  }
}
