package representation_data;

import enums.Sex;
import handler.TableHandlerUtility;

import java.util.Date;

public class Employee{
    private String lastName;
    private String middleName;
    private String firstName;
    private String id;
    private Date birthDate;
    private String address;
    private Sex sex;
    private Double salary;
    private String managementEmployeeId;
    private String departmentId;
    
    private static String prefixId = "EM";
    private static int idLen = 12;

    public Employee() { createNewId(); }

    public Employee(String lastName,
                    String middleName,
                    String firstName,
                    String id,
                    Date birthDate,
                    String address,
                    Sex sex,
                    Double salary,
                    String managementEmployeeId,
                    String departmentId) {
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.id = id;
        this.birthDate = birthDate;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.managementEmployeeId = managementEmployeeId;
        this.departmentId = departmentId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public Sex getSex() {
        return sex;
    }

    public Double getSalary() {
        return salary;
    }

    public String getManagementEmployeeId() {
        return managementEmployeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setManagementEmployeeId(String managementEmployeeId) {
        this.managementEmployeeId = managementEmployeeId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    
    public void createNewId() {
        id = TableHandlerUtility.generateRandomId(prefixId, idLen - prefixId.length());
    }
}
