package lk.ijse.Sarasavi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.Employee;
import lk.ijse.Sarasavi.dto.tm.EmployeeTM;
import lk.ijse.Sarasavi.model.EmployeeModel;
import lk.ijse.Sarasavi.util.Regex;
import lk.ijse.Sarasavi.util.TextFields;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeFormCntroller {

    public TableView<EmployeeTM> tblEmployee;
    public TableColumn<EmployeeTM,String> e_id;
    public TableColumn<EmployeeTM,String> e_name;
    public TableColumn<EmployeeTM,String> e_address;
    public TableColumn<EmployeeTM,Integer> e_number;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;


    public void initialize(){
        setCellValueFactory();
        loadTable();
    }

    private void setCellValueFactory() {
        e_id.setCellValueFactory(new PropertyValueFactory<>("name"));
        e_name.setCellValueFactory(new PropertyValueFactory<>("id"));
        e_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        e_number.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Invalid Data").show();
            return;
        }

        Employee employee = collectData();
        try {
            boolean b = EmployeeModel.saveEmployee(employee);
            if (b) {
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Employee Added").show();
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
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtNumber.clear();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES).showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            try {
                boolean b = EmployeeModel.deleteEmployee(id);
                if (b) {
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "No Employee Found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Operation Canceled").show();
        }
    }

    public Employee collectData(){
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtNumber.getText();


        Employee employee = new Employee();
        employee.setEId(id);
        employee.setEName(name);
        employee.setEAddress(address);
        employee.setContactNumber(Integer.parseInt(mobile));
        return employee;
    }

    public void loadTable(){
        try {
            List<Employee> allEmployees = EmployeeModel.getAllEmployees();
            List<EmployeeTM> tm = new ArrayList<>();
            for (Employee allEmployee : allEmployees) {
                EmployeeTM employeeTM = new EmployeeTM();
                employeeTM.setId(allEmployee.getEName());
                employeeTM.setName(allEmployee.getEId());
                employeeTM.setAddress(allEmployee.getEAddress());
                employeeTM.setContactNumber(allEmployee.getContactNumber());
                tm.add(employeeTM);
            }
            tblEmployee.setItems(FXCollections.observableArrayList(tm));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!validateData()){
            new Alert(Alert.AlertType.ERROR, "Invalid Data").show();
            return;
        }

        Employee employee = collectData();
        try {
            boolean b = EmployeeModel.updateEmployee(employee);
            if (b) {
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }


    public void setData(Employee employee){
        txtId.setText(employee.getEId());
        txtName.setText(employee.getEName());
        txtAddress.setText(employee.getEAddress());
        txtNumber.setText(String.valueOf(employee.getContactNumber()));
    }

    public void txtIdSearchOnAction(ActionEvent actionEvent) {
        int E_id = Integer.parseInt(txtId.getText());
        try {
            Employee employee = EmployeeModel.searchEmployee(String.valueOf(E_id));
            if (employee == null) {
                new Alert(Alert.AlertType.ERROR, "No Employee Found").show();
                return;
            }
            setData(employee);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void txtEidOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID,txtId);
    }

    public void txtEnameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME,txtName);
    }

    public void txtEaddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ADDRESS,txtAddress);
    }

    public void txtEnumberOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE,txtNumber);
    }

    public boolean validateData(){
        return Regex.isTextFieldValid(TextFields.ID,txtId.getText()) &&
                Regex.isTextFieldValid(TextFields.NAME,txtName.getText()) &&
                Regex.isTextFieldValid(TextFields.ADDRESS,txtAddress.getText()) &&
                Regex.isTextFieldValid(TextFields.PHONE,txtNumber.getText());
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/report/employeedetails.jrxml");
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