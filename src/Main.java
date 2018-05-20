import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String query;
        System.out.println("Welcome to the implementation of SQL in JAVA");
        while (!Objects.equals(query = Query.getQuery(), "")) {
            Query.parseQuery(query);
        }
        System.out.println("Thank you for using the SQL implementation");
    }
}
