package com.denzcoskun.steemtrackerandroid.screens.profile.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.denzcoskun.libdenx.fragments.BaseFragment;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.constants.CoinConstants;
import com.denzcoskun.steemtrackerandroid.constants.CurrencyConstants;
import com.denzcoskun.steemtrackerandroid.screens.profile.models.ConvertModel;
import com.denzcoskun.steemtrackerandroid.screens.profile.activities.ProfileActivity;
import com.denzcoskun.steemtrackerandroid.screens.profile.fragments.adapters.CurrencyAdapter;
import com.denzcoskun.steemtrackerandroid.screens.profile.fragments.models.CurrencyModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Denx on 21.02.2018.
 */

public class RewardFragment extends BaseFragment {

    @BindView(R.id.currency_list)
    ListView currencyList;

    ProgressDialog pDialog;
    List<CurrencyModel> currencyModels;
    CurrencyAdapter currencyAdapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);

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

        getUserBalances(ProfileActivity.sbdbalance, CurrencyConstants.DOLLAR);
        getUserBalances(ProfileActivity.sbdbalance, CurrencyConstants.LIRA);
        getUserBalances(ProfileActivity.sbdbalance, CurrencyConstants.EURO);
        getUserBalances(ProfileActivity.sbdbalance, CurrencyConstants.RUBLE);
        getUserBalances(ProfileActivity.sbdbalance, CurrencyConstants.KRWON);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.reward_fragment;
    }

    public void getUserBalances(String balance, String currency) {
        showpDialog();
        String dayUrl = getString(R.string.api_coin_market) + CoinConstants.STEEMDOLLAR + "/?convert=" + currency;
        ObjectMapper mapper = new ObjectMapper();
        getJsonArray(dayUrl, result -> {
            try {
                JSONArray jsonArray = (JSONArray) result;
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
                } else if (currency.equalsIgnoreCase(CurrencyConstants.KRWON)) {
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

    public double calculateAmount(String balance, String value) {
        return Double.parseDouble(balance.substring(0, 4)) * Double.parseDouble(value);
    }
}
