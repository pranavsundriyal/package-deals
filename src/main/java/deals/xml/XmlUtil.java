package deals.xml;

import deals.sql.model.Deals;

import deals.sql.model.PackageDeal;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static deals.Util.addValues;


/**
 * Created by psundriyal on 7/8/18.
 */

@Component
public class XmlUtil {

    public boolean write(Deals deals) {

        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(classLoader.getResource("deals.xml").getFile());

            JAXBContext jaxbContext = JAXBContext.newInstance(Deals.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(deals, file);
            //jaxbMarshaller.marshal(deals, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<PackageDeal> read() {
        JAXBContext jaxbContext = null;
        Deals deals = null;
        try {
            jaxbContext = JAXBContext.newInstance(Deals.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(classLoader.getResource("deals.xml").getFile());

            //We had written this file in marshalling example
            deals = (Deals) jaxbUnmarshaller.unmarshal( file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return deals.getPackageDeals();
    }


}