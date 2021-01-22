package com.example.a1092apipost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Map<String, String>> list = new ArrayList<>();
    Button button;
    TextView account,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        account = findViewById(R.id.Account);
        password = findViewById(R.id.Password);
        API api = new API(MainActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.getAllTopicApi(account.getText().toString(),password.getText().toString());
            }
        });
    }
}

class API {
    Context context;
    API(Context context) {
        this.context = context;
    }
    String mUrl = "http://120.110.114.73:23080/api/login";

    public void getAllTopicApi(String account,String password) {

        Log.d(TAG, "loginInApi: "+"FUCK");
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "onlogin: "+response);
                    JSONObject object = new JSONObject(response);
                    String key = object.getString("success");
                    if (key.equals("true")) {
                        String data = object.getString("data");
                        JSONObject jsonObjectData = new JSONObject(data);
                        String id = jsonObjectData.getString("id");
                        String name = jsonObjectData.getString("name");
                        String email = jsonObjectData.getString("email");
                        String account = jsonObjectData.getString("account");
                        String registered_at = jsonObjectData.getString("registered_at");
                        String token = jsonObjectData.getString("token");
                        Log.d(TAG, "ttesttt: "+id);
                        Log.d(TAG, "ttesttt: "+name);
                        Log.d(TAG, "ttesttt: "+email);
                        Log.d(TAG, "ttesttt: "+account);
                        Log.d(TAG, "ttesttt: "+registered_at);
                        Log.d(TAG, "ttesttt: "+token);
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.d(TAG, "onResponse: "+"login");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onEMVKD "+"login");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("account", account);
                map.put("password", password);
                Log.d(TAG, "getParams: "+"login");
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+"" );
                Log.d(TAG, "getHeaders: "+"logi123n");
                return map;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }
}