package ucf.assignments;

public class Item {
    double Value = 0;
    String SerialNumber = "";
    String Name = "";

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
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
