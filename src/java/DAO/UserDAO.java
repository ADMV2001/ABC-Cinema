package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.User;
import util.DBConnect;

public class UserDAO {
    
    public static boolean registerUser(String fname,String lname,String email,String mobile, String username, String password)
    {
        boolean isRegistered = false;
        
        try(Connection con = DBConnect.getConnection();
             Statement stmt = con.createStatement())
        {   
            String query = "INSERT INTO users values(0,'"+fname+"', '"+lname+"', '"+email+"', '"+mobile+"', '"+username+"', '"+password+"')";
            int rs = stmt.executeUpdate(query);
            
            if(rs > 0)
            {
                isRegistered = true;
            }
            else
            {
                isRegistered = false;
            }                
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return isRegistered;
    }

    public List<User> getAllUsers() {
        
        List<User> userList = new ArrayList<>();
        
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Iterate through the result set and create Book objects
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                String mobile = rs.getString("mobile");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User();
                user.setUserId(userId);
                user.setFirstName(fname);
                user.setLastName(lname);
                user.setEmail(email);
                user.setMobile(mobile);
                user.setUsername(username);
                user.setPassword(password);
                
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public User getUserById(int id)
    {
        User user = null;
        
        try {
            Connection con = DBConnect.getConnection();
            String query = "SELECT * FROM users where user_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                user = new User();
                user.setUserId(rs.getInt("user_id"));       // Set the book ID
                user.setFirstName(rs.getString("first_name")); // Set the book name
                user.setLastName(rs.getString("last_name")); // Set the author name
                user.setEmail(rs.getString("email"));       // Set the price
                user.setMobile(rs.getString("mobile")); // Set the category
                user.setUsername(rs.getString("username"));     // Set the status
                user.setPassword(rs.getString("password")); 
            }
            
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return user;
    }
    
}
