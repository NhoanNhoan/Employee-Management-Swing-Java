/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import enums.Sex;
import enums.TableHandlerType;
import handler.DepartmentHandler;
import handler.EmployeeHandler;
import handler.ContentValues;
import handler.ProjectManagementHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import representation_data.Employee;

import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.*;

/**
 *
 * @author LENOVO IDEAPAD
 */
public class EmployeeFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form AdditionalEmployeeInternalFrame
     */
    
    private ArrayList<String> listEmployeeIds;
    private ArrayList<String> listEmployeeNames;
    private ArrayList<String> listDepartmentIds;
    private ArrayList<String> listDepartmentNames;
    private HashMap<String, Integer> headerMapIndex;
    private String selectedId;
    
    public EmployeeFrame() {
        initComponents();
        initButtonGroup(buttonGroup1, new javax.swing.JRadioButton[] {rbMale, rbFemale});
        initHeaderMapIndex();
        
        try {
            loadCbManager();
            loadCbDepartment();
            loadEmployeeDatasToTable();
        }
        catch (SQLException ex) {
            javax.swing.JOptionPane.showConfirmDialog(this, "Can't load combo box data!");
        }
    }
    
    private void initHeaderMapIndex() {
        headerMapIndex = new HashMap<>();
        Integer index = 0;
        
        headerMapIndex.put(EmployeeHandler.KEY_ID, index++);
        headerMapIndex.put(EmployeeHandler.KEY_FIRST_NAME, index++);
        headerMapIndex.put(EmployeeHandler.KEY_MIDDLE_NAME, index++);
        headerMapIndex.put(EmployeeHandler.KEY_LAST_NAME, index++);
        headerMapIndex.put(EmployeeHandler.KEY_BIRTH_DATE, index++);
        headerMapIndex.put(EmployeeHandler.KEY_SEX, index++);
        headerMapIndex.put(EmployeeHandler.KEY_ADDRESS, index++);
        headerMapIndex.put(EmployeeHandler.KEY_SALARY, index++);
        headerMapIndex.put(EmployeeHandler.KEY_MANAGEMENT_EMPLOYEE_ID, index++);
        headerMapIndex.put(EmployeeHandler.KEY_DEPARTMENT_ID, index);
    }
    
    private void loadEmployeeDatasToTable() {
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        ResultSet result = null;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            result = employeeHandler.query(ProjectManagementHandler.TABLE_EMPLOYEE_NAME,
                    null, null, null, null, null, null, null);
            addRows(result);
        } 
        catch (SQLException sqlEx) {
            return;
        }
    }
    
    private void addRows(ResultSet resultSet) throws SQLException {
        Vector headers = createHeaderVector();
        Vector rows = new Vector();
        
        while (resultSet.next()) {
            Vector row = new Vector();
            
            row.add(resultSet.getString(EmployeeHandler.KEY_ID));
            row.add(resultSet.getString(EmployeeHandler.KEY_FIRST_NAME));
            row.add(resultSet.getString(EmployeeHandler.KEY_MIDDLE_NAME));
            row.add(resultSet.getString(EmployeeHandler.KEY_LAST_NAME));
            row.add(resultSet.getDate(EmployeeHandler.KEY_BIRTH_DATE));
            row.add(resultSet.getString(EmployeeHandler.KEY_SEX));
            row.add(resultSet.getString(EmployeeHandler.KEY_ADDRESS));
            row.add(resultSet.getDouble(EmployeeHandler.KEY_SALARY));
            row.add(resultSet.getString(EmployeeHandler.KEY_MANAGEMENT_EMPLOYEE_ID));
            row.add(resultSet.getString(EmployeeHandler.KEY_DEPARTMENT_ID));
            
            rows.add(row);
        }
        
        tableEmployees.setModel(new DefaultTableModel(rows, headers));
    }
    
    private void addRow(Employee employee) {
        DefaultTableModel model = (DefaultTableModel)tableEmployees.getModel();
        Vector row = new Vector();
        
        row.add(employee.getId());
        row.add(employee.getFirstName());
        row.add(employee.getMiddleName());
        row.add(employee.getLastName());
        
        if (null != employee.getBirthDate())
            row.add(new SimpleDateFormat("MM-dd-yyyy").format(employee.getBirthDate()));
        else
            row.add(null);
        
        row.add(employee.getSex() == Sex.MALE ? "male" : "female");
        row.add(employee.getAddress());
        row.add(employee.getSalary());
        row.add(employee.getManagementEmployeeId());
        row.add(employee.getDepartmentId());
        
        model.addRow(row);
    }
    
    private void deleteRow(int index) {
        DefaultTableModel model = (DefaultTableModel)tableEmployees.getModel();
        model.removeRow(index);
    }
    
    private void addValueToCbManager(Employee employee) {
        String name = employee.getFirstName() + " "
                + employee.getMiddleName() + " "
                + employee.getLastName();
        listEmployeeNames.add(name);
        listEmployeeIds.add(employee.getId());
        cbManager.addItem(name);
    }
    
    private void deleteValueOfCbManager() {
        int index = listEmployeeIds.indexOf(selectedId);
        
        // Not remove first value because it's null representaion
        if (0 != index) {
            cbManager.removeItemAt(index);
            listEmployeeIds.remove(index);
            listEmployeeNames.remove(index);
        }
    }
    
    private Vector createHeaderVector() {
        Vector header = new Vector();
        
        header.add(EmployeeHandler.KEY_ID);
        header.add(EmployeeHandler.KEY_FIRST_NAME);
        header.add(EmployeeHandler.KEY_MIDDLE_NAME);
        header.add(EmployeeHandler.KEY_LAST_NAME);
        header.add(EmployeeHandler.KEY_BIRTH_DATE);
        header.add(EmployeeHandler.KEY_SEX);
        header.add(EmployeeHandler.KEY_ADDRESS);
        header.add(EmployeeHandler.KEY_SALARY);
        header.add(EmployeeHandler.KEY_MANAGEMENT_EMPLOYEE_ID);
        header.add(EmployeeHandler.KEY_DEPARTMENT_ID);
        
        return header;
    }
    
    private void initButtonGroup(javax.swing.ButtonGroup btnGroup, javax.swing.JRadioButton[] radios) {
        for (var item : radios) {
            btnGroup.add(item);
        }
    }
    
    private void loadCbManager() throws SQLException {
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        ResultSet result = null;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            result = employeeHandler.query(ProjectManagementHandler.TABLE_EMPLOYEE_NAME,
                    new String[] {ProjectManagementHandler.KEY_EMPLOYEE_ID, 
                        ProjectManagementHandler.KEY_EMPLOYEE_FIRST_NAME,
                    ProjectManagementHandler.KEY_EMPLOYEE_MIDDLE_NAME,
                    ProjectManagementHandler.KEY_EMPLOYEE_LAST_NAME},
                    null, null, null, null, null, null);
        } 
        catch (SQLException sqlEx) {
            return;
        }
        
        listEmployeeNames = new ArrayList<>();
        listEmployeeIds = new ArrayList<>();
        
        listEmployeeNames.add("");
        listEmployeeIds.add("");
        
        while (result.next()) {
            listEmployeeIds.add(result.getString(ProjectManagementHandler.KEY_EMPLOYEE_ID));
            listEmployeeNames.add(result.getString(ProjectManagementHandler.KEY_EMPLOYEE_FIRST_NAME) + " " +
                    result.getString(ProjectManagementHandler.KEY_EMPLOYEE_MIDDLE_NAME) + " " +
                    result.getString(ProjectManagementHandler.KEY_EMPLOYEE_LAST_NAME));
        }
        
        DefaultComboBoxModel model = new DefaultComboBoxModel(listEmployeeNames.toArray(String[]::new));
        cbManager.setModel(model);
        
        if (null != handler) handler.close();
    }
    
    private void loadCbDepartment() throws SQLException {
        ProjectManagementHandler handler = null;
        DepartmentHandler departmentHandler = null;
        ResultSet result = null;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            departmentHandler = (DepartmentHandler)handler.getTableHandler(TableHandlerType.DEPARTMENT_HANDLER);
            result = departmentHandler.query(DepartmentHandler.TABLE_NAME,
                    new String[] {DepartmentHandler.KEY_ID, DepartmentHandler.KEY_NAME},
                    null, null, null, null, null, null);
        } 
        catch (SQLException sqlEx) {
            return;
        }
        
        listDepartmentNames = new ArrayList<>();
        listDepartmentIds = new ArrayList<>();
        
        listDepartmentIds.add("");
        listDepartmentNames.add("");
        
        while (result.next()) {
            listDepartmentIds.add(result.getString(DepartmentHandler.KEY_ID));
            listDepartmentNames.add(result.getString(DepartmentHandler.KEY_NAME));
        }
        
        DefaultComboBoxModel model = new DefaultComboBoxModel(listDepartmentNames.toArray(String[]::new));
        cbDepartment.setModel(model);
        
        if (null != handler) handler.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtMiddleName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtMonth = new javax.swing.JTextField();
        txtDay = new javax.swing.JTextField();
        txtYear = new javax.swing.JTextField();
        rbMale = new javax.swing.JRadioButton();
        rbFemale = new javax.swing.JRadioButton();
        cbManager = new javax.swing.JComboBox<>();
        cbDepartment = new javax.swing.JComboBox<>();
        btnInserth = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployees = new javax.swing.JTable();
        spinnerSalary = new javax.swing.JSpinner();
        btnDelete = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();

        jLabel1.setText("First name");

        jLabel2.setText("Middle name");

        jLabel3.setText("Last name");

        jLabel4.setText("Date of birth");

        jLabel5.setText("Month");

        jLabel6.setText("Day");

        jLabel7.setText("Year");

        jLabel8.setText("Sex");

        jLabel9.setText("Salary");

        jLabel10.setText("Manager");

        jLabel11.setText("Department");

        rbMale.setText("Male");

        rbFemale.setText("Female");

        cbManager.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbDepartment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnInserth.setText("INSERT");
        btnInserth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserthActionPerformed(evt);
            }
        });

        btnRefresh.setText("REFRESH");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel12.setText("Address");

        tableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Middle Name", "Last Name", "Birth Date", "Sex", "Address", "Salary", "Manager", "Department"
            }
        ));
        tableEmployees.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEmployeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableEmployees);

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnModify.setText("MODIFY");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28)
                        .addComponent(rbMale)
                        .addGap(65, 65, 65)
                        .addComponent(rbFemale))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jLabel6)
                        .addGap(4, 4, 4)
                        .addComponent(txtDay, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDepartment, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(cbManager, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInserth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete)
                        .addGap(82, 82, 82)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 108, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 852, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModify)
                        .addGap(84, 84, 84)
                        .addComponent(btnRefresh)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 983, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel5)
                        .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(txtDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbMale)
                            .addComponent(rbFemale)
                            .addComponent(jLabel8))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(spinnerSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnInserth)
                    .addComponent(btnDelete)
                    .addComponent(btnModify)
                    .addComponent(btnRefresh))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInserthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserthActionPerformed
        // Insert a new employee, need:
        // Update cbManager
        Employee employee = getValueOfComponents();
        
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        int affectedRows = 0;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            affectedRows = employeeHandler.insert(employee);
        } 
        catch (SQLException sqlEx) {
            return;
        } 
        catch (ClassNotFoundException ex) {
            return;
        }
        finally {
            refresh();
        }
        
        if (affectedRows > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Inserted a new employee!");
            addRow(employee);
            addValueToCbManager(employee);
        }
    }//GEN-LAST:event_btnInserthActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (null == selectedId) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select desired employe from table and let's try again!");
            return;
        }
        
        Employee employee = new Employee();
        employee.setId(selectedId);
        
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        int affectedRows = 0;
        boolean wasExist = false;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            
            wasExist = employeeHandler.wasExistId(selectedId);
            
            if (!wasExist) {
                javax.swing.JOptionPane.showMessageDialog(this, "Maybe somethings is wrong!");
                deleteRow(tableEmployees.getSelectedRow());
            }
            
            affectedRows = employeeHandler.delete(employee);
            
            ContentValues values = new ContentValues();
            String empty = null;
            values.put(EmployeeHandler.KEY_MANAGEMENT_EMPLOYEE_ID, empty);
            
            employeeHandler.update(EmployeeHandler.TABLE_NAME, values, EmployeeHandler.KEY_MANAGEMENT_EMPLOYEE_ID + " = '" + selectedId + "'", new String[] {selectedId});
        } catch (SQLException sqlEx) {
            return;
        }
        finally {
            refresh();
        }
        
        if (affectedRows > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Deleted a employee!");
            deleteRow(tableEmployees.getSelectedRow());
            deleteValueOfCbManager();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        if (null == selectedId) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select desired employe from table and let's try again!");
            return;
        }
        
        Employee employee = getValueOfComponents();
        Employee conditionEmployee = new Employee();
        conditionEmployee.setId(selectedId);
        
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        int affectedRows = 0;
        boolean wasExist = false;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            
            wasExist = employeeHandler.wasExistId(selectedId);
            
            if (!wasExist) {
                javax.swing.JOptionPane.showMessageDialog(this, "Maybe somethings is wrong!");
                deleteRow(tableEmployees.getSelectedRow());
            }
            
            affectedRows = employeeHandler.update(employee, conditionEmployee);
        } catch (SQLException sqlEx) {
            return;
        }
        finally {
            refresh();
        }
        
        if (affectedRows > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Updated a employee!");
            updateValuesOfRow(employee, tableEmployees.getSelectedRow());
        }
    }//GEN-LAST:event_btnModifyActionPerformed

    private void tableEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeesMouseClicked
        // TODO add your handling code here:
        int index = 0;
        selectedId = getStringValueFromRow(index++).toString();
        refresh();
        txtFirstName.setText(getStringValueFromRow(index++));
        txtMiddleName.setText(getStringValueFromRow(index++));
        txtLastName.setText(getStringValueFromRow(index++));
        String birthDateStr = getStringValueFromRow(index++);
        
        if (null != birthDateStr) {
            String[] birthDate = birthDateStr.split("-");
            if (null != birthDate) {
                txtMonth.setText(birthDate[0]);
                txtDay.setText(birthDate[1]);
                txtYear.setText(birthDate[2]);
            }
        }
        
        String sex = getStringValueFromRow(index++);
        if (null != sex) {
            if (sex.equalsIgnoreCase("male"))
                rbMale.setSelected(true);
            else
                rbFemale.setSelected(true);
        }
        
        txtAddress.setText(getStringValueFromRow(index++));
        
        String salaryStr = getStringValueFromRow(index++);
        if (null == salaryStr)
            spinnerSalary.setValue(0);
        else
            spinnerSalary.setValue(Double.parseDouble(salaryStr));
        
        String managerId = getStringValueFromRow(index++);
        if (null != managerId) {
            String managerName = getNameOfManager(managerId);
            cbManager.setSelectedItem(managerName);
        }
        
        String departmentId = getStringValueFromRow(index);
        if (null != departmentId) {
            String departmentName = getNameOfDepartment(departmentId);
            cbDepartment.setSelectedItem(departmentName);
        }
    }//GEN-LAST:event_tableEmployeesMouseClicked

    private String getStringValueFromRow(int index) {
        Object value = tableEmployees.getModel().getValueAt(tableEmployees.getSelectedRow(), index);
        return value != null ? value.toString() : null;
    }
    
    private String getNameOfManager(String id) {
        ProjectManagementHandler handler = null;
        EmployeeHandler employeeHandler = null;
        ResultSet result = null;
        String name = null;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            employeeHandler = (EmployeeHandler)handler.getTableHandler(TableHandlerType.EMPLOYEE_HANDLER);
            
            result = employeeHandler.query(EmployeeHandler.TABLE_NAME, 
                    new String[] {EmployeeHandler.KEY_FIRST_NAME, EmployeeHandler.KEY_MIDDLE_NAME, EmployeeHandler.KEY_LAST_NAME},
                    EmployeeHandler.KEY_ID + " = '?'", new String[] {id}, null, null, null, null);
            
            
                result.next();
                name = result.getString(EmployeeHandler.KEY_FIRST_NAME) + " " 
                        + result.getString(EmployeeHandler.KEY_MIDDLE_NAME) + " "
                        + result.getString(EmployeeHandler.KEY_LAST_NAME);
           
        } catch (SQLException sqlEx) {
            return name;
        }
        
        return name;
    }
    
    private String getNameOfDepartment(String id) {
        ProjectManagementHandler handler = null;
        DepartmentHandler departmentHandler = null;
        ResultSet result = null;
        String name = null;
        
        try {
            handler = new ProjectManagementHandler("jdbc:mysql://localhost:3306/company_employee_management");
            departmentHandler = (DepartmentHandler)handler.getTableHandler(TableHandlerType.DEPARTMENT_HANDLER);
            
            result = departmentHandler.query(DepartmentHandler.TABLE_NAME, 
                    new String[] {DepartmentHandler.KEY_NAME},
                    DepartmentHandler.KEY_ID + " = '?'", new String[] {id}, null, null, null, null);
            
            
                result.next();
                name = result.getString(DepartmentHandler.KEY_NAME);
           
        } catch (SQLException sqlEx) {
            return name;
        }
        
        return name;
    }
    
    private Employee getValueOfComponents() {
        String firstName = txtFirstName.getText();
        String middleName = txtMiddleName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        Sex sex = null;
        
        if (rbFemale.isSelected()) {
            sex = Sex.FEMALE;
        }
        else {
            sex = Sex.MALE;
        }
        
        Double salary = null;
        if (null != spinnerSalary.getValue())
            salary = Double.parseDouble(spinnerSalary.getValue().toString());
        
        Date dateOfBirth = getDateOfBirth();
        String managerId = getManagerId();
        String departmentId = getDepartmentId();
        
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setAddress(address);
        employee.setSex(sex);
        employee.setSalary(salary);
        employee.setBirthDate(dateOfBirth);
        employee.setManagementEmployeeId(managerId);
        employee.setDepartmentId(departmentId);
        
        return employee;
    }
    
    private Date getDateOfBirth() {
        String dd = null;
        String mm = null;
        String yyyy = null;
        Date dateOfBirth = null;
        
        if (!txtDay.getText().isBlank()) 
            dd = txtDay.getText();
        
        if (!txtMonth.getText().isBlank())
            mm = txtMonth.getText();
        
        if (!txtYear.getText().isBlank()) 
            yyyy = txtYear.getText();
        
        try {
            if (null != dd && null != mm && null != yyyy)
                dateOfBirth = new SimpleDateFormat("MM-dd-yyyy").parse(mm + "-" + dd + "-" + yyyy);
        }
        catch (ParseException ex) {
            dateOfBirth = null;
        }
        
        return dateOfBirth;
    }
    
    private String getManagerId() {
        if (listEmployeeNames.size() == 1)
            return null;
        
        Integer managerIdSelectedIndex = cbManager.getSelectedIndex();
        String managerId = null;
        if (0 != managerIdSelectedIndex)
            managerId = listEmployeeIds.get(managerIdSelectedIndex);
        
        return managerId;
    }
    
    private String getDepartmentId() {
        Integer departmentSelectedIndex = cbDepartment.getSelectedIndex();
        String departmentId = null;
        if (0 != departmentSelectedIndex) 
            departmentId = listDepartmentIds.get(departmentSelectedIndex);     
        return departmentId;
    }
    
    private void refresh() {
        txtFirstName.setText("");
        txtMiddleName.setText("");
        txtLastName.setText("");
        txtMonth.setText("");
        txtDay.setText("");
        txtYear.setText("");
        txtAddress.setText("");
        spinnerSalary.setValue(0);
        buttonGroup1.clearSelection();
    }
    
    private void updateValuesOfRow(Employee employee, int rowIndex) {
        DefaultTableModel model = (DefaultTableModel)tableEmployees.getModel();
        int index = 1;
        
        model.setValueAt(employee.getFirstName(), rowIndex, index++);
        model.setValueAt(employee.getMiddleName(), rowIndex, index++);
        model.setValueAt(employee.getLastName(), rowIndex, index++);
        if (null != employee.getBirthDate())
            model.setValueAt(new SimpleDateFormat("MM-dd-YYYY").format(employee.getBirthDate()), rowIndex, index++);
        else
            model.setValueAt(null, rowIndex, index++);
        model.setValueAt(employee.getSex() == Sex.MALE ? "male" : "female", rowIndex, index++);
        model.setValueAt(employee.getAddress(), rowIndex, index++);
        model.setValueAt(employee.getSalary(), rowIndex, index++);
        model.setValueAt(employee.getManagementEmployeeId(), rowIndex, index++);
        model.setValueAt(employee.getDepartmentId(), rowIndex, index++);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInserth;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnRefresh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbDepartment;
    private javax.swing.JComboBox<String> cbManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbFemale;
    private javax.swing.JRadioButton rbMale;
    private javax.swing.JSpinner spinnerSalary;
    private javax.swing.JTable tableEmployees;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDay;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMiddleName;
    private javax.swing.JTextField txtMonth;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
