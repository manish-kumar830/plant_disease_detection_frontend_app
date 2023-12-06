package com.plant.disease.plantdiseasedetection;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetectionActivity extends AppCompatActivity {

    ImageView image_view, supplement_image_view;
    TextView disease_name, description_view, precaution_view, supplement_name_view;
    LinearLayout buy_button;
    String[] precaution_list;
    Intent intent;
    String diseaseName, precaution, imageUrl, description, supplementName, supplementBuyLink, supplementImageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);


        image_view = findViewById(R.id.image_view);
        disease_name = findViewById(R.id.disease_name);
        description_view = findViewById(R.id.description_view);
        supplement_name_view = findViewById(R.id.supplement_name_view);
        precaution_view = findViewById(R.id.precaution_view);
        supplement_image_view = findViewById(R.id.supplement_image_view);
        buy_button = findViewById(R.id.buy_button);

        intent = getIntent();

        precaution = intent.getStringExtra("prevent");
        imageUrl = intent.getStringExtra("image_url");
        description = intent.getStringExtra("description");
        diseaseName = intent.getStringExtra("disease_name");
        supplementName = intent.getStringExtra("supplement_name");
        supplementBuyLink = intent.getStringExtra("supplement_buy_link");
        supplementImageLink = intent.getStringExtra("supplement_image_url");

        StringBuilder pr_str = new StringBuilder();

        precaution_list = precaution.split("\\\\n");

        int i = 1;

        for (String line : precaution_list) {
            pr_str.append(i + ". " + line + "\n\n");
            i++;
        }

        StringBuilder d_str = new StringBuilder();

        String[] d_list = description.split("\\n");

        int j = 1;

        for (String line : d_list) {
            d_str.append(j + ". " + line + "\n\n");
            j++;
        }

        precaution_view.setText(pr_str.toString());

        Picasso.get()
                .load(supplementImageLink)
                .into(supplement_image_view, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Image loaded successfully
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle error, for example, load a default image
                        image_view.setImageResource(R.drawable.not_available_supp);
                    }
                });

        Picasso.get()
                .load(imageUrl)
                .into(image_view, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Image loaded successfully
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle error, for example, load a default image
                        image_view.setImageResource(R.drawable.not_available);
                    }
                });
        description_view.setText(d_str.toString());
        supplement_name_view.setText(supplementName);
        disease_name.setText(diseaseName);



        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(supplementBuyLink);
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }


    }

    public void openUrl(String url){

        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(urlIntent);

    }


}