package com.nasa.serviceNasaAPI.dto;

public enum Camera {

    FHAZ("Передня камера"),
    RHAZ("Задня камера"),
    MAST("Щоглова камера"),
    CHEMCAM("Хімічно-камерний комплекс"),
    MAHLI("Ручний об'єктив сканер"),
    MARDI("Візуалізатор спуску"),
    NAVCAM("Навігаційна камера"),
    PANCAM("Панорамна камера"),
    MINITES("Тепловізійний спектрометр"),
    DEFAULT("");

    private String alias;

    private Camera(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

}
