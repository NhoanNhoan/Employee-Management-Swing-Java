package handler;

import creator_interfaces.WhereClauseCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class TableHandler implements WhereClauseCreator {
    private Statement statement;

    public TableHandler(Statement statement) {
        this.statement = statement;
    }

    public ResultSet query(String tableName, String[] columns,
                             String selection,
                             String[] selectionArgs,
                             String groupBy,
                             String having,
                             String orderBy,
                             String limit) throws SQLException {
        String cmd = SqlCommandCreation.createQueryCommand(tableName,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy,
                limit);
        return statement.executeQuery(cmd);
    }

    public int insert(String tableName, ContentValues values) throws SQLException, ClassNotFoundException {
        String cmd = SqlCommandCreation.createInsertionCommand(tableName, values);
        return statement.executeUpdate(cmd);
    }

    public int update(String tableName, ContentValues values, String whereClause, String[] whereArgs) throws SQLException {
        String cmd = SqlCommandCreation.createUpdatedCommand(tableName, values, whereClause, whereArgs);
        return statement.executeUpdate(cmd);
    }

    public int delete(String tableName, String whereClause, String[] whereArgs) throws SQLException {
        return statement.executeUpdate(SqlCommandCreation.createDeletionCommand(tableName, whereClause, whereArgs));
    }

    protected int delete(String tableName, String whereClause) throws SQLException {
        return statement.executeUpdate(SqlCommandCreation.createDeletionCommand(tableName, whereClause));
    }

    protected int update(String tableName, ContentValues contentValues, String whereClause) throws SQLException {
        String cmd = SqlCommandCreation.createUpdatedCommand(tableName, contentValues, whereClause);
        return statement.executeUpdate(cmd);
    }
    
    abstract boolean wasExistId(String id) throws SQLException;
}
