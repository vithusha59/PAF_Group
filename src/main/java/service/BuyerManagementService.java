package service;

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

import com.BuyerManagement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Byer")
public class BuyerManagementService {
	
	BuyerManagement buyer_ob = new BuyerManagement();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts() {
		return buyer_ob.readPost();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(
			@FormParam("buyer_name") String buyer_name, 
			@FormParam("amount") String amount,
			@FormParam("payment_methord") String payment_methord)
			 {
		
		String output = buyer_ob.createPost(buyer_name, amount, payment_methord);

		return output;
	}
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateItem(String postData) {
		// Convert the input string to a JSON object
		JsonObject postObject = new JsonParser().parse(postData).getAsJsonObject();

		// Read the values from the JSON object

		String bid = postObject.get("bid").getAsString();
		String buyer_name = postObject.get("buyer_name").getAsString();
		String amount = postObject.get("amount").getAsString();
		String payment_methord = postObject.get("payment_methord").getAsString();
	
		String output = buyer_ob.updateItem(bid, buyer_name, amount, payment_methord);

		return output;

	}
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String bid = doc.select("bid").text(); 
	 String output = buyer_ob.deleteItem(bid); 
	return output; 
	}

}
