import 'dart:async';

import 'package:flutter/services.dart';

class TripleDes {
  static const MethodChannel _channel =
  const MethodChannel('triple_des');


  static Future<String> encrypt(String val, String key) async {
    if (val == null || val.isEmpty || key == null || key.isEmpty)
      return null;
    print("hello");
    var args = {
      'text': val,
      'key': key,
    };
    return await _channel.invokeMethod('encrypt-single-value', args);
  }

  static Future<String> decrypt(String val, String key) async {
    if (val == null || val.isEmpty || key == null || key.isEmpty)
      return null;
    var args = {
      'text': val,
      'key': key,
    };
    return await _channel.invokeMethod('decrypt-single-value', args);
  }
}
