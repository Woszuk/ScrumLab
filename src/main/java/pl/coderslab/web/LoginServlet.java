package pl.coderslab.web;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.AdminsDao;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AdminsDao ad = new AdminsDao();
        try {
            Admins user = ad.read(email, password);
            if (user.getEnable() == 1) {
                HttpSession sess = request.getSession();
                sess.setAttribute("logged", user);
                response.sendRedirect("/app/dashboard");
            } else if (user.getEnable() == 0) {
                request.setAttribute("disable", "disable");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else {
                throw new NotFoundException("error");
            }
        } catch (NotFoundException e) {
            request.setAttribute("error", "YES");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("logged") != null) {
            response.sendRedirect("/app/dashboard");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

    }
}
