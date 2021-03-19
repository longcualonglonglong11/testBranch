package client.config;

import com.fts.common.tracing.ITracing;
import com.fts.common.tracing.instance.TracingFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "")

@PropertySource("classpath:application.properties")
public class ClientConfigTracing {
    @Bean
    public ITracing iTracing() {
        return TracingFactory.getTracingInstance("client_common_test", "");
    }
}
