import deals.local.JsonDeals;
import org.junit.Test;

/**
 * Created by psundriyal on 3/25/18.
 */
public class JsonDealTest {

    @Test
    public void readPackages(){
        JsonDeals jsonDeals = new JsonDeals();
        jsonDeals.getDeals();
    }
}
