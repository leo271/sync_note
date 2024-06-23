package backend;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {
  private static DatabaseController instance;
  private static final Logger logger = Logger.getLogger(DatabaseController.class.getName());
  private static final char US = '\u001f';
  private static final char RS = '\u001e';
  private static final String DB_URL = "jdbc:sqlite:database.db";

  private DatabaseController() {}

  public static synchronized DatabaseController getInstance() {
    if (instance == null) {
      instance = new DatabaseController();
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
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
    return null;
  }

  public boolean executeUpdate(String query) {
    try (Connection connection = connect(); Statement stmt = connection.createStatement();) {
      stmt.executeUpdate(query);
      return true;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return false;
    }
  }
}
