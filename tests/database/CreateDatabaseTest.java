package database;

import static core.Constants.Paths.DATABASE_PATH;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.Test;

import core.managers.DatabaseManager;
import core.managers.FileManager;

public class CreateDatabaseTest {
    public static void main(String[] args) {
        new CreateDatabaseTest().createDatabaseTest();
    }
    
    @Test
    public void createDatabaseTest() {
        try {
            if (FileManager.fileExists(DATABASE_PATH.substring(DATABASE_PATH.lastIndexOf(":") + 1, DATABASE_PATH.length())))
                throw new IllegalAccessError("The database already exists. This test should be skipped, or the db file removed.");

            DatabaseManager.createDatabase();
        }
        catch (IOException e) {
            fail(e);
        }
    }
}
