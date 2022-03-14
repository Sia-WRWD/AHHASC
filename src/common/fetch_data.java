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

    //Fetch All User Data
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

    //Fetch All User Data for Technician
    public ArrayList fetchUserDataT() throws IOException {
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

            String[] tmpArr = {userID, userName, userPhoneNumber, userAddress, userEmail, userGender, userRole};
            userData.add(tmpArr);
        }
        br.close();
        fr.close();

        return userData;
    }

    //Fetch All Client Data
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

    //Fetch All Appointment Records
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
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            String[] tempArr = {apptID, apptClientID, apptDate, apptTime, apptRoomNo, apptAppliance, apptTechnicianID,
                apptJobStatus, apptCreatedBy};
            apptData.add(tempArr);
        }
        br.close();
        fr.close();

        return apptData;
    }

    //Fetch All Feedback Records
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
            String feedbackApptID = fields[1];
            String feedbackContent = fields[2];
            String feedbackDate = fields[3];
            String feedbackUpdatedBy = fields[4];

            String[] tempArr = {feedbackID, feedbackApptID, feedbackContent, feedbackDate, feedbackUpdatedBy};
            feedbackData.add(tempArr);
        }
        br.close();
        fr.close();

        return feedbackData;
    }

    //Fetch All Transaction Records
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
            String paymentUpdatedBy = fields[5];

            String[] tempArr = {paymentID, paymentApptID, paymentDate, paymentAmount, paymentStatus, paymentUpdatedBy};
            paymentData.add(tempArr);
        }
        br.close();
        fr.close();

        return paymentData;
    }

    //Fetch User Session
    public ArrayList fetchSession() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\user_cache.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> userCacheData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String userID = fields[0];
            String userUsername = fields[1];
            String userRole = fields[2];

            userCacheData.add(userID);
            userCacheData.add(userUsername);
            userCacheData.add(userRole);
        }
        br.close();
        fr.close();

        return userCacheData;
    }

    //Fetch User Based on ID
    public ArrayList fetchUserBasedId(String id) throws IOException {

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

            if (userID.equals(id)) {
                br.close();

                userData.add(userID);
                userData.add(userUsername);
                userData.add(userName);
                userData.add(userPhoneNumber);
                userData.add(userAddress);
                userData.add(userEmail);
                userData.add(userGender);
                userData.add(userRole);

                break;
            }
        }
        br.close();
        fr.close();

        return userData;
    }

    //Fetch Client Based on ID
    public ArrayList fetchClientBasedId(String id) throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\customer_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> clientData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String clientID = fields[0];
            String clientName = fields[1];
            String clientPhoneNumber = fields[2];
            String clientAddress = fields[3];
            String clientEmail = fields[4];
            String clientGender = fields[5];

            if (clientID.equals(id)) {
                br.close();

                clientData.add(clientID);
                clientData.add(clientName);
                clientData.add(clientPhoneNumber);
                clientData.add(clientAddress);
                clientData.add(clientEmail);
                clientData.add(clientGender);

                break;
            }
        }
        br.close();
        fr.close();

        return clientData;
    }

    //Fetch Appointment Based on ID
    public ArrayList fetchApptBasedId(String id) throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> apptData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            if (apptID.equals(id)) {
                br.close();

                apptData.add(apptID);
                apptData.add(apptClientID);
                apptData.add(apptDate);
                apptData.add(apptTime);
                apptData.add(apptRoomNo);
                apptData.add(apptAppliance);
                apptData.add(apptTechnicianID);
                apptData.add(apptJobStatus);
                apptData.add(apptCreatedBy);

                break;
            }
        }
        br.close();
        fr.close();

        return apptData;
    }

    //Fetch Payment Based on ID
    public ArrayList fetchPayBasedId(String id) throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> payData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String payID = fields[0];
            String payApptID = fields[1];
            String payDate = fields[2];
            String payAmount = fields[3];
            String payStatus = fields[4];
            String payUpdatedBy = fields[5];

            if (payID.equals(id)) {
                br.close();

                payData.add(payID);
                payData.add(payApptID);
                payData.add(payDate);
                payData.add(payAmount);
                payData.add(payStatus);
                payData.add(payUpdatedBy);

                break;
            }
        }
        br.close();
        fr.close();

        return payData;
    }

    //Fetch Feedback Based on ID
    public ArrayList fetchFeedbackBasedId(String id) throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\feedback.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String> feedbackData = new ArrayList<String>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String feedbackID = fields[0];
            String feedbackApptID = fields[1];
            String feedbackContent = fields[2];
            String feedbackDate = fields[3];
            String feedbackUpdatedBy = fields[4];

            if (feedbackID.equals(id)) {
                br.close();

                feedbackData.add(feedbackID);
                feedbackData.add(feedbackApptID);
                feedbackData.add(feedbackContent);
                feedbackData.add(feedbackDate);
                feedbackData.add(feedbackUpdatedBy);

                break;
            }
        }
        br.close();
        fr.close();

        return feedbackData;
    }

    //Fetch All Ongoing Appointment
    public ArrayList fetchOngoingApptData() throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> ongoingApptData = new ArrayList<String[]>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            if (apptJobStatus.equals("Pending")) {
                String[] tempArr = {apptID, apptClientID, apptDate, apptTime, apptRoomNo, apptAppliance,
                    apptTechnicianID, apptJobStatus, apptCreatedBy};
                ongoingApptData.add(tempArr);

            }
        }
        br.close();
        fr.close();

        return ongoingApptData;
    }

    //Fetch Ongoing Appt Data based on ID
    public ArrayList fetchOngoingApptDataBasedId(String id) throws IOException {

        saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> ongoingApptData = new ArrayList<String[]>();

        while ((result = br.readLine()) != null) {
            String[] fields = result.split("\\|");
            String apptID = fields[0];
            String apptClientID = fields[1];
            String apptDate = fields[2];
            String apptTime = fields[3];
            String apptRoomNo = fields[4];
            String apptAppliance = fields[5];
            String apptTechnicianID = fields[6];
            String apptJobStatus = fields[7];
            String apptCreatedBy = fields[8];

            if (apptJobStatus.equals("Pending") && apptTechnicianID.equals(id)) {
                String[] tempArr = {apptID, apptClientID, apptDate, apptTime, apptRoomNo, apptAppliance,
                    apptTechnicianID, apptJobStatus, apptCreatedBy};
                ongoingApptData.add(tempArr);

            }
        }
        br.close();
        fr.close();

        return ongoingApptData;
    }

    //Fetch All Technician
    public ArrayList<String[]> fetchAllTechnician() throws IOException {
        saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt";
        File file = new File(saveDir);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String result;
        ArrayList<String[]> technicianData = new ArrayList<String[]>();

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

            if (userRole.equals("Technician")) {
                String[] tmpArr = {userID, userName};
                technicianData.add(tmpArr);
            }
        }
        br.close();
        fr.close();

        return technicianData;
    }

    //Fetch All Client
    public ArrayList<String[]> fetchAllClient() throws IOException {
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

            String[] tempArr = {customerID, customerName};
            clientData.add(tempArr);
        }
        br.close();
        fr.close();

        return clientData;
    }
}
