package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BuyerManagement {

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
	
	public String createPost(String buyer_name, String 	amount, String payment_methord) {
		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";

			}

			// create a prepared statement
			//LocalDate date = LocalDate.now();
			//LocalTime time = LocalTime.now();
			String query = "insert into buyer(buyer_name,amount,payment_methord) values (?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, buyer_name);
			preparedStmt.setString(2, amount);
			preparedStmt.setString(3, payment_methord);
			
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
			output = "<table border='1'><tr><th>Project ID</th><th> Buyer</th><th>Amount </th>" +
					 "<th>Payment Methord</th>" + 
					 "<th>Update</th><th>Remove</th></tr>"; 
			

			String query = "select * from buyer";
			java.sql.Statement stmt = con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set

			while (rs.next()) {
				String bid = Integer.toString(rs.getInt("bid"));
				String buyer_name = rs.getString("buyer_name");
				String amount = rs.getString("amount");
				String payment_methord = rs.getString("payment_methord");
				
				// Add into the html table

				output += "<tr><td>" + bid + "</td>";
				output += "<td>" + buyer_name + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + payment_methord + "</td>";
				
				// buttons
				output += "<td style='padding:10px; text-align:center;'><input name='btnUpdate' type='button' value='Update' class='btn btn-info'></td>"
						+ "<td style='padding:10px; text-align:center;'><form method='post' action='items.jsp'>"
						+ "<input style='margin-top:15px' name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + bid + "'>" + "</form></td></tr>";

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
	public String updateItem(String bid, String buyer_name, String amount, String payment_methord) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement

			String query = "UPDATE buyer SET buyer_name=?,amount=?,payment_methord=? WHERE bid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, buyer_name);
			preparedStmt.setString(2, amount);
			preparedStmt.setString(3, payment_methord);
			preparedStmt.setInt(4, Integer.parseInt(bid));
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
	
	public String deleteItem(String bid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from buyer where bid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bid));
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
