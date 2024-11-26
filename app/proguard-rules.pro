# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Disable logging for a specific class or method
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}
-dontwarn android.app.ResourcesManager
-dontwarn android.content.res.Configuration


# Ignore warnings related to accessing hidden APIs
#-dontwarn android.app.ResourcesManager
#-dontwarn android.content.res.ApkAssets
#-dontwarn android.content.res.Configuration


# Keep application-level code from being obfuscated
#-keep class com.example.composekotlin.** { *; }

# Allow access to required hidden APIs (ignoring the greylist and blacklist warnings)
# These rules disable obfuscation for specific packages or methods that access hidden APIs.
# This is to prevent the app from crashing due to hidden API access issues.

# Allow access to resources manager-related fields
