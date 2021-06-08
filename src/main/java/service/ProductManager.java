package service;

import config.ConnectionJDBC;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static final String SELECT_ALL_PRODUCT = "select * from product";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product" + "  (name, quantity, color, description, category_id) VALUES " +
            " (?, ?, ?, ?, ?)";
    private static final String SELECT_PRODUCT_BY_ID = "select * from product where id =?";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,quantity= ?,color=?,description=?,category_id=? where id = ?";
    private static final String SELECT_PRODUCT_BY_NAME = "select * from product where name =?";
    CategoryManager categoryManager = new CategoryManager();
    Connection connection = ConnectionJDBC.getConnect();
    public ProductManager(){
    }
    public List<Product> selectAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);
        System.out.println(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            String color = resultSet.getString("color");
            String description = resultSet.getString("description");
            int category_id = resultSet.getInt("category_id");
            Category category = categoryManager.findCatById(category_id);
            Product product = new Product(id,name,quantity,color,description,category);
            productList.add(product);
        }
        return productList;
    }
    public void insertProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setInt(2, product.getQuantity());
        preparedStatement.setString(3, product.getColor());
        preparedStatement.setString(4, product.getDescription());
        preparedStatement.setInt(5, product.getCategory().getId());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }
    public Product selectProduct(int id) throws SQLException{
        Product product = null;
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            String color = resultSet.getString("color");
            String description = resultSet.getString("description");
            int category_id = resultSet.getInt("category_id");
            Category category = categoryManager.findCatById(category_id);
            product = new Product(id,name,quantity,color,description,category);
        }
        return product;
    }
    public void updateProduct(Product product) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);
        statement.setString(1, product.getName());
        statement.setInt(2, product.getQuantity());
        statement.setString(3, product.getColor());
        statement.setString(4, product.getDescription());
        statement.setInt(5,product.getCategory().getId());
        statement.setInt(6,product.getId());
        statement.executeUpdate();
    }
    public List<Product> selectbyName(String search)throws SQLException {
        List<Product> productList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_NAME);
        preparedStatement.setString(1,search);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int quantity = resultSet.getInt("quantity");
            String color = resultSet.getString("color");
            String description = resultSet.getString("description");
            int category_id = resultSet.getInt("category_id");
            Category category = categoryManager.findCatById(category_id);
            Product product = new Product(id,name,quantity,color,description,category);
            productList.add(product);
        }
        return productList;
    }




}
