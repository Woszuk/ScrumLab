package pl.coderslab.web.app;

import pl.coderslab.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = {"/app/delete"})
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Integer id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            if(name.equals("plan")){
                request.setAttribute("plan", name);
                request.setAttribute("id", id);
            }else if(name.equals("recipe")){
                request.setAttribute("recipe", name);
                request.setAttribute("id", id);
            }else if(name.equals("recipePlan")){
                Integer planId = Integer.parseInt(request.getParameter("planId"));
                request.setAttribute("recipePlan", name );
                request.setAttribute("id", id);
                request.setAttribute("planId", planId);
            }else{
                throw new NotFoundException("error");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/app/delete.jsp").forward(request, response);
        }catch (NumberFormatException | NotFoundException | NullPointerException e){
            response.sendRedirect("/app/dashboard");
        }
    }
}
