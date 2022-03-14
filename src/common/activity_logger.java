/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author chinojen7
 */
public class activity_logger {

    String currentDateTime, user_activity;
    boolean logged = false;
    String saveDir = "";
    String idprefix = "AL";
    String logid = "";
    int newLogId = 0;

    // Database
    private boolean saveLog(String data) {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\";
        DecimalFormat dc = new DecimalFormat("000000");

        try {
            logIncrementor();
            logid = dc.format(newLogId);

            try {
                FileWriter ld = new FileWriter(saveDir + "activity_log.txt", true);
                PrintWriter ldp = new PrintWriter(ld);

                ldp.println(idprefix + logid + "|" + data);
                ldp.close();

                // JOptionPane.showMessageDialog(null, "You have been successfully created an log! Press OK to return to log form.", "Log creation success!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                // JOptionPane.showMessageDialog(null, "Invalid input! Please check your input to proceed.", "Invalid insertion detected!", JOptionPane.WARNING_MESSAGE);
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return logged;
    }

    // Action
    public String logUserActivity(String user_id, String activity) {

        this.getCurrentTimeAndDate();

        this.user_activity = "User, " + user_id + " has " + activity + " at " + this.currentDateTime + ".";
        this.saveLog(this.user_activity);

        return user_activity;
    }

    // ID Incrementor
    private void logIncrementor() {
        String[] matchedID = null;
        boolean hasRecord = false;
        try {
            saveDir = System.getProperty("user.dir") + "/src/database/";
            File file = new File(saveDir + "activity_log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Scanner inputFile;
            try {
                inputFile = new Scanner(file);
                while (inputFile.hasNext()) {
                    String entry = inputFile.nextLine();
                    matchedID = entry.split("\\|");
                    matchedID[0] = matchedID[0].replace(this.idprefix, "");
                    hasRecord = true;
                }
                inputFile.close();
                if (!hasRecord) {
                    JOptionPane.showMessageDialog(null, "No log(s) record was found! Restarting database entry.", "Log database is empty!", JOptionPane.ERROR_MESSAGE);
                    newLogId = 1;
                } else {
                    newLogId = Integer.parseInt(matchedID[0]) + 1;
                }
            } catch (FileNotFoundException ex) {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null, "Invalid input! Log ID can only consist of numbers", "Invalid input type!", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Date
    private void getCurrentTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        this.currentDateTime = dtf.format(now);
    }

}
