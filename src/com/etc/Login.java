package com.etc;

import com.var.UserVar;
import com.util.LoggerHandler;
import com.util.DBConnector;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Login {
    private static final Logger LOGGER = new
        LoggerHandler(Login.class.getName()).getGenericConsoleLogger();

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    UserVar user = null;

    /**
     * checks if user exists in database and if email and password matches
     * if so, it checks whether user has confirmed email.
     * @param UserVar
     */
    public UserVar userAuthentication(UserVar login) throws SQLException {
        String selectSQL = "select * from users where user_email = ? and user_password = ?";
        String updateSQL = "update users set user_status_session = ?, user_mode = ? where user_email = ?";

        try
        {
            LOGGER.info("Attempt to login.")
            con = DBConnector.getConnection();
            ps = con.prepareStatement(selectSQL);
            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());
            rs = ps.executeQuery();

            if (rs.next())
            {
                user = new UserVar();
                user.setEmail(rs.getString("user_email"));
                user.setUserId(rs.getInt("user_id"));
                user.setStatusEmail(rs.getBoolean("user_status_email"));
                user.setStatusSession(rs.getBoolean("user_status_session"));
                user.setFirstName(rs.getString("user_first_name"));

                if (user.isStatusSession())
                {
                    LOGGER.warning("User " + user.getEmail() + " already started a session");
                    return user;
                }
            }

            else
            {
                LOGGER.info("Email or password provided doesn't match");
                return login;
            }

            if (!(user.isStatusSession()))
            {
                LOGGER.info("Trying to validate session...")
                if (user.isStatusEmail())
                {
                    user.setStatusSession(true);
                    user.setUserMode(1);
                    LOGGER.info("User " + user.getEmail() + " logged successfully")
                }

                else
                {
                    user.setStatusSession(true);
                    user.setUserMode(4);
                    LOGGER.info("Email not confirmed. Session status: " + user.isStatusSession());
                }

                ps = con.prepareStatement(updateSQL);
                ps.setBoolean(1, user.isStatusEmail());
                ps.setInt(2, user.getUserMode());
                ps.setString(3, user.getEmail());
                ps.executeUpdate();
                return user;
            }

            else
            {
                System.out.printf("User %s already logged in.\n", user.getFirstName());
                return user;
            }
        }

        catch(SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally
        {
            if (con != null) con.close();
            if (ps != null) ps.close();
            if (rs != null) rs.close();
        }
        return login;
    }
}
