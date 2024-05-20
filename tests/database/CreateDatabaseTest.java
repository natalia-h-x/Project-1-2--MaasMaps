package database;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.Test;

import core.managers.DatabaseManager;

public class CreateDatabaseTest {
    @Test
    public void createDatabaseTest() {
        try {
            DatabaseManager.createDatabase();
        }
        catch (IOException e) {
            fail();
        }
    }
}
