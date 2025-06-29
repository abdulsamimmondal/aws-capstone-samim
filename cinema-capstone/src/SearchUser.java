import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchUser
 */
@WebServlet("/SearchUser")
public class SearchUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // Define constants to read database connection details from environment variables
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();   //PrintWriter variable, out initialize.
		out.print("<!DOCTYPE html><html><head><link href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\r\n" +
				"<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script>\r\n" +
				"<script src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\r\n" +
				"<meta charset=\"UTF-8\"></head><body><h3>RESULT</h3>" +
		"<table border='1' style=\"border: solid 1px #DDEEEE;border-collapse: collapse;border-spacing: 0;font: normal 13px Arial, sans-serif\">"
		+ "<tr><th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">ID</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">USERNAME</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">FULLNAME</th>"
		+ "<th style=\"background-color: #DDEFEF;border: solid 1px #DDEEEE;color: #336B6B;padding: 10px;text-align: left;text-shadow: 1px 1px 1px #fff; \">CREATED BY ADMIN ID</th></tr>");
		//Availiable Movies' Table appears.
		
		String role = request.getParameter("role");
		
        // Construct the JDBC URL using the environment variables (moved outside the if/else for reusability)
        String jdbcUrl = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?ssl=true&sslmode=require&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		if(role.equals("admins")){
			try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) // Auto-closable connection
			{  //A try statement.
				Class.forName("org.postgresql.Driver");
				
				PreparedStatement ps1=con.prepareStatement("SELECT id,username,fullname,createdby_admin FROM admins where username=?;");
				ps1.setString(1,request.getParameter("username"));
				ResultSet rs = ps1.executeQuery();
				while(rs.next())  //A while statement.
				 {
					out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getInt(1));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(2));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(3));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getInt(4));
					out.print("</td></tr>");
					//Prints the Availiable Movies' Table.
				}
				rs.close();
                ps1.close(); // Close ps1
			}
			catch(Exception e)
			{ //Catch Statement.
				System.out.println(e); //Exception.
			}
			out.print("</table></body></html>"); //Close the html components.
		}
		else if(role.equals("contentadmins")) {
			try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) // Auto-closable connection
			{  //A try statement.
				Class.forName("org.postgresql.Driver");
				
				PreparedStatement ps1=con.prepareStatement("SELECT id,username,fullname,createdby_admin FROM contentadmins where username=?;");
				ps1.setString(1,request.getParameter("username"));
				ResultSet rs = ps1.executeQuery();
				while(rs.next())  //A while statement.
				 {
					out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getInt(1));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(2));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(3));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getInt(4));
					out.print("</td></tr>");
					//Prints the Availiable Movies' Table.
				}
				rs.close();
                ps1.close(); // Close ps1
			}
			catch(Exception e)
			{ //Catch Statement.
				System.out.println(e); //Exception.
			}
			out.print("</table></body></html>"); //Close the html components.
		}
		else { // Handles 'clients' role
			try (Connection con = DriverManager.getConnection(jdbcUrl, DB_USER, DB_PASSWORD)) // Auto-closable connection
			{  //A try statement.
				Class.forName("org.postgresql.Driver");
				
				PreparedStatement ps1=con.prepareStatement("SELECT id,username,fullname FROM clients where username=?;");
				ps1.setString(1,request.getParameter("username"));
				ResultSet rs = ps1.executeQuery();
				while(rs.next())  //A while statement.
				 {
					out.print("<tr><td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getInt(1));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(2));
					out.print("</td>");
					out.print("<td style=\"border: solid 1px #DDEEEE;color: #333;padding: 10px; text-shadow: 1px 1px 1px #fff;\">");
					out.println(rs.getString(3));
					out.print("</td></tr>");
					//Prints the Availiable Movies' Table.
				}
				rs.close();
                ps1.close(); // Close ps1
			}
			catch(Exception e)
			{ //Catch Statement.
				System.out.println(e); //Exception.
			}
			out.print("</table></body></html>"); //Close the html components.
		}
	}
}
