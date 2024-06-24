package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Map.Entry;
// import infrastructure.NetworkClient;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.db_enum.*;

public class DatabaseInterface {
  // シングルトンインスタンスの定義
  private static DatabaseInterface localInstance;
  private static DatabaseInterface remoteInstance;
  private static final Logger logger = Logger.getLogger(DatabaseInterface.class.getName());

  private String TYPE;
  private static final String LOCAL = "local";
  private static final String REMOTE = "remote";
  private static final String LOCAL_DB_DRIVER = "jdbc:sqlite:database.db";
  private static final String UPSERT = "INSERT OR REPLACE INTO ";
  private static final String DELETE = "DELETE FROM ";

  // プライベートコンストラクタ
  private DatabaseInterface(String type) {
    this.TYPE = type;
  }

  // シングルトンインスタンスを取得するメソッド
  public static synchronized DatabaseInterface getLocal() {
    if (localInstance == null) {
      localInstance = new DatabaseInterface(LOCAL);
    }
    return localInstance;
  }

  public static synchronized DatabaseInterface getRemote() {
    if (remoteInstance == null) {
      remoteInstance = new DatabaseInterface(REMOTE);
    }
    return remoteInstance;
  }

  // データベースに接続するメソッド
  private Connection connectLocal() throws SQLException {
    return DriverManager.getConnection(LOCAL_DB_DRIVER);
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

  // private String buildSearchQuery(String base, Table table, Entry<String, String>[] entries) {
  // if (entries == null) {
  // return base + table.name;
  // }
  // var baseQuery = base + table.name + " WHERE ";
  // var queryBuilder = new StringBuilder();
  // for (var entry : entries) {
  // if (queryBuilder.length() > 0) {
  // queryBuilder.append(" AND ");
  // }
  // queryBuilder.append(entry.getKey() + " = " + entry.getValue());
  // }
  // return baseQuery + queryBuilder.toString();
  // }

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

  // private String buildUpdateQuery(String base, Table table, Entry<String, String>[] entries) {
  // if (entries == null) {
  // return base + table.name;
  // }
  // var baseQuery = base + table.name + " (";
  // var queryBuilder = new StringBuilder();
  // for (var entry : entries) {
  // if (queryBuilder.length() > 0) {
  // baseQuery += ", ";
  // queryBuilder.append(", ");
  // }
  // baseQuery += entry.getKey();
  // queryBuilder.append(entry.getValue());
  // }
  // return baseQuery + ") VALUES (" + queryBuilder.toString() + ")";
  // }

  private <T> ArrayList<T> executeQueryLocal(String query, String[] args, Function<JSON, T> parser,
      boolean isMulti) {
    ArrayList<T> list = new ArrayList<>();

    try (Connection connection = connectLocal();
        PreparedStatement statement = connection.prepareStatement(query)) {
      for (int i = 0; i < args.length; i++) {
        statement.setString(i + 1, args[i]);
      }

      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(parser.apply(fromResultSet(resultSet)));
          if (!isMulti)
            break;
        }
      }
      return list;
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return null;
    }
  }

  private String executeUpdateLocal(String query, String[] args) {
    try (Connection connection = connectLocal();
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

  // private String[] executeUpdateRemote(String[] queries) {
  // var res = NetworkClient.excuteQueries(queries, "UPDATE");
  // if (res.success()) {
  // return res.message;
  // } else {
  // return null;
  // }
  // }

  // private <T> ArrayList<ArrayList<T>> executeQueryRemote(String[] queries,
  // Function<String, T> parser) {
  // var res = NetworkClient.excuteQueries(queries, "QUERY");
  // if (res.success()) {
  // var list = new ArrayList<ArrayList<T>>();
  // for (var json : res.message) {
  // var ilist = new ArrayList<T>();
  // for (var j : json.split(RS + "")) {
  // ilist.add(parser.apply(j));
  // }
  // list.add(ilist);
  // }
  // return list;
  // } else {
  // return null;
  // }
  // }

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
  // TODO: サーバー側の処理を実装する
  private <T> ArrayList<T> search(Table table, JSON query, Function<JSON, T> parser,
      boolean isMulti) throws SQLException {
    if (!DB.isValidValue(query))
      throw new SQLException("Invalid value for" + query.toString());
    var keys = query.keySet().toArray(new String[0]);
    String[] values = new String[keys.length];
    for (int i = 0; i < keys.length; i++) {
      values[i] = query.get(keys[i]).toString();
    }
    String qstring = buildSearchQuery("SELECT * FROM ", table, keys);
    if (TYPE == LOCAL)
      return executeQueryLocal(qstring, values, parser, isMulti);
    else
      return null;
  }

  public <T> T searchSingle(Table table, JSON query, Function<JSON, T> parser) {
    try {
      var result = search(table, query, parser, false);
      return result.isEmpty() ? null : result.get(0);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return null;
    }
  }

  public <T> ArrayList<T> searchMulti(Table table, JSON query, Function<JSON, T> parser) {
    try {
      return search(table, query, parser, true);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Database error.", e);
      return null;
    }
  }

  // データベースを更新するメソッド
  // TODO: サーバー側の処理を実装する
  private int update(String query, Table table, ArrayList<JSON> data) {
    if (TYPE.equals(LOCAL)) {
      for (var json : data) {
        if (!DB.isValidValue(json))
          return 500;
        var keys = (String[]) json.keySet().toArray();
        var values = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
          values[i] = json.get(keys[i]).toString();
        }
        String q = buildUpdateQuery(query, table, keys);
        var update = executeUpdateLocal(q, values);
        if (update.equals("Failed")) {
          return 500;
        }
      }
      return 200;
    } else {
      return 500;
    }

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
