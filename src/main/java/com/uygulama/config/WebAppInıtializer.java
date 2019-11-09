package com.uygulama.config;

import com.uygulama.model.UserInfo;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

//
// ** Program buradan baslayacak **
//

// Dispatcher Servlet(On kontrol) ve Web Filter tanımı yapılacak
public class WebAppInıtializer implements WebApplicationInitializer {

    //WebApplicationInitializer implement edıldıgı ıcın onStartup metodu Override edilmeli
    //javax.servlet-api dependency ile ServletContext geliyor
    @Override
    public void onStartup(ServletContext servletContext) {

        //DispatcherServlet
        //Uygulamaya gelen bütün isteklerin karşılanmasından ve Spring uygulama
        //çatısı içinde yönlendirilmesinden sorumludur.
        //Burası kapalı olursa veritabanı ile ilişki kesiliyor
        AnnotationConfigWebApplicationContext context=getContext(); //Yukarıda belirtılen ayarı kullan

        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcherServlet=
                servletContext.addServlet("DispatcherServlet",new DispatcherServlet(context));

        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");

        //Web Filter
        // Karakter kodlaması ve sıkıştırma gibi genel işlemler, belirtilen HTTP isteklerin tamamına
        CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setForceRequestEncoding(true);
        characterEncodingFilter.setForceResponseEncoding(true);

        //Filter'ı servletContext'e eklıyoruz
        servletContext.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null,false,"/*");
    }

    //Konfigurasyon ayarı olarak nereyı kullanacagını belırtıyoruz
    private AnnotationConfigWebApplicationContext getContext(){
        AnnotationConfigWebApplicationContext context=new AnnotationConfigWebApplicationContext(); //Projemızı Annotation ile yapacagımızı belırtıyoruz
        context.setConfigLocation("com.uygulama.config"); //Burada belırtılen degerlerın kullanılacagını belırtıyoruz
        return context;
    }
}