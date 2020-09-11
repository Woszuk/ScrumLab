package pl.coderslab.web.recipes;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.utils.RecipeData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AppEditRecipeServlet", urlPatterns = {"/app/editRecipe"})
public class AppEditRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RecipeDao rd = new RecipeDao();

        Integer recipeId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String preparationTimeStr = request.getParameter("preparationTime");
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String updated = now.format(dtf);

        if(name.equals("")|| description.equals("") || preparationTimeStr.equals("") || preparation.equals("") || ingredients.equals("")){
            Cookie cookie = new Cookie("errorEditRecipe", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/editRecipe?id="+recipeId);
        }else{
            Integer preparationTime = Integer.parseInt(preparationTimeStr);
            rd.updateRecipe(name, description, preparationTime, preparation, ingredients, updated, recipeId);
            response.sendRedirect("/app/recipes");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            RecipeData.splitIngredients(request, response);
            getServletContext().getRequestDispatcher("/WEB-INF/recipes/app-edit-recipe.jsp").forward(request, response);
        }catch (NotFoundException e){
            response.sendRedirect("/app/dashboard");
        }catch (NumberFormatException e){
            response.sendRedirect("/app/recipes");
        }
    }
}
