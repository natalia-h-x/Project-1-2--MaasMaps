package core.managers;

import javax.swing.JOptionPane;
import java.awt.Component;

public class ExceptionManager {
    private ExceptionManager() {}

    public static void handle(Component component, Exception ex) {
        JOptionPane.showMessageDialog(component, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        handle(ex);
    }

    public static void handle(Exception ex) {
        ex.printStackTrace();
    }
}
