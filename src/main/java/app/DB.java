package app;

import java.sql.*;

public class DB {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/bubbleSort_db"; //Адрес БД
    //Данные пользователя
    private static final String USERNAME = "postgres";              //Логин
    private static final String PASSWORD = "evgenhalk1999";              //Пароль
    private static final String DRIVER = "org.postgresql.Driver";   //путь к JDBC драйверу

    private static Connection conn = null;
    private static Statement statement = null;


    private static Connection getDBConnection(){            //Подключение к БД
        Connection conn = null;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            conn = DriverManager.getConnection(URL,USERNAME, PASSWORD);
            System.out.println("Соединение с "+ URL + " установлено");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Соединение с "+ URL + "не установлено");
        }
        return conn;
    }

    public static void createDbUserTable() throws SQLException{            //Создание таблицы в БД

        String createTableSql = "CREATE TABLE sort_results("
                +"id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
                +"result VARCHAR(100) NOT NULL"
                +")";

        try {
            conn = getDBConnection();
            statement = conn.createStatement();

            //Start SQL query
            statement.execute(createTableSql);
            System.out.println("Таблица \"sort_results\" была успешно создана.");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if(statement != null) {
                statement.close();
            }if(conn != null){
                conn.close();
            }
        }
    }

    public static void insertIntoTable(String sortedNums) throws SQLException {             //Добавляем значение в созданную таблицу

        String InsertSortedNums = "INSERT INTO sort_results(result) VALUES ((?))";

        try {
            conn = getDBConnection();
            PreparedStatement statement = conn.prepareStatement(InsertSortedNums);
            statement.setString(1,sortedNums);
            //Start SQL query
            statement.executeUpdate();
            System.out.println("Данные успешно добавлены в \"sort_results\".");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if(statement != null) {
                statement.close();
            }if(conn != null){
                conn.close();
            }
        }
    }
}
