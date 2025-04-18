package stats.service;

import lombok.Data;

@Data
public class UserCreatedDTO {
    public Integer userId;
    public String email;
    public String role;
    public String dateCreated;
    public String name;
    public boolean active;
}
