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
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ryan
 */
public class appointment_management extends input_validation {

    private String apptID, feedbackID, paymentID;
    private String saveDir;
    private int newApptID, newFeedbackID, newPaymentID;
    private String apptIdPrefix, feedbackIdPrefix, paymentIdPrefix;

    boolean succeed = false;
    data_sorter ds = new data_sorter();

    public boolean validateApptFields(JTextField roomNo) {
        boolean validation = true;

        if (!this.roomNoValidation(roomNo.getText())) {
            validation = false;
            roomNo.setBackground(new Color(143, 168, 50));
        } else {
            roomNo.setBackground(new Color(0, 0, 0));
        }
        return validation;
    }

    public boolean validateCreateApptFields(JTextField roomNo, JTextField date) throws Exception {
        boolean validation = true;

        if (!this.roomNoValidation(roomNo.getText())) {
            validation = false;
            roomNo.setBackground(new Color(143, 168, 50));
        } else {
            roomNo.setBackground(new Color(0, 0, 0));
        }

        if (!this.dateValidation(date.getText())) {
            validation = false;
            date.setBackground(new Color(14, 168, 50));
        } else {
            date.setBackground(new Color(0, 0, 0));
        }
        return validation;
    }

    public boolean validatePaymentFields(JTextField date, JTextField amount, String apptDate) throws Exception {
        boolean validation = true;

        if (!this.updateDateValidation(date.getText(), apptDate)) {
            validation = false;
            date.setBackground(new Color(143, 168, 50));
        } else {
            date.setBackground(new Color(0, 0, 0));
        }

        if (!this.paymentValidation(amount.getText())) {
            validation = false;
            amount.setBackground(new Color(143, 168, 50));
        } else {
            amount.setBackground(new Color(0, 0, 0));
        }
        return validation;
    }

    public boolean validateFeedbackFields(JTextField date, String apptDate) throws Exception {
        boolean validation = true;

        if (!this.updateDateValidation(date.getText(), apptDate)) {
            validation = false;
            date.setBackground(new Color(143, 168, 50));
        } else {
            date.setBackground(new Color(0, 0, 0));
        }

        return validation;
    }

    private void apptIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        this.apptIdPrefix = "Appt";
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
        this.feedbackIdPrefix = "FB";
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
        this.paymentIdPrefix = "Pay";
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

            ldp.println(feedbackIdPrefix + feedback_id + "|"
                    + client_id + "|"
                    + "?" + "|"
                    + "?" + "|"
                    + "?"
            );

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
                    + "?"
            );

            ldp.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to create payment entry", "Error in creating payment!", JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }

    public void addApptInformation(String client_id, String room_no, String appt_date, String appt_time, String appliance, String technician_id, String created_by) {
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
                        + appt_date + "|"
                        + appt_time + "|"
                        + room_no + "|"
                        + appliance + "|"
                        + technician_id + "|"
                        + "Pending" + "|"
                        + created_by
                );

                ldp.close();

                this.addFeedbackInformation(feedbackID, apptIdPrefix + apptID);
                this.addPaymentInformation(paymentID, apptIdPrefix + apptID);

                JOptionPane.showMessageDialog(null, "You have been successfully created an appointment! Press OK to return to appointment form.", "Appointment creation success!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please check your input to proceed.", "Invalid insertion detected!", JOptionPane.WARNING_MESSAGE);

        }

    }

    //Delete Data Function
    public boolean deleteApptData(String filepath, String removeterm, int positionOfTerm, String delimiter) {

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

            oldFile.delete(); //Delete the old Admin.txt File.
            File dump = new File(filepath);
            newFile.renameTo(dump); //Renames The temp.txt File created just now to Admin.txt.

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An Error has Occured while Deleting File!", "File Deletion Failure", JOptionPane.WARNING_MESSAGE);
        }

        return succeed = true;
    }

    //Update Appointment Data
    public boolean updateApptData(String id, String room_no, String appliance, String job_status, String updatedBy) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt"; //Retrieving Directory of itemlist.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            try {
                if (apptID.equals(id)) {
                    br.close();
                    deleteApptData(saveDir, apptID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upApptRoomNo = room_no;
                    String upApptJobStatus = job_status;
                    String upApptAppliance = appliance;
                    String upApptData = apptID + "|" + apptClientID + "|" + apptDate + "|" + apptTime + "|" + upApptRoomNo
                            + "|" + upApptAppliance + "|" + apptTechnicianID + "|"
                            + upApptJobStatus + "|" + apptCreatedBy;
                    bw.write(upApptData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);

                    if (upApptJobStatus.equals("Cancelled")) {
                        this.updatePaymentStatus(apptID, updatedBy);
                    }

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

    //Update Payment Status to Cancelled if Appt is Cancelled
    private void updatePaymentStatus(String apptId, String updatedBy) throws IOException {
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt"; //Retrieving Directory of itemlist.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String paymentID = fields[0];
            String paymentApptID = fields[1];
            String paymentDate = fields[2];
            String paymentAmount = fields[3];
            String paymentStatus = fields[4];
            String paymentUpdatedBy = fields[5];

            try {
                if (paymentApptID.equals(apptId)) {
                    br.close();
                    deleteApptData(saveDir, paymentID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upPaymentStatus = "Cancelled";
                    String upPaymentUpdatedBy = updatedBy;
                    String upPaymentDate = "-";
                    String upPaymentAmount = "-";
                    String upPaymentData = paymentID + "|" + paymentApptID + "|" + upPaymentDate + "|"
                            + upPaymentAmount + "|" + upPaymentStatus + "|" + upPaymentUpdatedBy;
                    bw.write(upPaymentData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);
                    break;
                }
            } catch (Exception e) {
                br.close();
            }

        }
    }

    //Update Payment Data
    public boolean updatePayData(String id, String date, String amount, String status, String updated_by) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt"; //Retrieving Directory of itemlist.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String paymentID = fields[0];
            String paymentApptID = fields[1];
            String paymentDate = fields[2];
            String paymentAmount = fields[3];
            String paymentStatus = fields[4];
            String paymentUpdatedBy = fields[5];

            try {
                if (paymentID.equals(id)) {
                    br.close();
                    deleteApptData(saveDir, paymentID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upPaymentDate = date;
                    String upPaymentAmount = amount;
                    String upPaymentStatus = status;
                    String upPaymentUpdatedBy = updated_by;
                    String upPaymentData = paymentID + "|" + paymentApptID + "|" + upPaymentDate + "|"
                            + upPaymentAmount + "|" + upPaymentStatus + "|" + upPaymentUpdatedBy;
                    bw.write(upPaymentData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir);

                    if (upPaymentStatus.equals("Paid")) {
                        this.updateJobStatus(paymentApptID);
                    }

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

    //Update Job Status to Completed When Payment = Paid
    private void updateJobStatus(String apptId) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt"; //Retrieving Directory of itemlist.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            try {
                if (apptID.equals(apptId)) {
                    br.close();
                    deleteApptData(saveDir, apptID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upApptJobStatus = "Completed";
                    String upApptData = apptID + "|" + apptClientID + "|" + apptDate + "|"
                            + apptTime + "|" + apptRoomNo + "|" + apptAppliance + "|" + apptTechnicianID
                            + "|" + upApptJobStatus + "|" + apptCreatedBy;
                    bw.write(upApptData);
                    bw.close();
                    ds.sortItemData(saveDir, saveDir); 
                    break;
                }
            } catch (Exception e) {
                br.close();
            }

        }
    }

    //Update Feedback Data
    public boolean updateFeedbackData(String id, String content, String date, String updated_by) throws IOException {

        String saveDir = System.getProperty("user.dir") + "\\src\\database\\feedback.txt"; //Retrieving Directory of itemlist.txt File.
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String record;

        while ((record = br.readLine()) != null) { //Retrieving All Data user_details.txt File.
            String[] fields = record.split("\\|");
            String feedbackID = fields[0];
            String clientID = fields[1];
            String feedbackContent = fields[2];
            String feedbackDate = fields[3];
            String feedbackUpdatedBy = fields[4];

            try {
                if (feedbackID.equals(id)) {
                    br.close();
                    deleteApptData(saveDir, feedbackID, 1, "\\|");
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    String upFeedbackContent = content;
                    String upFeedbackDate = date;
                    String upFeedbackUpdatedBy = updated_by;
                    String upFeedbackData = feedbackID + "|" + clientID + "|" + upFeedbackContent + "|"
                            + upFeedbackDate + "|" + upFeedbackUpdatedBy;
                    bw.write(upFeedbackData);
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
