package pl.coderslab.web;

import pl.coderslab.utils.RecipeData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "RecipeDetailServlet", urlPatterns = {"/recipeDetails"})
public class RecipeDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeData.splitIngredientsRecipe(request);
        getServletContext().getRequestDispatcher("/WEB-INF/recipe-details.jsp").forward(request, response);
    }
}
