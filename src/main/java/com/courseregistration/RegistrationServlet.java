package com.courseregistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

		res.setContentType("text/html");

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		long phNumber = Long.parseLong(req.getParameter("phNumber"));
		String email = req.getParameter("email");

		try {

			String[] language = req.getParameterValues("lang");
			PrintWriter write = res.getWriter();
			Connection connect = ConnectionDB.initConnection();

			PreparedStatement statement = connect
					.prepareStatement("insert into RegisteredUsers values(?, ?, ?, ?, ?, ?, ?);");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setLong(3, phNumber);
			statement.setString(4, email);

			if (language.length == 1) {
				statement.setString(5, language[0]);
				statement.setString(6, null);
				statement.setString(7, null);
				statement.executeUpdate();
				write.println("<h3>Registration success!!!</h3>");
			} else if (language.length == 2) {
				statement.setString(5, language[0]);
				statement.setString(6, language[1]);
				statement.setString(7, null);
				statement.executeUpdate();
				write.println("<h3>Registration success!!!</h3>");
			} else if (language.length == 3) {
				statement.setString(5, language[0]);
				statement.setString(6, language[1]);
				statement.setString(7, language[2]);
				statement.executeUpdate();
				write.println("<h3>Registration success!!!</h3>");
			} else {
				write.println("");
			}

			statement.close();
			connect.close();

		} catch (SQLIntegrityConstraintViolationException e) {
			PrintWriter pw = res.getWriter();
			pw.println("<h3>User already exists!!</h3>");
		} catch (NullPointerException e) {
			PrintWriter pw = res.getWriter();
			pw.println("<h3>Please select atleast on language!!!!</h3>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
