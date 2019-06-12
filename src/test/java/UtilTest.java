import deals.sql.model.PackageDeal;
import deals.util.Util;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 8/12/18.
 */
public class UtilTest {

    @Test
    public void calculateAirlineDominanceTest () {
        List<PackageDeal> packageDealList = new ArrayList<>();
        PackageDeal packageDeal1 = new PackageDeal();
        packageDeal1.setFlightNo("UA-204 F9-223");
        PackageDeal packageDeal2 = new PackageDeal();
        packageDeal2.setFlightNo("LH-201 UA-223");
        packageDealList.add(packageDeal1);
        packageDealList.add(packageDeal2);
        Assert.assertNotNull(Util.calculateAirlineDominance(packageDealList));

    }
}
