import java.util.Iterator;


class Selectors {
    static void selectWhereInequal() {
        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].equals(Where.getRightOperand()))) {
                Database.getUnchangedList().add(a);
                iter.remove();
            }
        }
    }

    static void selectWhereEqual() {
        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ !a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].equals(Where.getRightOperand()))) {
                Database.getUnchangedList().add(a);
                iter.remove();
            }
        }
    }

    static void selectWhereGreaterOrEqual() {

        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            try {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ (Double.compare(Double.parseDouble(a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())]), Double.parseDouble(Where.getRightOperand()))) < 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }
            catch (Exception e) {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].compareTo(Where.getRightOperand()) < 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }
        }
    }

    static void selectWhereGreater() {
        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            try {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ (Double.compare(Double.parseDouble(a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())]), Double.parseDouble(Where.getRightOperand()))) <= 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }
            catch (Exception e) {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].compareTo(Where.getRightOperand()) <= 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }
        }
    }

    static void selectWhereLessOrEqual() {
        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            try {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ (Double.compare(Double.parseDouble(a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())]), Double.parseDouble(Where.getRightOperand()))) > 0)) {
                    iter.remove();
                }
            }
            catch (Exception e) {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].compareTo(Where.getRightOperand()) > 0)) {
                    iter.remove();
                }
            }
        }
    }

    static void selectWhereLess() {
        boolean firstRun = true;
        for (Iterator<String[]> iter = Database.getDbList().listIterator(); iter.hasNext(); ) {
            String [] a = iter.next();
            if (firstRun) {
                firstRun = false;
                continue;
            }
            try {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ (Double.compare(Double.parseDouble(a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())]), Double.parseDouble(Where.getRightOperand()))) >= 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }
            catch (Exception e) {
                if (Database.columnExists(Where.getLeftOperand(), Database.getFirstLine()) && (Delete.deleteMode ^ a[Database.getColumnIndex(Where.getLeftOperand(), Database.getFirstLine())].compareTo(Where.getRightOperand()) >= 0)) {
                    Database.getUnchangedList().add(a);
                    iter.remove();
                }
            }

        }
    }
}
