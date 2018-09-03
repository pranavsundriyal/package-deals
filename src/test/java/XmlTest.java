import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by psundriyal on 7/9/18.
 */
public class XmlTest {

    @Test
    public void readXmlTest() {
        XmlUtil xmlUtil = new XmlUtil();
        List<PackageDeal> packageDealList = xmlUtil.read();
        assertTrue(packageDealList.size() > 0);
    }
}
