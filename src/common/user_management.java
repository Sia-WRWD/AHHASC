/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import common.data_sorter;
import common.input_validation;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

    boolean succeed = false;
    data_sorter ds = new data_sorter();

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

        if (username != null) {
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

    public boolean validatePasswordField(JPasswordField password) {
        boolean validation = true;
        if (!this.passwordValidation(password.getText())) {
            validation = false;
            password.setBackground(new Color(143, 168, 50));
        } else {
            password.setBackground(new Color(0, 0, 0));
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
        if (this.role.equals("Technician") || this.role.equals("Manager")) {
            this.idprefix = "Us";
        } else {
            this.idprefix = "Cus";
        }
        return this.role;
    }

    private String select_account_filename() {
        String filename;

        this.get_role();
        if (this.idprefix.equals("Us")) {
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

        saveDir = System.getProperty("user.dir") + "/src/database/";
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
                            + contact + "|"
                            + address + "|"
                            + email + "|"
                            + gender + "|"
                            + role
                    );
                } else {
                    ldp.println(idprefix + userID + "|"
                            + name + "|"
                            + email + "|"
                            + contact + "|"
                            + address + "|"
                            + gender
                    );
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

    //Delete Data Function
    public boolean deleteUserData(String filepath, String removeterm, int positionOfTerm, String delimiter) {

        int pos = positionOfTerm - 1;

        String tempFile = System.getProperty("user.dir") + "\\src\\database\\temp.txt";
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        String readData;
        String data[];

        try {

            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);

            while ((readData = br.readLine()) != null) { //Data in data.txt Will Be Inserted into Data Array.
                data = readData.split("\\|");
                if (!(data[pos].equalsIgnoreCase(removeterm))) { //Identify Which Line Contains the removeterm Value.
                    pw.println(readData); //Writes to the temp.txt File Without the Data of the Identified Line with the removeterm Value.
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();

            oldFile.delete(); //Delete the old File.
            File dump = new File(filepath);
            newFile.renameTo(dump); //Renames The temp.txt File created just now to Original File Name.
            succeed = true;
        } catch (Exception e) {
            return succeed = false;
        }

        return succeed;
    }

    //Update User Data Function
    public boolean updateUserData(String id, String name, String phone_number, String address, String email, String gender, String role) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt"; //Retrieving Directory of user_details.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userPassword = fields[2];
            String userName = fields[3];
            String userPhoneNumber = fields[4];
            String userAddress = fields[5];
            String userEmail = fields[6];
            String userGender = fields[7];
            String userRole = fields[8];

            try {
                if (userID.equals(id)) {
                    br.close();
                    deleteUserData(saveDir, userID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upUserName = name;
                    String upUserPhoneNumber = phone_number;
                    String upUserAddress = address;
                    String upEmail = email;
                    String upUserGender = gender;
                    String upUserRole = role;
                    String upUserData = userID + "|" + userUsername + "|" + userPassword + "|" + upUserName
                            + "|" + upUserPhoneNumber + "|" + upUserAddress + "|" + upEmail + "|"
                            + upUserGender + "|" + upUserRole;
                    bw.write(upUserData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    succeed = true;
                    break;
                }
            } catch (Exception e) {
                br.close();
                return succeed = false;
            }

        }
        return succeed;
    }

    //Update Client Data
    public boolean updateClientData(String id, String name, String phone_number, String address, String email, String gender) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\customer_details.txt"; //Retrieving Directory of customer_details.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data customer_details.txt File.
            String[] fields = record.split("\\|");
            String clientID = fields[0];
            String clientName = fields[1];
            String clientPhoneNumber = fields[2];
            String clientAddress = fields[3];
            String clientEmail = fields[4];
            String clientGender = fields[5];

            try {
                if (clientID.equals(id)) {
                    br.close();
                    deleteUserData(saveDir, clientID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upClientName = name;
                    String upClientPhoneNumber = phone_number;
                    String upClientAddress = address;
                    String upClientEmail = email;
                    String upClientGender = gender;
                    String upClientData = clientID + "|" + upClientName + "|" + upClientPhoneNumber + "|" + upClientAddress
                            + "|" + upClientEmail + "|" + upClientGender;
                    bw.write(upClientData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    succeed = true;
                    break;
                }
            } catch (Exception e) {
                br.close();
                return succeed = false;
            }

        }
        return succeed;
    }

    //Update Profile Data
    public boolean updateProfileData(String id, String name, String phone_number, String address, String email, String gender) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt"; //Retrieving Directory of user_details.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userPassword = fields[2];
            String userName = fields[3];
            String userPhoneNumber = fields[4];
            String userAddress = fields[5];
            String userEmail = fields[6];
            String userGender = fields[7];
            String userRole = fields[8];

            try {
                if (userID.equals(id)) {
                    br.close();
                    deleteUserData(saveDir, userID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upUserName = name;
                    String upUserPhoneNumber = phone_number;
                    String upUserAddress = address;
                    String upUserEmail = email;
                    String upUserGender = gender;
                    String upProfileData = userID + "|" + userUsername + "|" + userPassword + "|" + upUserName
                            + "|" + upUserPhoneNumber + "|" + upUserAddress + "|" + upUserEmail + "|"
                            + upUserGender + "|" + userRole;
                    bw.write(upProfileData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    succeed = true;
                    break;
                }
            } catch (Exception e) {
                br.close();
                return succeed = false;
            }

        }
        return succeed;
    }

    //Update User Password
    public boolean updateUserPwd(String id, String password) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt"; //Retrieving Directory of user_details.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userPassword = fields[2];
            String userName = fields[3];
            String userPhoneNumber = fields[4];
            String userAddress = fields[5];
            String userEmail = fields[6];
            String userGender = fields[7];
            String userRole = fields[8];

            try {
                if (userID.equals(id)) {
                    br.close();
                    deleteUserData(saveDir, userID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upUserPassword = password;
                    String resetPwdData = userID + "|" + userUsername + "|" + upUserPassword + "|" + userName
                            + "|" + userPhoneNumber + "|" + userAddress + "|" + userEmail + "|"
                            + userGender + "|" + userRole;
                    bw.write(resetPwdData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    succeed = true;
                    break;
                }
            } catch (Exception e) {
                br.close();
                return succeed = false;
            }

        }
        return succeed;
    }

    //Update Forgot Password
    public boolean updateForgotPassword(String username, String password) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt"; //Retrieving Directory of user_details.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userPassword = fields[2];
            String userName = fields[3];
            String userPhoneNumber = fields[4];
            String userAddress = fields[5];
            String userEmail = fields[6];
            String userGender = fields[7];
            String userRole = fields[8];

            try {
                if (userUsername.equals(username)) {
                    br.close();
                    deleteUserData(saveDir, userID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upUserPassword = password;
                    String resetPwdData = userID + "|" + userUsername + "|" + upUserPassword + "|" + userName
                            + "|" + userPhoneNumber + "|" + userAddress + "|" + userEmail + "|"
                            + userGender + "|" + userRole;
                    bw.write(resetPwdData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    succeed = true;
                    break;
                }
            } catch (Exception e) {
                br.close();
                return succeed = false;
            }

        }
        return succeed;
    }

}
