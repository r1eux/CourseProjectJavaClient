package Requests;

import SocketAndGetService.GetService;
import Entities.Product;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import Enum.*;

public class ProductCheck {
    @FXML
    private Button logoutButton;
    @FXML
    private TilePane productTilePane;
    @FXML
    private void loadProducts() {
        Gson gson = new Gson();
        try {
            GetService<Product> productService = new GetService<>(Product.class);
            String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS, "");

         if (productsJson == null || productsJson.isEmpty()) {
             showAlert(Alert.AlertType.ERROR, "Ошибка", "Товаров нет!");
             Stage stage = (Stage) logoutButton.getScene().getWindow();
             FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
             Parent root = fxmlLoader.load();
             Scene newScene = new Scene(root);
             stage.setScene(newScene);
        }
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = gson.fromJson(productsJson, listType);

            System.out.println("Товары успешно получены: " + products);
            productTilePane.getChildren().clear();

            // Отображение товаров
            for (Product product : products) {
                VBox productBox = createProductBox(product);
                productTilePane.getChildren().add(productBox);
            }
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке товаров: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox createProductBox(Product product) {
        VBox productBox = new VBox();
        productBox.setSpacing(5);
        productBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");
        Label productId = new Label("ID: " + product.getProductId());
        Label productName = new Label("Название: " + product.getProdName());
        Label productPrice = new Label("Цена: " + product.getPrice());
        Label productColor = new Label("Цвет: " + product.getColor().getColor());
        Label productProducer = new Label("Производитель: " + product.getProducer().getProducer());
        Label productAmount = new Label("Количество: " + product.getAmount());
        Label productCategory = new Label("Категория: " + product.getCategory().getName());
        Label productProducerCountry = new Label("Страна производителя: " + product.getProducer().getProducer_country());
        Label productWarranty = new Label("Гарантия: " + product.getWarranty());
        ImageView productImageView = new ImageView();
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            try {
                Image productImage = new Image(product.getImage(), 100, 100, true, true);
                productImageView.setImage(productImage);
            } catch (Exception e) {
                System.out.println("Ошибка загрузки изображения для товара ID: " + product.getProductId());
                productImageView.setImage(new Image(product.getImage())); // Изображение по умолчанию
            }
        } else {
            productImageView.setImage(new Image(product.getImage()));
        }
        productBox.getChildren().addAll(
                productImageView, productId, productName, productPrice, productColor,
                productProducer, productAmount, productCategory, productProducerCountry, productWarranty
        );

        return productBox;
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    // Инициализация
    @FXML
    public void initialize() {
        loadProducts(); // Загрузка товаров при инициализации
    }
}
