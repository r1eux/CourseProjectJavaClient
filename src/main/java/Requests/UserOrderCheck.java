package Requests;

import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import Entities.Order;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import Enum.*;

public class UserOrderCheck {


    @FXML
    private Button logoutButton;



    @FXML
    private TilePane orderTilePane;
    // Метод загрузки заказов
    @FXML
    private void loadOrders() {
        Gson gson = new Gson();
        try {
            // Пример обращения к сервису для получения списка заказов
            GetService<Order> orderService = new GetService<>(Order.class);
            String ordersJson = orderService.GetEntities(TypeOfRequest.VIEW_ALL_ORDERS, "");

            if (ordersJson == null || ordersJson.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Заказов нет!");
                return;
            }

            // Парсим JSON в список объектов Order
            Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> orders = gson.fromJson(ordersJson, listType);

            System.out.println("Заказы успешно получены: " + orders);
            orderTilePane.getChildren().clear(); // Очищаем контейнер

            // Отображение заказов
            for (Order order : orders) {
                if (order.getUser().getUserId() == ClientSocket.getInstance().getUser().getUserId()) {
                    VBox orderBox = createOrderBox(order);
                    orderTilePane.getChildren().add(orderBox);
                }
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке заказов: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    private VBox createOrderBox(Order order) {
        VBox orderBox = new VBox();
        orderBox.setSpacing(5);
        orderBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        Label orderId = new Label("ID заказа: " + order.getOrderId());
        Label orderStatus = new Label("Статус: " + order.getStatus());
        Label shippingDate = new Label("Дата доставки: " + order.getShippingDate());
        Label orderDate = new Label("Дата заказа: " + order.getOrderDate());
        Label totalSum = new Label("Общая сумма: " + order.getTotalSum());


        orderBox.getChildren().addAll(orderId, orderStatus, shippingDate, orderDate, totalSum);

        return orderBox;
    }

    // Метод для перехода назад
    @FXML
    private void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuUser.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    // Инициализация
    @FXML
    public void initialize() {
        loadOrders(); // Загрузка заказов при инициализации
    }
}
