package exception;

import lombok.Data;

@Data
public class ApiError {

    private String setCode;
    private int setStatus;
    private String setMessage;

    public void setCode(String s) {
    }

    public void setStatus(int i) {
    }

    public void setMessage(String message) {
    }
}
