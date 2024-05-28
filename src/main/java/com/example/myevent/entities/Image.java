package com.example.myevent.entities;

public class Image {
    private String url;
    private Offre offre_id;

    public Image(String url) {
        super();
        this.url = url;
        this.offre_id = offre_id;
    }


    public String getUrl() {
        return url;
    }
    public Offre getOffre_id() {
        return offre_id;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setOffre_id(Offre offre_id) {
        this.offre_id = offre_id;
    }
}

