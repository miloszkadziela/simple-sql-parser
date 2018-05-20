import java.io.File;

class Drop {
    static void parseDrop(String[] tokenized) {
        try {
            if (!tokenized[1].equals("TABLE")) {
                throw new Exception("Syntax error, TABLE keyword expected after DROP");
            }
            File db = new File(tokenized[2] + ".csv");
            if (!db.isFile()) {
                throw new Exception("No such table in the database");
            }
            db.delete();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
