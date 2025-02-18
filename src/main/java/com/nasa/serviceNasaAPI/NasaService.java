package com.nasa.serviceNasaAPI;


import java.util.Optional;

public interface NasaService {

    Optional<?> constructResponse(String... options);

}
