package bean;

import java.util.List;

public class UserBean {
    private String account;
    private String head;
    private String nickName;
    private int requestCode;
    private int responseCode;
    private List<String> followList;
    private List<Integer> remarkList;
    private List<Integer>  postList;

    public final static int UNKNOWN_ERROR = 0x05001;//δ֪����

    public final static int USER_UP_STORE_REQUEST = 0x05002;//�û��ϴ�����
    public final static int USER_UP_STORE_RESPONSE_SUCCESSED = 0x05003;//�û��ϴ��ɹ�
    public final static int USER_UP_STORE_RESPONSE_FAILED =0x05004;//�û��ϴ�ʧ��

    public final static int USER_DOWN_LOAD_REQUEST = 0x05005;//�û���������
    public final static int USER_DOWN_LOAD_RESPONSE_SUCCESSED=0x05006;//�û����سɹ�
    public final static int USER_DOWN_LOAD_RESPONSE_FAILED=0x05007;//�û�����ʧ��

    public final static int USER_UP_FOLLOWLIST_REQUEST = 0x05008;//�û���Ӻ�������
    public final static int USER_UP_FOLLOWLIST_RESPONSE_SUCCESSED = 0x05009;//�û���Ӻ�������ɹ�
    public final static int USER_UP_FOLLOWLIST_RESPONSE_FAILED = 0x0500A;//�û���Ӻ�������ɹ�
    
    public final static int USER_DELETE_FOLLOWLIST_REQUEST = 0x0500B;//�û�ɾ����������
    public final static int USER_DELETE_FOLLOWLIST_RESPONSE_SUCCESSED = 0x0500C;//�û�ɾ����������ɹ�
    public final static int USER_DELETE_FOLLOWLIST_RESPONSE_FAILED = 0x0500D;//�û�ɾ����������ɹ�



    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setRemarkList(List<Integer> remarkList) {
        this.remarkList = remarkList;
    }

    public List<Integer> getPostList() {
        return postList;
    }

    public List<Integer> getRemarkList() {
        return remarkList;
    }

    public List<String> getFollowList() {
        return followList;
    }

    public String getAccount() {
        return account;
    }

    public String getHead() {
        return head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setFollowList(List<String> followList) {
        this.followList = followList;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPostList(List<Integer> postList) {
        this.postList = postList;
    }

}
