/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.IOException;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public interface user_management_interface {
    // Validation Functions
    public boolean validateClientFields(JTextField name, JTextField email, JTextField phone, JTextArea address);
    public boolean validateUserFields(JTextField username, JTextField name, JTextField email, JTextField phone,
            JTextArea address, String role_text);
    public boolean validatePasswordField(JPasswordField password);
    
    // User CRUD Functions
    public void addUserInformation(String username_text, String password_text, String repassword_text, 
            String name_text, String address_text, String email_text, String contact_text, String gender_text, String role_text);
    public boolean deleteUserData(String filepath, String removeterm, int positionOfTerm, String delimiter);
    public boolean updateUserData(String id, String name, String phone_number, String address, String email, 
            String gender, String role) throws IOException;
    public boolean updateClientData(String id, String name, String phone_number, String address, String email, 
            String gender) throws IOException;
    public boolean updateProfileData(String id, String name, String phone_number, String address, String email, 
            String gender) throws IOException;
    public boolean updateUserPwd(String id, String password) throws IOException;
    public boolean updateForgotPassword(String username, String password) throws IOException;
}
