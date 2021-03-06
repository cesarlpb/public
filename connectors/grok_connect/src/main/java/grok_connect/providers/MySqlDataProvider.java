package grok_connect.providers;

import java.sql.*;
import java.util.*;
import serialization.Types;
import grok_connect.utils.*;
import grok_connect.table_query.*;
import grok_connect.connectors_info.*;


public class MySqlDataProvider extends JdbcDataProvider {
    public MySqlDataProvider() {
        descriptor = new DataSource();
        descriptor.type = "MySQL";
        descriptor.description = "Query MySQL database";
        descriptor.connectionTemplate = DbCredentials.dbConnectionTemplate;
        descriptor.connectionTemplate.add(new Property(Property.BOOL_TYPE, DbCredentials.SSL));
        descriptor.credentialsTemplate = DbCredentials.dbCredentialsTemplate;
        descriptor.canBrowseSchema = true;
        descriptor.nameBrackets = "`";

        descriptor.typesMap = new HashMap<String, String>() {{
            put("bool", Types.BOOL);
            put("boolean", Types.BOOL);
            put("#bit.*", Types.INT);
            put("#tinyint.*", Types.INT);
            put("#smallint.*", Types.INT);
            put("#mediumint.*", Types.INT);
            put("#int.*", Types.INT);
            put("#integer.*", Types.INT);
            put("#bigint.*", Types.BIG_INT);
            put("#dec.*", Types.FLOAT);
            put("#decimal.*", Types.FLOAT);
            put("#float.*", Types.FLOAT);
            put("#double.*", Types.FLOAT);
            put("#double precision.*", Types.FLOAT);
            put("#char.*", Types.STRING);
            put("#varchar.*", Types.STRING);
            put("#text.*", Types.STRING);
            put("#tinytext.*", Types.STRING);
            put("date", Types.DATE_TIME);
            put("#datetime.*", Types.DATE_TIME);
            put("#timestamp.*", Types.DATE_TIME);
            put("#time.*", Types.DATE_TIME);
        }};
        descriptor.aggregations = new ArrayList<AggrFunctionInfo>() {{
            add(new AggrFunctionInfo(Stats.AVG, "avg(#)", Types.dataFrameNumericTypes));
            add(new AggrFunctionInfo(Stats.MIN, "min(#)", Types.dataFrameNumericTypes));
            add(new AggrFunctionInfo(Stats.MAX, "max(#)", Types.dataFrameNumericTypes));
            add(new AggrFunctionInfo(Stats.SUM, "sum(#)", Types.dataFrameNumericTypes));
            add(new AggrFunctionInfo(Stats.STDEV, "std(#)", Types.dataFrameNumericTypes));
            add(new AggrFunctionInfo(Stats.TOTAL_COUNT, "count(*)", Types.dataFrameColumnTypes));
            add(new AggrFunctionInfo(Stats.VALUE_COUNT, "count(#)", Types.dataFrameColumnTypes));
            add(new AggrFunctionInfo(Stats.MISSING_VALUE_COUNT, "count(*) - count(#)", Types.dataFrameColumnTypes));
        }};
    }

    public Connection getConnection(DataConnection conn) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.util.Properties properties = defaultConnectionProperties(conn);
        properties.setProperty("zeroDateTimeBehavior", "convertToNull");
        if (conn.parameters.containsKey(DbCredentials.SSL) && (boolean)conn.parameters.get(DbCredentials.SSL)) {
            properties.setProperty("useSSL", "true");
            properties.setProperty("verifyServerCertificate", "false");
        }
        return DriverManager.getConnection(getConnectionString(conn), properties);
    }

    public String getConnectionString(DataConnection conn) {
        String port = (conn.getPort() == null) ? "" : ":" + conn.getPort();
        return "jdbc:mysql://" + conn.getServer() + port + "/" + conn.getDb();
    }

    public String getSchemasSql(String db) {
        return "SELECT DISTINCT table_schema FROM information_schema.columns ORDER BY table_schema";
    }

    public String getSchemaSql(String db, String schema, String table)
    {
        List<String> filters = new ArrayList<>();

        if (schema != null)
            filters.add("table_schema = '" + schema + "'");

        if (table != null)
            filters.add("(table_name = '" + table + "')");

        String whereClause = String.join(" and \n", filters);

        return "SELECT table_schema, table_name, column_name, data_type " +
                "FROM information_schema.columns WHERE " + whereClause + " ORDER BY table_name";
    }
}
