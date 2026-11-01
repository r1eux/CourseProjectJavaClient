package Entities;




public class Review {


    private int reviewId;


    private String reviewData;


    private Product product;

    private Buyer buyer;

    // Конструктор без параметров


    // Геттеры и сеттеры
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewData() {
        return reviewData;
    }

    public void setReviewData(String reviewData) {
        this.reviewData = reviewData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    // Переопределение метода toString
    // Переопределение метода toString
    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", reviewData='" + reviewData + '\'' +
                ", product=" + product +
                ", buyer=" + buyer +
                '}';
    }}