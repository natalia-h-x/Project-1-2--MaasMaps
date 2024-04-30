import core.managers.DatabaseManager;
import ui.MaasMapsUI;

/**
 * Main class to run the application
 *
 * @author Sian Lodde
 */
public class Main {
    public static void main(String[] args) {
        new MaasMapsUI();
        DatabaseManager.connect();
        DatabaseManager.executeQuery();
    }
}