package lk.ijse.Sarasavi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Sarasavi.model.SignUpModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpPageController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnSignInFormOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sarasavi_BookShop");
        primaryStage.show();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String name = txtUserName.getText();
        String password = txtPassword.getText();

        var dto = new lk.ijse.Sarasavi.dto.SignUpDto(name, password);

        try {
            boolean isSaved = SignUpModel.saveUser(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"User Saved");
                clearFields();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtUserName.setText("");
        txtPassword.setText("");
    }

}