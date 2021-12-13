/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import common.input_validation;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.*;
import javax.swing.JComboBox;

/**
 *
 * @author chinojen7
 */
public class user_management extends input_validation {
    
    private String userID;
    private String saveDir; 
    private int newUserID;
    private String idprefix;
    private String role;
    
    private Color fgtxt = new Color(255, 255, 255);
    
    private int mouseX;
    private int mouseY;
    
    public user_management() {
    }
    
    public boolean validateClientFields(JTextField name, JTextField email, JTextField phone, JTextArea address) {
        boolean validation = true;
        
        if (!this.nameValidation(name.getText())) {
            validation = false;
            name.setBackground(new Color(143, 168, 50));
        } else {
            name.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.emailValidation(email.getText())) {
            validation = false;
            email.setBackground(new Color(143, 168, 50));
        } else {
            email.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.contactValidation(phone.getText())) {
            validation = false;
            phone.setBackground(new Color(143, 168, 50));
        } else {
            phone.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.addressValidation(address.getText())) {
            validation = false;
            address.setBackground(new Color(143, 168, 50));
        } else {
            address.setBackground(new Color(0, 0, 0));
        }    
        
        
        return validation;
        
    }
    
    public boolean validateUserFields(JTextField username, JTextField name, JTextField email, JTextField phone, JTextArea address, String role_text) {
        boolean validation = true;
        
        this.set_role(role_text);
        
        
        if (!this.usernameValidation(username.getText())) {
            validation = false;     
            if (usernameValidator(username.getText())) {
                validation = false;
                JOptionPane.showMessageDialog(null, "Username is already taken!", "Username taken", JOptionPane.WARNING_MESSAGE);
            }
            
            username.setBackground(new Color(143, 168, 50));
        } else {
            if (usernameValidator(username.getText())) {
                validation = false;
                JOptionPane.showMessageDialog(null, "Username is already taken!", "Username taken", JOptionPane.WARNING_MESSAGE);
                username.setBackground(new Color(143, 168, 50));
            } else {
                username.setBackground(new Color(0, 0, 0));
            }
        }
        
        if (!this.nameValidation(name.getText())) {
            validation = false;
            name.setBackground(new Color(143, 168, 50));
        } else {
            name.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.emailValidation(email.getText())) {
            validation = false;
            email.setBackground(new Color(143, 168, 50));
        } else {
            email.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.contactValidation(phone.getText())) {
            validation = false;
            phone.setBackground(new Color(143, 168, 50));
        } else {
            phone.setBackground(new Color(0, 0, 0));
        }
        
        if (!this.addressValidation(address.getText())) {
            validation = false;
            address.setBackground(new Color(143, 168, 50));
        } else {
            address.setBackground(new Color(0, 0, 0));
        }    
        
        
        return validation;
        
    }
    

    private boolean similarPassword(String currPass, String comparePass) {       
        boolean isSimilar = false;
        if (this.passwordConfirmation(currPass, comparePass)) {
            isSimilar = true;
        } else {
            isSimilar = false;
        }
        
        return isSimilar;   
    }
    
    private void set_role(String role) {
        this.role = role;
    }
    
    private String get_role() {
        if (this.role.equals("Technician") || this.role.equals("Management")) {
            this.idprefix = "Us";
        } else {
            this.idprefix = "Cus";
        }
        return this.role;
    }
    
    private String select_account_filename() {
        String filename;
        
        this.get_role();
        if(this.idprefix.equals("Us")) {
            filename = "user_details.txt";
        } else {
            filename = "customer_details.txt";
        }
        
        return filename;
    }

    private void usersIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File acc_file = new File(saveDir + this.select_account_filename());
            if (!acc_file.exists()) {
                acc_file.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(acc_file);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split("\\|");
                    matchedID[0] = matchedID[0].replace(this.idprefix, "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No user(s) record was found! Restarting database entry.", "Users database is empty!", JOptionPane.ERROR_MESSAGE);
                    newUserID = 1;
                } else {
                    newUserID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Users ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean usernameValidator(String username) {
        String userTmp = username;
        String[] matchedID = null;
        boolean notAvailable = false;

        saveDir = System.getProperty("user.dir")+"/src/database/";
        File acc_file = new File(saveDir + this.select_account_filename());
        Scanner inputFile;
        try {
            if (!acc_file.exists()) {
                acc_file.createNewFile();
            }

            inputFile = new Scanner(acc_file);
            while (inputFile.hasNext()) {
                String entry = inputFile.nextLine();
                matchedID = entry.split("\\|");
                if (userTmp.equals(matchedID[1])) {
                    notAvailable = true;
                }
            }
            inputFile.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return notAvailable;
    }

    public void addUserInformation(String username_text, String password_text, String repassword_text, String name_text, String address_text, String email_text, String contact_text, String gender_text, String role_text) {
        saveDir = System.getProperty("user.dir");
        DecimalFormat dc = new DecimalFormat("000000");
        try {
            this.set_role(role_text);
            usersIncrementor();
            userID = dc.format(newUserID);

            String name = name_text;
            String address = address_text;
            String email = email_text;
            String contact = contact_text;
            String gender = gender_text;
            
            try {
                FileWriter ld = new FileWriter(saveDir + this.select_account_filename(), true);
                PrintWriter ldp = new PrintWriter(ld);
                
                this.get_role();
                if (this.idprefix.equals("Us")) {
                    String username = username_text;
                    String password = password_text;
                    
                    ldp.println(idprefix + userID + "|"
                            + username + "|"
                            + password + "|"
                            + name + "|"
                            + email + "|"
                            + contact + "|"
                            + address + "|"
                            + gender + "|"
                            + role + "|"
                            + "false");
                } else {
                    ldp.println(idprefix + userID + "|"
                            + name + "|"
                            + email + "|"
                            + contact + "|"
                            + address + "|"
                            + gender + "|"
                            + "false");
                }
                ldp.close();
                JOptionPane.showMessageDialog(null, "You have been successfully registered as a user! Press OK to return to user registration form.", "Account creation succeeded!", JOptionPane.INFORMATION_MESSAGE);
                // new AdminHomePage().setVisible(true);
                // this.dispose();
            } catch (IOException e) {

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please check your input to proceed.", "Invalid insertion detected!", JOptionPane.WARNING_MESSAGE);

        }

    }
    
    // For Testing Purposes 
    public static void main(String[] args) {
        // user_management user = new user_management();
        // User Registrtion Test
        // user.addUserInformation("Sia123", "123", "123", "Sia", "sia@mail.com", "011-3211111", "KL", "Female", "Technician");
        
        // Customer Registration Test
        // user.addUserInformation(null, null, null, "Ryan", "lim@mail.com", "011-3211111", "KL", "Male", "Customer");
    }
}
