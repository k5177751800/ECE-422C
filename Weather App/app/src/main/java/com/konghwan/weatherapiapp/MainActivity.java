package com.konghwan.weatherapiapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView[] days;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTextViews();

        requestQueue = Volley.newRequestQueue(this);
        apiWeatherData();
    }

    private void initializeTextViews() {
        days = new TextView[7];

        for (int i = 0; i < 7; i++) {
            int textViewId = getResources().getIdentifier("textViewDay" + (i + 1), "id", getPackageName());
            days[i] = findViewById(textViewId);
        }
    }

    private void apiWeatherData() {
        //String url = "https://api.open-meteo.com/v1/forecast?latitude=30.28&longitude=-97.7&hourly=temperature_2m,relative_humidity_2m,precipitation_probability&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch";

        String url = "https://api.open-meteo.com/v1/forecast?latitude=30.2672&longitude=-97.7431&hourly=temperature_2m,relative_humidity_2m,precipitation_probability,precipitation,rain,surface_pressure,visibility,wind_speed_10m&temperature_unit=fahrenheit&wind_speed_unit=mph&precipitation_unit=inch";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleWeatherResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleErrorResponse(error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void handleWeatherResponse(JSONObject response) {
        try {
            double[] avgTemp = AverageTemp(response);
            double[] avgHumid = AverageHumid(response);
            double[] expPercipitation = percipitationFetch(response);
            double[] expWind = averageWind(response);
            double[] expPressure = averagePressure(response);
            double[] expVisibility = averageVisibility(response);

            for (int i = 0; i < 7; i++) {
                long roundedTemp = Math.round(avgTemp[i]);

                int dayNumber = i + 1;
                days[i].setText(String.format("Days %d\nAverage Temperature: %d°F\nHumidity: %d%%\nRain Probability: %d%%\nWind: %d mph\nPressure: %d inHg\nVisibility: %d mi\n\n",
                        dayNumber, roundedTemp, Math.round(avgHumid[i]), Math.round(expPercipitation[i]), Math.round(expWind[i]), Math.round(expPressure[i]), Math.round(expVisibility[i])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleErrorResponse(VolleyError error) {
        error.printStackTrace();
        if (error.networkResponse != null) {
            Log.e("Weather App", "Error code: " + error.networkResponse.statusCode);
        }
        days[0].setText("Error pulling data: " + error.getMessage());
    }

    private double[] AverageTemp(JSONObject response) {
        try {
            double[] avgTemps = new double[7];
            JSONObject hour = response.getJSONObject("hourly");
            JSONArray temperatures = hour.getJSONArray("temperature_2m");

            int hoursPerDay = 24;

            for (int i = 0; i < temperatures.length(); i++) {
                int dayIndex = i / hoursPerDay;
                avgTemps[dayIndex] += temperatures.getDouble(i);
            }

            for (int j = 0; j < avgTemps.length; j++) {
                avgTemps[j] /= hoursPerDay;
            }

            return avgTemps;
        } catch (Exception e) {
            e.printStackTrace();
            return new double[7];
        }
    }

    private double[] AverageHumid(JSONObject response) throws JSONException {
        double[] avgHumid = new double[7];
        JSONObject hour = response.getJSONObject("hourly");
        JSONArray humidity = hour.getJSONArray("relative_humidity_2m");

        int hoursPerDay = 24;

        for (int i = 0; i < humidity.length(); i++) {
            int dayIndex = i / hoursPerDay;
            avgHumid[dayIndex] += humidity.getDouble(i) / hoursPerDay;
        }

        return avgHumid;
    }


    private double[] percipitationFetch(JSONObject response) throws JSONException {
        double[] expPercipitation = new double[7];
        JSONObject hour = response.getJSONObject("hourly");
        JSONArray precipitation = hour.getJSONArray("precipitation_probability");

        int hoursPerDay = 24;

        for (int i = 0; i < precipitation.length(); i++) {
            int dayIndex = i / hoursPerDay;
            expPercipitation[dayIndex] += precipitation.getDouble(i) / hoursPerDay;
        }

        return expPercipitation;
    }
    private double[] averageWind(JSONObject response) throws JSONException{
        double[] expWind = new double[7];
        JSONObject hour = response.getJSONObject("hourly");
        JSONArray wind = hour.getJSONArray("wind_speed_10m");
        int hourPerDay = 24;
        for(int i=0; i<wind.length(); i++){
            int dayIndex = i / hourPerDay;
            expWind[dayIndex] +=wind.getDouble(i) / hourPerDay;
        }
        return expWind;
    }

    private double[] averagePressure(JSONObject response) throws JSONException{
        double[] expPressure = new double[7];
        JSONObject hour = response.getJSONObject("hourly");
        JSONArray pressure = hour.getJSONArray("surface_pressure");
        int hoursPerDay = 24;
        // Conversion factor from hPa to inHg
        double hPaToInHg = 0.02953;

        for (int i = 0; i < pressure.length(); i++) {
            int dayIndex = i / hoursPerDay;
            expPressure[dayIndex] += pressure.getDouble(i) * hPaToInHg / hoursPerDay;
        }

        return expPressure;
    }
    private double[] averageVisibility(JSONObject response) throws JSONException{
        double[] expVisibility = new double[7];
        JSONObject hour = response.getJSONObject("hourly");
        JSONArray visibility = hour.getJSONArray("visibility");
        int hoursPerDay = 24;

        // Conversion factor from meters to miles
        double metersToMiles = 0.000621371;

        for (int i = 0; i < visibility.length(); i++) {
            int dayIndex = i / hoursPerDay;
            expVisibility[dayIndex] += visibility.getDouble(i) * metersToMiles / hoursPerDay;
        }

        return expVisibility;


    }
}


//click for listeners
//        btn_getCityId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            //volley request
//            public void onClick(View view) {
//                // Instantiate the RequestQueue.
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                String url = "https://api.open-meteo.com/v1/forecast?latitude=30.28&longitude=-97.76&hourly=temperature_2m&temperature_unit=fahrenheit";
//
//                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        System.out.println(response);
//                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }, new Response.ErrorListener() {
//                    //Catch
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                queue.add(request);
//
////// Request a string response from the provided URL.
////                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
////                        new Response.Listener<String>() {
////                            @Override
////                            public void onResponse(String response) {
////                             Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
////                            }
////                        }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
////
////                    }
////                });
//
//// Add the request to the RequestQueue.
//                //Toast.makeText(MainActivity.this, "you clicked me 1", Toast.LENGTH_SHORT).show(); //test
//            }
//        });


