package backend;

import backend.model.DBCols;
import backend.model.Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseController {
  private DatabaseController() {}

  public static DatabaseController instance = new DatabaseController();

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

      // プレースホルダーにクエリの値をセットすることで、適切なエスケープ処理を行い、SQLインジェクションを防ぐことができる
      statement.setString(1, value);

      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getString(col.getName());
        }
      }
    } catch (SQLException e) {
      System.err.println("Database error.");
      e.printStackTrace();
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
      System.err.println("Database error.");
      e.printStackTrace();
    }
    return list;
  }

  public void insert(Table table, DBCols cols, String values) {
    String query = "INSERT INTO " + table.getName() + " (" + cols.getName() + ") VALUES (?)";
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, values);
      statement.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Database error.");
      e.printStackTrace();
    }
  }
}
