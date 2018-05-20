import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

class Update {
    static void parseUpdate(String[] tokenized, String query) {
        Database.getUnchangedList().clear();
        String dbName = tokenized[1];
        String [] whereSplit = query.split(" WHERE ");
        if (whereSplit.length == 2) {
            try {
                Where.handleWhereClause(whereSplit[1], dbName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if (whereSplit.length != 1) {
            return;
        }
        String updatersStr = whereSplit[0].split(" SET ")[1];
        String [] updaters = updatersStr.split(",");
        String [] tokenizedUpdater;
        Database.readFirstLine(dbName);
        for (String updater : updaters) {
            tokenizedUpdater = updater.split(" = ");
            if (tokenizedUpdater.length == 2) {
                Where.setLeftOperand(tokenizedUpdater[0].trim());
                Where.setRightOperand(tokenizedUpdater[1].trim());
                for (int j = 1; j < Database.getDbList().size(); ++j) {
                    String[] entry = Database.getDbList().get(j);
                    entry[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())] = Where.getRightOperand();
                    Database.getDbList().set(j, entry);
                }
            }
        }
        Database.getDbList().addAll(Database.getUnchangedList());
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(dbName + ".csv"), ',');
            writer.writeAll(Database.getDbList());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
