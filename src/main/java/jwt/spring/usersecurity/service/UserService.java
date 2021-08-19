package jwt.spring.usersecurity.service;

import jwt.spring.usersecurity.domain.Role;
import jwt.spring.usersecurity.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();
}
