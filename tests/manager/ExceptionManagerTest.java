package manager;

import org.junit.jupiter.api.Test;

import core.managers.ExceptionManager;
import ui.MaasMapsUI;

public class ExceptionManagerTest {
    @Test
    public void test1() {
        ExceptionManager.handle(new Exception());
        ExceptionManager.handle(new MaasMapsUI().getComponent(0), new Exception());
    }
}
