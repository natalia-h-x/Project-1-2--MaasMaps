package core.managers;

import javax.swing.ImageIcon;

public class IconManager {
    private IconManager() {}

    public static ImageIcon loadIcon(String path) {
        return new ImageIcon(path);
    }
}
