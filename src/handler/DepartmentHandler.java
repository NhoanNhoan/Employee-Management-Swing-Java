/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import enums.Sex;
import static handler.EmployeeHandler.KEY_ID;
import static handler.EmployeeHandler.TABLE_NAME;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import representation_data.Department;

/**
 *
 * @author LENOVO IDEAPAD
 */
public class DepartmentHandler extends TableHandler {
    public static final String TABLE_NAME = "department";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MANAGER_ID = "manager_id";
    public static final String KEY_INAUGURATION_DATE = "inauguration_date";
    
    public DepartmentHandler(Statement statement) {
        super(statement);
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
        return super.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
    
    public String createWhereClause(Object obj) {
        Department department = (Department)obj;
        ArrayList<String> listStatement = new ArrayList<>();
        
        String clauseGetStringArg = "? = '?'";
        String clauseGetNonStringArg = "? = ?";

        if (null != department.getId()) {
            listStatement.add(String.format(clauseGetStringArg, KEY_ID, department.getId()));
        }
        
        if (null != department.getName()) {
            listStatement.add(String.format(clauseGetStringArg, KEY_NAME, department.getName()));
        }
        
        if (null != department.getManagerId()) {
            listStatement.add(String.format(clauseGetStringArg, KEY_MANAGER_ID, department.getManagerId()));
        }
        
        if (null != department.getInaugurationDate()) {
            listStatement.add(String.format(clauseGetStringArg, KEY_INAUGURATION_DATE, department.getInaugurationDate()));
        }

        return String.join(" AND ", listStatement);
    }
    
    @Override
    public boolean wasExistId(String id) throws SQLException {
        ResultSet set = query(TABLE_NAME,
                null,
                "id = '?'",
                new String[] {KEY_ID},
                null, null, null, null);
        
        if (null != set) {
            set.last();
            return 0 != set.getRow();
        }
        
        return false;
    }
}
