package model.dao.mysql.util;

public class QueryDAOUtil {
    private static final String SELECT = "SELECT";

    private static final String AND = "AND";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";

    private static final Character SPACE = ' ';
    private static final Character EQUALLY = '=';
    private static final Character QUESTION = '?';
    private static final Character STAR = '*';




    /**
     * @param tableName
     * @param labelParameters
     * @return SELECT * FROM {tableName} WHERE {labelParameters}=? AND {labelParameters}=? ...
     */
    public static String createFindByParameterQuery(String tableName, String... labelParameters){
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT).append(SPACE);
        builder.append(STAR).append(SPACE);
        builder.append(FROM).append(SPACE);
        builder.append(tableName.toUpperCase()).append(SPACE);
        builder.append(WHERE).append(SPACE);
        builder.append(labelParameters[0]).append(EQUALLY).append(QUESTION).append(SPACE);

        for (int i = 1; i < labelParameters.length; i++){
            builder.append(AND).append(SPACE);
            builder.append(labelParameters[i]).append(EQUALLY).append(QUESTION).append(SPACE);
        }

        return builder.toString();
    }



}
