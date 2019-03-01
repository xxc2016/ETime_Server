package bean;

public class User {
     private String name;
     private String password;
     private String nickName;
     private String ImagePath;
     private int requestCode;
     private int responseCode;

     final public static  int  LOGIN_REQUEST = 0x00001;  //��½����
     final public static  int  REGISTER_REQUEST = 0x00002;//ע������
     
     final public static  int  LOGIN_RESPONSE_SUCCESSED  =0x00003;//��½�ɹ�
     final public static  int  LOGIN_RESPONSE_FAILED  =0x00004;//��½ʧ��
     final public static  int  REGISTER_RESPONSE_SUCCESSED  =0x00005;//ע��ɹ�
     final public static  int  REGISTER_RESPONSE_FAILED  =0x00006;//ע��ʧ��
     
     final public static  int  IMAGE_STORE_REQUEST = 0x00007;//�û�ͷ���ϴ�����
     final public static  int  IMAGE_STORE_RESPONSE_SUCCESSED = 0x00008;//�û�ͷ���ϴ��ɹ�
     final public static  int  IMAGE_STORE_RESPONSE_FAILED =0x00009;//�û�ͷ���ϴ�ʧ��
     final public static  int  IMAGE_DOWNLOAD_REQUEST= 0x00010;//�û���Ϣ��������
     final public static  int  IMAGE_DOWNLOAD_RESPONSE_SUCCESSED = 0x00011;//�û���Ϣ���سɹ�
     final public static  int  IMAGE_DOWNLOAD_RESPONSE_FAILED = 0x00012;//�û���Ϣ����ʧ��
     final public static  int  IMAGE_STORE_EXTRA_REQUEST = 0x00013;//�û���Ϣ�ϴ�����(��ͷ��)
     final public static  int  IMAGE_STORE_EXTRA_RESPONSE_SUCCESSED = 0x00014;//�û���Ϣ�ϴ��ɹ�����ͷ��
     final public static  int  IMAGE_STORE_EXTRA_RESPONSE_FAILED =0x00015;//�û���Ϣ�ϴ�ʧ�ܣ���ͷ��
     
     final public static int   UNKNOWN_ERROR = 0x00016;//δ֪����


     public String getName() {
          return name;
     }

     public int getResponseCode() {
          return responseCode;
     }

     public String getPassword() {
          return password;
     }

     public int getRequestCode() {
          return requestCode;
     }

     public void setName(String name) {
          this.name = name;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public void setRequestCode(int requestCode) {
          this.requestCode = requestCode;
     }

     public void setResponseCode(int responseCode) {
          this.responseCode = responseCode;
     }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
}

