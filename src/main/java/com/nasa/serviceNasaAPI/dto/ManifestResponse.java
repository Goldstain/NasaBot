package com.nasa.serviceNasaAPI.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManifestResponse {

    ManifestResponseRover photo_manifest;


    public ManifestResponseRover getPhoto_manifest() {
        return photo_manifest;
    }

    public void setPhoto_manifest(ManifestResponseRover photo_manifest) {
        this.photo_manifest = photo_manifest;
    }

    @Override
    public String toString() {
        return photo_manifest.toString();
    }


    public static class ManifestResponseRover {

        String name;
        String landing_date;
        String launch_date;
        String status;
        int max_sol;
        String max_date;
        int total_photos;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLanding_date() {
            return landing_date;
        }

        public void setLanding_date(String landing_date) {
            this.landing_date = landing_date;
        }

        public String getLaunch_date() {
            return launch_date;
        }

        public void setLaunch_date(String launch_date) {
            this.launch_date = launch_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getMax_sol() {
            return max_sol;
        }

        public void setMax_sol(int max_sol) {
            this.max_sol = max_sol;
        }

        public String getMax_date() {
            return max_date;
        }

        public void setMax_date(String max_date) {
            this.max_date = max_date;
        }

        public int getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(int total_photos) {
            this.total_photos = total_photos;
        }

        @Override
        public String toString() {
            return "Назва: " + name +
                    "\nСтартував з Землі: " + launch_date +
                    "\nПрибув на Марс: " + landing_date +
                    "\nСтатус місії: " + status +
                    "\nПрацював марсіанських днів: " + max_sol +
                    "\nОстання дата отриманих знімків: " + max_date +
                    "\nВсього зроблено знімків: " + total_photos;
        }
    }

}
