package models;

/**
 * Creates a class that extends the Part class
 */

public class InHouse extends Part {

    /**
     * The Machine id.
     * Machine ID to identify each part
     */

    private int machineId;

    /**
     * Instantiates a new In house.
     *
     * @param id        the id
     * @param name      the name
     * @param price     the price
     * @param stock     the stock
     * @param min       the min
     * @param max       the max
     * @param machineId the machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machine id.
     * Sets the value of machineID to whatever value it receives as input from its caller
     * @param machineId the machine id
     */

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets machine id.
     * Returns the current value of machineID so that other methods can use this information
     * @return the machine id
     */

    public int getMachineId() {
        return machineId;
    }
}
