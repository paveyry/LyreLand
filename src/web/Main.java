package web;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        get("/hello", (req, res) -> "Hello World");
    }
}
