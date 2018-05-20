import java.util.Scanner;

class Query {
    static void parseQuery(String query) {
        try {
            String[] tokenized = Tokenize.tokenizeQuery(query);
            switch (tokenized[0]) {
                case "CREATE":
                    Create.parseCreate(tokenized);
                    break;
                case "DROP":
                    Drop.parseDrop(tokenized);
                    break;
                case "SELECT":
                    Delete.deleteMode = false;
                    Where.parseWhere(tokenized, query);
                    break;
                case "DELETE":
                    Delete.deleteMode = true;
                    Delete.parseDelete(tokenized, query);
                    break;
                case "UPDATE":
                    Delete.deleteMode = false;
                    Update.parseUpdate(tokenized, query);
                    break;
                case "INSERT":
                    Insert.parseInsert(tokenized, query);
                    break;
                default:
                    System.out.println("SYNTAX ERROR - Your query is incorrect");
            }
        } catch (Exception e) {
            System.out.println("A less expected error occurred :" + e.getMessage());
        }
    }

    static String getQuery() {
        String query;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your query or press RETURN to exit");
        query = scanner.nextLine();
        return query;
    }
}
