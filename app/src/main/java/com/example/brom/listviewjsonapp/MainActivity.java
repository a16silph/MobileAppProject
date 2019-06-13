package com.example.brom.listviewjsonapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


// Create a new class, Mountain, that can hold your JSON data

// Create a ListView as in "Assignment 1 - Toast and ListView"

// Retrieve data from Internet service using AsyncTask and the included networking code

// Parse the retrieved JSON and update the ListView adapter

// Implement a "refresh" functionality using Android's menu system


public class MainActivity extends AppCompatActivity
{

    private ArrayList<Mountain> mountainList = new ArrayList<>();
    private ArrayAdapter<Mountain> mountainAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mountainAdapter = new ArrayAdapter<>(
                this, R.layout.list_item_textview,R.id.listItemTextView,mountainList);
        listView = findViewById(R.id.displayListView);
        listView.setAdapter(mountainAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //Adapter adapter = adapterView.getAdapter();

                //Mountain mountain = (Mountain)adapter.getItem(i);
                if(mountainList.get(i).GetName().equals("Black Panther"))
                {
                    Toast.makeText(getApplicationContext(),
                            mountainList.get(i).GetName() + " is a Marvel Hero from " +
                            mountainList.get(i).GetLocation() +
                            " and is 1.83" +
                            "m tall.",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            mountainList.get(i).GetName() + " is part of the " +
                            mountainList.get(i).GetLocation() +
                            " mountain range and is " +
                            mountainList.get(i).GetHeight() +
                            "m high.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        refresh();
    }

    public void refresh()
    {
        mountainList.clear();
        new FetchData().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.reload:
                Toast.makeText(getApplicationContext(),"Loading JSON data...",Toast.LENGTH_LONG).show();
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class FetchData extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            Log.e("SW", "Got into the async task");
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String jsonStr = null;

            try {
                // Construct the URL for the php-service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                Log.e("SW", "Got past the connect method");
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            // This code executes after we have received our data. The String object o holds
            // the un-parsed JSON string or is null if we had an IOException during the fetch.

            // Implement a parsing code that loops through the entire JSON and creates objects
            // of our newly created Mountain class.
            Log.e("SW", "Started on post execute with: " + o);
            try {
                JSONArray jsonArray = new JSONArray(o);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    // Ditt JSON-objekt som Java
                    JSONObject json1 = jsonArray.getJSONObject(i);
                    Log.d("SW","Got here!");
                    try
                    {
                        // När vi har ett JSONObjekt kan vi hämta ut dess beståndsdelar
                        String name = json1.getString("name");
                        int id = json1.getInt("ID");
                        String type = json1.getString("type");
                        String company = json1.getString("company");
                        String location = json1.getString("location");
                        String category =  json1.getString("category");
                        int size = json1.getInt("size");
                        int cost = json1.getInt("cost");
                        //JSONArray auxdata = json1.getJSONArray("auxdata");
                        Mountain mountain = new Mountain(name, location, size,type,id,size,cost,"", "");
                        mountainList.add(mountain);
                        //mountainAdapter.add(mountain);
                        Log.d("SW", "Added mountain: " + name);
                    }
                    catch (JSONException e)
                    {
                        Log.e("brom", "E:" + e.getMessage());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mountainAdapter.notifyDataSetChanged();

        }
    }
}

