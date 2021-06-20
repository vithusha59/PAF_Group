package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectManagement {
	
	private static Connection connect() {

		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget?serverTimezone=UTC", "root","");

			System.out.println("succsess");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}
		return con;

	}
	
	public String createPost(String project_category, String project_name, String short_des, String price, String date,String project_goal ,String long_des  ) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";

			}

			// create a prepared statement
			//LocalDate date = LocalDate.now();
			//LocalTime time = LocalTime.now();
			String query = "insert into project(project_category,project_name,short_des,price,date,project_goal,long_des) values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, project_category);
			preparedStmt.setString(2, project_name);
			preparedStmt.setString(3, short_des);
			preparedStmt.setString(4, price);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, project_goal);
			preparedStmt.setString(7, long_des);

			// execute the statement

			preparedStmt.execute();
			con.close();

			output = "Inserted successfully";
		}

		catch (Exception e) {
			output = "Error while inserting the details.";
		
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}

		return output;
	}
	
	public String readPost() {
		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project ID</th><th>Project Category</th><th>Project Name</th>" +
					 "<th>Short Description</th>" + 
					 "<th>price</th>" +
					 "<th>Publish Date</th>" +
					 "<th>Project Goal</th>" +
					 "<th>Long Description</th>" +
					 "<th>Update</th><th>Remove</th></tr>"; 
			

			String query = "select * from project";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set

			while (rs.next()) {
				String pid = Integer.toString(rs.getInt("pid"));
				String project_category = rs.getString("project_category");
				String project_name = rs.getString("project_name");
				String short_des = rs.getString("short_des");
				String price = rs.getString("price");
				String date = rs.getString("date");
				String project_goal = rs.getString("project_goal");
				String long_des = rs.getString("long_des");

				// Add into the html table

				output += "<tr><td>" + pid + "</td>";
				output += "<td>" + project_category + "</td>";
				output += "<td>" + project_name + "</td>";
				output += "<td>" + short_des + "</td>";
				output += "<td>" + price + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + project_goal + "</td>";
				output += "<td>" + long_des + "</td>";

				// buttons
				output += "<td style='padding:10px; text-align:center;'><input name='btnUpdate' type='button' value='Update' class='btn btn-info'></td>"
						+ "<td style='padding:10px; text-align:center;'><form method='post' action='items.jsp'>"
						+ "<input style='margin-top:15px' name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + pid + "'>" + "</form></td></tr>";

			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "reading the posts.";
			System.out.println(e.getMessage());
			System.out.println(e);
			e.printStackTrace();
		}
		return output;
	}
	public String updateItem(String pid, String project_category, String project_name, String short_des, String price, String date, String project_goal,String long_des ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = "UPDATE project SET project_category=?,project_name=?,short_des=?,price=?,date=?,project_goal=?,long_des=? WHERE pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, project_category);
			preparedStmt.setString(2, project_name);
			preparedStmt.setString(3, short_des);
			preparedStmt.setString(4, price);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, project_goal);
			preparedStmt.setString(7, long_des);
			preparedStmt.setInt(8, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the post.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteItem(String pid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from project where pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
