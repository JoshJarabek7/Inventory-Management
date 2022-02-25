package models;

// Creates a new class called Outsource that extends the Part class
public class Outsource extends Part {

    private String companyName;


    // A method that takes in the id, name, price, stock, min, max, and company name for an outsourced part
    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    // A method that sets the value of companyName to whatever you pass it as its argument
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // A method that returns what's passed into it as its argument
    public String getCompanyName() {
        return companyName;
    }
}
