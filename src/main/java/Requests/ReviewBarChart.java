package Requests;

import SocketAndGetService.GetService;
import Entities.Review;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Enum.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewBarChart {

    @FXML
    private Button buttonBack;

    @FXML
    private BarChart<String, Number> chart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    public void initialize() {
        categoryAxis.setLabel("Товары");
        numberAxis.setLabel("Количество отзывов");
        updateChart();
    }

    // Метод для обновления диаграммы
    private void updateChart() {
        // Загрузка данных о отзывах
        GetService<Review> reviewService = new GetService<>(Review.class);
        String reviewsJson = reviewService.GetEntities(TypeOfRequest.CHECK_ALL_REVIEWS, "");
        Gson gson = new Gson();
        Type reviewListType = new TypeToken<List<Review>>() {}.getType();
        List<Review> reviewList = gson.fromJson(reviewsJson, reviewListType);

        // Подсчёт количества отзывов для каждого товара
        Map<String, Integer> productReviewCount = new HashMap<>();

        for (Review review : reviewList) {
            String productName = review.getProduct() != null ? review.getProduct().getProdName() : "Unknown Product";
            productReviewCount.put(productName, productReviewCount.getOrDefault(productName, 0) + 1);
        }

        // Очистка графика перед добавлением данных
        chart.getData().clear();

        // Создание серии данных
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Количество отзывов на товары");

        for (Map.Entry<String, Integer> entry : productReviewCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        chart.getData().add(series);
    }

    public void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
