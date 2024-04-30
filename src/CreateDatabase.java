
import database.util.fileaccess.db_helpers.DBPreparation;
import database.util.sqlite.*;


/*
 * READ
 *
 * To create/verify the database, run this program.
 * The database will be created and kept at /resources/routing.db
 *
 * HOW IT WORKS
 * files used:
 *      - database/util/db_helpers/*
 *      - database/util/sqlite/CSVToSQLite.java
 *
 * This program unzips the gtfs.zip to the /resources/gtfs directory, and then generates an SQLite database by reading and content
 * in batches. It's an alternative solution to not hosting it, because it's 1GB in size and impossible to easily share.
 */

public class CreateDatabase {
    public static void main(String[] args) {
        new DBPreparation();
        new TxtToSQLite("resources/gtfs");
    }
}
