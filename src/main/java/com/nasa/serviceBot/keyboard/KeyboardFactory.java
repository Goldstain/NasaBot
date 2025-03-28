package com.nasa.serviceBot.keyboard;

import com.nasa.serviceNasaAPI.dto.Camera;
import com.nasa.serviceNasaAPI.dto.ManifestResponseFull;
import com.nasa.serviceNasaAPI.dto.ManifestResponseRoverFullDate;
import com.nasa.serviceNasaAPI.impl.MarsRoverPhotos;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeyboardFactory {

    MarsRoverPhotos marsRoverPhotos;

    @Autowired
    public KeyboardFactory(MarsRoverPhotos marsRoverPhotos) {
        this.marsRoverPhotos = marsRoverPhotos;
    }

    public InlineKeyboardMarkup mainMenuButton() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton("🚀 Головне меню");
        mainMenuButton.setCallbackData("mainMenu");

        keyboardRows.add(List.of(mainMenuButton));

        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup mainMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton newsButton = new InlineKeyboardButton("\uD83D\uDEF0\uFE0F Останні новини NASA");
        newsButton.setCallbackData("news");

        List<InlineKeyboardButton> row1 = List.of(newsButton);


        InlineKeyboardButton photoButton = new InlineKeyboardButton("\uD83C\uDF0C Фото дня");
        photoButton.setCallbackData("photo");

        InlineKeyboardButton apodButton = new InlineKeyboardButton("\uD83C\uDF9E\uFE0F   AstroVideo  🔴");
        apodButton.setCallbackData("apodYoutube");
        apodButton.setUrl("https://www.youtube.com/@APODVideos/videos");

        List<InlineKeyboardButton> row2 = List.of(photoButton, apodButton);


        InlineKeyboardButton photoRandomButton = new InlineKeyboardButton("\uD83C\uDF0C  Випадкове фото дня");
        photoRandomButton.setCallbackData("photoRandom");

        List<InlineKeyboardButton> row3 = List.of(photoRandomButton);


        InlineKeyboardButton marsRoversPhotosButton = new InlineKeyboardButton("\uD83D\uDCF8 \uD83D\uDFE0  Фото з марсоходів");
        marsRoversPhotosButton.setCallbackData("marsRoversPhotos");

        List<InlineKeyboardButton> row4 = List.of(marsRoversPhotosButton);


        InlineKeyboardButton astroInfoButton = new InlineKeyboardButton("\uD83C\uDF04 Астрономічні дані");
        astroInfoButton.setCallbackData("astroInfo");

        List<InlineKeyboardButton> row5 = List.of(astroInfoButton);


        InlineKeyboardButton helpButton = new InlineKeyboardButton("ℹ️ Допомога");
        helpButton.setCallbackData("help");

        List<InlineKeyboardButton> row6 = List.of(helpButton);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        keyboardRows.add(row4);
        keyboardRows.add(row5);
        keyboardRows.add(row6);
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }


    public ReplyKeyboardMarkup astroInfoMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setInputFieldPlaceholder("-45.32 37.234");

        KeyboardButton getAstroInfoByIP = new KeyboardButton("Отримати по Вашому IP");
        getAstroInfoByIP.setRequestLocation(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(getAstroInfoByIP);


        KeyboardButton getAstroInfoByLocation = new KeyboardButton("Отримати по координатам");

        KeyboardRow row2 = new KeyboardRow();
        row2.add(getAstroInfoByLocation);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }


    public InlineKeyboardMarkup roversMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton("\uD83D\uDCFD\uFE0F Curiosity   \uD83C\uDF16");
        button1.setCallbackData("curiosity");
        List<InlineKeyboardButton> row1 = List.of(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton("\uD83C\uDFA5 Opportunity");
        button2.setCallbackData("opportunity");
        List<InlineKeyboardButton> row2 = List.of(button2);

        InlineKeyboardButton button3 = new InlineKeyboardButton("\uD83C\uDFA5 Spirit");
        button3.setCallbackData("spirit");
        List<InlineKeyboardButton> row3 = List.of(button3);

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        keyboardRows.add(mainMenuButton().getKeyboard().getFirst());
        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup returnToRoversMenu() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();

        InlineKeyboardButton returnToRoversMenuButton = new InlineKeyboardButton("◀\uFE0F  Назад");
        returnToRoversMenuButton.setCallbackData("marsRoversPhotos");

        keyboardRows.add(List.of(returnToRoversMenuButton));
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup roverPhotoButton(InlineKeyboardMarkup keyboardMarkup) {

        InlineKeyboardButton photoButton = new InlineKeyboardButton("\uD83D\uDCF8   Подивитись фото  \uD83D\uDC7D");
        photoButton.setCallbackData("curiosityPhotos");

        List<List<InlineKeyboardButton>> keyboard = keyboardMarkup.getKeyboard();
        keyboard.addFirst(List.of(photoButton));
        return keyboardMarkup;

    }

    public InlineKeyboardMarkup availableCamerasKeyboard(String date) {
        ManifestResponseFull manifestResponseFull = marsRoverPhotos.getManifestResponseFull();
        ManifestResponseRoverFullDate[] photos = manifestResponseFull.getPhoto_manifest().getPhotos();
        ManifestResponseRoverFullDate fullDate = Arrays.stream(photos)
                .filter(obj -> obj.getEarth_date().equals(date))
                .findFirst()
                .orElse(new ManifestResponseRoverFullDate());
        if (fullDate.getEarth_date() == null) return returnToRoversMenu();


        String[] cameras = fullDate.getCameras();
        if (cameras == null || cameras.length == 0) return returnToRoversMenu();

        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        for (String camera : cameras) {
            var button = new InlineKeyboardButton(findCameraDescription(camera));
            button.setCallbackData(camera.concat(":roverCamera"));
            keyboardRows.add(List.of(button));
        }
        keyboardRows.add(returnToRoversMenu().getKeyboard().getFirst());
        inlineKeyboardMarkup.setKeyboard(keyboardRows);
        return inlineKeyboardMarkup;
    }


    private String findCameraDescription(String camera) {
        Camera cameraName = Arrays.stream(Camera.values())
                .filter(cam -> cam.toString().equals(camera))
                .findFirst()
                .orElse(Camera.DEFAULT);
        var alias = cameraName.getAlias();
        if (alias.isEmpty() || alias.isBlank()) {
            return camera;
        } else {
            return camera.concat("  - ").concat(alias);
        }

    }
}
