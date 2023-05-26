package com.example.weatherapp;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkCall {

    private String lat="39.933365";
    private String lon="32.859741";
    private String id="----------------";


    public void getWeatherData(View view) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        //RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat="+ lat + "&lon="+ lon + "&appid=" + id)
                .addHeader("accept", "application/json")
                .method("GET", null)
                .build();
        //Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, java.io.IOException e) {
                System.out.println("Hatalı işlem");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws java.io.IOException {
                if(response.isSuccessful())
                {
                    assert response.body() != null;
                    final String json = response.body().string();
                    Example example= new com.google.gson.Gson().fromJson(json, Example.class);
                    view.displayWeatherData(example);
                }
            }
        });
    }


}
