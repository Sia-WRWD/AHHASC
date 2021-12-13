/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author rlfy
 */
abstract public class input_validation {
    public boolean stringValidation(String text) {
        String input = text;
        String regex = "^[a-zA-Z0-9 ]+";
        String msg = "This field can only consist of letters, numbers or spacings only";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            // JOptionPane.showMessageDialog(null, msg, "Error: Input Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Username Validation
    public boolean usernameValidation(String text) {
        String input = text;
        String regex = "^[-a-zA-Z0-9!@#$%^&*()\\{\\}\\[\\]\"\';\\\\/?|.,><~`_+=]+";
        String msg = "Invalid username! Username can only consist of letters, numbers and certain escaped symbols.";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Username Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Password Validation
    public boolean passwordValidation(String password) {
        String input = password;
        String regex = "^[-a-zA-Z0-9!@#$%^&*()\\{\\}\\[\\]\"\';\\\\/?|.,><~`_+=]+";
        String msg = "Invalid password! Password can only consist of letters, numbers and certain escaped symbols.";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            // JOptionPane.showMessageDialog(null, msg, "Error: Password Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }
    
    // Password Confirmation
    public boolean passwordConfirmation(String password, String retype_password) {
        String input1 = password;
        String input2 = retype_password;
        
        if (input1.equals("") || input2.equals("")) {
            return false;
        }
        
        if (input1.equals(input2)) {
            return true;
        }
        
        return false;
    }

    // Email validation
    public boolean emailValidation(String text) {
        String input = text;
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String msg = "Invalid email address! Email must follw the format of xxxxx@xxx.xxx";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Email Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Name validation
    public boolean nameValidation(String text) {
        String input = text;
        String regex = "^[a-zA-Z ]+";
        String msg = "Invalid name! Name can only consist of letters and spacing";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Name Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Address Validation
    public boolean addressValidation(String text) {
        String input = text;
        String regex = "^[a-zA-Z0-9()&., ]+";
        String msg = "Invalid address! Address can only consist of letters, numbers, spacing and certain symbols";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Address Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Contact Number Validation
    public boolean contactValidation(String text) {
        String input = text;
        String regex = "^[0-9]+-[0-9]+";
        String msg = "Invalid Contact Number! Contact Number must follow the format of 000-0000000";
        boolean match = input.matches(regex);
        if (!match && !"".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Contact Number Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

}
