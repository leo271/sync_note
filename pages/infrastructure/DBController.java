// ローカルのデータベースとプログラムとのインターフェース
// TODO: 実際にDBと接続する

package Infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import Mocks.MockData;
import Model.Data.Document;
import Model.Data.Head;
import Model.Data.HeadGroup;

public class DBController {
  // 自分がマイリストに登録したHeads
  public Head[] fetchHeads() {
    return MockData.heads;
  }

  // 自分が書いたDocs
  public HashMap<String, Document> fetchDocs() {
    return MockData.docs;
  }

  // 自分がマイリストに登録したHeadGroups
  public HeadGroup[] fetchHeadGroups() {
    return MockData.headGroups;
  }

  // 自分がライクしたDocIds
  public HashSet<String> fetchLikedDocIds() {
    return MockData.likedDocIds;
  }
}
