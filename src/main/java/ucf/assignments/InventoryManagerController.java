package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.cell.TextFieldTableCell;

public class InventoryManagerController implements Initializable {
    //ObservableList<Item> items = FXCollections.observableArrayList();
    private final ObservableList<Item> items =
            FXCollections.observableArrayList();
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
    public TextField SearchTextField;

    //InventoryManagerTableView<Item>;
    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each column to a new value using .setCellValueFactory
        ValueTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Value"));
        SerialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("SerialNumber"));
        NameTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        SortByComboBox.getItems().add("Value");
        SortByComboBox.getItems().add("SerialNumber");
        SortByComboBox.getItems().add("Name");
        SearchByComboBox.getItems().add("SerialNumber");
        SearchByComboBox.getItems().add("Name");
        InventoryManagerTableView.setEditable(true);
        ValueTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        SerialNumberTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        NameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
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
    public void RemoveItemButtonClicked(ActionEvent actionEvent) {
        int thingToDelete = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        DeleteAnItem(items, items, thingToDelete);
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void ValueTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent) {
        int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        String NewValue = cellEditEvent.getNewValue();
        if(checkValue(NewValue))
        {
            items.get(thingToEdit).setValue(cellEditEvent.getNewValue());
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
    public void SerialNumberTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent) {
            int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
            String NewSerialNumber = cellEditEvent.getNewValue();
            if(checkSerialNumberLength(NewSerialNumber))
            {
            items.get(thingToEdit).setSerialNumber(NewSerialNumber);

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
    public void NameTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent) {
        int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        String NewName = cellEditEvent.getNewValue();
        if(checkNameLength(NewName))
        {
            items.get(thingToEdit).setName(NewName);
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

        //Item SelectedItem =  EdittedCell.getTableView().getItems();
        //SelectedItem.setName(EdittedCell.toString());
        //InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SearchButtonClicked(ActionEvent actionEvent) {
        String value = (String) SearchByComboBox.getValue();
        String SearchString = SearchTextField.getText();
        if(value.equals("SerialNumber"))
        {
            ObservableList<Item> SearchedList = SearchbySerialNumber(items, SearchString);
            InventoryManagerTableView.getItems().setAll(SearchedList);
        }
        else if(value.equals("Name"))
        {
            ObservableList<Item> SearchedList = SearchbyName(items, SearchString);
            InventoryManagerTableView.getItems().setAll(SearchedList);
        }
        //InventoryManagerTableView.getItems().setAll(items);

    }

    @FXML
    public void SaveInventoryButtonClicked(ActionEvent actionEvent) {
        String DataString =  PutDataToHTMLString(items, "ListName");
        String DataString2 = PutDataToTSVString(items, "ListName");
        System.out.println(DataString2);

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

        return value.matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$");
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

    public void DeleteAnItem(ObservableList<Item> list, ObservableList<Item> list2, int currentDeleteIndex)
    {
        //if both passed lists are the same
        if(list.equals(list2))
        {
            //remove the item from the list
            list.remove(currentDeleteIndex);
        }
    }

    public void DataToHTMLFile(ObservableList<Item> list, String Pathname, String Data)
    {


    }

    public String PutDataToHTMLString(ObservableList<Item> datalist,String ListName)
    {
        //initialize an output string
        String OutputString = "<!DOCTYPE html>\n" +
                "<html>\n" + "<head>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "</head>" +
                "<body>\n" +
                "<h2>" + ListName +"</h2>\n" +
                "<table style=\"width:100%\">\n"  + "  <tr>\n" +
                "    <th>Value</th>\n" +
                "    <th>SerialNumber</th> \n" +
                "    <th>Name</th>\n" +
                "  </tr>\n";

        //make a for loop
        for(int i = 0; i < datalist.size(); i++)
        {
            OutputString += "<tr>\n";
            //for all elements of the datalist make a temporary string to add to the output string
            String TempString = "<td>" + datalist.get(i).getValue() +  "</td>\n" +"<td>" + datalist.get(i).getSerialNumber() +  "</td>\n" + "<td>" + datalist.get(i).getName() +  "</td>\n";
            //add the temporary string to the output string

            OutputString += TempString;
            OutputString += "</tr>\n";
        }

        OutputString += "</table>\n" +
                "</body>\n" +
                "</html>";
        //return the output string
        return OutputString;
    }

    public String PutDataToTSVString(ObservableList<Item> datalist,String ListName)
    {
        String OutputString = "";

        for(int i = 0; i < datalist.size(); i++)
        {

            //for all elements of the datalist make a temporary string to add to the output string
            String TempString = datalist.get(i).getValue() +  "\t" + datalist.get(i).getSerialNumber() +  "\t" + datalist.get(i).getName() +  "\n";
            //add the temporary string to the output string
            OutputString += TempString;
        }

        return OutputString;

    }

    public ObservableList<Item> SearchbyName(ObservableList<Item> list, String SearchString)
    {
        ObservableList<Item> TempList =  FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getName().contains(SearchString))
            {
                TempList.add(list.get(i));
            }
        }
        return TempList;
    }

    public ObservableList<Item> SearchbySerialNumber(ObservableList<Item> list, String SearchString)
    {
        ObservableList<Item> TempList =  FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getSerialNumber().contains(SearchString))
            {
                TempList.add(list.get(i));
            }
        }
        return TempList;
    }


}
