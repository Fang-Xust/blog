package frank.uti;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DemoSingleton {

    //多线程环境下，调用DemoSingleton.getDataSource()也是线程安全的，
    //JVM在类加载的时候，类只加载一次，并且是线程安全的
    public static DataSource getDataSource(){
        return Holder.getDataSource();
    }

    private static class Holder{
        private static final DataSource DATA_SOURCE = new MysqlDataSource();

        static {
            //初始化
        }
        public static DataSource getDataSource(){
            return DATA_SOURCE;
        }
    }
}
