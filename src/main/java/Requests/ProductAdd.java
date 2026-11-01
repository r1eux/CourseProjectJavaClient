package Requests;

import SocketAndGetService.ClientSocket;
import Entities.Category;
import Entities.Color;
import Entities.Producer;
import Entities.Product;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Enum.TypeOfRequest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Enum.ResponseStatus;

public class ProductAdd implements Initializable {
    public TextField textfieldURL;
    public TextField textfieldName;
    public TextField textfieldPrice;
    public TextField textfieldColor;
    public TextField textfieldProducerCountry;
    public TextField textfieldCategory;
    public TextField textfieldWarranty;
    public TextField textfieldProducer;
    public TextField textfieldAmount;
    public Label labelMessageTrue;
    public Button buttonAddProduct;
    public Button buttonBack;
    public Label labelMessage;

    public void ProductAdd(ActionEvent actionEvent) throws IOException {

        if (!textfieldURL.getText().equals("") &&
                !textfieldName.getText().equals("") &&
                !textfieldPrice.getText().equals("") &&
                !textfieldColor.getText().equals("") &&
                !textfieldProducerCountry.getText().equals("") &&
                !textfieldCategory.getText().equals("") &&
                !textfieldWarranty.getText().equals("") &&
                !textfieldProducer.getText().equals("") &&
                !textfieldAmount.getText().equals("")) {

            labelMessage.setVisible(false);
            Product product = new Product();
            Color color = new Color();
            Category category=new Category();
            Producer producer = new Producer();
            color.setColor(textfieldColor.getText());
            System.out.println(color.getColor());
            producer.setProducer(textfieldProducer.getText());
            producer.setProducer_country(textfieldProducerCountry.getText());
            category.setName(textfieldCategory.getText());
           // System.out.println(category.getName());
            product.setColor(color);
            product.setProducer(producer);
            product.setCategory(category);
            product.setProdName(textfieldName.getText());
            String priceCheck = textfieldPrice.getText();
            boolean isInt = priceCheck.matches("-?\\d+");
            if (isInt) {

                product.setPrice(Double.parseDouble(textfieldPrice.getText()));
            } else {

                showAlert(Alert.AlertType.ERROR, "Ошибка", "Некорректная цена");
                return;
            }
            String amountCheck = textfieldAmount.getText();
            boolean isIntAmount = amountCheck.matches("-?\\d+");
            if (isIntAmount) {

                product.setAmount(Integer.parseInt(textfieldAmount.getText()));
            } else {

                showAlert(Alert.AlertType.ERROR, "Ошибка", "Некорректное количество");
                return;
            }

            product.setImage(textfieldURL.getText());
            product.setWarranty(textfieldWarranty.getText());
            if (textfieldName.equals("") || textfieldPrice.equals("") || textfieldColor.equals("") || textfieldProducer.equals("") || textfieldURL.equals("")) {
                labelMessage.setText("Не все поля заполнены.");
                labelMessage.setVisible(true);
                return;
            }
            Gson gson = new Gson();
            System.out.println(gson.toJson(product));
            Request requestFinal = new Request();

            requestFinal.setRequestMessage(new Gson().toJson(product));
            requestFinal.setRequestType(TypeOfRequest.ADD_PRODUCT);

            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
            ClientSocket.getInstance().getOut().flush();
            String answerFinal = ClientSocket.getInstance().getInStream().readLine();
            Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
            if (responseFinal.getResponseStatus() == ResponseStatus.OK){
                showAlert(Alert.AlertType.INFORMATION, "Успех", "Продукт добавлен");
            }else{
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Произошла ошибка при добавлении данных");

            }}else{
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Одно из полей не заполнено");
            return;

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
