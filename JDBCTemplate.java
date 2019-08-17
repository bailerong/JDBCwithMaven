package JDBC;

import java.sql.*;

public abstract class JDBCTemplate {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int effect=-1;
    private String url;
    public JDBCTemplate(String host, Integer port, String username, String password, String database){
        this.url=String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false",
                host,
                port,
                database,
                username,
                password);
    }
//我们首先要了解我们的JDBC的编程流程
    public final void call(){
        //加载驱动
        loadDdriver();
        //2:创建连接 
        createConnect();
        //3：创建命令
        createStatment();
    
        //4：准备 SQL
        createSql();
        //执行
        execute();
        //处理结果
        //1第一类：返回int
        handlerResult();
        //第二类返回结果集

        //7关闭结果集，命令、链接
        closeAll();
    }

    private void closeAll() {
if(this.resultSet!=null){
    try {
        this.resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
if(this.connection!=null){
    try {
        this.connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
if(this.statement!=null){
    try {
        this.statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
this.effect=-1;
    }

    private void handlerResult() {
if(this.executeType()){
    this.handlerR(resultSet);
}else{
    this.handlerCUD(effect);
}
    }
    public abstract void handlerCUD(int effect);
    public abstract void handlerR(ResultSet resultSet);

    private void createStatment()  {
        try {
            statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void execute() {
        String sql = this.createSql();
        if (sql != null) {
            if (this.executeType()) {
                try {
                    resultSet = statement.executeQuery(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    effect = statement.executeUpdate(sql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public abstract String createSql();
public abstract boolean executeType();
    private void createConnect() {
        try {
            connection= DriverManager.getConnection
                    (this.url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDdriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
