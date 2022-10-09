package common;

import java.io.Serializable;

public class MsgResp implements Serializable{
    private int status;
    private boolean result;
    private String adicionalInfo =  null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getAdicionalInfo(){return adicionalInfo;}

    public void setAdicionalInfo(String adicionalInfo){this.adicionalInfo = adicionalInfo;}
}
