package com.denzcoskun.steemtrackerandroid.screens.profile.fragments.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.screens.profile.fragments.models.CurrencyModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Denx on 24.02.2018.
 */

public class CurrencyAdapter extends BaseAdapter {

    @BindView(R.id.currency_image)
    ImageView currencyImage;

    @BindView(R.id.currency_type)
    TextView currencyType;

    @BindView(R.id.currency_text)
    TextView currencyText;

    @BindView(R.id.currency_value)
    TextView currencyValue;

    private LayoutInflater mInflater;
    private List<CurrencyModel> currencyModels;

    public CurrencyAdapter(Activity activity, List<CurrencyModel> currencyModels) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.currencyModels = currencyModels;
    }

    @Override
    public int getCount() {
        return currencyModels.size();
    }

    @Override
    public CurrencyModel getItem(int position) {
        return currencyModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = mInflater.inflate(R.layout.currency_list_row, null);
        ButterKnife.bind(this, view);

        final CurrencyModel currencyModel = currencyModels.get(position);

        currencyImage.setImageResource(currencyModel.getImage());

        currencyType.setText(currencyModel.getType());
        currencyText.setText(currencyModel.getText());
        currencyValue.setText(currencyModel.getValue());

        return view;
    }
}
