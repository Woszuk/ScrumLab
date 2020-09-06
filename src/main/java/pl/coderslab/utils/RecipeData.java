package pl.coderslab.utils;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Recipe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecipeData {
    public static void splitIngredients(HttpServletRequest request) {
        int recipeId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        if(session.getAttribute("logged") !=null){
            Admins user = (Admins)session.getAttribute("logged");
            RecipeDao rd = new RecipeDao();
            Recipe recipe = rd.detailsRecipe(recipeId, user.getId());
            checkIngredients(request, recipe);
        }else {
            RecipeDao rd = new RecipeDao();
            Recipe recipe = rd.detailsRecipeHomePage(recipeId);
            checkIngredients(request, recipe);
        }
    }

    public static void checkIngredients(HttpServletRequest request, Recipe recipe){

        String[] recipeIngredients;
        if (recipe.getIngredients().contains(",")) {
            recipeIngredients = recipe.getIngredients().split(",");
        } else if (recipe.getIngredients().contains(" ")) {
            recipeIngredients = recipe.getIngredients().split(" ");
        } else if (recipe.getIngredients().contains("\n")) {
            recipeIngredients = recipe.getIngredients().split("\n");
        } else {
            recipeIngredients = recipe.getIngredients().split(";");
        }

        request.setAttribute("recipe", recipe);
        request.setAttribute("ingredients", recipeIngredients);
    }
}
