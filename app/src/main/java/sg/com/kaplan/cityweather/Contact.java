package sg.com.kaplan.cityweather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);}

        // contact form validate
        public void send_click(View v){
        EditText name = (EditText)findViewById(R.id.name);
        EditText email = (EditText)findViewById(R.id.email);
        EditText subject = (EditText)findViewById(R.id.subject);
        EditText message = (EditText)findViewById(R.id.message);

        if (name.getText().toString().equals(""))
            name.setError("Mandotary Field");
        else if (email.getText().toString().equals(""))
            email.setError("Mandotary Field");
        else if (subject.getText().toString().equals(""))
            subject.setError("Mandotary Field");
        else if(message.getText().toString().equals(""))
            message.setError("Mandotary Field");

        // action after send button clicked
        else
        {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL,new String[]{"gracesstang@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT,"Dear Gracess,\n"+ message.getText().toString()+ "\n regards,"
            + name.getText().toString() + ",\n" +email.getText().toString());  //Standardize email body

            try{
                startActivity(Intent.createChooser(i,"send email"));
            }catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(this,"no mail app found",Toast.LENGTH_SHORT).show();
            }
        catch (Exception ex)
        {
            Toast.makeText(this,"Unexpected Error"+ ex.toString(),Toast.LENGTH_SHORT).show();
        }
        }
    }
}