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
}
