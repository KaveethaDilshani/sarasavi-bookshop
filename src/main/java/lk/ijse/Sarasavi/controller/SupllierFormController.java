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
import lk.ijse.Sarasavi.dto.SupplierDto;
import lk.ijse.Sarasavi.dto.tm.SupplierTM;
import lk.ijse.Sarasavi.model.SupplierModel;
import lk.ijse.Sarasavi.util.Regex;
import lk.ijse.Sarasavi.util.TextFields;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupllierFormController {

    public TableView<SupplierTM> tblSuppliers;
    public TableColumn<SupplierTM, Integer> colId;
    public TableColumn<SupplierTM, String> colname;
    public TableColumn<SupplierTM, String> colAddress;
    public TableColumn<SupplierTM, Integer> colNumber;
    @FXML
    private TextField txtS_address;

    @FXML
    private TextField txtS_id;

    @FXML
    private TextField txtS_name;

    @FXML
    private TextField txtS_number;

    public void initialize(){
        setCell();
        setTable();
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (!validate()){
            new Alert(Alert.AlertType.ERROR, "Fill Fields with valid Details").show();
            return;
        }

        SupplierDto supplierDto = collectData();
        try {
            boolean b = SupplierModel.saveSupplier(supplierDto);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Added").show();
                setTable();
                btnClearOnAction(event);
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();

        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtS_id.clear();
        txtS_name.clear();
        txtS_address.clear();
        txtS_number.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (!Regex.setTextColor(TextFields.INTEGER,txtS_id)){
            new Alert(Alert.AlertType.ERROR, "Enter Valid Id").show();
            return;
        }

        String text = txtS_id.getText();
        int id = Integer.parseInt(text);
        try {
            boolean b = SupplierModel.deleteSupplier(id);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted").show();
                setTable();
                btnClearOnAction(event);
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!validate()){
            new Alert(Alert.AlertType.ERROR, "Fill Fields with valid Details").show();
            return;
        }
        SupplierDto supplierDto = collectData();
        try {
            boolean b = SupplierModel.updateSupplier(supplierDto);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated").show();
                setTable();
                btnClearOnAction(event);
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public SupplierDto collectData(){
        return new SupplierDto(
                Integer.parseInt(txtS_id.getText()),
                txtS_name.getText(),
                txtS_address.getText(),
                Integer.parseInt(txtS_number.getText())
        );
    }

    public void setTable(){
        try {
            List<SupplierTM> list = new ArrayList<>();
            List<SupplierDto> allSuppliers = SupplierModel.getAllSuppliers();
            for (SupplierDto supplierDto : allSuppliers) {
                SupplierTM supplierTM = new SupplierTM(
                        supplierDto.getId(),
                        supplierDto.getName(),
                        supplierDto.getAddress(),
                        supplierDto.getContactNumber()
                );
                list.add(supplierTM);
            }
            ObservableList<SupplierTM> observableList = FXCollections.observableArrayList(list);
            tblSuppliers.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCell(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    }


    public void txtSupplierIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID,txtS_id);
    }

    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtS_name);
    }

    public void txtSupplierAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtS_address);
    }

    public void txtSupplierNumberOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtS_number);
    }

    public boolean validate(){
        return Regex.isTextFieldValid(TextFields.ID,txtS_id.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtS_name.getText()) &&
                Regex.isTextFieldValid(TextFields.ADDRESS,txtS_address.getText()) &&
                Regex.isTextFieldValid(TextFields.PHONE,txtS_number.getText());
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        if (!Regex.setTextColor(TextFields.INTEGER,txtS_id)){
            new Alert(Alert.AlertType.ERROR, "Enter Valid Id").show();
            return;
        }

        String text = txtS_id.getText();
        try {
            SupplierDto supplierDto = SupplierModel.getSupplierById(Integer.parseInt(text));
            setData(supplierDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setData(SupplierDto supplierDto) {
        txtS_id.setText(String.valueOf(supplierDto.getId()));
        txtS_name.setText(supplierDto.getName());
        txtS_address.setText(supplierDto.getAddress());
        txtS_number.setText(String.valueOf(supplierDto.getContactNumber()));
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

    }
}