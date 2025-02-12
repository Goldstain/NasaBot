package com.nasa.serviceNasaAPI.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManifestResponseFull {


    ManifestResponseRoverFull photo_manifest;


    public ManifestResponseRoverFull getPhoto_manifest() {
        return photo_manifest;
    }

    public void setPhoto_manifest(ManifestResponseRoverFull photo_manifest) {
        this.photo_manifest = photo_manifest;
    }
}
