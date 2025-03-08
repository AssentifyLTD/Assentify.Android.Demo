
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

# Keep all classes and members annotated with @Keep
# Preserve all classes, methods, fields annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }

# Preserve nested classes annotated with @Keep
-keep @androidx.annotation.Keep class . { *; }

# Ensure constructors and parameter names are maintained for classes annotated with @Keep
-keepclassmembers class * {
    @androidx.annotation.Keep <init>(...);
}

# Keep parameter names for methods and constructors in classes annotated with @Keep
-keepattributes Signature, Annotation, MethodParameters, InnerClasses, EnclosingMethod

-dontwarn javax.servlet.ServletContainerInitializer

# Keep protobuf objects
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
}

# Keep Companion object fields of serializable classes.
# This avoids serializer lookup through getDeclaredClasses as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep serializer() on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}


-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class com.bob.click2pay.navigation.Screen {
    * *;
}

-keep class com.bob.click2pay.navigation.Screen$* {
    * *;
}

# Keep INSTANCE.serializer() of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Preserve all classes in the Assentify SDK package
-keep class com.assentify.sdk.** { *; }
# Tensorflow
-keep class org.tensorflow.** { *;}
-keep class org.tensorflow.lite.* { *; }
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keepattributes Annotation
# Keep models
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Don't print notes about potential mistakes or omissions in the configuration for kotlinx-serialization classes
# See also https://github.com/Kotlin/kotlinx.serialization/issues/1900
-dontnote kotlinx.serialization.**

# Serialization core uses java.lang.ClassValue for caching inside these specified classes.
# If there is no java.lang.ClassValue (for example, in Android), then R8/ProGuard will print a warning.
# However, since in this case they will not be used, we can disable these warnings
-dontwarn kotlinx.serialization.internal.ClassValueReferences

-verbose

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
