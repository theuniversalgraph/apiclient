import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import core.model.Node;

import java.util.List;

public class NodeRestClient {

    private Client client;
    private static String ENDPOINT = "http://localhost:8080/api/v1/nodes";

    public NodeRestClient() {
        this.client = Client.create();
    }

    public List<Node> getAll() {
        WebResource webResource = client.resource(ENDPOINT);
        final ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Gson gson = new Gson();
        return gson.fromJson(response.getEntity(String.class), new TypeToken<List<Node>>(){}.getType());
    }

    public Node getNodeById(final String id){
        WebResource webResource = client.resource(ENDPOINT + "/" + id);
        final ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Gson gson = new Gson();
        return gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
    }

    public Boolean deleteNodeById(final String id){
        WebResource webResource = client.resource(ENDPOINT + "/" + id);
        final ClientResponse response = webResource.accept("application/json")
                .delete(ClientResponse.class);
        if(response.getStatus() == 200){
            return true;
        } else{
            return false;
        }
    }

    public Node createNode(final Node node){
        WebResource webResource = client.resource(ENDPOINT);
        Gson gson = new Gson();
        String request = gson.toJson(node);
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, request);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
    }

    public Node updateNode(final String id, final Node node){
        WebResource webResource = client.resource(ENDPOINT + "/" + id);
        Gson gson = new Gson();
        String request = gson.toJson(node);
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, request);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
    }
}