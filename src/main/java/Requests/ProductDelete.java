package Requests;

import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import Entities.Product; // Используем модель Product
import DataTrade.Request;
import DataTrade.Response;
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
import Enum.TypeOfRequest;
import javafx.stage.Stage;
import Enum.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ProductDelete {

    @FXML
    private Button logoutButton;

  // Поле для ввода ID товара
    @FXML
    private Button deleteButton; // Кнопка для удаления
    @FXML



    private TilePane productTilePane; // Контейнер для отображения товаров
        private Product selectedProduct;
    // Метод для загрузки и отображения товаров
    public void loadProducts() {
        try {
            System.out.println("Запрос списка товаров...");
            GetService<Product> productService = new GetService<>(Product.class);
            String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS, "");

            System.out.println("Ответ от сервера (JSON): " + productsJson);
            if (productsJson == null || productsJson.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Товаров нет!");
                backPressed(null);
            }

            // Парсим JSON в список объектов Product
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = gson.fromJson(productsJson, listType);

            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список товаров пуст.");
            }

            System.out.println("Товары успешно получены и распознаны: " + products);

            // Очищаем TilePane перед добавлением новых элементов
            productTilePane.getChildren().clear();

            // Добавляем товары в TilePane
            for (Product product : products) {
                VBox productBox = createProductBox(product);
                productTilePane.getChildren().add(productBox);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке товаров: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Создание бокса для отображения информации о продукте
    private VBox createProductBox(Product product) {
        VBox productBox = new VBox();
        productBox.setSpacing(5);
        productBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        // Добавляем визуальные элементы
        Label productId = new Label("ID: " + product.getProductId());
        Label productName = new Label("Название: " + product.getProdName());
        Label productPrice = new Label("Цена: " + product.getPrice());
        Label productColor = new Label("Цвет: " + product.getColor().getColor());
        Label productProducer = new Label("Производитель: " + product.getProducer().getProducer());
        Label productAmount = new Label("Количество: " + product.getAmount());
        Label productCategory = new Label("Категория: " + product.getCategory().getName());
        Label productProducerCountry = new Label("Страна производителя: " + product.getProducer().getProducer_country());
        Label productWarranty = new Label("Гарантия: " + product.getWarranty());


        // Создаём ImageView для отображения изображения товара
        ImageView productImageView = new ImageView();
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            try {
                Image productImage = new Image(product.getImage(), 100, 100, true, true); // Размер изображения 100x100
                productImageView.setImage(productImage);
            } catch (Exception e) {
                System.out.println("Ошибка загрузки изображения для товара ID: " + product.getProductId());
                productImageView.setImage(new Image(product.getImage())); // Указать путь к изображению по умолчанию
            }
        } else {
            productImageView.setImage(new Image(product.getImage())); // Указать путь к изображению по умолчанию
        }

        productBox.getChildren().addAll(
                productImageView, productId, productName, productPrice, productColor,
                productProducer, productAmount, productCategory, productProducerCountry, productWarranty
        );
        productBox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectedProduct = product;
                System.out.println("Выбран товар: " + selectedProduct);
                deleteButton.setDisable(false); // Включить кнопку удаления
            }
        });
        return productBox;
    }

    // Обработчик двойного нажатия на товар


    @FXML
    public void initialize() {
        loadProducts(); // Загрузка товаров при инициализации
    }

    // Метод для удаления товара
    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        String productId =String.valueOf(selectedProduct.getProductId()) ;
        Request requestFinal = new Request();

        requestFinal.setRequestMessage(new Gson().toJson(productId)); // Отправляем ID товара
        requestFinal.setRequestType(TypeOfRequest.DELETE_PRODUCT); // Тип запроса на удаление товара

        // Отправка запроса на сервер
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();

        // Получение ответа от сервера
        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);

        if (responseFinal.getResponseStatus() == ResponseStatus.ERROR) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не существует товара с таким ID");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Товар удален успешно");
            loadProducts(); // Перезагружаем список товаров после удаления
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Метод для возвращения на предыдущую сцену
    public void backPressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    // Вызывается при инициализации сцены

}
