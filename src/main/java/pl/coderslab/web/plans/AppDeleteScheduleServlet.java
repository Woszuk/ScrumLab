package pl.coderslab.web.plans;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.exception.ForeignKeyException;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AppDeleteScheduleServlet", urlPatterns = {"/app/deleteSchedule"})
public class AppDeleteScheduleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admins user = new Admins();
        if(session.getAttribute("logged") != null){
            user = (Admins)session.getAttribute("logged");
        }
        String idStr = request.getParameter("id");
        Integer id = Integer.parseInt(idStr);
        try{
            PlanDao pd = new PlanDao();
            pd.deleteSchedule(id, user.getId());
        }catch (ForeignKeyException e){
            Cookie cookie = new Cookie("errorDeletePlan", idStr);
            cookie.setMaxAge(2);
            response.addCookie(cookie);
        }
        response.sendRedirect("/app/schedules");
    }
}
