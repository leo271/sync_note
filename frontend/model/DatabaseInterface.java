package model;

import java.util.ArrayList;
import java.util.function.Function;
import model.db_enum.Table;

interface DatabaseInterface {
  // 検索するメソッド
  public <T> ArrayList<T> search(Table table, JSON query, Function<JSON, T> parser);

  // データベースを更新するメソッド
  public int upsert(Table table, JSON json);

  // 複数のJSONデータをアップサートするメソッド
  public int upsert(Table table, ArrayList<JSON> data);

  // 単一のJSONデータを削除するメソッド
  public int delete(Table table, JSON json);

  // 複数のJSONデータを削除するメソッド
  public int delete(Table table, ArrayList<JSON> data);
}
