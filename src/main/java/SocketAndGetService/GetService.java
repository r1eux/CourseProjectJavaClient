package SocketAndGetService;

import Enum.TypeOfRequest;
import DataTrade.Request;
import DataTrade.Response;
import com.google.gson.Gson;

import java.io.IOException;

public class GetService<T> {
    final Class<T> ClassType;

    public GetService(Class<T> classType) {
        ClassType = classType;
    }

    public String GetEntities(TypeOfRequest requestType,String message) {
        Request request = new Request();
        request.setRequestMessage(message);
        request.setRequestType(requestType);
        System.out.println("GetEntities !!!!!!!!!!!!!!!!!!!!"+String.valueOf(request.getRequestType()) );
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer = null;
        try {
            answer = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = new Gson().fromJson(answer, Response.class);
        System.out.println(response.getResponseData());
        return response.getResponseData();
    }

    public T GetEntity(TypeOfRequest requestType, T entity) throws IOException {
        Request request = new Request();

        request.setRequestMessage(new Gson().toJson(entity));
        request.setRequestType(requestType);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer;
        answer = ClientSocket.getInstance().getInStream().readLine();
        Response response = new Gson().fromJson(answer, Response.class);
        return entity = new Gson().fromJson(response.getResponseData(), ClassType);
    }
}
