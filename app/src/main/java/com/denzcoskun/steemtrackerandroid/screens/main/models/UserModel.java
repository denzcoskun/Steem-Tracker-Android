package com.denzcoskun.steemtrackerandroid.screens.main.models;

import com.denzcoskun.libdenx.models.BaseResponseModel;
import com.denzcoskun.steemtrackerandroid.screens.profile.models.ProfileModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONObject;

/**
 * Created by Denx on 18.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel extends BaseResponseModel{

    @JsonProperty("name")
    public String name;

    @JsonProperty("sbd_balance")
    public String sbdBalance;

    @JsonProperty("balance")
    public String balance;

    @JsonProperty("voting_power")
    public String votingPower;

    @JsonProperty("json_metadata")
    public JSONObject jsonMetadata;

    public ProfileModel profileModel;


}
