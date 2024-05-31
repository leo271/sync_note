// 仮のモックデータを格納するクラス。
// FIXME：プロダクト版ではこのファイルへの依存関係を削除

package mocks;

import java.util.HashMap;
import java.util.HashSet;
import model.Document;
import model.Head;
import model.HeadGroup;

public class Mocks {
  public static final Head[] heads = {
      new Head("情理実験ソフトウェア制作").addDocIDs(
          new String[] {"asduf9a8weurq23807yn", "a09n7s8efypaciwuasdjfn-cw", "uha0oseidnfpi as"}),
      new Head("代数学基礎A 5/14").addDocIDs(
          new String[] {"a98nsenr-qpwcrdnoq3", "auw-n4rcqpmwilrhuoaw", "9-aw74jmecr9pawlehcfanp"}),
      new Head("プログラミングB 5/21")
          .addDocIDs(new String[] {"ua-nwenfncdhsp98t7nq4", "a@w[e,f@c/a.lwioj[ern90av]]"}),
      new Head("情理実験検索エンジン評価").addDocIDs(new String[] {"a[w.es-0:9rviqwa4erv]"}),
      new Head("代数学基礎A 5/21")
          .addDocIDs(new String[] {"ap9wernuic[ma]wpeirhpbv", "[imz-s0erao]w@:mpeoimr@"}),
      new Head("プログラミングB 5/28"), new Head("情理実験センサと制御"), new Head("情報通信ネットワーク 5/15"),
      new Head("トラヒック理論 5/16"), new Head("情理実験ハードウェア記述言語"), new Head("情報通信ネットワーク 5/15"),
      new Head("プログラミングB 6/4")};

  // 自分が書いたdocumentのリスト
  public static final HashMap<String, Document> docs = new HashMap<String, Document>() {
    {
      var document0 = new Document(heads[0].name, "他のグループは何やってるのかな？");
      var document3 = new Document(heads[3].name, "情理実験の授業内容がよくわからない");
      var document4 = new Document(heads[4].name, "代数学基礎Aは名前は難しいけど、内容は群論だから簡単だよ！");
      put(document0.head, document0);
      put(document3.head, document3);
      put(document4.head, document4);
    }
  };

  // 自分が保存したHeadGroupのリスト
  public static final HeadGroup[] headGroups =
      {new HeadGroup("情理実験").addHeads(new Head[] {heads[0], heads[3], heads[6], heads[9]}),
          new HeadGroup("代数学基礎A").addHeads(new Head[] {heads[1], heads[4]}),
          new HeadGroup("プログラミングB").addHeads(new Head[] {heads[2], heads[5], heads[10]}),
          new HeadGroup("情報通信ネットワーク").addHeads(new Head[] {heads[7], heads[10]}),
          new HeadGroup("早稲田大学情報理工学科")
              .addHeadGroups(new String[] {"情理実験", "代数学基礎A", "プログラミングB", "情報通信ネットワーク"}),};

  // 自分がライクしたドキュメントIDのリスト
  public static final HashSet<String> likedDocIds = new HashSet<>() {
    {
      add("asduf9a8weurq23807yn");
      add("a98nsenr-qpwcrdnoq3");
      add("ap9wernuic[ma]wpeirhpbv");
    }
  };
}
