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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import Enum.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductSearch {
    @FXML
    private Button logoutButton;

    @FXML
    private TextField textfieldEnterSearch;


    @FXML
    private TilePane productTilePane; // Контейнер для отображения товаров в виде карточек

    @FXML
    private ComboBox<String> comboBoxSearchType; // Выпадающий список для выбора типа поиска

    // Методы для обработки действий
    @FXML
    private void Radio(ActionEvent actionEvent) {
        // Логика для переключения радио кнопок
    }

    @FXML
    private void loadProducts(ActionEvent actionEvent) {
        Gson gson = new Gson();
        try {
            System.out.println("Запрос списка товаров...");

            // Определение параметра поиска
            String selectedType = comboBoxSearchType.getValue();
            String text = "";

            switch (selectedType) {
                case "Название":
                    text = "name," + textfieldEnterSearch.getText();
                    break;
                case "Цена":
                    text = "price," + textfieldEnterSearch.getText();
                    break;
                case "Цвет":
                    text = "color," + textfieldEnterSearch.getText();
                    break;
                case "Производитель":
                    text = "producer," + textfieldEnterSearch.getText();
                    break;
                case "URL":
                    text = "url," + textfieldEnterSearch.getText();
                    break;
                case "ID":
                    text = "id," + textfieldEnterSearch.getText();
                    break;
                case "Количество":
                    text = "amount," + textfieldEnterSearch.getText();
                    break;
                case "Категория":
                    text = "category," + textfieldEnterSearch.getText();
                    break;
                case "Гарантия":
                    text = "warranty," + textfieldEnterSearch.getText();
                    break;
                case "Страна производителя":
                    text = "country," + textfieldEnterSearch.getText();
                    break;
            }

            // Запрос данных с сервера
            GetService<Product> productService = new GetService<>(Product.class);
            String productsJson = productService.GetEntities(TypeOfRequest.FIND_PRODUCT_BY_ANYTHING, gson.toJson(text));

            if (productsJson == null || productsJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            // Парсим JSON в список объектов Product
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = gson.fromJson(productsJson, listType);

            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список товаров пуст.");
            }

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


        // Добавление полей информации о товаре
        Label productId = new Label("ID: " + product.getProductId());
        Label productName = new Label("Название: " + product.getProdName());
        Label productPrice = new Label("Цена: " + product.getPrice());
        Label productColor = new Label("Цвет: " + product.getColor().getColor());
        Label productProducer = new Label("Производитель: " + product.getProducer().getProducer());
        Label productAmount = new Label("Количество: " + product.getAmount());
        Label productCategory = new Label("Категория: " + product.getCategory().getName());
        Label productProducerCountry = new Label("Страна производителя: " + product.getProducer().getProducer_country());
        Label productWarranty = new Label("Гарантия: " + product.getWarranty());

        // Добавление изображения товара
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

    // Обработчик двойного клика по товару
    // Инициализация комбобокса поиска
    @FXML
    public void initialize() {
        comboBoxSearchType.getItems().addAll(
                "Название", "Цена", "Цвет", "Производитель", "URL",
                "ID", "Количество", "Категория", "Гарантия", "Страна производителя"
        );
        comboBoxSearchType.setValue("Название");
    }


    @FXML
    private void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}

