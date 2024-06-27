// NetworkClientとVMを繋ぐ緩衝材

package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;
import model.db_enum.DB;

public class HeadsController {
  private static char US = '\u001f';

  // update
  public static Response<Void> updateHeadGroup(HeadGroup group) {
    var remote = RemoteDatabaseInterface.getInstance();
    var local = LocalDatabaseInterface.getInstance();
    try {
      local.upsert(DB.HEAD_GROUP, group.toJSONL());
      remote.upsert(DB.HEAD_GROUP, group.toJSONL());
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<HeadGroup> searchHeads(String name) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      Function<String, String> id = (x) -> x;
      var searchResult = remote.search(DB.NAME_SPACE, JSON.single(DB.NAME, name), id, true);
      var result = new HeadGroup("検索結果", new HashSet<>(), new HashSet<>(), 0);
      for (var res : searchResult) {
        var sep = res.split(US + "");
        if (sep[1].equals("G")) {
          result.addHeadGroup(sep[0]);
        } else {
          result.addHead(sep[0]);
        }
      }

      return Response.success(result);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // HeadもしくはHeadGroupを作成
  public static Response<Void> create(String name, String type) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var head = new JSON() {
        {
          put(DB.NAME, name);
          put(DB.TYPE_HG, type);
        }
      };
      remote.upsert(DB.NAME_SPACE, head);
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // get
  public static Response<HeadGroup> getHeadGroup(String name) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      Function<String, String> id = (x) -> x;
      var headGroup = remote.search(DB.HEAD_GROUP, JSON.single(DB.NAME, name), id, false);
      if (headGroup == null) {
        return Response.error(Response.NOT_FOUND);
      }
      return Response.success(HeadGroup.fronLines(headGroup));
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // like
  public static Response<Void> likeHeadGroup(HeadGroup group) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      Function<JSON, JSON> id = (x) -> x;
      var localGroup = local.search(DB.HEAD_GROUP, JSON.single(DB.NAME, group.name), id);
      if (localGroup == null || localGroup.size() == 0) {
        // すでにライクをしたことがない
        group.like();
        local.upsert(DB.DOCUMENT, group.toJSONL());
      } else {
        // もうライクをしているのでお気に入りから削除
        group.unlike();
        var condition = new JSON() {
          {
            put(DB.NAME, group.name);
            put(DB.TYPE_HG, "G");
          }
        };
        local.delete(DB.DOCUMENT, condition);
      }
      // リモートにも反映
      var like = new JSON() {
        {
          put(DB.GROUP_NAME, group.name);
          put(DB.LIKE, Integer.toString(group.like));
        }
      };
      remote.upsert(DB.DOCUMENT, like);
      return Response.SUCCESS;
    } catch (

    Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // util
  public static Response<Boolean> hasResistered(String name) {
    var remote = RemoteDatabaseInterface.getInstance();
    Function<String, Boolean> idb = (x) -> x != null;
    try {
      var headGroup = remote.search(DB.NAME_SPACE, JSON.single(DB.NAME, name), idb, false);
      if (headGroup == null || headGroup.size() == 0) {
        return Response.success(false);
      } else {
        return Response.success(true);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<ArrayList<String>> getMyHeadGroups() {
    var local = LocalDatabaseInterface.getInstance();
    try {
      Function<JSON, JSON> id = (x) -> x;
      var headGroups = local.search(DB.HEAD_GROUP, null, id);
      System.out.println("Searched");
      if (headGroups == null || headGroups.size() == 0 || headGroups.get(0).isEmpty()) {
        return Response.error(Response.NOT_FOUND);
      }
      var result = new HashSet<String>();
      for (var headGroup : headGroups) {
        result.add(headGroup.get(DB.GROUP_NAME));
      }
      return Response.success(new ArrayList<>(result));
    } catch (Exception e) {
      System.err.println(e);
      for (var trace : e.getStackTrace()) {
        System.err.println(trace);
      }
      return Response.error(Response.INVALID_VALUE);
    }
  }
}
