package lk.ijse.Sarasavi.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ButtonbarFromController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane mainNode;

    public void initialize() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
        setDateAndTime();
    }

    private void setDateAndTime() {
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }
    @FXML
    void btnAttendsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/attends_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    @FXML
    void btnManageIncomeOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/income1_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    @FXML
    void btnSignOutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sarasavi_BookShop");
        primaryStage.show();

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/supllier_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/items_form.fxml"));
        this.mainNode.getChildren().clear();
        this.mainNode.getChildren().add(rootNode);
    }
}