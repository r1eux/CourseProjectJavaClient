/*
package Pressed.User;

import ClientLogic.ClientSocket;
import ClientLogic.GetService;
import Entities.Product;
import Entities.User;
import ReqAndResp.Request;
import ReqAndResp.Response;
import by.curs.usercurs.HelloApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Enum.TypeOfRequest;
import javafx.stage.Stage;
import Enum.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DeleteUser {



    @FXML
    private Button logoutButton;
    @FXML
    private Label labelMessage;
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
    private TableColumn<User, String> userEmailColumn;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TextField usid;
    @FXML
    private Button delete;
    @FXML
    private Label labelMessageTrue;
    private User selectedUser;


    // Метод для загрузки пользователей и их отображения
    */
/*public void loadUsers() {
        try {
            // Устанавливаем фабрики для колонок
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("roleValue")); // Роль теперь отображается через метод getRoleName()
            userPersonNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
            userPersonPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("personPhoneNumber"));
            userSexColumn.setCellValueFactory(new PropertyValueFactory<>("sexValue"));
            userCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            userCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            userStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            userHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
            userPostIndexColumn.setCellValueFactory(new PropertyValueFactory<>("postIndex"));
            userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
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


    }*//*

    public void loadUsers() {
        try {
            // Устанавливаем фабрики для колонок
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("roleValue"));
            userPersonNameColumn.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
            userPersonPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("buyerPhoneNumber"));
            userSexColumn.setCellValueFactory(new PropertyValueFactory<>("sexValue"));
            userCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            userCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            userStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            userHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
            userPostIndexColumn.setCellValueFactory(new PropertyValueFactory<>("postIndex"));
            userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("buyerEmail"));

            System.out.println("Запрос списка пользователей...");
            GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.GET_ALL_USER, "");

            System.out.println("Ответ от сервера (JSON): " + usersJson);

            if (usersJson == null || usersJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(usersJson, listType);

            if (users == null || users.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список пользователей пуст.");
            }

            System.out.println("Пользователи успешно получены и распознаны: " + users);

            userTable.getItems().clear();
            userTable.getItems().addAll(users);

            // Добавляем функциональность двойного клика


        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void setupRowDoubleClick() {
        userTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    selectedUser = row.getItem();
                    userTable.getSelectionModel().select(row.getIndex());
                    System.out.println("Выбран пользователь: " + selectedUser);
                    // Здесь можно добавить действия с выбранным пользователем
                }
            });
            return row;
        });

        delete.setDisable(true); // Отключить кнопку, если ничего не выбрано
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            delete.setDisable(newSelection == null);
        });
    }



    public void DeleteUser(ActionEvent actionEvent)throws IOException{
        String id=String.valueOf(selectedUser.getUserId());
        Request requestFinal = new Request();

        requestFinal.setRequestMessage(new Gson().toJson(id));
        requestFinal.setRequestType(TypeOfRequest.DELETE_USER);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();



        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
        if (responseFinal.getResponseStatus() == ResponseStatus.ERROR) {
            labelMessage.setText("Такой пользователь не существует.");
            labelMessage.setVisible(true);
        }else{
            labelMessage.setVisible(false);
            labelMessageTrue.setText("Пользователь успешно удален");
            labelMessageTrue.setVisible(true);
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

import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import Entities.User;
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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Enum.TypeOfRequest;
import Enum.ResponseStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserDelete {

    @FXML
    private Button logoutButton;
    @FXML
    private Label labelMessage;
    @FXML
    private Label labelMessageTrue;
    @FXML
    private TilePane userTilePane; // TilePane для отображения пользователей
    @FXML
    private Button delete;

    private User selectedUser; // Выбранный пользователь

    public void loadUsers() {
        try {
            System.out.println("Запрос списка пользователей...");
            GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.GET_ALL_USER, "");

            System.out.println("Ответ от сервера (JSON): " + usersJson);

            if (usersJson == null || usersJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(usersJson, listType);

            if (users == null || users.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список пользователей пуст.");
            }

            System.out.println("Пользователи успешно получены и распознаны: " + users);

            userTilePane.getChildren().clear(); // Очищаем TilePane

            for (User user : users) {
                VBox userBox = createUserBox(user); // Создаем визуальный элемент для пользователя
                userTilePane.getChildren().add(userBox); // Добавляем в TilePane
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
        Label userId=new Label("ID: "+user.getUserId());
        Label userLogin=new Label("Логин: "+user.getLogin());
        Label userRole=new Label("Роль: "+user.getRole().getRoleValue());
        Label userSex=new Label("Пол:"+user.getBuyer().getSex().getSexValue() );
        Label userCountry=new Label("Страна: "+user.getBuyer().getAddress().getCountry());
        Label userStreet=new Label("Улица: "+user.getBuyer().getAddress().getStreet());
        Label userCity=new Label("Город:"+user.getBuyer().getAddress().getCity() );
        Label userHouseNumber=new Label("Номер дома:"+user.getBuyer().getAddress().getHouseNumber() );
        Label userPostIndex=new Label("Почтовый индекс:"+user.getBuyer().getAddress().getPostIndex() );
        Label userName = new Label("Имя: " + user.getBuyerName());
        Label userEmail = new Label("Email: " + user.getBuyerEmail());
        Label userPhone = new Label("Телефон: " + user.getBuyerPhoneNumber());
       /* ImageView userImage = new ImageView(new Image("path/to/default/image.png")); // Замените на путь к изображению пользователя
        userImage.setFitWidth(100);
        userImage.setFitHeight(100);*/

        userBox.getChildren().addAll(userId,userLogin,userRole,userSex,userCountry,userCity,userStreet,userHouseNumber,userPostIndex, userName, userEmail, userPhone);

        // Обработка двойного клика
        userBox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectedUser = user;
                System.out.println("Выбран пользователь: " + selectedUser);
                delete.setDisable(false); // Включить кнопку удаления
            }
        });

        return userBox;
    }

    @FXML
    public void DeleteUser(ActionEvent actionEvent) throws IOException {
        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Сначала выберите пользователяо");
            return;
        }

        String id = String.valueOf(selectedUser.getUserId());
        Request requestFinal = new Request();

        requestFinal.setRequestMessage(new Gson().toJson(id));
        requestFinal.setRequestType(TypeOfRequest.DELETE_USER);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();

        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);

        if (responseFinal.getResponseStatus() == ResponseStatus.ERROR) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Пользователь не был удален");
        } else {
            labelMessage.setVisible(false);
            showAlert(Alert.AlertType.INFORMATION, "Успех", "Пользователь удален успешно");
            loadUsers(); // Перезагрузка списка пользователей
        }
    }    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
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
        delete.setDisable(true); // Отключить кнопку удаления по умолчанию
    }
}
