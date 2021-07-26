This application is dedicated to Rey.
=======


*  UCF COP3330 Summer 2021 Assignment 5 Solution
*  Copyright 2021 Joshua Ashby
   


Inventory Manager
=======
Thank you for using my application!
This application manages Inventory.
Within this document you will find instructions
on how to use this application.



Add an Item to the list
-----------

In order to add an item enter the New item's desired value
in the "New Item Value" textfield, the New item's desired serial number
in the "New Item serial number" textfield, and the New item's desired name
in the "New Item Name" textfield then press the "Add item" button.
The new item will be marked as incomplete by default.
(Note: Each inventory item shall have a value representing its monetary value in US dollars
(where a $ is before the value, as in $4.00 and not 4.00) Each inventory item shall have a 
unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit. 
Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
(Note: If the requirements for any of the attributes above are not
met, a new item will not be added to the list and an error message will appear)

Remove an item from the list
-----------
In order to remove an item from a list, click the item you wish to delete
then click the "Remove Currently Clicked Item" button.

Edit the value of an existing inventory item
-----------
To edit  the value of an existing inventory item, double click the value
you wish to edit, then type what you would like the new value to be, and press enter to
edit the value.
(Note: each value should have its monetary value represented in US dollars
(where a $ is before the value, as in $4.00 and not 4.00)
(Note: If the requirements for the value are not
met the item will not be edited and an error message will appear)

Edit the serial number of an existing inventory item
-----------
To edit  the serial number of an existing inventory item, double click the value
you wish to edit, then type what you would like the new serial number to be, and press
enter to edit the serial number.
(Note: Each inventory item shall have a unique serial number in the format of XXXXXXXXXX 
where X can be either a letter or digit.)
(Note: If the requirements for the value are not
met the item will not be edited and an error message will appear)

Edit the name  of an existing inventory item
-----------
To edit  the name of an existing inventory item, double click the name
you wish to edit, then type what you would like the new name  to be, and press enter to
edit the name .
(Note: a name should be between 2 and 256 characters in length (inclusive))
(Note: If the requirements for the value are not
met the item will not be edited and an error message will appear)

Sort The inventory items by value
-----------
To sort the list by value, click the "value" column in table view.
You also have the option of choosing the "value" option from the combobox 
next to the "Sort by" Button, and then clicking the "Sort by" Button.

Sort The inventory items by serial number
-----------
To sort the list by serial number, click the "Serial Number" column in table view.
You also have the option of choosing the "SerialNumber" option from the combobox
next to the "Sort by" Button, and then clicking the "Sort by" Button.

Sort The inventory items by name
-----------
To sort the list by name, click the "name" column in table view.
You also have the option of choosing the "Name" option from the combobox
next to the "Sort by" Button, and then clicking the "Sort by" Button.

Search for an inventory item by serial number
-----------
To Search for an inventory item by serial number, enter the 
 string you would like to search by in the textfield labelled
"Search Bar:". Then use the combobox next to the "Search by" Button
and select the "SerialNumber" option, then click the "Search by" Button.
(Note: As of writing you may not edit an item that was searched, you may however
click the "Show All Inventory Items" button to show all inventory items)

Search for an inventory item by name
-----------
To Search for an inventory item by name, enter the
the string you would like to search by in the textfield labelled
"Search Bar:". Then use the combo box next to the "Search by" Button
and select the "Name" option, then click the "Search by" Button.
(Note: As of writing you may not edit an item that was searched, you may however
click the "Show All Inventory Items" button to show all inventory items)

Save Inventory items to a file
-----------
To save an inventory item to a file, first use the combobox next to the
"Save Inventory As" button to select a type of file to save the list to. 
This application currently supports  TSV, HTML, and JSON formats. Then enter the 
pathname of where you would like to store the item in the text field labelled 
"Pathname:". Then enter the name of the file (without any extentions) in the
text field labelled "File name:". Finally click the "Save Inventory As" button.

Load inventory items from a file that was previously created by the application.
-----------
To load inventory items from a file that was previously created by the application,
first enter the pathname in the text field labelled "Pathname:". Then enter the name 
of the file (with any extentions) in the text field labelled "File name:". Finally click 
the "Load A List" button.