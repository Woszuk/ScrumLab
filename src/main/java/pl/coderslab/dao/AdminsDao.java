package pl.coderslab.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.DuplicateException;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminsDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO admins (first_name, last_name, email, password) VALUES (?,?,?,?)";
    private static final String READ_USER_QUERY = "SELECT *  FROM admins WHERE email = ?";
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM admins";
    private static final String DISABLE_USER_QUERY = "UPDATE admins SET enable=0 WHERE id = ?";
    private static final String ENABLE_USER_QUERY = "UPDATE admins SET enable=1 WHERE id = ?";
    private static final String CHANGE_PASSWORD_QUERY = "UPDATE admins SET password = ? WHERE id = ?";
    private static final String CHANGE_DATA_QUERY = "UPDATE admins SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CREATE_USER_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            try {
                pr.setString(1, admin.getFirstName());
                pr.setString(2, admin.getLastName());
                pr.setString(3, admin.getEmail());
                pr.setString(4, hashPassword(admin.getPassword()));
                int result = pr.executeUpdate();
                if (result != 1) {
                    throw new RuntimeException("Execute update returned " + result);
                }
            } catch (MySQLIntegrityConstraintViolationException e) {
                throw new DuplicateException("Duplicate!");
            }
            try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    admin.setId(generatedKeys.getInt(1));
                    return admin;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public Admin read(String email, String password) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(READ_USER_QUERY)) {
            pr.setString(1, email);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    if(passwordCheck(password, rs.getString("password"))){
                        adminData(rs, admin);
                    }else {
                        throw new NotFoundException("Incorrect Data");
                    }
                }else {
                    throw new NotFoundException("Incorrect Data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public List<Admin> readAll() {
        List<Admin> adminsList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(READ_ALL_USERS_QUERY)) {
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Admin admin = new Admin();
                    adminData(rs, admin);
                    adminsList.add(admin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminsList;
    }

    public void disableUser(Integer userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(DISABLE_USER_QUERY)
        ) {
            pr.setInt(1, userId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enableUser(Integer userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(ENABLE_USER_QUERY)
        ) {
            pr.setInt(1, userId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(String password, int userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CHANGE_PASSWORD_QUERY)
        ) {
            pr.setString(1, hashPassword(password));
            pr.setInt(2, userId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeData(String firstName, String lastName, String email, int userId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CHANGE_DATA_QUERY)
        ) {
            pr.setString(1, firstName);
            pr.setString(2, lastName);
            pr.setString(3, email);
            pr.setInt(4, userId);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adminData(ResultSet rs, Admin admin) throws SQLException {
        admin.setId(rs.getInt("id"));
        admin.setFirstName(rs.getString("first_name"));
        admin.setLastName(rs.getString("last_name"));
        admin.setEmail(rs.getString("email"));
        admin.setPassword(rs.getString("password"));
        admin.setEnable(rs.getInt("enable"));
        admin.setSuperadmin(rs.getInt("superadmin"));
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean passwordCheck(String password, String passwordHashed){
        return BCrypt.checkpw(password, passwordHashed);
    }
}