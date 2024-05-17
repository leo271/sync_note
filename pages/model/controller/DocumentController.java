// NetworkClientとVMを繋ぐ緩衝材
// FIXME: NetworkClientのインターフェースができたらloadの処理を変更

package model.controller;

import java.util.ArrayList;
import java.util.HashMap;

import mocks.Mocks;
import model.data.Document;

public class DocumentController {
  private HashMap<String, ArrayList<Document>> documents = new HashMap<>();

  // サーバーから、指定されたヘッドに紐ずくドキュメント(配列)を取得
  public void loadDocumentsFromServer(String headName) {
    var docs = Mocks.docs;
    documents.put(headName, new ArrayList<>());
    for (var docEntry : docs.entrySet()) {
      if (Math.random() > 0.5) {
        documents.get(headName).add(docEntry.getValue());
      }
    }
  }

  // 存在しない場合はnullを返す
  public Document[] getDocLists(String headName) {
    return documents.get(headName).toArray(new Document[0]);
  }
}
