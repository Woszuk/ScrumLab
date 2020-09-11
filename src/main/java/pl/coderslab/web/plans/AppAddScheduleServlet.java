package pl.coderslab.web.plans;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.model.Admin;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AppAddScheduleServlet", urlPatterns = {"/app/addSchedule"})
public class AppAddScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        if(name.equals("") || description.equals("")){
            Cookie cookie = new Cookie("errorAddPlan", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/addSchedule");
        }else{
            HttpSession session = request.getSession();
            Admin user = new Admin();
            if(session.getAttribute("logged") != null){
                user = (Admin)session.getAttribute("logged");
            }

            PlanDao pd = new PlanDao();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String created = now.format(dtf);

            Plan plan = new Plan(name, description, created);
            pd.createPlan(plan, user.getId());
            response.sendRedirect("/app/schedules");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/plans/app-add-schedule.jsp").forward(request, response);
    }
}
