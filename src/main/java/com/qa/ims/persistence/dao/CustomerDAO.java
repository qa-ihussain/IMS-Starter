package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DBUtils;

public class CustomerDAO implements Dao<Customer> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Customer modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long customerID = resultSet.getLong("customer_id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String emailAddress = resultSet.getString("email_address");
		String addressLine1 = resultSet.getString("address_line_1");
		String postcode = resultSet.getString("postcode");
		return new Customer(customerID, firstName, lastName, emailAddress, addressLine1, postcode);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Customer> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("select * from customer");) {
			List<Customer> customer = new ArrayList<>();
			while (resultSet.next()) {
				customer.add(modelFromResultSet(resultSet));
			}
			return customer;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Customer readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customer ORDER BY customer_id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Customer create(Customer customer) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO customer(first_name, last_name, email_address, address_line_1, postcode) values('" + customer.getFirstName()
					+ "','" + customer.getLastName() + "','" + customer.getEmailAddress() + "','" + customer.getAddressLine1() + "','" + customer.getPostcode() + "')");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Customer readCustomer(Long customerID) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customer where customer_id = " + customerID);) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 */
	@Override
	public Customer update(Customer customer) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("update customer set first_name ='" + customer.getFirstName() + "', last_name ='"
					+ customer.getLastName() + "' email_address =" + customer.getEmailAddress()
					 + "' address_line_1 =" + customer.getAddressLine1() + "'postcode =" + customer.getPostcode() + "'where customer_id =" + customer.getCustomerID());
			return readCustomer(customer.getCustomerID());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public int delete(long customerID) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			return statement.executeUpdate("delete from customer where customer_id = " + customerID);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
