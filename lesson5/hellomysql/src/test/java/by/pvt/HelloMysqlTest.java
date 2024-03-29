package by.pvt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.*;

/**
 *
 */
public class HelloMysqlTest extends DBTestCase {

    public HelloMysqlTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/hello_mysql_junit");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(HelloMysqlTest.class.getResourceAsStream("system_users.xml"));
    }

    @Test
    public void testConnection() {

        try (Connection connection =
                     DriverManager
                             .getConnection("jdbc:mysql://localhost:3306/hello_mysql_junit", "root", "root");
             PreparedStatement ps = connection.prepareStatement("select * from system_users");
        ) {
            ResultSet rs = ps.executeQuery();
            assertNotNull(rs);

            int rawCount = 0;
            int activeUser = 0;

            while (rs.next()) {
                rawCount++;
                if (rs.getBoolean("active")) activeUser++;
            }
            assertEquals(4, rawCount);
            assertEquals(2, activeUser);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
