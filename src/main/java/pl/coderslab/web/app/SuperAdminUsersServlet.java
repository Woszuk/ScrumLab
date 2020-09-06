package pl.coderslab.web.app;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SuperAdminUsersServlet", urlPatterns = {"/app/superAdminUsers"})
public class SuperAdminUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admins user = new Admins();
        if(session.getAttribute("logged") != null){
            user = (Admins)session.getAttribute("logged");
        }

        if(user.getSuperadmin()== 1){
            AdminsDao ad = new AdminsDao();
            request.setAttribute("users", ad.readAll());
            getServletContext().getRequestDispatcher("/WEB-INF/app/super-admin-users.jsp").forward(request, response);
        }else{
            response.sendRedirect("/app/dashboard");
        }
    }
}
