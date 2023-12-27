package lk.ijse.Sarasavi.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.CustomerDto;
import lk.ijse.Sarasavi.dto.tm.CustomerTm;
import lk.ijse.Sarasavi.model.CustomerModel;
import lk.ijse.Sarasavi.util.Regex;
import lk.ijse.Sarasavi.util.TextFields;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> numbers;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        numbers.setCellValueFactory(new PropertyValueFactory<>("number"));
    }

    private void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try{
            List<CustomerDto> dtoList = model.getAllCustomer();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getNumber()
                        )
                );
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNumber.setText("");
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Please enter valid data").show();
            return;
        }

        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtNumber.getText();

        var dto = new CustomerDto(id, name, address, mobile);

        try {
            boolean isSaved = CustomerModel.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Saved").show();
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();;
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (!Regex.isTextFieldValid(TextFields.ID, txtId.getText())){
            new Alert(Alert.AlertType.ERROR, "Please enter valid ID").show();
            return;
        }
        String id  = txtId.getText();

        try {
            boolean isDeleted = CustomerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted").show();
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Deleted").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Please enter valid data").show();
            return;
        }
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtNumber.getText();

        var dto  = new CustomerDto(id, name, address, mobile);

        try {
            boolean isUpdated = CustomerModel.updateCustomer(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer details updated").show();;
                clearFields();
                loadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer details not updated").show();;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        }
    }

    public void txtCustomerIdOnKeyReleasedAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID,txtId);
    }

    public void txtCustomerNamOnKeyReleasedAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtCustomerAddressOnKeyReleasedAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtCustomerNumberOnKeyReleasedAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtNumber);
    }

    public boolean validateData(){
        return Regex.isTextFieldValid(TextFields.ID,txtId.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtName.getText()) &&
                Regex.isTextFieldValid(TextFields.ADDRESS,txtAddress.getText()) &&
                Regex.isTextFieldValid(TextFields.PHONE,txtNumber.getText());
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/customerdetails.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }
}