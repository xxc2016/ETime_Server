package database;
import java.sql.*;

public class JDBCConn {
     private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
     private static final String url = "jdbc:sqlserver://localhost:1433;DatabaseName=E_TIME";
//     private static final String user = "zyf";
//     private static final String password = "980829zyf";
     private static final String user = "e_time";
     private static final String password = "e_time123456";
     
     private static Boolean connectSQL = true;
     
     public static Connection getConnection() 
     {
    	 Connection conn = null;
    	 
    	 try
    	 {
    		 Class.forName(driver);
    		 System.out.println("���������ɹ�");
    		 
    		 conn = DriverManager.getConnection(url, user, password);
    		 System.out.println("���ݿ����ӳɹ�");
    		 connectSQL = true;
    	 }catch(ClassNotFoundException e)
    	 {
    		 e.printStackTrace();
    		 System.out.println("��������ʧ��");
    		 connectSQL = false;
    	 } catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���ݿ�����ʧ��");
			connectSQL = false;
		}
    	 
    	 return conn;
     }
     
     
     public Boolean getConnectionSQL()
     {
    	 return connectSQL;
     }
     
     
     

}
