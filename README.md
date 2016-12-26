EIL易居互动直播云平台播放Android SDK使用说明

EIL_nativeplayer_android SDK是Android 平台上使用的软件开发工具包(SDK), 负责播放视频直播和点播内容。

一. 功能特点

•	  音频编码：AAC

•	  视频编码：H.264

•	  播放流协议：RTMP, HLS, HTTP

•     显示：OpenGLES 2.0

•	  播放过程中支持录制不大于200s的视频(具体时间视格式而定)。

二. 运行环境

•	最低支持版本为Android 4.1 (API level 17)

•	支持的cpu架构：arm,armv7

三．快速集成

本章节提供一个快速集成推流SDK基础功能的示例。
具体可以参考app demo工程中的相应文件。

3.1 下载工程

3.1.1 github下载 从github下载SDK及demo工程

3.2 工程目录结构

•	appdemo: 示例工程，演示本SDK主要接口功能的使用
•	doc: SDK说明文档
•	libs: 集成SDK需要的所有库文件
o	libs/EIL_nativeplayersdk-debug(release).aar: Android Studio aar包
o	libs/ijkplayer-java-release.aar: Android Studio aar包

3.3 配置项目

引入目标库, 将libs目录下的库文件引入到目标工程中并添加依赖。
可参考下述配置方式（以android studio为例）：
•	将libs目录copy到目标工程app目录下；
- 在AndroidManifest.xml文件中申请相应权限
````xml
<!-- 使用权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

3.4 简单播放示例

具体可参考demo工程中的com.ej.demop类
•	在布局文件中加入预览View
•	<com.example.ejplayer.myplaysdk.widget.media.IjkVideoView
    android:id="@+id/video_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</com.example.ejplayer.myplaysdk.widget.media.IjkVideoView
•	
//创建播放管理类EILPlayerManager
player = new EILPlayerManager(this,R.id.video_view);

// 设置默认全屏模式，设置播放事件监听，返回播放信息。
player.setFullScreenOnly(true);
player.setScaleType(EILPlayerManager.SCALETYPE_FITXY);
player.playInFullScreen(true);

player.setPlayerStateListener(this);
//监听按钮响应事件 播放输入的url
public void clickTogglePlay(@SuppressWarnings("unused") View unused) {
    if(mRecordingEnabled) {
        player.stop();
        mRecordingEnabled=!mRecordingEnabled;
    }
    if(!mRecordingEnabled)
    {
        player.setFullScreenOnly(true);
        player.setScaleType(EILPlayerManager.SCALETYPE_FITXY);
        player.playInFullScreen(true);
        player.setPlayerStateListener(this);
        EditText et = (EditText)findViewById(R.id.editText);
        String s = et.getText().toString();
        player.play(s);
        mRecordingEnabled=!mRecordingEnabled;
    }
}



