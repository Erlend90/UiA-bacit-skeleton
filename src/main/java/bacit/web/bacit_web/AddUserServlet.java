package bacit.web.bacit_web;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "AddUserServlet", value = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Legg til en bruker:";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // AddUser
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<br>");
        out.println("<form action='AddUserServlet' method='POST'>");
        out.println("  <label for='firstName'>Fornavn:</label>");
        out.println("  <input type='text' name='firstName'/>");
        out.println("<br>");
        out.println("  <label for='lastName'>Etternavn:</label>");
        out.println("  <input type='text' name='lastName'/>");
        out.println("<br>");
        out.println("  <label for='userName'>Brukernavn:</label>");
        out.println("  <input type='text' name='userName'/>");
        out.println("<br>");
        out.println("  <label for='password'>Passord:</label>");
        out.println("  <input type='password' name='password'/>");
        out.println("<br>");
        out.println("  <label for='dob'>Fødselsdato:</label>");
        out.println("  <input type='date' name='dob'/>");
        out.println("<br>");
        out.println("  <input type='submit' />");
        out.println("</form>");
        out.println("</body></html>");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        UserModel user = new UserModel(request.getParameter("firstName"),request.getParameter("lastName"),request.getParameter("userName"),
                request.getParameter("password"),request.getParameter("dob"));
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getDob());

        try {
            PrintWriter out = response.getWriter();
            writeUserToDb(user,out);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeUserToDb(UserModel user, PrintWriter out) throws SQLException, ClassNotFoundException {
            Connection db = DBUtils.getINSTANCE().getConnection(out);
            String insertUserCommand = "insert into MytestDB.user (User_firstName, User_lastName, User_Email, User_password, User_dob) values(?,?,?,?,?)";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getDob());

            statement.execute();
        }
}