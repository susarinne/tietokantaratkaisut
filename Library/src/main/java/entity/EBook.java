package com.example.jpa.entity;

import jakarta.persistence.Entity;

@Entity
public class EBook extends Book {

    private String downloadUrl;
    
    public EBook() {}

    public EBook(String title, String downloadUrl) {
        super(title);
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    
}
