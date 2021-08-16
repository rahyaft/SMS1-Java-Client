import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainClass {
	public static void main(String[] args) {
		
		// SMS1 API base URL
		String apiBaseUrl = "https://SubDomain.Domain:Port/api/service/";
		
		// The API name according to SMS1.ir
		// String apiName = "send";
		String apiName = "patternSend";
		
		// Token is received from SMS1.ir
		String token = "YOUR_TOKEN";
		
		// Sample input model for API 'send'
		SendModel sendModel = new SendModel();
		sendModel.Message = "سلام";
		sendModel.Recipient = "09120000000";
		
		
		// Sample input model for API 'patternSend'
		PatternSendModel patternSendModel = new PatternSendModel();
		
		// Pattern Id: received from SMS1.ir
		patternSendModel.PatternId = 7;
		patternSendModel.Recipient = "09120000000";
		
		// Variables that exist in the approved pattern of your Token
		patternSendModel.Pairs = new HashMap<>();
		patternSendModel.Pairs.put("variable_1", "value_1");
		patternSendModel.Pairs.put("variable_2", "value_2");
		
		
		// In this example, we use the 'FasterXML/jackson' library
		// You may use any other third-party library, such as GSON, to convert an object into a JSON string
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = null;
		try
		{
			// For API 'send', you should use:
			// jsonString = objectMapper.writeValueAsString(sendModel);
			jsonString = objectMapper.writeValueAsString(patternSendModel);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		
		
		// Initializing the HTTP client
		HttpClient client = HttpClient.newHttpClient();
		// Creating the request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiBaseUrl + apiName))
							// Setting the HTTP 'Authorization' header equal to the received Token from SMS1.ir
							.header("Authorization", "Bearer " + token)
							.header("Content-Type", "application/json")
				            // Setting the HTTP 'Accept' header to JSON
							.header("Accept", "application/json")
							// Setting the request body
							.POST(BodyPublishers.ofString(jsonString))
							.build();

		
		HttpResponse<String> response;
		try
		{
			response = client.send(request, BodyHandlers.ofString());
			
			// HTTP response body
			System.out.println(response.body());
			
			// HTTP status code
			System.out.println(response.statusCode());
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		} 
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
	}
}
