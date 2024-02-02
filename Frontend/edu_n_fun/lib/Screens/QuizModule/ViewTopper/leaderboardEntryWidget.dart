import 'package:flutter/material.dart';

class LeaderboardEntryWidget extends StatelessWidget {
  final LeaderboardEntry entry;

  LeaderboardEntryWidget({required this.entry});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        CircleAvatar(
          radius: 36,
          backgroundImage: AssetImage(entry.avatarUrl),
          backgroundColor: Color(0xFF42A5F5),
        ),
        SizedBox(height: 8),
        Text(
          entry.name,
          style: TextStyle(
            fontFamily: 'Comic Sans MS',
            fontSize: 20,
            fontWeight: FontWeight.bold,
            color: Colors.black,
          ),
        ),
        SizedBox(height: 4),
        Text(
          entry.time,
          style: TextStyle(
            fontWeight: FontWeight.bold,
            color: Colors.green,
          ),
        ),
      ],
    );
  }
}

class LeaderboardEntryListTile extends StatelessWidget {
  final LeaderboardEntry entry;
  final int rank;

  LeaderboardEntryListTile({required this.entry, required this.rank});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          Container(
            padding: EdgeInsets.all(8.0),
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: Colors.orange,
            ),
            child: Text(
              '$rank',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 16,
                color: Colors.white,
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
          color: Colors.black, // Black text for readability
        ),
      ),
      trailing: Text(
        entry.time,
        style: TextStyle(
          fontSize: 17,
          fontWeight: FontWeight.bold,
          color: Colors.green, // Green for a professional touch
        ),
      ),
      contentPadding: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0), // Adjust padding
    );
  }
}


class LeaderboardEntry {
  final String name;
  final String time;
  final String avatarUrl;

  LeaderboardEntry({required this.name, required this.time, required this.avatarUrl});
}
