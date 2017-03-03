package com.ej.demop;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ejplayer.myplaysdk.common.EILPlayerManager;
public class MainActivity extends AppCompatActivity implements EILPlayerManager.PlayerStateListener{

    private static final String TAG = "appplayer";
    private EILPlayerManager player;
    boolean mRecordingEnabled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Intent intent=new Intent(this,PlayActivity.class);  //方法1

        // startActivity(intent);
        View layout_outer = this.findViewById(R.id.video_view);
        initPlayer(layout_outer);
        mRecordingEnabled=false;
        if (PermissionChecker.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET},
                    0);
        }
     //   this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }
    @Override
    protected void onPause(){
        super.onStop();
         player.pause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return player.onTouch(null,event);
        //return true;
    }
    private void initPlayer(View view) {
        player = new EILPlayerManager(this,view);
        Log.i(TAG,"initPlayer start\n");
        player.setFullScreenOnly(true);
        player.setScaleType(EILPlayerManager.SCALETYPE_FITXY);
        player.playInFullScreen(true);

        player.setPlayerStateListener(this);
        //player.play("http://pili-media.qdtong.net/recordings/z1.leju-live-2.58b3e2b1089d4/20170208082817--z1.leju-live-2.58b3e2b1089d4-1042595.m3u8");

        //player.play("http://zv.3gv.ifeng.com/live/zhongwen800k.m3u8");
        //player.play("rtmp://pili-live-rtmp.qdtong.net/leju-live-2/97aaaa");

       // player.play("http://videoplay.ejucloud.com/newcode-88b562--20161124101351.mp4");

        //player.play("/sdcard/2slice.mp4");
//        Button toggleRelease = (Button) findViewById(R.id.button_record);
//        toggleRelease.setClickable(false);
//        toggleRelease.setEnabled(false);
        Log.i(TAG,"initPlayer\n");
    }
    private void updateControls() {
        Button toggleRelease = (Button) findViewById(R.id.button_pause);
        int id = mRecordingEnabled ?
                R.string.togglePlayOff : R.string.togglePlayOn;
        toggleRelease.setText(id);
        //CheckBox cb = (CheckBox) findViewById(R.id.rebindHack_checkbox);
        //cb.setChecked(TextureRender.sWorkAroundContextProblem);
    }
    public void clickTogglePlay(@SuppressWarnings("unused") View unused) {
      //  Button toggleRelease = (Button) findViewById(R.id.button_record);

        player.live(false);
        if(mRecordingEnabled) {
            player.stop();
            mRecordingEnabled=!mRecordingEnabled;
//            toggleRelease.setClickable(false);
//            toggleRelease.setEnabled(false);
        }
        if(!mRecordingEnabled)
        {
        //    player.setFullScreenOnly(true);
        //    player.setScaleType(EILPlayerManager.SCALETYPE_FITXY);
        //    player.playInFullScreen(true);

            player.setPlayerStateListener(this);

            EditText et = (EditText)findViewById(R.id.editText);
            String s = et.getText().toString();
            player.play(s);
            mRecordingEnabled=!mRecordingEnabled;
//            toggleRelease.setClickable(true);
//            toggleRelease.setEnabled(true);
        }


        //  updateControls();
    }
    public void clickTogglePause(@SuppressWarnings("unused") View unused) {

        if(mRecordingEnabled)
            player.pause();
        else
            player.onResume();
        mRecordingEnabled=!mRecordingEnabled;
        updateControls();
    }
    public void clickToggleRecord(@SuppressWarnings("unused") View unused) {

//        player.pause();
//        player.record();
//        player.onResume();
    }
    @Override
    public void onComplete() {
        Log.i(TAG,"complete\n");
        new AlertDialog.Builder(this)
                .setTitle("complete")
                .setMessage("complete")
                .setPositiveButton("Cancel", null)
                .show();
    }

    @Override
    public void onError() {
        Log.i(TAG,"error\n");
        new AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("Could't Connect To URL, Please try again later")
                .setPositiveButton("Cancel", null)
                .show();

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPlay() {

        //  player.pause();
        Log.i(TAG,"onPlay\n");
    }
}

