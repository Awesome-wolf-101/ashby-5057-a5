package ucf.assignments;

public class Item {
    String Value = "";
    String SerialNumber = "";
    String Name = "";

    public Item(String Value, String SerialNumber, String Name)
    {
        this.Value = Value;
        this.SerialNumber = SerialNumber;
        this.Name = Name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


}
