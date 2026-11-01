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
import javafx.scene.control.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import Enum.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserSearch {
    @FXML
    private ComboBox<String> comboBoxSearchType;
    @FXML
    private TextField textfieldEnterSearch;
    @FXML
    private TilePane userTilePane;
    @FXML
    private Button logoutButton;

    private User selectedUser; // Для хранения выбранного пользователя



    public void Radio(ActionEvent actionEvent){
//        if(radiobuttonLogin.isSelected() || radiobuttonPhone.isSelected()){
//            textfieldEnterSearch.setEditable(true);
//            }
//        if(radiobuttonLogin.isSelected()){
//            radiobuttonPhone.setVisible(false);
//            textfieldEnterSearch.setEditable(true);
//        }
//        if(radiobuttonPhone.isSelected()){
//            radiobuttonLogin.setVisible(true);
//            textfieldEnterSearch.setEditable(true);
//        }if(!radiobuttonLogin.isSelected()){
//            radiobuttonPhone.setVisible(true);
//        }if(!radiobuttonPhone.isSelected()){
//            radiobuttonLogin.setVisible(false);
//        }
    }
   /* public void loadUsers(ActionEvent actionEvent) {


        Gson gson = new Gson();
            try {
            // Устанавливаем фабрики для колонок
            userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
            userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
            userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("roleValue")); // Роль теперь отображается через метод getRoleName()
            userPersonNameColumn.setCellValueFactory(new PropertyValueFactory<>("personName"));
            userPersonPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("personPhoneNumber"));
            userSexColumn.setCellValueFactory(new PropertyValueFactory<>("sexValue"));
            userCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            userCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
            userStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
            userHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("houseNumber"));
            userPostIndexColumn.setCellValueFactory(new PropertyValueFactory<>("postIndex"));

            System.out.println("Запрос списка пользователей...");
            String text="";
            if(rbLogin.isSelected()){
                 text="login,"+textfieldEnterSearch.getText();
            }
            if(radiobuttonPhone.isSelected()){
                text="phone,"+textfieldEnterSearch.getText();
            }if(rbCountry.isSelected()){
                    text="country,"+textfieldEnterSearch.getText();
            }
            if(rbSex.isSelected()){
                    text="sex,"+textfieldEnterSearch.getText();
            }
                if(rbID.isSelected()){
                    text="id,"+textfieldEnterSearch.getText();
                }
                if(rbCity.isSelected()){
                    text="city,"+textfieldEnterSearch.getText();
                }
                if(rbPost.isSelected()){
                    text="post,"+textfieldEnterSearch.getText();
                }
                if(rbName.isSelected()){
                    text="name,"+textfieldEnterSearch.getText();
                }
                if(rbRole.isSelected()){
                    text="role,"+textfieldEnterSearch.getText();
                }
                if(rbStreet.isSelected()){
                    text="street,"+textfieldEnterSearch.getText();
                }
                if(rbHome.isSelected()){
                    text="home,"+textfieldEnterSearch.getText();
                }



                GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.FIND_USER_BY_ANYTHING,gson.toJson(text));

            System.out.println("Ответ от сервера (JSON): " + usersJson);

            // Проверяем, что JSON не пуст
            if (usersJson == null || usersJson.isEmpty()) {
                throw new RuntimeException("Получен пустой ответ от сервера.");
            }

            // Парсим JSON в список объектов User

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

    }*/
   @FXML
   public void loadUsers(ActionEvent actionEvent) {
       try {
           System.out.println("Запрос списка пользователей...");
           GetService<User> userService = new GetService<>(User.class);

           // Определение параметра поиска
           String selectedType = comboBoxSearchType.getValue();
           String text = "";

           switch (selectedType) {
               case "Логин":
                   text = "login," + textfieldEnterSearch.getText();
                   break;
               case "Телефон":
                   text = "phone," + textfieldEnterSearch.getText();
                   break;
               case "Пол":
                   text = "sex," + textfieldEnterSearch.getText();
                   break;
               case "ID":
                   text = "id," + textfieldEnterSearch.getText();
                   break;
               case "Страна":
                   text = "country," + textfieldEnterSearch.getText();
                   break;
               case "Город":
                   text = "city," + textfieldEnterSearch.getText();
                   break;
               case "Имя":
                   text = "name," + textfieldEnterSearch.getText();
                   break;
               case "Роль":
                   text = "role," + textfieldEnterSearch.getText();
                   break;
               case "Улица":
                   text = "street," + textfieldEnterSearch.getText();
                   break;
               case "Дом":
                   text = "home," + textfieldEnterSearch.getText();
                   break;
               case "Индекс":
                   text = "post," + textfieldEnterSearch.getText();
                   break;
               case "Email":
                   text = "email," + textfieldEnterSearch.getText();
                   break;
           }

           String usersJson = userService.GetEntities(TypeOfRequest.FIND_USER_BY_ANYTHING, new Gson().toJson(text));

           if (usersJson == null || usersJson.isEmpty()) {
               throw new RuntimeException("Получен пустой ответ от сервера.");
           }

           Gson gson = new Gson();
           Type listType = new TypeToken<List<User>>() {}.getType();
           List<User> users = gson.fromJson(usersJson, listType);

           if (users == null || users.isEmpty()) {
               throw new RuntimeException("Ошибка парсинга JSON: список пользователей пуст.");
           }

           System.out.println("Пользователи успешно получены: " + users);

           userTilePane.getChildren().clear();

           for (User user : users) {
               VBox userBox = createUserBox(user);
               userTilePane.getChildren().add(userBox);
           }

       } catch (Exception e) {
           System.out.println("Ошибка при загрузке пользователей: " + e.getMessage());
           e.printStackTrace();
       }
   }

    private VBox createUserBox(User user) {
        VBox userBox = new VBox();
        userBox.setSpacing(5);
        userBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        Label userId = new Label("ID: " + user.getUserId());
        Label userLogin = new Label("Логин: " + user.getLogin());
        Label userRole = new Label("Роль: " + user.getRole().getRoleValue());
        Label userSex = new Label("Пол: " + user.getBuyer().getSex().getSexValue());
        Label userCountry = new Label("Страна: " + user.getBuyer().getAddress().getCountry());
        Label userStreet = new Label("Улица: " + user.getBuyer().getAddress().getStreet());
        Label userCity = new Label("Город: " + user.getBuyer().getAddress().getCity());
        Label userHouseNumber = new Label("Номер дома: " + user.getBuyer().getAddress().getHouseNumber());
        Label userPostIndex = new Label("Индекс: " + user.getBuyer().getAddress().getPostIndex());
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
    // Вызывается при инициализации сцены
    @FXML
    public void initialize() {
        comboBoxSearchType.getItems().addAll(
                "Логин", "Телефон", "Пол", "ID",
                "Страна", "Город", "Имя", "Роль",
                "Улица", "Дом", "Индекс", "Email"
        );
        comboBoxSearchType.setValue("Логин");
    }
}
