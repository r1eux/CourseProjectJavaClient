package Requests;

import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import Entities.*;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Enum.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserEdit implements Initializable {
    public Button buttonSignUp;
    public Button buttonBack;
    public TextField textfieldLogin;
    public Label labelMessage;
    public RadioButton cbFemale;
    public RadioButton cbMale;
    public TextField textfieldEmail;
  public Label labelMessageTrue;
    public ToggleGroup Sex1;
    public TextField textfieldCountry;
    public TextField textfieldCity;
    public TextField textfieldStreet;
    public TextField textfieldName;
    public TextField textfieldHouseNumber;
    public TextField textfieldPhone;
    public TextField textfieldPostIndex;
    public TextField textfieldFind;
    public Button buttonFind;
    public TextField textfieldPassword;
    public User userMain;
    public Buyer buyerMain;
    public Address addressMain;
    public void FindUser(ActionEvent actionEvent) {
        if(textfieldFind.getText().equals("")){

           labelMessage.setText("Данные не введены!");
           labelMessage.setVisible(true);
        }else{

            Gson gson = new Gson();
            User user1=new User();

            String id="id,"+textfieldFind.getText();
            GetService<User> userService = new GetService<>(User.class);
            String usersJson = userService.GetEntities(TypeOfRequest.FIND_USER_BY_ANYTHING,gson.toJson(id));
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(usersJson, listType);
            for(User user : users) {
                user1=user;
            }
            this.userMain=user1;
            this.buyerMain =user1.getBuyer();
            this.addressMain= buyerMain.getAddress();
            textfieldLogin.setText(user1.getLogin());
            textfieldPassword.setText(user1.getPassword());
            textfieldCountry.setText(user1.getBuyer().getAddress().getCountry());
            textfieldCity.setText(user1.getBuyer().getAddress().getCity());
            textfieldStreet.setText(user1.getBuyer().getAddress().getStreet());
            textfieldHouseNumber.setText(String.valueOf( user1.getBuyer().getAddress().getHouseNumber()));
            textfieldPhone.setText(user1.getBuyer().getPhoneNumber());
            textfieldName.setText(user1.getBuyer().getName());
            textfieldPostIndex.setText(String.valueOf(user1.getBuyer().getAddress().getPostIndex()));
            textfieldEmail.setText(user1.getBuyer().getEmail());
            String sex= user1.getBuyer().getSex().getSexValue();
            System.out.println(sex);
            if(sex.equals("man")){
                cbMale.setSelected(true);
            }else if(sex.equals("woman")){
                cbFemale.setSelected(true);
            }

        }


    }
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
        )){
        Gson gson = new Gson();
        User user1=new User();
        user1.setUserId(userMain.getUserId() );
        user1.setLogin(textfieldLogin.getText());
        user1.setPassword(textfieldPassword.getText());
        Buyer buyer1=new Buyer();
        buyer1.setBuyerId(userMain.getBuyer().getBuyerId());
        buyer1.setName(textfieldName.getText());
        buyer1.setPhoneNumber(textfieldPhone.getText());
        buyer1.setEmail(textfieldEmail.getText());
        Address address1=new Address();
        address1.setAddressId(userMain.getBuyer().getAddress().getAddressId());
        address1.setPostIndex(Integer.parseInt(textfieldPostIndex.getText()));
        address1.setCity(textfieldCity.getText());
        address1.setStreet(textfieldStreet.getText());
        address1.setCountry(textfieldCountry.getText());
        address1.setHouseNumber(Integer.parseInt(textfieldHouseNumber.getText()));
        buyer1.setAddress(address1);
        Sex sex=new Sex();
        if(cbMale.isSelected()){
            sex.setSexValue("Men");
            sex.setSexId(userMain.getBuyer().getSex().getSexId());
        }
        if(cbFemale.isSelected()){
            sex.setSexValue("Women");
            sex.setSexId(userMain.getBuyer().getSex().getSexId());
        }
        buyer1.setSex(sex);
        user1.setBuyer(buyer1);
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
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Одно из полей не заполнено");}

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
    public void initialize(URL location, ResourceBundle resources) {

    }
}
//поиск по id,после выгрузка объекта в объект на клиенте, его данные -- в label, под label -- textField с вводом новых данных, если не введено -- втыкать старые данные
