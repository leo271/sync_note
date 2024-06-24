// NetworkClientとVMを繋ぐ緩衝材

package model;

import java.util.function.Function;
import model.db_enum.DB;

public class HeadsController {
  static private Function<JSON, JSON> id = (x) -> x;

  // update
  public static Response<Void> updateHeadGroup(HeadGroup group) {
    var remote = DatabaseInterface.getRemote();
    var local = DatabaseInterface.getLocal();
    try {
      var condition = new JSON() {
        {
          put(DB.NAME.name, group.name);
          put(DB.TYPE_HG.name, "G");
        }
      };
      local.delete(DB.DOCUMENT, condition);
      remote.delete(DB.NAME_SPACE, condition);
      local.upsert(DB.HEAD_GROUP, group.toJSONL());
      remote.upsert(DB.NAME_SPACE, group.toJSONL());
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // HeadもしくはHeadGroupを作成
  public static Response<Void> createHead(String name, String type) {
    var remote = DatabaseInterface.getRemote();
    try {
      var head = new JSON() {
        {
          put(DB.NAME.name, name);
          put(DB.TYPE_HG.name, type);
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
    var remote = DatabaseInterface.getRemote();
    try {
      var headGroup = remote.searchMulti(DB.NAME_SPACE, JSON.single(DB.NAME.name, name), id);
      if (headGroup == null) {
        return Response.error(Response.NOT_FOUND);
      }
      return Response.success(HeadGroup.fromJSONL(headGroup));
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // like
  public static Response<Void> likeHeadGroup(HeadGroup group) {
    var local = DatabaseInterface.getLocal();
    var remote = DatabaseInterface.getRemote();
    try {
      var localGroup = local.searchMulti(DB.HEAD_GROUP, JSON.single(DB.NAME.name, group.name), id);
      if (localGroup == null) {
        // すでにライクをしたことがない
        group.like();
        local.upsert(DB.DOCUMENT, group.toJSONL());
      } else {
        // もうライクをしているのでお気に入りから削除
        group.unlike();
        var condition = new JSON() {
          {
            put(DB.NAME.name, group.name);
            put(DB.TYPE_HG.name, "G");
          }
        };
        local.delete(DB.DOCUMENT, condition);
      }
      // リモートにも反映
      var like = new JSON() {
        {
          put(DB.GROUP_NAME.name, group.name);
          put(DB.LIKE.name, Integer.toString(group.like));
        }
      };
      remote.upsert(DB.DOCUMENT, like);
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // util
  public static Response<Boolean> hasResistered(String name) {
    var remote = DatabaseInterface.getRemote();
    try {
      var headGroup = remote.searchSingle(DB.NAME_SPACE, JSON.single(DB.NAME.name, name), id);
      if (headGroup == null) {
        return Response.success(false);
      } else {
        return Response.success(true);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }
}
