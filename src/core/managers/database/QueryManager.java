package core.managers.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QueryManager {
    private QueryManager() {}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?>[] executeQuery(String query, List<?>... list) throws IllegalArgumentException {
        try (
            Statement stmt  = ConnectionManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            while (rs.next()) {
                for (int i = 1; i < list.length + 1; i++) {
                    ((List) list[i - 1]).add(rs.getObject(i));
                }
            }

            return list;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on executing query \"%s\"".formatted(query), e);
        }
    }
}
