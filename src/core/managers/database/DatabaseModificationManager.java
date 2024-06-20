package core.managers.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseModificationManager {
    private DatabaseModificationManager() {}

    public static void insertInTable(String tableName, String[] attributes, String[][] data) throws IllegalArgumentException {
        try {
            Connection conn = ConnectionManager.getConnection();

            conn.setAutoCommit(false); // start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(createInsertString(tableName, attributes, data))) {
                int count = 0;
                for (int tupleIndex = 0; tupleIndex < data.length; tupleIndex++) {
                    for (int attIndex = 0; attIndex < data[0].length; attIndex++) {
                        pstmt.setString(tupleIndex * data[0].length + (attIndex + 1), data[tupleIndex][attIndex]);
                    }
                }
                pstmt.addBatch();
                if (++count % 100 == 0) {
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }

                pstmt.executeBatch(); // final batch
                conn.commit(); // commit transaction
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Error on inserting in the table \"%s\"".formatted(tableName), e);
        }
    }

    private static String createInsertString(String tableName,  String[] attributes, String[][] data) {
        String insertSQL = "INSERT INTO `%s` (%s";

        StringBuilder bd = new StringBuilder();
        for (int i = 0; i < attributes.length; i++) {
            bd.append("`" + attributes[i] + "`");
            if (i < attributes.length - 1) {
                bd.append(", ");
            }
        }
        bd.append(") VALUES ");

        for (int attribute = 0; attribute < data.length; attribute++) {
            bd.append("(");
            for (int i = 0; i < data[0].length; i++) {
                bd.append("?");
                if (i < data[0].length - 1) {
                    bd.append(", ");
                }
            }
            bd.append(")");
            if (attribute < data.length - 1)
                bd.append(", ");
        }
        bd.append(";");
        insertSQL = String.format(insertSQL, tableName, bd.toString());
        return insertSQL;
    }
}
