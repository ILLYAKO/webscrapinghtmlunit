package view;



import domain.Item;
import service.Scraper;
import service.WriteFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletController extends HttpServlet {

    private Item item = new Item();


//    public void init() {
//        carService = new CarService();
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String action = request.getPathInfo();
        try {
            switch (action) {
                case "/find":
                    find(request, response);
                    break;
                case "/new":
                    find(request, response);
                    break;
                case "/insert":
                    insertCar(request, response);
                    break;
                case "/delete":
                    deleteCar(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateCar(request, response);
                    break;
                default:
                    last(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void last(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("I am last.");
    }
    private void find(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        item.setTitle(request.getParameter("jobTitle"));
        item.setCity(request.getParameter("city"));

        Scraper scraper = new Scraper(item);

        try {WriteFile wf = new WriteFile(item,scraper.getJobItems());}
        catch (IOException ioe){System.out.println("Exception Message: " + ioe.getMessage());}

        request.setAttribute("item", item);
        request.setAttribute("jobItems", scraper.getJobItems());


        RequestDispatcher dispatcher = request.getRequestDispatcher("/joblist.jsp");
        dispatcher.forward(request, response);

    }
    private void listCar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       // List<Car> listCar = carService.findAll();
      // request.setAttribute("listCar", listCar);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/CarList1.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/CarForm.jsp");
       // request.setAttribute("types", CarType.values());
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
     //   Car existingCar = carService.findCarById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/CarForm.jsp");
       // request.setAttribute("types", CarType.values());
      //  request.setAttribute("car", existingCar);
        dispatcher.forward(request, response);

    }

    private void insertCar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = UUID.randomUUID().toString();
        String plate = request.getParameter("plate");
        String color = request.getParameter("color");
        int year = Integer.parseInt(request.getParameter("year"));
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String typeStr = request.getParameter("type");

      //  Car car = new Car(id,
      //          plate, color, year, brand, model, CarType.valueOf(typeStr));
       // carService.addCar(car);
        response.sendRedirect("list");
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String id = request.getParameter("id");
        String plate = request.getParameter("plate");
        String color = request.getParameter("color");
        int year = Integer.parseInt(request.getParameter("year"));
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String typeStr = request.getParameter("type");

      //  Car car = new Car(id,
       //         plate, color, year, brand, model, CarType.valueOf(typeStr));
      //  carService.modifyCar(car);
        response.sendRedirect("list");
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");

       // Car car = new Car(id);
       // carService.removeCar(car);
        response.sendRedirect("list");

    }
}
