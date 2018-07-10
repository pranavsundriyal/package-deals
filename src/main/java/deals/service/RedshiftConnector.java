package deals.service;


import deals.sql.model.Deals;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
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

    @Autowired
    XmlUtil xmlWriter;

    public List<PackageDeal> execute(String query) {
        Connection conn = null;
        Statement stmt = null;

        List<PackageDeal> deals = new ArrayList<>();
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
                log.info(rs.getDouble("pack_price")+" | "+ rs.getDouble("sa_price"));
                deals.add(new PackageDeal(rs.getDouble("pack_price"),
                        rs.getDouble("sa_price"),
                        rs.getString("outbound_departure_time"),
                        rs.getString("inbound_arrival_time"),
                        rs.getString("marketing_flights"),
                        rs.getString("outbound_airport"),
                        rs.getString("inbound_airport")));

            }
            Deals dealsList = new Deals();
            dealsList.setPackageDeals(deals);
            xmlWriter.write(dealsList);
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
        return deals;
    }
}
