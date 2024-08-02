package dao;

import core.Database;
import entity.Customer;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDao {
    private Connection connection;

    public CustomerDao() {
        this.connection = Database.getInstance();
    }

    public ArrayList<Customer> findAll() {
        ArrayList<Customer> customers = new ArrayList<>();

        ResultSet rs = null;
        try {
            rs = this.connection.createStatement().executeQuery("SELECT * FROM cutomer");
            while (rs.next()) {
                customers.add(this.match(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;

    }
    public Customer match(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getNString("name"));
        customer.setMail(rs.getNString("mail"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setType(Customer.TYPE.valueOf(rs.getString("type")));

        return customer;
    }
}
