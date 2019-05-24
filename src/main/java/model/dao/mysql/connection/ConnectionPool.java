package model.dao.mysql.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private static ConnectionPool instance;

    private static String DATASOURCE_NAME = "java:comp/env/jdbc/railway_system";
    private static volatile javax.sql.DataSource dataSource;

    private ConnectionPool() throws SQLException{
        try {
            Context initContex = new InitialContext();
            Context context = (Context) initContex.lookup("java:comp/env/");
            dataSource = (javax.sql.DataSource) context.lookup(DATASOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() throws SQLException{
        if(instance == null){
            synchronized (ConnectionPool.class){
                if(instance == null){
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }



    public Connection getConnection() throws SQLException{
        Connection connection = dataSource.getConnection();
        return connection;
    }
}
