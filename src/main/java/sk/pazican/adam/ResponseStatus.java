package sk.pazican.adam;

public enum ResponseStatus {
    BADREQUEST(400),
    NOTFOUND(404),
    INTERNALERROR(500)
    ;
    private int responseStatus;

    ResponseStatus(int responseStatus){
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage(){
        switch (this.responseStatus){
            case 400:
                return "Bad request";
            case 404:
                return "Not found";
            case 500 :
                return "Internal server error";
        }
        return null;
    }
}
