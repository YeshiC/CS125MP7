package doge.org.cs125mp7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainMenu extends AppCompatActivity {

    private static String json;

    private static final String TAG = "MP7";

    /**
     * Request queue for our network requests.
     */
    private static RequestQueue requestQueue;

    String provider;
    //Location location;
    String locationString = "";
    //double longitude;
//double latitude;
    public static String cityname = "Champaign";
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        requestQueue = Volley.newRequestQueue(this);


        final Button startAPICall = findViewById(R.id.button);
        //LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "OPPS");
//            return;
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        longitude = location.getLongitude();
//        latitude = location.getLatitude();
        startAPICall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Start API button2 clicked");
                startAPICall();


            }
        });

    }


    void startAPICall() {
        try {


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=b0afd3047fb4468bae6220328182504&q=" + cityname + "&num_of_days=2&tp=3&format=json",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            json = response.toString();
                            Log.d(TAG, response.toString());
                            json = response.toString();
                            String json = response.toString();
                            JsonParser parser = new JsonParser();
                            JsonObject result = parser.parse(json).getAsJsonObject();
                            JsonObject data = result.getAsJsonObject("data");
                            JsonArray condition = data.getAsJsonArray("current_condition");
                            JsonObject ob = condition.get(0).getAsJsonObject();
                            temp = ob.get("temp_F").getAsInt();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(this, Dog.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public int getTemp(){
        return temp;
    }

}
