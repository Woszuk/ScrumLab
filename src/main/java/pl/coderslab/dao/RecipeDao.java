package pl.coderslab.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import pl.coderslab.exception.ForeignKeyException;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    private static final String ALL_RECIPE_QUERY = "SELECT * FROM recipe";
    private static final String READ_RECIPE_QUERY = "SELECT * FROM recipe WHERE id = ? AND admin_id =?";
    private static final String READ_RECIPE_HOME_PAGE_QUERY = "SELECT * FROM recipe WHERE id = ?";
    private static final String ALL_RECIPE_USER_QUERY = "SELECT * FROM recipe WHERE admin_id = ?";
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, description, preparation_time, preparation, ingredients, created,  admin_id) VALUE (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe WHERE id = ? AND admin_id = ?";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ?, description = ?, preparation_time = ?, preparation =?, ingredients = ?, updated = ? WHERE id = ?";
    private static final String ID_RECIPE_QUERY = "SELECT id FROM recipe WHERE name = ?";

    public List<Recipe> allRecipe(){
        List <Recipe> recipes = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_RECIPE_QUERY);
        ){
            try(ResultSet rs = pr.executeQuery()){
                while (rs.next()){
                    Recipe recipe = new Recipe();
                    recipeData(recipe, rs);
                    recipes.add(recipe);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return recipes;
    }

    public Recipe detailsRecipe(Integer recipeId, Integer userId ){
        Recipe recipe = new Recipe();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(READ_RECIPE_QUERY)
        ){
            pr.setInt(1, recipeId);
            pr.setInt(2, userId);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    recipeData(recipe, rs);
                }else {
                    throw new NotFoundException("Error");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return recipe;
    }

    public Recipe detailsRecipeHomePage(Integer recipeId){
        Recipe recipe = new Recipe();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(READ_RECIPE_HOME_PAGE_QUERY)
        ){
            pr.setInt(1, recipeId);
            try(ResultSet rs = pr.executeQuery()){
                if (rs.next()){
                    recipeData(recipe, rs);
                }else {
                    throw new NotFoundException("Error");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return recipe;
    }

    public List<Recipe> allRecipesUser (Integer adminId){
        List<Recipe> recipes = new ArrayList<>();
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_RECIPE_USER_QUERY)
        ){
            pr.setInt(1, adminId);
            try(ResultSet rs = pr.executeQuery()){
                while (rs.next()){
                    Recipe recipe = new Recipe();
                    recipeData(recipe, rs);
                    recipes.add(recipe);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return recipes;
    }

    public Integer sumRecipesUser(Integer adminId){
        Integer sum = 0;
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(ALL_RECIPE_USER_QUERY)
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

    public Recipe createRecipe(Recipe recipe, Integer adminId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement pr = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, recipe.getName());
            pr.setString(2, recipe.getDescription());
            pr.setInt(3, recipe.getPreparationTime());
            pr.setString(4, recipe.getPreparation());
            pr.setString(5, recipe.getIngredients());
            pr.setString(6, recipe.getCreated());
            pr.setInt(7, adminId);
            int result = pr.executeUpdate();
            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }
            try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public void deleteRecipe (Integer recipeId, Integer userId){
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(DELETE_RECIPE_QUERY)
        ){
            try{
                pr.setInt(1, recipeId);
                pr.setInt(2, userId);
                pr.executeUpdate();
            }catch (MySQLIntegrityConstraintViolationException e){
                throw new ForeignKeyException("Error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecipe(String name, String description, Integer preparationTime, String preparation, String ingredients, String date, Integer recipeId){
        try(Connection connection = DbUtil.getConnection();
            PreparedStatement pr = connection.prepareStatement(UPDATE_RECIPE_QUERY)
        ){
            pr.setString(1, name);
            pr.setString(2, description);
            pr.setInt(3, preparationTime);
            pr.setString(4, preparation);
            pr.setString(5, ingredients);
            pr.setString(6, date);
            pr.setInt(7, recipeId);

            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void recipeData(Recipe recipe, ResultSet rs) throws SQLException {
        recipe.setId(rs.getInt("id"));
        recipe.setName(rs.getString("name"));
        recipe.setIngredients(rs.getString("ingredients"));
        recipe.setDescription(rs.getString("description"));
        recipe.setCreated(rs.getString("created"));
        recipe.setUpdated(rs.getString("updated"));
        recipe.setPreparationTime(rs.getInt("preparation_time"));
        recipe.setPreparation(rs.getString("preparation"));
    }
}
