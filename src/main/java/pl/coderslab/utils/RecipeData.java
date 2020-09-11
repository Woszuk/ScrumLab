package pl.coderslab.utils;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RecipeData {
    public static void splitIngredients(HttpServletRequest request, HttpServletResponse response) {
        int recipeId = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") !=null) {
            user = (Admin) session.getAttribute("logged");
        }
            RecipeDao rd = new RecipeDao();
            Recipe recipe = rd.detailsRecipe(recipeId, user.getId());
            checkIngredients(request, recipe);
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

    public static void splitIngredientsRecipe(HttpServletRequest request){
        int recipeId = Integer.parseInt(request.getParameter("id"));
        RecipeDao rd = new RecipeDao();
        Recipe recipe = rd.detailsRecipeHomePage(recipeId);
        checkIngredients(request, recipe);
    }
}
