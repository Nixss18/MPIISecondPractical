package com.example.nearbyplacesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReqApiActivity extends AppCompatActivity {
    private String url2 = "https://axoltlapi.herokuapp.com/";
    private final String TAG = "MPI";
    private String url = "https://random-d.uk/api/list";
    ListView listView;
    ArrayList<JSONObject> apiData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiData = new ArrayList<>();
        getApiData();

        setContentView(R.layout.activity_req_api);
    }
    private void getApiData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        apiData.add(response);
                        TextView textView = findViewById(R.id.txtView);
                        textView.setText("Facts about Axolotls");
                        //Toast.makeText(getApplicationContext(), apiData.get(0).toString(), Toast.LENGTH_LONG).show();
                        listView = (ListView) findViewById(R.id.factView);
                        CustomBaseAdapter cbr = new CustomBaseAdapter(getApplicationContext(), apiData);
                        listView.setAdapter(cbr);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // do something on error
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.map){
            Intent intent = new Intent(ReqApiActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}