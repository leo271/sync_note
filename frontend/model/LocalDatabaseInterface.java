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
  private static final String SELECT = "SELECT * FROM ";

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
  private String buildSearchQuery(String base, Table table, JSON keys) {
    if (keys == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " WHERE ";
    var queryBuilder = new StringBuilder();
    for (var key : keys) {
      if (queryBuilder.length() > 0) {
        queryBuilder.append(" AND ");
      }
      queryBuilder.append(key.getKey().name + " = ?");
    }
    return baseQuery + queryBuilder.toString();
  }

  // 挿入クエリを構築するメソッド
  private String buildUpdateQuery(String base, Table table, JSON query) {
    if (query == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " (";
    var queryBuilder = new StringBuilder();
    for (var entry : query) {
      if (queryBuilder.length() > 0) {
        baseQuery += ", ";
        queryBuilder.append(", ");
      }
      baseQuery += entry.getKey().name;
      queryBuilder.append("?");
    }
    return baseQuery + ") VALUES (" + queryBuilder.toString() + ")";
  }

  private <T> ArrayList<T> executeSearch(String query, JSON args, Function<JSON, T> parser) {
    System.out.println("LOCAL:\t" + query + "\tquery:\t" + (args == null ? null : args.toString()));
    ArrayList<T> list = new ArrayList<>();

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      if (args != null) {
        for (int i = 0; i < args.size(); i++) {
          if (args.get(i).getKey().type == ColumnType.INT)
            statement.setInt(i + 1, Integer.parseInt(args.get(i).getValue()));
          else
            statement.setString(i + 1, args.get(i).getValue().toString());
        }
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(parser.apply(fromResultSet(resultSet)));
        }
      }
      return list;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "D" + e.getMessage());
      logger.log(Level.SEVERE, "Database error." + e.getMessage());
      return null;
    }
  }

  private String executeUpdate(String query, JSON args) {
    System.out.println("LOCAL:\t" + query + "\tquery:\t" + (args == null ? null : args.toString()));
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < args.size(); i++) {
        if (args.get(i).getKey().type == ColumnType.INT)
          statement.setInt(i + 1, Integer.parseInt(args.get(i).getValue()));
        else
          statement.setString(i + 1, args.get(i).getValue());
      }
      statement.executeUpdate();
      return "Success";
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error." + e.getMessage());
      for (var trace : e.getStackTrace()) {
        logger.log(Level.SEVERE, trace.toString());
      }
      return "Failed";
    }
  }

  // ResultSetからJSONに変換するメソッド
  private JSON fromResultSet(ResultSet resultSet) throws SQLException {
    var metadata = resultSet.getMetaData();
    var columns = metadata.getColumnCount();
    var result = new JSON();
    for (int i = 1; i <= columns; i++) {
      if (DB.columnByName(metadata.getColumnName(i)) == null || resultSet.getString(i) == null)
        continue;
      result.put(DB.columnByName(metadata.getColumnName(i)), resultSet.getString(i));
    }
    return result;
  }

  // 検索するメソッド
  public <T> ArrayList<T> search(Table table, JSON query, Function<JSON, T> parser)
      throws SQLException {
    if (!DB.isValidValue(query))
      throw new SQLException("Invalid value for" + query.toString());

    String qstring = buildSearchQuery(SELECT, table, query);
    return executeSearch(qstring, query, parser);
  }

  // データベースを更新するメソッド
  private int update(String query, Table table, ArrayList<JSON> data) {
    for (var json : data) {
      if (!DB.isValidValue(json))
        return 500;
      String q = buildUpdateQuery(query, table, json);
      var update = executeUpdate(q, json);
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
    if (!DB.isValidValue(json))
      return 500;
    String q = buildSearchQuery(DELETE, table, json);
    var update = executeUpdate(q, json);
    if (update.equals("Failed")) {
      return 500;
    }
    return 200;
  }

  // 複数のJSONデータを削除するメソッド
  public int delete(Table table, ArrayList<JSON> data) {
    for (var json : data) {
      var res = delete(table, json);
      if (res != 200) {
        return res;
      }
    }
    return 200;
  }
}
