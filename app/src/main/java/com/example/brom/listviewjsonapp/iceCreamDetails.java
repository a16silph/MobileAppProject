package com.example.brom.listviewjsonapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class iceCreamDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_cream_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.MyPrefsName), MODE_PRIVATE);
        String name = pref.getString(getString(R.string.savedIceCreamName), "Ice Cream!");
        int price = pref.getInt(getString(R.string.savedIceCreamPrice), 20);
        int size = pref.getInt(getString(R.string.savedIceCreamSize), 100);
        int grades = pref.getInt(getString(R.string.savedIceCreamGrades), 7);
        int kidsGrades = pref.getInt(getString(R.string.savedIceCreamKidsGrades), 2);
        String tagline = pref.getString(getString(R.string.savedIceCreamTagline), "Average ice cream that will take your money!");
        String pros = pref.getString(getString(R.string.savedIceCreamPros), "Doesn't constipate you!");
        String cons = pref.getString(getString(R.string.savedIceCreamCons), "Is too expensive for its taste.");
        String imagePath = pref.getString(getString(R.string.savedIceCreamImage), null);

        TextView nameView = findViewById(R.id.iceCreamName);
        TextView priceView = findViewById(R.id.iceCreamPrice);
        TextView sizeView = findViewById(R.id.iceCreamSize);
        TextView gradesView = findViewById(R.id.iceCreamGrades);
        TextView kidsGradesView = findViewById(R.id.iceCreamKidsGrades);
        TextView taglineView = findViewById(R.id.iceCreamTagline);
        TextView prosView = findViewById(R.id.iceCreamPros);
        TextView consView = findViewById(R.id.iceCreamCons);
        ImageView imageView = findViewById(R.id.imageView);

        nameView.setText(name);
        priceView.setText(price + " sek");
        sizeView.setText(size + " ml");
        gradesView.setText(grades + "/10 vuxenpoäng.");;
        kidsGradesView.setText(kidsGrades + "/10 barnvänlighet");
        taglineView.setText(tagline);
        prosView.setText("Pros: " + pros);
        consView.setText("Cons: " + cons);

        Picasso.get().load(imagePath).fit().into(imageView);
    }

}
