module project.insurance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;
    requires java.sql;
    requires itextpdf;
    requires org.apache.logging.log4j.slf4j;
    requires slf4j.api;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.annotation;


    opens project to javafx.fxml, org.hibernate.orm.core;
    exports project;
    exports project.Controllers;
    opens project.Controllers to javafx.fxml, org.hibernate.orm.core;
    exports project.Model;
    opens project.Model to javafx.fxml, org.hibernate.orm.core;
    exports project.Helpers;
    opens project.Helpers to javafx.fxml, org.hibernate.orm.core;
}