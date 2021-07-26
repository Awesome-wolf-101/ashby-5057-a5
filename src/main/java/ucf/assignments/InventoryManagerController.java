/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Joshua Ashby
 */
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
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

public class InventoryManagerController implements Initializable {
    private  ObservableList<Item> items =
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
    public ComboBox SaveInventoryAsComboBox;
    public TextField SaveInventoryAsPathNameTextField;
    public TextField SaveInventoryAsFileNameTextField;
    public TextField LoadAListPathNameTextField;
    public TextField LoadAListFileNameTextField;

    @FXML
    public void initialize(URL url, ResourceBundle rb)
    {
        //set each column to a new value using .setCellValueFactory
        ValueTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Value"));
        SerialNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("SerialNumber"));
        NameTableColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
        //add the appropriate values to the comboboxes
        SortByComboBox.getItems().add("Value");
        SortByComboBox.getItems().add("SerialNumber");
        SortByComboBox.getItems().add("Name");
        SearchByComboBox.getItems().add("SerialNumber");
        SearchByComboBox.getItems().add("Name");
        SaveInventoryAsComboBox.getItems().add("HTML");
        SaveInventoryAsComboBox.getItems().add("TSV");
        SaveInventoryAsComboBox.getItems().add("JSON");
        //make the table editable
        InventoryManagerTableView.setEditable(true);
        ValueTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        SerialNumberTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        NameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    public void AddItemButtonClicked(ActionEvent actionEvent)
    {
        //get the values needed for the new item
        String newValue = AddItemNewValueTextField.getText();
        String newSerialNumber = AddItemNewSerialNumberTextField.getText();
        String newName = AddItemNewNameTextField.getText();
        //use the auxillary functions to make sure that all of the user's values are valid
        if(checkNameLength(newName) && checkSerialNumberLength(newSerialNumber) && checkValue(newValue) && !doesSerialNumberAlreadyExist(items ,newSerialNumber))
        {
            //add an item to the list
            addAnItem(items, newValue, newSerialNumber, newName);
        }
        else
        {
            //make an alert if the user's values are not valid
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
                    "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                    "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
            errorAlert.showAndWait();
        }
        //show the list
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SortButtonClicked(ActionEvent actionEvent)
    {
        //figure out what the user chose to sort by
        String userValue = (String) SortByComboBox.getValue();
        //depending on what the user choose call the appropriate function
        if(userValue.equals("Value"))
        {
            sortListByValue(items);
        }
        else if(userValue.equals("SerialNumber"))
        {
            sortListBySerialNumber(items);
        }
        else if(userValue.equals("Name"))
        {
            sortListByName(items);
        }
        //show the sorted list
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void RemoveItemButtonClicked(ActionEvent actionEvent)
    {
        //get the index to be deleted
        int thingToDelete = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        //call the delete function
        deleteAnItem(items, items, thingToDelete);
        //show the list
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void ValueTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent)
    {
        //get the index that is being edited
        int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        //get the value the user inputted
        String NewValue = cellEditEvent.getNewValue();
        //check to make sure the value is valid and update the item, otherwise show an alert
        if(checkValue(NewValue))
        {
            items.get(thingToEdit).setValue(cellEditEvent.getNewValue());
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
                    "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                    "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
            errorAlert.showAndWait();
        }
        //show the list
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SerialNumberTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent)
    {
            //get the index to edit and the user value
            int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
            String newSerialNumber = cellEditEvent.getNewValue();
            //check to make sure that the serial number is valid and update the item, otherwise show an alert
            if(checkSerialNumberLength(newSerialNumber) && !doesSerialNumberAlreadyExist(items, newSerialNumber))
            {
                items.get(thingToEdit).setSerialNumber(newSerialNumber);
            }
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input not valid");
                errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
                        "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                        "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
                errorAlert.showAndWait();
            }
            //show the list
            InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void NameTableColumnEditted(TableColumn.CellEditEvent<Item, String> cellEditEvent)
    {
        //get the index to edit and the user value
        int thingToEdit = InventoryManagerTableView.getSelectionModel().getSelectedIndex();
        String newName = cellEditEvent.getNewValue();
        //check to see if the new name is valid and update the item, otherwise show an alert
        if(checkNameLength(newName))
        {
            items.get(thingToEdit).setName(newName);
        }
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
                    "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                    "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
            errorAlert.showAndWait();
        }
        //show the list
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void SearchButtonClicked(ActionEvent actionEvent)
    {
        //get the values from the user
        String value = (String) SearchByComboBox.getValue();
        String searchString = SearchTextField.getText();
        //based on user input call the appropriate search function and display the new list
        if(value.equals("SerialNumber"))
        {
            ObservableList<Item> SearchedList = searchBySerialNumber(items, searchString);
            InventoryManagerTableView.getItems().setAll(SearchedList);
        }
        else if(value.equals("Name"))
        {
            ObservableList<Item> SearchedList = searchByName(items, searchString);
            InventoryManagerTableView.getItems().setAll(SearchedList);
        }
        

    }

    @FXML
    public void SaveInventoryButtonClicked(ActionEvent actionEvent)
    {
        //get the values form the user
        String value = (String) SaveInventoryAsComboBox.getValue();
        String pathName = SaveInventoryAsPathNameTextField.getText();
        String fileName = SaveInventoryAsFileNameTextField.getText();
        //call the appropriate functions based on the users input
        if(value.equals("HTML"))
        {
            String DataString =  putDataToHTMLString(items, fileName);
            putDataToHTMLFile(fileName, DataString, pathName);
        }
        else if(value.equals("TSV"))
        {
            String DataString2 = putDataToTSVString(items, fileName);
            putDataToTSVFile(fileName, DataString2, pathName);
        }
        else if(value.equals("JSON"))
        {
            String DataString2 = putDataToJsonString(items, fileName);
            putDataToJsonFile(fileName, DataString2, pathName);
        }
    }

    @FXML
    public void LoadAListButtonClicked(ActionEvent actionEvent)
    {
        //get the values form the user
        String Pathname = LoadAListPathNameTextField.getText();
        String Filename = LoadAListFileNameTextField.getText();
        //find the file extension
        String[] arrOfStr = Filename.split("\\.");
        String FileExtension = arrOfStr[arrOfStr.length -1];
        System.out.println(FileExtension);
        //make a file reader
        FileReader file1R = makeFileReader(Filename, Pathname);
        //based on the file extension choose the correct function
        //and set items equal to the result of that function
        if(FileExtension.equals("html"))
        {
            items = loadAnHTMLList(file1R);
        }
        else if(FileExtension.equals("txt"))
        {
            items = loadAnTxtList(file1R);
        }
        else if(FileExtension.equals("json"))
        {
            items = loadAJSONList(file1R);
        }
        //set the teble view to items
        InventoryManagerTableView.getItems().setAll(items);
    }

    @FXML
    public void ShowAllInventoryItemsClicked(ActionEvent actionEvent)
    {
        //show all the items
        InventoryManagerTableView.getItems().setAll(items);
    }

    public void addAnItem(ObservableList<Item> list, String newValue, String newSerialNumber, String newName)
    {
        //create a new item using the item constructor
        Item tempitem = new Item(newValue, newSerialNumber, newName);
        //add that item to the passed list
        list.add(tempitem);
    }

    public void sortListByValue(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's value
        Comparator<Item> studentComparator = Comparator.comparing(Item::getValue);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public void sortListByName(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's name
        Comparator<Item> studentComparator = Comparator.comparing(Item::getName);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public static void sortListBySerialNumber(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's serial number
        Comparator<Item> studentComparator = Comparator.comparing(Item::getSerialNumber);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public  boolean checkNameLength(String description)
    {
        //if the description is within 2 and 256 characters return true otherwise
        //return false
        if(description.length() >= 2 && description.length() <= 256)
        {
            return true;
        }
        return false;
    }

    public boolean checkSerialNumberLength(String description)
    {
        //if the passed string is not 10 characters long or does not
        //contain only letters and numbers return false, otherwise return true
        if(description.length() != 10)
        {
            return false;
        }
        if(!description.matches("[a-zA-Z0-9]*"))
        {
            return false;
        }
        return true;
    }

    public boolean checkValue(String value)
    {
        //make sure the value matches the regex
        return value.matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$");
    }

    public boolean doesSerialNumberAlreadyExist(ObservableList<Item> myList, String serialNumberGiven)
    {
        //if the serial number given already exists in the passed list return true
        //otherwise return false
        for(int i = 0; i < myList.size(); i++)
        {
            if(myList.get(i).getSerialNumber().equals(serialNumberGiven))
            {
                return true;
            }
        }
        return false;
    }

    public void deleteAnItem(ObservableList<Item> list, ObservableList<Item> list2, int currentDeleteIndex)
    {
        //if both passed lists are the same
        if(list.equals(list2))
        {
            //remove the item from the list
            list.remove(currentDeleteIndex);
        }
    }

    public String putDataToHTMLString(ObservableList<Item> datalist, String listName)
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
                "<h2>" + listName +"</h2>\n" +
                "<table style=\"width:100%\">\n"  + "<tr>\n" +
                "<th>Value</th>\n" +
                "<th>SerialNumber</th> \n" +
                "<th>Name</th>\n" +
                "</tr>\n";

        //make a for loop
        for(int i = 0; i < datalist.size(); i++)
        {
            OutputString += "<tr>\n";
            //for all elements of the datalist make a temporary string to add to the output string
            String TempString = "<td>" + datalist.get(i).getValue() +  " </td>\n" +"<td>" + datalist.get(i).getSerialNumber() +  " </td>\n" + "<td>" + datalist.get(i).getName() +  " </td>\n";
            //add the temporary string to the output string
            OutputString += TempString;
            OutputString += "</tr>\n";
        }

        OutputString += "</table>\n" +
                "</body>\n" +
                "</html>";
        //return the output string
        String Pathname3 = System.getProperty("user.dir") + "\\\\" + "SavedLists"+ "\\\\";
        System.out.println(Pathname3);
        return OutputString;
    }

    public String putDataToTSVString(ObservableList<Item> datalist, String listName)
    {
        //initialize an output string
        String outputString = "";
        for(int i = 0; i < datalist.size(); i++)
        {
            //for all elements of the datalist make a temporary string to add to the output string
            String tempString = datalist.get(i).getValue() +  "\t" + datalist.get(i).getSerialNumber() +  "\t" + datalist.get(i).getName() +  "\n";
            //add the temporary string to the output string
            outputString += tempString;
        }
        //return the output sting
        return outputString;
    }

    public String putDataToJsonString(ObservableList<Item> datalist, String listName)
    {
        //create the begining of an output string
        String outputString = "{\n"+ "\"" + listName + "\": [" +"\n" + "";
        //add every peice of data to the output string
        for(int i = 0; i < datalist.size(); i++)
        {
            if(i != datalist.size() - 1)
            {
                //for all elements of the datalist make a temporary string to add to the output string
                String tempString = "{\n\t\"Value\":" + "\"" + datalist.get(i).getValue() + "\",\n"  +
                        "\t\"SerialNumber\":" + "\"" + datalist.get(i).getSerialNumber() + "\",\n" +
                        "\t\"Name\":" + "\"" + datalist.get(i).getName() + "\"\n},\n";
                //add the temporary string to the output string
                outputString += tempString;
            }
            else
            {
                String TempString = "{\n\t\"Value\":" + "\"" + datalist.get(i).getValue() + "\",\n"  +
                        "\t\"SerialNumber\":" + "\"" + datalist.get(i).getSerialNumber() + "\",\n" +
                        "\t\"Name\":" + "\"" + datalist.get(i).getName() + "\"\n}\n";
                //add the temporary string to the output string
                outputString += TempString;
            }
        }
        //return the output string
        return outputString +"\t]" + "\n}";
    }

    public ObservableList<Item> searchByName(ObservableList<Item> list, String searchString)
    {
        //create a temporary list
        ObservableList<Item> tempList =  FXCollections.observableArrayList();
        //if the list contains part of te search string, add it to the temporary list
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getName().contains(searchString))
            {
                tempList.add(list.get(i));
            }
        }
        //return the temporary list
        return tempList;
    }

    public ObservableList<Item> searchBySerialNumber(ObservableList<Item> list, String searchString)
    {
        //create a temporary string
        ObservableList<Item> tempList =  FXCollections.observableArrayList();
        //if the list contains part of te search string, add it to the temporary list
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getSerialNumber().contains(searchString))
            {
                tempList.add(list.get(i));
            }
        }
        //return the temporary list
        return tempList;
    }

    public void putDataToHTMLFile(String userFileName, String textToOutput, String pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.html
        String pathname2 = pathname + userFileName + ".html";
        //create a new file object based on this pathname
        File file4 = new File(pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public void putDataToTSVFile(String userFileName, String textToOutput, String pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.txt
        String pathname2 = pathname + userFileName + ".txt";
        //create a new file object based on this pathname
        File file4 = new File(pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public void putDataToJsonFile(String userFileName, String textToOutput, String pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.json
        String pathname2 = pathname + userFileName + ".json";
        //create a new file object based on this pathname
        File file4 = new File(pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public ObservableList<Item> loadAnHTMLList(FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine())
        {
            //make a string per line
            String ItemString = sc.nextLine();
            //if the string contains a $, get the next values
            //and make a new item to add to the temporary list
            if(ItemString.contains("$"))
            {
                String[] arrOfStr = ItemString.split(" ");
                String newValue = arrOfStr[0].substring(4);
                String itemString2 = sc.nextLine();
                String[] arrOfStr2 = itemString2.split(" ");
                String newSerialNumber = arrOfStr2[0].substring(4);
                String itemString3 = sc.nextLine();
                String[] arrOfStr3 = itemString3.split(" ");
                //call get last string to extract the part of the string we want
                String newName = arrOfStr3[0].substring(4) + " " + getLastString(arrOfStr3);
                Item tempitem = new Item(newValue, newSerialNumber, newName);
                templist.add(tempitem);
            }
        }
        //return the temporary list
        return templist;
    }

    public FileReader makeFileReader(String ListNameToLoad, String Pathname)
    {
        String Pathname2 = Pathname + "\\\\" + ListNameToLoad;
        //make a file object based on this pathname
        File file5 = new File(Pathname2);
        //if the file with the given pathname exists
        if (file5.exists()) {
            //use a try catch block
            try {
                //make a file reader to read from the file
                FileReader file1R = new FileReader(Pathname2);
                return file1R;
                //set the data list to the list returned by the LoadAList function
            }
            //if the try block fails
            catch (IOException e) {
                //print the exception
                e.printStackTrace();
            }
        }
       return null;
    }

    public ObservableList<Item> loadAnTxtList(FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine())
        {
            //split the string by tab get the values
            //and turn those values into a new item
            //then add the new item to the temporary list
            String itemString = sc.nextLine();
            String[] arrOfStr = itemString.split("\t");
            String newValue = arrOfStr[0];
            String newSerialNumber = arrOfStr[1];
            String newName = arrOfStr[2];
            Item tempitem = new Item(newValue, newSerialNumber, newName);
            templist.add(tempitem);
        }
        //return the temporary list
        return templist;
    }

    public  String getLastString(String [] strarr)
    {
        //make an output string
        String outputString = "";
        //start at the first index of the string array and loop
        //through the array using a for loop
        for(int i = 1; i < strarr.length; i++)
        {
            //if an array does not contain "</td>" . . .
            if(!strarr[i].contains("</td>"))
            {
                //add each string to the output string while also adding a space
                outputString += strarr[i] + " ";
            }

        }
        //return the output string
        return outputString;
    }

    public ObservableList<Item> loadAJSONList(FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine())
        {
            //make a string per line
            String ItemString = sc.nextLine();
            //if the string contains "Value", get the next values
            //and make a new item to add to the temporary list
            if(ItemString.contains("Value"))
            {
                String[] arrOfStr = ItemString.split(":");
                String newValue = arrOfStr[1].substring(1,6);
                System.out.println(newValue);
                String itemString2 = sc.nextLine();
                String[] arrOfStr2 = itemString2.split(":");
                String newSerialNumber = arrOfStr2[1].substring(1,11);
                System.out.println(newSerialNumber);
                String itemString3 = sc.nextLine();
                String[] arrOfStr3 = itemString3.split(":");
                String newName = arrOfStr3[1].substring(1, arrOfStr3[1].substring(1).length() -1) ;
                System.out.println(newName);
                Item tempitem = new Item(newValue, newSerialNumber, newName);
                templist.add(tempitem);
            }
        }
        //return the temporary list
        return templist;
    }

}