package sk.pazican.adam;

public class ResponseObject {
    protected int status;
    protected String message;

    public ResponseObject(int status, String message){
        this.status = status;
        this.message = message;
    }

    protected int getStatus() {
        return status;
    }

    protected void setStatus(int status) {
        this.status = status;
    }

    protected String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }
}
