package DataTrade;

import Enum.TypeOfRequest;

public class Request {
    private TypeOfRequest requestType;
    private String requestMessage;

    public Request(TypeOfRequest requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }
    public Request(){

    }
    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public TypeOfRequest getRequestType() {
        return requestType;
    }

    public void setRequestType(TypeOfRequest requestType) {
        this.requestType = requestType;
    }
}
