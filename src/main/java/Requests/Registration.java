package Requests;

import SocketAndGetService.ClientSocket;
import Entities.*;
import Enum.ResponseStatus;
import Enum.TypeOfRequest;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Registration implements Initializable {
    public PasswordField passwordfieldPassword;
    public PasswordField passwordfieldConfirmPassword;
    public Button buttonSignUp;
    public Button buttonBack;
    public TextField textfieldLogin;
    public Label labelMessage;
    public RadioButton cbFemale;
    public RadioButton cbMale;
    public TextField textfieldEmail;
    public Spinner spinnerAge;
    public TextField textfieldAddress;
    public ToggleGroup Sex;
    public TextField textfieldCountry;
    public TextField textfieldCity;
    public TextField textfieldStreet;
    public TextField textfieldName;
    public TextField textfieldHouseNumber;
    public TextField textfieldPhone;
    public TextField textfieldPostIndex;


    public void Signup_Pressed(ActionEvent actionEvent) throws IOException {
        labelMessage.setVisible(false);

        //POJO
        Role role = new Role();

        User user = new User();
        user.setLogin(textfieldLogin.getText());
        user.setPassword(textfieldLogin.getText());

        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(""));
        request.setRequestType(TypeOfRequest.GET_ROLE);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer = ClientSocket.getInstance().getInStream().readLine();
        Response response = new Gson().fromJson(answer, Response.class);
        if (response.getResponseStatus() == ResponseStatus.OK) {
            ClientSocket.getInstance().setRole(new Gson().fromJson(response.getResponseData(), Role.class));
        }
        user.setRole( ClientSocket.getInstance().getRole());


        Buyer buyer = new Buyer();
        Address address=new Address();

        address.setCountry(textfieldCountry.getText());
        address.setCity(textfieldCity.getText());
        address.setStreet(textfieldStreet.getText());

        String checkHouseNumber=textfieldHouseNumber.getText();
        boolean isInt=checkHouseNumber.matches("-?\\d+");
        if(isInt){

            address.setHouseNumber(Integer.parseInt(textfieldHouseNumber.getText()));
        }else{

            labelMessage.setText("Некорректный номер дома");
            labelMessage.setVisible(true);
            return;
        }
        String checkPostIndex=textfieldPostIndex.getText();
        boolean isIntIndex=checkHouseNumber.matches("-?\\d+");
        if(isIntIndex){

            address.setPostIndex(Integer.parseInt(textfieldPostIndex.getText()));
        }else{

            labelMessage.setText("Некорректный почтовый индекс");
            labelMessage.setVisible(true);
            return;
        }

        buyer.setAddress(address);
        buyer.setName(textfieldName.getText());
        buyer.setPhoneNumber(textfieldPhone.getText());
            buyer.setEmail(textfieldEmail.getText());
        Request requestSex = new Request();
        requestSex.setRequestMessage(new Gson().toJson(cbFemale.isSelected()));//true=zhenshina
        requestSex.setRequestType(TypeOfRequest.GET_SEX);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestSex));
        ClientSocket.getInstance().getOut().flush();
        String answerSex = ClientSocket.getInstance().getInStream().readLine();
        Response responseSex = new Gson().fromJson(answerSex, Response.class);
        if (responseSex.getResponseStatus() == ResponseStatus.OK) {
            ClientSocket.getInstance().setSex(new Gson().fromJson(responseSex.getResponseData(), Sex.class));
        }
        buyer.setSex( ClientSocket.getInstance().getSex());

        user.setBuyer(buyer);


        if (!passwordfieldPassword.getText().equals(passwordfieldConfirmPassword.getText())) {
            labelMessage.setText("Пароли не совпадают");
            labelMessage.setVisible(true);
            return;
        }
        if (textfieldLogin.equals("") || passwordfieldPassword.equals("") || passwordfieldConfirmPassword.equals("")) {
            labelMessage.setText("Не все поля заполнены.");
            labelMessage.setVisible(true);
            return;
        }



        Request requestFinal = new Request();

        requestFinal.setRequestMessage(new Gson().toJson(user));
        requestFinal.setRequestType(TypeOfRequest.REGISTER);

        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();



        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
        if (responseFinal.getResponseStatus() == ResponseStatus.OK) {
            labelMessage.setVisible(false);
            ClientSocket.getInstance().setUser(new Gson().fromJson(responseFinal.getResponseData(), User.class));
            Stage stage = (Stage) buttonSignUp.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        } else {
            labelMessage.setText("Пользователь с таким логином уже существует.");
            labelMessage.setVisible(true);
        }
    }

    public void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
