import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

class Create {
    static void parseCreate(String[] tokenized) {
        try {
            if(!tokenized[1].equals("TABLE")) {
                throw new Exception("Syntax error, TABLE keyword expected after CREATE");
            }

            if (tokenized[2].contains("(") || tokenized[2].contains(")")) {
                throw new Exception("Error in table name");
            }
            String tableName = tokenized[2];
            tokenized = (Tokenize.tokenizeFurther(tokenized, "\\(")).toArray(tokenized);
            tokenized = (Tokenize.tokenizeFurther(tokenized, "\\)")).toArray(tokenized);

            String [] headerNames = new String[tokenized.length  - 3];
            System.arraycopy(tokenized, 3, headerNames, 0, tokenized.length - 3);

            if (new File(tableName + ".csv").isFile()) {
                throw new Exception("Error creating database - database already exists");
            }

            CSVWriter writer = new CSVWriter(new FileWriter(tableName +".csv"), ',');
            writer.writeNext(headerNames);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
