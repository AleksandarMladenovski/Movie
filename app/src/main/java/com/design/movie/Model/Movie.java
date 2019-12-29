package com.design.movie.Model;

public class Movie {
    private String name;
    private String genre;
    private String imageUrl;
    private String description;

    public Movie(){
        name="";
        genre="";
        imageUrl="";
        description="";
    }
    public Movie(String name, String genre, String imageUrl, String description) {
        this.name = name;
        this.genre = genre;
        this.imageUrl=imageUrl;
        this.description=description;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
