package database;

import org.junit.jupiter.api.Test;

import core.managers.database.DatabaseDefinitionManager;
import core.managers.database.DatabaseModificationManager;
import core.managers.database.gtfs.ShapeManager;
import tools.generator.sqlite.db_helpers.Unzipper;

public class DatabaseTest {
    @Test
    public void test1() {
        Unzipper.prepareForDatabase();
    }

    @Test
    public void test2() {
        DatabaseDefinitionManager.createTable("test", new String[]{"test1", "test2"}, new String[]{"TEXT", "TEXT"});
        DatabaseModificationManager.insertInTable("test", new String[]{"test1", "test2"}, new String[][]{{"testA", "testB"}, {"testC", "testD"}});
    }

    @Test
    public void shapeTest() {
        ShapeManager.getShape(1071429);
    }
}
