// ローカルのデータベースとプログラムとのインターフェース
// TODO: 実際にDBと接続する

package infrastructure;

import java.util.HashMap;
import java.util.HashSet;

import mocks.Mocks;
import model.Document;
import model.Head;
import model.HeadGroup;

public class DBController {
  // 自分がマイリストに登録したHeads
  public Head[] fetchHeads() {
    return Mocks.heads;
  }

  // 自分が書いたDocs
  public HashMap<String, Document> fetchDocs() {
    return Mocks.docs;
  }

  // 自分がマイリストに登録したHeadGroups
  public HeadGroup[] fetchHeadGroups() {
    return Mocks.headGroups;
  }

  // 自分がライクしたDocIds
  public HashSet<String> fetchLikedDocIds() {
    return Mocks.likedDocIds;
  }
}
