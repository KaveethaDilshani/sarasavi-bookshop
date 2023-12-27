package lk.ijse.Sarasavi.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import lk.ijse.Sarasavi.dto.AttendesDto;
import lk.ijse.Sarasavi.dto.Employee;

import lk.ijse.Sarasavi.dto.tm.AttendesTM;
import lk.ijse.Sarasavi.model.AttendesModel;
import lk.ijse.Sarasavi.model.EmployeeModel;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendsFormController {
    public TableView<AttendesTM> tblAttendance;
    public TableColumn<AttendesTM, String> colEmpId;
    public TableColumn<AttendesTM, LocalDate> colDate;
    public TableColumn<AttendesTM, String> colWorkingHour;
    public TableColumn<AttendesTM, String> colOtHour;
    @FXML
    private ComboBox<Employee> cmbEmployees;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtOtHours;

    @FXML
    private TextField txtWorkingHours;

    public void initialize() {
        cmbEmployees.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                return employee == null ? "" : employee.getEName();
            }

            @Override
            public Employee fromString(String s) {
                return null;
            }
        });
        try {
            cmbEmployees.setItems(FXCollections.observableArrayList(EmployeeModel.getAllEmployees()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtDate.setValue(LocalDate.now());
        setCell();
        setTable();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        AttendesDto attendesDto = collectData();
        boolean attendanceAddedForToday = AttendesModel.isAttendanceAddedForToday(cmbEmployees.getValue().getEId());
        if (attendanceAddedForToday){
            new Alert(Alert.AlertType.ERROR, "Attendance already added for today").show();
            return;
        }
        boolean b = AttendesModel.saveAttendes(attendesDto);
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Attendes Added").show();
            setTable();
            btnClearOnAction(event);
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtDate.setValue(LocalDate.now());
        txtOtHours.setText("");
        txtWorkingHours.setText("");
        cmbEmployees.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public AttendesDto collectData() {
        AttendesDto attendes = new AttendesDto();
        attendes.setDate(Date.valueOf(txtDate.getValue()));
        attendes.setWorkingHours(txtWorkingHours.getText());
        attendes.setOtHours(txtOtHours.getText());
        attendes.setEmployeeId(cmbEmployees.getValue().getEId());
        return attendes;
    }

    public void setTable(){
        List<AttendesTM> list = new ArrayList<>();
        List<AttendesDto> allAttendesForToday = AttendesModel.getAllAttendesForToday();
        for (AttendesDto attendesDto : allAttendesForToday) {
            AttendesTM attendesTM = new AttendesTM();
            attendesTM.setDate(attendesDto.getDate().toLocalDate());
            attendesTM.setOtHours(attendesDto.getOtHours());
            attendesTM.setWorkingHours(attendesDto.getWorkingHours());
            attendesTM.setEmployeeId(attendesDto.getEmployeeId());
            list.add(attendesTM);
        }
        tblAttendance.setItems(FXCollections.observableArrayList(list));
    }

    public void setCell(){
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colWorkingHour.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colOtHour.setCellValueFactory(new PropertyValueFactory<>("otHours"));
    }

}