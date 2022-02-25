import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.*;

public class JoshJarabek_Inventory extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main Screen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //SAMPLE DATA
        Part a = new InHouse(1,"RAM",99.99,11,1,15,1223);
        Part b = new InHouse(2,"PC Case",150.00,5,1,8,1224);
        Part c = new Outsource(3,"Motherboard",165.99,6,1,10,"ComCases Inc.");
        Part d = new Outsource(4,"GPU",549.00,4,2,6,"Trial Inc.");
        Part e = new Outsource(5,"CPU",350.00,7,2,11,"Trial Inc.");

        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(c);
        Inventory.addPart(d);
        Inventory.addPart(e);

        Product f = new Product(100, "GPU & CPU Bundle",999.99, 7, 1, 10);
        Product g = new Product(101, "GPU & Motherboard Bundle",800.00, 9, 1, 15);
        Product h = new Product(102, "RAM & Case Bundle",300.00, 6, 2, 9);
        Product i = new Product(103, "Complete System",1500.00, 3, 1, 5);

        Inventory.addProduct(f);
        Inventory.addProduct(g);
        Inventory.addProduct(h);
        Inventory.addProduct(i);

        launch(args);
    }



}