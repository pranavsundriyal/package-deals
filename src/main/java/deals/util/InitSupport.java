package deals.util;

import com.codahale.metrics.servlet.InstrumentedFilter;
import com.expedia.www.platform.isactive.servlet.BuildInfoServlet;
import com.expedia.www.platform.isactive.servlet.IsActiveServlet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Configuration
@EnableConfigurationProperties

@SuppressWarnings({"PMD.SingularField", "PMD.ImmutableField"})
public class InitSupport implements ServletContextAware {

    private ServletContext servletContext;

    /**
    * Create the The Metrics Web FilterRegistrationBean for this service.
    *
    * @return FilterRegistrationBean
    */
    @Bean
    public FilterRegistrationBean metricsWebFilterRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new InstrumentedFilter());
        return registrationBean;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    public ServletRegistrationBean registBuildInfo() {
        final ServletRegistrationBean buildInfo = new ServletRegistrationBean(new BuildInfoServlet(), "/buildInfo");
        buildInfo.setName("buildInfo");
        return buildInfo;
    }

    @Bean
    public ServletRegistrationBean registIsActive() {
        final ServletRegistrationBean isActiveBean = new ServletRegistrationBean(
                new IsActiveServlet(), "/isActive");
        isActiveBean.setName("isActive");
        return isActiveBean;
    }


}
