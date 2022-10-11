package com.courseregistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userList")
public class UsersList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html");

		String lang = req.getParameter("languages");

		try {
			PrintWriter write = res.getWriter();
			Connection conne = ConnectionDB.initConnection();

			if (!lang.equals("Any")) {
				PreparedStatement state = conne.prepareStatement(
						"select * from RegisteredUsers where Language=? or Language1=? or Language2=?;");
				state.setString(1, lang);
				state.setString(2, lang);
				state.setString(3, lang);

				ResultSet set = state.executeQuery();
				while (set.next()) {
					write.println("First Name: " + set.getString("FirstName") + "<br/>Phone Number: "
							+ set.getLong("PhoneNumber") + "<br/>Email: " + set.getString("Email") + "<br/><br/>");
				}
				state.close();
			} else {
				PreparedStatement state = conne.prepareStatement("select * from RegisteredUsers;");

				ResultSet set = state.executeQuery();
				while (set.next()) {
					write.println("First Name: " + set.getString("FirstName") + "<br/>Phone Number: "
							+ set.getLong("PhoneNumber") + "<br/>Email: " + set.getString("Email") + "<br/><br/>");
				}
				state.close();
			}

			conne.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
