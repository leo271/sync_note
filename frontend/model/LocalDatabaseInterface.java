package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.db_enum.*;

public class LocalDatabaseInterface {
  // シングルトンインスタンスの定義
  private static LocalDatabaseInterface instance;
  private static final Logger logger = Logger.getLogger(LocalDatabaseInterface.class.getName());

  private static final String DB_DRIVER = "jdbc:sqlite:database.db";
  private static final String UPSERT = "INSERT OR REPLACE INTO ";
  private static final String DELETE = "DELETE FROM ";

  // プライベートコンストラクタ
  private LocalDatabaseInterface() {}

  // シングルトンインスタンスを取得するメソッド
  public static synchronized LocalDatabaseInterface getInstance() {
    if (instance == null) {
      instance = new LocalDatabaseInterface();
    }
    return instance;
  }

  // データベースに接続するメソッド
  private Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_DRIVER);
  }

  // クエリを構築するメソッド
  private String buildSearchQuery(String base, Table table, String[] keys) {
    if (keys == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " WHERE ";
    var queryBuilder = new StringBuilder();
    for (var key : keys) {
      if (queryBuilder.length() > 0) {
        queryBuilder.append(" AND ");
      }
      queryBuilder.append(key + " = ?");
    }
    return baseQuery + queryBuilder.toString();
  }

  // 挿入クエリを構築するメソッド
  private String buildUpdateQuery(String base, Table table, String[] keys) {
    if (keys == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " (";
    var queryBuilder = new StringBuilder();
    for (var key : keys) {
      if (queryBuilder.length() > 0) {
        baseQuery += ", ";
        queryBuilder.append(", ");
      }
      baseQuery += key;
      queryBuilder.append("?");
    }
    return baseQuery + ") VALUES (" + queryBuilder.toString() + ")";
  }

  private <T> ArrayList<T> executeSearch(String query, String[] args, Function<JSON, T> parser) {
    ArrayList<T> list = new ArrayList<>();

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < args.length; i++) {
        statement.setString(i + 1, args[i]);
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(parser.apply(fromResultSet(resultSet)));
        }
      }
      return list;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return null;
    }
  }

  private String executeUpdate(String query, String[] args) {
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < args.length; i++) {
        statement.setString(i + 1, args[i]);
      }
      statement.executeUpdate();
      return "Success";
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return "Failed";
    }
  }

  // ResultSetからJSONに変換するメソッド
  private JSON fromResultSet(ResultSet resultSet) throws SQLException {
    var metadata = resultSet.getMetaData();
    var columns = metadata.getColumnCount();
    var result = new JSON();
    for (int i = 1; i <= columns; i++) {
      result.put(metadata.getColumnName(i), resultSet.getString(i));
    }
    return result;
  }

  // 検索するメソッド
  public <T> ArrayList<T> search(Table table, JSON query, Function<JSON, T> parser)
      throws SQLException {
    if (!DB.isValidValue(query))
      throw new SQLException("Invalid value for" + query.toString());
    var keys = query.keySet().toArray(new String[0]);
    String[] values = new String[keys.length];
    for (int i = 0; i < keys.length; i++) {
      values[i] = query.get(keys[i]).toString();
    }
    String qstring = buildSearchQuery("SELECT * FROM ", table, keys);
    return executeSearch(qstring, values, parser);
  }

  // データベースを更新するメソッド
  private int update(String query, Table table, ArrayList<JSON> data) {
    for (var json : data) {
      if (!DB.isValidValue(json))
        return 500;
      var keys = json.keySet().toArray(String[]::new);
      var values = new String[keys.length];
      for (int i = 0; i < keys.length; i++) {
        values[i] = json.get(keys[i]).toString();
      }

      String q = buildUpdateQuery(query, table, keys);
      var update = executeUpdate(q, values);
      if (update.equals("Failed")) {
        return 500;
      }
    }
    return 200;
  }

  // 単一のJSONデータをアップサートするメソッド
  public int upsert(Table table, JSON json) {
    return update(UPSERT, table, new ArrayList<JSON>(Arrays.asList(json)));
  }

  // 複数のJSONデータをアップサートするメソッド
  public int upsert(Table table, ArrayList<JSON> data) {
    return update(UPSERT, table, data);
  }

  // 単一のJSONデータを削除するメソッド
  public int delete(Table table, JSON json) {
    return update(DELETE, table, new ArrayList<JSON>(Arrays.asList(json)));
  }

  // 複数のJSONデータを削除するメソッド
  public int delete(Table table, ArrayList<JSON> data) {
    return update(DELETE, table, data);
  }
}
