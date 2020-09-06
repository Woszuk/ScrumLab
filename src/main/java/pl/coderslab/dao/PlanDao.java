package pl.coderslab.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import pl.coderslab.exception.ForeignKeyException;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    private static final String ALL_PLANS_QUERY = "SELECT * FROM plan WHERE admin_id = ?";
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name, description, created, admin_id) VALUES (?, ?, ?, ?)";
    private static final String PLAN_QUERY = "SELECT * FROM plan WHERE id = ? AND admin_id = ?";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan WHERE id = ? AND admin_id = ?";
    private static final String UPDATE_PLAN_QUERY = "UPDATE plan SET name = ?, description = ? WHERE id = ?";
    private static final String ID_PLAN_QUERY = "SELECT id FROM plan WHERE name = ?";
    private static final String LAST_PLAN_QUERY = "SELECT name FROM plan WHERE admin_id = ? ORDER BY id DESC LIMIT 1";

    public List<Plan> allPlan(Integer adminId){
        List<Plan> plans = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_PLANS_QUERY)
        ){
            pr.setInt(1, adminId);
            try(ResultSet rs = pr.executeQuery()){
                while (rs.next()){
                    Plan plan = new Plan();
                    plan.setId(rs.getInt("id"));
                    plan.setName(rs.getString("name"));
                    plan.setDescription(rs.getString("description"));
                    plan.setCreated(rs.getString("created"));
                    plans.add(plan);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return plans;
    }

    public Plan createPlan(Plan plan, Integer adminId){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, plan.getName());
            pr.setString(2, plan.getDescription());
            pr.setString(3, plan.getCreated());
            pr.setInt(4, adminId);
            int result = pr.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plan;
    }

    public Plan detailsPlan(Integer id, Integer userId){
        Plan plan = new Plan();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(PLAN_QUERY)
        ){
            pr.setInt(1, id);
            pr.setInt(2, userId);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    plan.setId(rs.getInt("id"));
                    plan.setName(rs.getString("name"));
                    plan.setDescription(rs.getString("description"));
                }else{
                    throw new NotFoundException("Error");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return plan;
    }

    public void deleteSchedule(Integer id, Integer userId){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            try {
                pr.setInt(1, id);
                pr.setInt(2, userId);
                pr.executeUpdate();
            }catch (MySQLIntegrityConstraintViolationException e){
                throw new ForeignKeyException("Error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSchedule(String name, String description, Integer id){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            pr.setString(1, name);
            pr.setString(2, description);
            pr.setInt(3, id);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer sumPlansUser(Integer adminId){
        Integer sum = 0;
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_PLANS_QUERY)
        ){
            pr.setInt(1, adminId);
            try(ResultSet rs = pr.executeQuery()){
                while (rs.next()){
                    sum++;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return sum;
    }

    public Integer planId(String plan){
        Integer planId = 0;
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ID_PLAN_QUERY)
        ){
            pr.setString(1, plan);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    planId = rs.getInt("id");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return planId;
    }

    public String lastPlan(Integer adminId){
        String name = "";
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(LAST_PLAN_QUERY)
        ){
            pr.setInt(1, adminId);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    name = rs.getString("name");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return name;
    }
}
