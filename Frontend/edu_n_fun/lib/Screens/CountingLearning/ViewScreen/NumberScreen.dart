import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

import '../../../Widgets/SoundManager.dart';

class NumberCode extends StatefulWidget {
  final String number;

  NumberCode({required this.number});

  @override
  _NumberCodeState createState() => _NumberCodeState();
}

class _NumberCodeState extends State<NumberCode> {
  VideoPlayerController? _controller;
  bool _isAnimating = false;

  @override
  void initState() {
    super.initState();
    _playNumberVideo();
  }

  @override
  void dispose() {
    _controller?.dispose();
    super.dispose();
  }

  void _playNumberVideo() {
    String videoPath = 'assets/animations/number_${widget.number.toUpperCase()}.mp4';
    _controller = VideoPlayerController.asset(videoPath)
      ..initialize().then((_) {
        setState(() {
          _controller!.play();
        });
      });
  }

  @override
  void didUpdateWidget(NumberCode oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.number != oldWidget.number) {
      _playNumberVideo();
    }
  }

  @override
  Widget build(BuildContext context) {
    return _controller != null && _controller!.value.isInitialized
        ? Stack(
      children: [
        ClipRRect(
          borderRadius: BorderRadius.circular(12.0),
          child: AspectRatio(
            aspectRatio: _controller!.value.aspectRatio,
            child: VideoPlayer(_controller!),
          ),
        ),
        Positioned.fill(
          child: Container(
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(12.0),
              gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [
                  Colors.transparent,
                  Colors.transparent,
                  Colors.black.withOpacity(0.2),
                  Colors.black.withOpacity(0.2),
                  Colors.transparent,
                  Colors.transparent,
                ],
                stops: [0.0, 0.1, 0.3, 0.7, 0.9, 1.0],
              ),
            ),
          ),
        ),
        Positioned(
          top: 16.0,
          right: 16.0,
          child: GestureDetector(
            onTap: handleSpeakerButton,
            onTapDown: (_) {
              setState(() {
                _isAnimating = true;
              });
            },
            onTapUp: (_) {
              setState(() {
                _isAnimating = false;
              });
            },
            onTapCancel: () {
              setState(() {
                _isAnimating = false;
              });
            },
            child: AnimatedContainer(
              duration: const Duration(milliseconds: 300),
              decoration: BoxDecoration(
                color: _isAnimating ? Colors.blue : Colors.white,
                borderRadius: BorderRadius.circular(20.0),
              ),
              padding: EdgeInsets.all(8.0),
              child: Image.asset(
                'assets/icons/speaker.png',
                width: 24.0,
                height: 24.0,
                color: _isAnimating ? Colors.white : Colors.black,
              ),
            ),
          ),
        ),
      ],
    )
        : CircularProgressIndicator();
  }

  void handleSpeakerButton() {
    SoundManager.playSound(widget.number);
  }
}
