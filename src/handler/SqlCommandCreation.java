package handler;

import java.util.Set;

public abstract class SqlCommandCreation {
    public static String createQueryCommand(String tableName,
                                            String[] columns,
                                      String selection,
                                      String[] selectionArgs,
                                      String groupBy,
                                      String having,
                                      String orderBy,
                                      String limit) {
        StringBuilder cmd = new StringBuilder("SELECT ");

        if (null == columns) {
            cmd.append(" * ");
        }
        else {
            for (int i = 0; i < columns.length; i++) {
                cmd.append(columns[i]);
                if (i != columns.length - 1) {
                    cmd.append(", ");
                }
            }
        }

        cmd.append(" FROM ").append(tableName);

        if (null != selection) {
            cmd.append(" WHERE ");
            for (String str : selectionArgs) {
                selection = selection.replaceFirst("\\?", str);
            }

            cmd.append(selection);
        }

        if (null != groupBy) {
            cmd.append(" GROUP BY ").append(groupBy);
        }

        if (null != having) {
            cmd.append(" HAVING ").append(having);
        }

        if (null != orderBy) {
            cmd.append(" ORDER BY ").append(orderBy);
        }

        if (null != limit) {
            cmd.append(" LIMIT ").append(limit);
        }

        return cmd.toString();
    }

    public static String createInsertionCommand(String tableName, ContentValues values) throws ClassNotFoundException {
        StringBuilder cmd = new StringBuilder("INSERT INTO " + tableName + "(");
        Set<String> columnNames = values.getKeys();

        for (String column : columnNames) {
            cmd.append(column + ", ");
        }

        cmd.delete(cmd.length() - 2, cmd.length());

        cmd.append(" ) VALUES(");

        for (String column : columnNames) {
            if (values.getValue(column).getClass() == String.class) {
                cmd.append("'" + values.getValue(column) + "', ");
            }
            else {
                cmd.append(values.getValue(column) + ", ");
            }
        }

        cmd.delete(cmd.length() - 2, cmd.length());
        cmd.append(")");

        return cmd.toString();
    }

    public static String createUpdatedCommand(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        StringBuilder cmd = new StringBuilder("UPDATE " + tableName + " SET ");
        Set<String> columnNames = values.getKeys();

        for (String column : columnNames) {
            if (values.getValue(column) == null) {
                cmd.append(column + " = " + values.getValue(column) + ", ");
            }
            else if (values.getValue(column).getClass() == String.class) {
                cmd.append(column + " = '" + values.getValue(column) + "', ");
            }
            else {
                cmd.append(column + " = " + values.getValue(column) + ", ");
            }
        }

        cmd.delete(cmd.length() - 2, cmd.length());

        cmd.append(" WHERE ");
        for (String arg : whereArgs) {
            whereClause = whereClause.replaceFirst("\\?", arg);
        }

        cmd.append(whereClause);

        return cmd.toString();
    }

    public static String createDeletionCommand(String tableName, String whereClause, String[] whereArgs) {
        StringBuilder cmd = new StringBuilder();
        cmd.append("DELETE FROM " + tableName);

        for (String arg : whereArgs) {
            whereClause = whereClause.replaceFirst("\\?", arg);
        }

        cmd.append(" WHERE ");
        cmd.append(whereClause);
        return cmd.toString();
    }

    public static String createDeletionCommand(String tableName, String whereClause) {
        StringBuilder cmd = new StringBuilder();
        cmd.append("DELETE FROM " + tableName);
        cmd.append(" WHERE ");
        cmd.append(whereClause);
        return cmd.toString();
    }

    public static String createUpdatedCommand(String tableName, ContentValues contentValues, String whereClause) {
        StringBuilder cmd = new StringBuilder("UPDATE " + tableName + " SET ");
        Set<String> columnNames = contentValues.getKeys();

        for (String column : columnNames) {
            if (contentValues.getValue(column).getClass() == String.class) {
                cmd.append(column + " = '" + contentValues.getValue(column) + "', ");
            }
            else {
                cmd.append(column + " = " + contentValues.getValue(column) + ", ");
            }
        }

        cmd.delete(cmd.length() - 2, cmd.length());

        cmd.append(" WHERE ");
        cmd.append(whereClause);

        return cmd.toString();
    }
}
