package deals.service;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by psundriyal on 6/17/18.
 */

@Component
public class TopDestinationService {

   

    private Logger log = Logger.getLogger(RedshiftConnector.class.getName());



    public List<String> execute(String query) {
        Connection conn = null;
        Statement stmt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate searchDate = LocalDate.now().minusDays(2);
        log.info(formatter.format(searchDate));

        List<String> destinations = new ArrayList<>();
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

            ResultSet rs = stmt.executeQuery(query);

            //Get the data from the result set.
            while(rs.next()){
                log.info(rs.getString("inbound_airport"));
                destinations.add(
                        rs.getString("inbound_airport"));

            }
            rs.close();
            stmt.close();
            conn.close();
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
        return destinations;
    }
}
