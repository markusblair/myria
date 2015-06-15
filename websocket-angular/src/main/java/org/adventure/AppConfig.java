package org.adventure;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.adventure.character.CharacterScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jdo.JdoTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
@ComponentScan(basePackages = { "org.adventure" }, excludeFilters = { @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION) })
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class AppConfig implements TransactionManagementConfigurer {
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager();
	}

	  @Bean
	  public CustomScopeConfigurer customScope () {
	      CustomScopeConfigurer configurer = new CustomScopeConfigurer ();
	      Map<String, Object> workflowScope = new HashMap<String, Object>();
	      workflowScope.put("characterSession", new CharacterScope());
	      configurer.setScopes(workflowScope);

	      return configurer;
	  }
	
	@Bean
	public PersistenceManagerFactory persistenceManagerFactory() {
		Properties properties = new Properties();
		properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "com.objectdb.jdo.PMF");
		properties.setProperty("javax.jdo.option.ConnectionURL", "Myria.odb");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(properties);
		return pmf;
	}

	@Bean
	public PersistenceManager persistenceManager() {
		return persistenceManagerFactory().getPersistenceManager();
	}

	@Bean
	public JdoTransactionManager txManager() {
		JdoTransactionManager txManager = new JdoTransactionManager();
		txManager.setPersistenceManagerFactory(persistenceManagerFactory());

		return txManager;
	}

}
