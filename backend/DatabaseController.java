package backend;

import backend.model.DBCols;
import backend.model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {
  private static DatabaseController instance;
  private static final Logger logger = Logger.getLogger(DatabaseController.class.getName());

  private DatabaseController() {}

  public static synchronized DatabaseController getInstance() {
    if (instance == null) {
      instance = new DatabaseController();
    }
    return instance;
  }

  private Connection connect() throws SQLException {
    return DriverManager.getConnection("jdbc:sqlite:database.db");
  }

  private String buildQuery(Table table, DBCols col) {
    return "SELECT * FROM " + table.getName() + " WHERE " + col.getName() + " = ?";
  }

  public String searchSingle(Table table, DBCols col, String value) {
    String query = buildQuery(table, col);

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, value);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString(col.getName());
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
    return null;
  }

  public ArrayList<String> searchMulti(Table table, DBCols col, String value) {
    ArrayList<String> list = new ArrayList<>();
    String query = buildQuery(table, col);

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, value);

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(resultSet.getString(col.getName()));
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
    return list;
  }

  public void insert(Table table, DBCols.DBStr values) {
    String[] valueArray = values.str.split(",");
    String query =
        "INSERT INTO " + table.getName() + " VALUES (" + "?,".repeat(valueArray.length - 1) + "?)";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < valueArray.length; i++) {
        String[] element = valueArray[i].split("\t");
        if (element[1] == "s")
          statement.setString(i + 1, element[0]);
        else if (element[1] == "d")
          statement.setInt(i + 1, element[0].charAt(0));
        statement.setString(i + 1, valueArray[i]);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
  }
}
