package Requests;

import SocketAndGetService.ClientSocket;
import Entities.Role;
import Entities.User;
import Enum.ResponseStatus;
import Enum.TypeOfRequest;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    @FXML
    private PasswordField passwordfieldPassword;
    @FXML
    private TextField textfieldLogin;

    public Button buttonRegister;
    public Button buttonLogin;
    public Label labelMessage;

    public void Login_Pressed(ActionEvent actionEvent) throws IOException {
        User user = new User();
        user.setLogin(textfieldLogin.getText());
        user.setPassword(passwordfieldPassword.getText());
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(user));
        requestModel.setRequestType(TypeOfRequest.LOGIN);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
        ClientSocket.getInstance().getOut().flush();
        String answer = ClientSocket.getInstance().getInStream().readLine();
        Response responseModel = new Gson().fromJson(answer, Response.class);
        if (responseModel.getResponseStatus() == ResponseStatus.OK) {
            labelMessage.setVisible(false);
            ClientSocket.getInstance().setUser(new Gson().fromJson(responseModel.getResponseData(), User.class));
           Role role=ClientSocket.getInstance().getUser().getRole();
           System.out.println(role.getRoleValue());
           if(role.getRoleValue().equals("user")){
            Stage stage = (Stage) buttonLogin.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuUser.fxml"));

            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);}
           else{
               Stage stage = (Stage) buttonLogin.getScene().getWindow();
               FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));

               Parent root = fxmlLoader.load();
               Scene newScene = new Scene(root);
               stage.setScene(newScene);
           }
        } else {
            labelMessage.setVisible(true);
        }
    }

    public void Register_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
         FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Registration.fxml"));

        Parent root = fxmlLoader.load();
        // Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));

        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }
}
