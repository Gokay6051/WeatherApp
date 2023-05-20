package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View{
    private TextView city;
    private TextView temp;
    private TextView interval;
    private TextView weather;
    private ImageView image;

    private String lat="";
    private String lon="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.textViewCity);
        temp = findViewById(R.id.textViewTemp);
        interval = findViewById(R.id.textViewInterval);
        weather = findViewById(R.id.textViewWeather);
        image = findViewById(R.id.imageViewWeather);

        if(haveNetworkConnection())
        {
            getWeatherData();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "network_connection_error", Toast.LENGTH_SHORT).show();
        }
    }

    private void getWeatherData() {
        NetworkCall networkCall = new NetworkCall();
        networkCall.getWeatherData(this);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WIFI")) {
                if (networkInfo.isConnected()) {
                    haveConnectedWifi = true;
                }
            }
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (networkInfo.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void displayWeatherData(Example example) {
        String cityName = example.getName();
        int temperature = (int) (example.getMain().getTemp() - 272.15);
        String weatherDescription = example.getWeather().get(0).getDescription();

        int tempMin = (int) (example.getMain().getTempMin() - 272.15);
        int tempMax = (int) (example.getMain().getTempMax() - 272.15);
        String intervals = String.format("%d°C/%d°C", tempMin, tempMax);

        String iconUrl = example.getWeather().get(0).getIcon();
        String imageUrl = "https://openweathermap.org/img/w/" + iconUrl + ".png";


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                city.setText(cityName);
                temp.setText(String.valueOf(temperature)+"°C");
                weather.setText(weatherDescription);
                interval.setText(intervals);
                Picasso.get().load(imageUrl).into(image);
            }
        });
    }


}