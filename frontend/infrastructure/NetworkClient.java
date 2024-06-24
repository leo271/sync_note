// サーバー側との直接の通信をラップするクラス

package infrastructure;

import java.io.IOException;
import model.Response;

public class NetworkClient {
    private static final char GS = '\u001d';
    private static final char RS = '\u001e';
    private static final char US = '\u001f';
    private static final JabberClient client1 = new JabberClient(8080);

    public static Response<String[]> excuteQueries(String[] queries, String type) {
        try {
            var res = client1.get(concatQueries(queries, type));
            return Response.success(res.split(GS + ""));
        } catch (IOException e) {
            return Response.error(Response.NETWORK_ERROR);
        }
    }

    private static String concatQueries(String[] queries, String type) {
        if (!type.equals("UPDATE") && !type.equals("QUERY")) {
            System.err.println("Invalid query type: " + type);
            return "";
        }
        var ret = new StringBuilder();
        for (int i = 0; i < queries.length; i++) {
            ret.append(type).append(US).append(queries[i]).append(RS);
        }
        return ret.toString();
    }
}
