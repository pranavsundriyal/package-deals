package deals.sql.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by psundriyal on 11/13/18.
 */
public class TimestampAdapter extends XmlAdapter<String, Timestamp> {


    @Override
    public Timestamp unmarshal(String v) throws Exception {
        return new Timestamp(Long.parseLong(v));
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        return v.toString();
    }
}
