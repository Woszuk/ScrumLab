package pl.coderslab.web.recipes;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.RecipeData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AppRecipeDetailsServlet", urlPatterns = {"/app/recipeDetails"})
public class AppRecipeDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            RecipeData.splitIngredients(request);
            getServletContext().getRequestDispatcher("/WEB-INF/recipes/app-recipe-details.jsp").forward(request, response);
        }catch (NotFoundException e){
            response.sendRedirect("/app/dashboard");
        }catch (NumberFormatException e){
            response.sendRedirect("/app/recipes");
        }
    }
}
