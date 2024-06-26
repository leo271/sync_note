package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import infrastructure.NetworkClient;
import model.db_enum.*;

public class RemoteDatabaseInterface {
  // シングルトンインスタンスの定義
  private static RemoteDatabaseInterface instance;
  private static final Logger logger = Logger.getLogger(RemoteDatabaseInterface.class.getName());

  private static final String UPSERT = "INSERT OR REPLACE INTO ";
  private static final String DELETE = "DELETE FROM ";
  private static final char RS = '\u001E';

  // プライベートコンストラクタ
  private RemoteDatabaseInterface() {}

  // シングルトンインスタンスを取得するメソッド
  public static synchronized RemoteDatabaseInterface getInstance() {
    if (instance == null) {
      instance = new RemoteDatabaseInterface();
    }
    return instance;
  }

  private String buildSearchQuery(String base, Table table, Entry<String, String>[] entries,
      boolean isLike) {
    if (entries == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " WHERE ";
    var queryBuilder = new StringBuilder();
    for (var entry : entries) {
      if (queryBuilder.length() > 0) {
        queryBuilder.append(" AND ");
      }
      if (isLike) {
        queryBuilder.append(entry.getKey() + " LIKE '%" + entry.getValue() + "%'");
      } else {
        queryBuilder.append(entry.getKey() + " = '" + entry.getValue() + "'");
      }
    }
    return baseQuery + queryBuilder.toString();
  }

  // 挿入クエリを構築するメソッド
  private String buildUpdateQuery(String base, Table table, Entry<String, String>[] entries) {
    if (entries == null) {
      return base + table.name;
    }
    var baseQuery = base + table.name + " (";
    var queryBuilder = new StringBuilder();
    for (var entry : entries) {
      if (queryBuilder.length() > 0) {
        baseQuery += ", ";
        queryBuilder.append(", ");
      }
      baseQuery += entry.getKey();
      queryBuilder.append(entry.getValue());
    }
    return baseQuery + ") VALUES (" + queryBuilder.toString() + ")";
  }

  private <T> ArrayList<ArrayList<T>> executeSearch(String[] queries, Function<String, T> parser) {
    var res = NetworkClient.excuteQueries(queries, "QUERY");
    if (res.success()) {
      var list = new ArrayList<ArrayList<T>>();
      for (var json : res.message) {
        var ilist = new ArrayList<T>();
        for (var j : json.split(RS + "")) {
          ilist.add(parser.apply(j));
        }
        list.add(ilist);
      }
      return list;
    } else {
      logger.log(Level.SEVERE, "Database error.", res.message[0]);
      return null;
    }
  }

  private String[] executeUpdate(String[] queries) {
    var res = NetworkClient.excuteQueries(queries, "UPDATE");
    if (res.success()) {
      return res.message;
    } else {
      logger.log(Level.SEVERE, "Database error.", res.message[0]);
      return null;
    }
  }

  // 検索するメソッド
  public <T> ArrayList<T> search(Table table, JSON query, Function<String, T> parser,
      boolean isLike) throws SQLException {
    if (!DB.isValidValue(query))
      throw new SQLException("Invalid value for" + query.toString());
    String qstring = buildSearchQuery("SELECT * FROM ", table, query.toEntries(), isLike);
    var res = executeSearch(new String[] {qstring}, parser);
    if (res == null) {
      logger.log(Level.SEVERE, "Failed to execute search query");
      throw new SQLException("Failed to execute search query");
    }
    return res.get(0);
  }

  // データベースを更新するメソッド
  private int update(String query, Table table, ArrayList<JSON> data) {
    var queries = new String[data.size()];
    for (int i = 0; i < data.size(); i++) {
      if (!DB.isValidValue(data.get(i)))
        return 500;
      queries[i] = buildUpdateQuery(query, table, data.get(i).toEntries());
    }
    var update = executeUpdate(queries);
    if (update == null) {
      logger.log(Level.SEVERE, "Failed to execute update query");
      return 500;
    }
    for (var u : update) {
      if (!u.equals("Success")) {
        logger.log(Level.SEVERE, "Failed to execute update query");
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

