package pl.coderslab.web.plans;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppSchedulesServlet", urlPatterns = {"/app/schedules"})
public class AppSchedulesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admins user = new Admins();
        if(session.getAttribute("logged") != null){
            user = (Admins)session.getAttribute("logged");
        }
        PlanDao pd = new PlanDao();
        List<Plan> plans = pd.allPlan(user.getId());
        request.setAttribute("plans", plans);
        getServletContext().getRequestDispatcher("/WEB-INF/plans/app-schedules.jsp").forward(request, response);
    }
}
