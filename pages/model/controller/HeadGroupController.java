// NetworkClientとVMを繋ぐ緩衝材
// FIXME: NetworkClientのインターフェースができたらloadの処理を変更

package Model.Controller;

import java.util.HashMap;
import Mocks.MockData;
import Model.Data.HeadGroup;

public class HeadGroupController {
  private HashMap<String, HeadGroup> headGroups = new HashMap<>();

  // サーバーから、指定された名前のヘッドグループを取得
  public void loadGroupsFromServer(String headGroupName) {
    var groups = MockData.headGroups;
    for (var groupEntry : groups) {
      if (headGroupName == groupEntry.name) {
        headGroups.put(headGroupName, groupEntry);
      }
    }
  }

  // 存在しない場合はnullを返す
  public HeadGroup getDocLists(String groupName) {
    return headGroups.get(groupName);
  }
}
