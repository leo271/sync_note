// NetworkClientとVMを繋ぐ緩衝材

package model;

import java.util.ArrayList;
import java.util.Comparator;
import model.db_enum.DB;

public class DocumentController {
  // ヘッド名からドキュメント一覧をソート済みでを取得
  public static Response<ArrayList<Document>> getFromHead(String head) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var documents =
          remote.search(DB.DOCUMENT, JSON.single(DB.HEAD, head), Document::fromString, false);
      if (documents == null) {
        return Response.error(Response.NOT_FOUND);
      } else {
        documents.sort(Comparator.comparing((x) -> x.like, Comparator.reverseOrder()));
        return Response.success(documents);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // IDからドキュメントを取得
  public static Response<Document> getFromID(String docID) {
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var document =
          remote.search(DB.DOCUMENT, JSON.single(DB.DOC_ID, docID), Document::fromString, false);
      if (document == null) {
        return Response.error(Response.NOT_FOUND);
      } else {
        return Response.success(document.get(0));
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<Boolean> hasWritten(String head) {
    var local = LocalDatabaseInterface.getInstance();
    var query = new JSON() {
      {
        put(DB.HEAD, head);
        put(DB.TYPE_ML, "M");
      }
    };
    try {
      var document = local.search(DB.DOCUMENT, query, Document::fromJSON);
      return Response.success(document != null && document.size() > 0);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // 自分が書いたドキュメントを取得
  public static Response<ArrayList<Document>> getMyDocuments() {
    var local = LocalDatabaseInterface.getInstance();
    try {
      var document = local.search(DB.DOCUMENT, JSON.single(DB.TYPE_ML, "M"), Document::fromJSON);
      System.out.println("Got my documents");
      if (document == null || document.size() == 0) {
        return Response.error(Response.NOT_FOUND);
      } else {
        document.sort(Comparator.comparing((x) -> x.head, Comparator.reverseOrder()));
        return Response.success(document);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ライクしたドキュメントを取得
  public static Response<ArrayList<Document>> getLiked() {
    var local = LocalDatabaseInterface.getInstance();
    try {
      var documents = local.search(DB.DOCUMENT, JSON.single(DB.TYPE_ML, "L"), Document::fromJSON);
      System.out.println("Got liked documents");
      if (documents == null || documents.size() == 0) {
        return Response.error(Response.NOT_FOUND);
      } else {
        documents.sort(Comparator.comparing((x) -> x.head, Comparator.reverseOrder()));
        return Response.success(documents);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントをライクする（トグル）
  public static Response<Void> likeDocument(Document document) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var localDoc =
          local.search(DB.DOCUMENT, JSON.single(DB.DOC_ID, document.docID), Document::fromJSON);
      if (localDoc == null || localDoc.size() == 0) {
        // すでにライクをしたことがない
        document.like();
        local.upsert(DB.DOCUMENT, document.toJSON(false));
      } else {
        // もうライクをしているのでお気に入りから削除
        document.unlike();
        local.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID, document.docID));
      }
      remote.upsert(DB.DOCUMENT, document.toJSON(false));
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを作成
  public static Response<Document> create(String head) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var newDoc = new Document(head);
      local.upsert(DB.DOCUMENT, newDoc.toJSON(true));
      remote.upsert(DB.DOCUMENT, newDoc.toJSON(true));
      return Response.success(newDoc);
    } catch (Exception e) {
      System.err.println(e);
      for (var el : e.getStackTrace()) {
        System.err.println(el);
      }
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを更新
  public static Response<Void> updateContent(Document document) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      var likes = getFromID(document.docID).message.like;
      document.like = likes;
      local.upsert(DB.DOCUMENT, document.toJSON(true));
      remote.upsert(DB.DOCUMENT, document.toJSON(true));
      return Response.success(null);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを削除
  public static Response<Void> delete(String docID) {
    var local = LocalDatabaseInterface.getInstance();
    var remote = RemoteDatabaseInterface.getInstance();
    try {
      local.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID, docID));
      remote.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID, docID));
      return Response.success(null);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }
}
