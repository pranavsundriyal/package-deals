package deals.service;


import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by psundriyal on 10/24/17.
 */


@Component
public class RedshiftConnector {

    static final String dbURL = "jdbc:redshift://bexg.cdzdl6bkwykk.us-east-1.redshift.amazonaws.com:5439/prod";
    static final String MasterUsername = "air_shop_data_ingestion";
    static final String MasterUserPassword = "Airshopdata123";

    private Logger log = Logger.getLogger(RedshiftConnector.class.getName());

    public ResultSet execute(String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs= null;
        List<Object> rowData = new ArrayList<>();
        try{
            //Dynamically load driver at runtime.
            //Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc41.Driver
            //Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
            Class.forName("com.amazon.redshift.jdbc.Driver");

            //Open a connection and define properties.
            System.out.println("Connecting to database...");
            Properties props = new Properties();

            //Uncomment the following line if using a keystore.
            //props.setProperty("ssl", "true");
            props.setProperty("user", MasterUsername);
            props.setProperty("password", MasterUserPassword);
            conn = DriverManager.getConnection(dbURL, props);

            //Try a simple query.
            log.info("Listing system tables...");
            stmt = conn.createStatement();

            rs = stmt.executeQuery(query);

            List<String> columnNames = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnLabel(i));
            }

            int rowIndex = 0;
            while (rs.next()) {
                rowIndex++;
                // collect row data as objects in a List
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    //rowData.add(rs.getObject(i));
                }
                // for test purposes, dump contents to check our results
                // (the real code would pass the "rowData" List to some other routine)
                System.out.printf("Row %d%n", rowIndex);
                List<Object> colObjects = new ArrayList<>();

                for (int colIndex = 0; colIndex < rsmd.getColumnCount(); colIndex++) {
                    String objType = "null";
                    String objString = "";
                    Object columnObject = rowData.get(colIndex);
                    if (columnObject != null) {
                        objString = columnObject.toString() + " ";
                        objType = columnObject.getClass().getName();
                    }
                    log.info(columnNames.get(colIndex)+" | "+objString+" | "+ objType);
                }

            }

            //Get the data from the result set.
            stmt.close();
            conn.close();
            rs.close();
        }catch(Exception ex){
            //For convenience, handle all errors here.
            ex.printStackTrace();
        }finally{
            //Finally block to close resources.
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(Exception ex){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        log.info("Finished connectivity test.");
        return rs;
    }
}
