package Requests;

import SocketAndGetService.ClientSocket;
import Entities.*;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Enum.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserEditPersonal implements Initializable {

    public PasswordField passwordfieldConfirmPassword;
    public Button buttonSignUp;
    public Button buttonBack;
    public TextField textfieldLogin;
    public Label labelMessage;
    public RadioButton cbFemale;
    public RadioButton cbMale;
    public TextField textfieldEmail;
    public TextField textfieldCity;
    public Label labelMessageTrue;
    public TextField textfieldAddress;
    public ToggleGroup Sex1;
    public TextField textfieldCountry;

    public TextField textfieldStreet;
    public TextField textfieldName;
    public TextField textfieldHouseNumber;
    public TextField textfieldPhone;
    public TextField textfieldPostIndex;
    public TextField textfieldFind;
    public Button buttonFind;
    public TextField textfieldPassword;
    public User userMain;
    public Buyer personMain;
    public Address addressMain;
    public Sex sexMain;
    public Role roleMain;

    public void Edit(ActionEvent actionEvent) throws IOException {
        if ((
                (cbFemale.isSelected() || cbMale.isSelected()) &&
                        !textfieldLogin.getText().equals("") &&
                        !textfieldPassword.getText().equals("") &&
                        !textfieldEmail.getText().equals("") &&
                        !textfieldCity.getText().equals("") &&
                        !textfieldCountry.getText().equals("") &&
                        !textfieldStreet.getText().equals("") &&
                        !textfieldName.getText().equals("") &&
                        !textfieldHouseNumber.getText().equals("") &&
                        !textfieldPhone.getText().equals("") &&
                        !textfieldPostIndex.getText().equals("")
        )){ Gson gson = new Gson();
            User user1=new User();
            user1.setUserId(ClientSocket.getInstance().getUser().getUserId() );
            user1.setLogin(textfieldLogin.getText());
            user1.setPassword(textfieldPassword.getText());
            Buyer buyer=new Buyer();
            buyer.setBuyerId(ClientSocket.getInstance().getUser().getBuyer().getBuyerId());
            buyer.setName(textfieldName.getText());
            buyer.setPhoneNumber(textfieldPhone.getText());
            buyer.setEmail(textfieldEmail.getText());
            Address address1=new Address();
            address1.setAddressId(ClientSocket.getInstance().getUser().getBuyer().getAddress().getAddressId());
            address1.setPostIndex(Integer.parseInt(textfieldPostIndex.getText()));
            address1.setCity(textfieldCity.getText());
            address1.setStreet(textfieldStreet.getText());
            address1.setCountry(textfieldCountry.getText());
            address1.setHouseNumber(Integer.parseInt(textfieldHouseNumber.getText()));
            buyer.setAddress(address1);
            Sex sex=new Sex();
            if(cbMale.isSelected()){
                sex.setSexValue("Men");
                sex.setSexId(ClientSocket.getInstance().getUser().getBuyer().getSex().getSexId());
            }
            if(cbFemale.isSelected()){
                sex.setSexValue("Women");
                sex.setSexId(ClientSocket.getInstance().getUser().getBuyer().getSex().getSexId());
            }
            buyer.setSex(sex);
            user1.setBuyer(buyer);
            Request requestFinal = new Request();

            requestFinal.setRequestMessage(new Gson().toJson(user1));
            requestFinal.setRequestType(TypeOfRequest.EDIT_USER);

            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
            ClientSocket.getInstance().getOut().flush();



            String answerFinal = ClientSocket.getInstance().getInStream().readLine();
            Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
            if (responseFinal.getResponseStatus() == ResponseStatus.OK){
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Данные успешно изменены");
            }else{
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Произошла ошибка при изменении данных");

            }}else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Одно из полей не заполнено");

        }


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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuUser.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
//поиск по id,после выгрузка объекта в объект на клиенте, его данные -- в label, под label -- textField с вводом новых данных, если не введено -- втыкать старые данные
