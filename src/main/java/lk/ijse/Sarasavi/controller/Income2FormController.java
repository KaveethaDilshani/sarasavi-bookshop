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
import java.util.List;

public class Income2FormController {
    public AnchorPane root;
    public TableColumn<IncomeTM, String> colYear;
    public TableColumn<IncomeTM, Double> colIncome;
    public TableView<IncomeTM> tblIncome;

    public void initialize(){
        setCellValue();
        setData();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }

    public void btnMonthlyOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/income1_form.fxml"));
        root.getChildren().clear();
        root.getChildren().add(load);
    }

    public void setData(){
        try {
            List<IncomeTM> incomeAnnualy = OrderModel.getIncomeAnnualy();
            ObservableList<IncomeTM> incomeTMS = FXCollections.observableArrayList(incomeAnnualy);
            tblIncome.setItems(incomeTMS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCellValue(){
        colYear.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

}
