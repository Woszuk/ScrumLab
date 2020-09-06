package pl.coderslab.web.app;

import pl.coderslab.dao.AdminsDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EnableUserServlet", urlPatterns = {"/app/enableUser"})
public class EnableUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AdminsDao ad = new AdminsDao();
        ad.enableUser(id);
        response.sendRedirect("/app/superAdminUsers");
    }
}
