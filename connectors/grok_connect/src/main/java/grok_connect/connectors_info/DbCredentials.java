package grok_connect.connectors_info;

import java.util.*;
import grok_connect.utils.*;


public class DbCredentials
{
    public static final String SERVER = "server";
    public static final String DB = "db";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PORT = "port";

    public static final String DB_DESCRIPTION = "Database name";

    public String server;
    public String port;
    public String db;
    public String login;
    public String password;

    public DbCredentials() { }

    public DbCredentials(String server, String port, String db, String login, String password)
    {
        this.server = server;
        this.port = port;
        this.db = db;
        this.login = login;
        this.password = password;
    }

    public static List<Property> dbConnectionTemplate = new ArrayList<Property>() {{
        add(new Property(Property.STRING_TYPE, DbCredentials.SERVER));
        add(new Property(Property.INT_TYPE, DbCredentials.PORT));
        add(new Property(Property.STRING_TYPE, DbCredentials.DB, DbCredentials.DB_DESCRIPTION));
        add(new Property(Property.STRING_TYPE, DbCredentials.LOGIN));
        add(new Property(Property.STRING_TYPE, DbCredentials.PASSWORD, new Prop("password")));
    }};

    public DbCredentials(Map<String, Object> map)
    {
        server = (String)map.get(SERVER);
        db = (String)map.get(DB);
        login = (String)map.get(LOGIN);
        password = (String)map.get(PASSWORD);
    }
}