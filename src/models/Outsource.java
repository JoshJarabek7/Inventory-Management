package models;

/**
 * Creates a new class called Outsource that extends the Part class
 */
public class Outsource extends Part {

    /**
     * The Company name.
     * The companyName to identify each part
     */
    private String companyName;


    /**
     * Instantiates a new Outsource.
     *
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param stock       the inventory
     * @param min         the min
     * @param max         the max
     * @param companyName the company name
     */
    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Sets company name.
     * A method that sets the value of companyName to whatever you pass it as its argument
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets company name.
     * A method that returns what's passed into it as its argument
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
