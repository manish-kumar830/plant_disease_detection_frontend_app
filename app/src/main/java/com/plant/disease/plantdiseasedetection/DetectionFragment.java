package com.plant.disease.plantdiseasedetection;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetectionFragment extends Fragment {

    ImageView imageView;

    private String selectedImage="";

    LinearLayout detectButton;

    public static final int PICK_IMAGE = 1;

    ProgressDialog progressDialog;

    public DetectionFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detection, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Detection is in progress...");
        progressDialog.setTitle("Alert!");

        imageView = view.findViewById(R.id.imageView);
        detectButton = view.findViewById(R.id.detectButton);

        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedImage==""){
                    Toast.makeText(getContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                } else {
                    File file = new File(selectedImage);
                    uploadImage(file);
                }


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });


        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case PICK_IMAGE:
                    if (resultCode == RESULT_OK && data != null) {

                        Uri image = data.getData();
                        selectedImage = FileUtils.getPath(getContext(), image);
                        Picasso.get().load(image).into(imageView);
                    }
            }

        }
    }

    private void uploadImage(File file) {

        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://cc11-2409-4051-190-14f8-a5c1-e8ad-62ef-43e0.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApiCall retrofitAPI = retrofit.create(RetrofitApiCall.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestFile);
        Call<ResponseModel> call = retrofitAPI.getDiseaseName(body);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                progressDialog.dismiss();

                Intent intent = new Intent(getContext(),DetectionActivity.class);
                intent.putExtra("disease_name",response.body().getDiseaseName());
                intent.putExtra("description",response.body().getDescription());
                intent.putExtra("image_url",response.body().getImageUrl());
                intent.putExtra("prevent",response.body().getPrevent());
                intent.putExtra("supplement_buy_link",response.body().getSupplementBuyLink());
                intent.putExtra("supplement_image_url",response.body().getSupplementImageUrl());
                intent.putExtra("supplement_name",response.body().getSupplementName());

                getContext().startActivity(intent);

//                Intent intent = new Intent(getContext(),DetectionActivity.class);
//                intent.putExtra("disease_name","Apple Cedar apple rust");
//                intent.putExtra("description","Cedar apple rust (Gymnosporangium juniperi-virginianae) is a fungal disease that requires juniper plants to complete its complicated two year life-cycle. Spores overwinter as a reddish-brown gall on young twigs of various juniper species. In early spring, during wet weather, these galls swell and bright orange masses of spores are blown by the wind where they infect susceptible apple and crab-apple trees. The spores that develop on these trees will only infect junipers the following year. From year to year, the disease must pass from junipers to apples to junipers again; it cannot spread between apple trees.");
//                intent.putExtra("image_url","https://www.planetnatural.com/wp-content/uploads/2012/12/apple-rust.jpg");
//                intent.putExtra("prevent","Choose resistant cultivars when available.\\nRake up and dispose of fallen leaves and other debris from under trees.\\nRemove galls from infected junipers. In some cases, juniper plants should be removed entirely.\\nApply preventative, disease-fighting fungicides labeled for use on apples weekly, starting with bud break, to protect trees from spores being released by the juniper host. This occurs only once per year, so additional applications after this springtime spread are not necessary.\\nOn juniper, rust can be controlled by spraying plants with a copper solution (0.5 to 2.0 oz/ gallon of water) at least four times between late August and late October.\\nSafely treat most fungal and bacterial diseases with SERENADE Garden. This broad spectrum bio-fungicide uses a patented strain of Bacillus subtilis that is registered for organic use. Best of all, SERENADE is completely non-toxic to honey bees and beneficial insects.");
//                intent.putExtra("supplement_buy_link","https://agribegri.com/products/buy-organic-fungicide-all-in-1-online--organic-fungicide-.php");
//                intent.putExtra("supplement_image_url","https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcT2JQ-fAdtrzzmkSespqEpKwop3BnWntsLioVSgjy79mpxQVPSqoD4v9yfL0mtiFJvFnPqeE7EcefadhdEpc9uUTCZbROwOPsklL_XDMSLTpxOGvIcBMMFiBQ&usqp=CAE");
//                intent.putExtra("supplement_name","Katyayani All in 1 Organic Fungicide");
//
//                getContext().startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}