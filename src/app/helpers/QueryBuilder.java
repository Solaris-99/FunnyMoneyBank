package app.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
* Modificado de https://medium.com/@matblockgreninja/creating-a-dynamic-query-builder-in-java-a-step-by-step-guide-d3a0253e6986
*
**/
public class QueryBuilder {

    private static final int SELECT = 0;
    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    // SELECT COLS FROM TABLE WHERE CONDITIONS;
    // INSERT INTO TABLE (COLS) VALUES (VALUES)
    // UPDATE TABLE SET COL1=VAL1, COL2=VAL2;
    // DELETE FROM TABLE WHERE CONDITION


    private int MODE;
    private List<String> columns;
    private List<String> tables;
    private List<String> conditions;
    private List<String> values;
    private String query;

    public QueryBuilder() {
        this.columns = new ArrayList<String>();
        this.tables = new ArrayList<String>();
        this.conditions = new ArrayList<String>();
        this.values = new ArrayList<String>();
    }

    public QueryBuilder select(String ...columns) {
        this.MODE = SELECT;
        Collections.addAll(this.columns, columns);
        return this;
    }

    public QueryBuilder insert(String ...columns){
        this.MODE = INSERT;
        Collections.addAll(this.columns,columns);
        return this;
    }

    public QueryBuilder update(String ...columns){
        this.MODE = UPDATE;
        Collections.addAll(this.columns, columns);
        return this;
    }

    public QueryBuilder delete(String table){
        tables.add(table);
        this.MODE = DELETE;
        return this;
    }

    public QueryBuilder from(String... tables) {
        Collections.addAll(this.tables, tables);
        return this;
    }

    public QueryBuilder where(String... conditions) {
        Collections.addAll(this.conditions, conditions);
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();


        sb.append("SELECT ");

        for (int i = 0; i < columns.size(); i++) {
            sb.append(columns.get(i));
            if (i < columns.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(" FROM ");
        for (int i = 0; i < tables.size(); i++) {
            sb.append(tables.get(i));
            if (i < tables.size() - 1) {
                sb.append(", ");
            }
        }
        if (!conditions.isEmpty()) {
            sb.append(" WHERE ");
            for (int i = 0; i < conditions.size(); i++) {
                sb.append(conditions.get(i));
                if (i < conditions.size() - 1) {
                    sb.append(" AND ");
                }
            }
        }
        return sb.toString();
    }

}
