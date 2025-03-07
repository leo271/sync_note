package infrastructure;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class JabberClient {
  private final int PORT;
  private static final char EOT = '\u0004';

  public JabberClient(int port) {
    this.PORT = port;
  }

  public String get(String queries) throws IOException {
    InetAddress addr = InetAddress.getByName("localhost");
    try (Socket socket = new Socket(addr, PORT);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
      out.write((queries + EOT).getBytes(StandardCharsets.UTF_8));
      out.flush();

      int read;
      while ((read = in.read()) != EOT) {
        if (read == -1) {
          System.err.println("Server closed connection before EOT");
          throw new EOFException("Server closed connection before EOT");
        }
        buffer.write(read);
      }

      return buffer.toString(StandardCharsets.UTF_8);
    } finally {
    }
  }
}
