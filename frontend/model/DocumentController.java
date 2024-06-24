// NetworkClientとVMを繋ぐ緩衝材

package model;

import java.util.ArrayList;
import java.util.Comparator;
import model.db_enum.DB;

public class DocumentController {
  // ヘッド名からドキュメント一覧をソート済みでを取得
  public static Response<ArrayList<Document>> getDocuments(String head) {
    var remote = DatabaseInterface.getRemote();
    try {
      var documents =
          remote.searchMulti(DB.DOCUMENT, JSON.single(DB.HEAD.name, head), Document::fromJSON);
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
  public static Response<Document> getDocument(String docID) {
    var remote = DatabaseInterface.getRemote();
    try {
      var document =
          remote.searchSingle(DB.DOCUMENT, JSON.single(DB.DOC_ID.name, docID), Document::fromJSON);
      if (document == null) {
        return Response.error(Response.NOT_FOUND);
      } else {
        return Response.success(document);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // 自分が書いたドキュメントを取得
  public static Response<ArrayList<Document>> getMyDocuments() {
    var db = DatabaseInterface.getLocal();
    try {
      var query = new JSON() {
        {
          put(DB.TYPE_ML.name, "M");
        }
      };
      var document = db.searchMulti(DB.DOCUMENT, query, Document::fromJSON);
      if (document == null) {
        return Response.error(Response.NOT_FOUND);
      } else {
        document.sort(Comparator.comparing((x) -> x.head, Comparator.reverseOrder()));
        return Response.success(document);
      }
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  public static Response<ArrayList<Document>> getLikedDocuments() {
    var db = DatabaseInterface.getLocal();
    try {
      var query = new JSON() {
        {
          put(DB.TYPE_ML.name, "L");
        }
      };
      var documents = db.searchMulti(DB.DOCUMENT, query, Document::fromJSON);
      if (documents == null) {
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
    var local = DatabaseInterface.getLocal();
    var remote = DatabaseInterface.getRemote();
    try {
      var localDoc = local.searchSingle(DB.DOCUMENT, JSON.single(DB.DOC_ID.name, document.docID),
          Document::fromJSON);
      if (localDoc == null) {
        // すでにライクをしたことがない
        document.like();
        local.upsert(DB.DOCUMENT, document.toJSON());
      } else {
        // もうライクをしているのでお気に入りから削除
        document.unlike();
        local.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID.name, document.docID));
      }
      remote.upsert(DB.DOCUMENT, document.toJSON());
      return Response.SUCCESS;
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを作成
  public static Response<String> createDocument(String head) {
    var local = DatabaseInterface.getLocal();
    var remote = DatabaseInterface.getRemote();
    try {
      var newDoc = new Document(head);
      local.upsert(DB.DOCUMENT, newDoc.toJSON());
      remote.upsert(DB.DOCUMENT, newDoc.toJSON());
      return Response.success(newDoc.docID);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを更新
  public static Response<Void> updateDocument(Document document) {
    var local = DatabaseInterface.getLocal();
    var remote = DatabaseInterface.getRemote();
    try {
      local.upsert(DB.DOCUMENT, document.toJSON());
      remote.upsert(DB.DOCUMENT, document.toJSON());
      return Response.success(null);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }

  // ドキュメントを削除
  public static Response<Void> deleteDocument(String docID) {
    var local = DatabaseInterface.getLocal();
    var remote = DatabaseInterface.getRemote();
    try {
      local.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID.name, docID));
      remote.delete(DB.DOCUMENT, JSON.single(DB.DOC_ID.name, docID));
      return Response.success(null);
    } catch (Exception e) {
      return Response.error(Response.INVALID_VALUE);
    }
  }
}
