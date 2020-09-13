package app;

import java.sql.*;
import java.util.Scanner;

public class bubbleSortApp {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/bubbleSort_db"; //Адрес БД
    //Данные пользователя
    private static final String USERNAME = "postgres";              //Логин
    private static final String PASSWORD = "postgres";              //Пароль
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

    private static void createDbUserTable() throws SQLException{            //Создание таблицы в БД

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

    public static String bubble_sort(int[] nums, int N) {               //Пузырьковая сортировка
        StringBuilder sortedNums = new StringBuilder();
        for (int i=0; i<N-1; i++){
            for (int j=0; j<N-i-1; j++){
                if (nums[j] > nums[j+1]){
                    int tmp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = tmp;
                }
            }
        }
        for (int i=0; i<N; i++){
            if (i==N-1)
            {
                sortedNums.append(nums[i]);
            }else{
                sortedNums.append(nums[i]).append(", ");
            }
        }
        return sortedNums.toString();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N;                                                              //Количество элементов в массиве
        System.out.print("Сколько всего элементов в массиве? >> ");
        N = in.nextInt();

        int[] nums = new int[N];
        System.out.print("Введите элементы массива >>\n");
        for (int i=0; i<N; i++){
            nums[i] = in.nextInt();
        }

        String sortedNums = bubble_sort(nums,N);
        System.out.print("Отсортированнный массив: " + sortedNums + "\n");

        try {
            //createDbUserTable();
            insertIntoTable(sortedNums);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}