package Requests;
import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import Entities.*;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Enum.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ProductEdit {
    public TextField textfieldProductName;     // Название товара
    public TextField textfieldPrice;          // Цена товара
    public TextField textfieldProductImageUrl; // URL изображения товара
    public TextField textfieldColor;          // Цвет товара
    public TextField textfieldProducer;       // Производитель товара
    public TextField textfieldProductId; // ID товара (опционально, для поиска и отображения)
    public TextField textfieldAmount;
    public TextField textfieldProducerCountry;
    public TextField textfieldWarranty;
    public TextField textfieldProductCategory;

    public Button buttonEdit;                 // Кнопка "Редактировать"
    public Button buttonBack;                 // Кнопка "Назад"
    public Button buttonFind;                 // Кнопка "Найти товар"

    public Label labelMessage;                // Сообщение об ошибке
    public Label labelMessageTrue;            // Сообщение об успешном редактировании

    public Product productMain;
    public Color colorMain;// Главный объект Product для редактирования
    public Producer producerMain;
    public Category categoryMain;
    private Product productFound;

    public void FindProduct(ActionEvent actionEvent) {
        if (textfieldProductId.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Данные не введены!");
        } else {

                labelMessage.setVisible(false); // Скрыть сообщение об ошибке
                // Инициализация необходимых объектов
                Gson gson = new Gson();
                Product productFound = new Product();

                // Формируем запрос для поиска товара
                String id = "id," + textfieldProductId.getText();
                GetService<Product> productService = new GetService<>(Product.class);

                // Получаем JSON с товарами
                String productsJson = productService.GetEntities(TypeOfRequest.FIND_PRODUCT_BY_ANYTHING, gson.toJson(id));
                Type listType = new TypeToken<List<Product>>() {
                }.getType();
                List<Product> products = gson.fromJson(productsJson, listType);

                // Получаем первый найденный товар
                if (!products.isEmpty()) {
                    productFound = products.get(0);
                    this.productMain = productFound;
                    this.colorMain = productFound.getColor();
                    this.producerMain = productFound.getProducer();
                    this.categoryMain = productFound.getCategory();

                    // Заполняем поля интерфейса данными товара
                    textfieldProductName.setText(productFound.getProdName());
                    textfieldPrice.setText(String.valueOf(productFound.getPrice()));
                    textfieldProductImageUrl.setText(productFound.getImage());
                    textfieldAmount.setText(String.valueOf(productFound.getAmount()));
                    textfieldWarranty.setText(String.valueOf(productFound.getWarranty()));
                    // Устанавливаем значение цвета
                    if (productFound.getColor() != null) {
                        textfieldColor.setText(productFound.getColor().getColor());
                    }

                    // Устанавливаем значение производителя
                    if (productFound.getProducer() != null) {
                        textfieldProducer.setText(productFound.getProducer().getProducer());
                        textfieldProducerCountry.setText(productFound.getProducer().getProducer_country());
                    }
                    if (productFound.getCategory() != null) {
                        textfieldProductCategory.setText(productFound.getCategory().getName());
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Найден", "Товар найден");
                } else {
                    // Если товар не найден
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Товар не найден");
                }
            }
        }

    public void Edit(ActionEvent actionEvent) throws IOException {
        Gson gson = new Gson();
        if (!textfieldProductImageUrl.getText().equals("") &&
                !textfieldProductName.getText().equals("") &&
                !textfieldPrice.getText().equals("") &&
                !textfieldColor.getText().equals("") &&
                !textfieldProducerCountry.getText().equals("") &&
                !textfieldProductCategory.getText().equals("") &&
                !textfieldWarranty.getText().equals("") &&
                !textfieldProducer.getText().equals("") &&
                !textfieldAmount.getText().equals("")) {
        // Создаем объект для редактируемого товара
        Product product1 = new Product();
        product1.setProductId(productMain.getProductId());  // используем текущий ID товара

        // Обновляем данные товара
        product1.setProdName(textfieldProductName.getText());
        product1.setPrice(Double.parseDouble(textfieldPrice.getText()));  // Цена товара
        product1.setImage(textfieldProductImageUrl.getText());  // URL изображения товара
        product1.setAmount(Integer.parseInt(textfieldAmount.getText()));
        product1.setWarranty(textfieldWarranty.getText());
        // Получаем и обновляем цвет товара
        Color color1 = new Color();
        color1.setColorId(colorMain.getColorId());
        color1.setColor(textfieldColor.getText());  // Предположим, что цвет вводится в текстовое поле
        product1.setColor(color1);

        // Получаем и обновляем производителя товара
        Producer producer1 = new Producer();
        producer1.setProducerId(producerMain.getProducerId());
        producer1.setProducer(textfieldProducer.getText());  // Производитель товара
        producer1.setProducer_country(textfieldProducerCountry.getText());
        product1.setProducer(producer1);

        Category category1 = new Category();
        category1.setName(textfieldProductCategory.getText());
        category1.setId(categoryMain.getId());
        product1.setCategory(category1);

            System.out.println(gson.toJson(product1));
        // Теперь создаем объект для отправки на сервер
        Request requestFinal = new Request();
        requestFinal.setRequestMessage(new Gson().toJson(product1));  // Преобразуем товар в JSON
        requestFinal.setRequestType(TypeOfRequest.EDIT_PRODUCT);  // Тип запроса для редактирования товара

        // Отправляем запрос на сервер
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();

        // Получаем ответ от сервера
        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);

        // Проверяем статус ответа от сервера
        if (responseFinal.getResponseStatus() == ResponseStatus.OK) {
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Товар был изменен");
            labelMessageTrue.setVisible(true);
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Произошла ошибка при изменении товара");
        }}else{showAlert(Alert.AlertType.ERROR, "Ошибка", "Одно из полей не заполнено");}
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }
}
