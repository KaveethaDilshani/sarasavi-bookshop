package lk.ijse.Sarasavi.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import lk.ijse.Sarasavi.dto.CustomerDto;
import lk.ijse.Sarasavi.dto.Item;
import lk.ijse.Sarasavi.dto.OrderDetailDto;
import lk.ijse.Sarasavi.dto.OrderDto;
import lk.ijse.Sarasavi.dto.tm.CartItem;
import lk.ijse.Sarasavi.model.CustomerModel;
import lk.ijse.Sarasavi.model.ItemModel;
import lk.ijse.Sarasavi.model.OrderModel;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class OrderFormcontroller {
    public TableView<CartItem> tblCart;
    public TableColumn<CartItem, Integer> colItemCode;
    public TableColumn<CartItem, String> colDescription;
    public TableColumn<CartItem, Integer> colQty;
    public TableColumn<CartItem, Double> colUnitPrice;
    public TableColumn<CartItem, Double> colSubTotal;
    public JFXComboBox<CustomerDto> cmbCustomers;
    public JFXComboBox<Item> txtItemCode;
    public TextField txtQty;
    public Label txtOrderId;
    public DatePicker txtOrderDate;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtCustomerName;
    public Label lblTotal;


    public void initialize(){
        try {
            txtOrderId.setText(OrderModel.generateNextOrderId()+"");
            setComboBoxes();
            txtOrderDate.setValue(java.time.LocalDate.now());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    }

    public void setComboBoxes(){
        try {
            cmbCustomers.setItems(FXCollections.observableArrayList(CustomerModel.getAllCustomer()));
            txtItemCode.setItems(FXCollections.observableArrayList(ItemModel.getAllItems()));

            cmbCustomers.setConverter(new StringConverter<CustomerDto>() {
                @Override
                public String toString(CustomerDto customerDto) {
                    return customerDto==null?"":customerDto.getName();
                }

                @Override
                public CustomerDto fromString(String s) {
                    return null;
                }
            });

            txtItemCode.setConverter(new StringConverter<Item>() {

                @Override
                public String toString(Item item) {
                    return item==null?"":item.getName();
                }

                @Override
                public Item fromString(String s) {
                    return null;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void cmbItemCodeOnAction(ActionEvent actionEvent) {
        Item selectedItem = txtItemCode.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblDescription.setText(selectedItem.getName());
            lblUnitPrice.setText(String.valueOf(selectedItem.getPrice()));
            lblQtyOnHand.setText(String.valueOf(selectedItem.getQty()));
        }
    }

    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        CustomerDto selectedItem = cmbCustomers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtCustomerName.setText(selectedItem.getName());
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (txtItemCode.getSelectionModel().getSelectedItem() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an item").show();
            return;
        }
        if (txtQty.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity").show();
            return;
        }
        try {
            Integer.parseInt(txtQty.getText());
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Invalid quantity").show();
            return;
        }

        if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(lblQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity").show();
            return;
        }

        for (CartItem item : tblCart.getItems()) {
            if (item.getItemCode()==txtItemCode.getSelectionModel().getSelectedItem().getId()) {
                item.setQuantity(item.getQuantity() + Integer.parseInt(txtQty.getText()));
                txtItemCode.getSelectionModel().getSelectedItem().setQty(txtItemCode.getSelectionModel().getSelectedItem().getQty() - Integer.parseInt(txtQty.getText()));
                item.setSubTotal(item.getQuantity() * item.getUnitPrice());
                clearItemDetails();
                return;
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setItemCode(txtItemCode.getSelectionModel().getSelectedItem().getId());
        cartItem.setDescription(lblDescription.getText());
        cartItem.setQuantity(Integer.parseInt(txtQty.getText()));
        cartItem.setUnitPrice(Double.parseDouble(lblUnitPrice.getText()));
        cartItem.setSubTotal(cartItem.getUnitPrice()*cartItem.getQuantity());
        tblCart.getItems().add(cartItem);
        txtItemCode.getSelectionModel().getSelectedItem().setQty(txtItemCode.getSelectionModel().getSelectedItem().getQty() - Integer.parseInt(txtQty.getText()));
        clearItemDetails();
        setLblTotal();
    }

    public void clearItemDetails(){
        txtItemCode.getSelectionModel().clearSelection();
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        txtQty.setText("");
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        CartItem selectedItem = tblCart.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an item").show();
            return;
        }
        txtItemCode.getSelectionModel().getSelectedItem().setQty(txtItemCode.getSelectionModel().getSelectedItem().getQty() + selectedItem.getQuantity());
        tblCart.getItems().remove(selectedItem);
        setLblTotal();
    }

    public void setLblTotal(){
        double total = 0.0;
        for (CartItem item : tblCart.getItems()) {
            total += item.getSubTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        if (txtCustomerName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Cart is empty").show();
            return;
        }

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?").showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
            ArrayList<OrderDetailDto> objects = new ArrayList<>();
            ObservableList<CartItem> items = tblCart.getItems();
            for (CartItem item : items) {
                OrderDetailDto orderDetailDto = new OrderDetailDto();
                orderDetailDto.setOrderId(Integer.parseInt(txtOrderId.getText()));
                orderDetailDto.setItemId(item.getItemCode());
                orderDetailDto.setUnitPrice(item.getUnitPrice());
                orderDetailDto.setQuantity(item.getQuantity());
                objects.add(orderDetailDto);
            }
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderId(Integer.parseInt(txtOrderId.getText()));
            orderDto.setCustomerId(cmbCustomers.getSelectionModel().getSelectedItem().getId());
            orderDto.setOrderDate(Date.valueOf(LocalDate.now()));
            orderDto.setOrderType("On Line");
            orderDto.setOrderDetailDtos(objects);
            orderDto.setTotal(Double.parseDouble(lblTotal.getText()));


            try {
                if (OrderModel.placeOrder(orderDto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Order placed successfully").show();
                    clearAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }

        }


    }

    private void clearAll() throws SQLException {
        txtOrderId.setText("");
        txtCustomerName.setText("");
        tblCart.getItems().clear();
        lblTotal.setText("0.0");
        setLblTotal();
        txtCustomerName.requestFocus();
        setComboBoxes();
        txtOrderId.setText(OrderModel.generateNextOrderId()+"");
    }
}
