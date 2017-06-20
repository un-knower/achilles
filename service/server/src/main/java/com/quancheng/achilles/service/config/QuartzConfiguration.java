package com.quancheng.achilles.service.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.quancheng.achilles.service.job.OrderCacheSyncJob;
import com.quancheng.achilles.service.job.SJJob;


@Configuration
public class QuartzConfiguration {
	private static final String QUARTZ_DS_PASSWORD = "org.quartz.dataSource.qrtzDS.password";
	private static final String QUARTZ_DS_USER = "org.quartz.dataSource.qrtzDS.user";
	private static final String QUARTZ_DS_URL = "org.quartz.dataSource.qrtzDS.URL";
	
	@Autowired
	Environment env;

//<bean id="cacheSyncJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
//	    <property name="jobClass" value="com.quancheng.achilles.servicedao.job.OrderCacheSyncJob" />
//	     <property name="group" value="inn"/>
//	    <property name="durability" value="true" />
//	    <property name="requestsRecovery" value="true" />
//</bean>

	@Bean(name="cacheSyncJobDetail")
	public JobDetailFactoryBean jobDetailFactoryBean(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(OrderCacheSyncJob.class);
		factory.setGroup("inn");
		factory.setDurability(true);
		factory.setRequestsRecovery(true);
		return factory;
	}
	
//	@Bean(name="sjJobDetail")
//	public JobDetailFactoryBean sjJobDetail(){
//		JobDetailFactoryBean factory = new JobDetailFactoryBean();
//		factory.setJobClass(SJJob.class);
//		factory.setGroup("sj");
//		factory.setDurability(true);
//		factory.setRequestsRecovery(true);
//		return factory;
//	}
	
//<bean id="cacheSyncJobTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
//	    <property name="jobDetail" ref="cacheSyncJobDetail">
//	    </property>
//	     <property name="group" value="inn"/>
//	    <property name="cronExpression">
//	        <value>0 0 1 * * ?</value>
//	    </property>
//</bean>
	/**
	 * Job is scheduled every day at 1 AM.
	 * @return
	 */
	@Bean(name="cacheSyncJobTrigger")
	public CronTriggerFactoryBean cronTriggerFactoryBean(@Qualifier("cacheSyncJobDetail") JobDetailFactoryBean jobDetailFactoryBean ){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(jobDetailFactoryBean .getObject());
		stFactory.setGroup("inn");
		// every 5 minute test
		stFactory.setCronExpression("0 0 1 * * ?");
		return stFactory;
	}
	
//	@Bean(name="sjTrigger")
//	public CronTriggerFactoryBean sjTrigger(@Qualifier("sjJobDetail") JobDetailFactoryBean sjTrigger){
//		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
//		stFactory.setJobDetail(sjTrigger.getObject());
//		stFactory.setGroup("sj");
//		// every 5 minute test
//		stFactory.setCronExpression("0 15 03 1 * ?");
//		return stFactory;
//	}
	
//	<bean id="innQuartzScheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
//	    <property name="configLocation" value="classpath:application.properties" />
//	    <property name="triggers">
//	        <list>
//	            <ref bean="cacheSyncJobTrigger" />
//	        </list>
//	    </property>
//	     <property name="applicationContextSchedulerContextKey" value="applicationContextKey" />
//	    <property name="autoStartup" value="true"></property>
//  </bean>
	@Bean(name="innQuartzScheduler")
	public SchedulerFactoryBean schedulerFactoryBean(
//			@Qualifier("sjTrigger") CronTriggerFactoryBean ctfb,
			@Qualifier("cacheSyncJobTrigger") CronTriggerFactoryBean cacheSyncJobTrigger
			) {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		// fix the issue that sqlSession can't be autowired
		scheduler.setJobFactory(autoWiringSpringBeanJobFactory());
		
		scheduler.setConfigLocation(new ClassPathResource("application-quartz.properties"));
		scheduler.setTriggers( cacheSyncJobTrigger.getObject() 
//		        ,ctfb.getObject()
		        );
		scheduler.setApplicationContextSchedulerContextKey("applicationContextKey");
		scheduler.setAutoStartup(true);
		
		Properties quartzProperties = new Properties();
		quartzProperties.put(QUARTZ_DS_URL, env.getProperty(QUARTZ_DS_URL));
		quartzProperties.put(QUARTZ_DS_USER, env.getProperty(QUARTZ_DS_USER));
		quartzProperties.put(QUARTZ_DS_PASSWORD, env.getProperty(QUARTZ_DS_PASSWORD));
		scheduler.setQuartzProperties(quartzProperties);
		
		return scheduler;
	}
	
	@Bean
    public AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory(){
        return new AutoWiringSpringBeanJobFactory();
    }
}
