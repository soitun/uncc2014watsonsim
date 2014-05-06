package uncc2014watsonsim;

/**
 *
 * @author Phani Rahul
 */
public class Translation {

    public static String getIndriQuery(String question) {
        String query = "#filrej( portal.title #filrej( "
                + "template.title #filrej(index.title #filrej( "
                + "list.title #filrej( wikipedia.title #combine("+question+"))))))";

        question = question.replaceAll("[^0-9a-zA-Z ]+", " ");
        String words[] = StringUtils.filterRelevant(question).split(" ");

        for (String word : words) {
            query = " #filrej(" + word + ".title " + query + " )";
        }
        return query;
    }

    public static String getLuceneQuery(String question) {
        String query = question + " NOT title:*\\:*" + " NOT title:list*"
                + " NOT title:index*" ;
        question = question.replaceAll("[^0-9a-zA-Z ]+", " ");
        String[] words = StringUtils.filterRelevant(question).split(" ");

        for (String word : words) {
            query = " NOT title:" + word + " " + query ;
        }
        return query;
    }
    
    /**
     * Translates a question/anagram pair into a lucene query. Based on getLuceneQuery
     * @param question the question text of query
     * @param anagram the anagram part of the query. This method will make sure that the 
     * 		          order of the words will be enforced
     * @return the processed query string
     */
    public static String getLuceneQueryAnagram(String question, String anagram) {
    	String query = question + "\"" + anagram + "\"" + " NOT title:*\\:*" + " NOT title:list*"
                + " NOT title:index*" ;
        question = question.replaceAll("[^0-9a-zA-Z ]+", " ");
        String[] words = StringUtils.filterRelevant(question).split(" ");

        for (String word : words) {
            query = " NOT title:" + word + " " + query ;
        }
        return query;
    }

}