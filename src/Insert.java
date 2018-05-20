import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

class Insert {
    static void parseInsert(String[] tokenized, String query) throws Exception {
        try {
            if (!tokenized[1].equals("INTO")) {
                throw new Exception("Syntax error, INTO keyword expected after INSERT");
            }
            if (tokenized[2].contains("(") || tokenized[2].contains(")")) {
                throw new Exception("Your table name is incorrect");
            }
            String tableName = tokenized[2];
            Database.readFirstLine(tableName);
            String[] values;
            String[] row = new String[Database.getFirstLine().length];
            String[] split = query.split("VALUES");
            values = split[1].split(",");
            if (tokenized[3].equals("VALUES") && split.length == 2) {
                handleAllColumns(values, row);
            }
            else {
                String[] keys = new String[tokenized.length - values.length - 1 - 3];
                System.arraycopy(tokenized, 3, keys, 0, keys.length);
                handleChosenColumns(keys, values, row);
            }
            writeIntoFile(tableName, row);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Your query is of incorrect length by a minimum of " + e.getMessage());
            }
        }
    private static void writeIntoFile(String tableName, String[] row) throws Exception {
        File db = new File(tableName + ".csv");
        if (!db.isFile()) {
            throw new Exception("No such table in the database");
        }
        CSVReader reader = new CSVReader(new FileReader(tableName + ".csv"));
        List<String[]> previousEntries = reader.readAll();
        reader.close();
        CSVWriter writer = new CSVWriter(new FileWriter(tableName + ".csv"), ',');
        writer.writeAll(previousEntries);
        writer.writeNext(row);
        writer.close();
    }
    private static void handleChosenColumns(String [] keys, String [] values, String [] row) {
        for (int i = 0; i < keys.length; i++) {
            refactor(keys, i);
        }
        for (int i = 0; i < values.length; i++) {
            refactor(values, i);
        }
        if (keys.length == values.length) {
            for (int i = 0; i < keys.length; ++i) {
                row[Database.getColumnIndex(keys[i], Database.getFirstLine())] = values[i];
            }
        }
    }
    private static void handleAllColumns(String [] values, String [] row) {
        if (values.length == Database.getFirstLine().length) {
            for (int i = 0; i < values.length; i++) {
                refactor(values, i);
                row[i] = values[i];
            }
        }
    }
    private static void refactor(String [] values, int i) {
            values[i] = values[i].trim();
            values[i] = values[i].replaceAll("\\(", "");
            values[i] = values[i].replaceAll("\\)", "");

    }
}