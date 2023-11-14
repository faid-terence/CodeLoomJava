// package com.faidterence.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserCRUD{
	
	public static void main (String[] args) {
		UserCRUD f = new UserCRUD();
		f.connectDB();
		
		Scanner sc = new Scanner(System.in);  
		System.out.println("Enter Reg NUmber");
		String reg = sc.nextLine();
		System.out.println("Enter First Name?");
		String firstname = sc.nextLine();
		System.out.println("Enter Last Name?");
		String lastname = sc.nextLine();
		f.registerUser(reg, firstname, lastname);
		f.displayUsers();
		System.out.println("Enter Reg NUmber to Search ");
		String regnumber = sc.nextLine();
		f.searchUserById(regnumber);
		
		System.out.println("Enter Reg NUmber to Update ");
		String regnumberToDelete = sc.nextLine();
		f.updateUserById(reg, firstname, lastname);
		f.deleteUserById(regnumberToDelete);
	}
	
	public Connection connectDB() {
         Connection connection = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",  "postgres");
			if(connection!=null) {
				System.out.println("Connection OK");
			}
			else {
				System.out.println("Connection Failed");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
    public int registerUser(String id, String firstName, String lastName) {
        Connection connection = connectDB();
        PreparedStatement register = null;
        int res = 0;

        try {
            // Prepare the SQL query for user registration
            register = connection.prepareStatement("INSERT INTO \"users\" (\"reg_no\", \"firstName\", \"lastName\") VALUES (?, ?, ?)");

            // Set the parameter values in the prepared statement
            register.setString(1, id);
            register.setString(2, firstName);
            register.setString(3, lastName);

            // Execute the insert query
            res = register.executeUpdate();

            if (res > 0) {
                System.out.println("User registered successfully");
            } else {
                System.out.println("Failed to register user");
            }

        } catch (SQLException ex) {
            // Handle specific SQL exceptions or log them for debugging
            ex.printStackTrace();
            System.out.println("Error registering user. Please check your input.");
        } finally {
            // Close the PreparedStatement and Connection in the finally block
            try {
                if (register != null) {
                    register.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return res;
    }


	public void displayUsers() {
		Connection connection = connectDB();
		Statement s = null;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery("Select * from users");
			while(rs.next()) {
				String reg = rs.getString("reg_no");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				System.out.println("User ID: "+reg);
				System.out.println("User firstName: "+firstName);
				System.out.println("User lastName: "+lastName);
				System.out.println(".............................................");
				
			}
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}
	public void searchUserById(String id) {
	    Connection connection = connectDB();
	    PreparedStatement search = null;
	    try {
	        search = connection.prepareStatement("SELECT * FROM users WHERE reg_no=?");
	        search.setString(1, id);
	        ResultSet rs = search.executeQuery();

	        while (rs.next()) {
	            String reg = rs.getString("reg_no");
	            String firstName = rs.getString("firstName");
	            String lastName = rs.getString("lastName");

	            System.out.println("User ID: " + reg);
	            System.out.println("User firstName: " + firstName);
	            System.out.println("User lastName: " + lastName);
	            System.out.println(".............................................");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (search != null) {
	                search.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	  public void updateUserById(String id, String firstName, String lastName) {
	        Connection connection = connectDB();
	        PreparedStatement update = null;

	        try {
	            
	        	update = connection.prepareStatement("UPDATE users SET \"firstName\" = ?, \"lastName\" = ? WHERE reg_no = ?");
	            update.setString(1, firstName);
	            update.setString(2, lastName);
	            update.setString(3, id);

	           
	            int rowsAffected = update.executeUpdate();

	            
	            if (rowsAffected > 0) {
	                System.out.println("User updated successfully");
	            } else {
	                System.out.println("No user found with the provided ID");
	            }

	        } catch (SQLException e) {
	           
	            e.printStackTrace();
	        } finally {
	            
	            try {
	                if (update != null) {
	                    update.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	  
	    public void deleteUserById(String id) {
	        Connection connection = connectDB();
	        PreparedStatement delete = null;

	        try {
	            delete = connection.prepareStatement("DELETE FROM users WHERE reg_no = ?");
	            
	            
	            delete.setString(1, id);
	            int rowsAffected = delete.executeUpdate();

	           
	            if (rowsAffected > 0) {
	                System.out.println("User deleted successfully");
	            } else {
	                System.out.println("No user found with the provided ID");
	            }

	        } catch (SQLException e) {
	           
	            e.printStackTrace();
	        } finally {
	            
	            try {
	                if (delete != null) {
	                    delete.close();
	                }
	                if (connection != null) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
