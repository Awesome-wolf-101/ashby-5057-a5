package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.scene.control.*;

public class InventoryManagerController implements Initializable {

    @FXML
    public TableColumn ValueTableColumn;
    public TableColumn SerialNumberTableColumn;
    public TableColumn NameTableColumn;

    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each column to a new value using .setCellValueFactory
        ValueTableColumn.setCellValueFactory(new PropertyValueFactory("DueDate"));
        SerialNumberTableColumn.setCellValueFactory(new PropertyValueFactory("completed"));
        NameTableColumn.setCellValueFactory(new PropertyValueFactory("Description"));
    }
}
