package Requests;

import SocketAndGetService.GetService;
import Entities.Product;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Enum.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDiagram {

    @FXML
    private Button buttonBack;

    @FXML
    private PieChart pieChart;

    private List<Product> productList;

    @FXML
    public void initialize() {
        loadStatistics();
    }

    private void loadStatistics() {
        // Получение данных из сервиса
        GetService<Product> productService = new GetService<>(Product.class);
        String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS, "");

        if (productsJson == null || productsJson.isEmpty()) {
            System.err.println("Получен пустой JSON от сервиса.");
            return;
        }

        // Парсим JSON в список объектов Product
        Gson gson = new Gson();
        Type productListType = new TypeToken<List<Product>>() {}.getType();
        productList = gson.fromJson(productsJson, productListType);

        if (productList == null || productList.isEmpty()) {
            System.err.println("Список товаров пуст.");
            return;
        }

        // Подсчитываем количество товаров по категориям
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Product product : productList) {
            String categoryName = product.getCategory() != null ? product.getCategory().getName() : "Unknown";
            categoryCounts.put(categoryName, categoryCounts.getOrDefault(categoryName, 0) + product.getAmount());
        }

        // Создаём данные для PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Статистика товаров по категориям");
    }

    @FXML
    public void BackCard_Pressed(ActionEvent actionEvent) throws IOException {
        // Переход на предыдущую сцену
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
