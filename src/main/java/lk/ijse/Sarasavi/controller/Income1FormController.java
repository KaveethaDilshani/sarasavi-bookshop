package lk.ijse.Sarasavi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Sarasavi.dto.tm.IncomeTM;
import lk.ijse.Sarasavi.model.OrderModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Income1FormController {
    public AnchorPane root;
    public TableView<IncomeTM> tblIncome;
    public TableColumn<IncomeTM, String> colMonth;
    public TableColumn<IncomeTM, Double> colIncome;

    public void initialize(){
        setData();
        setCellValues();
    }

    public void btnAnnualyOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/income2_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    public void setData(){
        try {
            List<IncomeTM> incomeByMonthly = OrderModel.getIncomeByMonthly(LocalDate.now().getYear());
            ObservableList<IncomeTM> incomeTMS = FXCollections.observableArrayList(incomeByMonthly);
            tblIncome.setItems(incomeTMS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCellValues(){
        colIncome.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("identifier"));
    }
}
