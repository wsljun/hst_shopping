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
 -ignorewarnings
 #代码混淆压缩比，在0~7之间，默认为5，一般不做修改
 -optimizationpasses 5

 #把混淆类中的方法名也混淆了
 -useuniqueclassmembernames

 #优化时允许访问并修改有修饰符的类和类的成员
 -allowaccessmodification

 # 避免混淆内部类、泛型、匿名类
 -keepattributes InnerClasses,Signature,EnclosingMethod

 #抛出异常时保留代码行号
 -keepattributes SourceFile,LineNumberTable

 #重命名抛出异常时的文件名称为"SourceFile"
 -renamesourcefileattribute SourceFile

 #保持所有实现 Serializable 接口的类成员
 -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }

 #保留我们使用的四大组件，自定义的Application等等这些类不被混淆
 #因为这些子类都有可能被外部调用
 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Appliction
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference

 #保留support下的所有类及其内部类
 -keep class android.support.** {*;}
 # 保留继承的support类
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.support.v7.**
 -keep public class * extends android.support.annotation.**

 #保留我们自定义控件（继承自View）不被混淆
 -keep public class * extends android.view.View{
     *** get*();
     void set*(***);
     public <init>(android.content.Context);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }

 #Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
 -keep public class * extends android.app.Fragment

 # 保持测试相关的代码
 -dontnote junit.framework.**
 -dontnote junit.runner.**
 -dontwarn android.test.**
 -dontwarn android.support.test.**
 -dontwarn org.junit.**