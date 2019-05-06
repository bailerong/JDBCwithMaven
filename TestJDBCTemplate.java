package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TestJDBCTemplate {
    public static void main(String[] args) {
        JDBCTemplate jdbcTemplate=new JDBCTemplate
                ("localhost",3306,"root","369888abc","memo") {
            @Override
            public void handlerCUD(int effect) {
                //do nothing
            }

            @Override
            public void handlerR(ResultSet resultSet) {
            if(resultSet!=null){
                try {
                    while(resultSet.next()){
                        //如果返回true表示有下一行记录，否则无计记录
                        int id=resultSet.getInt("id");
                        String name=resultSet.getString("name");
                        LocalDateTime createdTime=
                                resultSet.getTimestamp("created_time").toLocalDateTime();
                        LocalDateTime modifyTime=
                                resultSet.getTimestamp("modify_time").toLocalDateTime();
                        System.out.println(
                                String.format(
                                        "编号:%d,名称:%s,创建时间:%s,修改时间:%s",
                                        id,
                                        name,
                                        createdTime.toString(),
                                        modifyTime.toString()
                                )
                        );
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            }

            @Override
            public String createSql() {
                return "select id,name,created_time,modify_time from memo_group";
            }

            @Override
            public boolean executeType() {
                return true;
            }
        };
        jdbcTemplate.call();
    }
}
