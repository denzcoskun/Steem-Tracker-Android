package com.denzcoskun.steemtrackerandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.denzcoskun.steemtrackerandroid.models.ProfileModel;
import com.denzcoskun.steemtrackerandroid.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_username)
    EditText editTextUsername;

    @BindView(R.id.button_search)
    Button buttonSearch;

    ProgressDialog pDialog;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);

        buttonSearch.setOnClickListener(v -> makeRequestUser(editTextUsername.getText().toString()));
    }

    private void makeRequestUser(String username){
        showpDialog();
        String dayUrl = getString(R.string.steemjs_url)+"get_accounts?names[]="+username;
        JsonArrayRequest userRequest = new JsonArrayRequest
                (Request.Method.GET, dayUrl, null, response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JSONArray jsonArray = response;
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        userModel = mapper.readValue(jsonObject.toString(), UserModel.class);
                        JSONObject jsonMetadata = userModel.jsonMetadata;
                        JSONObject profile = jsonMetadata.getJSONObject("profile");
                        userModel.profileModel = mapper.readValue(profile.toString(), ProfileModel.class);
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("model",userModel.profileModel);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hidepDialog();
                }, error -> {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    System.out.println( error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    hidepDialog();
                });
        AppSingleton.getInstance().addToRequestQueue(userRequest);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
