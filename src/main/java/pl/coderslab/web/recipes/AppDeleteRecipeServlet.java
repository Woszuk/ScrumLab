package pl.coderslab.web.recipes;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.exception.ForeignKeyException;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AppDeleteRecipeServlet", urlPatterns = {"/app/deleteRecipe"})
public class AppDeleteRecipeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") != null){
            user = (Admin)session.getAttribute("logged");
        }
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            RecipeDao rd = new RecipeDao();
            rd.deleteRecipe(id, user.getId());
        } catch (ForeignKeyException e) {
            Cookie cookie = new Cookie("errorDeleteRecipe", idStr);
            cookie.setMaxAge(2);
            response.addCookie(cookie);
        }
        response.sendRedirect("/app/recipes");
    }
}
