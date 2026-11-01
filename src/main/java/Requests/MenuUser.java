package Requests;

import mainapp.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuUser {
@FXML
private Button buttonProductList;
@FXML
private Button buttonCart;
@FXML
private Button buttonBack;
@FXML
private Button buttonEdit;
    @FXML
    private Button buttonOrder;
public void ViewAndAddToCart(ActionEvent event) throws IOException {
    Stage stageProduct= (Stage) buttonProductList.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CartAdd.fxml"));
    Parent root = fxmlLoader.load();
    Scene newScene = new Scene(root);
    stageProduct.setScene(newScene);
}
    public void ViewCartAndOrder(ActionEvent event) throws IOException {
        Stage stageProduct= (Stage) buttonCart.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CartCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);
    }
    public void backPressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }
    public void EditUser(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonEdit.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserEditPersonal.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }
    public void ViewOrder(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonOrder.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserOrdersCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }

}
