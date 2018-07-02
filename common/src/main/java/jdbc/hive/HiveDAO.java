package jdbc.hive;

import jdbc.service.ReflectionService;
import jdbc.conn.DBConnection;
import tools.FileUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author yidxue
 */
public class HiveDAO {

    private String URLHIVE = "jdbc:hive2://10.29.42.49:10000/default";
    private String DBType = "hive";
    private Connection conn;

    public HiveDAO() {
        this.conn = DBConnection.getConnection(DBType, URLHIVE);
    }

    public <T> void create(String tablename, Class<T> clazz) {
        try {
            HashMap<String, String> colAndType = ReflectionService.getColAndType(clazz);
            String fields = colAndType.entrySet().stream().map(x -> x.getKey() + "\t" + x.getValue()).collect(Collectors.joining(","));
            Statement stmt = this.conn.createStatement();
            String sql = "create table if not exists " + tablename + "(" + fields + ")"
                             + " row format delimited fields terminated by '\\t'"
                             + " collection items terminated by ','";

            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void loadToHive(ArrayList<T> records, Class<T> clazz, String tableName, String path) {
        try {
            FileUtil.writeByStream(records.stream().map(x -> ReflectionService.getValues(x, clazz).stream().collect(Collectors.joining("\t"))).collect(Collectors.toList()), path);
            // 每一层目录都设置 777 权限
            FileUtil.changeFolderPermission(path, true);
            Statement stmt = this.conn.createStatement();
            String sql = "LOAD DATA LOCAL INPATH '" + path + "' into table " + tableName;
            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}