module by.curs.usercurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens mainapp to javafx.fxml, com.google.gson;
    exports mainapp;
    opens Requests to javafx.fxml, com.google.gson;
    exports Requests;
    opens DataTrade to javafx.fxml, com.google.gson;
    exports DataTrade;
    opens Entities to javafx.fxml, com.google.gson;
    exports Entities;
    opens SocketAndGetService to javafx.fxml, com.google.gson;
    exports SocketAndGetService;
    opens Enum to javafx.fxml, com.google.gson;
    exports Enum;
}
