package database;

import static core.Constants.Paths.DATABASE_PATH;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.Test;

import core.managers.FileManager;
import core.managers.database.DatabaseDefinitionManager;
import tools.generator.sqlite.db_helpers.Unzipper;

public class CreateDatabaseTest {
    public CreateDatabaseTest() {
        Unzipper.prepareForDatabase();
    }

    @Test
    public void createDatabaseTest() {
        try {
            if (FileManager.fileExists(DATABASE_PATH.substring(DATABASE_PATH.lastIndexOf(":") + 1, DATABASE_PATH.length())))
                throw new IllegalAccessError("The database already exists. This test should be skipped, or the db file removed.");

            DatabaseDefinitionManager.createDatabase();
        }
        catch (IOException e) {
            fail(e);
        }
    }
}
