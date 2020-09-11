package pl.coderslab.web.recipes;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppRecipesServlet", urlPatterns = {"/app/recipes"})
public class AppRecipesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") != null){
            user = (Admin)session.getAttribute("logged");
        }

        RecipeDao rd = new RecipeDao();
        List<Recipe> recipeList = rd.allRecipesUser(user.getId());
        request.setAttribute("recipes", recipeList);
        getServletContext().getRequestDispatcher("/WEB-INF/recipes/app-recipes.jsp").forward(request, response);
    }
}
