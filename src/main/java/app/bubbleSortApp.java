package app;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class bubbleSortApp {

    public static class bubbleSort {

        private ArrayList<Integer> list = new ArrayList<>();
        private int N = 10;

        private void setList(ArrayList<Integer> list){
            this.list = list;
        }

        private ArrayList<Integer> getList(){
            return list;
        }

        private ArrayList<Integer> generateArrElems(){    //Генерация значений массива в диапазоне: 1..100
            final Random random = new Random();
            ArrayList<Integer> TMPlist = new ArrayList<>();
            for(int i = 0; i<N; i++) {
                TMPlist.add(i,random.nextInt(100)+1);
            }
            return TMPlist;
        }

        private ArrayList<Integer> inArrElems(){
            Scanner in = new Scanner(System.in);
            ArrayList<Integer> TMPlist = new ArrayList<>();
            for(int i = 0; i<N; i++) {
                TMPlist.add(i,in.nextInt());
            }
            return TMPlist;
        }

        public void bubble_sort() {                     //Пузырьковая сортировка
            for (int i=0; i<list.size()-1; i++) {
                for (int j = 0; j< list.size()-i-1; j++) {
                    if (list.get(j) > list.get(j+1)) {
                        int tmp = list.get(j);
                        list.set(j,list.get(j+1));
                        list.set((j+1),tmp);
                    }
                }
            }
        }

        public String listToStr(){
            List<String> strings = list.stream().map(Object::toString).collect(Collectors.toList());
            String[] myArray = strings.toArray(new String[N]);
            return String.join(", ", myArray);

        }
    }


    public static void main(String[] args) {
        ArrayList<Integer> models;
        bubbleSort obj = new bubbleSort();
        //DB object_db  = new DB();

        obj.setList(obj.generateArrElems());
        obj.bubble_sort();
        models = obj.getList();

        for(int i =0; i<models.size(); i++){
            System.out.println(models.get(i));
        }

        System.out.println(models);
        System.out.println(obj.listToStr());

        /*try {
            //object_db.createDbUserTable();
            object_db.insertIntoTable(sortedNums);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }
}