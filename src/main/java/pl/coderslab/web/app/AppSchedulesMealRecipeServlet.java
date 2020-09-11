package pl.coderslab.web.app;

import pl.coderslab.dao.*;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppSchedulesMealRecipeServlet", urlPatterns = {"/app/recipePlanAdd"})
public class AppSchedulesMealRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");


        RecipePlanDao rpd = new RecipePlanDao();

        try {
            Integer planId = Integer.parseInt(request.getParameter("planId"));
            Integer recipeId = Integer.parseInt(request.getParameter("recipeId"));
            Integer dayNameId = Integer.parseInt(request.getParameter("dayId"));
            Integer mealNameId = Integer.parseInt(request.getParameter("mealNameId"));
            rpd.create(new RecipePlan(recipeId, mealNameId, dayNameId, planId));
            response.sendRedirect("/app/dashboard");
        }catch (NotFoundException | NumberFormatException e){
            Cookie cookie = new Cookie("errorRecipePlanAdd", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/recipePlanAdd");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") != null){
            user = (Admin)session.getAttribute("logged");
        }

        RecipeDao rd = new RecipeDao();
        PlanDao pd = new PlanDao();
        DayNameDao dnd = new DayNameDao();
        MealNameDao mnd = new MealNameDao();

        List<Recipe> recipes = rd.allRecipesUser(user.getId());
        List<Plan> plans = pd.allPlan(user.getId());
        List<DayName> dayNames = dnd.allDaysName();
        List<MealName> mealNames = mnd.allMealName();

        request.setAttribute("recipes", recipes);
        request.setAttribute("plans", plans);
        request.setAttribute("dayNames", dayNames);
        request.setAttribute("mealNames", mealNames);
        getServletContext().getRequestDispatcher("/WEB-INF/app/app-schedules-meal-recipe.jsp").forward(request, response);
    }
}
