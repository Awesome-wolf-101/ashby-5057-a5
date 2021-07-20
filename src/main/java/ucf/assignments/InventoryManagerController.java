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



    ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    public TableView InventoryManagerTableView;
    public TableColumn ValueTableColumn;
    public TableColumn SerialNumberTableColumn;
    public TableColumn NameTableColumn;
    public TextField AddItemNewValueTextField;
    public TextField AddItemNewSerialNumberTextField;
    public TextField AddItemNewNameTextField;
    public Button RemoveItemButton;
    public Button SortButton;
    public Button SearchButton;
    public ComboBox SortByComboBox;
    public ComboBox SearchByComboBox;


    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each column to a new value using .setCellValueFactory
        ValueTableColumn.setCellValueFactory(new PropertyValueFactory("Value"));
        SerialNumberTableColumn.setCellValueFactory(new PropertyValueFactory("SerialNumber"));
        NameTableColumn.setCellValueFactory(new PropertyValueFactory("Name"));
        SortByComboBox.getItems().add("Value");
        SortByComboBox.getItems().add("SerialNumber");
        SortByComboBox.getItems().add("Name");
        SearchByComboBox.getItems().add("SerialNumber");
        SearchByComboBox.getItems().add("Name");

    }


    @FXML
    public void AddItemButtonClicked(ActionEvent actionEvent) {
        String NewValue = AddItemNewValueTextField.getText();
        String NewSerialNumber = AddItemNewSerialNumberTextField.getText();
        String NewName = AddItemNewNameTextField.getText();
        if(checkNameLength(NewName) && checkSerialNumberLength(NewSerialNumber) && checkValue(NewValue) && !doesSerialNumberAlreadyExist(items ,NewSerialNumber))
        {
            AddAnItem(items, NewValue, NewSerialNumber, NewName);
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars\n" +
                    "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                    "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
            errorAlert.showAndWait();
        }


        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SortButtonClicked(ActionEvent actionEvent) {
        String value = (String) SortByComboBox.getValue();
        if(value.equals("Value"))
        {
            SortListByValue(items);
        }
        else if(value.equals("SerialNumber"))
        {
            SortListBySerialNumber(items);
        }
        else if(value.equals("Name"))
        {
            SortListByName(items);
        }

        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SearchButtonClicked(ActionEvent actionEvent) {
    }

    public void AddAnItem(ObservableList<Item> list, String NewValue, String NewSerialNumber, String NewName)
    {
        //create a new item using the item constructor
        Item tempitem = new Item(NewValue, NewSerialNumber, NewName);
        //add that item to the passed list
        list.add(tempitem);
    }

    public static void  SortListByValue(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's due date
        Comparator<Item> studentComparator = Comparator.comparing(Item::getValue);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public static void  SortListByName(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's due date
        Comparator<Item> studentComparator = Comparator.comparing(Item::getName);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public static void  SortListBySerialNumber(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's due date
        Comparator<Item> studentComparator = Comparator.comparing(Item::getSerialNumber);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public  boolean checkNameLength(String description)
    {
        if(description.length() >= 2 && description.length() <= 256)
        {
            return true;
        }
        return false;
    }

    public boolean checkSerialNumberLength(String description)
    {
        if(description.length() == 10)
        {
            return true;
        }
        return false;
    }
    public boolean checkValue(String value)
    {

        return value.matches("^\\$(0|[1-9][0-9]{0,2})(,\\d{3})*(\\.\\d{1,2})?$");
    }

    public boolean doesSerialNumberAlreadyExist(ObservableList<Item> myList, String SerialNumberGiven)
    {
        for(int i = 0; i < myList.size(); i++)
        {
            if(myList.get(i).getSerialNumber().equals(SerialNumberGiven))
            {
                return true;
            }
        }
        return false;
    }
}
