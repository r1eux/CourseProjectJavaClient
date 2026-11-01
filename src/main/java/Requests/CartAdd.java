package Requests;

import Entities.*;
import javafx.fxml.FXML;

import java.util.logging.Logger;

// В начало класса AddToCart
import SocketAndGetService.ClientSocket;
import SocketAndGetService.GetService;
import DataTrade.Request;
import DataTrade.Response;
import mainapp.MainApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import Enum.*;

public class CartAdd {
    private static final Logger LOGGER = Logger.getLogger(CartAdd.class.getName());
    // Все остальные поля и методы...
    public ImageView productImageView;

    @FXML
    private Button addReviewButton;
    @FXML
    private Button checkReviewButton;
    @FXML
    private Button logoutButton;

    @FXML
    private Button addToCartButton; // Кнопка "Добавить в корзину"

    @FXML
    private TextField textfieldEnterSearch;

    @FXML
    private Label labelMessageTrue;

    @FXML
    private Label labelMessage;

    @FXML
    private TextField textfieldReview;

@FXML
private TextField quantityTextField;


    private Product selectedProduct; // Выбранный продукт
    @FXML
    private TilePane productTilePane;
    @FXML
    private VBox reviewTilePane;
    // Метод для обработки переключения радио кнопок
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Метод для загрузки товаров
    @FXML
    private void loadProducts() {
        try {
            System.out.println("Запрос списка товаров...");
            GetService<Product> productService = new GetService<>(Product.class);
            String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS, "");

            System.out.println("Ответ от сервера (JSON): " + productsJson);
            if (productsJson == null || productsJson.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Товаров нет!");
                Back_Pressed(null);
            }

            // Парсим JSON в список объектов Product
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> products = gson.fromJson(productsJson, listType);

            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список товаров пуст.");
            }

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
                productImageView.setImage(new Image("/path/to/default/image.png")); // Путь к изображению по умолчанию
            }
        } else {
            productImageView.setImage(new Image("/path/to/default/image.png")); // Путь к изображению по умолчанию
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
                addReviewButton.setDisable(false);
                checkReviewButton.setDisable(false);
                textfieldReview.setDisable(false);
               // Включить кнопку удаления
            }
        });

        return productBox;
    }

    @FXML
    private void AddReview(ActionEvent event) throws IOException {
        if(!textfieldReview.getText().isEmpty()) {
            String reviewContent= textfieldReview.getText();
            Review review= new Review();
            review.setReviewData(reviewContent);
            review.setBuyer(ClientSocket.getInstance().getUser().getBuyer());
            review.setProduct(selectedProduct);
            Request requestModel = new Request();
            requestModel.setRequestMessage(new Gson().toJson(review));
            requestModel.setRequestType(TypeOfRequest.CREATE_REVIEW);
            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
            ClientSocket.getInstance().getOut().flush();
            String answer = ClientSocket.getInstance().getInStream().readLine();
            Response responseModel = new Gson().fromJson(answer, Response.class);
        }else{
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Отзыв пуст");
            return;

        }




    }

    @FXML
    private void handleLoadReviews(ActionEvent event) {
        loadReviews(); // Загрузка всех отзывов
    }

    private void loadReviews() {
        try {
            System.out.println("Запрос списка отзывов...");
            GetService<Review> reviewService = new GetService<>(Review.class);
            System.out.println(selectedProduct.getProductId());
            String reviewsJson = reviewService.GetEntities(TypeOfRequest.CHECK_REVIEW, String.valueOf(selectedProduct.getProductId()));

            System.out.println("Ответ от сервера (JSON): " + reviewsJson);
            if (reviewsJson == null || reviewsJson.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Отзывов нет!");
                return;
            }

            // Парсим JSON в список объектов Review
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Review>>() {}.getType();
            List<Review> reviews = gson.fromJson(reviewsJson, listType);

            if (reviews == null || reviews.isEmpty()) {
                throw new RuntimeException("Ошибка парсинга JSON: список отзывов пуст.");
            }

            System.out.println("Отзывы успешно получены и распознаны: " + reviews);

            // Открываем окно для отображения всех отзывов
            showReviewsInWindow(reviews);
        } catch (Exception e) {
            System.out.println("Произошла ошибка при загрузке отзывов: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showReviewsInWindow(List<Review> reviews) {
        // Создаем новое окно (Stage)
        Stage reviewStage = new Stage();
        reviewStage.setTitle("Все отзывы");

        // Создаем VBox для размещения всех отзывов
        VBox reviewsBox = new VBox();
        reviewsBox.setSpacing(10);
        reviewsBox.setStyle("-fx-padding: 15; -fx-background-color: #f0f0f0;");

        // Добавляем в VBox информацию о каждом отзыве
        for (Review review : reviews) {
            VBox reviewBox = new VBox();
            reviewBox.setSpacing(5);
            reviewBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: #f9f9f9;");

            // Добавляем текстовые метки для каждого отзыва
            Label reviewId = new Label("ID отзыва: " + review.getReviewId());
            Label reviewData = new Label("Текст отзыва: " + review.getReviewData());
            Label productName = new Label("Товар: " + review.getProduct().getProdName());
            Label buyerName = new Label("Покупатель: " + review.getBuyer().getName());

            reviewBox.getChildren().addAll(reviewId, reviewData, productName, buyerName);

            // Добавляем каждый reviewBox в основной контейнер (reviewsBox)
            reviewsBox.getChildren().add(reviewBox);
        }

        // Настройки сцены
        Scene reviewScene = new Scene(reviewsBox, 400, 500);
        reviewStage.setScene(reviewScene);
        reviewStage.show();
    }



    @FXML
    private void Back_Pressed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MenuUser.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
    // Метод для добавления в корзину
    @FXML
    private void addToCart(ActionEvent actionEvent) throws IOException {
        if (selectedProduct == null) {
            LOGGER.warning("Попытка добавить в корзину, но продукт не выбран.");
            labelMessage.setText("Пожалуйста, выберите продукт.");
            labelMessage.setVisible(true);
            return;
        }
        String amount=quantityTextField.getText();
        if(selectedProduct.getAmount()-Integer.parseInt(amount)<0){
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Нельзя купить товара больше, чем есть в наличии!");
            return;
        }
        LOGGER.info("Добавление продукта в корзину: " + new Gson().toJson(selectedProduct));
Gson gson = new Gson();
        ItemCart itemCart = new ItemCart();
        itemCart.setProduct(selectedProduct);
        int baseAmount=selectedProduct.getAmount();
        System.out.println(baseAmount);
        String cartId = String.valueOf(ClientSocket.getInstance().getUser().getUserId());
        System.out.println(cartId);
        itemCart.setAmount(Integer.parseInt(amount));
        System.out. println("ЕБУЧЕЕ КОЛИЧЕСТВО БЯТЬ!!!!!!!!!!"+ itemCart.getAmount());
        Request requestFinal = new Request();
        requestFinal.setRequestMessage(new Gson().toJson(cartId));
        requestFinal.setRequestType(TypeOfRequest.FIND_CART);
        LOGGER.info("Отправка запроса FIND_CART: " + new Gson().toJson(requestFinal));

        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestFinal));
        ClientSocket.getInstance().getOut().flush();

        String answerFinal = ClientSocket.getInstance().getInStream().readLine();
        LOGGER.info("Ответ от сервера FIND_CART: " + answerFinal);

        Response responseFinal = new Gson().fromJson(answerFinal, Response.class);
        if (responseFinal.getResponseStatus() == ResponseStatus.ERROR) {
            LOGGER.warning("Ошибка при получении корзины: " + responseFinal.getResponseMessage());
            labelMessage.setText("Не удалось найти корзину.");
            labelMessage.setVisible(true);
            return;
        }

        Cart cart = new Gson().fromJson(responseFinal.getResponseData(), Cart.class);
        LOGGER.info("Полученная корзина: " + new Gson().toJson(cart));

                    itemCart.setCart(cart);
                    System.out.println("GANDONY BLYat"+gson.toJson(itemCart.getCart()));
                    GetService<ItemCart> productService = new GetService<>(ItemCart.class);
                    String productsJson = productService.GetEntities(TypeOfRequest.VIEW_ALL_PRODUCTS_IN_CART, "");

        // Парсим JSON в список объектов Product
                    Type listType = new TypeToken<List<ItemCart>>() {}.getType();
                    List<ItemCart> products = gson.fromJson(productsJson, listType);

                System.out.println("Выбранный товар "+ selectedProduct.getProductId());
            boolean checkCopy=false;
            ItemCart cartBuf=new ItemCart();
            if(products!=null) {
                for (ItemCart cr : products) {

                    if (selectedProduct.getProductId() == cr.getProduct().getProductId() && ClientSocket.getInstance().getUser().getUserId() == cr.getCart().getUser().getUserId()) {
                        checkCopy = true;
                        cartBuf=cr;

                    }
                }
            }
        // Логируем отправляемый запрос на поиск корзины


        // Логируем отправляемый запрос на добавление элемента в корзину

        if(checkCopy){
            itemCart.setCartItemId(cartBuf.getCartItemId());
            Request requestAddCartItem = new Request();
            requestAddCartItem.setRequestMessage(new Gson().toJson(itemCart));
            System.out.println(gson.toJson(itemCart));
            Product product1 = new Product();
            product1.setProductId(itemCart.getProduct().getProductId());  // используем текущий ID товара

            // Обновляем данные товара
            product1.setProdName(itemCart.getProduct().getProdName());
            product1.setPrice(itemCart.getProduct().getPrice());  // Цена товара
            product1.setImage(itemCart.getProduct().getImage());  // URL изображения товара
            product1.setAmount(baseAmount- itemCart.getAmount());
            product1.setWarranty(itemCart.getProduct().getWarranty());
            System.out.println("EBUCHEE KOLICHESTVO BLYAT"+product1.getAmount());
            // Получаем и обновляем цвет товара
            Color color1 = new Color();
            color1.setColorId(itemCart.getProduct().getColor().getColorId());
            color1.setColor(itemCart.getProduct().getColor().getColor());  // Предположим, что цвет вводится в текстовое поле
            product1.setColor(color1);

            // Получаем и обновляем производителя товара
            Producer producer1 = new Producer();
            producer1.setProducerId(itemCart.getProduct().getProducer().getProducerId());
            producer1.setProducer(itemCart.getProduct().getProducer().getProducer());
            producer1.setProducer_country(itemCart.getProduct().getProducer().getProducer_country());// Производитель товара
            product1.setProducer(producer1);

            Category category1 = new Category();
            category1.setId(itemCart.getProduct().getCategory().getId());
            category1.setName(itemCart.getProduct().getCategory().getName());
            product1.setCategory(category1);

            // Теперь создаем объект для отправки на сервер
            Request requestChangeAmount = new Request();
            requestChangeAmount.setRequestMessage(new Gson().toJson(product1));  // Преобразуем товар в JSON
            requestChangeAmount.setRequestType(TypeOfRequest.EDIT_PRODUCT);  // Тип запроса для редактирования товара

            // Отправляем запрос на сервер
            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestChangeAmount));
            ClientSocket.getInstance().getOut().flush();

            // Получаем ответ от сервера
            String answerChangeAmount = ClientSocket.getInstance().getInStream().readLine();
            Response responseChangeAmount = new Gson().fromJson(answerChangeAmount, Response.class);

            // Проверяем статус ответа от сервера
            if (responseChangeAmount.getResponseStatus() == ResponseStatus.OK) {
                labelMessageTrue.setText("Товар успешно изменен");
                labelMessageTrue.setVisible(true);
            } else {
                labelMessage.setText("Произошла ошибка при изменении товара");
                labelMessage.setVisible(true);
            }
            requestAddCartItem.setRequestType(TypeOfRequest.INCREASE_AMOUNT_ITEM);
            LOGGER.info("Отправка запроса INCREASE_AMOUNT_ITEM: " + new Gson().toJson(requestAddCartItem));

            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestAddCartItem));
            ClientSocket.getInstance().getOut().flush();}
        else {
            Request requestAddCartItem = new Request();
            Product product1 = new Product();
            System.out.println("ЕбАНЫЙ РОТ ЭТОГО ЕБАНОГО КАЗИНО БЛЯТЬ!!!!!!!!!!!!!!!!!!!"+gson.toJson(itemCart));
            System.out.println("PIZDA EBANAYA1111111"+ itemCart.getProduct().getAmount());
            System.out.println("PROOOOODUCT IDDDDDDDDDDD"+ itemCart.getProduct().getProductId());
            product1.setProductId(itemCart.getProduct().getProductId());  //
            product1.setProdName(itemCart.getProduct().getProdName());
            product1.setPrice(itemCart.getProduct().getPrice());  // Цена товара
            product1.setImage(itemCart.getProduct().getImage());  // URL изображения товара
            product1.setAmount(baseAmount- itemCart.getAmount());
            product1.setWarranty(itemCart.getProduct().getWarranty());
            System.out.println(product1.getAmount());
            // Получаем и обновляем цвет товара
            Color color1 = new Color();
            color1.setColorId(itemCart.getProduct().getColor().getColorId());
            color1.setColor(itemCart.getProduct().getColor().getColor());  // Предположим, что цвет вводится в текстовое поле
            product1.setColor(color1);

            // Получаем и обновляем производителя товара
            Producer producer1 = new Producer();
            producer1.setProducerId(itemCart.getProduct().getProducer().getProducerId());
            producer1.setProducer(itemCart.getProduct().getProducer().getProducer());
            producer1.setProducer_country(itemCart.getProduct().getProducer().getProducer_country());// Производит// Производитель товара
            product1.setProducer(producer1);
            Category category1 = new Category();
            category1.setId(itemCart.getProduct().getCategory().getId());
            category1.setName(itemCart.getProduct().getCategory().getName());
            product1.setCategory(category1);
            // Теперь создаем объект для отправки на сервер
            Request requestChangeAmount = new Request();
            requestChangeAmount.setRequestMessage(new Gson().toJson(product1));

            System.out.println(product1);// Преобразуем товар в JSON
            requestChangeAmount.setRequestType(TypeOfRequest.EDIT_PRODUCT);  // Тип запроса для редактирования товара

            // Отправляем запрос на сервер
            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestChangeAmount));
            ClientSocket.getInstance().getOut().flush();

            // Получаем ответ от сервера
            String answerChangeAmount = ClientSocket.getInstance().getInStream().readLine();
            Response responseChangeAmount = new Gson().fromJson(answerChangeAmount, Response.class);

            // Проверяем статус ответа от сервера
            if (responseChangeAmount.getResponseStatus() == ResponseStatus.OK) {
                labelMessageTrue.setText("Товар успешно изменен");
                labelMessageTrue.setVisible(true);
            } else {
                labelMessage.setText("Произошла ошибка при изменении товара");
                labelMessage.setVisible(true);
            }
            requestAddCartItem.setRequestMessage(new Gson().toJson(itemCart));
            requestAddCartItem.setRequestType(TypeOfRequest.ADD_CART_ITEM);
            LOGGER.info("Отправка запроса ADD_CART_ITEM: " + new Gson().toJson(requestAddCartItem));

            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestAddCartItem));
            ClientSocket.getInstance().getOut().flush();
        }
        String answerAddToCart = ClientSocket.getInstance().getInStream().readLine();
        LOGGER.info("Ответ от сервера ADD_CART_ITEM: " + answerAddToCart);

        Response responseAddToCart = new Gson().fromJson(answerAddToCart, Response.class);
        if (responseAddToCart.getResponseStatus() == ResponseStatus.ERROR) {
            LOGGER.warning("Ошибка при добавлении продукта в корзину: " + responseAddToCart.getResponseMessage());
            labelMessage.setText("Не получилось добавить товар в корзину.");
            labelMessage.setVisible(true);
        } else {
            LOGGER.info("Товар успешно добавлен в корзину.");
            labelMessageTrue.setText("Товар успешно добавлен");
            labelMessageTrue.setVisible(true);
        }
        loadProducts();
    }
    @FXML
    public void initialize() {
        loadProducts();

    }
}

