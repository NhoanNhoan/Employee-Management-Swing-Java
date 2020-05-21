package handler;

import enums.Sex;
import representation_data.Employee;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContentValues {
    private HashMap<String, Object> args;

    public ContentValues() {
        args = new HashMap<>();
    }

    public void put(String key, String value) {
        args.put(key, value);
    }

    public void put(String key, Integer value) {
        args.put(key, value);
    }

    public void put(String key, Long value) {
        args.put(key, value);
    }

    public void put(String key, Boolean value) {
        args.put(key, value);
    }

    public void put(String key, Double value) {
        args.put(key, value);
    }
    
    public void put(String key, Float value) {
        args.put(key, value);
    }

    public Object getValue(String key) {
        return args.get(key);
    }

    public HashMap<String, Object> getArgs() {
        return args;
    }

    public Set<String> getKeys() {
        return args.keySet();
    }

    public ContentValues(Employee employee) {
        args = new HashMap<>();
        
        this.put(ProjectManagementHandler.KEY_EMPLOYEE_ID, employee.getId());

        if (null != employee.getLastName()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_LAST_NAME, employee.getLastName());
        }

        if (null != employee.getMiddleName()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_MIDDLE_NAME, employee.getMiddleName());
        }

        if (null != employee.getFirstName()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_FIRST_NAME, employee.getFirstName());
        }

        if (null != employee.getBirthDate()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_BIRTH_DATE, new SimpleDateFormat("yyyy-dd-MM").format(employee.getBirthDate()));
        }

        if (null != employee.getAddress()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_ADDRESS, employee.getAddress());
        }

        if (null != employee.getSex()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_SEX, employee.getSex() == Sex.MALE ? "male" : "female");
        }

        if (null != employee.getSalary()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_SALARY, employee.getSalary());
        }

        if (null != employee.getManagementEmployeeId()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_MANAGEMENT_EMPLOYEE_ID, employee.getManagementEmployeeId());
        }

        if (null != employee.getDepartmentId()) {
            this.put(ProjectManagementHandler.KEY_EMPLOYEE_DEPARTMENT_ID, employee.getDepartmentId());
        }
    }

    public void put(String key, Object value) {
        args.put(key, value);
    }
}
