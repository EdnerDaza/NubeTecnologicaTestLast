package com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities;

import java.io.Serializable;

public class DataList implements Serializable {
    public int id;
    public String name;
    public String iconURL;
    public String imageURL;
    public String textShortDescription;
    public String textDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTextShortDescription() {
        return textShortDescription;
    }

    public void setTextShortDescription(String textShortDescription) {
        this.textShortDescription = textShortDescription;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }
}
