package com.example.adilkhan.restraunt.Model;

public class Category_Model {

    String name, image;

    public Category_Model(){

    }

    public Category_Model(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category_Model{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
