package handler;

import enums.Sex;
import representation_data.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployeeHandler extends TableHandler {
    public static final String TABLE_NAME = "employee";

    public static final String KEY_ID = "id";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_MIDDLE_NAME = "middle_name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_BIRTH_DATE = "birthdate";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SEX = "sex";
    public static final String KEY_SALARY = "salary";
    public static final String KEY_MANAGEMENT_EMPLOYEE_ID = "management_employee_id";
    public static final String KEY_DEPARTMENT_ID = "department_id";

    public EmployeeHandler(Statement statement) {
        super(statement);
    }

    public ResultSet query(String tableName, 
                           String[] columns,
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

    public int insert(Employee employee) throws SQLException, ClassNotFoundException {
        while (wasExistId(employee.getId())) {
            employee.createNewId();
        }
        
        return super.insert(TABLE_NAME, new ContentValues(employee));
    }

    public int delete(Employee employee) throws SQLException {
        return super.delete(TABLE_NAME, createWhereClause(employee));
    }

    public int update(Employee updatedEmployee, Employee conditionEmployee) throws SQLException {
        return super.update(TABLE_NAME, new ContentValues(updatedEmployee), createWhereClause(conditionEmployee));
    }

    @Override
    public String createWhereClause(Object obj) {
        Employee employee = (Employee)obj;
        ArrayList<String> listStatement = new ArrayList<>();
        
        String clauseGetStringArg = "%s = '%s'";
        String clauseGetNonStringArg = "%s = %s";
        
        if (null != employee.getId()) {
            listStatement.add(String.format(clauseGetStringArg, KEY_ID, employee.getId()));
        }

        if (null != employee.getLastName()) {
            //listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_LAST_NAME + " = '" + employee.getLastName() + "'");
            listStatement.add(String.format(clauseGetStringArg, ProjectManagementHandler.KEY_EMPLOYEE_LAST_NAME, employee.getLastName()));
        }

        if (null != employee.getMiddleName()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_MIDDLE_NAME + " = '" + employee.getMiddleName() + "'");
        }

        if (null != employee.getFirstName()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_FIRST_NAME + " = '" + employee.getFirstName() + "'");
        }

        if (null != employee.getBirthDate()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_BIRTH_DATE + " = '" + employee.getBirthDate() + "'");
        }

        if (null != employee.getAddress()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_ADDRESS + " = '" + employee.getAddress() + "'");
        }

        if (null != employee.getSex()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_SEX + " = '" + (employee.getSex() == Sex.FEMALE ? "male" : "female") + "'");
        }

        if (null != employee.getSalary()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_SALARY + " = " + employee.getSalary().toString());
        }

        if (null != employee.getManagementEmployeeId()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_MANAGEMENT_EMPLOYEE_ID + " = '" + employee.getManagementEmployeeId() + "'");
        }

        if (null != employee.getDepartmentId()) {
            listStatement.add(ProjectManagementHandler.KEY_EMPLOYEE_DEPARTMENT_ID + " = '" + employee.getDepartmentId() + "'");
        }

        return String.join(" AND ", listStatement);
    }
    
    @Override
    public boolean wasExistId(String id) throws SQLException {
        ResultSet set = query(TABLE_NAME,
                null,
                "id = '?'",
                new String[] {id},
                null, null, null, null);
        
        if (null != set) {
            set.last();
            int rows = set.getRow();
            return 0 != rows;
        }
        
        return false;
    }
}
