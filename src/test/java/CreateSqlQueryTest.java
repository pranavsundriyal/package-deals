import org.junit.Test;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by psundriyal on 3/26/18.
 */
public class CreateSqlQueryTest {

        @Test
        public void readPackages(){
            String c = read();
            System.out.println(c);
            Assert.notNull(c);
        }

        public String read(){
            BufferedReader br = null;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("select * from ods.air_book_results where book_date > '2018-03-13' and (tuid like '59787118'\n");
            try {

                String sCurrentLine;

                br = new BufferedReader(new FileReader("/Users/psundriyal/expedia/package-deals/src/main/resources/data/data"));


                while ((sCurrentLine = br.readLine()) != null) {
                    stringBuffer.append("or tuid like '"+sCurrentLine+"'\n");
                }
                stringBuffer.append(")");

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
}


