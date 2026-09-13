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

# Required by bundled dependency references; these optional annotation/progress classes are not packaged.
-dontwarn com.facebook.infer.annotation.Nullsafe$Mode
-dontwarn com.facebook.infer.annotation.Nullsafe
-dontwarn me.zhanghai.android.materialprogressbar.HorizontalProgressDrawable
-dontwarn me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable
-dontwarn me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable

# Preserve generic signatures and runtime annotations used by Retrofit and Gson.
-keepattributes Signature,InnerClasses,EnclosingMethod
-keepattributes RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations,AnnotationDefault

# Retrofit service interfaces are discovered through annotations and generic return types.
-keep,allowoptimization,allowshrinking,allowobfuscation interface com.mmschooledu.examresults.Task.MainApiService
-keep,allowoptimization,allowshrinking,allowobfuscation interface com.mmschooledu.examresults.Task.RegionApiService

# Gson-backed exam-result models require original JSON field names.
-keepclassmembers class com.mmschooledu.examresults.model.** {
    <fields>;
}

# Android XML onClick callbacks are resolved by method name at runtime.
-keepclassmembers class com.mmschooledu.Main {
    public void onIconClick(android.view.View);
}
-keepclassmembers class ** {
    public void _Back_Click(android.view.View);
    public void _Font_Click(android.view.View);
    public void downloadClick(android.view.View);
    public void onClick(android.view.View);
    public void playClick(android.view.View);
    public void readClick(android.view.View);
}

# Keep useful line information in production crash reports while retaining obfuscation.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile