import 'package:flutter/material.dart';
import 'package:chewie/chewie.dart';
import 'package:video_player/video_player.dart';

class VideoPlayScreen extends StatefulWidget {
  final String backgroundImage;
  final String title;
  final String video;

  VideoPlayScreen({required this.backgroundImage, required this.title, required this.video});

  @override
  _VideoPlayScreenState createState() => _VideoPlayScreenState();
}

class _VideoPlayScreenState extends State<VideoPlayScreen> {
  late VideoPlayerController _videoPlayerController;
  ChewieController? _chewieController;
  bool _isMuted = false;
  bool _isPlaying = true; // Track the play/pause state

  @override
  void initState() {
    super.initState();
    _videoPlayerController = VideoPlayerController.asset(widget.video);
    _initializeVideoPlayer();
  }

  @override
  void dispose() {
    _chewieController?.dispose();
    _videoPlayerController.dispose();
    super.dispose();
  }

  Future<void> _initializeVideoPlayer() async {
    await _videoPlayerController.initialize();
    _chewieController = ChewieController(
      videoPlayerController: _videoPlayerController,
      autoPlay: true,
      looping: true,
      allowedScreenSleep: false,
      showControls: true,
      showOptions: false,
      autoInitialize: true,
      errorBuilder: (context, errorMessage) {
        return Center(
          child: Text(
            errorMessage,
            style: TextStyle(color: Colors.white),
          ),
        );
      },
    );
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage(widget.backgroundImage),
                fit: BoxFit.cover,
              ),
            ),
          ),
          Positioned(
            top: 50.0,
            left: 20.0,
            child: GestureDetector(
              onTap: () {
                Navigator.pop(context);
              },
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.green, // You can use any color you like here
                  shape: BoxShape.circle,
                ),
                padding: EdgeInsets.all(12.0),
                child: Icon(
                  Icons.arrow_back,
                  color: Colors.white,
                  size: 30.0,
                ),
              ),
            ),
          ),
          Center(
            child: SizedBox(
              width: 380,
              height: 500,
              child: Stack(
                children: [
                  if (_chewieController != null && _chewieController!.videoPlayerController.value.isInitialized)
                    Center(
                      child: AspectRatio(
                        aspectRatio: _chewieController!.videoPlayerController.value.aspectRatio,
                        child: Chewie(controller: _chewieController!),
                      ),
                    ),
                  Align(
                    alignment: Alignment.topCenter, // Align at the top center
                    child: Padding(
                      padding: EdgeInsets.only(top: 20.0),
                      child: Text(
                        widget.title, // Your custom text title
                        style: TextStyle(
                          fontSize: 44.0, // Customize the font size
                          fontWeight: FontWeight.bold,
                          color: Colors.redAccent, // Customize the font color
                          fontFamily: 'EduSA', // Use the kids' font (replace 'KidsFont' with the actual font name)
                        ),
                      ),
                    ),
                  ),
                  Align(
                    alignment: Alignment.bottomLeft, // Align at the bottom left corner
                    child: Padding(
                      padding: EdgeInsets.only(left: 20.0, bottom: 20.0),
                      child: GestureDetector(
                        onTap: () {
                          setState(() {
                            _isMuted = !_isMuted;
                            _videoPlayerController.setVolume(_isMuted ? 0.0 : 1.0);
                          });
                        },
                        child: Tooltip(
                          message: _isMuted ? "Sound Off" : "Sound On",
                          child: Container(
                            decoration: BoxDecoration(
                              color: Colors.blue, // You can use any color you like here
                              shape: BoxShape.circle,
                            ),
                            padding: EdgeInsets.all(12.0),
                            child: Icon(
                              _isMuted ? Icons.volume_off : Icons.volume_up,
                              color: Colors.white,
                              size: 40.0,
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                  Align(
                    alignment: Alignment.bottomRight, // Align at the bottom right corner
                    child: Padding(
                      padding: EdgeInsets.only(right: 20.0, bottom: 20.0),
                      child: GestureDetector(
                        onTap: () {
                          setState(() {
                            // Toggle the play/pause state
                            _isPlaying = !_isPlaying;

                            // Perform play/pause action on the video
                            if (_chewieController != null) {
                              if (_isPlaying) {
                                _chewieController!.play();
                              } else {
                                _chewieController!.pause();
                              }
                            }
                          });
                        },
                        child: AnimatedSwitcher(
                          duration: Duration(milliseconds: 300),
                          switchInCurve: Curves.easeInOut,
                          switchOutCurve: Curves.easeInOut,
                          key: ValueKey<bool>(_isPlaying),
                          transitionBuilder: (Widget child, Animation<double> animation) {
                            return ScaleTransition(
                              scale: animation,
                              child: child,
                            );
                          },
                          // Define the two icons to switch between
                          child: _isPlaying
                              ? Container(
                            width: 65,
                            height: 65,
                            decoration: BoxDecoration(
                              color: Colors.yellow,
                              shape: BoxShape.circle,
                            ),
                            child: Icon(
                              Icons.pause,
                              color: Colors.white,
                              size: 50.0,
                              key: ValueKey<String>("pause"), // Key for the pause icon
                            ),
                          )
                              : Container(
                            width: 65,
                            height: 65,
                            decoration: BoxDecoration(
                              color: Colors.yellow,
                              shape: BoxShape.circle,
                            ),
                            child: Icon(
                              Icons.play_arrow,
                              color: Colors.white,
                              size: 50.0,
                              key: ValueKey<String>("play"), // Key for the play icon
                            ),
                          ),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
