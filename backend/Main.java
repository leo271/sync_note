package backend;


public class Main {
  public static void main(String[] args) {
    System.out.println("Starting JabberServer...");
    try {
      new JabberServer(8080).start();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
