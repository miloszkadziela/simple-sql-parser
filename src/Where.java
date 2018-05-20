import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Where {
    private static HashMap<String, Runnable> removers = new HashMap<>();
    private static String operator = null;
    private static String leftOperand;
    private static String rightOperand;
    private static int limit = -2;

    public static void setLimit(int newLimit) {
        limit = newLimit + 1;
    }

    public static int getLimit() {
        return limit;

    }
    static String getLeftOperand() {
        return leftOperand;
    }

    static void setLeftOperand(String newLeftOperand) {
        leftOperand = newLeftOperand;
    }

    static String getRightOperand() {
        return rightOperand;
    }

    static void setRightOperand(String newRightOperand) {
        rightOperand = newRightOperand;
    }

    static void parseWhere(String[] tokenized, String query) {
        try {
            ArrayList<String> columns = new ArrayList<>();
            int i;
            for (i = 1; !tokenized[i].equals("FROM"); ++i) {
                columns.add(tokenized[i]);
            }

            String dbName = tokenized[++i];
            if (tokenized.length - 1 == i) {
                Database.printSpecificColumns(dbName, columns.toArray(new String[0]));
            } else if (tokenized[++i].equals("WHERE")) {
                String[] conditions = query.split(" WHERE ");
                    handleWhereClause(conditions[1], dbName);
                Database.printSpecificColumns("temp", columns.toArray(new String[0]));
            }
            else if (tokenized[i].equals("LIMIT")) {
                setLimit(Integer.parseInt(tokenized[i+1]));
                Database.printSpecificColumns(dbName, columns.toArray(new String[0]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Your query is of incorrect length by a minimum of " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean conditionContainsOperator(String condition, String op) {
        if (condition.contains(op)) {
            operator = op;
            return true;
        }
        return false;
    }

    private static void populateHashMap() {
        removers.put(" != ", () -> Selectors.selectWhereInequal());
        removers.put(" = ", () -> Selectors.selectWhereEqual());
        removers.put(" >= ", () -> Selectors.selectWhereGreaterOrEqual());
        removers.put(" > ", () -> Selectors.selectWhereGreater());
        removers.put(" <= ", () -> Selectors.selectWhereLessOrEqual());
        removers.put(" < ", () -> Selectors.selectWhereLess());

    }

    static void handleWhereClause(String conditionsStr, String dbName) throws IOException {
        String [] operators = {" != ", " >= ", " > ", " <= ", " < ", " = "};
        String [] conditionsArray = conditionsStr.split(",");
        String [] tokenizedCondition;
        boolean result = false;

        CSVReader reader = new CSVReader(new FileReader(dbName +".csv"));
        Database.setDbList(reader.readAll());
        populateHashMap();
        Database.readFirstLine(dbName);
        for (String aConditionsArray : conditionsArray) {
            operator = null;
            result = false;
            for (String operator1 : operators) {
                result = result ^ conditionContainsOperator(aConditionsArray, operator1);
            }
            if (result) {
                tokenizedCondition = aConditionsArray.split(operator);
                if (tokenizedCondition.length == 2) {
                    leftOperand = tokenizedCondition[0].trim();
                    rightOperand = tokenizedCondition[1].trim();
                    removers.get(operator).run();
                    CSVWriter writer = new CSVWriter(new FileWriter("temp.csv"), ',');
                    writer.writeAll(Database.getDbList());
                    writer.close();
                }
            }
        }
    }
}
