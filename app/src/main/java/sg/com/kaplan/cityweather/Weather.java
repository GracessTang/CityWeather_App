package sg.com.kaplan.cityweather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather extends AppCompatActivity {




        private EditText cityText;
        private TextView textView;
        private ImageView icon;

        // Using API from open weathermap.org
        private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private static final String API_KEY = "32f923e22a7b08aaa71a3c766ae533e9"; //my personal API

        private static final String ICONS_URL = "http://openweathermap.org/img/w/"; //Weather icon

        private static final String ERROR_1 = "Please enter a city";
        private static final String ERROR_2 = "Failed to find weather";

        private static final String SUCCESS = "Weather successfully loaded"; //toast

    public void getWeather(View view) {
        String city = cityText.getText().toString();

        if (city.length() == 0) {
            Toast.makeText(Weather.this, ERROR_1, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String encoded = URLEncoder.encode(city, "UTF-8");

            GetData data = new GetData();
            data.execute(API_URL + encoded + "&appid=" + API_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            Toast.makeText(Weather.this, ERROR_2, Toast.LENGTH_SHORT).show();
        }
    }

    protected class GetData extends AsyncTask<String, Void, String> {
        @Override
        public String doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection;

            String result = "";

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;
            } catch (Exception e) {
                Weather.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Weather.this, ERROR_2, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                String location = jsonObject.getString("name");
                long time = jsonObject.getLong("dt");

                String weatherInfo = jsonObject.getString("weather");
                JSONArray weatherArr = new JSONArray(weatherInfo);
                JSONObject weather = weatherArr.getJSONObject(0);
                String weatherMain = weather.getString("main");
                String weatherDesc = weather.getString("description");
                String weatherIcon = weather.getString("icon");

                JSONObject main = jsonObject.getJSONObject("main");
                double temp = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                JSONObject wind = jsonObject.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDegrees = wind.getInt("deg");

                JSONObject sys = jsonObject.getJSONObject("sys");
                String country = sys.getString("country");

                Date pDate = new Date(time * 1000);
                SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String date = jdf.format(pDate);

                String finalString = "Location: " + location + ", " + country;
                finalString += "\r\nWeather: " + weatherMain + " (" + weatherDesc + ")";
                finalString += "\r\nTemperature: " + String.format("%.1f", (temp - 273.15)) + " °C";
                finalString += "\r\nHumidity: " + humidity + "%";
                finalString += "\r\nWind Speed: " + windSpeed + "m/s (" + windDegrees + " degrees)";
                finalString += "\r\nLatest Update: " + date;

                GetImage getImage = new GetImage();

                Bitmap currentIcon = getImage.execute(ICONS_URL + weatherIcon + ".png").get();

                icon.setImageBitmap(currentIcon);

                if (!finalString.equals("")) {
                    textView.setText(finalString);

                    textView.setVisibility(View.VISIBLE);
                    icon.setVisibility(View.VISIBLE);


                    Toast.makeText(Weather.this, SUCCESS, Toast.LENGTH_SHORT).show();
                } else {
                    Weather.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Weather.this, ERROR_2, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(in);

                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityText = (EditText) findViewById(R.id.cityText);
        textView = (TextView) findViewById(R.id.textView);
        icon = (ImageView) findViewById(R.id.icons);
    }


    // Swipe screen back to mainpage
    float x1, x2, y1, y2;
    public boolean onTouchEvent(MotionEvent touchEvent) {

        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    Intent i = new Intent(Weather.this, MainActivity.class);
                    startActivity(i);
                }

                break;
        }
        return false;
    }
}
