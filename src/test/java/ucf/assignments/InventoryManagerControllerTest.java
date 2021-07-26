/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerControllerTest {

    //test if you can add an item(requirement #3)
    @Test
    void addAnItem() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add an item
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        //check to make sure the description of the first item of the list is the same
        //as the description in AddanItem's parameters
        assertEquals("Candy", data.get(0).getName());
    }

    //test if you can sort a list by value(requirement #8)
    @Test
    void sortListByValue() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add some items
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Candy1");
        controller.addAnItem(data, "$67.00", "0123456781", "Candy2");
        controller.sortListByValue(data);
        //check to make sure the description of the first item of the list is the same
        //as the description in AddanItem's parameters
        assertEquals(true, data.get(0).getValue().equals("$3.00"));
    }

    //test if you can sort a list by value(requirement #10)
    @Test
    void sortListByName() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add some items
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        controller.sortListByName(data);
        //check to make sure the description of the first item of the list is the same
        //as the description in AddanItem's parameters
        assertEquals(true, data.get(0).getName().equals("Apple"));
    }

    //test if you can sort a list by value(requirement #9)
    @Test
    void sortListBySerialNumber() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add some items
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "2345678901", "Candy1");
        controller.addAnItem(data, "$67.00", "1234567890", "Candy2");
        controller.sortListBySerialNumber(data);
        //check to make sure the description of the first item of the list is the same
        //as the description in AddanItem's parameters
        assertEquals(true, data.get(1).getSerialNumber().equals("1234567890"));
    }

    //test if a name has a valid length(part of requirement #2)
    @Test
    void checkNameLength() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        assertEquals(false,controller.checkNameLength("1")) ;
    }

    //test if a serial number has a valid length(part of requirement #2)
    @Test
    void checkSerialNumberLength() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        assertEquals(true, controller.checkSerialNumberLength("0123456789")) ;
    }

    //test if a value is in a valid format(part of requirement #2)
    @Test
    void checkValue() {
        //make a new controller
        InventoryManagerController controller = new InventoryManagerController();
        assertEquals(true,controller.checkValue("$66678.00")) ;

    }

    //test if a serial number already exists in a given list(part of requirement #2)
    @Test
    void doesSerialNumberAlreadyExist() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add some items
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "2345678901", "Candy1");
        controller.addAnItem(data, "$67.00", "1234567890", "Candy2");
        assertEquals(true,controller.doesSerialNumberAlreadyExist(data, "2345678901")) ;

    }

    //test if you can remove an item(requirement #4)
    @Test
    void deleteAnItem() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        //add some items
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "2345678901", "Candy1");
        controller.addAnItem(data, "$67.00", "1234567890", "Candy2");
        controller.deleteAnItem(data, data, 0);
        assertEquals(true,data.size() == 2) ;
    }

    //test if you can put data to an HTML String(part of requirement #13)
    @Test
    void putDataToHTMLString() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        String Actual = controller.putDataToHTMLString(data, "ListName");
        assertEquals("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "</head><body>\n" +
                "<h2>ListName</h2>\n" +
                "<table style=\"width:100%\">\n" +
                "<tr>\n" +
                "<th>Value</th>\n" +
                "<th>SerialNumber</th> \n" +
                "<th>Name</th>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>$4.00 </td>\n" +
                "<td>0123456789 </td>\n" +
                "<td>Candy </td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>",Actual) ;

    }

    //test if you can put data to an TSV String(part of requirement #13)
    @Test
    void putDataToTSVString() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        String Actual = controller.putDataToTSVString(data, "ListName");
        assertEquals("$4.00\t0123456789\tCandy\n",Actual) ;
    }

    //test if you can search a list by Name(requirement #12)
    @Test
    void searchbyName() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        ObservableList<Item> data2 = controller.searchByName(data, "Orange");
        assertEquals("Orange", data2.get(0).getName()) ;
    }

    //test if you can search a list by serial Number(requirement #11)
    @Test
    void searchbySerialNumber() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        ObservableList<Item> data2 = controller.searchByName(data, "Orange");
        assertEquals("0123456781", data2.get(0).getSerialNumber());
    }

    //test if you can put data to an HTML File(part of requirement #13)
    @Test
    void putDataToHTMLFile() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        String StringToOutPut = controller.putDataToHTMLString(data, "ListName");
        //call the put data to file function
        controller.putDataToHTMLFile("New file", StringToOutPut, "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        String Pathname2 = "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\" + "New file" + ".html";
        //make a file object for this new file name
        File file5 = new File(Pathname2);
        //make a boolean variable to see if this file now exists
        boolean actual = file5.exists();
        //check to make sure this boolean variable is true
        assertEquals(true, actual);

    }

    //test if you can put data to an TSV File(part of requirement #13)
    @Test
    void putDataToTSVFile() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        String StringToOutPut = controller.putDataToHTMLString(data, "ListName");
        //call the put data to file function
        controller.putDataToTSVFile("New file", StringToOutPut, "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        String Pathname2 = "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\" + "New file" + ".txt";
        //make a file object for this new file name
        File file5 = new File(Pathname2);
        //make a boolean variable to see if this file now exists
        boolean actual = file5.exists();
        //check to make sure this boolean variable is true
        assertEquals(true, actual);
    }

    //test if you can load an HTML File as a list(part of requirement #14)
    @Test
    void loadAnHTMLList() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        String StringToOutPut = controller.putDataToHTMLString(data, "ListName");
        //call the put data to file function
        controller.putDataToHTMLFile("New file", StringToOutPut, "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        data.clear();
        //get the pathname of the new file
        String Pathname2 = "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\" + "New file" + ".html";
        //make a file reader
        try
        {
            FileReader file1R = new FileReader(Pathname2);
            //call the load a list function and store the result in data
            data = controller.loadAnHTMLList(file1R);
            //check to see if the item at the second index of data has a description
            //equal to it's original description
            assertEquals("Candy ", data.get(0).getName());
        }
        catch (IOException e) {
            //print the exception
            e.printStackTrace();
        }

    }

    //test if you can make a file reader given a pathname and the name of a file to load(part of requirement #14)
    @Test
    void makeFileReader() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        String StringToOutPut = controller.putDataToHTMLString(data, "ListName");
        //call the put data to file function
        controller.putDataToHTMLFile("ListName", StringToOutPut, "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        FileReader file1R = controller.makeFileReader("ListName.html",  "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        assertEquals(true, file1R != null);
    }

    //test if you can load an TSV/Tst File as a list(part of requirement #14)
    @Test
    void loadAnTxtList() {
        InventoryManagerController controller = new InventoryManagerController();
        //make an observable list
        ObservableList<Item> data = FXCollections.observableArrayList();
        controller.addAnItem(data, "$4.00", "0123456789", "Candy");
        controller.addAnItem(data, "$3.00", "0123456780", "Apple");
        controller.addAnItem(data, "$67.00", "0123456781", "Orange");
        String StringToOutPut = controller.putDataToTSVString(data, "ListName");
        //call the put data to file function
        controller.putDataToTSVFile("New file", StringToOutPut, "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\");
        data.clear();
        //get the pathname of the new file
        String Pathname2 = "C:\\Users\\joshu\\IdeaProjects\\untitled\\ashby-5057-a5\\\\" + "New file" + ".html";
        //make a file reader
        try
        {
            FileReader file1R = new FileReader(Pathname2);
            //call the load a list function and store the result in data
            data = controller.loadAnHTMLList(file1R);
            //check to see if the item at the second index of data has a description
            //equal to it's original description
            assertEquals("Candy ", data.get(0).getName());
        }
        catch (IOException e) {
            //print the exception
            e.printStackTrace();
        }

    }

    @Test
    void putDataToJsonString() {
    }

    @Test
    void putDataToJsonFile() {
    }

    @Test
    void getLastString() {
    }

    @Test
    void loadAJSONList() {
    }
}