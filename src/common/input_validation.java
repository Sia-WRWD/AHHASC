/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
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
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Contact Number Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Create Date Validation
    public boolean dateValidation(String text) throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse(text);
        Date now = new Date();

        String input = text;
        String regex = "^[0-9]{4}-(0[1-9]|10|11|12)-([0-2][0-9]|3[0-1])";
        String msg = "Invalid Date! Date must follow the format of yyyy-mm-dd";
        String msg2 = "Invalid Date! Please enter an appropriate date.";
        
        boolean match = input.matches(regex);
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Date Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        
        if (date1.compareTo(now) <= 0) {
            JOptionPane.showMessageDialog(null, msg2, "Error: Date Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }
    
    // Update Date Validation
    public boolean updateDateValidation(String text, String apptDateText) throws Exception {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = formatter1.parse(text);
        Date apptDate = formatter1.parse(apptDateText);

        String input = text;
        String regex = "^[0-9]{4}-(0[1-9]|10|11|12)-([0-2][0-9]|3[0-1])";
        String msg = "Invalid Date! Date must follow the format of yyyy-mm-dd";
        String msg2 = "Invalid Date! Please enter a date after the appointment date.";
        
        boolean match = input.matches(regex);
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Date Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        
        if (date1.compareTo(apptDate) <= 0) {
            JOptionPane.showMessageDialog(null, msg2, "Error: Date Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Time Validation
    public boolean timeValidation(String text) {
        String input = text;
        String regex = "[0-2][0-3]:[0-5][0-9]|24:00";
        String msg = "Invalid Time! Time must follow the format of 00:00";
        boolean match = input.matches(regex);
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Time Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Room No. Validation
    public boolean roomNoValidation(String text) {
        String input = text;
        String regex = "[0-9]{3}";
        String msg = "Invalid Room No! Room No. must follow the format of 000 - 999";
        boolean match = input.matches(regex);
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Room No. Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

    // Payment Aamount Validation
    public boolean paymentValidation(String text) {
        String input = text;
        String regex = "RM[0-9]{1,}.[0-9]{2,}";
        String msg = "Invalid Time! Payment Amount must follow the format of RM99.99";
        boolean match = input.matches(regex);
        if (!match || "".equals(input)) {
            JOptionPane.showMessageDialog(null, msg, "Error: Payment Amount Validation Error", JOptionPane.WARNING_MESSAGE);
            // txt.setBackground(new Color(143, 168, 50));
            return false;
        }
        // txt.setBackground(new Color(102, 102, 102));
        return true;
    }

}
