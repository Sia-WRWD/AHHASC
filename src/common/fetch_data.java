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
        ArrayList<String> userData = new ArrayList<String>();

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
        }
        br.close();
        fr.close();

        return userData;
    }

    public void fetchClientData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\customer_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String customerID = fields[0];
            String customerName = fields[1];
            String userPhoneNumber = fields[2];
            String userAddress = fields[3];
            String userEmail = fields[4];
            String userGender = fields[5];

            System.out.println(Arrays.toString(fields));
        }
        br.close();
        fr.close();
    }

    public void fetchApptData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String appointmentID = fields[0];
            String appointmentClientID = fields[1];
            String appointmentRoomNo = fields[2];
            String appointmentDate = fields[3];
            String appointmentFeedbackID = fields[4];
            String appointmentTechnicianID = fields[5];
            String appointmentPaymentID = fields[6];
            String appointmentJobStatus = fields[7];
            String appointmentCreatedBy = fields[8];

            System.out.println(Arrays.toString(fields));
        }
        br.close();
        fr.close();
    }

    public void fetchFeedbackData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\feedback.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String feedbackID = fields[0];
            String clientID = fields[1];
            String feedbackContent = fields[2];
            String feedbackDate = fields[3];

            System.out.println(Arrays.toString(fields));
        }
        br.close();
        fr.close();
    }

    public void fetchTransactionData() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String paymentID = fields[0];
            String paymentApptID = fields[2];
            String paymentDate = fields[1];
            String paymentAmount = fields[3];
            String paymentStatus = fields[4];

            System.out.println(Arrays.toString(fields));
        }
        br.close();
        fr.close();
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
