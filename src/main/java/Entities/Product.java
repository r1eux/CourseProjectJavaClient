package Entities;



import java.io.Serializable;


public class Product implements Serializable {

    private int productId;


    private String prodName;


    private double price;

    private String image;

    private Color color;
private int amount;

    private Producer producer;

    private Category category;

    private String warranty;

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }
    public String getWarranty() {
        return warranty;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Category getCategory() {
        return category;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Producer getProducer() {
        return producer;
    }

  public void setProducer(Producer producer) {
      this.producer = producer;
   }
//    public String getProductColor(){
//        return color.getColor();
//    }
//    public String getProductProducer(){
//        return producer.getProducer();
//    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", prodName='" + prodName + '\'' +
                ", price=" + price +
                ", picUrl='" + image + '\'' +
                ", color=" + (color != null ? color.toString() : "null") + // Выводим строковое представление цвета, если оно не null
                ", amount=" + amount +
                ", producer=" + (producer != null ? producer.toString() : "null") + // Выводим строковое представление производителя, если оно не null
                '}';
    }

}