package com.smeetbhatt.triple_des;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** TripleDesPlugin */
public class TripleDesPlugin implements FlutterPlugin, MethodCallHandler {
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "triple_des");
    channel.setMethodCallHandler(new TripleDesPlugin());
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "triple_des");
    channel.setMethodCallHandler(new TripleDesPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
//    if (call.method.equals("getPlatformVersion")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
//    } else {
//      result.notImplemented();
//    }

    System.out.println(call.method);
    String text = call.argument("text");
    String key = call.argument("key");
    switch (call.method) {
      case "encrypt-single-value":
        result.success(encrypt(text, key));
        break;
      case "decrypt-single-value":
        result.success(decrypt(text, key));
        break;
      case "encrypt-json-data":
        break;
      case "decrypt-json-data":
        break;
        default:
          result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }

  private static TripleDESCrypto des = new TripleDESCrypto();
  private String encrypt(String text, String key) {
    try {
      return des.encrypt(text, key);
    }
    catch (Exception e){
      return null;
    }
  }

  private String decrypt(String text, String key) {
    try {
      return des.decrypt(text, key);
    }
    catch (Exception e){
      return null;
    }
  }
}
