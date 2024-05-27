package database;

import org.junit.jupiter.api.Test;

import core.managers.DatabaseManager;
import tools.generator.sqlite.db_helpers.DBPreparation;

public class DatabaseTest {
    @Test
    public void test1() {
        new DBPreparation();
    }

    @Test
    public void test2() {
        DatabaseManager.createTable("test", new String[]{"test1", "test2"}, new String[]{"TEXT", "TEXT"});
        DatabaseManager.insertInTable("test", new String[]{"test1", "test2"}, new String[][]{{"testA", "testB"}, {"testC", "testD"}});
    }

    @Test
    public void shapeTest() {
        DatabaseManager.getShape(1071429);
    }
}
