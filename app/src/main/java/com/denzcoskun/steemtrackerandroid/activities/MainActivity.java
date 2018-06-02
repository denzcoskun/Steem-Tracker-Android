package com.denzcoskun.steemtrackerandroid.activities;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.denzcoskun.libdenx.activities.BaseActivity;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.helpers.DataHelper;
import com.denzcoskun.steemtrackerandroid.screens.profile.models.ProfileModel;
import com.denzcoskun.steemtrackerandroid.models.UserModel;
import com.denzcoskun.steemtrackerandroid.screens.profile.activities.ProfileActivity;
import com.denzcoskun.steemtrackerandroid.widget.SteemAppWidget;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.edit_text_username)
    EditText editTextUsername;

    @BindView(R.id.button_search)
    Button buttonSearch;

    ProgressDialog pDialog;
    UserModel userModel;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);

        buttonSearch.setOnClickListener(v -> getUserInfos(editTextUsername.getText().toString()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    public void getUserInfos(String username) {
        showpDialog();
        String dayUrl = getString(R.string.steemjs_url) + "get_accounts?names[]=" + username;
        getJsonArray(dayUrl, result -> {
            DataHelper dataHelper = new DataHelper(MainActivity.this);
            dataHelper.setModel(userModel);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JSONArray jsonArray = (JSONArray) result;
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                userModel = mapper.readValue(jsonObject.toString(), UserModel.class);
                if (userModel != null) {
                    Intent intent = new Intent(MainActivity.this, SteemAppWidget.class);
                    intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                    int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), SteemAppWidget.class));
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                    sendBroadcast(intent);
                    JSONObject jsonMetadata = userModel.jsonMetadata;
                    JSONObject profile = jsonMetadata.getJSONObject("profile");
                    userModel.profileModel = mapper.readValue(profile.toString(), ProfileModel.class);
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                    i.putExtra("model", userModel.profileModel);
                    i.putExtra("balance", userModel.balance);
                    i.putExtra("sbdBalance", userModel.sbdBalance);
                    startActivity(i);

                }
                hidepDialog();
            } catch (Exception e) {
                hidepDialog();
            }
        });
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
