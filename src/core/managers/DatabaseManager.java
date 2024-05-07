package core.managers;

import static core.Constants.Paths.DATABASE_URL;

import java.awt.geom.Point2D;
import java.nio.file.FileSystemNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.algorithms.datastructures.AdjacencyListGraph;
import core.algorithms.datastructures.Graph;
import core.models.Location;
import tools.generator.sqlite.TxtToSQLite;
import tools.generator.sqlite.db_helpers.DBPreparation;

public class DatabaseManager {
    private DatabaseManager() {}

    private static Connection connect() throws SQLException {
        try {
            if (!FileManager.fileExists(DATABASE_URL.replace("jdbc:sqlite:", ""))) {
                // Create the database and try to connect. If this fails, throw the exception.
                createDatabase();
            }

            return DriverManager.getConnection(DATABASE_URL);
        }
        catch (SQLException e) {
            throw new FileSystemNotFoundException("Could not connect to the SQL database at path \"%s\"".formatted(DATABASE_URL));
        }
    }

    private static void createDatabase() {
        new DBPreparation();
        new TxtToSQLite("resources/gtfs");
    }

    public static ResultSet executeQuery(String query) throws IllegalArgumentException {
        try (
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(query)
            ) {
            return rs;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on executing query \"%s\"".formatted(query), e);
        }
    }

    public static Graph<Point2D> loadGraph() throws SQLException {
        Graph<Point2D> graph = new AdjacencyListGraph<>();
        ResultSet rs = executeQuery("select a.stop_lon as from_stop_lon, a.stop_lat as from_stop_lat, b.stop_lon as to_stop_lon, b.stop_lat as to_stop_lat\r\n" + //
                        "from stops a\r\n" + //
                        "left join transfers on a.stop_id = transfers.from_stop_id \r\n" + //
                        "left join stops b on b.stop_id = transfers.to_stop_id \r\n" + //
                        "where a.stop_lat < 50.90074 and a.stop_lat > 50.815816 and a.stop_lon < 5.753384 and a.stop_lon > 5.64213\r\n" + //
                        "  and b.stop_lat < 50.90074 and b.stop_lat > 50.815816 and b.stop_lon < 5.753384 and b.stop_lon > 5.64213");

        while (rs.next()) {
            Location fromLocation = new Location(rs.getDouble(1), rs.getDouble(2));
            Location toLocation = new Location(rs.getDouble(3), rs.getDouble(4));
            int weight = (int) (1000 * rs.getDouble(5));

            if (!graph.getVertecesList().contains(fromLocation))
                graph.addVertex(fromLocation);

            if (!graph.getVertecesList().contains(toLocation))
                graph.addVertex(toLocation);

            graph.addEdge(fromLocation, toLocation, weight);
        }

        return graph;
    }
}
