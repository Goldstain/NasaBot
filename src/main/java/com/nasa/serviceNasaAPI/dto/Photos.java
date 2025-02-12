package com.nasa.serviceNasaAPI.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Photos {

    PhotosByDateCamera[] photos;

    public PhotosByDateCamera[] getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosByDateCamera[] photos) {
        this.photos = photos;
    }
}
