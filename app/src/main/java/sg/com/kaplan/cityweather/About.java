package sg.com.kaplan.cityweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    // Action after share button clicked
    Button shareBtn;
    Intent shareIntent;
    String shareBody = "This is a great app,you should try it out!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        shareBtn = (Button)findViewById(R.id.sharebtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/pain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent,"Share Via"));

            }
        });


    }
}
