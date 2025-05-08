package com.HE180030.common;

public interface Constants {
    interface DatabaseConfig {
        String JDBC_URL = "jdbc.url";
        String JDBC_DRIVER_CLASSNAME_DEFAULT = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String JDBC_DRIVER_CLASSNAME = "jdbc.driverClassName";
        String JDBC_USERNAME = "jdbc.username";
        String JDBC_PASSWORD = "jdbc.password";
        String HIBERNATE_DIALECT = "hibernate.dialect";
        String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
        String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
        String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    }

    interface MailProperties {
        String HOST_NAME = "smtp.gmail.com";
        int PORT = 465;
        String APP_EMAIL = "hoana5k44nknd@gmail.com";
        String APP_PASSWORD = "";
    }
}
