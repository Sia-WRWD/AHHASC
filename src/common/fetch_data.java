/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author chinojen7
 */
public class fetch_data {

    private String saveDir;

    public static void main(String[] args) throws IOException {
        fetch_data fd = new fetch_data();

        fd.fetchAllData();
    }

    public void fetchAllData() throws IOException {
        this.fetchUserData();
        this.fetchClientData();
        this.fetchApptData();
        this.fetchFeedbackData();
        this.fetchTransactionData();
        this.fetchSession();
    }

    public ArrayList fetchUserData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> userData = new ArrayList<String[]>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userName = fields[3];
            String userPhoneNumber = fields[4];
            String userAddress = fields[5];
            String userEmail = fields[6];
            String userGender = fields[7];
            String userRole = fields[8];

            String[] tmpArr = {userID, userUsername, userName, userPhoneNumber, userAddress, userEmail, userGender, userRole};
            userData.add(tmpArr);
        }
        br.close();
        fr.close();

        return userData;
    }

    public ArrayList fetchClientData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\customer_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> clientData = new ArrayList<String[]>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String customerID = fields[0];
            String customerName = fields[1];
            String customerPhoneNumber = fields[2];
            String customerAddress = fields[3];
            String customerEmail = fields[4];
            String customerGender = fields[5];

            String[] tempArr = {customerID, customerName, customerPhoneNumber, customerAddress, customerEmail, customerGender};
            clientData.add(tempArr);
        }
        br.close();
        fr.close();

        return clientData;
    }

    public ArrayList fetchApptData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> apptData = new ArrayList<String[]>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptRoomNo = fields[2];
            String apptDate = fields[3];
            String apptFeedbackID = fields[4];
            String apptTechnicianID = fields[5];
            String apptPaymentID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            String[] tempArr = {apptID, apptClientID, apptRoomNo, apptDate, apptFeedbackID, apptTechnicianID, apptPaymentID,
                                apptJobStatus, apptCreatedBy};
            apptData.add(tempArr);
        }
        br.close();
        fr.close();

        return apptData;
    }

    public ArrayList fetchFeedbackData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\feedback.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> feedbackData = new ArrayList<String[]>();
        
        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String feedbackID = fields[0];
            String clientID = fields[1];
            String feedbackContent = fields[2];
            String feedbackDate = fields[3];

            String[] tempArr = {feedbackID, clientID, feedbackContent, feedbackDate};
            feedbackData.add(tempArr);
        }
        br.close();
        fr.close();
        
        return feedbackData;
    }

    public ArrayList fetchTransactionData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> paymentData = new ArrayList<String[]>();
        
        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String paymentID = fields[0];
            String paymentApptID = fields[1];
            String paymentDate = fields[2];
            String paymentAmount = fields[3];
            String paymentStatus = fields[4];

            String[] tempArr = {paymentID, paymentApptID, paymentDate, paymentAmount, paymentStatus};
            paymentData.add(tempArr);
        }
        br.close();
        fr.close();
        
        return paymentData;
    }

    public ArrayList fetchSession() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\user_cache.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> userCacheData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String userCacheID = fields[0];
            String userCacheUsername = fields[1];
            String userCacheRole = fields[2];

            userCacheData.add(userCacheID);
            userCacheData.add(userCacheUsername);
            userCacheData.add(userCacheRole);
        }
        br.close();
        fr.close();

        return userCacheData;
    }

}
