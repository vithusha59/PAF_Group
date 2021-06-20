package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//Model
import model.Product;

//SET PATH ..............................................
@Path("/Product")
public class Product_Service {
	
	// Object
	Product productObj = new Product();
	
	// Read
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)

	public String readProduct() {

		return productObj.readProducts();
	}

	// Insert
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProducts(

			@FormParam("Product_Code") String Product_Code,
			@FormParam("Product_Name") String Product_Name,
			@FormParam("Category") String Category, 
			@FormParam("collaborators") String collaborators,
			@FormParam("Price") String Price,
	        @FormParam("Email") String Email)
	{

		String output = productObj.insertProducts(Product_Code, Product_Name, Category, collaborators, Price, Email);
		return output;
	}

	// Update
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProducts(String productData) {

		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();

		// Read the values from the JSON object
		String Product_ID = productObject.get("Product_ID").getAsString();
		String Product_Code = productObject.get("Product_Code").getAsString();
		String Product_Name = productObject.get("Product_Name").getAsString();
		String Category = productObject.get("Category").getAsString();
		String collaborators = productObject.get("collaborators").getAsString();
		String Price = productObject.get("Price").getAsString();
		String Email = productObject.get("Email").getAsString();

		String output = productObj.updateProducts(Product_ID, Product_Code, Product_Name, Category, collaborators, Price,Email);

		return output;
	}

	// Delete
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProducts(String productData) {

		// Convert the input string to an XML document
		Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

		// Read the value from the element <ProductID>
		String Product_ID = doc.select("Product_ID").text();

		String output = productObj.deleteProduct(Product_ID);
		return output;
	}

}
