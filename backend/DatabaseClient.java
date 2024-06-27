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

  public boolean testConnection() {
    try (Connection connection = connect()) {
      if (connection != null && !connection.isClosed()) {
        System.out.println("Connection successful!");
        return true;
      } else {
        System.out.println("Connection failed.");
        return false;
      }
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  private Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  public String executeQuery(String query) throws SQLException {
    if (!testConnection()) {
      System.out.println("Connection test failed.");
      return null;
    }
    query = query.trim() + ";";

    try (Connection connection = connect(); Statement stmt = connection.createStatement();) {
      ResultSet rs = stmt.executeQuery(query);
      var metadata = rs.getMetaData();
      int columnCount = metadata.getColumnCount();
      var result = new StringBuilder();

      while (rs.next()) {
        for (int i = 1; i <= columnCount; i++) {
          String columnValue = rs.getString(i);
          result.append(columnValue).append(US);
        }
        result.append(RS);
      }

      // 取得した結果をデバッグ出力
      String resultString = result.toString();

      return resultString;

    } catch (SQLException e) {
      System.out.println("Error executing query: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
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
