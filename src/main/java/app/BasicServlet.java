package app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class BasicServlet extends HttpServlet {

    final Random random = new Random();
    String generatedSeq = "";

    private void generateNums(){            //Генерируем значения для исходного массива
        generatedSeq = "";
        for (int i =0; i<10;i++)
        {
            if(i==9)
            {
                generatedSeq += String.valueOf(random.nextInt(100)+1);
            }else {
                generatedSeq += String.valueOf(random.nextInt(100)+1) + ", ";
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //Обрабатываем Get запрос
        String url = req.getRequestURI();
        System.out.println("URL is: " + url);
        if(url.equals("/BubbleSort")){
            generateNums();
            req.setAttribute("arr",generatedSeq);
            req.getRequestDispatcher("/WEB-INF/view/BubbleSort.jsp").forward(req,resp);
        }

        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req,resp);

    }

    private int[] convertStrToInt(){
        String strArr[] = generatedSeq.split(", ");
        int numArr[] = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            numArr[i] = Integer.parseInt(strArr[i]);
            // System.out.println(numArr[i]);
        }
        return numArr;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //обрабатываем post запрос из submit
        generateNums();
        req.setAttribute("arr",generatedSeq);   //Задаём новые значения
        bubbleSortApp a = new bubbleSortApp();     //Создаём объект класса bubbleSortApp для получения доступа к bubblesort()

        int[] numsArr = convertStrToInt();      //Конвертируем строку в массив с типом int

        generatedSeq = a.bubble_sort(numsArr,10); //Cортируем значения и результат сохраняем
        try {
            a.insertIntoTable(generatedSeq);
        } catch (SQLException e) {
            e.getMessage();
        }
        req.setAttribute("sortedarr",generatedSeq); //Присваеваем результат по ссылке sortedarr в JSP
        req.getRequestDispatcher("/WEB-INF/view/BubbleSort.jsp").forward(req,resp); //Направляем запрос на этот же сервлет для генерерации и сортировки новых значений
    }
}
