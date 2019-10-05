package com.etc;

import com.var.UserVar;
import com.util.DBConnector;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author fsaulo
 */
public class Login {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    UserVar user = null;

    /**
     * checks if user exists in database
     * and if email and password matches
     * if so, it checks whether user has
     * confirmed email.
     * @param UserVar
     */
    public UserVar userAuthentication(UserVar login) throws SQLException {

        String selectSQL = "select * from users where user_email = ? and user_password = ?";
        String updateSQL = "update users set user_status_session = ?, user_mode = ? where user_email = ?";

        try {

            con = DBConnector.getConnection();
            ps = con.prepareStatement(selectSQL);

            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());

            rs = ps.executeQuery();

            if (rs.next()) {

                user = new UserVar();

                user.setEmail(rs.getString("user_email"));
                user.setUserId(rs.getInt("user_id"));
                user.setStatusEmail(rs.getBoolean("user_status_email"));
                user.setStatusSession(rs.getBoolean("user_status_session"));
                user.setFirstName(rs.getString("user_first_name"));

                if (user.isStatusSession()) {

                    System.out.printf("User %s already started session\n", user.getEmail());
                    return user;

                }
            }

            else {

                System.out.println("email or password are incorrect");
                return login;

            }

            if (!(user.isStatusSession())) {

                System.out.println("validating session...");

                if (user.isStatusEmail()) {

                    user.setStatusSession(true);
                    user.setUserMode(1);
                    System.out.printf("%s logged with success.\n", user.getFirstName());

                }

                else {

                    user.setStatusSession(true);
                    user.setUserMode(4);
                    System.out.printf("Email registered in our database: %s\n", user.getEmail());
                    System.out.printf("Your email must be confirmed, " + "please check your mail box\n");
                    System.out.println(user.isStatusEmail());

                }

                ps = con.prepareStatement(updateSQL);
                ps.setBoolean(1, user.isStatusEmail());
                ps.setInt(2, user.getUserMode());
                ps.setString(3, user.getEmail());
                ps.executeUpdate();

                return user;
            }

            else {

                System.out.printf("User %s already logged in.\n", user.getFirstName());
                return user;

            }
        }

        catch(SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {

            if (con != null) con.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();

        }
        return login;
    }
}
