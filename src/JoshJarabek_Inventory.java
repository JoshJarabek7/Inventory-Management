import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;

/**
 *
 * @author Josh Jarabek
 */

/**
 * RUNTIME_ERROR
 * Could not get the FXML loader to work for multiple days
 * I received multiple different runtime errors and nullpointer exceptions
 *
 * Two things were wrong:
 *
 * I had created a stage I had forgotten about on the Main Screen fxml
 * I also was putting the wrong name for the path to the fxml file
 */

/**
 * FUTURE_ENHANCEMENT
 * I tried forever to get the application to grow and shrink with the box resizing, but I couldn't
 * ever perfect it. I'd like for it to be proportional nobody how the user resizes the window.
 * I'd also like to make it more aesthetically pleasing, because it currently looks like an application
 * from Windows 98.
 */

/**
 * Creates the JoshJarabek_Inventory class which serves as the main
 */
public class JoshJarabek_Inventory extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main Screen.fxml"));
        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SAMPLE DATA
        Part a = new InHouse(1,"Frame",79.99,110,1,300,1223);
        Part b = new InHouse(2,"Handlebars",20.00,5,1,8,1224);
        Part c = new Outsource(3,"Wheel",40.00,6,1,10,"Tire Company Inc.");
        Part d = new Outsource(4,"Screws",0.01,300,2,1100,"Hardware Company Inc.");
        Part e = new Outsource(5,"Basket",40.00,7,2,11,"Bicycles Inc.");

        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(c);
        Inventory.addPart(d);
        Inventory.addPart(e);

        Product f = new Product(100, "Bike",299.99, 7, 1, 10);
        Product g = new Product(101, "Basket and Bike Combo",309.99, 9, 1, 15);
        Product h = new Product(102, "New Wheels w/ Parts",84.99, 6, 2, 9);
        Product i = new Product(103, "DIY Rebuild Bike",109.99, 3, 1, 5);

        Inventory.addProduct(f);
        Inventory.addProduct(g);
        Inventory.addProduct(h);
        Inventory.addProduct(i);

        launch(args);
    }

}