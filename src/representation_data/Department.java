package representation_data;

import java.util.Date;

public class Department {
    private String name;
    private String id;
    private String managerId;
    private Date inaugurationDate;

    public String getName() { return name; }
    
    public String getId() { return id; }
    
    public String getManagerId() { return managerId; }
    
    public Date getInaugurationDate() { return inaugurationDate; }
}
