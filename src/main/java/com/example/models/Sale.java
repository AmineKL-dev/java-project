package com.example.models;
public class Sale {
    private int idSale;
    private String date;
    private String productName;
    private String productCategory;
    private int quantity;
    private double price;
    private double totalPrice;

    // Constructor
    public Sale(int idSale, String date, String productName, String productCategory, int quantity, double price,
            double totalPrice) {
        this.idSale = idSale;
        this.date = date;
        this.productName = productName;
        this.productCategory = productCategory;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }
    public Sale() {
        // Default constructor
    }
    
    // Getters and Setters
    public int getIdSale() {
        return idSale;
    }
    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Sale [idSale=" + idSale + ", date=" + date + ", productName=" + productName + ", productCategory="
                + productCategory + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice
                + "]";
    }


}