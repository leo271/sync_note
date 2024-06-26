package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseClient {
  private static DatabaseClient instance;
  private static final Logger logger = Logger.getLogger(DatabaseClient.class.getName());
  private static final char US = '\u001f';
  private static final char RS = '\u001e';
  private static final String DB_URL = "jdbc:sqlite:database.db";

  private DatabaseClient() {}

  public static synchronized DatabaseClient getInstance() {
    if (instance == null) {
      instance = new DatabaseClient();
    }
    return instance;
  }

  private Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  public String executeQuery(String query) throws SQLException {
    try (Connection connection = connect();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);) {
      var metadata = rs.getMetaData();
      var result = new StringBuilder();
      while (rs.next()) {
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
          result.append(rs.getString(i)).append(US);
        }
        result.append(RS);
      }
      return result.toString();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error." + e.getMessage());
    }
    return null;
  }

  public boolean executeUpdate(String query) {
    try (Connection connection = connect(); Statement stmt = connection.createStatement();) {
      stmt.executeUpdate(query);
      return true;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error." + e.getMessage());
      return false;
    }
  }
}
