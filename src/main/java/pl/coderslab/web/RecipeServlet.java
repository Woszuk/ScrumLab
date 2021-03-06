package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecipeServlet", urlPatterns = {"/recipe"})
public class RecipeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.allRecipe();
        request.setAttribute("recipes", recipes);
        getServletContext().getRequestDispatcher("/WEB-INF/recipe.jsp").forward(request, response);
    }
}