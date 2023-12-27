package lk.ijse.Sarasavi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Sarasavi.dto.Item;
import lk.ijse.Sarasavi.dto.tm.ItemTM;
import lk.ijse.Sarasavi.model.ItemModel;
import lk.ijse.Sarasavi.util.Regex;
import lk.ijse.Sarasavi.util.TextFields;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtCategory;
    public TextField txtQty;
    public TableView<ItemTM> tblItems;
    public TableColumn<ItemTM, Integer> colId;
    public TableColumn<ItemTM, String> colName;
    public TableColumn<ItemTM, Double> colPrice;
    public TableColumn<ItemTM, String> colCategory;
    public TableColumn<ItemTM, Integer> colQty;

    public void initialize(){
        setCellValueFactory();
        setTableData();
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Invalid Data").show();
            return;
        }
        Item item = collectData();
        try {
            boolean b = ItemModel.saveItem(item);
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added").show();
                setTableData();
                btnClearOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.ERROR, "Item Not Added").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        if (Regex.setTextColor(TextFields.INTEGER, txtId)){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return;
        }


        String text = txtId.getText();
        try {
            boolean b = ItemModel.deleteItem(Integer.parseInt(text));
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted").show();
                setTableData();
                btnClearOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.ERROR, "Item Not Deleted").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Invalid Data").show();
            return;
        }
        Item item = collectData();
        try {
            boolean b = ItemModel.updateItem(item);
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated").show();
                setTableData();
                btnClearOnAction(actionEvent);
            }else {
                new Alert(Alert.AlertType.ERROR, "Item Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        txtCategory.clear();
        txtQty.clear();
    }

    public Item collectData(){
        Item item = new Item();
        item.setId(Integer.parseInt(txtId.getText()));
        item.setName(txtName.getText());
        item.setPrice(Double.parseDouble(txtPrice.getText()));
        item.setCategory(txtCategory.getText());
        item.setQty(Integer.parseInt(txtQty.getText()));
        return item;
    }


    public void setData(Item item){
        txtId.setText(String.valueOf(item.getId()));
        txtName.setText(item.getName());
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtCategory.setText(item.getCategory());
        txtQty.setText(String.valueOf(item.getQty()));
    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void setTableData(){
        try {
            setCellValueFactory();
            List<Item> allItems = ItemModel.getAllItems();
            ArrayList<ItemTM> objects = new ArrayList<>();
            for (Item allItem : allItems) {
                ItemTM itemTM = new ItemTM();
                itemTM.setId(allItem.getId());
                itemTM.setName(allItem.getName());
                itemTM.setPrice(allItem.getPrice());
                itemTM.setCategory(allItem.getCategory());
                itemTM.setQty(allItem.getQty());
                objects.add(itemTM);
            }
            tblItems.setItems(FXCollections.observableArrayList(objects));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void txtSearchItemOnAction(ActionEvent actionEvent) {

        if (!Regex.setTextColor(TextFields.INTEGER, txtId)){
            new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            return;
        }

        String text = txtId.getText();
        try {
            Item item = ItemModel.searchItem(Integer.parseInt(text));
            setData(item);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void txtItemIdOKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtId);
    }

    public void txtItemNameOKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.TEXT_SPACE_NUMBER,txtName);
    }

    public void txtItemPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.DOUBLE,txtPrice);
    }

    public void txtItemCategoryOKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.TEXT_SPACE_NUMBER,txtCategory);
    }

    public void txtItemQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.INTEGER,txtQty);
    }

    public boolean validateData(){
        return Regex.isTextFieldValid(TextFields.INTEGER,txtId.getText()) &&
                Regex.isTextFieldValid(TextFields.TEXT_SPACE_NUMBER,txtName.getText()) &&
                Regex.isTextFieldValid(TextFields.DOUBLE,txtPrice.getText()) &&
                Regex.isTextFieldValid(TextFields.TEXT_SPACE_NUMBER,txtCategory.getText()) &&
                Regex.isTextFieldValid(TextFields.INTEGER,txtQty.getText());
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

    }
}