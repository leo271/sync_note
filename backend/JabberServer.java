package backend;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class JabberServer {
  private final int PORT; // ポート番号を設定する．

  public JabberServer(int port) {
    this.PORT = port;
  }

  public void start() throws IOException {
    ServerSocket serverSocket = new ServerSocket(PORT); // ソケットを作成する
    System.out.println("Started: " + serverSocket);

    try {
      while (true) {
        Socket clientSocket = serverSocket.accept(); // コネクション設定要求を待つ
        System.out.println("Connection accepted: " + clientSocket);
        new ClientHandler(clientSocket).start(); // 新しいスレッドでクライアントを処理
      }
    } finally {
      serverSocket.close();
    }
  }
}


class ClientHandler extends Thread {
  private static final char RS = '\u001e';
  private static final char US = '\u001f';
  private static final char GS = '\u001d';
  private static final char EOT = '\u0004';
  private final Socket socket;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try (InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

      var db = DatabaseClient.getInstance();
      int read;
      while ((read = in.read()) != EOT) {
        if (read == -1) {
          throw new EOFException("Client closed connection before EOT");
        }
        buffer.write(read);
      }
      String received = buffer.toString(StandardCharsets.UTF_8);

      var ret = new StringBuilder();
      for (String elm : received.split(RS + "")) {
        var q = elm.split(US + "");
        if (ret.length() > 0) {
          ret.append(GS);
        }
        if (q.length != 2) {
          System.err.println("Invalid query: " + elm);
          continue;
        }
        System.out.println("Query: " + q[0] + " " + q[1]);
        if (q[0].equals("UPDATE")) {
          ret.append(db.executeUpdate(q[1]));
        } else {
          ret.append(db.executeQuery(q[1]));
        }
      }
      ret.append(EOT);

      out.write(ret.toString().getBytes(StandardCharsets.UTF_8));
      out.flush();
    } catch (IOException e) {
      System.err.println("IOError: " + e);
    } catch (SQLException e) {
      System.err.println("SQLError: " + e);
    } finally {
      try {
        System.out.println("closing...");
        socket.close();
      } catch (IOException e) {
        System.err.println("Error closing socket: " + e);
      }
    }
  }
}
