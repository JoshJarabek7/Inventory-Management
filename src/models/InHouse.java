package models;

// Creates a class that extends the Part class
public class InHouse extends Part {

    // Machine ID to identify each part
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    // Sets the value of machineID to whatever value it receives as input from its caller
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    // Returns the current value of machineID so that other methods can use this information
    public int getMachineId() {
        return machineId;
    }
}
