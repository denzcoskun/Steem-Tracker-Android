package com.denzcoskun.steemtrackerandroid.screens.profile.models;

import com.denzcoskun.libdenx.models.BaseResponseModel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Denx on 21.02.2018.
 */

public class ConvertModel extends BaseResponseModel{

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("rank")
    public String rank;

    @JsonProperty("price_usd")
    public String priceUsd;

    @JsonProperty("price_btc")
    public String priceBtc;

    @JsonProperty("24h_volume_usd")
    public String h24VolumeUsd;

    @JsonProperty("market_cap_usd")
    public String marketCapUsd;

    @JsonProperty("available_supply")
    public String availableSupply;

    @JsonProperty("total_supply")
    public String totalSupply;

    @JsonProperty("max_supply")
    public String maxSupply;

    @JsonProperty("percent_change_1h")
    public String percentChange1h;

    @JsonProperty("percent_change_24h")
    public String percentChange24h;

    @JsonProperty("percent_change_7d")
    public String percentChange7d;

    @JsonProperty("last_updated")
    public String lastUpdated;

    @JsonProperty("price_try")
    public String priceTry;

    @JsonProperty("24h_volume_try")
    public String h24VolumeTry;

    @JsonProperty("market_cap_try")
    public String marketCapTry;

    @JsonProperty("price_eur")
    public String priceEur;

    @JsonProperty("24h_volume_eur")
    public String h24VolumeEur;

    @JsonProperty("market_cap_eur")
    public String marketCapEur;

    @JsonProperty("price_rub")
    public String priceRub;

    @JsonProperty("24h_volume_rub")
    public String h24VolumeRub;

    @JsonProperty("market_cap_rub")
    public String marketCapRub;

    @JsonProperty("price_krw")
    public String priceKrw;

    @JsonProperty("24h_volume_krw")
    public String h24VolumeKrw;

    @JsonProperty("market_cap_krw")
    public String marketCapKrw;

}
