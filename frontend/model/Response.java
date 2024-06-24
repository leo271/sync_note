package model;



public class Response<T> {
  public T message;
  public String error;

  public static final Response<Void> SUCCESS = new Response<Void>(null, null);
  public static final String NOT_FOUND = "Not found";
  public static final String INVALID_VALUE = "Invalid value";
  public static final String NETWORK_ERROR = "Network error";

  public Response(T message, String error) {
    this.message = message;
    this.error = error;
  }

  public static <T> Response<T> success(T message) {
    return new Response<T>(message, null);
  }

  public static <T> Response<T> error(String error) {
    return new Response<T>(null, error);
  }

  public boolean hasError() {
    return error != null;
  }

  public boolean success() {
    return error == null;
  }

  public boolean ise(String error) {
    return this.error == error;
  }
}
