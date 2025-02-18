package com.nasa.serviceNasaAPI.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AstroInfoDTO {

    Location location;
    String date;
    String current_time;
    String sunrise;
    String sunset;
    String solar_noon;
    String day_length;
    String sun_altitude;
    String sun_distance;
    String sun_azimuth;
    String moonrise;
    String moonset;
    String moon_altitude;
    String moon_distance;
    String moon_azimuth;
    String moon_illumination_percentage;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSolar_noon() {
        return solar_noon;
    }

    public void setSolar_noon(String solar_noon) {
        this.solar_noon = solar_noon;
    }

    public String getDay_length() {
        return day_length;
    }

    public void setDay_length(String day_length) {
        this.day_length = day_length;
    }

    public String getSun_altitude() {
        return sun_altitude;
    }

    public void setSun_altitude(String sun_altitude) {
        this.sun_altitude = sun_altitude;
    }

    public String getSun_distance() {
        return sun_distance;
    }

    public void setSun_distance(String sun_distance) {
        this.sun_distance = sun_distance;
    }

    public String getSun_azimuth() {
        return sun_azimuth;
    }

    public void setSun_azimuth(String sun_azimuth) {
        this.sun_azimuth = sun_azimuth;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoon_altitude() {
        return moon_altitude;
    }

    public void setMoon_altitude(String moon_altitude) {
        this.moon_altitude = moon_altitude;
    }

    public String getMoon_distance() {
        return moon_distance;
    }

    public void setMoon_distance(String moon_distance) {
        this.moon_distance = moon_distance;
    }

    public String getMoon_azimuth() {
        return moon_azimuth;
    }

    public void setMoon_azimuth(String moon_azimuth) {
        this.moon_azimuth = moon_azimuth;
    }

    public String getMoon_illumination_percentage() {
        return moon_illumination_percentage;
    }

    public void setMoon_illumination_percentage(String moon_illumination_percentage) {
        this.moon_illumination_percentage = moon_illumination_percentage;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(location.toString());
        return result
                .append("\nДата: " + date)
                .append("\nПоточний час: " + current_time.substring(0, current_time.indexOf('.')))
                .append("\nСхід сонця: " + sunrise)
                .append("\nЗахід сонця " + sunset)
                .append("\nПолудень: " + solar_noon)
                .append("\nТривалість дня: " + day_length)
                .append("\nВисота сонця над горизонтом: " + sun_altitude.substring(0, sun_altitude.indexOf('.')) + "\u00B0")
                .append("\nВідстань до сонця: " + sun_distance.substring(0, sun_distance.indexOf('.')) + " км")
                .append("\nАзимут сонця: " + sun_azimuth.substring(0, sun_azimuth.indexOf('.')) + "\u00B0")
                .append("\nСхід місяця: " + moonrise)
                .append("\nЗахід місяця: " + moonset)
                .append("\nВисота місяця над горизонтом: " + moon_altitude.substring(0, moon_altitude.indexOf('.')) + "\u00B0")
                .append("\nВідстань до місяця: " + moon_distance.substring(0, moon_distance.indexOf('.')) + " км")
                .append("\nАзимут місяця: " + moon_azimuth.substring(0, moon_azimuth.indexOf('.')) + "\u00B0")
                .append("\nВідсоток освітлення місяця: " + moon_illumination_percentage + " %")
                .toString();

    }
}
