package org.headstar.beangraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BeanGraphConfiguration {

    private final List<BeanGraphConfigurer> configurers = new ArrayList<BeanGraphConfigurer>();
    private BeanGraphProducer graphSource;

    @Autowired(required = false)
    public void setDependencyConfigurers(final List<BeanGraphConfigurer> configurers) {
        if (configurers != null) {
            this.configurers.addAll(configurers);
        }
    }

    @Bean
    public BeanGraphProducer dependencyGraphSource() {
        if (graphSource == null) {
            graphSource = new BeanGraphProducer();
            for (BeanGraphConfigurer configurer : this.configurers) {
                configurer.configureReporters(graphSource);
            }
        }
        return graphSource;
    }
}