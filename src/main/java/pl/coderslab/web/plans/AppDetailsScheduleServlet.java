package pl.coderslab.web.plans;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;
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

@WebServlet(name = "AppDetailsScheduleServlet", urlPatterns = {"/app/detailsSchedule"})
public class AppDetailsScheduleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin user = new Admin();
        if(session.getAttribute("logged") != null){
            user = (Admin)session.getAttribute("logged");
        }

        try{
            Integer id = Integer.parseInt(request.getParameter("id"));
            PlanDao pd = new PlanDao();
            RecipePlanDao rpd = new RecipePlanDao();
            Plan plan = pd.detailsPlan(id, user.getId());

            Set<String> dayName = new HashSet<>();
            List<RecipePlanDetails> recipePlanDetails = rpd.detailPlan(id);
            for(RecipePlanDetails recipePlan: recipePlanDetails){
                dayName.add(recipePlan.getDayName());
            }

            request.setAttribute("plan", plan);
            request.setAttribute("recipePlanDetails", recipePlanDetails);
            request.setAttribute("dayName", dayName);
            getServletContext().getRequestDispatcher("/WEB-INF/plans/app-details-schedule.jsp").forward(request, response);
        }catch (NotFoundException e){
            response.sendRedirect("/app/dashboard");
        }catch (NumberFormatException e){
            response.sendRedirect("/app/schedules");
        }
    }
}
