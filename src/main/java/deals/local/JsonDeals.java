package deals.local;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import deals.model.json.Deal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by psundriyal on 3/23/18.
 */
public class JsonDeals {

    public String read(){
        BufferedReader br = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("/Users/psundriyal/expedia/package-deals/src/main/resources/data/package-deals.json"));

            while ((sCurrentLine = br.readLine()) != null) {
                stringBuffer.append(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    public List<Deal> getDeals() {
        String json = read();
        List<Deal> deals = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            deals = objectMapper.readValue(json, new TypeReference<List<Deal>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deals;
    }

}
