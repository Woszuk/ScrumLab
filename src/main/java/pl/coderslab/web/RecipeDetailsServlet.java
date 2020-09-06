package pl.coderslab.web;

import pl.coderslab.utils.RecipeData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RecipeDetailServlet", urlPatterns = {"/recipeDetails"})
public class RecipeDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeData.splitIngredients(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/recipe-details.jsp").forward(request, response);
    }
}
