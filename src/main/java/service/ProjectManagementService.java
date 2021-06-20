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

import com.ProjectManagement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Path("/Project")
public class ProjectManagementService {
	
	ProjectManagement project_ob =new ProjectManagement();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts() {
		return project_ob.readPost();
	}
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(
			@FormParam("project_category") String project_category, 
			@FormParam("project_name") String project_name,
			@FormParam("short_des") String short_des,
			@FormParam("price") String price,
			@FormParam("date") String date,
			@FormParam("project_goal") String project_goal,
			@FormParam("long_des") String long_des) {
		
		String output = project_ob.createPost(project_category, project_name, short_des, price, date, project_goal, long_des);

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

		String pid = postObject.get("pid").getAsString();
		String project_category = postObject.get("project_category").getAsString();
		String project_name = postObject.get("project_name").getAsString();
		String short_des = postObject.get("short_des").getAsString();
		String price = postObject.get("price").getAsString();
		String date = postObject.get("date").getAsString();
		String project_goal = postObject.get("project_goal").getAsString();
		String long_des = postObject.get("long_des").getAsString();

		String output = project_ob.updateItem(pid, project_category, project_name, short_des, price,date,project_goal, long_des);

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
	 String pid = doc.select("pid").text(); 
	 String output = project_ob.deleteItem(pid); 
	return output; 
	}
	


}
