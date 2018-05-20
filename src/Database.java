import java.util.*;
import com.opencsv.*;
import java.io.*;


class Database {
    private static List<String[]> dbList;
    private static List<String[]> unchangedList = new ArrayList<>();
    private static String[] firstLine;

    static String[] getFirstLine() {
        return firstLine;
    }

    static void setFirstLine(String[] newFirstLine) {
        firstLine = newFirstLine;
    }

    static List<String[]> getUnchangedList() {
        return unchangedList;
    }

    static void setUnchangedList(List<String[]> newUnchangedList) {
        unchangedList = newUnchangedList;
    }

    static List<String[]> getDbList() {
        return dbList;
    }

    static void setDbList(List<String[]> newList) {
        dbList = newList;
    }

    private static void printEntireEntry(String[] nextLine) {
        for (String aNextLine : nextLine) {
            String output = String.format("%15s", aNextLine);
            System.out.print(output + " ");
        }
        System.out.println();
    }

    static void printSpecificColumns(String dbName, String[] columns) {
        int lineIndex = 0;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(dbName + ".csv"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            String[] aNextLine;
            while (Where.getLimit() != lineIndex && (aNextLine = reader != null ? reader.readNext() : new String[0]) != null) {
                if (lineIndex == 0)
                    setFirstLine(aNextLine);
                if (columns.length == 1 && columns[0].equals("*")) {
                    printEntireEntry(aNextLine);
                }
                for (String column : columns) {
                    if (columnExists(column, firstLine)) {
                        String output = String.format("%15s", aNextLine[getColumnIndex(column, firstLine)]);
                        System.out.print(output + " ");
                    }
                }
                ++lineIndex;
                System.out.println();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Where.setLimit(-2);
    }

    static void readFirstLine(String databaseName) {
        try {
            CSVReader rdr = new CSVReader(new FileReader("" + databaseName + ".csv"));
            firstLine = rdr.readNext();
            rdr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean columnExists(String column, String[] firstLine) {
        for (String aFirstLine : firstLine) {
            if (Objects.equals(aFirstLine, column)) return true;
        }
        return false;
    }
    static int getColumnIndex(String column, String[] firstLine) {
        for (int i = 0; i < firstLine.length; ++i) {
            if (Objects.equals(firstLine[i], column)) return i;
        }
        return 0;
    }


}
