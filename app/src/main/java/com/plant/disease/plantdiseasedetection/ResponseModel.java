package com.plant.disease.plantdiseasedetection;

public class ResponseModel {

    String disease_name, description, prevent, image_url, supplement_name, supplement_image_url, supplement_buy_link;

    public ResponseModel(String disease_name, String description, String prevent, String image_url, String supplement_name, String supplement_image_url, String supplement_buy_link) {
        this.disease_name = disease_name;
        this.description = description;
        this.prevent = prevent;
        this.image_url = image_url;
        this.supplement_name = supplement_name;
        this.supplement_image_url = supplement_image_url;
        this.supplement_buy_link = supplement_buy_link;
    }

    public String getDiseaseName() {
        return disease_name;
    }

    public void setDiseaseName(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrevent() {
        return prevent;
    }

    public void setPrevent(String prevent) {
        this.prevent = prevent;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getSupplementName() {
        return supplement_name;
    }

    public void setSupplementName(String supplement_name) {
        this.supplement_name = supplement_name;
    }

    public String getSupplementImageUrl() {
        return supplement_image_url;
    }

    public void getSupplementImageUrl(String supplement_image_url) {
        this.supplement_image_url = supplement_image_url;
    }

    public String getSupplementBuyLink() {
        return supplement_buy_link;
    }

    public void getSupplementBuyLink(String supplement_buy_link) {
        this.supplement_buy_link = supplement_buy_link;
    }
}
