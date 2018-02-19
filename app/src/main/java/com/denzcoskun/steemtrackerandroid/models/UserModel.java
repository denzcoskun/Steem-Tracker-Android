package com.denzcoskun.steemtrackerandroid.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Denx on 18.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {

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
