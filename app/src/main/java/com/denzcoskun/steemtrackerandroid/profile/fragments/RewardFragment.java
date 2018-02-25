package com.denzcoskun.steemtrackerandroid.profile.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.denzcoskun.steemtrackerandroid.AppSingleton;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.constants.CoinConstants;
import com.denzcoskun.steemtrackerandroid.constants.CurrencyConstants;
import com.denzcoskun.steemtrackerandroid.models.ConvertModel;
import com.denzcoskun.steemtrackerandroid.profile.ProfileActivity;
import com.denzcoskun.steemtrackerandroid.profile.fragments.adapters.CurrencyAdapter;
import com.denzcoskun.steemtrackerandroid.profile.fragments.models.CurrencyModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Denx on 21.02.2018.
 */

public class RewardFragment extends Fragment {

    @BindView(R.id.currency_list)
    ListView currencyList;

    ProgressDialog pDialog;
    List<CurrencyModel> currencyModels;
    CurrencyAdapter currencyAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currencyModels = new ArrayList<>();
        currencyModels.add(new CurrencyModel(R.drawable.sbd,
                getString(R.string.steem),
                "",
                ProfileActivity.balance));

        currencyModels.add(new CurrencyModel(R.drawable.sbd,
                getString(R.string.sbd),
                getString(R.string.sbd_text),
                ProfileActivity.sbdbalance));

        currencyAdapter = new CurrencyAdapter(getActivity(), currencyModels);
        currencyList.setAdapter(currencyAdapter);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);

        makeRequestConvert(ProfileActivity.sbdbalance, CurrencyConstants.DOLLAR);
        makeRequestConvert(ProfileActivity.sbdbalance, CurrencyConstants.LIRA);
        makeRequestConvert(ProfileActivity.sbdbalance, CurrencyConstants.EURO);
        makeRequestConvert(ProfileActivity.sbdbalance, CurrencyConstants.RUBLE);
        makeRequestConvert(ProfileActivity.sbdbalance, CurrencyConstants.KRWON);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reward_fragment, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void makeRequestConvert(String balance, String currency) {
        showpDialog();
        String dayUrl = getString(R.string.api_coin_market) + CoinConstants.STEEMDOLLAR + "/?convert=" + currency;
        JsonArrayRequest userRequest = new JsonArrayRequest
                (Request.Method.GET, dayUrl, null, response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JSONArray jsonArray = response;
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        ProfileActivity.convertModel = mapper.readValue(jsonObject.toString(), ConvertModel.class);
                        if (currency.equalsIgnoreCase(CurrencyConstants.LIRA)) {
                            currencyModels.add(new CurrencyModel(R.drawable.tury,
                                    getString(R.string.try_currency),
                                    getString(R.string.try_text),
                                    new DecimalFormat("##,##0.000").format(calculateAmount(balance, ProfileActivity.convertModel.priceTry))
                                            + " " + getString(R.string.try_symbol)));
                        } else if (currency.equalsIgnoreCase(CurrencyConstants.EURO)) {
                            currencyModels.add(new CurrencyModel(R.drawable.eur,
                                    getString(R.string.eur_currency),
                                    getString(R.string.eur_text),
                                    new DecimalFormat("##,##0.000").format(calculateAmount(balance, ProfileActivity.convertModel.priceEur))
                                            + " " + getString(R.string.eur_symbol)));
                        } else if (currency.equalsIgnoreCase(CurrencyConstants.RUBLE)) {
                            currencyModels.add(new CurrencyModel(R.drawable.rus,
                                    getString(R.string.rub_currency),
                                    getString(R.string.rub_text),
                                    new DecimalFormat("##,##0.000").format(calculateAmount(balance, ProfileActivity.convertModel.priceRub))
                                            + " " + getString(R.string.rub_symbol)));
                        } else if (currency.equalsIgnoreCase(CurrencyConstants.DOLLAR)) {
                            currencyModels.add(new CurrencyModel(R.drawable.usa,
                                    getString(R.string.usd_currency),
                                    getString(R.string.usd_text),
                                    new DecimalFormat("##,##0.000").format(calculateAmount(balance, ProfileActivity.convertModel.priceUsd))
                                     + " " + getString(R.string.usd_symbol)));
                        }else if (currency.equalsIgnoreCase(CurrencyConstants.KRWON)) {
                            currencyModels.add(new CurrencyModel(R.drawable.krw,
                                    getString(R.string.krw_currency),
                                    getString(R.string.krw_text),
                                    new DecimalFormat("##,##0.000").format(calculateAmount(balance, ProfileActivity.convertModel.priceKrw))
                                            + " " + getString(R.string.krw_symbol)));
                        }
                        currencyAdapter.notifyDataSetChanged();
                        if (currencyModels.size() == 7) {
                            hidepDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    System.out.println(error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(),
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

    public double calculateAmount(String balance, String value) {
        Double total = Double.parseDouble(balance.substring(0, 4)) * Double.parseDouble(value);
        return total;
    }
}
