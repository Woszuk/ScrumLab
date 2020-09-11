package pl.coderslab.web.app;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AppEditPasswordServlet", urlPatterns = {"/app/editPassword"})
public class AppEditPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("newPassword");
        String password2 = request.getParameter("newPassword2");

        if (!password.matches("\\w{5,}") || !password.equals(password2)) {
            request.setAttribute("different", "YES");
            getServletContext().getRequestDispatcher("/WEB-INF/app/app-edit-password.jsp").forward(request, response);
        }else{
            HttpSession session = request.getSession();
            Admin user = new Admin();
            if(session.getAttribute("logged") != null){
                user = (Admin)session.getAttribute("logged");
            }

            AdminsDao ad = new AdminsDao();
            ad.changePassword(password, user.getId());

            response.sendRedirect("/app/editPassword");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/app/app-edit-password.jsp").forward(request, response);
    }
}
