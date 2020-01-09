import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:triple_des/triple_des.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Triple DES",
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const String _KEY = "736282947252748395019284718391046281930183771625486920285647467583209871";
//  static const String _KEY = "7362829472527483";
  final _textController = TextEditingController();
  String _generatedVal = "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Triple DES"),
      ),
      body: SafeArea(
        child: Container(
          padding: EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              TextField(
                controller: _textController,
                decoration: InputDecoration(
                    hintText: "Enter Text",
                    labelText: "Text"
                ),
                keyboardType: TextInputType.text,
              ),
              SizedBox(height: 10,),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
                  FlatButton(
                    child: Text("Encrypt", style: TextStyle(color: Colors.blue),),
                    onPressed: _encrypt,
                  ),
                  FlatButton(
                    child: Text("Decrypt", style: TextStyle(color: Colors.blue),),
                    onPressed: _decrypt,
                  )
                ],
              ),
              SizedBox(height: 10,),
              SelectableText("$_generatedVal", textAlign: TextAlign.center,
                style: TextStyle(color: Colors.black, fontSize: 23
                ),
              )
            ],

          ),
        ),
      ),
    );
  }

  void _encrypt() async {
    String encryptedText = await TripleDes.encrypt(_textController.text, _KEY);
    setState(() {
      _generatedVal = "Cipher Text\n\n" + encryptedText ;
    });
  }

  void _decrypt() async {
    String encryptedText = await TripleDes.decrypt(_textController.text, _KEY);
    setState(() {
      _generatedVal = "Plain Text\n\n" + encryptedText;
    });
  }
}
