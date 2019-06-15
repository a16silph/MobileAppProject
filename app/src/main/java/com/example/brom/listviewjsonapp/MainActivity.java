package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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



public class MainActivity extends AppCompatActivity
{

    private ArrayList<IceCream> iceCreamList = new ArrayList<>();
    private ArrayAdapter<IceCream> iceCreamAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iceCreamAdapter = new ArrayAdapter<>(
                this, R.layout.list_item_textview,R.id.listItemTextView, iceCreamList);
        listView = findViewById(R.id.displayListView);
        listView.setAdapter(iceCreamAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.MyPrefsName), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(getString(R.string.savedIceCreamName), iceCreamList.get(i).GetName());
                editor.putInt(getString(R.string.savedIceCreamPrice), iceCreamList.get(i).GetPrice());
                editor.putInt(getString(R.string.savedIceCreamSize), iceCreamList.get(i).GetSize());
                editor.putInt(getString(R.string.savedIceCreamGrades), iceCreamList.get(i).GetGrades());
                editor.putInt(getString(R.string.savedIceCreamKidsGrades), iceCreamList.get(i).GetKidsGrades());
                editor.putString(getString(R.string.savedIceCreamTagline), iceCreamList.get(i).GetTagline());
                editor.putString(getString(R.string.savedIceCreamPros), iceCreamList.get(i).GetPros());
                editor.putString(getString(R.string.savedIceCreamCons), iceCreamList.get(i).GetCons());
                editor.putString(getString(R.string.savedIceCreamImage), iceCreamList.get(i).GetImagePath());
                editor.apply();
                sendMessage(view);

            }
        });
        refresh();
    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view)
    {


        // Do something in response to button
        Intent intent = new Intent(this, iceCreamDetails.class);
        //EditText editText = (EditText) findViewById(R.id.activityDetails);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    public void refresh()
    {
        iceCreamList.clear();
        new FetchData().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.reload:
                Toast.makeText(getApplicationContext(),
                        "Loading JSON data...",
                        Toast.LENGTH_LONG).show();
                refresh();
                return true;
            case R.id.about:
                /*Toast.makeText(getApplicationContext(),
                        "There should be some ice cream related about text here...",
                        Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(this, about.class);
                startActivity(intent);
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
                URL url = new URL("http://wwwlab.iit.his.se/a16silph/Programmering_Av_Mobila_Applikationer/glass.json");

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
            // of our newly created IceCream class.
            Log.e("SW", "Started on post execute with: " + o);
            try {
                JSONArray jsonArray = new JSONArray(o);
                Log.e("SW", "Got past the array!");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    // Ditt JSON-objekt som Java
                    JSONObject json1 = jsonArray.getJSONObject(i);
                    Log.e("SW", "Got past the object!");
                    try
                    {
                        // När vi har ett JSONObjekt kan vi hämta ut dess beståndsdelar
                        String name = "" + json1.opt("name");
                        int price = json1.getInt("price");
                        int size = json1.getInt("size");
                        int grades = json1.getInt("grades");
                        int kidsGrades = json1.getInt("kidsGrades");
                        String tagline = "" + json1.opt("tagline");
                        String pros = "" + json1.opt("pros");
                        String cons = "" + json1.opt("cons");
                        String imageLink = json1.getString("imageLink");
                        IceCream iceCream = new IceCream(name,
                                price, size,grades,
                                kidsGrades,tagline,pros,
                                cons, imageLink);
                        iceCreamList.add(iceCream);
                        //Log.d("SW", "Added iceCream: " + name);
                    }
                    catch (JSONException e)
                    {
                        Log.e("brom", "E:" + e.getMessage());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iceCreamAdapter.notifyDataSetChanged();

        }
    }
}

