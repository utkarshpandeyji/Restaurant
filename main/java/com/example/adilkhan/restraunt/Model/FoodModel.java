package com.example.adilkhan.restraunt.Model;

public class FoodModel {

   private String description, discount, image, menuId, name, price;

    public FoodModel() {}

    public FoodModel(String description, String discount, String image, String menuId, String name, String price) {
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "description='" + description + '\'' +
                ", discount='" + discount + '\'' +
                ", image='" + image + '\'' +
                ", menuId='" + menuId + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
