package nl._42.restsecure.autoconfigure.component;

public class GenericErrorResult {

    private String errorCode;

    public GenericErrorResult(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
