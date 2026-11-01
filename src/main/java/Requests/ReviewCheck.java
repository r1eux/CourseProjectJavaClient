package Requests;
import Entities.Review;
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
public class ReviewCheck {



    @FXML
    private Button logoutButton;
    @FXML
    private VBox reviewTilePane;
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void loadReviews() {
        try {
            System.out.println("Запрос списка отзывов...");
            GetService<Review> reviewService = new GetService<>(Review.class);
            String reviewsJson = reviewService.GetEntities(TypeOfRequest.CHECK_ALL_REVIEWS, "");

            System.out.println("Ответ от сервера (JSON): " + reviewsJson);
            if (reviewsJson == null || reviewsJson.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Отзывов нет!");
                return;
            }

            // Парсим JSON в список объектов Review
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Review>>() {}.getType();
            List<Review> reviews = gson.fromJson(reviewsJson, listType);

            if (reviews == null || reviews.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список отзывов пуст.");
            }

            System.out.println("Отзывы успешно получены и распознаны: " + reviews);

            // Очищаем и отображаем отзывы в панели
            reviewTilePane.getChildren().clear();
            for (Review review : reviews) {
                VBox reviewBox = createReviewBox(review);
                reviewTilePane.getChildren().add(reviewBox);
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке отзывов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox createReviewBox(Review review) {
        VBox reviewBox = new VBox();
        reviewBox.setSpacing(5);
        reviewBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        // Получаем данные из review
        Label reviewText = new Label("Отзыв: " + review.getReviewData());
        Label reviewUser = new Label("Пользователь: " + review.getBuyer().getName()); // Имя покупателя
        Label reviewProduct = new Label("Товар: " + review.getProduct().getProdName()); // Название товара
        Label reviewId = new Label("ID отзыва: " + review.getReviewId()); // ID отзыва

        // Добавляем элементы в VBox
        reviewBox.getChildren().addAll(
                reviewId, reviewText, reviewUser, reviewProduct
        );

        return reviewBox;
    }
    @FXML
    public void initialize() {
        loadReviews();

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
