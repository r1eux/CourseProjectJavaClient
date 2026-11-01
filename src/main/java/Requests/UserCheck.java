/*
package Pressed.User;

import ClientLogic.GetService;
import Entities.User;
import by.curs.usercurs.HelloApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Enum.TypeOfRequest;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ViewUser {


    @FXML
    private VBox movieContainer; // Контейнер для отображения пользователей

    @FXML
    private Button profileButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<User, Integer> userIdColumn;
    @FXML
    private TableColumn<User, String> userLoginColumn;
    @FXML
    private TableColumn<User, String> userRoleColumn;
    @FXML
    private TableColumn<User, String> userPersonNameColumn;
    @FXML
    private TableColumn<User, String> userPersonPhoneNumberColumn;
    @FXML
    private TableColumn<User, String> userSexColumn;
    @FXML
    private TableColumn<User, String> userCountryColumn;
    @FXML
    private TableColumn<User, String> userCityColumn;
    @FXML
    private TableColumn<User, String> userStreetColumn;
    @FXML
    private TableColumn<User, Integer> userHouseNumberColumn;
    @FXML
    private TableColumn<User, Integer> userPostIndexColumn;
    @FXML
    private TableView<User> userTable;

    // Метод для загрузки пользователей и их отображения
    public void loadUsers() {
        try {
            // Устанавливаем фабрики для колонок
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("roleValue")); // Роль теперь отображается через метод getRoleValue()
            userPersonNameColumn.setCellValueFactory(new PropertyValueFactory<>("personName"));
            userPersonPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("personPhoneNumber"));
            userSexColumn.setCellValueFactory(new PropertyValueFactory<>("sexValue"));
            userCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            userCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            userStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            userHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
            userPostIndexColumn.setCellValueFactory(new PropertyValueFactory<>("postIndex"));
            System.out.println("Запрос списка пользователей...");
            GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.GET_ALL_USER,"");

            System.out.println("Ответ от сервера (JSON): " + usersJson);

            // Проверяем, что JSON не пуст
            if (usersJson == null || usersJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            // Парсим JSON в список объектов User
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(usersJson, listType);

            if (users == null || users.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список пользователей пуст.");
            }

            System.out.println("Пользователи успешно получены и распознаны: " + users);

            // Очищаем таблицу перед добавлением новых элементов
            userTable.getItems().clear();

            // Добавляем пользователей в таблицу
            for (User user : users) {
                userTable.getItems().add(user);  // Добавляем в таблицу объект User
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }




    public void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }
    // Вызывается при инициализации сцены
    @FXML
    public void initialize() {
        loadUsers(); // Загрузка пользователей при инициализации
    }
}*/
package Requests;

import SocketAndGetService.GetService;
import Entities.User;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import Enum.TypeOfRequest;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserCheck {

    @FXML
    private TilePane userTilePane; // Контейнер для отображения пользователей

    @FXML
    private Button logoutButton;

    // Метод для загрузки пользователей и их отображения
    public void loadUsers() {
        try {
            System.out.println("Запрос списка пользователей...");
            GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.GET_ALL_USER, "");

            System.out.println("Ответ от сервера (JSON): " + usersJson);

            if (usersJson == null || usersJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            // Парсим JSON в список объектов User
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(usersJson, listType);

            if (users == null || users.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список пользователей пуст.");
            }

            System.out.println("Пользователи успешно получены и распознаны: " + users);

            // Очищаем TilePane перед добавлением новых элементов
            userTilePane.getChildren().clear();

            // Добавляем пользователей в TilePane
            for (User user : users) {
                VBox userBox = createUserBox(user);
                userTilePane.getChildren().add(userBox);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private VBox createUserBox(User user) {
        VBox userBox = new VBox();
        userBox.setSpacing(5);
        userBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        // Добавляем визуальные элементы
        Label userId = new Label("ID: " + user.getUserId());
        Label userLogin = new Label("Логин: " + user.getLogin());
        Label userRole = new Label("Роль: " + user.getRole().getRoleValue());
        Label userSex = new Label("Пол: " + user.getBuyer().getSex().getSexValue());
        Label userCountry = new Label("Страна: " + user.getBuyer().getAddress().getCountry());
        Label userStreet = new Label("Улица: " + user.getBuyer().getAddress().getStreet());
        Label userCity = new Label("Город: " + user.getBuyer().getAddress().getCity());
        Label userHouseNumber = new Label("Номер дома: " + user.getBuyer().getAddress().getHouseNumber());
        Label userPostIndex = new Label("Почтовый индекс: " + user.getBuyer().getAddress().getPostIndex());
        Label userName = new Label("Имя: " + user.getBuyerName());
        Label userEmail = new Label("Email: " + user.getBuyerEmail());
        Label userPhone = new Label("Телефон: " + user.getBuyerPhoneNumber());

        userBox.getChildren().addAll(
                userId, userLogin, userRole, userSex, userCountry, userCity,
                userStreet, userHouseNumber, userPostIndex, userName, userEmail, userPhone
        );

        return userBox;
    }

    public void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    public void initialize() {
        loadUsers(); // Загрузка пользователей при инициализации
    }
}
