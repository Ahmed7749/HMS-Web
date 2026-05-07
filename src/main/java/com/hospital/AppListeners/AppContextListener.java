package com.hospital.AppListeners;

import com.hospital.utils.EmailService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("HMS-Web Application is starting up...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("HMS-Web Application is shutting down...");
        EmailService.shutdown();
    }
}