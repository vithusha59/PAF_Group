package model;

import java.sql.*;

public class Product {
	// DB Connection
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/ass_paf", "root", "");

			// For testing
			System.out.print("DB Successfully connected");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.print("DB not connected");
		}

		return con;
	}

	// Insert
	public String insertProducts(String Product_Code, String Product_Name, String Category, String collaborators,String Price,String Email) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into product(`Product_ID`,`Product_Code`,`Product_Name`,`Category`,`collaborators`,`Price`,`Email`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Product_Code);
			preparedStmt.setString(3, Product_Name);
			preparedStmt.setString(4, Category);
			preparedStmt.setString(5, collaborators);
			preparedStmt.setDouble(6, Double.parseDouble(Price));
			preparedStmt.setString(7, Email);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Insertion successful";

		} catch (Exception e) {
			output = "Insertion Unsuccess";
			System.err.println(e.getMessage());
		}
		return output;
	}

	// Read
	public String readProducts() {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Product_Code</th>" + "<th>Product_Name</th><th>Category</th>"
					+ "<th>collaborators</th>" + "<th>Price</th><th>Email</th></tr>";

			String query = "select * from product";

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String Product_ID  = Integer.toString(rs.getInt("Product_ID"));
				String Product_Code = rs.getString("Product_Code");
				String Product_Name = rs.getString("Product_Name");
				String Category = rs.getString("Category");
				String collaborators = rs.getString("collaborators");
				String Price = Double.toString(rs.getDouble("Price"));
				String Email = rs.getString("Email");

				// Add into the html table
				output += "<tr><td>" + Product_Code + "</td>";
				output += "<td>" + Product_Name + "</td>";
				output += "<td>" + Category + "</td>";
				output += "<td>" + collaborators + "</td>";
				output += "<td>" +"Rs. " + Price + "</td>";
				output += "<td>" + Email + "</td>";

			}

			con.close();

			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	// Update
	public String updateProducts(String Product_ID, String Product_Code, String Product_Name, String Category,
			String collaborators, String Price,String Email) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE product SET Product_Code=?,Product_Name=?,Category=?,collaborators=?,Price=?,Email=? WHERE Product_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Product_Code);
			preparedStmt.setString(2, Product_Name);
			preparedStmt.setString(3, Category);
			preparedStmt.setString(4, collaborators);
			preparedStmt.setDouble(5, Double.parseDouble(Price));
			preparedStmt.setString(6, Email);
			preparedStmt.setInt(7, Integer.parseInt(Product_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Successfully Updated";

		} catch (Exception e) {
			output = "Updating unsuccesful .";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	// Delete
	public String deleteProduct(String Product_ID) {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from product where Product_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Product_ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = " successfully Deleted";

		} catch (Exception e) {
			output = "Error while deleting the Product Details.";
			System.err.println(e.getMessage());
		}
		return output;
	}


}
