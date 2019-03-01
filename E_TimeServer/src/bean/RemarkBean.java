package bean;


import java.util.List;

public class RemarkBean {
    public int remarkId;
    public User user;
    public int detailId;
    public String time;
    public String date;
    public String content;
    public List<String> bitmapPath;//����ͼƬ
    private int requestCode;
    private int responseCode;

    static  public class User{
        public String account;
        public String head;
        public String nickName;
    }

   public final static int UNKNOWN_ERROR = 0x04001;//δ֪����

   public final static int REMARK_UP_STORE_REQUEST = 0x04002;//�����ϴ�����
   public final static int REMARK_UP_STORE_RESPONSE_SUCCESSED = 0x04003;//�����ϴ��ɹ�
   public final static int REMARK_UP_STORE_RESPONSE_FAILED =0x04004;//�����ϴ�ʧ��
   public final static int REMARK_DOWN_LOAD_REQUEST = 0x04005;//������������
   public final static int REMARK_DOWN_LOAD_RESPONSE_SUCCESSED=0x04006;//�������سɹ�
   public final static int REMARK_DOWN_LOAD_RESPONSE_FAILED=0x04007;//��������ʧ��
    public final static int REMARK_DELETE_REQUEST = 0x04008;//����ɾ������
    public final static int REMARK_DELETE_RESPONSE_SUCCESSED=0x04009;//����ɾ���ɹ�
    public final static int REMARK_DELETE_RESPONSE_FAILED=0x0400A;//����ɾ��ʧ��

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public int getDetailId() {
        return detailId;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(int remarkId) {
        this.remarkId = remarkId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBitmapPath(List<String> bitmapPath) {
        this.bitmapPath = bitmapPath;
    }

    public List<String> getBitmapPath() {
        return bitmapPath;
    }
}
