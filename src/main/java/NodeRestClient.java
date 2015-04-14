import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import core.model.Node;

import java.util.List;

public class NodeRestClient {

    private Client client;
    private static String END_POINT = "http://localhost:8080/api/v1/nodes";

    public NodeRestClient() {
        this.client = Client.create();
    }

    public List<Node> getAll() {
        WebResource webResource = client.resource(END_POINT);
        final ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Gson gson = new Gson();
        List<Node> nodeList = gson.fromJson(response.getEntity(String.class), new TypeToken<List<Node>>(){}.getType());
        return nodeList;
    }

    public Node getNode(String id){
        WebResource webResource = client.resource(END_POINT + "/" + id);
        final ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Gson gson = new Gson();
        Node node = gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
        return node;
    }

    public Boolean deleteNode(String id){
        WebResource webResource = client.resource(END_POINT + "/" + id);
        final ClientResponse response = webResource.accept("application/json")
                .delete(ClientResponse.class);
        if(response.getStatus() == 200){
            return true;
        } else{
            return false;
        }
    }

    public Node createNode(Node node){
        WebResource webResource = client.resource(END_POINT);
        Gson gson = new Gson();
        String request = gson.toJson(node);
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, request);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Node createdNode = gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
        return createdNode;
    }

    public Node updateNode(String id, Node node){
        WebResource webResource = client.resource(END_POINT + "/" + id);
        Gson gson = new Gson();
        String request = gson.toJson(node);
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, request);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        Node createdNode = gson.fromJson(response.getEntity(String.class), new TypeToken<Node>(){}.getType());
        return createdNode;
    }
}