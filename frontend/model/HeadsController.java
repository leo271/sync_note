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
      local.delete(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, group.name));
      remote.delete(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, group.name));
      local.upsert(DB.HEAD_GROUP, group.toJSONL());
      remote.upsert(DB.HEAD_GROUP, group.toJSONL());
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<Void> removeLocal(String name) {
    var local = LocalDatabaseInterface.getInstance();
    try {
      local.delete(DB.DOCUMENT, JSON.single(DB.NAME, name));
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<Void> removeHead(String name) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      remote.delete(DB.NAME_SPACE, JSON.single(DB.NAME, name));
      remote.delete(DB.HEAD_GROUP, JSON.single(DB.NAME, name));
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<Void> removeHeadGroup(String name) {
    var remote = RemoteDatabaseInterface.getInstance();
    var local = LocalDatabaseInterface.getInstance();
    try {
      remote.delete(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, name));
      local.delete(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, name));
      remote.delete(DB.HEAD_GROUP, JSON.single(DB.NAME, name));
      local.delete(DB.HEAD_GROUP, JSON.single(DB.NAME, name));
      remote.delete(DB.NAME_SPACE, JSON.single(DB.NAME, name));
      local.delete(DB.NAME_SPACE, JSON.single(DB.NAME, name));
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
        if (res.isEmpty())
          continue;
        var sep = res.split(US + "");
        if (sep[1].equals("G")) {
          result.addHeadGroup(sep[0]);
        } else {
          result.addHead(sep[0]);
        }
      }
      if (result.heads.size() == 0 && result.headGroups.size() == 0)
        return Response.error(Response.NOT_FOUND);

      return Response.success(result);
    } catch (Exception e) {
      System.err.println(e);
      for (var trace : e.getStackTrace()) {
        System.err.println(trace);
      }
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
      var headGroup = remote.search(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, name), id, false);
      if (headGroup == null || headGroup.size() == 0 || headGroup.get(0).isEmpty()) {
        return Response.error(Response.NOT_FOUND);
      }
      return Response.success(HeadGroup.fronLines(headGroup));
    } catch (Exception e) {
      System.out.println(e);
      for (var elm : e.getStackTrace()) {
        System.out.println(elm);
      }

      return Response.error(Response.INVALID_VALUE);
    }
  }

  // like
  public static Response<Void> toggleLike(String headGroup) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      Function<JSON, JSON> id = (x) -> x;
      var localGroup = local.search(DB.HEAD_GROUP, JSON.single(DB.NAME, headGroup), id);
      var remoteGroup = getHeadGroup(headGroup);
      if (remoteGroup.hasError()) {
        return Response.error(Response.NOT_FOUND);
      }
      var group = remoteGroup.message;
      if (localGroup == null || localGroup.size() == 0) {
        // すでにライクをしたことがない
        group.like();
        local.upsert(DB.HEAD_GROUP, group.toJSONL());
      } else {
        // もうライクをしているのでお気に入りから削除
        group.unlike();
        local.delete(DB.HEAD_GROUP, JSON.single(DB.GROUP_NAME, headGroup));
      }
      // リモートにも反映
      remote.upsert(DB.HEAD_GROUP, group.toJSONL());
      return Response.SUCCESS;
    } catch (Exception e) {
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
