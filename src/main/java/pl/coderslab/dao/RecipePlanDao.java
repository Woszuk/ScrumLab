package pl.coderslab.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.RecipePlan;
import pl.coderslab.model.RecipePlanDetails;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO recipe_plan (recipe_id, meal_name_id, day_name_id, plan_id) VALUES (?,?,?,?)";
    private static final String LAST_PLAN_USER_QUERY = "SELECT recipe.id as recipe_id, day_name.name as day_name, meal_name.name as meal_name,  recipe.name as recipe_name, recipe.description as recipe_description FROM `recipe_plan`" +
            "JOIN day_name on day_name.id=day_name_id JOIN recipe on recipe.id=recipe_id JOIN meal_name on meal_name.id = meal_name_id" +
            " WHERE recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?) " +
            "ORDER by day_name.display_order, meal_name.display_order";

    private static final String DETAIL_PLAN_QUERY = "SELECT  recipe.id as recipe_id, recipe_plan.id as id, plan_id as planId, day_name.name as day_name, meal_name.name as meal_name, recipe.name as recipe_name, recipe.description as recipe_description FROM recipe_plan " +
            "JOIN day_name on day_name.id=day_name_id JOIN recipe on recipe.id=recipe_id JOIN meal_name on meal_name.id = meal_name_id WHERE plan_id = ? " +
            "ORDER by day_name.display_order, meal_name.display_order";
    private static final String DELETE_RECIPE_PLAN_QUERY = "DELETE FROM recipe_plan WHERE id = ?";

    public RecipePlan create(RecipePlan recipePlan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CREATE_USER_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            try{
                pr.setInt(1, recipePlan.getRecipeId());
                pr.setInt(2, recipePlan.getMealNameId());
                pr.setInt(3, recipePlan.getDayName());
                pr.setInt(4, recipePlan.getPlanId());

                int result = pr.executeUpdate();
                if (result != 1) {
                    throw new RuntimeException("Execute update returned " + result);
                }
                try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
                    if (generatedKeys.first()) {
                        recipePlan.setId(generatedKeys.getInt(1));
                        return recipePlan;
                    } else {
                        throw new RuntimeException("Generated key was not found");
                    }
                }
            }catch (MySQLIntegrityConstraintViolationException e){
                throw new NotFoundException("Error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipePlan;
    }

    public List<RecipePlanDetails> lastPlan(Integer adminId) {
        List<RecipePlanDetails> recipePlanDetails = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(LAST_PLAN_USER_QUERY)) {
            pr.setInt(1, adminId);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    RecipePlanDetails rpd = new RecipePlanDetails();
                    rpd.setRecipeId(rs.getInt("recipe_id"));
                    rpd.setDayName(rs.getString("day_name"));
                    rpd.setMealName(rs.getString("meal_name"));
                    rpd.setRecipeName(rs.getString("recipe_name"));
                    rpd.setRecipeDescription(rs.getString("recipe_description"));
                    recipePlanDetails.add(rpd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipePlanDetails;
    }

    public List<RecipePlanDetails> detailPlan(Integer planId) {
        List<RecipePlanDetails> recipePlanDetails = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(DETAIL_PLAN_QUERY)) {
            pr.setInt(1, planId);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    RecipePlanDetails rpd = new RecipePlanDetails();
                    rpd.setId(rs.getInt("id"));
                    rpd.setPlanId(rs.getInt("planId"));
                    rpd.setRecipeId(rs.getInt("recipe_id"));
                    rpd.setDayName(rs.getString("day_name"));
                    rpd.setMealName(rs.getString("meal_name"));
                    rpd.setRecipeName(rs.getString("recipe_name"));
                    rpd.setRecipeDescription(rs.getString("recipe_description"));
                    recipePlanDetails.add(rpd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipePlanDetails;
    }

    public void deleteRecipePlan(Integer id){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(DELETE_RECIPE_PLAN_QUERY)) {
            pr.setInt(1, id);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}