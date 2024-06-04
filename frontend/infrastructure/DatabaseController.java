package infrastructure;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import backend.model.Column;
import backend.model.ColumnType;
import backend.model.DB;
import backend.model.Table;

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

  private String buildQuery(Table table, Column col) {
    return "SELECT * FROM " + table.name + " WHERE " + col.name + " = ?";
  }

  public String searchSingle(Table table, Column col, String value) throws SQLException {
    if (!DB.isValidValue(col, value))
      throw new SQLException("Invalid value for column " + col.name + ": " + value);
    String query = buildQuery(table, col);

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, value);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString(col.name);
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
    return null;
  }

  public ArrayList<String> searchMulti(Table table, Column col, String value) throws SQLException {
    if (!DB.isValidValue(col, value))
      throw new SQLException("Invalid value for column " + col.name + ": " + value);
    ArrayList<String> list = new ArrayList<>();
    String query = buildQuery(table, col);

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {

      statement.setString(1, value);

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(resultSet.getString(col.name));
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
    return list;
  }

  public void insert(Table table, ArrayList<Column> columns) {
    String query =
        "INSERT INTO " + table.name + " VALUES (" + "?,".repeat(columns.size() - 1) + "?)";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < columns.size(); i++) {
        Column column = columns.get(i);
        if (column.type == ColumnType.INT)
          statement.setInt(i + 1, Integer.parseInt(column.value));
        else
          statement.setString(i + 1, column.value);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
    }
  }
}
