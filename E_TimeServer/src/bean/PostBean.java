package bean;

import java.util.List;

public class PostBean {

    private List<Post> posts;//������ϸ����
    private List<Integer> requestPostList;//������������б�
    private List<Integer> responsePostList;//��Ӧ��������б�
    private int requestCode;
    private int responseCode;

    public static class Post
    {
        public int PostId;
        public int detailId;
        public User user;
        public String title;
        public String pic;
        public String time;
        public String date;
        public int watch;
        public int remark;

        public Post(int PostId,int detailId,User user,String title,String pic,String time,int watch,int remark,String date)
        {
            this.detailId = detailId;
            this.PostId = PostId;
            this.user = user;
            this.title  =title;
            this.pic = pic;
            this.time = time;
            this.watch = watch;
            this.remark =  remark;
            this.date = date ;
        }
        
        public Post()
        {
        	
        }

        public static class User{
            public String account;
            public String head;
            public String nickName;
        }

    }

    final public static int UNKNOWN_ERROR = 0x02001;//δ֪����

    final public static int POST_UP_STORE_REQUEST = 0x02002;//�����ϴ�����
    final public static int POST_UP_STORE_RESPONSE_SUCCESSED = 0x02003;//�����ϴ��ɹ�
    final public static int POST_UP_STORE_RESPONSE_FAILED =0x02004;//�����ϴ�ʧ��

    final public static int POST_DOWN_LOAD_COMMUNITY_ALL_REQUEST = 0x02005;//����ȫ��������������
    final public static int POST_DOWN_LOAD_COMMUNITY_ALL_RESPONSE_SUCCESSED=0x02006;//����ȫ���������سɹ�
    final public static int POST_DOWN_LOAD_COMMUNITY_ALL_RESPONSE_FAILED=0x02007;//����ȫ����������ʧ��

    final public static int POST_DOWN_LOAD_LIST_REQUEST = 0x02008;//�������������������
    final public static int POST_DOWN_LOAD_LIST_RESPONSE_SUCCESSED=0x02009;//�����б��������سɹ�
    final public static int POST_DOWN_LOAD_LIST_RESPONSE_FAILED=0x0200A;//�����б���������ʧ��

    final public static int POST_COMMUNITY_GET_LIST_REQUEST = 0x0200B;//�����׼��������������������
    final public static int POST_COMMUNITY_GET_LIST_RESPONSE_SUCCESSED=0x0200C;//�����׼�����������������������سɹ�
    final public static int POST_COMMUNITY_GET_LIST_RESPONSE_FAILED=0x0200D;//�����׼������������������������ʧ��
    
    final public static int POST_DOWN_LOAD_LIST_TOP = 0x0200E; //��������Ϊ��׼��

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Integer> getRequestPostList() {
        return requestPostList;
    }

    public void setRequestPostList(List<Integer> requestPostList) {
        this.requestPostList = requestPostList;
    }

    public List<Integer> getResponsePostList() {
        return responsePostList;
    }

    public void setResponsePostList(List<Integer> responsePostList) {
        this.responsePostList = responsePostList;
    }
    
}