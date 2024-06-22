package manager;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import core.managers.amenity.AmenityAccessibilityManager;

public class AmenityAccessibilityManagerTest {
    @Test
    public void test() {
        double accessibility = AmenityAccessibilityManager.getAccessibilityMetric("6227XB");

        assertEquals(0.09723312793036683, accessibility);

        System.out.println();
    }
}
