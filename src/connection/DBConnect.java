package connection;

import java.sql.*;

public class DBConnect {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public DBConnect() {
        try{
            //Laptop-Pribadi
            String url = "jdbc:sqlserver://LAPTOP-H6F615RS:1433;databaseName=Ticketing-Queue;integratedSecurity=true;";
            //Laptop-Kampus
            //String url = "jdbc:sqlserver://10.8.10.160:1433;databaseName=Ticketing-Queue;integratedSecurity=false; user=sa; password=polman";

            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();

        }catch (Exception e)
        {
            System.out.println("Error saat connect database: "+e.getMessage());
        }
    }

    public static void main(String[] args) {
        DBConnect connect = new DBConnect();
        System.out.println("Connection Berhasil");
    }
}
