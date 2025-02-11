package com.nasa.serviceNasaAPI.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManifestResponse {
    ManifestRoverInfo photo_manifest;

    public ManifestRoverInfo getPhoto_manifest() {
        return photo_manifest;
    }

    public void setPhoto_manifest(ManifestRoverInfo photo_manifest) {
        this.photo_manifest = photo_manifest;
    }

    @Override
    public String toString() {
        return photo_manifest.toString();
    }
}
