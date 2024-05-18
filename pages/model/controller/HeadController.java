// NetworkClientとVMを繋ぐ緩衝材
// FIXME: NetworkClientのインターフェースができたらloadの処理を変更

package Model.Controller;

import java.util.HashMap;
import Mocks.MockData;
import Model.Data.Head;

public class HeadController {
  private HashMap<String, Head> heads = new HashMap<>();

  // サーバーから、指定された名前のHeadを取得
  public void loadHeadsFromServer(String headName) {
    var docs = MockData.heads;
    for (var docEntry : docs) {
      if (docEntry.name == headName) {
        heads.put(headName, docEntry);
        return;
      }
    }
  }

  // 存在しない場合はnullを返す
  public Head getHead(String headName) {
    return heads.get(headName);
  }
}
