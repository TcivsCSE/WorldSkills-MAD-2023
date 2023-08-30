import 'dart:async';
import 'dart:io';
import 'dart:typed_data';

import 'package:audioplayers/audioplayers.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:mic_stream/mic_stream.dart';
import 'package:path_provider/path_provider.dart';

class RecordPage extends StatefulWidget {
  const RecordPage({super.key});

  @override
  State<StatefulWidget> createState() => _RecordPageState();
}

class _RecordPageState extends State<RecordPage> {
  bool isRecording = false;
  late StreamSubscription<Uint8List> micStreamListener;
  List<int> micStreamBuffer = [];

  AudioPlayer _audioPlayer = AudioPlayer();

  void _recordVoice() async {
    micStreamBuffer.clear();
    Stream<Uint8List> micStream = (await MicStream.microphone(
    ))!;
    micStreamListener = micStream.listen((samples) => _audioPlayer.play(BytesSource(samples)));
  }

  void _saveRecord() async {
    await micStreamListener.cancel();
    String appDocDir = (await getApplicationDocumentsDirectory()).path;
    String timestamp = DateTime.now().toIso8601String();
    await Directory("$appDocDir/records").create();
    File audioFile = File("$appDocDir/records/$timestamp");
    await audioFile.create();
    await audioFile.writeAsBytes(Uint8List.fromList(micStreamBuffer));
    micStreamBuffer.clear();
    print(audioFile.path);
  }

  void _playAudio(String fileName) async {
    String appDocDir = (await getApplicationDocumentsDirectory()).path;
    await Directory("$appDocDir/records").create();
    File audioFile = File("$appDocDir/records/$fileName");
    ByteBuffer fileBuffer = (await audioFile.readAsBytes()).buffer;
    for (int offsetIndex in Iterable.generate((fileBuffer.lengthInBytes / 1344).floor())) {
      await _audioPlayer.play(BytesSource(fileBuffer.asUint8List(1344 * offsetIndex, 1344)));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Records"),
      ),
      body: Column(
        children: [
          Column(
            children: [
              ElevatedButton(
                onPressed: () async {
                  if (isRecording) {
                    setState(() {
                      isRecording = false;
                    });
                    _saveRecord();
                  } else {
                    setState(() {
                      isRecording = true;
                    });
                    _recordVoice();
                  }
                },
                child: Text(isRecording ? "Stop Record" : "Start Record"),
              ),
              ElevatedButton(
                onPressed: () async {
                  _playAudio("2023-08-23T04:42:25.155087");
                },
                child: Text("Play"),
              )
            ],
          ),
          Expanded(
            child: ListView(
              children: [

              ],
            ),
          )
        ],
      ),
    );
  }
}
