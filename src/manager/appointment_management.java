/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ryan
 */
public class appointment_management {
    private String apptID, feedbackID, paymentID;
    private String saveDir; 
    private int newApptID, newFeedbackID, newPaymentID;
    private String apptIdPrefix, feedbackIdPrefix, paymentIdPrefix;

    private void apptIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        this.apptIdPrefix = "ApptID";
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File appt_file = new File(saveDir + "appointment_details.txt");
            if (!appt_file.exists()) {
                appt_file.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(appt_file);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split("\\|");
                    matchedID[0] = matchedID[0].replace(this.apptIdPrefix, "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No appointment(s) record was found! Restarting database entry.", "Appointment database is empty!", JOptionPane.ERROR_MESSAGE);
                    newApptID = 1;
                } else {
                    newApptID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Appointment ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void feedbackIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        this.feedbackIdPrefix = "FID";
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File feedback_file = new File(saveDir + "feedback.txt");
            if (!feedback_file.exists()) {
                feedback_file.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(feedback_file);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split("\\|");
                    matchedID[0] = matchedID[0].replace(this.feedbackIdPrefix, "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No feedback(s) record was found! Restarting database entry.", "Feedback database is empty!", JOptionPane.ERROR_MESSAGE);
                    newFeedbackID = 1;
                } else {
                    newFeedbackID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Feedback ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void paymentIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        this.paymentIdPrefix = "PaymentID";
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File payment_file = new File(saveDir + "transaction.txt");
            if (!payment_file.exists()) {
                payment_file.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(payment_file);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split("\\|");
                    matchedID[0] = matchedID[0].replace(this.paymentIdPrefix, "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No payment(s) record was found! Restarting database entry.", "Payment database is empty!", JOptionPane.ERROR_MESSAGE);
                    newPaymentID = 1;
                } else {
                    newPaymentID = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Payment ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addFeedbackInformation(String feedback_id, String client_id) {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\";

        try {
            FileWriter ld = new FileWriter(saveDir + "feedback.txt", true);
            PrintWriter ldp = new PrintWriter(ld);
            
            ldp.println(feedback_id + "|"
                        + client_id + "|"
                        + "?" + "|"
                        + "?" + "|"
                        + "false");
            
            ldp.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to create feedback entry", "Error in creating feedback!", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }
    
    private void addPaymentInformation(String payment_id, String client_id) {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\";

        try {
            FileWriter ld = new FileWriter(saveDir + "transaction.txt", true);
            PrintWriter ldp = new PrintWriter(ld);
            
            ldp.println(paymentIdPrefix + payment_id + "|"
                        + client_id + "|"
                        + "?" + "|"
                        + "?" + "|"
                        + "?" + "|"
                        + "false");
            
            ldp.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to create payment entry", "Error in creating payment!", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }

    public void addApptInformation(String client_id, String room_no, String appt_date, String technician_id, String created_by) {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\";
        DecimalFormat dc = new DecimalFormat("000000");
        try {
            apptIncrementor();
            feedbackIncrementor();
            paymentIncrementor();
            
            apptID = dc.format(newApptID);
            feedbackID = dc.format(newFeedbackID);
            paymentID = dc.format(newPaymentID);
            
            try {
                FileWriter ld = new FileWriter(saveDir + "appointment_details.txt", true);
                PrintWriter ldp = new PrintWriter(ld);

                ldp.println(apptIdPrefix + apptID + "|"
                            + client_id + "|"
                            + room_no + "|"
                            + appt_date + "|"
                            + feedbackIdPrefix + feedbackID + "|"
                            + technician_id + "|"
                            + paymentIdPrefix + paymentID + "|"
                            + "Pending" + "|"
                            + created_by + "|"
                            + "false");
                
                ldp.close();
                
                this.addFeedbackInformation(feedbackID, client_id);
                this.addPaymentInformation(paymentID, client_id);
                
                JOptionPane.showMessageDialog(null, "You have been successfully created an appointment! Press OK to return to appointment form.", "Appointment creation success!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please check your input to proceed.", "Invalid insertion detected!", JOptionPane.WARNING_MESSAGE);

        }

    }
    
    // For Testing Purposes 
    public static void main(String[] args) {
        appointment_management appointment = new appointment_management();
        // Appointment Creation Test
        appointment.addApptInformation("Cus000002", "301", "2021-12-31", "Us000003", "Us000001");
        // appointment.addFeedbackInformation("Cus000002", "301");
        
        // Customer Registration Test
        // user.addUserInformation(null, null, null, "Ryan", "lim@mail.com", "011-3211111", "KL", "Male", "Customer");
    }
    
}
