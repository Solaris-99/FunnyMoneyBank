package app.dao;
import app.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {
    private final Connection con;
    private final String table;

    public Dao(String table){
        this.con = DBConnection.getConnection();
        this.table = table;
    }

    public abstract ResultSet select(int id);
    public abstract void insert();
    public abstract void update(int id);
    public abstract void delete(int id);

//    public ResultSet select(String[] cols,String filters, String[] values){
//        //cols, , val1,val2,val3 arg
//        StringBuilder query = new StringBuilder("SELECT ");
//        query.append(String.join(", ",cols));
//        query.append("FROM `");
//        query.append(this.table);
//        query.append("` WHERE ");
//        query.append(filters);
//        String sql = query.toString();
//        try {
//            PreparedStatement stmt = this.con.prepareStatement(sql);
//            for (int i = 0; i < values.length; i++){
//                stmt.setString(i+1, values[i]);
//            }
//            return stmt.executeQuery();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void insert(String[] cols, String[] values){
//        //{{"col1, col2, col3"},{ val1, val2, val3},
//        StringBuilder query = new StringBuilder("INSERT INTO ");
//        query.append(this.table);
//        query.append("(");
//        query.append(String.join(", ",cols));
//        query.append(") VALUES (");
//
//        for(int i = 0; i < values.length; i++){
//            if(i == values.length-1){
//                query.append(" ?)"); //final append, avoid trailing comma
//            }else{
//                query.append(" ?, ");
//            }
//        }
//        String sql = query.toString();
//        try {
//            PreparedStatement stmt = this.con.prepareStatement(sql);
//            for (int i = 0; i < values.length; i++){
//                stmt.setString(i+1, values[i]);
//            }
//
//            stmt.executeQuery();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void update(String[] cols, String[] values, String condition){
//        StringBuilder query = new StringBuilder("UPDATE ");
//        query.append(this.table);
//        query.append(" SET ");
//        for(int i = 0; i < values.length; i++){
//            String colSet = cols[i] + " = " + values[i];
//            query.append(colSet);
//        }
//        query.append(" WHERE ");
//        query.append(condition);
//    }
//
//
//    public void delete(String[] args){
//    }


}
