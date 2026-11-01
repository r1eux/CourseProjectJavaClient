package Entities;




public class Category {


    private int categoryId;


    private String categoryName;

    // Геттеры и сеттеры
 public int getId() {
     return this.categoryId;
 }

    public void setId(int categoryId) {
        this.categoryId = categoryId;
    }

  public String getName() {
        return this.categoryName;
  }

    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
