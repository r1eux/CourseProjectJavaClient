package Requests;

import mainapp.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuAdmin {
    @FXML
    MenuButton userMenu;
    @FXML
    MenuButton reviewMenu;
    @FXML
    MenuItem addUser;
    @FXML
    MenuItem deleteUser;
    @FXML
    MenuItem editUser;
    @FXML
    MenuItem viewUser;
    @FXML
    MenuButton productMenu;
    @FXML
    MenuItem addProduct;
    @FXML
    MenuItem deleteProduct;
    @FXML
    MenuItem editProduct;
    @FXML
    MenuItem viewProduct;

    @FXML
    MenuItem viewOrder;
    @FXML
            MenuButton cartMenu;
    @FXML
            MenuButton orderMenu;
    @FXML
    MenuButton statsMenu;
    @FXML
    MenuItem ReviewBarChart;
    @FXML
    Button buttonBack;
    @FXML
    MenuItem CategoryDiagram;
    @FXML
    MenuItem ReviewCheck;

   ;
    public void backPressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void AddProduct(ActionEvent actionEvent) throws IOException{
        Stage stageProduct= (Stage) productMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ProductAdd.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);

    }
    public void DeleteProduct(ActionEvent actionEvent) throws IOException{
        Stage stageProduct= (Stage) productMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ProductDelete.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);
    }
    public void EditProduct(ActionEvent actionEvent) throws IOException{
        Stage stageProduct= (Stage) productMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ProductEdit.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);
    }
    public void ViewProduct(ActionEvent actionEvent) throws IOException{
        Stage stageProduct= (Stage) productMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ProductCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);
    }
    public void AddUser(ActionEvent actionEvent) throws IOException{
        Stage stageUser = (Stage) userMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserAdd.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void EditUser(ActionEvent actionEvent) throws IOException{
        Stage stageUser = (Stage) userMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserEdit.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void ViewUser(ActionEvent actionEvent) throws IOException{
        Stage stageUser = (Stage) userMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void DeleteUser(ActionEvent actionEvent) throws IOException{
        Stage stageUser = (Stage) userMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserDelete.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void ReviewCheck(ActionEvent actionEvent) throws IOException{
        Stage stageUser = (Stage) reviewMenu.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ReviewCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void ViewOrder(ActionEvent actionEvent) throws IOException{
        Stage stageOrder= (Stage) orderMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("OrderCheck.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageOrder.setScene(newScene);
    }
    public void SearchUser(ActionEvent actionEvent) throws IOException{
        System.out.println("SearchUserISActive");
        Stage stageUser= (Stage) userMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("UserSearch.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageUser.setScene(newScene);
    }
    public void SearchProduct(ActionEvent actionEvent) throws IOException{
        Stage stageProduct= (Stage) productMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ProductSearch.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageProduct.setScene(newScene);
    }
    public void ManWomen(ActionEvent actionEvent) throws IOException{
        Stage stageOrder= (Stage) statsMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ReviewBarChart.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageOrder.setScene(newScene);
    }
    public void PieDiagram(ActionEvent actionEvent) throws IOException{
        Stage stageOrder= (Stage) statsMenu.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("CategoryDiagram.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stageOrder.setScene(newScene);
    }

}
