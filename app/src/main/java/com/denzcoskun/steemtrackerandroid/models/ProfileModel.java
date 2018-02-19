package com.denzcoskun.steemtrackerandroid.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Denx on 18.02.2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileModel implements Serializable{

    @JsonProperty("name")
    public String name;

    @JsonProperty("profile_image")
    public String profileImage;

    @JsonProperty("cover_image")
    public String coverImage;

    @JsonProperty("about")
    public String about;

    @JsonProperty("location")
    public String location;

}
