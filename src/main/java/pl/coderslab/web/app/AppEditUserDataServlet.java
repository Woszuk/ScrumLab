package pl.coderslab.web.app;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AppEditUserDataServlet", urlPatterns = {"/app/editData"})
public class AppEditUserDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        if(firstName.equals("") || lastName.equals("") || email.equals("")) {
            Cookie cookie = new Cookie("errorEditUserData", "YES");
            cookie.setMaxAge(2);
            response.addCookie(cookie);
            response.sendRedirect("/app/editData");
        }else{
            HttpSession session = request.getSession();
            Admins user = new Admins();
            if(session.getAttribute("logged") != null){
                user = (Admins)session.getAttribute("logged");
            }

            AdminsDao ad = new AdminsDao();
            ad.changeData(firstName, lastName, email, user.getId());
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            response.sendRedirect("/app/editData");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/app/app-edit-user-data.jsp").forward(request, response);
    }
}
