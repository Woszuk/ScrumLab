package pl.coderslab.web.recipes;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AppAddRecipeServlet", urlPatterns = {"/app/addRecipe"})
public class AppAddRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Integer preparationTime = 0;
        try {
            preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        } catch (NumberFormatException e) {
            preparationTime = 0;
        }
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String created = now.format(dtf);

        if(name.equals("") || description.equals("") || preparationTime==0 || preparation.equals("") || ingredients.equals("")){
            Cookie cookie = new Cookie("errorAddRecipe", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/addRecipe");
        }else{
            Recipe recipe = new Recipe(name, description, preparationTime, preparation, ingredients, created);
            HttpSession session = request.getSession();
            Admin user = new Admin();
            if(session.getAttribute("logged") != null){
                user = (Admin)session.getAttribute("logged");
            }

            RecipeDao rd = new RecipeDao();
            rd.createRecipe(recipe, user.getId());
            response.sendRedirect("/app/recipes");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/recipes/app-add-recipe.jsp").forward(request, response);
    }
}
