package bean;

import java.util.List;

public class TraceBean {

    private List<Trace> traces;
    private String userAccount;
    private int requestCode;
    private int responseCode;

    public final static int  UP_STORE_REQUEST = 0x01001;//上传日志请求
    public final static int  UP_STORE_RESPONSE_SUCCESSED = 0x01002;//上传日志成功
    public final static int  UP_STORE_RESPONSE_FAILED =0x01003;//上传日志失败
    public final static int  DOWN_LOAD_REQUEST = 0x01004;//下载日志请求
    public final static int  DOWN_LOAD_REUQEST_SUCCESSED = 0x01005;//下载日志成功
    public final static int  DOWN_LOAD_REQUEST_FAILED =0x01006;//下载日志失败
    public final static int  UNKNOWN_ERROR =  0x01007; //未知错误

    public static class Trace {  //用于上传和下载用户日程
        public String userAccount;
        public String time;
        public String event;
        public String date;
        public boolean finish;
        public int traceId;
        public int imageType;
        public boolean important;
        public boolean urgent;
        public boolean fix;
        public int predict;


        public  Trace(String userAccount,String time,String event,String date,boolean finish,int traceId
                ,boolean important,boolean urgent,boolean fix,int predict) {
            this.userAccount = userAccount;
            this.time = time;
            this.event =event;
            this.date = date;
            this.finish = finish;
            this.traceId = traceId;
            this.important = important;
            this.urgent = urgent;
            this.fix = fix;
            this.predict = predict;
        }


        public  Trace(String userAccount,String time,String event,String date,int finish,int traceId
                ,int important,int urgent,int fix,int predict)
        {
            this.userAccount = userAccount;
            this.time = time;
            this.event = event;
            this.date  = date;
            this.finish  =judgeFinish_boolean(finish);
            this.traceId = traceId;
            this.important = judgeImportant_boolean(important);
            this.urgent = judgeUrgent_boolean(urgent);
            this.fix = judgeFix_boolean(fix);
            this.predict = predict;
        }


        public static boolean judgeImportant_boolean(int important)
        {
            if(important==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


        public static boolean judgeUrgent_boolean(int urgent)
        {
            if(urgent==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static boolean judgeFinish_boolean(int finish)
        {
            if(finish==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public static boolean judgeFix_boolean(int fix)
        {
            if(fix==1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setTraces(List<Trace> traces) {
        this.traces = traces;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public List<Trace> getTraces() {
        return traces;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }


    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
