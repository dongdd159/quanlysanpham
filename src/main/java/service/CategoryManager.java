package service;

import config.ConnectionJDBC;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private static final String GET_CAT_BY_ID = "select * from category where id =? ";
    private static final String GET_ALL_CAT = "select * from category";
    Connection connection = ConnectionJDBC.getConnect();

    public CategoryManager() {
    }
    public Category findCatById(int id) throws SQLException {
        Category category = null;
        PreparedStatement preparedStatement = connection.prepareStatement(GET_CAT_BY_ID);
        preparedStatement.setInt(1, id);
        System.out.println(preparedStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            category = new Category(id,name);
        }
        return category;
    }
    public List<Category> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(GET_ALL_CAT);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Category category = new Category(id, name);
            categoryList.add(category);
        }
        return categoryList;
    }
}
