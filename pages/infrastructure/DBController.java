// ローカルのデータベースとプログラムとのインターフェース
// TODO: 実際にDBと接続する

package infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import mocks.DefaultMock;
import model.data.Document;
import model.data.Head;
import model.data.HeadGroup;

public class DBController {
  // 自分がマイリストに登録したHeads
  public Head[] fetchHeads() {
    return DefaultMock.heads;
  }

  // 自分が書いたDocs
  public HashMap<String, Document> fetchDocs() {
    return DefaultMock.docs;
  }

  // 自分がマイリストに登録したHeadGroups
  public HeadGroup[] fetchHeadGroups() {
    return DefaultMock.headGroups;
  }

  // 自分がライクしたDocIds
  public HashSet<String> fetchLikedDocIds() {
    return DefaultMock.likedDocIds;
  }
}
