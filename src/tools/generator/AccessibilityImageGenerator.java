package tools.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import core.Context;
import core.managers.ExceptionManager;
import core.models.ZipCode;
import ui.MaasMapsUI;
import ui.map.geometry.AccessibilityMapBackground;

public class AccessibilityImageGenerator {
    public static void generateImage() {
        try {
            BufferedImage newImage = new BufferedImage(Context.getContext().getMap().getMapWidth(), Context.getContext().getMap().getMapHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = newImage.createGraphics();

            List<String> postalCodes = new ArrayList<>();
            for (ZipCode zipCode : Context.getContext().getZipCodeDatabase().getZipCodes()) {
                postalCodes.add(zipCode.getCode());
            }

            AccessibilityMapBackground mapBackground = new AccessibilityMapBackground(postalCodes);
            mapBackground.paint(g2d);

            File file = new File("resources/accessibilityMap.png");
            ImageIO.write(newImage, "png", file);
        }
        catch (IOException e) {
            ExceptionManager.handle(e);
        }
    }

    public static void main(String[] args) {
        new MaasMapsUI();
        generateImage();
    }
}
