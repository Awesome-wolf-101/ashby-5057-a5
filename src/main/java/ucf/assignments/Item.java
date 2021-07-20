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

    public void setValue(String value) {
        Value = value;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


}
