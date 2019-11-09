package com.uygulama.config;

import com.uygulama.model.Book;
import com.uygulama.model.UserInfo;
import org.hibernate.LazyInitializationException;
import org.hibernate.cfg.AvailableSettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.FetchType;
import java.util.Properties;

@PropertySource(value="classpath:hibernate.properties", encoding = "UTF-8") //db ve hibernate ayarlarının oldugu properties dosyasını tanıttık
@EnableTransactionManagement //SprigFramework dependency ile geliyor
@Configuration //Konfigürasyon sınıfı oldugunu belırtıyoruz
@ComponentScan(basePackages = {"com.uygulama"})

// AppConfig'de Bean'larımız bulunur
//AvailableSettings'i implement etmek için hibernate dependency'i eklemek gerekli
public class AppConfig implements AvailableSettings {

    @Autowired //Nesne olusturulan sınıf ıle baglantı kuruldu
    private Environment environment; // Defalarca yazmayı kolaylastırmak ıcın

    @Bean
    public LocalSessionFactoryBean getSessionFactory(){

        LocalSessionFactoryBean factoryBean= new LocalSessionFactoryBean();

        //Java Utilden
        Properties properties=new Properties(); //Degerlerımızı bu nesneye atacagız

        //JDBC Ayarları
        properties.put(DRIVER, environment.getProperty("mysql.driver"));
        properties.put(URL, environment.getProperty("mysql.url"));
        properties.put(USER, environment.getProperty("mysql.user"));
        properties.put(PASS, environment.getProperty("mysql.password"));

        //HIBERNATE Ayarları
        properties.put(SHOW_SQL,environment.getProperty("hibernate.show_sql"));
        properties.put(FORMAT_SQL, environment.getProperty("hibernate.format_sql"));
        properties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put(DIALECT, environment.getProperty("hibernate.dialect"));
        properties.put(ENABLE_LAZY_LOAD_NO_TRANS, environment.getProperty("hibernate.ENABLE_LAZY_LOAD_NO_TRANS")); //izin vermezsek ilişkili tablodaki veriler gelmıyor

        //C3P0 Ayarları
        properties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        properties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        properties.put(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        properties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));
        properties.put(C3P0_CONFIG_PREFIX + ".initialPoolSize", environment.getProperty("hibernate.c3p0.initialPoolSize"));


        factoryBean.setHibernateProperties(properties); //Degerlerin oldugu nesneyi Hibernate ozellıgı olarak FactoryBean'e atadık
        factoryBean.setAnnotatedClasses(Book.class, UserInfo.class);//bu Bean'lerı alıyor --Modeldeki class'ları yazıyoruz

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager(){
        //Islemleri yapmak ısın transaction nesnesı olusturuyoruz
        HibernateTransactionManager transactionManager=new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());

        return transactionManager;
    }
}
