package net.voxhash.shadowprotect.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import net.voxhash.shadowprotect.database.table.UserSettings;

public class Database {

    public SessionFactory sf;

    String name;
    String url;
    String username;
    String password;
    Integer port;
    // user settings (such as blocked users, chat settings like sign spy and staff chat, etc)

    public Database(String url, int port, String name, String username, String password) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.port = port;
        this.password = password;
    }

    public SessionFactory getFactory() {
        if (this.sf == null) {
            try {
                Configuration config = new Configuration();

                config.setProperty(Environment.URL, this.url);
                config.setProperty(Environment.USER, this.username);
                config.setProperty(Environment.PASS, this.password);

                config.addAnnotatedClass(UserSettings.class);

                ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

                this.sf = config.buildSessionFactory(sr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return this.sf;
    }
}
