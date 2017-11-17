package sg.com.kaplan.cityweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            // set Image button 1 = Weather
        ImageButton imgBtn1 = (ImageButton) findViewById(R.id.imgBtn1);
        imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Weather.class);
                startActivity(intent);
            }
        });

            // set image button 2 = Gallery
        ImageButton imgBtn2 = (ImageButton) findViewById(R.id.imgBtn2);
        imgBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Gallery.class);
                startActivity(intent);
            }
        });


        // set image button 3 = Contact us
        ImageButton imgBtn3 = (ImageButton) findViewById(R.id.imgBtn3);
        imgBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Contact.class);
                startActivity(intent);
            }
        });


        // set image button 4 = About us
        ImageButton imgBtn4 = (ImageButton) findViewById(R.id.imgBtn4);
        imgBtn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
            }
        });




            }
        }






