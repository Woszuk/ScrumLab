package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.RecipePlanDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "AppDashboardServlet" , urlPatterns = {"/app/dashboard"})
public class AppDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao rd = new RecipeDao();
        PlanDao pd = new PlanDao();
        RecipePlanDao rpd = new RecipePlanDao();

        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") != null){
            user = (Admin)session.getAttribute("logged");
        }

        Set<String> dayName = new HashSet<>();
        Integer sumRecipe = rd.sumRecipesUser(user.getId());
        Integer sumPlans = pd.sumPlansUser(user.getId());
        List<RecipePlanDetails> recipePlanDetails = rpd.lastPlan(user.getId());
        for(RecipePlanDetails recipePlan: recipePlanDetails){
            dayName.add(recipePlan.getDayName());
        }
        String namePlan = pd.lastPlan(user.getId());

        request.setAttribute("namePlan", namePlan);
        request.setAttribute("sumRecipes", sumRecipe);
        request.setAttribute("sumPlans", sumPlans);
        request.setAttribute("recipePlanDetails", recipePlanDetails);
        request.setAttribute("dayName", dayName);

        getServletContext().getRequestDispatcher("/WEB-INF/app/dashboard.jsp").forward(request, response);
    }
}
