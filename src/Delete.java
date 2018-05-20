import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

class Delete {
    static  boolean deleteMode = false;

    static void parseDelete(String[] tokenized, String query) {
        String dbName;
        if (Objects.equals(tokenized[1], "FROM") && (tokenized.length == 3 || Objects.equals(tokenized[3], "WHERE") && tokenized.length >= 7)) {
            dbName = tokenized[2];
        }
        else return;
        if (tokenized.length == 3) {
            Database.readFirstLine(dbName);
            try {
                CSVWriter writer = new CSVWriter(new FileWriter(dbName + ".csv"), ',');
                writer.writeNext(Database.getFirstLine());
                writer.close();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        else if (tokenized.length >= 7){
            String[] conditions = query.split(" WHERE ");
            try {
                Where.handleWhereClause(conditions[1], dbName);
                CSVWriter writer = new CSVWriter(new FileWriter(dbName + ".csv"), ',');
                writer.writeAll(Database.getDbList());
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
