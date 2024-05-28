package ui;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import core.Context;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.translation.TranslationListener;

public class TranslationListenerTest {
    @Test
    public void test1() {
        new MaasMapsUI();
        ProxyMap map = Context.getContext().getMap();
        TranslationListener translationListener = map.getTranslationListener();

        translationListener.setScale(0);
        assertEquals(1.0, translationListener.getScale());
        translationListener.setScale(1);
        assertEquals(1.2, translationListener.getScale());
        translationListener.setScale(-1);
        assertEquals(1.0, translationListener.getScale());
        translationListener.setScale(257);
        assertEquals(translationListener.calculateScale(translationListener.getMaxZoom()), translationListener.getScale());
        translationListener.setScale(-257);
        assertEquals(translationListener.calculateScale(translationListener.getMinZoom()), translationListener.getScale());
        translationListener.setScale(111111);
        assertEquals(translationListener.calculateScale(translationListener.getMaxZoom()), translationListener.getScale());
        translationListener.setScale(-111111);
        assertEquals(translationListener.calculateScale(translationListener.getMinZoom()), translationListener.getScale());
        translationListener.setScale(999999999);
        assertEquals(translationListener.calculateScale(translationListener.getMaxZoom()), translationListener.getScale());
        translationListener.setScale(-999999999);
        assertEquals(translationListener.calculateScale(translationListener.getMinZoom()), translationListener.getScale());
        translationListener.setScale(Integer.MAX_VALUE);
        assertEquals(translationListener.calculateScale(translationListener.getMaxZoom()), translationListener.getScale());
        translationListener.setScale(Integer.MIN_VALUE);
        assertEquals(translationListener.calculateScale(translationListener.getMinZoom()), translationListener.getScale());
        translationListener.setScale(236);
        assertEquals(translationListener.calculateScale(translationListener.getMaxZoom()), translationListener.getScale());

        translationListener.setTranslation(new Point(0, 0));
        assertEquals(new Point(0, 0), translationListener.getTranslation());
        translationListener.setTranslation(new Point(1, 1));
        assertEquals(new Point(1, 1), translationListener.getTranslation());
        translationListener.setTranslation(new Point(-1, -1));
        assertEquals(new Point(-1, -1), translationListener.getTranslation());
        translationListener.setTranslation(new Point(257, 257));
        assertEquals(new Point(257, 257), translationListener.getTranslation());
        translationListener.setTranslation(new Point(-257, -257));
        assertEquals(new Point(-257, -257), translationListener.getTranslation());
        translationListener.setTranslation(new Point(111111, 111111));
        assertEquals(new Point(111111, 111111), translationListener.getTranslation());
        translationListener.setTranslation(new Point(-111111, -111111));
        assertEquals(new Point(-111111, -111111), translationListener.getTranslation());
        translationListener.setTranslation(new Point(999999999, 999999999));
        assertEquals(new Point(999999999, 999999999), translationListener.getTranslation());
        translationListener.setTranslation(new Point(-999999999, -999999999));
        assertEquals(new Point(-999999999, -999999999), translationListener.getTranslation());
        translationListener.setTranslation(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertEquals(new Point(Integer.MAX_VALUE, Integer.MAX_VALUE), translationListener.getTranslation());
        translationListener.setTranslation(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE));
        assertEquals(new Point(Integer.MIN_VALUE, Integer.MIN_VALUE), translationListener.getTranslation());
        translationListener.setTranslation(new Point(236, 236));
        assertEquals(new Point(236, 236), translationListener.getTranslation());
        
        try {
            Robot robot = new Robot();
            robot.mouseMove((int) map.getLocation().getX(), (int) map.getLocation().getY());
            robot.mousePress(MouseEvent.BUTTON1);
            robot.mouseMove((int) map.getLocation().getX() + 100, (int) map.getLocation().getY() + 100);
            robot.mouseRelease(MouseEvent.BUTTON1);
            robot.mouseWheel(30);
            robot.mouseWheel(-30);
            robot.mouseWheel(-40);
            robot.mouseWheel(40);
            robot.mouseWheel(-30);
        }
        catch (AWTException e) {
            fail();
        }
    }
}
