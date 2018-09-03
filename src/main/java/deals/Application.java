package deals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import deals.cache.CacheManager;
import deals.model.json.Deal;
import deals.local.JsonDeals;
import deals.service.RedshiftConnector;
import deals.sql.SqlQueryGenerator;
import deals.sql.model.PackageDeal;
import deals.xml.XmlUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HashMap<String, List<Deal>> dealMap(){
        HashMap<String,List<Deal>> dealMap = new HashMap<>();
        JsonDeals jsonDeals = new JsonDeals();
        List<Deal> deals = jsonDeals.getDeals();
        for (Deal deal : deals) {
            String key = deal.getOutboundAirport() + "-" + deal.getInboundAirport();
            if (!dealMap.containsKey(key)){
                List<Deal> dealList = new ArrayList<>();
                dealList.add(deal);
                dealMap.put(key,dealList);
            } else {
                List<Deal> dealList = dealMap.get(key);
                dealList.add(deal);
                dealMap.put(key,dealList);
            }
        }
        return dealMap;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

}
