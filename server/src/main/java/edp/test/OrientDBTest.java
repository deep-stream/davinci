package edp.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.jdbc.OrientJdbcConnection;
import edp.core.model.QueryColumn;
import edp.core.utils.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class OrientDBTest {
    public static void main(String[] args) throws SQLException {

//        String sql = "select dept_name from Organization limit 1";
//        String sql = "select from Organization limit 1";
//        String sql = "SELECT @rid,@version,@class,in,out,count(*) as num FROM Has_Line_Long LIMIT 1";
          String sql = "select * from cron_job";
//        Properties info = new Properties();

//        info.put("user", "admin");
//        info.put("password", "admin");
//
//        Connection conn =  DriverManager.getConnection("jdbc:orient:remote:localhost/deppon", info); //(OrientJdbcConnection)
//
//        Statement stmt = conn.createStatement();
//
//        ResultSet rs = stmt.executeQuery(sql);
//
//        rs.next();
//
//        rs.getInt("@version");
//        rs.getString("@class");
//        rs.getString("@rid");
//
//        rs.getString(1);
//
//        ResultSetMetaData metaData = rs.getMetaData();
//
//        System.out.println("metaData.getCatalogName-->" + metaData.getCatalogName(1));
//        System.out.println("metaData.getColumnName-->" + metaData.getColumnName(1));
//        System.out.println("metaData.getColumnType-->" + metaData.getColumnType(1));
//        System.out.println("metaData.getColumnTypeName-->" + metaData.getColumnTypeName(1));
//
//        rs.close();
//        stmt.close();



//          String jdbcUrl = "jdbc:orient:remote:localhost/deppon";
//          String driverClass = "com.orientechnologies.orient.jdbc.OrientJdbcDriver";
//          String user = "root";
//          String password = "root";


        String jdbcUrl = "jdbc:mysql://localhost:3306/davinci0.3";
        String driverClass = "com.mysql.jdbc.Driver";
        String user = "root";
        String password = "root";




//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(jdbcUrl);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);


        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(50);
        dataSource.setUseOracleImplicitCache(true);

        dataSource.init();


        JdbcTemplate jdbcTemplate =  new JdbcTemplate(dataSource);
        jdbcTemplate.query(sql, resultSet -> {
            Object ret = null;
            if(resultSet ==null)return;

            System.out.println("resultSet.getString-->" + resultSet.getObject(1));
//
//            System.out.println("\n\n=================all properties============================");
//            final OResult currentRecord = resultSet.unwrap(OResult.class);
//            Optional<Collection<OProperty>> p = currentRecord.toElement().getSchemaType().map(x -> x.properties());
//            System.out.println("Optional<Collection<OProperty>>: " + p);

//            Object pValue = currentRecord.getProperty("dept_name");
//            System.out.println("currentRecord..getClass().getSimpleName: " + pValue.getClass().getSimpleName());

//            System.out.println("getSQLTypeFromJavaClass(pValue): " + getSQLTypeFromJavaClass(pValue));


//            OType pType = currentRecord.toElement().getSchemaType().map(x -> x.getProperty("dept_name")).map(x->x.getType()).orElse(null);
//            System.out.println("x.getType(\"in\"): " + pType);
//            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx---> " + currentRecord.toElement().getSchemaType());
//
//            System.out.println("\n\n=================type============================");
//            String p1 = currentRecord.toElement().getSchemaType().map(x -> x.getProperty("dept_name")).map(x->x.getType()).map(x->x.toString()).orElse("other");
////            Optional<OProperty> p1 = null;
//            System.out.println("x.getProperty(\"in\"): " + p1);

            System.out.println("\n\n=================metadata============================");

            ResultSetMetaData metaData = resultSet.getMetaData();
            System.out.println("metaData.getCatalogName-->" + metaData.getCatalogName(1));
            System.out.println("metaData.getColumnCount-->" + metaData.getColumnCount());
            for(int i=0;i<metaData.getColumnCount();i++){
                System.out.println("resultSet.getObject-->" + resultSet.getObject(i+1));
                System.out.println("resultSet.getObject.getClass().getSimpleName()-->" + resultSet.getObject(i+1)); //==null?"null":resultSet.getObject(i+1).getClass()
                System.out.println("metaData.getColumnName-->" + metaData.getColumnName(i+1));
                System.out.println("metaData.getColumnType-->" + metaData.getColumnType(i+1));
                System.out.println("metaData.getColumnTypeName-->" + metaData.getColumnTypeName(i+1));
                System.out.println("JDBC TypeName-->" + JDBCType.valueOf(metaData.getColumnType(i+1)).getName());
                System.out.println("metaData.getColumnClassName-->" + metaData.getColumnClassName(i+1));
            }


        });


//        System.out.println("OType.STRING, Types.VARCHAR" + OType.STRING + "," + Types.VARCHAR);
//        System.out.println("JDBCType.valueOf(Types.VARCHAR)-->" + JDBCType.valueOf(Types.VARCHAR));
//        System.out.println("JDBCType.valueOf(12)-->" + JDBCType.valueOf(12));

    }

    private static final Map<OType, Integer> typesSqlTypes = new HashMap<>();
    static {
        typesSqlTypes.put(OType.STRING, Types.VARCHAR);
        typesSqlTypes.put(OType.INTEGER, Types.INTEGER);
        typesSqlTypes.put(OType.FLOAT, Types.FLOAT);
        typesSqlTypes.put(OType.SHORT, Types.SMALLINT);
        typesSqlTypes.put(OType.BOOLEAN, Types.BOOLEAN);
        typesSqlTypes.put(OType.LONG, Types.BIGINT);
        typesSqlTypes.put(OType.DOUBLE, Types.DOUBLE);
        typesSqlTypes.put(OType.DECIMAL, Types.DECIMAL);
        typesSqlTypes.put(OType.DATE, Types.DATE);
        typesSqlTypes.put(OType.DATETIME, Types.TIMESTAMP);
        typesSqlTypes.put(OType.BYTE, Types.TINYINT);
        typesSqlTypes.put(OType.SHORT, Types.SMALLINT);

        // NOT SURE ABOUT THE FOLLOWING MAPPINGS
        typesSqlTypes.put(OType.BINARY, Types.BINARY);
        typesSqlTypes.put(OType.EMBEDDED, Types.JAVA_OBJECT);
        typesSqlTypes.put(OType.EMBEDDEDLIST, Types.ARRAY);
        typesSqlTypes.put(OType.EMBEDDEDMAP, Types.JAVA_OBJECT);
        typesSqlTypes.put(OType.EMBEDDEDSET, Types.ARRAY);
        typesSqlTypes.put(OType.LINK, Types.JAVA_OBJECT);
        typesSqlTypes.put(OType.LINKLIST, Types.ARRAY);
        typesSqlTypes.put(OType.LINKMAP, Types.JAVA_OBJECT);
        typesSqlTypes.put(OType.LINKSET, Types.ARRAY);
        typesSqlTypes.put(OType.TRANSIENT, Types.NULL);
    }

    public static int getSQLTypeFromJavaClass(final Object value) {
        if (value instanceof Boolean)
            return typesSqlTypes.get(OType.BOOLEAN);
        else if (value instanceof Byte)
            return typesSqlTypes.get(OType.BYTE);
        else if (value instanceof Date)
            return typesSqlTypes.get(OType.DATETIME);
        else if (value instanceof Double)
            return typesSqlTypes.get(OType.DOUBLE);
        else if (value instanceof BigDecimal)
            return typesSqlTypes.get(OType.DECIMAL);
        else if (value instanceof Float)
            return typesSqlTypes.get(OType.FLOAT);
        else if (value instanceof Integer)
            return typesSqlTypes.get(OType.INTEGER);
        else if (value instanceof Long)
            return typesSqlTypes.get(OType.LONG);
        else if (value instanceof Short)
            return typesSqlTypes.get(OType.SHORT);
        else if (value instanceof String)
            return typesSqlTypes.get(OType.STRING);
        else if (value instanceof List)
            return typesSqlTypes.get(OType.EMBEDDEDLIST);
        else
            return Types.JAVA_OBJECT;
    }
}
