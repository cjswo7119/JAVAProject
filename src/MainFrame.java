import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    DB_MAN DBM=new DB_MAN();  //DB_MAN 클래스 객체 선언
    String strSQL="Select * from Client";  //쿼리문
    
    class Product{  //정렬시 벡터를 이용한 테이블 정렬을 위해 각 컬럼의 데이터를 담는 Product클래스 생성.
        public String code;  //상품코드
        public String type;  //상품타입
        public String name;  //상품명
        public int price;  //상품가격

        Product(String code, String type, String name, int price){  //Product클래스의 생성자
            this.code=code;  //객체생성시 전달 받은 삳품코드 전달.
            this.type=type;  //객체생성시 전달 받은 삳품타입 전달.
            this.name=name;  //객체생성시 전달 받은 삳품명 전달.
            this.price=price;  //객체생성시 전달 받은 삳품가격 전달.
        }
    }
    
    class ProductDescComparator implements Comparator<Product>{  //상품 가격 내림차순 정렬하는 메소드
        @Override
        public int compare(Product arg0, Product arg1){
            if(arg0.price < arg1.price){
                return 1;
            }else if(arg0.price > arg1.price){
                return -1;
            }else{
                return 0;
            }
        }
    }
    class ProductAscComparator implements Comparator<Product>{  //상품 가격 오름차순 정렬하는 메소드
        @Override
        public int compare(Product arg0, Product arg1){
            if(arg0.price > arg1.price){
                return 1;
            }else if(arg0.price < arg1.price){
                return -1;
            }else{
                return 0;
            }
        }
    }
    
    public MainFrame() {
        initComponents();
        lblDisLogin.setVisible(false);
        //로그인 중 ID또는 PW가 일치하지 않을 경우 에러메시지 보여주기 위해 처음 가동시 안보이도록 해주기
        //비밀번호 찾기 중 ID또는 이메일이 일치하지 않을 경우 에러메시지 보여주기 위해 처음 가동시 안보이도록 해주기
        try{
            DBM.dbOpen();  //데이터베이스 접속
            getDBData(CpuTable,"CPU");
            //CPU 상품을 보여주는 cpu테이블(CpuTable)과 데이터베이스에서 P_type이 "CPU"인 상품을 검색하기 위해,
            //두번째 매개변수("CPU")를 통해 전달하여 where절을 통해 P_TYPE='CPU' 쿼리문으로 Cpu테이블 작성.
            getDBData(MainBoardTable,"Main Board");
            //MainBoard 상품을 보여주는 MainBoard테이블(MainBoardTable)과 데이터베이스에서 P_type이 "Main Board"인 상품을 검색하기 위해,
            //두번째 매개변수("Main Board")를 통해 전달하여 where절을 통해 P_TYPE='Main Board' 쿼리문으로 MainBoard테이블 작성.
            getDBData(MemoryTable,"Memory");
            //Memory 상품을 보여주는 Memory테이블(MemoryTable)과 데이터베이스에서 P_type이 "Memory"인 상품을 검색하기 위해,
            //두번째 매개변수("Memory")를 통해 전달하여 where절을 통해 P_TYPE='Memory' 쿼리문으로 Memory테이블 작성.
            getDBData(GraphicTable,"Graphic Card");
            //Graphic 상품을 보여주는 Graphic테이블(GraphicTable)과 데이터베이스에서 P_type이 "Graphic Card"인 상품을 검색하기 위해,
            //두번째 매개변수("Graphic Card")를 통해 전달하여 where절을 통해 P_TYPE='Graphic Card' 쿼리문으로 Graphic테이블 작성.
            getDBData(SSDTable,"SSD");
            //SSD 상품을 보여주는 SSD테이블(SSDTable)과 데이터베이스에서 P_type이 "SSD"인 상품을 검색하기 위해,
            //두번째 매개변수("SSD")를 통해 전달하여 where절을 통해 P_TYPE='SSD' 쿼리문으로 SSD테이블 작성.
            getDBData(HardDiskTable,"HardDisk");
            //SSD 상품을 보여주는 SSD테이블(SSDTable)과 데이터베이스에서 P_type이 "SSD"인 상품을 검색하기 위해,
            //두번째 매개변수("SSD")를 통해 전달하여 where절을 통해 P_TYPE='SSD' 쿼리문으로 SSD테이블 작성.
            getDBData(CaseTable,"Case");
            //SSD 상품을 보여주는 SSD테이블(SSDTable)과 데이터베이스에서 P_type이 "SSD"인 상품을 검색하기 위해,
            //두번째 매개변수("SSD")를 통해 전달하여 where절을 통해 P_TYPE='SSD' 쿼리문으로 SSD테이블 작성.
            getDBData(PowerTable,"Power");
            //SSD 상품을 보여주는 SSD테이블(SSDTable)과 데이터베이스에서 P_type이 "SSD"인 상품을 검색하기 위해,
            //두번째 매개변수("SSD")를 통해 전달하여 where절을 통해 P_TYPE='SSD' 쿼리문으로 SSD테이블 작성.
            DBM.dbClose();  //데이터베이스 해제
        }catch (Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        CpuTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        CpuTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        CpuTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        MainBoardTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        MainBoardTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        MainBoardTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        MemoryTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        MemoryTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        MemoryTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        GraphicTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        GraphicTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        GraphicTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        SSDTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        SSDTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        SSDTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        HardDiskTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        HardDiskTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        HardDiskTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        CaseTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        CaseTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        CaseTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        PowerTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        PowerTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        PowerTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        OrderTable.getColumnModel().getColumn(2).setMaxWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        OrderTable.getColumnModel().getColumn(2).setMinWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        OrderTable.getColumnModel().getColumn(2).setWidth(350);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        OrderListTable.getColumnModel().getColumn(0).setMaxWidth(35);  //해당 테이블의 주문번호의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문번호 속성 최대 너비 지정.
        OrderListTable.getColumnModel().getColumn(0).setMinWidth(35);  //해당 테이블의 주문번호의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문번호 속성 최소 너비 지정.
        OrderListTable.getColumnModel().getColumn(0).setWidth(35);  //해당 테이블의 주문번호의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문번호 속성 너비 지정.
        OrderListTable.getColumnModel().getColumn(1).setMaxWidth(175);  //해당 테이블의 주문날짜의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문날짜 속성 최대 너비 지정.
        OrderListTable.getColumnModel().getColumn(1).setMinWidth(175);  //해당 테이블의 주문날짜의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문날짜 속성 최소 너비 지정.
        OrderListTable.getColumnModel().getColumn(1).setWidth(175);  //해당 테이블의 주문날짜의 간격을 좁혀 너비의 낭비를 줄이기 위해 주문날짜 속성 너비 지정.
        OrderListTable.getColumnModel().getColumn(2).setMaxWidth(400);  //해당 테이블의 주소 컬럼 길이를 넓혀 주소를 모두 보이게 하기위해 주소컬럼의 속성 최대 너비 지정.
        OrderListTable.getColumnModel().getColumn(2).setMinWidth(400);  //해당 테이블의 주소 컬럼 길이를 넓혀 주소를 모두 보이게 하기위해 주소컬럼의 속성 최소 너비 지정.
        OrderListTable.getColumnModel().getColumn(2).setWidth(400);  //해당 테이블의 주소 컬럼 길이를 넓혀 주소를 모두 보이게 하기위해 주소컬럼의 속성 너비 지정.
    }

    public void getDBData(JTable table, String strType){
        String SQL="Select * from Product";  //상품정보를 화면에 보여주기 위한 select 쿼리문으로써 해당 상품 기종의 상품을 출력하기 위한 쿼리 시작 부분.
        String strOutput=SQL+" where Product.P_TYPE= '"+strType+"'";  //같은 상품 기종을 검색하여 테이블에 결과값 전달하기 위한 where 조건문.
        int iCntRow=0;  //테이블의 행을 증가시키며 상품을 추가할 정수형 변수
        try{
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strOutput);  //executeQuery입력받은 쿼리실행
            while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                table.setValueAt(DBM.DB_rs.getString("P_CODE"),iCntRow,0);  //테이블의 각 행의 0번째 인덱스 열에 상품코드를 전달.
                table.setValueAt(DBM.DB_rs.getString("P_TYPE"),iCntRow,1);  //테이블의 각 행의 1번째 인덱스 열에 상품 기종을 전달.
                table.setValueAt(DBM.DB_rs.getString("P_NAME"),iCntRow,2);  //테이블의 각 행의 2번째 인덱스 열에 상품명을 전달.
                table.setValueAt(DBM.DB_rs.getString("P_PRICE"),iCntRow,3);  //테이블의 각 행의 3번째 인덱스 열에 상품가격을 전달.
                iCntRow++;
                //다음 행으로 이동하여 상품정보를 받아와야하기 때문에 +1 증가.
                //만일 이 문장이 없을 시 계속 같은 행에 중첩해서 작성하기 때문에 필요.
            }
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    public void getDBOrderData(JTable table){  //관리자가 확인하기 위한 모든 고객들의 주문내역 확인.
        String SQL="Select * from ListOrder";  //상품정보를 화면에 보여주기 위한 select 쿼리문으로써 해당 상품 기종의 상품을 출력하기 위한 쿼리 시작 부분.
        int iCntRow=0;  //테이블의 행을 증가시키며 상품을 추가할 정수형 변수
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(SQL);  //executeQuery입력받은 쿼리실행
            while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                table.setValueAt(DBM.DB_rs.getString("NO"),iCntRow,0);  //테이블의 각 행의 0번째 인덱스 열에 주문번호를 전달.
                table.setValueAt(DBM.DB_rs.getString("ORDERDATE"),iCntRow,1);  //테이블의 각 행의 0번째 인덱스 열에 주문날짜를 전달.
                table.setValueAt(DBM.DB_rs.getString("ADDRESS"),iCntRow,2);  //테이블의 각 행의 1번째 인덱스 열에 주소를 전달.
                table.setValueAt(DBM.DB_rs.getString("PAYMENT"),iCntRow,3);  //테이블의 각 행의 2번째 인덱스 열에 결제방식을 전달.
                table.setValueAt(DBM.DB_rs.getString("TOTALPRICE"),iCntRow,4);  //테이블의 각 행의 3번째 인덱스 열에 결제금액을 전달.
                iCntRow++;
                //다음 행으로 이동하여 상품정보를 받아와야하기 때문에 +1 증가.
                //만일 이 문장이 없을 시 계속 같은 행에 중첩해서 작성하기 때문에 필요.
            }
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    public void getDBOrderData(JTable table, String strType){
        String SQL="Select * from ListOrder";  //상품정보를 화면에 보여주기 위한 select 쿼리문으로써 해당 상품 기종의 상품을 출력하기 위한 쿼리 시작 부분.
        String strOutput=SQL+" where ListOrder.ID= '"+strType+"'";  //로그인한 아이디를 검색하여 테이블에 결과값 전달하기 위한 where 조건문.
        int iCntRow=0;  //테이블의 행을 증가시키며 상품을 추가할 정수형 변수
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strOutput);  //executeQuery입력받은 쿼리실행
            while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                table.setValueAt(DBM.DB_rs.getString("NO"),iCntRow,0);  //테이블의 각 행의 0번째 인덱스 열에 주문번호를 전달.
                table.setValueAt(DBM.DB_rs.getString("ORDERDATE"),iCntRow,1);  //테이블의 각 행의 0번째 인덱스 열에 주문날짜를 전달.
                table.setValueAt(DBM.DB_rs.getString("ADDRESS"),iCntRow,2);  //테이블의 각 행의 1번째 인덱스 열에 주소를 전달.
                table.setValueAt(DBM.DB_rs.getString("PAYMENT"),iCntRow,3);  //테이블의 각 행의 2번째 인덱스 열에 결제방식을 전달.
                table.setValueAt(DBM.DB_rs.getString("TOTALPRICE"),iCntRow,4);  //테이블의 각 행의 3번째 인덱스 열에 결제금액을 전달.
                iCntRow++;
                //다음 행으로 이동하여 상품정보를 받아와야하기 때문에 +1 증가.
                //만일 이 문장이 없을 시 계속 같은 행에 중첩해서 작성하기 때문에 필요.
            }
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    public void TableSort(JTable table, String type){
        Vector<Product> vec = new Vector<Product>();
        int idx=0;  //반복문에서 전달받은 테이블의 각 행으로 움직이기 위한 정수형 변수.
        while(table.getValueAt(idx, 0)!= null){  
        //테이블의 각 행을 하나씩 읽으며, 각행의 컬럼을 Product객체를 생성하여 product의 각 인스턴스 변수에 전달.
        //존재하지 않는행의 첫번째 컬럼을 읽을시 null인 경우 반복문 탈출.
            Product product = new Product(table.getValueAt(idx, 0).toString(), table.getValueAt(idx, 1).toString(), table.getValueAt(idx, 2).toString(), Integer.parseInt(table.getValueAt(idx, 3).toString()));
            //Product클래스의 생성자를 이용한 product객체 생성.
            vec.add(product);  //벡터에 product객체 전달.
            idx++;  //다음행을 읽기 위해 1증가.
        }
        if(type.equals("high")){  //내림차순을 구하려는 경우
            Collections.sort(vec, new ProductDescComparator());
        }
        else if(type.equals("low")){  //오름차순을 구하려는 경우
            Collections.sort(vec, new ProductAscComparator());
        }
        for(idx=0; idx<vec.size(); idx++){  //정렬이 완료된 벡터를 읽어서 각 테이블의 각 행에 전달.
            table.setValueAt(vec.elementAt(idx).code, idx, 0);  //상품코드 전달.
            table.setValueAt(vec.elementAt(idx).type, idx, 1);  //상품타입 전달.
            table.setValueAt(vec.elementAt(idx).name, idx, 2);  //상품명 전달.
            table.setValueAt(vec.elementAt(idx).price, idx, 3);  //상품 가격 전달.
        }
    }
    
    public static String user="";  //비밀번호 분실 후 비밀번호를 수정할 해당 아이디를 담는 문자열 변수.
    public static String IDcheck="";  //회원가입 시 중복검사를 거쳐야만 회원가입이 될 수 있도록 검사하는 문자열 변수. 
    public static String loginuser="";  //로그인시 로그인한 유저의 정보를 담는 문자열 변수.
    public static String[] order={"null","null","null","null","null","null","null","null"};  //주문내역에 추가할 각 상품들의 정보
    public static String delorder="";  //주문을 취소할 주문번호를 담는 문자열 변수.
    public static String usertype="";  //유저 타입에 따른 기능제공을 다르게 하기 때문에 구별하기 위한 사용자 타입을 담은 문자열 변수.
    
    public static boolean isLower(String word) {
    //비밀번호가 소문자를 포함하는지 검사하기 위해 자바의 정규표현식을 통해 a~z를 포함한다면 true를 반환.
        return Pattern.matches("(^[a-z]*$)", word);
    }
    public static boolean isNum(String word) {
    //비밀번호가 숫자를 포함하는지 검사하기 위해 자바의 정규표현식을 통해 0~9를 포함한다면 true를 반환.
    //상품 추가 시, 상품코드와 상품가격이 숫자로 이루어졌는지 검사하기 위해 사용.
        return Pattern.matches("(^[0-9]*$)", word);
    }
    public static boolean isKorean(String word) {
    //비밀번호가 숫자를 포함하는지 검사하기 위해 자바의 정규표현식을 통해 0~9를 포함한다면 true를 반환.
        return Pattern.matches("(^[가-힣]*$)", word);
    }
    public static boolean isLowNum(String word) {
        boolean lower=false;  //소문자를 포함하는지 검사하기 위해 포함한다면 true, 없다면 false를 저장하는 변수.
        boolean number=false;  //숫자를 포함하는지 검사하기 위해 포함한다면 true, 없다면 false를 저장하는 변수.
        String[] arrayword=word.split("");
        //소문자 숫자 조합을 확인하기 위해 문자열을 한자씩 읽어서 문자열배열에 전달.
        for(int i=0;i<arrayword.length;i++){
        //문자열배열에서 한자씩 읽으며 소문자 또는 숫자가 포함됬는지 확인.
            if(isLower(arrayword[i])){  //입력받은 문자열이 소문자를 포함하는지 검사
                lower=isLower(arrayword[i]);  //포함시 지역변수 lower에 true를 전달.
            }
            if(isNum(arrayword[i])){  //입력받은 문자열이 숫자를 포함하는지 검사
                number=isNum(arrayword[i]);  //포함시 지역변수 number에 true를 전달.
            }
        }
        if(lower&&number){
        //소문자와 숫자 모두 포함한다면 true 리턴
            System.out.print(lower&&number);
            return true;
        }
        else {
        //소문자와 숫자 중 하나라도 포함하지 않는다면 false 리턴.
            System.out.print(lower&&number);
            return false;
        }
    }
    public boolean isNumeric(String s) {  //생년월일 입력 중 년도가 정수인지 판별하여 정수일 시 true, 아닐 시 false반환.
        try {
            Integer.parseInt(s);  //정수로 변환하여 성공하면 true.
            return true;
        } catch(NumberFormatException e) {  //정수로 변환 중 형 변환에 실패할 시 false 반환.
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jCheckBox6 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        comJob = new javax.swing.JComboBox<>();
        lblID = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPW = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtCheck = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnCheck = new javax.swing.JButton();
        btnJoin = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        txtID = new javax.swing.JTextField();
        lblCheck = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtBirth = new javax.swing.JLabel();
        comMonth = new javax.swing.JComboBox<>();
        comDay = new javax.swing.JComboBox<>();
        txtYear = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jLabel17 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jFrame2 = new javax.swing.JFrame();
        jLabel13 = new javax.swing.JLabel();
        FindClient = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        id_FindEmail = new javax.swing.JTextField();
        btnFindID = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        pw_FindID = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        pw_FindEmail = new javax.swing.JTextField();
        btnFindPW = new javax.swing.JButton();
        btnFindBack = new javax.swing.JButton();
        lblDisMat = new javax.swing.JLabel();
        jFrame3 = new javax.swing.JFrame();
        lbl_Success = new javax.swing.JLabel();
        btn_Success = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        txtNPW = new javax.swing.JPasswordField();
        txtNPWcheck = new javax.swing.JPasswordField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btnPWChangeBack = new javax.swing.JButton();
        btnPWChange = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        lblPWerror = new javax.swing.JLabel();
        jFrame4 = new javax.swing.JFrame();
        jLabel26 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jFrame5 = new javax.swing.JFrame();
        lblUser = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblUPUser = new javax.swing.JLabel();
        btnProOrder = new javax.swing.JButton();
        btnOrdList = new javax.swing.JButton();
        btnProdAdd = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        CpuTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        MainBoardTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        MemoryTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        GraphicTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        SSDTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        HardDiskTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        CaseTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        PowerTable = new javax.swing.JTable();
        ProdType = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        txtOrder = new javax.swing.JLabel();
        btnProdDel = new javax.swing.JButton();
        lblLogOut = new javax.swing.JLabel();
        btnProdCancle = new javax.swing.JButton();
        btnClientOrder = new javax.swing.JButton();
        jLabel73 = new javax.swing.JLabel();
        lblUserType = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Logout = new javax.swing.JMenuItem();
        Exit = new javax.swing.JMenuItem();
        jFrame6 = new javax.swing.JFrame();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtAddType = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtAddName = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtAddPrice = new javax.swing.JTextField();
        btnAddProdOK = new javax.swing.JButton();
        btnAddProdBack = new javax.swing.JButton();
        lblAddType = new javax.swing.JLabel();
        addProdType = new javax.swing.JComboBox<>();
        lblAddProdErr = new javax.swing.JLabel();
        jDialog3 = new javax.swing.JDialog();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        btnUpUser = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        comUpJob = new javax.swing.JComboBox<>();
        lblUpCheck = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtUpEmail = new javax.swing.JTextField();
        txtUpPW = new javax.swing.JPasswordField();
        txtUpBirth = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtUpCheck = new javax.swing.JPasswordField();
        comUpMonth = new javax.swing.JComboBox<>();
        comUpDay = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        txtUpYear = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        lblUpID = new javax.swing.JLabel();
        lblUpName = new javax.swing.JLabel();
        btnUpBack = new javax.swing.JButton();
        jLabel72 = new javax.swing.JLabel();
        lblUpType = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jDialog4 = new javax.swing.JDialog();
        jLabel56 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstPayment = new javax.swing.JList<>();
        txtAddress = new javax.swing.JTextField();
        btnOrder = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        btnOrdBack = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lblOrdID = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        jLabel63 = new javax.swing.JLabel();
        lblOrdcheck = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jFrame7 = new javax.swing.JFrame();
        jLabel29 = new javax.swing.JLabel();
        btnOrderOK = new javax.swing.JButton();
        jFrame8 = new javax.swing.JFrame();
        jLabel50 = new javax.swing.JLabel();
        btnOrderBack = new javax.swing.JButton();
        btnDelOrder = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        OrderListTable = new javax.swing.JTable();
        lblDelOrdCheck = new javax.swing.JLabel();
        lblFindUser = new javax.swing.JLabel();
        txtFindUser = new javax.swing.JTextField();
        btnFindUser = new javax.swing.JButton();
        btnAllUser = new javax.swing.JButton();
        jDialog5 = new javax.swing.JDialog();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblDelNo = new javax.swing.JLabel();
        lblDelDate = new javax.swing.JLabel();
        lblDelAddress = new javax.swing.JLabel();
        lblDelPay = new javax.swing.JLabel();
        lblDelPrice = new javax.swing.JLabel();
        btnDelCheck = new javax.swing.JButton();
        btnDelBack = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        lblDelCpu = new javax.swing.JLabel();
        lblDelMain = new javax.swing.JLabel();
        lblDelMem = new javax.swing.JLabel();
        lblDelGraphic = new javax.swing.JLabel();
        lblDelSsd = new javax.swing.JLabel();
        lblDelHard = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        lblDelCase = new javax.swing.JLabel();
        lblDelPower = new javax.swing.JLabel();
        jDialog6 = new javax.swing.JDialog();
        jLabel67 = new javax.swing.JLabel();
        btnClient = new javax.swing.JButton();
        btnManager = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel71 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Login_ID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnJoin_main = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        lblDisLogin = new javax.swing.JLabel();
        Login_PW = new javax.swing.JPasswordField();

        jDialog1.setModal(true);

        jCheckBox6.setText("동영상 편집");
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox6ActionPerformed(evt);
            }
        });

        jLabel8.setText("직 업");

        comJob.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "학생", "회사원", "선생님", "공무원", "무직", "기타" }));

        lblID.setText("ID 입력 후 중복 확인 부탁드립니다.");

        jLabel4.setText("회원 가입");

        jLabel5.setText("아이디");

        jLabel6.setText("비밀번호");

        jLabel7.setText("비밀번호 확인");

        jLabel10.setText("성 명");

        btnCheck.setText("중복확인");
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });

        btnJoin.setText("회원가입");
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        jLabel11.setText("성별");

        jLabel12.setText("관심 분야");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("남자");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("여자");

        jCheckBox1.setText("게임");

        jCheckBox2.setText("영화 시청");

        jCheckBox3.setText("프로그래밍");

        jCheckBox4.setText("독서");

        jCheckBox5.setText("강의 시청");

        lblCheck.setForeground(new java.awt.Color(255, 51, 0));

        jLabel14.setText("본인확인 이메일");

        txtBirth.setText("생년월일");

        comMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        comDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jLabel9.setText("년");

        jLabel15.setText("월");

        jLabel16.setText("일");
        jLabel16.setToolTipText("");

        btnBack.setText("취소");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel22.setText("비밀번호는 10자 이상 20자 미만 입력입니다.");

        jLabel70.setText("사용자 타입");
        jLabel70.setToolTipText("");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDialog1Layout.createSequentialGroup()
                            .addGap(149, 149, 149)
                            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jDialog1Layout.createSequentialGroup()
                                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jCheckBox1)
                                        .addComponent(jCheckBox4))
                                    .addGap(18, 18, 18)
                                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCheckBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jCheckBox3)
                                        .addComponent(jCheckBox6)))
                                .addGroup(jDialog1Layout.createSequentialGroup()
                                    .addComponent(jRadioButton1)
                                    .addGap(18, 18, 18)
                                    .addComponent(jRadioButton2))
                                .addComponent(comJob, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jDialog1Layout.createSequentialGroup()
                            .addGap(332, 332, 332)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnJoin)))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(txtBirth)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8))
                        .addGap(13, 13, 13)
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jDialog1Layout.createSequentialGroup()
                                        .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45))
                                    .addGroup(jDialog1Layout.createSequentialGroup()
                                        .addComponent(txtID)
                                        .addGap(12, 12, 12)))
                                .addComponent(btnCheck))
                            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPW, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                .addComponent(txtCheck, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDialog1Layout.createSequentialGroup()
                                .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comDay, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16))
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheck)
                    .addComponent(jLabel5))
                .addGap(1, 1, 1)
                .addComponent(lblID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comDay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel15)
                        .addComponent(jLabel16)
                        .addComponent(txtBirth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox2)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox4)
                            .addComponent(jCheckBox5)))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(12, 12, 12)
                .addComponent(lblCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnJoin)
                    .addComponent(btnBack))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("회원가입 완료!");

        jButton4.setText("확인");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jButton4)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jLabel13.setText("ID / PW 찾기");

        FindClient.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                FindClientStateChanged(evt);
            }
        });

        jLabel18.setText("본인 확인 이메일");

        btnFindID.setText("검색");
        btnFindID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindIDActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel21.setText("검색 결과 :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(id_FindEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFindID))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id_FindEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFindID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        FindClient.addTab("ID 찾기", jPanel1);

        jLabel19.setText("ID 입력");

        jLabel20.setText("본인 확인 이메일");
        jLabel20.setToolTipText("");

        btnFindPW.setText("검색");
        btnFindPW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindPWActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pw_FindEmail)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pw_FindID))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(0, 204, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFindPW)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pw_FindID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pw_FindEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFindPW)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        FindClient.addTab("PW 찾기", jPanel2);

        btnFindBack.setText("취소");
        btnFindBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindBackActionPerformed(evt);
            }
        });

        lblDisMat.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame2Layout.createSequentialGroup()
                                .addComponent(lblDisMat, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(btnFindBack))
                            .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FindClient, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnFindBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDisMat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_Success.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Success.setText("로그인 성공!");

        btn_Success.setText("확인");
        btn_Success.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuccessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame3Layout.createSequentialGroup()
                .addGroup(jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(lbl_Success, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jFrame3Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(btn_Success)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(lbl_Success, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Success)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jDialog2.setModal(true);

        jLabel23.setText("변경할 비밀번호");

        jLabel24.setText("비밀번호 확인");

        btnPWChangeBack.setText("취소");
        btnPWChangeBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPWChangeBackActionPerformed(evt);
            }
        });

        btnPWChange.setText("변경");
        btnPWChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPWChangeActionPerformed(evt);
            }
        });

        jLabel25.setText("비밀번호 변경");

        lblPWerror.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPWChangeBack)
                .addGap(18, 18, 18)
                .addComponent(btnPWChange)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel25)
                .addContainerGap(150, Short.MAX_VALUE))
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPWerror, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog2Layout.createSequentialGroup()
                        .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNPWcheck)
                            .addComponent(txtNPW))))
                .addContainerGap())
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(27, 27, 27)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNPWcheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addComponent(lblPWerror, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPWChangeBack)
                    .addComponent(btnPWChange))
                .addContainerGap())
        );

        jLabel26.setText("비밀번호 수정 완료!");

        jButton5.setText("완료");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(jFrame4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jButton5)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUser.setText("환영합니다!");
        lblUser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel28.setFont(new java.awt.Font("굴림", 1, 18)); // NOI18N
        jLabel28.setText("INHA COM");

        lblUPUser.setText("회원정보 수정");
        lblUPUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUPUserMouseClicked(evt);
            }
        });

        btnProOrder.setText("상품 주문");
        btnProOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProOrderActionPerformed(evt);
            }
        });

        btnOrdList.setText("주문 내역");
        btnOrdList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdListActionPerformed(evt);
            }
        });

        btnProdAdd.setText("상품 추가");
        btnProdAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdAddActionPerformed(evt);
            }
        });

        jLabel27.setText("상품 정보");

        CpuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CpuTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CpuTableMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(CpuTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CPU", jPanel3);

        MainBoardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MainBoardTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainBoardTableMouseClicked(evt);
            }
        });
        jScrollPane18.setViewportView(MainBoardTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("메인보드", jPanel4);

        MemoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        MemoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MemoryTableMouseClicked(evt);
            }
        });
        jScrollPane19.setViewportView(MemoryTable);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("메모리", jPanel5);

        GraphicTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        GraphicTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        GraphicTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GraphicTableMouseClicked(evt);
            }
        });
        jScrollPane20.setViewportView(GraphicTable);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("그래픽 카드", jPanel6);

        SSDTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        SSDTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SSDTableMouseClicked(evt);
            }
        });
        jScrollPane21.setViewportView(SSDTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("SSD", jPanel10);

        HardDiskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        HardDiskTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HardDiskTableMouseClicked(evt);
            }
        });
        jScrollPane22.setViewportView(HardDiskTable);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("하드디스크", jPanel7);

        CaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        CaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CaseTableMouseClicked(evt);
            }
        });
        jScrollPane23.setViewportView(CaseTable);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("케이스", jPanel8);

        PowerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        PowerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PowerTableMouseClicked(evt);
            }
        });
        jScrollPane24.setViewportView(PowerTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("파워", jPanel9);

        ProdType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "가격낮은순", "가격높은순" }));
        ProdType.setToolTipText("");
        ProdType.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ProdType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProdTypeItemStateChanged(evt);
            }
        });

        jLabel30.setText("주문 정보 :");

        btnProdDel.setText("상품 삭제");
        btnProdDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdDelActionPerformed(evt);
            }
        });

        lblLogOut.setText("로그아웃");
        lblLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogOutMouseClicked(evt);
            }
        });

        btnProdCancle.setText("선택 취소");
        btnProdCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdCancleActionPerformed(evt);
            }
        });

        btnClientOrder.setText("고객 주문");
        btnClientOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientOrderActionPerformed(evt);
            }
        });

        jLabel73.setText("type :");

        lblUserType.setForeground(new java.awt.Color(51, 51, 255));

        jMenu1.setText("Menu");

        Logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        Logout.setText("로그아웃");
        Logout.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                LogoutMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });
        jMenu1.add(Logout);

        Exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        Exit.setText("종료");
        Exit.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                ExitMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        jMenu1.add(Exit);

        jMenuBar1.add(jMenu1);

        jFrame5.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame5Layout.createSequentialGroup()
                        .addComponent(jLabel73)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLogOut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblUPUser))
                    .addGroup(jFrame5Layout.createSequentialGroup()
                        .addComponent(btnProdCancle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame5Layout.createSequentialGroup()
                        .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jFrame5Layout.createSequentialGroup()
                                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jFrame5Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ProdType, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jFrame5Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOrdList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProdAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProdDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(btnClientOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(lblUPUser)
                        .addComponent(lblLogOut)
                        .addComponent(lblUser)
                        .addComponent(jLabel73))
                    .addComponent(lblUserType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProdType, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jFrame5Layout.createSequentialGroup()
                        .addComponent(btnClientOrder)
                        .addGap(18, 18, 18)
                        .addComponent(btnOrdList)
                        .addGap(18, 18, 18)
                        .addComponent(btnProdAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnProdDel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProdCancle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnProOrder, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel30))
                    .addComponent(txtOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel31.setText("상품 추가");

        jLabel32.setText("기 종");

        jLabel33.setText("상품 코드");

        jLabel34.setText("상품명");

        jLabel35.setText("상품 가격");

        btnAddProdOK.setText("추 가");
        btnAddProdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdOKActionPerformed(evt);
            }
        });

        btnAddProdBack.setText("취 소");
        btnAddProdBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdBackActionPerformed(evt);
            }
        });

        lblAddType.setText("1");

        addProdType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CPU", "Main Board", "Memory", "Graphic Card", "SSD", "HardDisk", "Case", "Power" }));
        addProdType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                addProdTypeItemStateChanged(evt);
            }
        });

        lblAddProdErr.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jFrame6Layout = new javax.swing.GroupLayout(jFrame6.getContentPane());
        jFrame6.getContentPane().setLayout(jFrame6Layout);
        jFrame6Layout.setHorizontalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame6Layout.createSequentialGroup()
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jFrame6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jFrame6Layout.createSequentialGroup()
                                .addComponent(btnAddProdBack)
                                .addGap(18, 18, 18)
                                .addComponent(btnAddProdOK))
                            .addGroup(jFrame6Layout.createSequentialGroup()
                                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35))
                                .addGap(35, 35, 35)
                                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtAddName)
                                    .addComponent(txtAddPrice)
                                    .addGroup(jFrame6Layout.createSequentialGroup()
                                        .addComponent(lblAddType, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtAddType, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(addProdType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jFrame6Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jLabel31))
                    .addGroup(jFrame6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAddProdErr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jFrame6Layout.setVerticalGroup(
            jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(addProdType, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtAddType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddType))
                .addGap(18, 18, 18)
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtAddName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtAddPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAddProdErr, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProdOK)
                    .addComponent(btnAddProdBack))
                .addGap(25, 25, 25))
        );

        jDialog3.setModal(true);

        jLabel36.setText("성 명");

        jLabel37.setText("월");

        jLabel38.setText("일");
        jLabel38.setToolTipText("");

        btnUpUser.setText("정보 수정");
        btnUpUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpUserActionPerformed(evt);
            }
        });

        jLabel39.setText("비밀번호는 10자 이상 20자 미만 입력입니다.");

        jLabel40.setText("성별");

        jLabel41.setText("관심 분야");

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("남자");

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setText("여자");

        jCheckBox7.setText("게임");

        jCheckBox8.setText("영화 시청");

        jCheckBox9.setText("프로그래밍");

        jCheckBox10.setText("독서");

        jCheckBox11.setText("동영상 편집");
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });

        jCheckBox12.setText("강의 시청");

        jLabel42.setText("직 업");

        comUpJob.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "학생", "회사원", "선생님", "공무원", "무직", "기타" }));

        lblUpCheck.setForeground(new java.awt.Color(255, 51, 0));

        jLabel43.setText("본인 확인 이메일");

        jLabel44.setText("회원 정보 수정");

        txtUpBirth.setText("생년월일");

        jLabel45.setText("아이디");

        comUpMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        comUpDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        jLabel46.setText("비밀번호");

        jLabel47.setText("비밀번호 확인");

        jLabel48.setText("년");

        lblUpID.setText("ID란");

        lblUpName.setText("성명란");

        btnUpBack.setText("취 소");
        btnUpBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpBackActionPerformed(evt);
            }
        });

        jLabel72.setText("사용자 유형");

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel47)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog3Layout.createSequentialGroup()
                                        .addComponent(txtUpBirth)
                                        .addGap(54, 54, 54)
                                        .addComponent(txtUpYear, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comUpMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comUpDay, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel38))
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addComponent(jLabel45)
                                .addGap(68, 68, 68)
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUpPW, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUpID, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUpCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel42))
                                .addGap(2, 2, 2)
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDialog3Layout.createSequentialGroup()
                                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox7)
                                            .addComponent(jCheckBox10))
                                        .addGap(18, 18, 18)
                                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jCheckBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jCheckBox12))
                                        .addGap(18, 18, 18)
                                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox9)
                                            .addComponent(jCheckBox11)))
                                    .addGroup(jDialog3Layout.createSequentialGroup()
                                        .addComponent(jRadioButton3)
                                        .addGap(22, 22, 22)
                                        .addComponent(jRadioButton4))
                                    .addComponent(txtUpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comUpJob, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblUpCheck, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jDialog3Layout.createSequentialGroup()
                                            .addComponent(btnUpBack, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnUpUser)))))
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel72))
                                .addGap(35, 35, 35)
                                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblUpName, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                    .addComponent(lblUpType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel44)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUpType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUpName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(lblUpID, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUpPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUpCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comUpMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(txtUpYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comUpDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpBirth))
                .addGap(18, 18, 18)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtUpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jDialog3Layout.createSequentialGroup()
                            .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox7)
                                .addComponent(jCheckBox8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox10)
                                .addComponent(jCheckBox12)))
                        .addGroup(jDialog3Layout.createSequentialGroup()
                            .addComponent(jCheckBox9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jCheckBox11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comUpJob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUpCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpUser)
                    .addComponent(btnUpBack))
                .addContainerGap())
        );

        jDialog4.setModal(true);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel56.setText("결제 방식 :");

        lstPayment.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "카드", "무통장입금", "계좌이체" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lstPayment);

        btnOrder.setText("주 문");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel57.setText("총액 :");

        btnOrdBack.setText("취 소");
        btnOrdBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdBackActionPerformed(evt);
            }
        });

        jLabel58.setText("상품 주문");

        jLabel59.setText("아이디 :");

        lblTotalPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalPrice.setText("0");

        jLabel61.setText("원");

        lblOrdID.setText("ID");

        OrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "상품코드", "기종", "상품명", "가격"
            }
        ));
        jScrollPane5.setViewportView(OrderTable);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel63.setText("주소 :");

        jLabel49.setText("주문시간 :");

        lblDate.setText("date");

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addGroup(jDialog4Layout.createSequentialGroup()
                                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblOrdID, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel49)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddress)
                            .addGroup(jDialog4Layout.createSequentialGroup()
                                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOrdcheck, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jDialog4Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(49, 49, 49)
                                        .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel61))
                                    .addGroup(jDialog4Layout.createSequentialGroup()
                                        .addComponent(btnOrdBack, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addGap(330, 330, 330)
                .addComponent(jLabel58)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(lblOrdID, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(lblDate))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel63)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel57)
                        .addComponent(lblTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel61))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOrdcheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOrder)
                        .addComponent(btnOrdBack)))
                .addContainerGap())
        );

        jLabel29.setText("주문 완료!");

        btnOrderOK.setText("확 인");
        btnOrderOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame7Layout = new javax.swing.GroupLayout(jFrame7.getContentPane());
        jFrame7.getContentPane().setLayout(jFrame7Layout);
        jFrame7Layout.setHorizontalGroup(
            jFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame7Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(jFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOrderOK)
                    .addComponent(jLabel29))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jFrame7Layout.setVerticalGroup(
            jFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame7Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel29)
                .addGap(28, 28, 28)
                .addComponent(btnOrderOK)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("주문 목록");

        btnOrderBack.setText("취 소");
        btnOrderBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderBackActionPerformed(evt);
            }
        });

        btnDelOrder.setText("상세 내역");
        btnDelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelOrderActionPerformed(evt);
            }
        });

        OrderListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "주문 날짜", "주소", "결제 방식 ", "가 격(원)"
            }
        ));
        jScrollPane2.setViewportView(OrderListTable);

        lblDelOrdCheck.setText("상세 내역에서 주문을 취소하실 수 있습니다.");

        lblFindUser.setText("고객 검색");

        btnFindUser.setText("고객 검색");
        btnFindUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindUserActionPerformed(evt);
            }
        });

        btnAllUser.setText("통합 검색");
        btnAllUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame8Layout = new javax.swing.GroupLayout(jFrame8.getContentPane());
        jFrame8.getContentPane().setLayout(jFrame8Layout);
        jFrame8Layout.setHorizontalGroup(
            jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame8Layout.createSequentialGroup()
                        .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDelOrdCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame8Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblFindUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFindUser, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnFindUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOrderBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelOrder)
                            .addComponent(btnAllUser))))
                .addContainerGap())
        );
        jFrame8Layout.setVerticalGroup(
            jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFindUser)
                    .addComponent(txtFindUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFindUser)
                    .addComponent(btnAllUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOrderBack)
                        .addComponent(btnDelOrder))
                    .addComponent(lblDelOrdCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jDialog5.setModal(true);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("주문 내역");

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel52.setText("No :");

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel53.setText("주문 날짜 :");

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel54.setText("주소 :");

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel55.setText("결제 방식 :");

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel60.setText("가격(원) :");

        lblDelNo.setText("null");

        lblDelDate.setText("null");

        lblDelAddress.setText("null");

        lblDelPay.setText("null");

        lblDelPrice.setText("null");

        btnDelCheck.setText("주문 취소");
        btnDelCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelCheckActionPerformed(evt);
            }
        });

        btnDelBack.setText("취소");
        btnDelBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelBackActionPerformed(evt);
            }
        });

        jLabel62.setText("CPU :");

        jLabel64.setText("Mainboard :");

        jLabel65.setText("Memory :");

        jLabel66.setText("Graphic Card :");

        jLabel68.setText("SSD :");

        jLabel69.setText("Hard Disk :");

        lblDelCpu.setText("null");

        lblDelMain.setText("null");

        lblDelMem.setText("null");

        lblDelGraphic.setText("null");

        lblDelSsd.setText("null");

        lblDelHard.setText("null");

        jLabel77.setText("Case :");

        jLabel78.setText("Power :");

        lblDelCase.setText("null");

        lblDelPower.setText("null");

        javax.swing.GroupLayout jDialog5Layout = new javax.swing.GroupLayout(jDialog5.getContentPane());
        jDialog5.getContentPane().setLayout(jDialog5Layout);
        jDialog5Layout.setHorizontalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDelCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelSsd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelHard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelCase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelPower, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62)
                            .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel64)
                            .addComponent(jLabel65)
                            .addComponent(jLabel66))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDelNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jDialog5Layout.createSequentialGroup()
                                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDelMain)
                                    .addComponent(lblDelCpu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDelMem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDelGraphic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jDialog5Layout.createSequentialGroup()
                                            .addGap(22, 22, 22)
                                            .addComponent(jLabel78))
                                        .addComponent(jLabel69)
                                        .addGroup(jDialog5Layout.createSequentialGroup()
                                            .addGap(33, 33, 33)
                                            .addComponent(jLabel68)))
                                    .addComponent(jLabel77, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(101, 101, 101)))))
                .addContainerGap())
        );
        jDialog5Layout.setVerticalGroup(
            jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(lblDelNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(lblDelDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(lblDelAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(lblDelPay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(lblDelPrice))
                .addGap(18, 18, 18)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(lblDelCpu)
                            .addComponent(lblDelSsd))
                        .addGap(18, 18, 18)
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(lblDelMain)
                            .addComponent(lblDelHard))
                        .addGap(18, 18, 18)
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(lblDelMem)
                            .addComponent(jLabel77)
                            .addComponent(lblDelCase))
                        .addGap(18, 18, 18)
                        .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(lblDelGraphic)
                            .addComponent(lblDelPower)))
                    .addGroup(jDialog5Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel69)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel78)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jDialog5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelCheck)
                    .addComponent(btnDelBack))
                .addContainerGap())
        );

        jDialog6.setModal(true);

        jLabel67.setFont(new java.awt.Font("굴림체", 1, 18)); // NOI18N
        jLabel67.setText("사용자 타입");

        btnClient.setText("고 객");
        btnClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientActionPerformed(evt);
            }
        });

        btnManager.setText("관 리 자");
        btnManager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagerActionPerformed(evt);
            }
        });

        jButton6.setText("취 소");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel71.setText("사용자 유형을 선택해 주십시요.");

        javax.swing.GroupLayout jDialog6Layout = new javax.swing.GroupLayout(jDialog6.getContentPane());
        jDialog6.getContentPane().setLayout(jDialog6Layout);
        jDialog6Layout.setHorizontalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnClient, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel67)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog6Layout.setVerticalGroup(
            jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel67)
                .addGap(18, 18, 18)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jDialog6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClient, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManager, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("휴먼둥근헤드라인", 0, 24)); // NOI18N
        jLabel1.setText("로그인");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ID :");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("PW :");

        jButton1.setText("로그인");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnJoin_main.setText("회원가입");
        btnJoin_main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoin_mainActionPerformed(evt);
            }
        });

        jButton7.setText("ID / PW 찾기");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        lblDisLogin.setForeground(new java.awt.Color(255, 0, 102));
        lblDisLogin.setText("ID 또는 PW를 확인하여 주십시요.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnJoin_main, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Login_ID, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(lblDisLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Login_PW))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Login_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Login_PW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDisLogin)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(btnJoin_main))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnJoin_mainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoin_mainActionPerformed
        jDialog6.setLocation(350,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jDialog6.setSize(380,320);  //Model 대화상자 크기 지정
        jDialog6.show();  //회원가입을 위한 사용자 유형 선택창 보여주기
    }//GEN-LAST:event_btnJoin_mainActionPerformed

    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        String date;  //생년월일 중 입력받은 년도를 담는 문자열 변수.
        String gender = null;  //입력받은 성별을 저장한 문자열 변수.
        String email;  //입력받은 이메일이 유효한지 판단하기 위한 문자열 변수.
        int hobby=0;  //관심 분야 속성에 따른 bit연산을 담는 정수형 변수.

        if(txtID.getText().length()<5 || txtID.getText().length()>15){
        //ID가 5자 미만 15자 초과 입력 시 조건문 에러메세지 출력 후 리턴.
            if(txtID.getText().length()==0){
            //ID 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
                lblCheck.setText("ID를 입력받지 못했습니다.");
                //ID를 입력받지 못한 에러메시지 출력
                return;
            }
            lblCheck.setText("ID는 5자 이상 15자 이하 입력입니다.");
            //입력받은 ID길이가 5자 미만일 시 에러메시지 출력
            return;
        }
        else if(!txtID.getText().equals(IDcheck)){
        //중복검사 후 아이디와 회원가입 버튼 클릭 시 아이디가 다를 경우 리턴.
            lblCheck.setText("ID 중복 확인");
            return;
        }
        if(!isLowNum(txtID.getText())){
        //ID와 비밀번호 중 하나라도 포함되지 않은 경우 에러메시지 출력 후 리턴.
            lblCheck.setText("ID는 소문자와 숫자 조합입력이 필요합니다.");
            return;
        }
        if(txtPW.getText().length()<10 || txtPW.getText().length()>20){
        //PW가 10자 미만 20자 초과 입력 시 조건문 에러메세지 출력 후 리턴.
            if(txtPW.getText().length()==0){
            //PW 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
                lblCheck.setText("비밀번호를 입력받지 못했습니다.");
                //ID를 입력받지 못한 경우 에러메시지 출력
                return;
            }
            lblCheck.setText("비밀번호는 10자 이상 20자 이하 입력입니다.");
            //입력한 비밀번호 길이가 10자 미만일 시 에러메시지 출력
            return;
        }
        if(!isLowNum(txtPW.getText())){
        //입력받은 비밀번호가 숫자 또는 소문자가 포함되었는지 확인하기 위에서 생성한 isLowNum메서드 실행.
        //만일 숫자 또는 소문자가 포함되지 않을 경우 에러메시지를 출력 후 리턴.
            lblCheck.setText("비밀번호는 소문자와 숫자 조합입력이 필수입니다.");
            return;
        }
        if(!txtPW.getText().equals(txtCheck.getText())){
        //비밀번호와 비밀번호 확인이 불일치 할 경우 비밀번호 확인 text를 비워주고 에러메시지 출력 후 리턴.
            txtCheck.setText("");
            lblCheck.setText("입력받은 비밀번호가 불일치합니다");
            return;
        }
        if(txtName.getText().length()<2 || txtName.getText().length()>20){
        //PW가 2자 미만 20자 초과 입력 시 조건문 에러메세지 출력 후 리턴.
            if(txtName.getText().length()==0){
            //PW 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
                lblCheck.setText("성명을 입력받지 못했습니다.");
                //성명을 입력받지 못한 경우 에러메시지 출력
                return;
            }
            lblCheck.setText("성명은 2자 이상 20자 이하 입력입니다.");
            //입력한 성명의 길이가 2자 미만일 시 에러메시지 출력
            return;
        }
        if(!isKorean(txtName.getText())){
        //성명 입력시 한글이 아닌 경우 에러메시지 출력 후 리턴
                lblCheck.setText("성명은 한글로만 입력가능합니다.");
                return;
        }
        if(!isNumeric(txtYear.getText())) {  //생년월일 중 년도의 입력임으로 정수가 아닌 그외의 입력시 에러 메세지 출력 후 리턴.
            lblCheck.setText("년도는 정수의 입력만 가능합니다.");return;
        }
        if(txtYear.getText().length()!=4){ 
        //데이터베이스에 들어갈 때 포맷형식에 맞추어 입력한 년도는 네자리가 되도록 입력받는다.
        //아닐시 에러메세지 출력 후 리턴.
            lblCheck.setText("년도는 네자리로 입력해 주십시요. ex)1996 ");return;
        }
        date=txtYear.getText()+comMonth.getSelectedItem().toString()+comDay.getSelectedItem().toString();
        //사용자가 선택한 회원가입에서 입력한 생년월일로써 유효한 데이터인지 판단하기 위해 현재날짜와 비교하여 그보다 이전인지 확인하기 위한 문자열 date생성.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  //값을 비교하기 위해 위의 선언한 date의 형식과 같게 맞추기 위해 포맷형식(yyyymmdd)지정.
        Calendar cld = Calendar.getInstance();  //현재 날짜와 시간정보를 가진 Calender객체 cld생성.
        String strToday = sdf.format(cld.getTime());  //현재날짜정보(cld)를 위에서 지정한 포맷형식(yyyymmdd)으로 문자열 strToday생성.
        if(Integer.parseInt(date)>Integer.parseInt(strToday)){  //입력한 값이 현재보다 미래인 경우인지 확인.
            lblCheck.setText("정확한 년도를 입력해주시기 바랍니다.");return; //현재 입력한 값이 미래인 경우 에러메세지 출력 후 리턴.
        }
        if(jRadioButton1.isSelected())  //성별구분 남자 클릭시
            gender="M";
        else if(jRadioButton2.isSelected())  //성별구분 여자 클릭시
            gender="F";
        email=txtEmail.getText();  //보인확인 이메일 입력을 받아서 아래의 유효한 이메일인제 체크.
        if ((email.length() < 4) || !email.contains("@") || !email.contains(".")) {
        //이메일은 4자 미만이거나 '@'가 포함되지 않거나 '.'이 포함되지 않은 경우 오류메시지를 출력 후 리턴하여 빠져나와준다.
            lblCheck.setText("유효한 이메일이 아닙니다");  //오류메세지 출력
            return;  //유효하지 않은 이메일 확인 후 리턴
        }
        if(jCheckBox1.isSelected()) hobby+=32;  //게임
        if(jCheckBox2.isSelected()) hobby+=16;  //영화 시청
        if(jCheckBox3.isSelected()) hobby+=8;  //프로그래밍
        if(jCheckBox4.isSelected()) hobby+=4;  //독서
        if(jCheckBox5.isSelected()) hobby+=2;  //강의 시청
        if(jCheckBox6.isSelected()) hobby+=1;  //동영상 편집
        
        strSQL="Insert Into Client Values (";
        //회원가입 기능을 통해 Client테이블에 Insert문을 수행하여 튜플(새로운 데이터)을 추가하기 위한 sql문을 작성한다.
        strSQL+="'"+txtID.getText()+"', ";  //입력받은 id를 sql문에 추가.
        strSQL+="'"+txtPW.getText()+"', ";  //입력받은 비밀번호를 sql문에 추가.
        strSQL+="'"+txtName.getText()+"', ";  //입력받은 이름을 sql문에 추가.
        strSQL+="'"+date+"', ";  //입력받은 생년월일을 sql문에 추가.
        strSQL+="'"+email+"', ";  //입력받은 이메일을 sql문에 추가.
        strSQL+="'"+gender+"', ";  //입력받은 성별을 sql문에 추가.
        strSQL+="'"+Integer.toString(hobby)+"', ";  //입력받은 취미를 sql문에 추가.
        strSQL+="'"+comJob.getSelectedItem().toString()+"', ";  //입력받은 직업을 sql문에 추가.
        strSQL+="'"+lblType.getText()+"')";  //입력받은 사용자 유형을 sql문에 추가.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_stmt.executeUpdate(strSQL);
            //결과를 안 받아도될 떄는 executeUpdate, 즉 select문을 사용하지 않을 때 사용.
            DBM.dbClose();  //데이터베이스 연결 해제
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        
        txtID.setText("");txtPW.setText("");txtCheck.setText("");txtName.setText("");
        //파일에 정보 전달 후 화면 비우기
        IDcheck="";  //다음 회원가입을 위해 공백.
        lblID.setText("ID 입력 후 중복 확인 부탁드립니다.");  //회원가입 완료 후 처음상태로 돌아감.
        lblCheck.setText("");  //회원가입 완료 후 처음상태로 돌아감.
                
        jFrame1.setLocation(350,350);  //회원가입 완료창 화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jFrame1.setSize(300,300);  //회원가입 완료창 대화상자 크기 지정.
        jFrame1.show();  //회원가입 완료창 뛰우기.
        jDialog1.dispose();  //회원가입 종료.
    }//GEN-LAST:event_btnJoinActionPerformed

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        strSQL="Select Client.ID from Client Where Client.ID = '"+txtID.getText()+"'";
        //id 중복확인을 위해 sql문 중 select문을 입력받은 id와 동일한 튜플(행) 검색.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strSQL);
            //select 문을 통해 결과를 전달 받기 때문에 executeQuery사용.
            while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                if(txtID.getText().equals(DBM.DB_rs.getString("ID"))){
                //데이터베이스 각 행의 ID필드 값을 읽어온 뒤 비교했을 경우 입력받은 아이디와 동일한지 비교.
                    lblID.setText("중복된 아이디입니다.");  //비교하여 동일한 경우 에러메시지 전달.
                    txtID.setText("");  //중복확인이 된 아이디는 비워주기.
                    DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
                    DBM.dbClose();  //데이터베이스 연결해제
                    return;  //중복된 아이디를 발견하였다면 리턴.
                } 
            }
            lblID.setText("사용가능한 아이디입니다.");  //반복문을 통해 중복된 아이디가 아닌 경우 사용가능 메시지를 출력
            IDcheck=txtID.getText();
            //중복확인 후 회원가입을 진행 시 중복확인 때 검사를 마친 아이디와 동일한지 파악하기 위해,
            //중복확인 검사를 마친 후 입력받은 아이디를 IDcheck에 저장.
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
            DBM.dbClose();  //데이터베이스 연결해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }//GEN-LAST:event_btnCheckActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jFrame1.dispose();  //대화상자 닫기.
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jFrame2.setLocation(350,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jFrame2.setSize(380,490);  // ID/PW 찾기 프레임창 뛰우기(사이즈:368,330)
        jFrame2.show();  //ID/PW찾는 프레임 하면에 보여주기.
        jTextArea1.setText("");  //이전에 ID/PW를 찾은 적이 있을때 이전 기록이 남지 않도록 비워주기.
        id_FindEmail.setText("");  //이전에 ID를 찾기 위해 이메일을 입력한 적이 있다면 기록이 남지 않도록 비워주기.
        pw_FindID.setText("");  //이전에 PW를 찾기 위해 아이디를 입력한 적이 있다면 기록이 남지 않도록 비워주기.
        pw_FindEmail.setText("");  //이전에 PW를 찾기 위해 이메일을 입력한 적이 있다면 기록이 남지 않도록 비워주기.
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        jDialog1.dispose();  //Model 대화상자 닫기.(회원가입 도중 다른 작업을 하지 못하도록 jDialog로 생성 후 modal체크)
    }//GEN-LAST:event_btnBackActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        strSQL="Select Client.ID, Client.NAME, Client.TYPE from Client Where Client.ID = '"+Login_ID.getText()+"' and Client.PASSWORD= '"+Login_PW.getText()+"'";
        //로그인 시 ID와 PW를 동일한 값을 찾기 위해 쿼리문을 통해 입력받은 아이디와 비밀번호를 검색하는 쿼리문 작성.
        //회원 정보 수정시 ID와 성명을 통해 계정을 검색하며 ID와 성명은 고정값으로 두어 수정이 불가능하도록 함.
        //유형에 따른 기능제공을 다르게 하기위해 회원의 타입 검색.
        try{
            DBM.dbOpen(); //데이터베이스 접속.
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strSQL);  //위에서 입력받은 쿼리문을 전달.
            while(DBM.DB_rs.next()) {
            //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                lblUpID.setText(DBM.DB_rs.getString("ID"));  //회원정보 수정 시 로그인한 유저의 ID를 기입하여 해당 사용자의 정보를 조회할 때 사용
                lblUpName.setText(DBM.DB_rs.getString("NAME"));  //회원정보 수정 시 로그인한 유저의 Name을 기입하여 해당 사용자의 정보를 조회할 때 사용
                lblUpType.setText(DBM.DB_rs.getString("TYPE"));  //회원정보 수정 시 로그인한 유저의 사용자 타입을 기입하여 해당 사용자의 정보를 조회할 때 사용
                usertype=DBM.DB_rs.getString("TYPE");  //현재 로그인한 유저의 타입을 전역변수 usertype에 전달.
                lblDisLogin.setVisible(false);  //로그인 성공시 로그인 실패 메세지 감추기.
                DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
                DBM.dbClose();  //데이터베이스 연결해제
                loginuser=Login_ID.getText();  //로그인한 계정의 아이디 정보를 담아서 로그인 상태을 유지.
                lblUser.setText(loginuser+"님 환영합니다!");  //로그인한 사용자의 정보를 화면에 출력.
                jFrame5.setLocation(300,300);  //서비스 실행 화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
                jFrame5.setSize(760,450);  //서비스 실행 대화상자 크기 지정.
                jFrame5.show();  //서비스 실행창 뛰우기.
                jFrame3.setLocation(350,350);  //로그인 성공창 화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
                jFrame3.setSize(300,300);  //로그인 성공창 대화상자 크기 지정.
                jFrame3.show();  //로그인 성공창 뛰우기.
                Login_ID.setText("");  //로그인 후 로그아웃 시 입력했던 회원정보(ID)를 화면에서 비우기.
                Login_PW.setText("");  //로그인 후 로그아웃 시 입력했던 회원정보(PW)를 화면에서 비우기.
                for(int i=0; i<order.length; i++){  //로그인 시 서비스화면에서 주문정보를 초기화.
                    order[i]="null";  //각 상품인덱스를 null로 초기화
                }
                txtOrder.setText(Arrays.toString(order));  //입력받은 상품을 포함한 전체 주문내역을 초기화.
                if(usertype.equals("Manager")){  //로그인한 사용자가 관리자인 경우
                    lblUserType.setText("관리자용");  //관리자가 로그인한 경우 화면에 관리자용 출력.
                    btnOrdList.setVisible(false);  //주문 버튼 비활성화
                    btnClientOrder.setVisible(true);  //고객 주문 버튼 활성화
                    btnProdAdd.setVisible(true);  //상품 추가 버튼 활성화
                    btnProdDel.setVisible(true);  //상품 삭제 버튼 활성화
                    btnProOrder.setVisible(false);  //상품 주문 버튼 비활성화
                    btnProdCancle.setVisible(false);  //상품 선택 취소 번튼 비활성화
                }else if(usertype.equals("Client")){  //로그인한 사용자가 고객인 경우
                    lblUserType.setText("고객용");  //고객이 로그인한 경우 화면에 고객용 출력.
                    btnOrdList.setVisible(true);  //주문 버튼 활성화
                    btnClientOrder.setVisible(false);  //고객 주문 버튼 비활성화
                    btnProdAdd.setVisible(false);  //상품 추가 버튼 비활성화
                    btnProdDel.setVisible(false);  //상품 삭제 버튼 비활성화
                    btnProOrder.setVisible(true);  //상품 주문 버튼 활성화
                    btnProdCancle.setVisible(true);  //상품 선택 취소 번튼 비활성화
                }
                this.dispose();  //로그인을 완료하여 로그인 창 닫기.
                return;  //성공적으로 로그인 하였다면 리턴.
            }
            lblDisLogin.setVisible(true);  //로그인 실패시 로그인 실패 메세지 보여주기.
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
            DBM.dbClose();  //데이터베이스 연결해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_SuccessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuccessActionPerformed
        jFrame3.dispose();  //대화상자 닫기.
    }//GEN-LAST:event_btn_SuccessActionPerformed

    private void btnFindIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindIDActionPerformed
        jTextArea1.setText("");  //다른 이메일의 ID 조회 시 결과화면 비워주기.
        strSQL="Select Client.ID from Client Where Client.EMAIL = '"+id_FindEmail.getText()+"'";
        //아이디를 조회하기 위해 입력받은 본인확인용 이메일에 해당하는 아이디를 검색하는 쿼리문 작성.
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strSQL);
            //입력한 본인확인용이메일과 동일한 튜플(행)의 아이디를 검색하는 쿼리문 실행.
            if(!DBM.DB_rs.next()){
            //다음 튜플(행)로 커서를 옮기고 이메일을 검색하여 쿼리문의 결과가 없는 경우
            //에러메시지 출력 후 데이터베이스 연결을 해제한 후 리턴.
                lblDisMat.setText("이메일을 확인해주세요.");
                DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
                DBM.dbClose();  //데이터베이스 연결해제
                return;
            }
            do{
            //do-while문을 통해서 쿼리문의 결과가 없을경우 DBM.DB_rs.next()는 false를 반환하기 때문에
            //먼저 if문을 돌리게 하여 이메일이 조회되지 않을때 에러메시지를 출력하고
            //if문에서 쿼리문의 커서를 이미 다음위치로 옮겼으니 do-while을 통해 나머지 코딩을 수행하도록 한다.
            //쿼리문에서 동일한 아이디를 모두 검색하고 끝까지 검색했을 시 false를 통해 반복문 탈출.
                jTextArea1.append(DBM.DB_rs.getString("ID")+"\n");
                //textArea1에 append하여 기존내용에 추가하면서 아이디 출력.
                lblDisMat.setText("");  //do-while문을 통해 검색결과가 조회됬음으로 에러메시지를 비운다.
            }while(DBM.DB_rs.next());
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
            DBM.dbClose();  //데이터베이스 연결해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }//GEN-LAST:event_btnFindIDActionPerformed

    private void FindClientStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FindClientStateChanged
        jTextArea1.setText("");  //이전에 ID/PW를 찾은 적이 있을때 이전 기록이 남지 않도록 비워주기.
        id_FindEmail.setText("");  //이전에 ID를 찾기 위해 이메일을 입력한 적이 있다면 기록이 남지 않도록 비워주기.
        pw_FindID.setText("");  //이전에 PW를 찾기 위해 아이디를 입력한 적이 있다면 기록이 남지 않도록 비워주기.
        pw_FindEmail.setText("");  //이전에 PW를 찾기 위해 이메일을 입력한 적이 있다면 기록이 남지 않도록 비워주기.
    }//GEN-LAST:event_FindClientStateChanged

    private void btnFindPWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindPWActionPerformed
        jTextArea1.setText("");  //다른 계정의 PW 조회 시 결과화면 비워주기.
        strSQL="Select Client.ID, Client.PASSWORD from Client Where Client.ID = '"+pw_FindID.getText()+"' and "+"Client.EMAIL = '"+pw_FindEmail.getText()+"'";
        //PW를 조회하기 위해 입력받은 본인확인용 이메일과 함께 해당하는 아이디를 검색하는 쿼리문 작성.
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(strSQL);
            //입력한 본인확인용이메일과 아이디가 동일한 튜플(행)의 비밀번호를 검색하는 쿼리문 실행.
            if(!DBM.DB_rs.next()){
            //다음 튜플(행)로 커서를 옮기고 쿼리문을 검색하여 쿼리문의 결과가 없는 경우
            //에러메시지 출력 후 데이터베이스 연결을 해제한 후 리턴.
                lblDisMat.setText("아이디 또는 이메일을 확인해주세요.");
                DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
                DBM.dbClose();  //데이터베이스 연결해제
                return;
            }
            do{
            //do-while문을 통해서 쿼리문의 결과가 없을경우 DBM.DB_rs.next()는 false를 반환하기 때문에
            //먼저 if문을 돌리게 하여 쿼리문이 조회되지 않을때 에러메시지를 출력하고
            //if문에서 쿼리문의 커서를 이미 다음위치로 옮겼으니 do-while을 통해 나머지 코딩을 수행하도록 한다.
            //쿼리문에서 튜플(행)를 모두 검색하고 끝까지 검색했을 시 false를 통해 반복문 탈출.
                user=DBM.DB_rs.getString("ID");  //해당 아이디의 비밀번호 수정 시 퀴리문에서 사용할 아이디를 전역변수 user에 전달
                jDialog2.setLocation(250,250);  //비밀번호 수정 화면. 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
                jDialog2.setSize(320,300);  //비밀번호 수정 화면 크기 지정.
                jDialog2.show();  //비밀번호 수정 화면 보여주기.
                lblDisMat.setText("");  //do-while문을 통해 검색결과가 조회됬음으로 에러메시지를 비운다.
            }while(DBM.DB_rs.next());
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
            DBM.dbClose();  //데이터베이스 연결해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
    }//GEN-LAST:event_btnFindPWActionPerformed

    private void btnFindBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindBackActionPerformed
        jFrame2.dispose();  //ID/PW찾기 대화상자 닫기.
    }//GEN-LAST:event_btnFindBackActionPerformed

    private void btnPWChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPWChangeActionPerformed
        if(txtNPW.getText().length()<10 || txtNPW.getText().length()>20){
        //PW가 10자 미만 20자 초과 입력 시 조건문 에러메세지 출력 후 리턴.
            if(txtNPW.getText().length()==0){
            //PW 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
                lblPWerror.setText("비밀번호를 입력받지 못했습니다.");
                //ID를 입력받지 못한 경우 에러메시지 출력
            return;
        }
        lblPWerror.setText("비밀번호는 10자 이상 20자 이하 입력입니다.");
        //입력한 비밀번호 길이가 10자 미만일 시 에러메시지 출력
        return;
        }
        if(!isLowNum(txtNPW.getText())){
        //입력받은 비밀번호가 숫자 또는 소문자가 포함되었는지 확인하기 위에서 생성한 isLowNum메서드 실행.
        //만일 숫자 또는 소문자가 포함되지 않을 경우 에러메시지를 출력 후 리턴.
            lblPWerror.setText("비밀번호는 소문자와 숫자 조합입력이 필수입니다.");
            return;
        }
        if(!txtNPW.getText().equals(txtNPWcheck.getText())){
        //비밀번호와 비밀번호 확인이 불일치 할 경우 비밀번호 확인 text를 비워주고 에러메시지 출력 후 리턴.
            txtNPWcheck.setText("");
            lblPWerror.setText("입력받은 비밀번호가 불일치합니다");
            return;
        }
        strSQL="Update Client set Client.PASSWORD = '"+txtNPW.getText()+"' where Client.ID = '"+user+"'";
        //PW찾기에서 검색한 ID에 해당하는 튜플(행)의 비밀번호를 수정한 비밀번호로 수정하는 쿼리문.
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_stmt.executeUpdate(strSQL);
            //PW찾기에서 검색한 ID에 해당하는 튜플(행)의 비밀번호를 수정한 비밀번호로 수정하는 쿼리문 실행.
            DBM.dbClose();  //데이터베이스 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        jFrame4.setLocation(750,250);  //비밀번호 수정 완료 화면. 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jFrame4.setSize(290,200);  //비밀번호 수정 완료 화면 크기 지정.
        jFrame4.show();  //비밀번호 수정 완료 화면 보여주기.
        txtNPW.setText("");  //수정완료 후 비밀번호를 입력한 텍스트창 비우기.
        txtNPWcheck.setText("");  //수정완료 후 비밀번호 확인을 입력한 텍스트창 비우기.
        lblPWerror.setText("");  //비밀번호 수정이 완료되어 에러메시지 출력을 비워준다.
        user="";  //사용이 완료된 전역변수 user의 내용은 비워준다.
        jDialog2.dispose();  //Model 비밀번호 수정화면 닫기.(비밀번호 수정 중 다른 작업을 하지 못하도록 jDialog로 생성 후 modal체크)
    }//GEN-LAST:event_btnPWChangeActionPerformed

    private void btnPWChangeBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPWChangeBackActionPerformed
        jDialog2.dispose();  //Model 비밀번호 수정화면 닫기.(비밀번호 수정 중 다른 작업을 하지 못하도록 jDialog로 생성 후 modal체크)
    }//GEN-LAST:event_btnPWChangeBackActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jFrame4.dispose();  //비밀번호 수정 완료화면 닫기.
    }//GEN-LAST:event_jButton5ActionPerformed

    private void lblUPUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUPUserMouseClicked
        jDialog3.setLocation(350,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jDialog3.setSize(460,580);  //Model 대화상자 크기 지정.(회원정보 수정 시 다른 작업을 막기 위하여 modal설정)
        jDialog3.show();  //회원 정보 수정 화면 보여주기
    }//GEN-LAST:event_lblUPUserMouseClicked

    private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ActionPerformed

    private void CpuTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CpuTableMouseClicked
        int Row=0;  //CpuTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=CpuTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[0]=CpuTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_CpuTableMouseClicked

    private void MainBoardTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainBoardTableMouseClicked
        int Row=0;  //MainBoardTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=MainBoardTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[1]=MainBoardTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_MainBoardTableMouseClicked

    private void MemoryTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MemoryTableMouseClicked
        int Row=0;  //MemoryTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=MemoryTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[2]=MemoryTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_MemoryTableMouseClicked

    private void GraphicTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GraphicTableMouseClicked
        int Row=0;  //GraphicTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=GraphicTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[3]=GraphicTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_GraphicTableMouseClicked

    private void SSDTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SSDTableMouseClicked
        int Row=0;  //SSDTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=SSDTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[4]=SSDTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_SSDTableMouseClicked

    private void HardDiskTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HardDiskTableMouseClicked
        int Row=0;  //HardDiskTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=HardDiskTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[5]=HardDiskTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_HardDiskTableMouseClicked

    private void CaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CaseTableMouseClicked
        int Row=0;  //CaseTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=CaseTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[6]=CaseTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_CaseTableMouseClicked

    private void PowerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PowerTableMouseClicked
        int Row=0;  //PowerTable에서 선택한 행의 인덱스를 저장하기 위한 변수.
        Row=PowerTable.getSelectedRow();  //사용자가 선택한 행의 인덱스를 전달.
        order[7]=PowerTable.getValueAt(Row,0).toString();  //선택한 행의 1번째 열의 값(상품코드)를 상품 주문란(문자열 배열)에 전달.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_PowerTableMouseClicked

    private void btnProdAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdAddActionPerformed
        jFrame6.setLocation(750,290);  //상품추가 화면. 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jFrame6.setSize(350,330);  //상품 추가 화면 크기 지정.
        jFrame6.show();  //상품 추가 화면 보여주기.
    }//GEN-LAST:event_btnProdAddActionPerformed

    private void addProdTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_addProdTypeItemStateChanged
        String str=evt.getItem().toString();  //콤보박스의 아이템이 변경시마다 호출되는 이벤트로써 변경된 값의 object를 문자열로 변환 후 지역변수 문자열 변수 str에 저장.
        switch(str) {  //변경된 해당아이템의 문자열과 동일한 경우 해당 상품의 상품코드 앞자리를 보여줌.
            case "CPU": lblAddType.setText("1");  //cpu를 선택한 경우 상품코드의 앞자리는 1이기 때문에 1출력.
            break;
            case "Main Board": lblAddType.setText("2");  //Main Board를 선택한 경우 상품코드의 앞자리는 2이기 때문에 2출력.
            break;
            case "Memory": lblAddType.setText("3");  //Memory를 선택한 경우 상품코드의 앞자리는 3이기 때문에 3출력.
            break;
            case "Graphic Card": lblAddType.setText("4");  //Graphic Card를 선택한 경우 상품코드의 앞자리는 4이기 때문에 4출력.
            break;
            case "SSD": lblAddType.setText("5");  //SSD를 선택한 경우 상품코드의 앞자리는 5이기 때문에 5출력.
            break;
            case "HardDisk": lblAddType.setText("6");  //HardDisk를 선택한 경우 상품코드의 앞자리는 6이기 때문에 6출력.
            break;
            case "Case": lblAddType.setText("7");  //Case를 선택한 경우 상품코드의 앞자리는 7이기 때문에 7출력.
            break;
            case "Power": lblAddType.setText("8");  //Power를 선택한 경우 상품코드의 앞자리는 8이기 때문에 8출력.
            break;
        }        
    }//GEN-LAST:event_addProdTypeItemStateChanged

    private void btnAddProdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdBackActionPerformed
        txtAddType.setText("");  //상품 추가화면에서 취소버튼 클릭시 상품코드 입력란 비우기
        txtAddName.setText("");  //상품 추가화면에서 취소버튼 클릭시 상품이름 입력란 비우기
        txtAddPrice.setText("");  //상품 추가화면에서 취소버튼 클릭시 상품가격 입력란 비우기
        lblAddProdErr.setText("");  //상품 추가화면에서 취소버튼 클릭시 에러메시지 비우기.
        jFrame6.dispose();  //상품 추가 화면 닫기.
    }//GEN-LAST:event_btnAddProdBackActionPerformed

    private void btnAddProdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdOKActionPerformed
        if(!isNum(txtAddType.getText())) {  //상품코드가 숫자(정수형)로만 입력되었는지 검사.
            lblAddProdErr.setText("상품코드는 숫자로만 입력가능합니다.");
            return;
        }
        if(txtAddName.getText().length()==0){  //상품명을 입력받았는지 검사
            lblAddProdErr.setText("상품명을 입력받지 못했습니다.");
            return;
        }
        if(!isNum(txtAddPrice.getText())){   //가격이 숫자(정수형)로만 입력되었는지 검사.
            lblAddProdErr.setText("상품가격은 숫자로만 입력가능합니다.");
            return;
        }
        String SQL="Select P_CODE From Product";
        //상품이 중복되는지 검사하기 위해 상품코드를 입력한 상품코드를 검색하는 sql문 작성.
        String pcode=lblAddType.getText()+txtAddType.getText();
        //해당 기종의 앞자리를 표시한 라벨과 사용자가 입력한 상품코드를 합쳐서 하나의 문자열로 저장
        //사용자가 입력한 상품코드를 저장한 문자열 변수.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_rs=DBM.DB_stmt.executeQuery(SQL);  //select 문을 통해 결과를 전달 받기 때문에 executeQuery사용.
            while(DBM.DB_rs.next()){
            //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
            //상품이 존재할 경우 반복문 수행.
                if(pcode.equals(DBM.DB_rs.getString("P_CODE"))){
                //데이터베이스 각 행의 P_CODE필드 값을 읽어온 뒤 비교했을 경우 입력받은 상품코드와 동일한지 비교.
                    lblAddProdErr.setText("중복된 상품코드입니다!");  //비교하여 동일한 경우 에러메시지 전달.
                    txtAddType.setText("");  //중복확인이 된 상품코드는 비워주기.
                    DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
                    DBM.dbClose();  //데이터베이스 연결해제
                    return;  //중복된 상품코드를 발견하였다면 리턴.
                }
            }
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        SQL="Insert Into Product Values (";
        //상품 추가 기능을 통해 Product테이블에 Insert문을 수행하여 튜플(새로운 데이터)을 추가하기 위한 sql문을 작성한다.
        SQL+="'"+pcode+"', ";  //해당 기종의 상품코드 앞자리와 입력한 상품코드 전달.
        SQL+="'"+addProdType.getSelectedItem().toString()+"', ";  //입력받은 해당 상품기종을 전달.
        SQL+="'"+txtAddName.getText()+"', ";  //입력받은 해당 상품이름을 전달.
        SQL+="'"+txtAddPrice.getText()+"')";  //입력받은 상품 가격을 전달.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_stmt.executeUpdate(SQL);
            //결과를 안 받아도될 떄는 executeUpdate, 즉 select문을 사용하지 않을 때 사용.
            String str=addProdType.getSelectedItem().toString();  //콤보박스의 선택된 아이템은 해당 상품정보를 갱신.
            switch(str) {  //변경된 해당아이템의 문자열과 동일한 경우 해당 상품의 상품코드 앞자리를 보여줌.
                case "CPU": getDBData(CpuTable,str);  //선택된 기종이 CPU인경우 CPU테이블 갱신.
                break;
                case "Main Board": getDBData(MainBoardTable,str);  //선택된 기종이 Main Board인경우 MainBoard테이블 갱신.
                break;
                case "Memory": getDBData(MemoryTable,str);  //선택된 기종이 Memory인경우 Memory테이블 갱신.
                break;
                case "Graphic Card": getDBData(GraphicTable,str);  //선택된 기종이 Graphic인경우 Graphic테이블 갱신.
                break;
                case "SSD": getDBData(SSDTable,str);  //선택된 기종이 SSD인경우 SSD테이블 갱신.
                break;
                case "HardDisk": getDBData(HardDiskTable,str);  //선택된 기종이 HardDisk인경우 HardDisk테이블 갱신.
                break;
                case "Case": getDBData(CaseTable,str);  //선택된 기종이 Case인경우 Case테이블 갱신.
                break;
                case "Power": getDBData(PowerTable,str);  //선택된 기종이 Power인경우 Power테이블 갱신.
                break;
            } 
            DBM.dbClose();  //데이터베이스 연결 해제
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        lblAddProdErr.setText("추가완료!");  //성공적으로 추가시 완료 메시지 출력.
    }//GEN-LAST:event_btnAddProdOKActionPerformed

    private void btnProdDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdDelActionPerformed
        int iTabindex=0;  //jTabbedPane1 index 저장.
        iTabindex=jTabbedPane1.getSelectedIndex();  //jTabbedPane1 index 반환.
        String pcode="";  //화면의 테이블에서 해당 상품코드를 클릭시 상품코드를 전달받아 쿼리문의 where(조건문)절에 붙는 문자열 변수.
        //ArrayList<product> data= new ArrayList<>();
        int iCntRow=0;  //화면의 테이블에서 선택한 행의 번호.
        String SQL="Delete From Product where P_CODE = '";  //Product테이블에서 해당하는 상품코드의 행을 지우기 위한 쿼리문(초반문).
        try{
            DBM.dbOpen();  //데이터베이스 접속
            switch(iTabindex) {
                case 0:  //"CPU"
                    iCntRow=CpuTable.getSelectedRow();
                    pcode=CpuTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel CPUmodel = (DefaultTableModel)CpuTable.getModel();
                    CPUmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    CPUmodel.setNumRows(10);  //테이블의 행을 10개 추가.        
                    getDBData(CpuTable,"CPU");  //선택된 기종이 CPU인경우 CPU테이블 갱신.
                break;
                case 1:  //"Main Board"
                    iCntRow=MainBoardTable.getSelectedRow();
                    pcode=MainBoardTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel MainBoardmodel = (DefaultTableModel)MainBoardTable.getModel();
                    MainBoardmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    MainBoardmodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(MainBoardTable,"Main Board");  //선택된 기종이 Main Board인경우 MainBoard테이블 갱신.
                break;
                case 2:  //"Memory"
                    iCntRow=MemoryTable.getSelectedRow();
                    pcode=MemoryTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel Memorymodel = (DefaultTableModel)MemoryTable.getModel();
                    Memorymodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    Memorymodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(MemoryTable,"Memory");  //선택된 기종이 Memory인경우 Memory테이블 갱신.
                break;
                case 3:  //"Graphic Card"
                    iCntRow=GraphicTable.getSelectedRow();
                    pcode=GraphicTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel Graphicmodel = (DefaultTableModel)GraphicTable.getModel();
                    Graphicmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    Graphicmodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(GraphicTable,"Graphic Card");  //선택된 기종이 Graphic Card인경우 Graphic테이블 갱신.
                break;
                case 4:  //"SSD"
                    iCntRow=SSDTable.getSelectedRow();
                    pcode=SSDTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel SSDmodel = (DefaultTableModel)SSDTable.getModel();
                    SSDmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    SSDmodel.setNumRows(10); //테이블의 행을 10개 추가.
                    getDBData(SSDTable,"SSD");  //선택된 기종이 SSD인경우 SSD테이블 갱신.
                break;
                case 5:  //"HardDisk"
                    iCntRow=HardDiskTable.getSelectedRow();
                    pcode=HardDiskTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel HardDiskmodel = (DefaultTableModel)HardDiskTable.getModel();
                    HardDiskmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    HardDiskmodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(HardDiskTable,"HardDisk");  //선택된 기종이 HardDisk인경우 HardDisk테이블 갱신.
                break;
                case 6:  //"Case"
                    iCntRow=CaseTable.getSelectedRow();
                    pcode=CaseTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel Casemodel = (DefaultTableModel)CaseTable.getModel();
                    Casemodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    Casemodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(CaseTable,"Case");  //선택된 기종이 Case인경우 Case테이블 갱신.
                break;
                case 7:  //"Power"
                    iCntRow=PowerTable.getSelectedRow();
                    pcode=PowerTable.getValueAt(iCntRow,0).toString();
                    SQL+=pcode+"'";
                    DBM.DB_stmt.executeUpdate(SQL);
                    DefaultTableModel Powermodel = (DefaultTableModel)PowerTable.getModel();
                    Powermodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
                    Powermodel.setNumRows(10);  //테이블의 행을 10개 추가.
                    getDBData(PowerTable,"Power");  //선택된 기종이 Power인경우 Power테이블 갱신.
                break;
            }
            DBM.dbClose();
        }catch(Exception e) {
            System.out.println("SQLException : "+e.getMessage());
        }
    }//GEN-LAST:event_btnProdDelActionPerformed

    private void btnUpUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpUserActionPerformed
        String date;  //생년월일 중 입력받은 년도를 담는 문자열 변수.
        String gender = null;  //입력받은 성별을 저장한 문자열 변수.
        String email;  //입력받은 이메일이 유효한지 판단하기 위한 문자열 변수.
        int hobby=0;  //관심 분야 속성에 따른 bit연산을 담는 정수형 변수.
        if(txtUpPW.getText().length()<10 || txtUpPW.getText().length()>20){
        //PW가 10자 미만 20자 초과 입력 시 조건문 에러메세지 출력 후 리턴.
            if(txtUpPW.getText().length()==0){
            //PW 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
                lblUpCheck.setText("비밀번호를 입력받지 못했습니다.");
                //ID를 입력받지 못한 경우 에러메시지 출력
                return;
            }
            lblUpCheck.setText("비밀번호는 10자 이상 20자 이하 입력입니다.");
            //입력한 비밀번호 길이가 10자 미만일 시 에러메시지 출력
            return;
        }
        if(!isLowNum(txtUpPW.getText())){
        //입력받은 비밀번호가 숫자 또는 소문자가 포함되었는지 확인하기 위에서 생성한 isLowNum메서드 실행.
        //만일 숫자 또는 소문자가 포함되지 않을 경우 에러메시지를 출력 후 리턴.
            lblUpCheck.setText("비밀번호는 소문자와 숫자 조합입력이 필수입니다.");
            return;
        }
        if(!txtUpPW.getText().equals(txtUpCheck.getText())){
        //비밀번호와 비밀번호 확인이 불일치 할 경우 비밀번호 확인 text를 비워주고 에러메시지 출력 후 리턴.
            txtUpCheck.setText("");
            lblUpCheck.setText("입력받은 비밀번호가 불일치합니다");
            return;
        }
        if(!isNumeric(txtUpYear.getText())) {  //생년월일 중 년도의 입력임으로 정수가 아닌 그외의 입력시 에러 메세지 출력 후 리턴.
            lblUpCheck.setText("년도는 정수의 입력만 가능합니다.");return;
        }
        if(txtUpYear.getText().length()!=4){ 
        //데이터베이스에 들어갈 때 포맷형식에 맞추어 입력한 년도는 네자리가 되도록 입력받는다.
        //아닐시 에러메세지 출력 후 리턴.
            lblUpCheck.setText("년도는 네자리로 입력해 주십시요. ex)1996 ");return;
        }
        date=txtUpYear.getText()+comUpMonth.getSelectedItem().toString()+comUpDay.getSelectedItem().toString();
        //사용자가 선택한 회원가입에서 입력한 생년월일로써 유효한 데이터인지 판단하기 위해 현재날짜와 비교하여 그보다 이전인지 확인하기 위한 문자열 date생성.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  //값을 비교하기 위해 위의 선언한 date의 형식과 같게 맞추기 위해 포맷형식(yyyymmdd)지정.
        Calendar cld = Calendar.getInstance();  //현재 날짜와 시간정보를 가진 Calender객체 cld생성.
        String strToday = sdf.format(cld.getTime());  //현재날짜정보(cld)를 위에서 지정한 포맷형식(yyyymmdd)으로 문자열 strToday생성.
        if(Integer.parseInt(date)>Integer.parseInt(strToday)){  //입력한 값이 현재보다 미래인 경우인지 확인.
            lblUpCheck.setText("정확한 년도를 입력해주시기 바랍니다.");return; //현재 입력한 값이 미래인 경우 에러메세지 출력 후 리턴.
        }
        if(jRadioButton3.isSelected())  //성별구분 남자 클릭시
            gender="M";
        else if(jRadioButton4.isSelected())  //성별구분 여자 클릭시
            gender="F";
        email=txtUpEmail.getText();  //보인확인 이메일 입력을 받아서 아래의 유효한 이메일인제 체크.
        if ((email.length() < 4) || !email.contains("@") || !email.contains(".")) {
        //이메일은 4자 미만이거나 '@'가 포함되지 않거나 '.'이 포함되지 않은 경우 오류메시지를 출력 후 리턴하여 빠져나와준다.
            lblUpCheck.setText("유효한 이메일이 아닙니다");  //오류메세지 출력
            return;  //유효하지 않은 이메일 확인 후 리턴
        }
        if(jCheckBox7.isSelected()) hobby+=32;  //게임
        if(jCheckBox8.isSelected()) hobby+=16;  //영화 시청
        if(jCheckBox9.isSelected()) hobby+=8;  //프로그래밍
        if(jCheckBox10.isSelected()) hobby+=4;  //독서
        if(jCheckBox11.isSelected()) hobby+=2;  //강의 시청
        if(jCheckBox12.isSelected()) hobby+=1;  //동영상 편집
        
        String SQL="Update Client Set ";  //회원정보를 수정하기 위한 실행시킬 sql문의 update문 앞문장
        SQL+="PASSWORD = '"+txtUpPW.getText()+"', ";  //입력받은 비밀번호를 sql문에 추가.
        SQL+="BIRTH = '"+date+"', ";  //입력받은 생년월일을 sql문에 추가.
        SQL+="EMAIL = '"+email+"', ";  //입력받은 이메일을 sql문에 추가.
        SQL+="GENDER = '"+gender+"', ";  //입력받은 성별을 sql문에 추가.
        SQL+="HOBBY = '"+Integer.toString(hobby)+"', ";  //입력받은 취미를 sql문에 추가.
        SQL+="JOB = '"+comUpJob.getSelectedItem().toString()+"' ";  //입력받은 직업을 sql문에 추가.
        SQL+="Where Client.ID = '"+lblUpID.getText()+"'";  //해당 아이디에 해당하는 계정의 정보를 수정.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_stmt.executeUpdate(SQL);
            //결과를 안 받아도될 떄는 executeUpdate, 즉 select문을 사용하지 않을 때 사용.
            DBM.dbClose();  //데이터베이스 연결 해제
            lblUpCheck.setText("수정 성공!.");
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        
        txtUpPW.setText("");txtUpCheck.setText("");
        //파일에 정보 전달 후 화면 비우기
    }//GEN-LAST:event_btnUpUserActionPerformed

    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox11ActionPerformed

    private void btnUpBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpBackActionPerformed
        jDialog3.dispose();  //Model 대화상자 닫기.(회원정보 수정 도중 다른 작업을 하지 못하도록 jDialog로 생성 후 modal체크)
    }//GEN-LAST:event_btnUpBackActionPerformed

    private void lblLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogOutMouseClicked
        loginuser="";  //로그아웃 클릭시 로그인했던 회원정보(아이디)는 코드 내에서 비워주어 정보를 비워줌.
        usertype="";  //로그아웃 클릭시 로그인했던 회원정보(회원 타입)는 코드 내에서 비워주어 정보를 비워줌.
        jFrame1.dispose();  //로그아웃 시 모두 초기화 시키기위해 회원가입 완료창이 열려있다면 닫기.
        jFrame2.dispose();  //로그아웃 시 모두 초기화 시키기위해 ID/PW찾기창이 열려있다면 닫기.
        jFrame3.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인 성공창이 열려있다면 닫기.
        jFrame4.dispose();  //로그아웃 시 모두 초기화 시키기위해 비밀번호 수정완료창이 열려있다면 닫기.
        jFrame5.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인창으로 돌아가고 상품정보 확인 및 주문탭은 닫기.
        jFrame6.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품추가창이 열려있다면 닫기.
        jFrame7.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품주문 완료창이 열려있다면 닫기.
        jFrame8.dispose();  //로그아웃 시 로그인창으로 돌아가고 고객 주문목록창이 열려있다면 닫기.
        this.show();  //로그아웃하여 다시 로그인 창 뛰우기.
    }//GEN-LAST:event_lblLogOutMouseClicked

    private void btnProOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProOrderActionPerformed
        OrderTable.getColumnModel().getColumn(2).setMaxWidth(400);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최대 너비 지정.
        OrderTable.getColumnModel().getColumn(2).setMinWidth(400);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 최소 너비 지정.
        OrderTable.getColumnModel().getColumn(2).setWidth(400);  //해당 테이블의 상품명 컬럼 길이를 넓혀 상품명을 모두 보이게 하기위해 상품명컬럼의 속성 너비 지정.
        DefaultTableModel Ordermodel = (DefaultTableModel)OrderTable.getModel();
        //OrderTable(상품 주문 목록)의 새로운 내용을 담을 DefaultTableModel 타입의 Ordermodel객체 생성.
        Ordermodel.setNumRows(0);  //생성한 Ordermodel의 내용을 비우기 위해 행을 초기화.
        Ordermodel.setNumRows(8);  //새로운 데이터를 입력하기 위해 행을 8개 생성.
        Date today = new Date();  //현재 날짜와 시간을 지정한 today객체 생성.
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss a");  //시간정보를 보여주는 포맷형식 설정(ex:2018/11/28 7:36:02 PM)
        lblDate.setText(date.format(today));  //화면에 현재날짜의 위의 포맷형식을 적용하여 출력.
        String SQL="Select * from Product";  //상품정보를 화면에 보여주기 위한 select 쿼리문으로써 해당 상품 기종의 상품을 출력하기 위한 쿼리 시작 부분.
        int iCntRow=0;  //테이블의 행을 증가시키며 상품을 추가할 정수형 변수
        int totprice=0;  //주문할 상품들의 총합을 담는 정수형 변수
        for(int i=0;i<order.length;i++){  //order 배열에는 상품들의 상품코드들을 담고 있기때문에 반복문을 통해 sql 조건문에 각 상품코드의 데이터를 조회.
            String strOutput=SQL+" Where Product.P_CODE= '"+order[i].toString()+"'";  //같은 상품 기종을 검색하여 테이블에 결과값 전달하기 위한 where 조건문.
            try{
                DBM.dbOpen();  //데이터베이스 접속
                DBM.DB_rs=DBM.DB_stmt.executeQuery(strOutput);  //executeQuery입력받은 쿼리실행
                while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                    OrderTable.setValueAt(DBM.DB_rs.getString("P_CODE"),iCntRow,0);  //테이블의 각 행의 0번째 인덱스 열에 상품코드를 전달.
                    OrderTable.setValueAt(DBM.DB_rs.getString("P_TYPE"),iCntRow,1);  //테이블의 각 행의 1번째 인덱스 열에 상품 기종을 전달.
                    OrderTable.setValueAt(DBM.DB_rs.getString("P_NAME"),iCntRow,2);  //테이블의 각 행의 2번째 인덱스 열에 상품명을 전달.
                    OrderTable.setValueAt(DBM.DB_rs.getString("P_PRICE"),iCntRow,3);  //테이블의 각 행의 3번째 인덱스 열에 상품가격을 전달.
                    totprice+=Integer.parseInt(DBM.DB_rs.getString("P_PRICE"));  //상품들의 총합을 구하기 위해 각 상품의 상품가격을 읽어올때마다 totprice에 더하기.
                    iCntRow++;
                    //다음 행으로 이동하여 상품정보를 받아와야하기 때문에 +1 증가.
                    //만일 이 문장이 없을 시 계속 같은 행에 중첩해서 작성하기 때문에 필요.
                }
                DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
            }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
                System.out.println("SQLException : "+e.getMessage());
            }
        }
        lblTotalPrice.setText(Integer.toString(totprice));  //주문할 상품들의 총액을 화면에 보여주기.
        lblOrdID.setText(loginuser);  //로그인한 사용자의 ID정보 출력.
        jDialog4.setLocation(450,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jDialog4.setSize(760,470);  //Model 대화상자 크기 지정.(상품 주문시 다른화면의 작업을 막기위해서 modal 설정.)
        jDialog4.show();  //상품 주문창 보여주기.
    }//GEN-LAST:event_btnProOrderActionPerformed

    private void btnOrdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdBackActionPerformed
        jDialog4.dispose();  //Model 상품 주문 화면 닫기.(상품 주문(결제) 중 다른 작업을 하지 못하도록 jDialog로 생성 후 modal체크)
    }//GEN-LAST:event_btnOrdBackActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        if(txtAddress.getText().length()==0){
        //ID 입력 중 입력을 받지 못했을 시 에러메세지 출력 후 리턴.
            lblOrdcheck.setText("주소를 입력받지 못했습니다.");
            //ID를 입력받지 못한 에러메시지 출력
            return;
        }
        if(lstPayment.getSelectedValue()==null){
        //결제수단을 선택하지 않았을시 에러메시지 출력.
            lblOrdcheck.setText("결제수단을 선택하여 주십시요.");
            return;
        }
        int ordno=0;  //주문 내역(Listorder)테이블에 전달할 주문번호를 담는 정수형 변수.
        String SQL="Select nvl(max(NO),0) from Listorder";
        //상품주문시 주문번호는 테이블에 데이터가 없을 시 sql문의 nvl함수를 통해 전달 값이 null값일 떄 첫 주문이므로 0을 전달.
        //상품테이블에 데이터가 존재할시 상품번호 중 가장 큰 번호를 전달 받아 상품추가시 1을 더하여 상품 주문 테이블에 데이터 추가.
        try{
            DBM.dbOpen();  //데이터베이스 접속
            DBM.DB_rs=DBM.DB_stmt.executeQuery(SQL);  //executeQuery입력받은 쿼리실행
            while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                ordno=Integer.parseInt(DBM.DB_rs.getString("nvl(max(NO),0)"));  //테이블의 각 행의 0번째 인덱스 열에 상품코드를 전달.
            }
            DBM.DB_rs.close();  //연결된 resultSet객체의 데이터베이스와 jdbc연결 해제
        }catch(Exception e){  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        SQL="Insert Into Listorder Values (";  //상품 주문(Listorder)테이블에 데이터를 삽입(Insert)하는 sql문 앞 문장.
        SQL+=Integer.toString(ordno+1)+", ";  //상품주문(Lisorder)테이블에 상품번호 중 큰 수를 조사하여 1을 더한 값을 해당 주문 데이터의 주문번호(Listorder.No)에 저장.
        SQL+="'"+loginuser+"', ";  //상품을 주문하는 현재 로그인 한 사용자의 ID를 상품 주문(Listorder)테이블에 전달
        SQL+="'"+txtAddress.getText()+"', ";  //사용자가 입력한 주소를 상품 주문(Listorder)테이블에 전달
        SQL+="'"+lstPayment.getSelectedValue()+"', ";  //사용자가 리스트에서 선택한 결제방법을 상품 주문(Listorder)테이블에 전달
        for(int i=0;i<order.length;i++){
        //사용자가 삼품을 선택한 정보를 가진 order테이블을 상품 주문(Listorder)테이블의 각 상품 기종에 전달하기 위해 반복문 실핼.
            if(order[i].equals("null")){
            //상품을 선택하지 않아서 문자열배열(order)에 문자열 "null"이 저장되어있는 경우,
            //데이터를 전달받는 Listorder테이블에 null값을 전달하기 위해 문자열을 표시하는 작은따움표('')를 제외하고 null만 전달한다.
                SQL+=order[i]+", ";  //order문자열 배열 중 null인 경우 작은 따움표('')를 제거 후 sql문에 전달.
            }
            else SQL+="'"+order[i]+"', ";
            //상품을 선택하여 상품의 상품코드가 저장되어있다면
            //문자열로 데이터를 입력하기 위해 작은 때움표('')포함하여 sql문에 작성.
        }
        SQL+="'"+lblTotalPrice.getText()+"', ";  //결제금액을 계산한 lblTotalPrice 문자열을 sql문에 전달.
        SQL+="'"+lblDate.getText()+"')";  //결제한 날짜를 sql문에 전달.
        try{  //예외처리문을 통해 발생할 수 있는 예외상황을 처리
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_stmt.executeUpdate(SQL);
            //결과를 안 받아도될 떄는 executeUpdate, 즉 select문을 사용하지 않을 때 사용.
            DBM.dbClose();  //데이터베이스 연결 해제
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        for(int i=0; i<order.length; i++){  //로그인 시 서비스화면에서 주문정보를 초기화.
            order[i]="null";  //각 상품인덱스를 null로 초기화
        }
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품을 포함한 전체 주문내역을 초기화.
        txtAddress.setText("");  //주문이 완료된 후 화면에 입력한 주문정보(주소 정보)를 모두 비우기.
        lblOrdcheck.setText("");  //상품 주문을 완료하고 에러메시지 라벨을 비우기.(다음 주문시 에러메시지가 남아있기때문)
        jFrame7.setLocation(440,140);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 상품 주문완료 화면 표시.
        jFrame7.setSize(330,210);  //상품 주문 완료 화면 크기 지정.
        jFrame7.show();  //상품 주문 완료 화면 보여주기.
        jDialog4.dispose();  //상품주문이 완료 후 상품주문 화면은 닫기.
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnOrderOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderOKActionPerformed
        jFrame7.dispose();  //상품주문을 완료 후 상품완료창(jFrame7)이 뛰워졌을 시 '확인'을 눌러 나가기.
    }//GEN-LAST:event_btnOrderOKActionPerformed

    private void btnProdCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdCancleActionPerformed
        int iTabindex=0;  //jTabbedPane1(상품 기종) index 저장.
        iTabindex=jTabbedPane1.getSelectedIndex();  //선택한 상품 기종 index 반환.
        order[iTabindex]="null";
        //jTabbedPane1(상품 기종) 인덱스의 상품기종 순서와 order배열 인덱스의 상품기종 순서가 같기 때문에
        //jTabbedPane1(상품 기종)의 선택한 인덱스를 order배열의 인덱스에 대입하여 해당 인덱스의 상품을 취소할 시
        //order배열의 해당 인덱스에 "null" 저장.
        txtOrder.setText(Arrays.toString(order));  //입력받은 상품코드명을 포함한 전체 주문내역을 화면 표시.
    }//GEN-LAST:event_btnProdCancleActionPerformed

    private void btnOrdListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdListActionPerformed
        DefaultTableModel MainBoardmodel = (DefaultTableModel)OrderListTable.getModel();
        MainBoardmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
        MainBoardmodel.setNumRows(15);  //테이블의 행을 10개 추가.       
        getDBOrderData(OrderListTable,loginuser);  //해당 로그인한 사용자의 주문목록을 표시.
        //첫번째 매개변수는 주문목록을 보여주는 테이블(OrderListTable), 두번쨰매개변수는 현재 로그인한 유저의 id전달.
        btnFindUser.setVisible(false);  //고객은 전체 사용자의 주목목록에서 검색할 수 없어야 함으로 검색기능 비활성화.
        txtFindUser.setVisible(false);  //고객은 전체 사용자의 주목목록에서 검색할 수 없어야 함으로 검색기능 비활성화.
        lblFindUser.setVisible(false);  //고객은 전체 사용자의 주목목록에서 검색할 수 없어야 함으로 검색기능 비활성화.
        btnAllUser.setVisible(false);  //관리자는 모든고객을 보면서 특정 고객을 검색할 있도록 검색 기능 활성화.
        jFrame8.setLocation(340,240);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 상품 주문완료 화면 표시.
        jFrame8.setSize(930,380);  //상품 주문 완료 화면 크기 지정.
        jFrame8.show();  //상품 주문 완료 화면 보여주기.
    }//GEN-LAST:event_btnOrdListActionPerformed

    private void btnDelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelOrderActionPerformed
        lblDelCpu.setText("");lblDelMain.setText("");lblDelMem.setText("");lblDelGraphic.setText("");
        //주문 취소화면 진입시 이전 기록이 남았있을 수 있으므로  cpu, mainboard, memory, graphic card 비우기.
        lblDelSsd.setText("");lblDelHard.setText("");lblDelCase.setText("");lblDelPower.setText("");
        //주문 취소화면 진입시 이전 기록이 남았있을 수 있으므로  ssd, hard disk, case, power 비우기.
        String SQL="Select * From ListOrder where NO = '";
        //Product테이블에서 해당하는 상품코드의 행을 지우기 위해 주문을 취소하기 전,
        //선택한 주문내역을 확인시켜주기 위해 주문내역을 확인하는 화면에 뛰워줄 주문내역 검색하는 쿼리문.
        String listno="";  //주문 취소할 주문번호를 전달 받을 문자열 변수
        int iCntRow=0;  //화면의 테이블에서 선택한 행의 번호.
        iCntRow=OrderListTable.getSelectedRow();  //주문내역(OrderListTable)테이블에서 선택한 행의 번호 전달.
        if(iCntRow==-1){  //테이블에서 취소할 주문이 선택되지 않은 경우 에러메세지 출력 후 리턴.
            lblDelOrdCheck.setText("취소할 주문을 선택 후 '상세 내역'버튼을 눌러주십시오.");
            return;
        }else lblDelOrdCheck.setText("상세 내역에서 주문을 취소하실 수 있습니다.");  //유효한 주문목록일 경우 에러메세지 비우기.
        listno=OrderListTable.getValueAt(iCntRow,0).toString();  //선택한 행의 0번째 인덱스 즉, 검색할 주문번호 전달.
        delorder=OrderListTable.getValueAt(iCntRow,0).toString();  //주문내역을 취소하기 위해 취소할 주문번호를 전역변수delorder에 전달.
        SQL+=listno+"'";
        try{
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_rs=DBM.DB_stmt.executeQuery(SQL);  //executeQuery입력받은 쿼리실행
                while(DBM.DB_rs.next()){  //튜플(행)을 하나씩 읽으며, 다음 읽을 값이 없을 시 false를 통해 반복문 마침.
                lblDelNo.setText(DBM.DB_rs.getString("NO"));  //데이터베이스에서 검색된 주문번호를 화면에 표시
                lblDelAddress.setText(DBM.DB_rs.getString("ADDRESS"));  //데이터베이스에서 검색된 주소를 화면에 표시
                lblDelPay.setText(DBM.DB_rs.getString("PAYMENT"));  //데이터베이스에서 검색된 결제방식를 화면에 표시
                lblDelCpu.setText(DBM.DB_rs.getString("CPU"));  //데이터베이스에서 검색된 CPU를 화면에 표시
                lblDelMain.setText(DBM.DB_rs.getString("MAINBOARD"));  //데이터베이스에서 검색된 Main board를 화면에 표시
                lblDelMem.setText(DBM.DB_rs.getString("MEMORY"));  //데이터베이스에서 검색된 Memory를 화면에 표시
                lblDelGraphic.setText(DBM.DB_rs.getString("GRAPHIC"));  //데이터베이스에서 검색된 Graphic Card를 화면에 표시
                lblDelSsd.setText(DBM.DB_rs.getString("SSD"));  //데이터베이스에서 검색된 SSD를 화면에 표시
                lblDelHard.setText(DBM.DB_rs.getString("HARDDISK"));  //데이터베이스에서 검색된 Hard Disk를 화면에 표시
                lblDelCase.setText(DBM.DB_rs.getString("CASE"));  //데이터베이스에서 검색된 Case를 화면에 표시
                lblDelPower.setText(DBM.DB_rs.getString("POWER"));  //데이터베이스에서 검색된 Power를 화면에 표시
                lblDelPrice.setText(DBM.DB_rs.getString("TOTALPRICE"));  //데이터베이스에서 검색된 주문 총액를 화면에 표시
                lblDelDate.setText(DBM.DB_rs.getString("ORDERDATE"));  //데이터베이스에서 검색된 주문 날짜를 화면에 표시
            }
            DBM.DB_rs.close();  //데이터베이스 연결 해제
            DBM.dbClose();  //데이터베이스 연결해제
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        jDialog5.setLocation(340,240);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 상품 주문완료 화면 표시.
        jDialog5.setSize(430,410);  //상품 취소 화면 크기 지정.
        jDialog5.show();  //주문 취소 화면 보여주기.(취소 도중 다른 동작을 막기위한 모달 설정)
    }//GEN-LAST:event_btnDelOrderActionPerformed

    private void btnDelCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelCheckActionPerformed
        String SQL="Delete From ListOrder where NO = '";  //Product테이블에서 해당하는 상품코드의 행을 지우기 위한 쿼리문(초반문).
        SQL+=delorder+"'";  //주문목록에서 선택한 주문내역의 주문번호 데이터를 가진 전역변수 delorder를 sql 쿼리문에 삽입.
        try{
            DBM.dbOpen();  //데이터베이스 접속.
            DBM.DB_stmt.executeUpdate(SQL);
            //결과를 안 받아도될 떄는 executeUpdate, 즉 select문을 사용하지 않을 때 사용.
            DefaultTableModel Ordermodel = (DefaultTableModel)OrderListTable.getModel();
            Ordermodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
            Ordermodel.setNumRows(10);  //테이블의 행을 10개 추가.
            getDBOrderData(OrderListTable,loginuser);  //해당 로그인한 사용자의 주문목록을 표시.
            DBM.dbClose();  //데이터베이스 연결 해제
        }catch(Exception e) {  //SQL문 실핼 중 발생할 수 있는 예외상황 처리. 콘솔에 에러메시지 전달.
            System.out.println("SQLException : "+e.getMessage());
        }
        jDialog5.dispose();  //취소 완료 후 해당 주문최소 화면 나가기.
    }//GEN-LAST:event_btnDelCheckActionPerformed

    private void ProdTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProdTypeItemStateChanged
        int iTabindex=0;  //jTabbedPane1 index 저장.
        iTabindex=jTabbedPane1.getSelectedIndex();  //jTabbedPane1 index 반환.
        String sort=ProdType.getSelectedItem().toString();  //선택한 정렬방식을 저장하는 문자열 변수
        String sorttype=null;  //테이블에 정렬 함수(TableSort)에 전달할 정렬타입
        if(sort.equals("가격높은순")) sorttype="high";  //가격높은순 정렬시 함수(TableSort)에 high전달.
        else sorttype="low";  //가격낮은순 정렬시 함수(TableSort)에 low전달.
        switch(iTabindex) {  //선택한 해당 탭의 테이블을 정렬.
                case 0:  //"CPU"
                    TableSort(CpuTable,sorttype);  //CPU 테이블 정렬
                break;
                case 1:  //"Main Board"
                    TableSort(MainBoardTable,sorttype);  //메인보드 테이블 정렬
                break;
                case 2:  //"Memory"
                    TableSort(MemoryTable,sorttype);  //메모리 테이블 정렬
                break;
                case 3:  //"Graphic Card"
                    TableSort(GraphicTable,sorttype);  //그래픽 카드 테이블 정렬
                break;
                case 4:  //"SSD"
                    TableSort(SSDTable,sorttype);  //SSD 테이블 정렬
                break;
                case 5:  //"HardDisk"
                    TableSort(HardDiskTable,sorttype);  //하드디스크 테이블 정렬
                break;
                case 6:  //"Case"
                    TableSort(CaseTable,sorttype);  //케이스 테이블 정렬
                break;
                case 7:  //"Power"
                    TableSort(PowerTable,sorttype);  //파워 테이블 정렬
                break;
            }
    }//GEN-LAST:event_ProdTypeItemStateChanged

    private void btnOrderBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderBackActionPerformed
        jFrame8.dispose();  //주문목록창 닫기.
    }//GEN-LAST:event_btnOrderBackActionPerformed

    private void btnDelBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelBackActionPerformed
        jDialog5.dispose();  //주문 내역 취소창 닫기.
    }//GEN-LAST:event_btnDelBackActionPerformed

    private void btnManagerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagerActionPerformed
        lblType.setText("Manager");  //회원가입 시 mamager로써 화면에 Manager임을 회원가입 화면에서 확인.
        jDialog6.dispose();  //사용자 유형 선택화면 닫기.
        jDialog1.setLocation(350,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jDialog1.setSize(550,600);  //Model 대화상자 크기 지정
        jDialog1.show();  //회원가입 창 보여주기
    }//GEN-LAST:event_btnManagerActionPerformed

    private void btnClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientActionPerformed
        lblType.setText("Client");  //회원가입 시 고객으로써 화면에 Client임을 회원가입 화면에서 확인.
        jDialog6.dispose();  //사용자 유형 선택화면 닫기.
        jDialog1.setLocation(350,350);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 화면 표시.
        jDialog1.setSize(550,600);  //Model 대화상자 크기 지정
        jDialog1.show();  //회원가입 창 보여주기
    }//GEN-LAST:event_btnClientActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jDialog6.dispose();  //사용자 유형 선택화면 닫기.
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnClientOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientOrderActionPerformed
        DefaultTableModel MainBoardmodel = (DefaultTableModel)OrderListTable.getModel();
        MainBoardmodel.setNumRows(0);  //테이블에 수정된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
        MainBoardmodel.setNumRows(15);  //테이블의 행을 10개 추가.  
        getDBOrderData(OrderListTable);  //관리자가 모든 사용자의 주문목록을 확인.
        txtFindUser.setText("");  //이전에 관리자가 사용자를 검색한 흔적이 있다면 비워주기.
        btnFindUser.setVisible(true);  //관리자는 모든고객을 보면서 특정 고객을 검색할 있도록 검색 기능 활성화.
        txtFindUser.setVisible(true);  //관리자는 모든고객을 보면서 특정 고객을 검색할 있도록 검색 기능 활성화.
        lblFindUser.setVisible(true);  //관리자는 모든고객을 보면서 특정 고객을 검색할 있도록 검색 기능 활성화.
        btnAllUser.setVisible(true);  //관리자는 모든고객을 보면서 특정 고객을 검색할 있도록 검색 기능 활성화.
        jFrame8.setLocation(340,240);  //화면 좌측 모서리에서 x 350, y350에서 시작하여 상품 주문완료 화면 표시.
        jFrame8.setSize(930,380);  //상품 주문 완료 화면 크기 지정.
        jFrame8.show();  //상품 주문 완료 화면 보여주기.
    }//GEN-LAST:event_btnClientOrderActionPerformed

    private void btnFindUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindUserActionPerformed
        DefaultTableModel Ordermodel = (DefaultTableModel)OrderListTable.getModel();
        Ordermodel.setNumRows(0);  //테이블에 검색된 데이터를 넣어주기 위해 기존 내용을 지운 뒤 테이블을 0행으로 비워주기
        Ordermodel.setNumRows(15);  //테이블의 행을 10개 추가.
        getDBOrderData(OrderListTable,txtFindUser.getText());  //관리자가 입력한 사용자 아이디를 매개변수로 데이터베이스에서 주문목록을 검색하여 테이블에 출력.
    }//GEN-LAST:event_btnFindUserActionPerformed

    private void btnAllUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllUserActionPerformed
        getDBOrderData(OrderListTable);  //통합 검색 버튼 클릭시 관리자가 모든 사용자의 주문목록을 확인.
    }//GEN-LAST:event_btnAllUserActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        loginuser="";  //로그아웃 클릭시 로그인했던 회원정보(아이디)는 코드 내에서 비워주어 정보를 비워줌.
        usertype="";  //로그아웃 클릭시 로그인했던 회원정보(회원 타입)는 코드 내에서 비워주어 정보를 비워줌.
        jFrame1.dispose();  //로그아웃 시 모두 초기화 시키기위해 회원가입 완료창이 열려있다면 닫기.
        jFrame2.dispose();  //로그아웃 시 모두 초기화 시키기위해 ID/PW찾기창이 열려있다면 닫기.
        jFrame3.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인 성공창이 열려있다면 닫기.
        jFrame4.dispose();  //로그아웃 시 모두 초기화 시키기위해 비밀번호 수정완료창이 열려있다면 닫기.
        jFrame5.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인창으로 돌아가고 상품정보 확인 및 주문탭은 닫기.
        jFrame6.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품추가창이 열려있다면 닫기.
        jFrame7.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품주문 완료창이 열려있다면 닫기.
        jFrame8.dispose();  //로그아웃 시 로그인창으로 돌아가고 고객 주문목록창이 열려있다면 닫기.
        this.show();  //로그아웃하여 다시 로그인 창 뛰우기.
    }//GEN-LAST:event_LogoutActionPerformed

    private void LogoutMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_LogoutMenuKeyPressed
        loginuser="";  //로그아웃 클릭시 로그인했던 회원정보(아이디)는 코드 내에서 비워주어 정보를 비워줌.
        usertype="";  //로그아웃 클릭시 로그인했던 회원정보(회원 타입)는 코드 내에서 비워주어 정보를 비워줌.
        jFrame1.dispose();  //로그아웃 시 모두 초기화 시키기위해 회원가입 완료창이 열려있다면 닫기.
        jFrame2.dispose();  //로그아웃 시 모두 초기화 시키기위해 ID/PW찾기창이 열려있다면 닫기.
        jFrame3.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인 성공창이 열려있다면 닫기.
        jFrame4.dispose();  //로그아웃 시 모두 초기화 시키기위해 비밀번호 수정완료창이 열려있다면 닫기.
        jFrame5.dispose();  //로그아웃 시 모두 초기화 시키기위해 로그인창으로 돌아가고 상품정보 확인 및 주문탭은 닫기.
        jFrame6.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품추가창이 열려있다면 닫기.
        jFrame7.dispose();  //로그아웃 시 로그인창으로 돌아가고 상품주문 완료창이 열려있다면 닫기.
        jFrame8.dispose();  //로그아웃 시 로그인창으로 돌아가고 고객 주문목록창이 열려있다면 닫기.
        this.show();  //로그아웃하여 다시 로그인 창 뛰우기.
    }//GEN-LAST:event_LogoutMenuKeyPressed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        System.exit(0);  //프로그램 종료
    }//GEN-LAST:event_ExitActionPerformed

    private void ExitMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_ExitMenuKeyPressed
        System.exit(0);  //프로그램 종료
    }//GEN-LAST:event_ExitMenuKeyPressed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable CaseTable;
    private javax.swing.JTable CpuTable;
    private javax.swing.JMenuItem Exit;
    private javax.swing.JTabbedPane FindClient;
    private javax.swing.JTable GraphicTable;
    private javax.swing.JTable HardDiskTable;
    private javax.swing.JTextField Login_ID;
    private javax.swing.JPasswordField Login_PW;
    private javax.swing.JMenuItem Logout;
    private javax.swing.JTable MainBoardTable;
    private javax.swing.JTable MemoryTable;
    private javax.swing.JTable OrderListTable;
    private javax.swing.JTable OrderTable;
    private javax.swing.JTable PowerTable;
    private javax.swing.JComboBox<String> ProdType;
    private javax.swing.JTable SSDTable;
    private javax.swing.JComboBox<String> addProdType;
    private javax.swing.JButton btnAddProdBack;
    private javax.swing.JButton btnAddProdOK;
    private javax.swing.JButton btnAllUser;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheck;
    private javax.swing.JButton btnClient;
    private javax.swing.JButton btnClientOrder;
    private javax.swing.JButton btnDelBack;
    private javax.swing.JButton btnDelCheck;
    private javax.swing.JButton btnDelOrder;
    private javax.swing.JButton btnFindBack;
    private javax.swing.JButton btnFindID;
    private javax.swing.JButton btnFindPW;
    private javax.swing.JButton btnFindUser;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnJoin_main;
    private javax.swing.JButton btnManager;
    private javax.swing.JButton btnOrdBack;
    private javax.swing.JButton btnOrdList;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnOrderBack;
    private javax.swing.JButton btnOrderOK;
    private javax.swing.JButton btnPWChange;
    private javax.swing.JButton btnPWChangeBack;
    private javax.swing.JButton btnProOrder;
    private javax.swing.JButton btnProdAdd;
    private javax.swing.JButton btnProdCancle;
    private javax.swing.JButton btnProdDel;
    private javax.swing.JButton btnUpBack;
    private javax.swing.JButton btnUpUser;
    private javax.swing.JButton btn_Success;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> comDay;
    private javax.swing.JComboBox<String> comJob;
    private javax.swing.JComboBox<String> comMonth;
    private javax.swing.JComboBox<String> comUpDay;
    private javax.swing.JComboBox<String> comUpJob;
    private javax.swing.JComboBox<String> comUpMonth;
    private javax.swing.JTextField id_FindEmail;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JDialog jDialog5;
    private javax.swing.JDialog jDialog6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JFrame jFrame6;
    private javax.swing.JFrame jFrame7;
    private javax.swing.JFrame jFrame8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblAddProdErr;
    private javax.swing.JLabel lblAddType;
    private javax.swing.JLabel lblCheck;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDelAddress;
    private javax.swing.JLabel lblDelCase;
    private javax.swing.JLabel lblDelCpu;
    private javax.swing.JLabel lblDelDate;
    private javax.swing.JLabel lblDelGraphic;
    private javax.swing.JLabel lblDelHard;
    private javax.swing.JLabel lblDelMain;
    private javax.swing.JLabel lblDelMem;
    private javax.swing.JLabel lblDelNo;
    private javax.swing.JLabel lblDelOrdCheck;
    private javax.swing.JLabel lblDelPay;
    private javax.swing.JLabel lblDelPower;
    private javax.swing.JLabel lblDelPrice;
    private javax.swing.JLabel lblDelSsd;
    private javax.swing.JLabel lblDisLogin;
    private javax.swing.JLabel lblDisMat;
    private javax.swing.JLabel lblFindUser;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLogOut;
    private javax.swing.JLabel lblOrdID;
    private javax.swing.JLabel lblOrdcheck;
    private javax.swing.JLabel lblPWerror;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblUPUser;
    private javax.swing.JLabel lblUpCheck;
    private javax.swing.JLabel lblUpID;
    private javax.swing.JLabel lblUpName;
    private javax.swing.JLabel lblUpType;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserType;
    private javax.swing.JLabel lbl_Success;
    private javax.swing.JList<String> lstPayment;
    private javax.swing.JTextField pw_FindEmail;
    private javax.swing.JTextField pw_FindID;
    private javax.swing.JTextField txtAddName;
    private javax.swing.JTextField txtAddPrice;
    private javax.swing.JTextField txtAddType;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JLabel txtBirth;
    private javax.swing.JPasswordField txtCheck;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFindUser;
    private javax.swing.JTextField txtID;
    private javax.swing.JPasswordField txtNPW;
    private javax.swing.JPasswordField txtNPWcheck;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtOrder;
    private javax.swing.JPasswordField txtPW;
    private javax.swing.JLabel txtUpBirth;
    private javax.swing.JPasswordField txtUpCheck;
    private javax.swing.JTextField txtUpEmail;
    private javax.swing.JPasswordField txtUpPW;
    private javax.swing.JTextField txtUpYear;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
