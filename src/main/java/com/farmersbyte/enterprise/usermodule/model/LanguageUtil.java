package com.farmersbyte.enterprise.usermodule.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LanguageUtil {

    public String getValue(final Language languageObject, final String language) {
        String result = "";
        if(Objects.isNull(languageObject)) {
            return result;
        }
        switch(language) {
            case "te":
                result = languageObject.getTelugu();
                break;
            case "ta":
                result = languageObject.getTamil();
                break;
            case "ma":
                result = languageObject.getMalayalam();
                break;
            case "fr":
                result = languageObject.getFrench();
                break;
            case "ka":
                result = languageObject.getKannada();
                break;
            case "hi":
                result = languageObject.getHindi();
                break;
            default:
                result = languageObject.getEnglish();
        }
        return result;
    }
}
