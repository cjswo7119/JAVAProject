import java.sql.*;
import java.io.*;
import java.util.*;

public class DB_MAN {
    String strDriver="oracle.jdbc.driver.OracleDriver";  //jdbc드라이버
    //String strURL="jdbc:oracle:thin:@10.40.41.55:1521";
    String strURL="jdbc:oracle:thin:@localhost:1521";  //
    String strUser="INHATC";  //데이터베이스 사용자 아이디
    String strPWD="inha1958";  //데이터베이스 사용자 비밀번호
    
    Connection DB_con;  //db연결
    Statement DB_stmt;  //db상태
    ResultSet DB_rs;  //db실행 결과
    
    public void dbOpen() throws IOException{ //db접속
        try{
            Class.forName(strDriver);  //jdbc 드라이버를 읽는다.
            DB_con=DriverManager.getConnection(strURL,strUser,strPWD);
            //드라이버를 읽을때 연결한 데이터베이스의 사용자 ID와 암호르 함께 지정.
            DB_stmt=DB_con.createStatement();
            //데이터베이스에 작성된 sql을 보내거나 sql문을 실행하여 결과값을 반환하는 기능을 제공하는 statement객체 생성.
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    public void dbClose() throws IOException{  //db해제
        try{
            DB_stmt.close();  //연결된 statement객체외 jdbc연결 해제
            DB_con.close();  //연결된 데이터베이스와 jdbc연결 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }
}
