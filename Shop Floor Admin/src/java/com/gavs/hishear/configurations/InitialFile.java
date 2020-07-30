/**
 * InitialFile.java
 */
package com.gavs.hishear.configurations;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Pinky S
 * 
 */
public class InitialFile {

	private DataSource ds1;
	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(InitialFile.class);

	public void setDBProperties() {

		Connection connection = null;
		String dbUserName = null;
		String dbPassword = null;
		String m3UserName = null;
		String m3Password = null;

		try {
			connection = getConnection();
			
			if (connection != null) {
				Statement statement = connection.createStatement();
				String runQuery = "SELECT * FROM Application_Credentials where ApplicationName = 'Shop Floor Admin'";

				ResultSet resultSet = statement.executeQuery(runQuery);
				while (resultSet.next()) {
					dbUserName = resultSet.getString("DBUserName");
					dbPassword = resultSet.getString("DBPassword");
					m3UserName = resultSet.getString("M3UserName");
					m3Password = resultSet.getString("M3Password");
				}
				resultSet.close();
				resultSet = null;

			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		HashMap<String, String> propertiesMap = new HashMap<String, String>();
		String code = null;
		String value = null;

		try {
			System.out.println("code and value");
			connection = getConnection();
			Properties versionProperties = new Properties();
			
			/*URL versionUrl = getClass().getClassLoader().getResource("./version.properties");
			versionProperties.load(new FileInputStream(versionUrl.getFile()));
			propertiesMap.put("VERSION", versionProperties.getProperty("app.version"));*/
			
			
			if (connection != null) {
				System.out.println("Conection"+connection);
				Statement statement = connection.createStatement();
				String runQuery = "select * from Application_Properties where ApplicationName in ('ALL','Shop Floor Admin') order by ID";
				ResultSet resultSet = statement.executeQuery(runQuery);
				while (resultSet.next()) {
					code = resultSet.getString("Code");
					value = resultSet.getString("Value");
					System.out.println(code + " " +value);
					propertiesMap.put(code, value);
				}
				
				
				resultSet.close();
				resultSet = null;

			} else {
				System.out.println("Error: No active Connection");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		System.out.println(dbUserName + " database config" + dbPassword);
		writeDBPropertyFile(dbUserName, dbPassword, m3UserName, m3Password,
				propertiesMap);
	}

	public void writeDBPropertyFile(String dbUserName, String dbPassword,
			String m3UserName, String m3Password,
			HashMap<String, String> propertiesMap) {
		try {
			// Create file

			FileWriter fstream = new FileWriter(
					"C:\\Servers\\ShopFloorAdmin.properties");
			BufferedWriter out = new BufferedWriter(fstream);
			int count = 0;
			if (propertiesMap.get("JDBC_DBSERVER") == null
					|| propertiesMap.get("JDBC_DBSERVER").trim().isEmpty()
					|| propertiesMap.get("JDBC_PORT") == null
					|| propertiesMap.get("JDBC_PORT").trim().isEmpty()
					|| propertiesMap.get("JDBC_DB") == null
					|| propertiesMap.get("JDBC_DB").trim().isEmpty()
					|| propertiesMap.get("JDBC_DRIVER") == null
					|| propertiesMap.get("JDBC_DRIVER").trim().isEmpty()) {

				System.out.println("Database information is not found ");
				count++;
			} else {
				String url = "jdbc:sqlserver://"
						+ propertiesMap.get("JDBC_DBSERVER") + ":"
						+ propertiesMap.get("JDBC_PORT") + ";databaseName="
						+ propertiesMap.get("JDBC_DB");
				out.write("jdbc.overrideDriverClassName="
						+ propertiesMap.get("JDBC_DRIVER") + "\n");
				out.write("jdbc.overrideUrl=" + url + "\n");
				out.write("jdbc.overrideUsername=" + dbUserName + "\n");
				out.write("jdbc.overridePassword=" + dbPassword + "\n");

				out.write("jdbc.overrideHost="
						+ propertiesMap.get("JDBC_DBSERVER") + "\n");
				out.write("jdbc.overridePort=" + propertiesMap.get("JDBC_PORT")
						+ "\n");
				out.write("jdbc.overrideDb=" + propertiesMap.get("JDBC_DB")
						+ "\n");
			}

			if (propertiesMap.get("M3_ENVT") == null
					|| propertiesMap.get("M3_ENVT").trim().isEmpty()) {
				System.out.println("M3 Environment information is not found");
				count++;
			} else {
				out.write("M3.ENVIRONMENT=" + propertiesMap.get("M3_ENVT")
						+ "\n");
			}

			// for reading application properties
			if (propertiesMap.get("M3_HOST") == null
					|| propertiesMap.get("M3_HOST").trim().isEmpty()
					|| propertiesMap.get("M3_PORT") == null
					|| propertiesMap.get("M3_PORT").trim().isEmpty()
					|| propertiesMap.get("M3_PATH") == null
					|| propertiesMap.get("M3_PATH").trim().isEmpty()) {

				System.out.println("M3 information is not found ");
				count++;
			} else {
				String m3url = "http://" + propertiesMap.get("M3_HOST").trim()
						+ ":" + propertiesMap.get("M3_PORT").trim() + "/"
						+ propertiesMap.get("M3_PATH").trim();

				out.write("M3.ENDPOINTURL=" + m3url + "\n");
				out.write("m3.server=" + propertiesMap.get("M3_HOST") + "\n");
			}

            if(propertiesMap.get("M3_API_PORT") == null ||
                    propertiesMap.get("M3_API_PORT").trim().isEmpty()) {
                System.out.println("M3 API port is not found ");
                count++;
            } else {
                out.write("m3.port=" + propertiesMap.get("M3_API_PORT") + "\n");
            }

			if (propertiesMap.get("M3_NAMESPACE") == null
					|| propertiesMap.get("M3_NAMESPACE").trim().isEmpty()) {
				System.out.println("M3 Namespace information is not found ");
				count++;
			} else {
				out.write("NAMESPACE=" + propertiesMap.get("M3_NAMESPACE")
						+ "\n");
			}

			if (propertiesMap.get("M3_PREFIX") == null
					|| propertiesMap.get("M3_PREFIX").trim().isEmpty()) {
				System.out.println("M3 Prefix information is not found ");
				count++;
			} else {
				out.write("MWS_HEADER_NS_PREFIX="
						+ propertiesMap.get("M3_PREFIX") + "\n");
			}
			if (propertiesMap.get("M3_HEADER") == null
					|| propertiesMap.get("M3_HEADER").trim().isEmpty()) {
				System.out.println("M3 Header information is not found ");
				count++;
			} else {
				out.write("MWS_HEADER_NS_URI=" + propertiesMap.get("M3_HEADER")
						+ "\n");
			}

			if (m3UserName == null || m3UserName.trim().isEmpty()) {
				System.out.println("M3 User Name is not found");
				count++;
			} else {
				out.write("M3.USERNAME=" + m3UserName + "\n");
			}

			if (m3Password == null || m3Password.trim().isEmpty()) {
				System.out.println("M3 Password  is not found");
				count++;
			} else {
				out.write("M3.PASSWORD=" + m3Password + "\n");
			}

			propertiesMap.put("LDAP_HOST_2", "tordcp02");
			propertiesMap.put("LDAP_PORT_2", "389");
			if (propertiesMap.get("LDAP_HOST") == null
					|| propertiesMap.get("LDAP_HOST").trim().isEmpty()
					|| propertiesMap.get("LDAP_PORT") == null
					|| propertiesMap.get("LDAP_PORT").trim().isEmpty()
					||propertiesMap.get("LDAP_HOST_2") == null
					|| propertiesMap.get("LDAP_HOST_2").trim().isEmpty()
					|| propertiesMap.get("LDAP_PORT_2") == null
					|| propertiesMap.get("LDAP_PORT_2").trim().isEmpty()
					|| propertiesMap.get("LDAP_SECURITY") == null
					|| propertiesMap.get("LDAP_SECURITY").trim().isEmpty()) {

				System.out.println("LDAP information is not found");
				count++;
			} else {
				String ldapUrl = propertiesMap.get("LDAP_HOST").trim() + ":"
						+ propertiesMap.get("LDAP_PORT") + "/";
				
				String ldapUrlSecond = propertiesMap.get("LDAP_HOST_2").trim() + ":"
						+ propertiesMap.get("LDAP_PORT_2") + "/";
				
				out.write("ldap.providerURL=" + ldapUrl + "\n");
				
				out.write("ldap.providerURL.second=" + ldapUrlSecond + "\n");

				out.write("ldap.security.principle="
						+ propertiesMap.get("LDAP_SECURITY") + "\n");
			}

			if (propertiesMap.get("COMP") == null
					|| propertiesMap.get("COMP").trim().isEmpty()) {
				System.out.println("Company information is not found ");
				count++;
			} else {
				out.write("COMPANY=" + propertiesMap.get("COMP") + "\n");
			}

			if (propertiesMap.get("PMS070MI_WRITE_PRIORITY") == null
					|| propertiesMap.get("PMS070MI_WRITE_PRIORITY").trim()
							.isEmpty()) {
				System.out
						.println("Write Priority for PMS070MI information is missing");
				count++;
			} else {
				out.write("PMS070MI.WRITE.PRIORITY="
						+ propertiesMap.get("PMS070MI_WRITE_PRIORITY") + "\n");
			}

			if (propertiesMap.get("PMS050MI_WRITE_PRIORITY") == null
					|| propertiesMap.get("PMS050MI_WRITE_PRIORITY").trim()
							.isEmpty()) {
				System.out
						.println("Write Priority for PMS050MI information is not found");
				count++;
			} else {
				out.write("PMS050MI.WRITE.PRIORITY="
						+ propertiesMap.get("PMS050MI_WRITE_PRIORITY") + "\n");
			}

			if (propertiesMap.get("LIS200_WRITE_PRIORITY") == null
					|| propertiesMap.get("LIS200_WRITE_PRIORITY").trim()
							.isEmpty()) {
				System.out
						.println("Write Priority for LIS200 information is not found");
				count++;
			} else {
				out.write("LIS200.WRITE.PRIORITY="
						+ propertiesMap.get("LIS200_WRITE_PRIORITY") + "\n");
			}

			if (propertiesMap.get("PMS130_WRITE_PRIORITY") == null
					|| propertiesMap.get("PMS130_WRITE_PRIORITY").trim()
							.isEmpty()) {
				System.out
						.println("Write Priority for PMS130 information is not found");
				count++;
			} else {
				out.write("PMS130.WRITE.PRIORITY="
						+ propertiesMap.get("PMS130_WRITE_PRIORITY") + "\n");
			}

			if (propertiesMap.get("WEBSERVICES_WITH_TRANS_TYPE") == null
					|| propertiesMap.get("WEBSERVICES_WITH_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Web Services with Trans Type information is not found ");
				count++;
			} else {
				out.write("WEBSERVICES.WITH.TRANS.TYPE="
						+ propertiesMap.get("WEBSERVICES_WITH_TRANS_TYPE")
						+ "\n");
			}

			if (propertiesMap.get("WEBSERVICES_COMPARABLE_FIELDS") == null
					|| propertiesMap.get("WEBSERVICES_COMPARABLE_FIELDS")
							.trim().isEmpty()) {
				System.out
						.println("Web Services Comparable Fields information is not found ");
				count++;
			} else {
				out.write("WEBSERVICES.COMPARABLE.FIELDS="
						+ propertiesMap.get("WEBSERVICES_COMPARABLE_FIELDS")
						+ "\n");
			}

			if (propertiesMap.get("MHS850MI_TRANS_TYPE") == null
					|| propertiesMap.get("MHS850MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Trans Type for MHS850MI information is not found ");
				count++;
			} else {
				out.write("MHS850MI.TRANS.TYPE="
						+ propertiesMap.get("MHS850MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("MMZ428MI_TRANS_TYPE") == null
					|| propertiesMap.get("MMZ428MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Trans Type for MMZ428MI information is not found ");
				count++;
			} else {
				out.write("MMZ428MI.TRANS.TYPE="
						+ propertiesMap.get("MMZ428MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("MWZ422MI_TRANS_TYPE") == null
					|| propertiesMap.get("MWZ422MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Trans Type for MWZ422MI information is not found ");
				count++;
			} else {
				out.write("MWZ422MI.TRANS.TYPE="
						+ propertiesMap.get("MWZ422MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("PMS050MI_TRANS_TYPE") == null
					|| propertiesMap.get("PMS050MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Trans Type for PMS050MI information is not found ");
				count++;
			} else {
				out.write("PMS050MI.TRANS.TYPE="
						+ propertiesMap.get("PMS050MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("PMS130_TRANS_TYPE") == null
					|| propertiesMap.get("PMS130_TRANS_TYPE").trim().isEmpty()) {
				System.out
						.println("Trans Type for PMS130 information is not found ");
				count++;
			} else {
				out.write("PMS130.TRANS.TYPE="
						+ propertiesMap.get("PMS130_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("PPS320_TRANS_TYPE") == null
					|| propertiesMap.get("PPS320_TRANS_TYPE").trim().isEmpty()) {
				System.out
						.println("Trans Type for PPS320 information is not found ");
				count++;
			} else {
				out.write("PPS320.TRANS.TYPE="
						+ propertiesMap.get("PPS320_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("PPY001MI_TRANS_TYPE") == null
					|| propertiesMap.get("PPY001MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Trans Type for PPY001MI information is not found ");
				count++;
			} else {
				out.write("PPY001MI.TRANS.TYPE="
						+ propertiesMap.get("PPY001MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_MHS850MI_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_MHS850MI_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for MHS850MI information is not found ");
				count++;
			} else {
				out.write("MHS850MI.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_MHS850MI_BOLTON")
						+ "\n");
			}

			if (propertiesMap.get("MMZ428MI_TRANS_TYPE") == null
					|| propertiesMap.get("MMZ428MI_TRANS_TYPE").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for MMZ428MI information is not found ");
				count++;
			} else {
				out.write("MMZ428MI.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("MMZ428MI_TRANS_TYPE") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_MWZ422MI_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_MWZ422MI_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for MWZ422MI information is not found ");
				count++;
			} else {
				out.write("MWZ422MI.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_MWZ422MI_BOLTON")
						+ "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PMS050MI_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_PMS050MI_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for PMS050MI information is not found ");
				count++;
			} else {
				out.write("PMS050MI.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_PMS050MI_BOLTON")
						+ "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PMS130_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_PMS130_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for PMS130 information is not found ");
				count++;
			} else {
				out.write("PMS130.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_PMS130_BOLTON") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PPS320_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_PPS320_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for PPS320 information is not found ");
				count++;
			} else {
				out.write("PPS320.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_PPS320_BOLTON") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PPY001MI_BOLTON") == null
					|| propertiesMap.get("KEY_FIELDS_PPY001MI_BOLTON").trim()
							.isEmpty()) {
				System.out
						.println("Bolt On Key Field for PPY001MI information is not found ");
				count++;
			} else {
				out.write("PPY001MI.KEY.FIELDS.BOLT.ON="
						+ propertiesMap.get("KEY_FIELDS_PPY001MI_BOLTON")
						+ "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_MHS850MI_M3") == null
					|| propertiesMap.get("KEY_FIELDS_MHS850MI_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for MHS850MI information is not found ");
				count++;
			} else {
				out.write("MHS850MI.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_MHS850MI_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_MMZ428MI_M3") == null
					|| propertiesMap.get("KEY_FIELDS_MMZ428MI_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for MMZ428MI information is not found ");
				count++;
			} else {
				out.write("MMZ428MI.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_MMZ428MI_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_MWZ422MI_M3") == null
					|| propertiesMap.get("KEY_FIELDS_MWZ422MI_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for MWZ422MI information is not found ");
				count++;
			} else {
				out.write("MWZ422MI.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_MWZ422MI_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PMS050MI_M3") == null
					|| propertiesMap.get("KEY_FIELDS_PMS050MI_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for PMS050MI information is not found ");
				count++;
			} else {
				out.write("PMS050MI.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_PMS050MI_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PMS130_M3") == null
					|| propertiesMap.get("KEY_FIELDS_PMS130_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for PMS130 information is not found ");
				count++;
			} else {
				out.write("PMS130.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_PMS130_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PPS320_M3") == null
					|| propertiesMap.get("KEY_FIELDS_PPS320_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for PPS320 information is not found ");
				count++;
			} else {
				out.write("PPS320.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_PPS320_M3") + "\n");
			}

			if (propertiesMap.get("KEY_FIELDS_PPY001MI_M3") == null
					|| propertiesMap.get("KEY_FIELDS_PPY001MI_M3").trim()
							.isEmpty()) {
				System.out
						.println("M3 Key Field for PPY001MI information is not found ");
				count++;
			} else {
				out.write("PPY001MI.KEY.FIELDS.M3="
						+ propertiesMap.get("KEY_FIELDS_PPY001MI_M3") + "\n");
			}
			
			/*if (StringUtils.trimToNull(propertiesMap.get("VERSION")) == null) {
				System.out.println("Version Property is not found");
				count++;
			} else {
				out.write("VERSION=" + propertiesMap.get("VERSION")
						+ "\n");
			}*/
			
			if (StringUtils.trimToNull(propertiesMap.get("PAB_DIST_NAME")) == null) {
				System.out
						.println("Distinguished name of the Public Address Book is not found");
				count++;
			} else {
				out.write("ldap.pabDC=" + propertiesMap.get("PAB_DIST_NAME")
						+ "\n");
			}

            out.write("WELCOME_MESSAGE_TEXT=" + propertiesMap.get("WELCOME_MESSAGE_TEXT")
                    + "\n");
            if (StringUtils.trimToNull(propertiesMap.get("WELCOME_MESSAGE_TYPE")) == null) {
                System.out.println("WELCOME_MESSAGE_TYPE Property is not found");
                count++;
            } else {
                out.write("WELCOME_MESSAGE_TYPE=" + propertiesMap.get("WELCOME_MESSAGE_TYPE")
                        + "\n");
            }
            if(propertiesMap.get("DynamicMessage_SFSA") == null || ((String)propertiesMap.get("DynamicMessage_SFSA")).trim().isEmpty())
            {
                System.out.println("DynamicMessagePath is not found");
                count++;
            } else
            {
                out.write((new StringBuilder()).append("DynamicMessage_SFSA=").append((String)propertiesMap.get("DynamicMessage_SFSA")).append("\n").toString());
            }
			if (count > 0) {
				System.out.println(" Property is missing/invalid  ");
				System.exit(0);
			}
			out.flush();
			fstream.flush();
			out.close();
			fstream.close();
			// Close the output stream
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Written successfully");
	}

	public DataSource getDs1() {
		return ds1;
	}

	public void setDs1(DataSource ds1) {
		this.ds1 = ds1;
		this.jdbcTemplate = new JdbcTemplate(ds1);
	}

	/**
	 * Safely closes cods1nnection
	 * 
	 * @param connection
	 *            Connection to close. Can be null.
	 */
	public void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * Get the Connection.
	 * 
	 * @return Connection
	 * @throws SQLException
	 */

	public Connection getConnection() throws SQLException {
		return ds1.getConnection();
	}

}
