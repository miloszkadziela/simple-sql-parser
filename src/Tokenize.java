import java.util.ArrayList;
import java.util.List;

class Tokenize {
    private static String[] tokenizeString(String query, String type) {
        String [] tokenizedQuery;
        tokenizedQuery = query.split(type);
        return tokenizedQuery;
    }

    static String [] tokenizeQuery(String query) {
        String[] tokenizedQuery1;

        List<String> finalTokenizedQuery = new ArrayList<String>();
        String[] finalArray = new String[finalTokenizedQuery.size()];
        String type1 = "\\s";
        String type2 = "[,]";
        tokenizedQuery1 = tokenizeString(query, type1);
        finalTokenizedQuery = tokenizeFurther(tokenizedQuery1, type2);
        finalArray = finalTokenizedQuery.toArray(finalArray);
        return finalArray;
    }

    static List<String> tokenizeFurther(String[] tokenizedQuery1, String type2) {
        String[] tokenizedQuery2;
        List<String> finalTokenizedQuery = new ArrayList<String>();
        for (String aTokenizedQuery1 : tokenizedQuery1) {
            tokenizedQuery2 = tokenizeString(aTokenizedQuery1, type2);
            for (String aTokenizedQuery2 : tokenizedQuery2) {
                if (!aTokenizedQuery2.equals("")) {
                    finalTokenizedQuery.add(aTokenizedQuery2);
                }
            }
        }
        return finalTokenizedQuery;
    }
}
