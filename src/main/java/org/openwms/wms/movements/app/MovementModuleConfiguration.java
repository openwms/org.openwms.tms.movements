/*
 * Copyright 2005-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.wms.movements.app;

import io.micrometer.core.instrument.MeterRegistry;
import org.ameba.annotation.EnableAspects;
import org.ameba.app.SpringProfiles;
import org.ameba.http.PermitAllCorsConfigurationSource;
import org.ameba.http.ctx.CallContextClientRequestInterceptor;
import org.ameba.i18n.AbstractSpringTranslator;
import org.ameba.i18n.Translator;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.ameba.system.NestedReloadableResourceBundleMessageSource;
import org.openwms.wms.movements.impl.MovementHandler;
import org.openwms.wms.movements.impl.MovementRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;
import java.util.Locale;
import java.util.Properties;

/**
 * A MovementModuleConfiguration.
 *
 * @author Heiko Scherrer
 */
@Configuration
@EnableTransactionManagement
@EnablePluginRegistries({MovementHandler.class})
@EnableJpaRepositories(basePackageClasses = MovementRepository.class)
@EntityScan(basePackageClasses = MovementRepository.class)
@EnableJpaAuditing
@EnableAspects(propagateRootCause = true)
@EnableScheduling
public class MovementModuleConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }

    @Profile(SpringProfiles.DEVELOPMENT_PROFILE)
    @Bean Filter corsFiler() {
        return new CorsFilter(new PermitAllCorsConfigurationSource());
    }

    @LoadBalanced
    @Bean
    RestTemplate aLoadBalanced() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new CallContextClientRequestInterceptor());
        return restTemplate;
    }

    @Bean BeanMapper beanMapper() {
        return new DozerMapperImpl("META-INF/dozer/movements-bean-mappings.xml");
    }

    public @Bean LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    public @Bean LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean Translator translator() {
        return new AbstractSpringTranslator() {
            @Override
            protected MessageSource getMessageSource() {
                return messageSource();
            }
        };
    }

    @Bean MessageSource messageSource() {
        NestedReloadableResourceBundleMessageSource messageSource = new NestedReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/META-INF/i18n/mvm");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCommonMessages(new Properties());
        return messageSource;
    }
}
