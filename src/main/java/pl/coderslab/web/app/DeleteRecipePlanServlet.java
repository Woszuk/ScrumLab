package pl.coderslab.web.app;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipePlanDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteRecipePlanServlet", urlPatterns = {"/app/deleteRecipePlan"})
public class DeleteRecipePlanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Integer planId = Integer.parseInt(request.getParameter("planId"));
        RecipePlanDao rpd = new RecipePlanDao();
        rpd.deleteRecipePlan(id);
        response.sendRedirect("/app/detailsSchedule?id="+planId);
    }
}
