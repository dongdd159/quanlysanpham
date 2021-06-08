package controller;

import model.Category;
import model.Product;
import service.CategoryManager;
import service.ProductManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/Products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductManager productManager;
    private CategoryManager categoryManager;
    public void init() {
        productManager = new ProductManager();
        categoryManager = new CategoryManager();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showNewProductForm(request, response);
                    break;
                case "edit":
                    showUpdateForm(request, response);
                    break;
//                case "delete":
//                    deleteProduct(request, response);
//                    break;
                case "search":
                    SearchProduct(request, response);
                      break;
                default:
                        listProduct(request,response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    CreateProduct(request,response);
                    break;
                case "edit":
                    UpdateProduct(request,response);
                    break;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Product> productList = productManager.selectAllProducts();
        request.setAttribute("listProduct", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }
    private void showNewProductForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/create.jsp");
        List<Category> categoryList = categoryManager.findAll();
        request.setAttribute("categories",categoryList);
        dispatcher.forward(request, response);
    }
    private void CreateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int category_id = Integer.parseInt(request.getParameter("category"));
        Category category = categoryManager.findCatById(category_id);
        Product product = new Product(name,quantity,color,description,category);
        productManager.insertProduct(product);
        List<Product> productList = productManager.selectAllProducts();
        request.setAttribute("listProduct", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }
    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productManager.selectProduct(id);
        List<Category> categories = categoryManager.findAll();
        List<Category> categoryList = new ArrayList<>();
        for (Category category:categories) {
            if (category.getId()==product.getCategory().getId()){
                continue;
            }
            categoryList.add(category);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/update.jsp");
        request.setAttribute("categories",categoryList);
        request.setAttribute("product", product);
        dispatcher.forward(request, response);
    }
    private void UpdateProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int category_id = Integer.parseInt(request.getParameter("category"));
        Category category = categoryManager.findCatById(category_id);
        Product product = new Product(id,name,quantity,color,description,category);
        productManager.updateProduct(product);
        List<Product> productList = productManager.selectAllProducts();
        request.setAttribute("listProduct", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }
    private void SearchProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String search = request.getParameter("proname");
        List<Product> productList = productManager.selectbyName(search);
        request.setAttribute("listProduct", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/search.jsp");
        dispatcher.forward(request, response);
    }
}
