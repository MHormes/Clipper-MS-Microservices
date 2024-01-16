package clipperms.trading.service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;


public class ExternalAPITemplate {

    //configure the url of the external API
    private static final String URI = "http://localhost:8080";

    @Value("${token}")
    private String token;

    //Client to make all the requests
    RestClient restClient;

    public ExternalAPITemplate() {
        restClient = RestClient.create();
    }

    public JsonObject post() {
        //json object => populate according to the external API requirements
        JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("key", "value");
        //jsonObject.addProperty("key", "value");

        return restClient.post()
                .uri(URI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(jsonObject)
                .retrieve()
                .body(JsonObject.class);
    }

}
