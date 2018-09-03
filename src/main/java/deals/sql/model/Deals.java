package deals.sql.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by psundriyal on 7/8/18.
 */
@XmlRootElement(name = "deals")
@XmlAccessorType(XmlAccessType.FIELD)
public class Deals {

    private List<PackageDeal> packageDeals;

    public List<PackageDeal> getPackageDeals() {
        return packageDeals;
    }

    public void setPackageDeals(List<PackageDeal> packageDeals) {
        this.packageDeals = packageDeals;
    }
}
