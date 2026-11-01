package Requests;

import Entities.*;
import mainapp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import DataTrade.Request;
import DataTrade.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import Enum.*;

public class CartCheck {
    private static final Logger LOGGER = Logger.getLogger(CartCheck.class.getName());
    public ImageView productImageView;
    @FXML
    private TableView<Product> productTable;


    @FXML
    private Button addToCartButton; // Кнопка "Добавить в корзину"
    @FXML
    private Button addToOrderButton; // Кнопка "Добавить в заказ"
    @FXML
    private Button placeOrderButton; // Кнопка "Оформить заказ"

    @FXML
    private TextField quantityTextField; // Поле для ввода количества

    @FXML
    private Label labelMessageTrue;
    @FXML
    private Label labelMessage;
    @FXML
    private Button logoutButton;

    private Product selectedProduct;
  public  List<ItemOrder> orderItems=new ArrayList<ItemOrder>();

  public ItemCart itemCart;
    @FXML
    private TilePane productTilePane;
    // Метод для обработки переключения радио кнопок
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Метод для загрузки продуктов в таблицу
    @FXML
    private void loadProducts() {
        try {
            System.out.println("Запрос списка товаров v karte...");
            Gson gson = new Gson();

            GetService<ItemCart> productService = new GetService<>(ItemCart.class);
            String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS_IN_CART, "");

            System.out.println("Ответ от сервера (JSON): " + productsJson);
//            if (productsJson == null || productsJson.isEmpty()) {
//                showAlert(Alert.AlertType.ERROR, "Ошибка", "Товаров нет!");
//                backPressed(null);
//            }

            // Парсим JSON в список объектов CartItem
            Type listType = new TypeToken<List<ItemCart>>() {}.getType();
            List<ItemCart> itemCarts = gson.fromJson(productsJson, listType);
            List<Product> products = new ArrayList<>();

            if (itemCarts != null) {
                for (ItemCart ci : itemCarts) {
                    if (ci.getCart().getUser().getUserId() == ClientSocket.getInstance().getUser().getUserId()) {
                        ci.getProduct().setAmount(ci.getAmount());
                        products.add(ci.getProduct());
                    }
                }
            }

          /*  if (products == null || products.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список товаров пуст.");
            }*/

            System.out.println("Товары успешно получены и распознаны: " + products);

            // Очищаем TilePane перед добавлением новых элементов
            productTilePane.getChildren().clear();

            // Добавляем товары в TilePane
            for (Product product : products) {
                VBox productBox = createProductBox(product);
                productTilePane.getChildren().add(productBox);
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке товаров: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Создание бокса для отображения информации о продукте
    private VBox createProductBox(Product product) {
        VBox productBox = new VBox();
        productBox.setSpacing(5);
        productBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

        // Добавляем визуальные элементы
        Label productId = new Label("ID: " + product.getProductId());
        Label productName = new Label("Название: " + product.getProdName());
        Label productPrice = new Label("Цена: " + product.getPrice());
        Label productColor = new Label("Цвет: " + product.getColor().getColor());
        Label productProducer = new Label("Производитель: " + product.getProducer().getProducer());
        Label productAmount = new Label("Количество: " + product.getAmount());
        Label productCategory = new Label("Категория: " + product.getCategory().getName());
        Label productProducerCountry = new Label("Страна производителя: " + product.getProducer().getProducer_country());
        Label productWarranty = new Label("Гарантия: " + product.getWarranty());

        // Создаём ImageView для отображения изображения товара
        ImageView productImageView = new ImageView();
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            try {
                Image productImage = new Image(product.getImage(), 100, 100, true, true); // Размер изображения 100x100
                productImageView.setImage(productImage);
            } catch (Exception e) {
                System.out.println("Ошибка загрузки изображения для товара ID: " + product.getProductId());
                productImageView.setImage(new Image(product.getImage())); // Путь к изображению по умолчанию
            }
        } else {
            productImageView.setImage(new Image(product.getImage())); // Путь к изображению по умолчанию
        }

        productBox.getChildren().addAll(
                productImageView, productId, productName, productPrice, productColor,
                productProducer, productAmount, productCategory, productProducerCountry, productWarranty
        );

        // Обработка двойного клика для выбора товара
        productBox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectedProduct = product;
                System.out.println("Выбран товар: " + selectedProduct);
                // Включить кнопку удаления

            }
        });

        return productBox;
    }



    // Метод для добавления товара в заказ
    @FXML
    private void addToOrder(ActionEvent actionEvent) throws IOException {
        Gson gson = new Gson();

        if (selectedProduct == null) {
            LOGGER.warning("Попытка добавить в корзину, но продукт не выбран.");
            labelMessage.setText("Пожалуйста, выберите продукт.");
            addToOrderButton.setVisible(true);
            return;
        }
        ItemOrder orderItem=new ItemOrder();
        orderItem.setProduct(selectedProduct);
        orderItem.setPricePurchase(selectedProduct.getPrice());
        orderItem.setAmount(selectedProduct.getAmount());

        orderItems.add(orderItem);

        GetService<ItemCart> productService = new GetService<>(ItemCart.class);
        String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS_IN_CART, "");

        LOGGER.info("Получены данные для удаления: " + productsJson);

        Type listType = new TypeToken<List<ItemCart>>() {}.getType();
        List<ItemCart> products = gson.fromJson(productsJson, listType);

        for(ItemCart cr:products){
            if(cr.getProduct().getProductId()==selectedProduct.getProductId() && cr.getCart().getUser().getUserId()==ClientSocket.getInstance().getUser().getUserId()) {

                Request requestFinal = new Request();
                requestFinal.setRequestMessage(new Gson().toJson(cr));
                requestFinal.setRequestType(TypeOfRequest.DELETE_FROM_CART);
                LOGGER.info("Отправка запроса FIND_CART: " + new Gson().toJson(requestFinal));

                ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
                ClientSocket.getInstance().getOut().flush();

                String answerFinal = ClientSocket.getInstance().getInStream().readLine();
                LOGGER.info("Ответ от сервера FIND_CART: " + answerFinal);

                Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
                break;
            };


        }

       /* labelMessageTrue.setText("Товар успешно добавлен в заказ.");
        labelMessageTrue.setVisible(true);*/
        placeOrderButton.setDisable(false);
        loadProducts();
    }
    public void backPressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuUser.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
    // Метод для оформления заказа
    @FXML
    private void placeOrder(ActionEvent actionEvent) throws IOException {
        Gson gson = new Gson();
        Order order=new Order();
        LocalDate date= LocalDate.now();
        LocalDate shippingDate=date.plusDays(7);
        order.setStatus("Ожидает подтверждения");
        order.setOrderDate((date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));;
        order.setShippingDate(shippingDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        order.setUser(ClientSocket.getInstance().getUser());
       double totalSum=0;
        for(ItemOrder or:orderItems){
            int am=or.getAmount();
            double sum=or.getPricePurchase();
            double finSum=am*sum;
            totalSum+=finSum;
            System.out.println(or);

        }
        order.setTotalSum(totalSum);
        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(order));
        request.setRequestType(TypeOfRequest.CREATE_ORDER);

        LOGGER.info("Отправка запроса CREATE_ORDER: " + new Gson().toJson(request));

        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();

        String responseText = ClientSocket.getInstance().getInStream().readLine();
        Response response = new Gson().fromJson(responseText, Response.class);
       Order orderFromServer=gson.fromJson(response.getResponseData(),Order.class);
        for(ItemOrder or:orderItems){
            or.setOrder(orderFromServer);
            System.out.println(or);
        }

        Request requestItems = new Request();
        requestItems.setRequestMessage(new Gson().toJson(orderItems));
        requestItems.setRequestType(TypeOfRequest.CREATE_ORDER_ITEMS);

        LOGGER.info("Отправка запроса CREATE_ORDER_ITEMS: " + new Gson().toJson(requestItems));

        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestItems));
        ClientSocket.getInstance().getOut().flush();

        String responseTextItems = ClientSocket.getInstance().getInStream().readLine();
        Response responseItems = new Gson().fromJson(responseText, Response.class);



        labelMessageTrue.setText("Заказ успешно оформлен.");
        labelMessageTrue.setVisible(true);
    }

    @FXML
    private void deleteFromCart(ActionEvent actionEvent) throws IOException {
        Gson gson=new Gson();
        GetService<ItemCart> productService = new GetService<>(ItemCart.class);
        String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS_IN_CART, "");

        LOGGER.info("Получены данные для удаления: " + productsJson);

        Type listType = new TypeToken<List<ItemCart>>() {}.getType();
        List<ItemCart> products = gson.fromJson(productsJson, listType);

        for(ItemCart cr:products){
            if(cr.getProduct().getProductId()==selectedProduct.getProductId() && cr.getCart().getUser().getUserId()==ClientSocket.getInstance().getUser().getUserId()) {

                Request requestFinal = new Request();
                requestFinal.setRequestMessage(new Gson().toJson(cr));
                requestFinal.setRequestType(TypeOfRequest.DELETE_FROM_CART);
                LOGGER.info("Отправка запроса DELETE_FROM_CART: " + new Gson().toJson(requestFinal));

                ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
                ClientSocket.getInstance().getOut().flush();

                String answerFinal = ClientSocket.getInstance().getInStream().readLine();
                LOGGER.info("Ответ от сервера DELETE_FROM_CART: " + answerFinal);

                Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
                break;
            };


        }

        labelMessageTrue.setText("Товар успешно удален.");
        labelMessageTrue.setVisible(true);
        loadProducts();


    }
    // Метод инициализации
    @FXML
    public void initialize() {
        loadProducts();


    }
}
