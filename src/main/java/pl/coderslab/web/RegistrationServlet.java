package pl.coderslab.web;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.exception.DuplicateException;
import pl.coderslab.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        String newFirstName = firstName;
        String newLastName = firstName;
        if (!firstName.equals("") && !lastName.equals("")) {
            newFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            newLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        }
        AdminsDao ad = new AdminsDao();
        Admin user = new Admin(newFirstName, newLastName, email, password);

        if (newFirstName.equals("") || newLastName.equals("") || email.equals("")) {
            request.setAttribute("error", "YES");
            getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
        } else if (!password.matches("\\w{5,}") || !password.equals(password2)) {
            request.setAttribute("different", "YES");
            try {
                ad.create(user);
            } catch (DuplicateException e) {
                String duplicate = "YES";
                request.setAttribute("duplicate", duplicate);
            }
            getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
        } else {
            try {
                ad.create(user);
            } catch (DuplicateException e) {
                String duplicate = "YES";
                request.setAttribute("duplicate", duplicate);
                getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
            }
        }
        response.sendRedirect("/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response);
    }
}
