package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void getValue() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item is duedate is returned
        boolean actual = item.getValue().equals("$4.00");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setValue() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new due date
        item.setValue("$5.00");
        //make a boolean variable to see if the item has the new due date
        boolean actual = item.getValue().equals("$5.00");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getSerialNumber() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item is duedate is returned
        boolean actual = item.getSerialNumber().equals("0123456789");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setSerialNumber() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new due date
        item.setSerialNumber("01234567891");
        //make a boolean variable to see if the item has the new due date
        boolean actual = item.getSerialNumber().equals("01234567891");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void getName() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //make a boolean variable to see if the item is duedate is returned
        boolean actual = item.getName().equals("Pizza Slice");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }

    @Test
    void setName() {
        //make a new item object
        Item item = new Item("$4.00", "0123456789", "Pizza Slice");
        //set the item to a new due date
        item.setName("Doritos");
        //make a boolean variable to see if the item has the new due date
        boolean actual = item.getName().equals("Doritos");
        //make sure that the boolean variable is true
        assertEquals(true, actual);
    }
}