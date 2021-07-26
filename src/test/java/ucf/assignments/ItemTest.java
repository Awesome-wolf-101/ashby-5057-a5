/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Joshua Ashby
 */
package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {


    @Test
    void getValue() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item's value is returned
        boolean actual = item.getValue().equals("$4.00");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    //test if you can change a value(requirement #5)
    @Test
    void setValue() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new value
        item.setValue("$5.00");
        //make a boolean variable to see if the item has the new value
        boolean actual = item.getValue().equals("$5.00");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getSerialNumber() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item's Serial Number is returned
        boolean actual = item.getSerialNumber().equals("0123456789");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    //test if you can change a value(requirement #6)
    @Test
    void setSerialNumber() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new Serial Number
        item.setSerialNumber("01234567891");
        //make a boolean variable to see if the item has the new Serial Number
        boolean actual = item.getSerialNumber().equals("01234567891");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getName() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item's Name is returned
        boolean actual = item.getName().equals("Pizza Slice");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    //test if you can change a value(requirement #7)
    @Test
    void setName() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new Name
        item.setName("Doritos");
        //make a boolean variable to see if the item has the new Name
        boolean actual = item.getName().equals("Doritos");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }
}