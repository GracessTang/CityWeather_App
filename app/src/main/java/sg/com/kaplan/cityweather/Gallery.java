package sg.com.kaplan.cityweather;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Gallery extends AppCompatActivity {

    MediaPlayer mediaPlayer; // set background music
    ViewPager viewPager;  // set Image slideshow

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gallery);
            mediaPlayer = MediaPlayer.create(this, R.raw.bgm); //music file

            viewPager = (ViewPager)findViewById(R.id.viewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
            viewPager.setAdapter(viewPagerAdapter);

    }

    public void playBgm(View view){
            mediaPlayer.start();
    } //click for bgm start
    public void pauseBgm(View view){
            mediaPlayer.pause();
    } //click for bgm pause
    public void stopBgm(View view){
            mediaPlayer.stop();
    } //click for bgm stop


}
