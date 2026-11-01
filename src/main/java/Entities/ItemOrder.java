package Entities;


import java.io.Serializable;


public class ItemOrder implements Serializable {

    private int orderItemId;


    private Order order;


    private Product product;


    private int amount;


    private double pricePurchase;

    // Getters and Setters
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPricePurchase() {
        return pricePurchase;
    }

    public void setPricePurchase(double pricePurchase) {
        this.pricePurchase = pricePurchase;
    }
    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", order=" + (order != null ? order.toString() : "null") + // Убедитесь, что 'order' не равен null
                ", product=" + (product != null ? product.toString() : "null") + // Убедитесь, что 'product' не равен null
                ", amount=" + amount +
                ", pricePurchase=" + pricePurchase +
                '}';
    }
}
