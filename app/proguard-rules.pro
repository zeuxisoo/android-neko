# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zeuxis/Documents/AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 10
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-dontoptimize
-dontobfuscate
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn
-dontnote

-keep public class im.after.**
-keepclassmembers public class im.after.**  { public *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# Support library
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

# Retrolambda
-dontwarn java.lang.invoke.**

# Jackson
-dontwarn com.fasterxml.jackson.**

# Picasso
-dontwarn com.squareup.okhttp.**

# Pretty time
-keep public class com.ocpsoft.pretty.time.**

# Sweet alert
-keep public class cn.pedant.SweetAlert.Rotate3dAnimation {
    public <init>(...);
}

# Loopj
-keep class com.loopj.android.http.** { *; }
-keep interface com.loopj.android.http.** { *; }

# Bypass
-keep class in.uncod.android.bypass.** { *; }

# Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}