package handler;

import enums.TableHandlerType;

import java.sql.*;
import java.util.Properties;

public class ProjectManagementHandler {
    // <editor-fold defaultstate="collapse" desc="Table names">
    public static final String TABLE_EMPLOYEE_NAME = "employee";
    public static final String TABLE_WORK_ASSIGNMENT = "work_assignment";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields of employee table">
    public static final String KEY_EMPLOYEE_ID = "id";
    public static final String KEY_EMPLOYEE_LAST_NAME = "last_name";
    public static final String KEY_EMPLOYEE_MIDDLE_NAME = "middle_name";
    public static final String KEY_EMPLOYEE_FIRST_NAME = "first_name";
    public static final String KEY_EMPLOYEE_BIRTH_DATE = "birthdate";
    public static final String KEY_EMPLOYEE_ADDRESS = "address";
    public static final String KEY_EMPLOYEE_SEX = "sex";
    public static final String KEY_EMPLOYEE_SALARY = "salary";
    public static final String KEY_EMPLOYEE_MANAGEMENT_EMPLOYEE_ID = "management_employee_id";
    public static final String KEY_EMPLOYEE_DEPARTMENT_ID = "department_id";
   // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields of work assignment">
    public static final String KEY_WORK_ASSIGNMENT_EMPLOYEE_ID = "employee_id";
    public static final String KEY_WORK_ASSIGNMENT_PROJECT_ID = "project_id";
    public static final String KEY_WORK_ASSIGNMENT_NUM_ORDER = "num_order";
    public static final String KEY_WORK_ASSIGNMENT_TIME = "time";
    // </editor-fold>

    private final Connection connection;
    private static TableHandler tableHandler;
    
    public ProjectManagementHandler(String uri) throws SQLException {
        Driver driver = new org.gjt.mm.mysql.Driver();
        DriverManager.registerDriver(driver);
        
        String conStr = uri;
        
        Properties info = new Properties();
        info.setProperty("characterEncoding", "utf8");
        info.setProperty("user", "root");
        info.setProperty("password", "");
        
        connection = DriverManager.getConnection(conStr, info);
    }
    
    public TableHandler getTableHandler(TableHandlerType type) throws SQLException {
        switch (type) {
            case EMPLOYEE_HANDLER:
                tableHandler = new EmployeeHandler(connection.createStatement());
                break;
                
            case DEPARTMENT_HANDLER:
                tableHandler = new DepartmentHandler(connection.createStatement());
                break;
            
            default:
                break;
        }
        
        return tableHandler;
    }
    
    public void close() throws SQLException {
        connection.close();
    }
}
