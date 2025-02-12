package com.nasa.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "nasa")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NasaConfig {

    String api_key;
    String main_api;
    String picture_of_the_day;
    String picture_of_the_day_random;
    String mars_rovers_manifest;
    String mars_photos_curiosity;




    public String getApi_key() {
        return api_key;
    }

    public String getMain_api() {
        return main_api;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public void setMain_api(String main_api) {
        this.main_api = main_api;
    }

    public String getPicture_of_the_day() {
        return picture_of_the_day;
    }

    public void setPicture_of_the_day(String picture_of_the_day) {
        this.picture_of_the_day = picture_of_the_day;
    }

    public String getPicture_of_the_day_random() {
        return picture_of_the_day_random;
    }

    public void setPicture_of_the_day_random(String picture_of_the_day_random) {
        this.picture_of_the_day_random = picture_of_the_day_random;
    }

    public String getMars_rovers_manifest() {
        return mars_rovers_manifest;
    }

    public void setMars_rovers_manifest(String mars_rovers_manifest) {
        this.mars_rovers_manifest = mars_rovers_manifest;
    }

    public String getMars_photos_curiosity() {
        return mars_photos_curiosity;
    }

    public void setMars_photos_curiosity(String mars_photos_curiosity) {
        this.mars_photos_curiosity = mars_photos_curiosity;
    }
}
