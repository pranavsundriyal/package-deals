package deals.service;

import deals.sql.SqlQueryGenerator;
import deals.sql.model.PackageDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Autowired
    RedshiftConnector redshiftConnector;

    private Logger log = Logger.getLogger(RedshiftConnector.class.getName());



    public List<String> execute(String origin) {

        List<String> destinations = new ArrayList<>();
        ResultSet rs = redshiftConnector.execute(SqlQueryGenerator.generateTopDest(origin));
        try {
            while (rs.next()) {
                destinations.add(rs.getString("inbound_airport"));
            }
            rs.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinations;
    }
}
