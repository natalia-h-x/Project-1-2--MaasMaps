package tools.generator.sqlite.db_helpers;

public class DBPreparation {

    public DBPreparation() {
        Unzipper unzipper = new Unzipper("resources/gtfs", "resources/gtfs/gtfs.zip");

        try {
            unzipper.unzip();
            System.out.println("-- Success --");
        }
        catch (Exception e) {
            System.err.println("Error unzipping file: " + e.getMessage());
        }
    }
}
