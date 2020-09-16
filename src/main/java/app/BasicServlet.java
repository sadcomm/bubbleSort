package app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class BasicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //Обрабатываем Get запрос
        String url = req.getRequestURI();
        System.out.println("URL is: " + url);
        if(url.equals("/BubbleSort")){
            bubbleSortApp sortObj = new bubbleSortApp();
            sortObj.generateArrElems();
            String tmpstr = sortObj.listToStr();
            req.setAttribute("arr",tmpstr);
            req.getRequestDispatcher("/WEB-INF/view/BubbleSort.jsp").forward(req,resp);
        }

        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { //обрабатываем post запрос из submit
        bubbleSortApp sortObj = new bubbleSortApp();
        sortObj.generateArrElems();
        String str2 = sortObj.listToStr();      //Конвертируем строку в массив с типом int
        req.setAttribute("arr",str2);   //Задаём новые значения
        bubbleSortApp a = new bubbleSortApp();     //Создаём объект класса bubbleSortApp для получения доступа к bubblesort()

        sortObj.bubble_sort();
        String str = sortObj.listToStr();
        System.out.println(str);
        try {
            DB.insertIntoTable(str);
        } catch (SQLException e) {
            e.getMessage();
        }
        req.setAttribute("sortedarr",str); //Присваеваем результат по ссылке sortedarr в JSP
        req.getRequestDispatcher("/WEB-INF/view/BubbleSort.jsp").forward(req,resp); //Направляем запрос на этот же сервлет для генерерации и сортировки новых значений
    }
}
