package core.managers.database;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import core.managers.serialization.XMLSerializationManager;
import tools.generator.sqlite.TxtToSQLite;

public class DatabaseDefinitionManager {
    private DatabaseDefinitionManager() {}

    public static void createTable(String tableName, String[] headers, String[] types) throws IllegalArgumentException {
        try (Statement stmt = ConnectionManager.getConnection().createStatement()) {
            StringBuilder bld = new StringBuilder();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n";
            bld.append(createTableSQL);

            for (int i = 0; i < headers.length; i++) {
                bld.append("`" + headers[i] + "` " + XMLSerializationManager.removeTags(types[i]) + ",\n");
            }

            List<String> compositePK = new LinkedList<>();

            for (int i = 0; i < headers.length; i++) {
                String type = types[i];

                while (type.contains("<")) {
                    String tag = XMLSerializationManager.getFirstTag(type);
                    type = XMLSerializationManager.removeFirstTag(type);

                    switch (tag.split(" ")[0]) {
                        case "PK":
                            compositePK.add(headers[i]);
                            break;
                        case "FK":
                            bld.append(
                                    "FOREIGN KEY (`%s`) REFERENCES `%s`),"
                                    .formatted(headers[i], tag.replace("FK ", "")
                                                              .replace(".", "`(`")))
                               .append("\n");
                            break;
                        default:
                            throw new UnsupportedOperationException("XML Command not supported.");
                    }
                }
            }

            if (!compositePK.isEmpty())
                bld.append("PRIMARY KEY (");

            for (String attrib : compositePK) {
                bld.append("`" + attrib + "`,");
            }

            if (!compositePK.isEmpty())
                bld.append(")\n");

            bld.append(");");
            stmt.execute(bld.toString().replaceAll(",(\n|)\\)", ")"));
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on creating table \"%s\"".formatted(tableName), e);
        }
    }

    public static void createDatabase() throws IOException {
        TxtToSQLite.txtToSQLite("resources/gtfs");
    }
}
