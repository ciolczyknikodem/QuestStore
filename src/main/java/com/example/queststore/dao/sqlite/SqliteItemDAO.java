package com.example.queststore.dao.sqlite;

import com.example.queststore.dao.ItemDAO;
import com.example.queststore.data.DbHelper;
import com.example.queststore.data.contracts.ItemEntry;
import com.example.queststore.data.statements.ItemStatement;
import com.example.queststore.models.Item;
import com.example.queststore.utils.QueryLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SqliteItemDAO extends DbHelper implements ItemDAO {

    private ItemStatement itemStatement = new ItemStatement();

    @Override
    public List<Item> getItemsByStudentId(int studentId) {
        String sqlStatement = itemStatement.getItemsByStudentId();
        PreparedStatement statement = getPreparedStatementBy(Collections.singletonList(studentId), sqlStatement);
        return getItemsBy(statement);
    }

    @Override
    public List<Item> getItemsByCategory(String category) {
        String sqlStatement = itemStatement.getItemsByCategory();
        PreparedStatement statement = getPreparedStatementBy(Collections.singletonList(category), sqlStatement);
        return getItemsBy(statement);
    }

    @Override
    public List<Item> getAllItems() {
        String sqlStatement = itemStatement.getAllItems();
        PreparedStatement statement = getPreparedStatementBy(Collections.emptyList(), sqlStatement);
        return getItemsBy(statement);
    }


    private List<Item> getItemsBy(PreparedStatement statement) {

        List<Item> items = new ArrayList<>();
        try {
            ResultSet resultSet = query(statement);
            while (resultSet.next()) {
                items.add(new Item (
                        resultSet.getInt(ItemEntry.ID),
                        resultSet.getString(ItemEntry.ITEM_NAME),
                        resultSet.getInt(ItemEntry.PRICE),
                        resultSet.getString(ItemEntry.DESCRIPTION),
                        resultSet.getString(ItemEntry.CATEGORY)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            QueryLogger.logInfo(e.getClass().getName() + ": " + e.getMessage(), "logs/errors.log");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
        return items;
    }

    @Override
    public Item getItemById(int id) {
        String sqlStatement = itemStatement.getItemById();
        PreparedStatement statement = getPreparedStatementBy(Collections.singletonList(id), sqlStatement);
        return getItemFromStore(statement);
    }

    @Override
    public Item getItemByName(String itemName) {
        String sqlStatement = itemStatement.getItemByName();
        PreparedStatement statement = getPreparedStatementBy(Collections.singletonList(itemName), sqlStatement);
        return getItemFromStore(statement);
    }

    private Item getItemFromStore(PreparedStatement statement) {

        Item item = null;
        try {
            ResultSet resultSet = query(statement);
            while (resultSet.next()) {
                item = new Item (
                        resultSet.getInt(ItemEntry.ID),
                        resultSet.getString(ItemEntry.ITEM_NAME),
                        resultSet.getInt(ItemEntry.PRICE),
                        resultSet.getString(ItemEntry.DESCRIPTION),
                        resultSet.getString(ItemEntry.CATEGORY)
                );
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            QueryLogger.logInfo(e.getClass().getName() + ": " + e.getMessage(), "logs/errors.log");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            closeConnection();
        }
        return item;
    }



    public boolean add(Item item) {
        String sqlStatement = itemStatement.addItemStatement();
        PreparedStatement statement = getPreparedStatementBy(Arrays.asList(item.getName(), item.getDescription(),
                item.getPrice(), item.getCategory()), sqlStatement);
        return update(statement);
    }

    public boolean update(Item item) {
        String sqlStatement = itemStatement.updateQueryStatement();
        PreparedStatement statement = getPreparedStatementBy(Arrays.asList(item.getName(), item.getDescription(),
                item.getPrice(), item.getCategory(), item.getId()), sqlStatement);
        return update(statement);
    }

    @Override
    public boolean delete(Item item) {
        String sqlStatement = itemStatement.deleteItemStatement();
        PreparedStatement statement = getPreparedStatementBy(Collections.singletonList(item.getId()), sqlStatement);
        return update(statement);
    }
}