package jdbc;

import static org.junit.Assert.fail;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.junit.Ignore;
import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTest {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void testConnection() {
		String resource = "config/config.properties";
		Properties properties = new Properties();
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			properties.load(reader);
			try (Connection con = DriverManager.getConnection(
					properties.getProperty("spring.datasource.url"),
					properties.getProperty("spring.datasource.username"),
					properties.getProperty("spring.datasource.password")
			)) {
				log.info(con);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}