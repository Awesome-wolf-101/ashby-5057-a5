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
    //ObservableList<Item> items = FXCollections.observableArrayList();
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
        SaveInventoryAsComboBox.getItems().add("HTML");
        SaveInventoryAsComboBox.getItems().add("TSV");
        SaveInventoryAsComboBox.getItems().add("JSON");
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
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
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
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
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
                errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
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
            errorAlert.setContentText("Each inventory item shall have a value representing its monetary value in US dollars(where a $ is before the value, as in $4.00 and not 4.00)\n" +
                    "Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit\n" +
                    "Each inventory item shall have a name between 2 and 256 characters in length (inclusive)");
            errorAlert.showAndWait();
        }
        InventoryManagerTableView.getItems().setAll(items);

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
        String value = (String) SaveInventoryAsComboBox.getValue();
        String Pathname = SaveInventoryAsPathNameTextField.getText();
        String Filename = SaveInventoryAsFileNameTextField.getText();
        if(value.equals("HTML"))
        {
            String DataString =  PutDataToHTMLString(items, Filename);
            PutDataToHTMLFile(Filename, DataString, Pathname);
            //System.out.println(PutDataToJsonString(items, "hello"));
            //System.out.println(DataString);
        }
        else if(value.equals("TSV"))
        {
            String DataString2 = PutDataToTSVString(items, Filename);
            PutDataToTSVFile(Filename, DataString2, Pathname);
            //System.out.println(DataString2);
        }
        else if(value.equals("JSON"))
        {
            String DataString2 = PutDataToJsonString(items, Filename);
            PutDataToJsonFile(Filename, DataString2, Pathname);
            //System.out.println(DataString2);
        }
    }

    @FXML
    public void LoadAListButtonClicked(ActionEvent actionEvent) {
        String Pathname = LoadAListPathNameTextField.getText();
        String Filename = LoadAListFileNameTextField.getText();

        String[] arrOfStr = Filename.split("\\.");
        String FileExtension = arrOfStr[arrOfStr.length -1];

        System.out.println(FileExtension);
        FileReader file1R = MakeFileReader(Filename, Pathname);
        if(FileExtension.equals("html"))
        {
            items = LoadAnHTMLList(file1R);
        }
        else
        {
            items = LoadAnTxtList(file1R);
        }
        InventoryManagerTableView.getItems().setAll(items);


    }

    public void AddAnItem(ObservableList<Item> list, String NewValue, String NewSerialNumber, String NewName)
    {
        //create a new item using the item constructor
        Item tempitem = new Item(NewValue, NewSerialNumber, NewName);
        //add that item to the passed list
        list.add(tempitem);
    }

    public void  SortListByValue(ObservableList<Item> myList)
    {
        //make a new comparator for the item object, using the item's due date
        Comparator<Item> studentComparator = Comparator.comparing(Item::getValue);
        //sort the list passed into the function
        myList.sort(studentComparator);
    }

    public void  SortListByName(ObservableList<Item> myList)
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

    public String PutDataToJsonString(ObservableList<Item> datalist,String ListName)
    {
        String OutputString = "{\n"+ "\"" + ListName + "\": [" +"\n" + "";

        for(int i = 0; i < datalist.size(); i++)
        {
            if(i != datalist.size() - 1)
            {
                //for all elements of the datalist make a temporary string to add to the output string
                String TempString = "{\n\t\"Value\":" + "\"" + datalist.get(i).getValue() + "\",\n"  +
                        "\t\"SerialNumber\":" + "\"" + datalist.get(i).getSerialNumber() + "\",\n" +
                        "\t\"Name\":" + "\"" + datalist.get(i).getName() + "\"\n},\n";
                //add the temporary string to the output string
                OutputString += TempString;
            }
            else
            {
                String TempString = "{\n\t\"Value\":" + "\"" + datalist.get(i).getValue() + "\",\n"  +
                        "\t\"SerialNumber\":" + "\"" + datalist.get(i).getSerialNumber() + "\",\n" +
                        "\t\"Name\":" + "\"" + datalist.get(i).getName() + "\"\n}\n";
                //add the temporary string to the output string
                OutputString += TempString;
            }
        }

        return OutputString +"\t]" + "\n}";

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

    public void PutDataToHTMLFile(String UserFileName, String textToOutput, String Pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.txt
        String Pathname2 = Pathname + UserFileName + ".html";
        //create a new file object based on this pathname
        File file4 = new File(Pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(Pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public void PutDataToTSVFile(String UserFileName, String textToOutput, String Pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.txt
        String Pathname2 = Pathname + UserFileName + ".txt";
        //create a new file object based on this pathname
        File file4 = new File(Pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(Pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public void PutDataToJsonFile(String UserFileName, String textToOutput, String Pathname )
    {
        //get the pathname to save to by getting the user's working directory
        //going into the saved lists directory and making/overriting the userfilename.txt
        String Pathname2 = Pathname + UserFileName + ".json";
        //create a new file object based on this pathname
        File file4 = new File(Pathname2);
        //use a try block and a catch block
        try {
            file4.createNewFile();
            //make a file writer
            FileWriter myWriter = new FileWriter(Pathname2);
            //write the output text to the file writer
            myWriter.write(textToOutput);
            //close the file writer
            myWriter.close();
        } catch (IOException e) {
            //print out the error if the try block fails
            e.printStackTrace();
        }
    }

    public ObservableList<Item> LoadAnHTMLList( FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine())
        {
            String ItemString = sc.nextLine();
            if(ItemString.contains("$"))
            {
                String[] arrOfStr = ItemString.split(" ");
                String NewValue = arrOfStr[0].substring(4);
                System.out.println(NewValue);
                String ItemString2 = sc.nextLine();
                String[] arrOfStr2 = ItemString2.split(" ");
                String NewSerialNumber = arrOfStr2[0].substring(4);
                System.out.println(NewSerialNumber);
                String ItemString3 = sc.nextLine();
                String[] arrOfStr3 = ItemString3.split(" ");
                String NewName = arrOfStr3[0].substring(4) + " " + GetLastString(arrOfStr3);
                System.out.println(NewName);
                Item tempitem = new Item(NewValue, NewSerialNumber, NewName);
                templist.add(tempitem);
            }
        }
        //return the temporary list
        return templist;
    }

    public FileReader MakeFileReader(String ListNameToLoad, String Pathname) {
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

    public ObservableList<Item> LoadAnTxtList( FileReader file1R)
    {
        //make a new scanner
        Scanner sc = new Scanner(file1R);
        //make a temporarylist to store the read data
        ObservableList<Item> templist = FXCollections.observableArrayList();
        //while the file still has lines
        while(sc.hasNextLine())
        {
            String ItemString = sc.nextLine();
            String[] arrOfStr = ItemString.split("\t");
            String NewValue = arrOfStr[0];
            String NewSerialNumber = arrOfStr[1];
            String NewName = arrOfStr[2];
            Item tempitem = new Item(NewValue, NewSerialNumber, NewName);
            templist.add(tempitem);
        }
        //return the temporary list
        return templist;
    }

    public  String GetLastString(String [] strarr)
    {
        //make an output string
        String OutputString = "";
        //start at the third index of the string array and loop
        //through the array using a for loop
        for(int i = 1; i < strarr.length; i++)
        {
            if(!strarr[i].contains("</td>"))
            {
                //add each string to the output string while also adding a space
                OutputString += strarr[i] + " ";
            }

        }
        //return the output string
        return OutputString;
    }

}


