package pl.coderslab.web.plans;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "AppEditScheduleServlet", urlPatterns = {"/app/editSchedule"})
public class AppEditScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        if(name.equals("") || description.equals("")){
            Cookie cookie = new Cookie("errorEditPlan", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/editSchedule?id="+id);
        }else {
            PlanDao pd = new PlanDao();
            pd.updateSchedule(name, description, id);
            response.sendRedirect("/app/schedules");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admins user = new Admins();
        if(session.getAttribute("logged") != null){
            user = (Admins)session.getAttribute("logged");
        }
        try{
            Integer id = Integer.parseInt(request.getParameter("id"));
            System.out.println(id);
            PlanDao pd = new PlanDao();
            Plan plan = pd.detailsPlan(id, user.getId());
            request.setAttribute("plan", plan);
            getServletContext().getRequestDispatcher("/WEB-INF/plans/app-edit-schedule.jsp").forward(request, response);
        }catch (NotFoundException e){
            response.sendRedirect("/app/dashboard");
        }catch (NumberFormatException e){
            response.sendRedirect("/app/schedules");
        }
    }
}
