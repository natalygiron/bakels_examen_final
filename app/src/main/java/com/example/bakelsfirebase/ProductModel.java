package com.example.bakelsfirebase;

public class ProductModel {

    String name, brand, category, imagen;
    int stock;
    double price;

    public ProductModel(String name, String brand, String category, String imagen, int stock, double price) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.imagen = imagen;
        this.stock = stock;
        this.price = price;
    }

    public ProductModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
