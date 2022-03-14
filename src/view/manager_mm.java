/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.activity_logger;
import common.fetch_data;
import common.login;
import common.user_management;
import java.awt.Color;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import common.appointment_management;
import common.generate_report_data;

/**
 *
 * @author chinojen7
 */
public class manager_mm extends javax.swing.JFrame {

    //To Instantiate the Classes
    fetch_data fd = new fetch_data();
    user_management um = new user_management();
    appointment_management am = new appointment_management();
    activity_logger al = new activity_logger();
    generate_report_data gr = new generate_report_data();

    //To Store Data Fetched
    ArrayList<String[]> userData = new ArrayList<String[]>();
    ArrayList<String[]> clientData = new ArrayList<String[]>();
    ArrayList<String[]> apptData = new ArrayList<String[]>();
    ArrayList<String[]> paymentData = new ArrayList<String[]>();
    ArrayList<String[]> feedbackData = new ArrayList<String[]>();
    ArrayList<String[]> ongoingApptData = new ArrayList<String[]>();

    //Set Table Model
    DefaultTableModel user_model;
    DefaultTableModel client_model;
    DefaultTableModel appt_model;
    DefaultTableModel payment_model;
    DefaultTableModel feedback_model;
    DefaultTableModel ongoingAppt_model;

    //Set Table Columns
    String[] user_column = {"User ID", "Username", "Name", "Phone Number", "Address", "Email", "Gender", "Role"};
    String[] client_column = {"Client ID", "Name", "Phone Number", "Address", "Email", "Gender"};
    String[] appt_column = {"Appt ID", "Client ID", "Appt Date", "Appt Time", "Room No", "Appliance", "Technician ID", "Job Status", "Created By"};
    String[] payment_column = {"Payment ID", "Appt ID", "Payment Date", "Payment Amount", "Payment Status", "Updated By"};
    String[] feedback_column = {"Feedback ID", "Appt ID", "Feedback Content", "Feedback Date", "Updated By"};
    String[] ongoingAppt_column = {"Appt ID", "Client ID", "Appt Date", "Appt Time", "Room No", "Appliance", "Technician ID", "Job Status", "Created By"};

    //Store Fetched User Data
    ArrayList<String> userCacheDataList;
    ArrayList<String> currentUserDataList;
    String[] userCacheData;
    String[] currentUserData;

    //Store Fetched Selected Data
    ArrayList<String> selectedUserDataList;
    ArrayList<String> selectedClientDataList;
    ArrayList<String> selectedApptDataList;
    ArrayList<String> selectedFeedbackDataList;
    ArrayList<String> selectedPayDataList;
    String[] selectedUserData;
    String[] selectedClientData;
    String[] selectedApptData;
    String[] selectedFeedbackData;
    String[] selectedPayData;

    String userID, clientID, apptID, payID, feedbackID, Items, apptDate;

    //Check for Response to React
    boolean deleteResponse = false;
    boolean updateResponse = false;

    //To Store Checkbox's Items
    ArrayList<String[]> technicianDataList;

    /**
     * Creates new form manager_mm
     */
    public manager_mm() throws IOException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.hideMainPanels();
        this.home_panel.setVisible(true);
        this.setAllData();
        this.setTableSelectionMode();
        this.resetChangeAccDetailsForm();
        this.disableUpdateTabs();
        this.disableUnUpdatableFields();
        this.populateComboBox();
    }

    private void hideMainPanels() {
        this.home_panel.setVisible(false);
        this.manage_user_panel.setVisible(false);
        this.manage_appt_panel.setVisible(false);
        this.manage_payment_panel.setVisible(false);
        this.manage_feedback_panel.setVisible(false);
        this.personal_details_panel.setVisible(false);
        this.update_details_panel.setVisible(false);
        this.generate_report_panel.setVisible(false);
    }

    private void hideRegisterPanels() {
        this.staff_register_panel.setVisible(false);
        this.client_register_panel.setVisible(false);
    }

    private void setTableSelectionMode() {
        userTable.setSelectionMode(userTable.getSelectionModel().SINGLE_SELECTION);
        clientTable.setSelectionMode(clientTable.getSelectionModel().SINGLE_SELECTION);
        apptTable.setSelectionMode(apptTable.getSelectionModel().SINGLE_SELECTION);
        paymentTable.setSelectionMode(paymentTable.getSelectionModel().SINGLE_SELECTION);
        feedbackTable.setSelectionMode(feedbackTable.getSelectionModel().SINGLE_SELECTION);
    }

    private void disableUpdateTabs() {
        update_details_tabs.setEnabledAt(0, false);
        update_details_tabs.setEnabledAt(1, false);
        update_details_tabs.setEnabledAt(2, false);
        update_details_tabs.setEnabledAt(3, false);
        update_details_tabs.setEnabledAt(4, false);
    }

    //Adding Data to Technician & Client Combo Box
    private void populateComboBox() {
        ArrayList<String[]> client = new ArrayList<String[]>();
        ArrayList<String[]> technician = new ArrayList<String[]>();

        try {
            client = this.fd.fetchAllClient();
            technician = this.fd.fetchAllTechnician();

            // Technician
            for (int i = 0; i < technician.size(); i++) {
                String[] technician_info = technician.get(i);
                String technician_option = this.formatComboBoxName(technician_info);

                cbCreateTechnician.addItem(technician_option);
            }

            // Client
            for (int i = 0; i < client.size(); i++) {
                String[] client_info = client.get(i);
                String client_option = this.formatComboBoxName(client_info);

                cbCreateClient.addItem(client_option);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Format Combo Box for Appointment Part
    private String formatComboBoxName(String[] data) {
        String name = data[1];
        String[] id_list = data[0].split("", 0);
        boolean found = false;
        String id = "(";
        String selection_name = "";

        for (int i = 0; i < id_list.length; i++) {
            if (!id_list[i].equals("U") && !id_list[i].equals("s") && !id_list[i].equals("C") && !id_list[i].equals("u")) {
                if (!id_list[i].equals("0") && !found) {
                    found = true;
                    id += id_list[i];
                } else if (!id_list[i].equals("0") && found) {
                    id += id_list[i];
                } else if (id_list[i].equals("0") && found) {
                    id += id_list[i];
                }
            }
        }

        id += ")";
        selection_name = id + " " + name;
        // System.out.println(selection_name);
        return selection_name;
    }

    private String retrieveId(String data, String role) {
        String id = "";

        String data_id = data.split(" ", 0)[0];
        data_id = data_id.replace("(", "");
        data_id = data_id.replace(")", "");

        if (role.equals("Technician")) {
            id = "Us" + ("0".repeat(6 - data_id.length())) + data_id;
        } else if (role.equals("Client")) {
            id = "Cus" + ("0".repeat(6 - data_id.length())) + data_id;
        }

        return id;
    }

    private void disableUnUpdatableFields() {

        //Update Appt Form
        tfUACusId.setEnabled(false);
        tfUAApptDate.setEnabled(false);
        tfUAApptTime.setEnabled(false);
        tfUATechId.setEnabled(false);
        tfUACreatedBy.setEnabled(false);

        //Update Payment Form
        tfUPApptId.setEnabled(false);
        tfUPPayUpdatedBy.setEnabled(false);

        //Update Feedback Form
        tfUFApptId.setEnabled(false);
        tfUFFeedUpdatedBy.setEnabled(false);
    }

    private void setAllData() throws IOException {
        this.setUserData();
        this.setClientData();
        this.setApptData();
        this.setPaymentData();
        this.setFeedbackData();
        this.setOngoingApptData();
        this.fetchSessionData();
        this.fetchCurrentUserData();
    }

    //Fetch Selected Feedback Data
    private void fetchSelectedFeedbackData() throws IOException {
        feedbackID = String.valueOf(feedbackTable.getValueAt(feedbackTable.getSelectedRow(), 0));
        this.selectedFeedbackDataList = this.fd.fetchFeedbackBasedId(feedbackID);
        this.selectedFeedbackData = this.selectedFeedbackDataList.toArray(new String[0]);
    }

    //Fetch Selected Payment Data
    private void fetchSelectedPayData() throws IOException {
        payID = String.valueOf(paymentTable.getValueAt(paymentTable.getSelectedRow(), 0));
        this.selectedPayDataList = this.fd.fetchPayBasedId(payID);
        this.selectedPayData = this.selectedPayDataList.toArray(new String[0]);
    }

    //Fetch Selected Appointment Data
    private void fetchSelectedApptData() throws IOException {
        apptID = String.valueOf(apptTable.getValueAt(apptTable.getSelectedRow(), 0));
        this.selectedApptDataList = this.fd.fetchApptBasedId(apptID);
        this.selectedApptData = this.selectedApptDataList.toArray(new String[0]);
    }

    //Fetch Selected Client Data
    private void fetchSelectedClientData() throws IOException {
        clientID = String.valueOf(clientTable.getValueAt(clientTable.getSelectedRow(), 0));
        this.selectedClientDataList = this.fd.fetchClientBasedId(clientID);
        this.selectedClientData = this.selectedClientDataList.toArray(new String[0]);
    }

    //Fetch Selected User Data
    private void fetchSelectedUserData() throws IOException {
        userID = String.valueOf(userTable.getValueAt(userTable.getSelectedRow(), 0));
        this.selectedUserDataList = this.fd.fetchUserBasedId(userID);
        this.selectedUserData = this.selectedUserDataList.toArray(new String[0]);
    }

    //Fetch Current User Data
    private void fetchCurrentUserData() throws IOException {
        this.currentUserDataList = this.fd.fetchUserBasedId(userCacheData[0]);
        this.currentUserData = currentUserDataList.toArray(new String[0]);
    }

    //Fetch User Session Data
    private void fetchSessionData() throws IOException {
        this.userCacheDataList = this.fd.fetchSession();
        this.userCacheData = userCacheDataList.toArray(new String[0]);
    }

    //Fetch & Display Ongoing Appt Data
    private void setOngoingApptData() throws IOException {
        this.ongoingAppt_model = new DefaultTableModel(new Object[][]{}, this.ongoingAppt_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.ongoingApptTable.setModel(this.ongoingAppt_model);
        this.ongoingApptData = this.fd.fetchOngoingApptData();
        int list_size = this.ongoingApptData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.ongoingApptData.get(row);
            this.ongoingAppt_model.addRow(row_data);
        }
    }

    //Fetch & Display User Data
    private void setUserData() throws IOException {
        this.user_model = new DefaultTableModel(new Object[][]{}, this.user_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.userTable.setModel(this.user_model);
        this.userData = this.fd.fetchUserData();
        int list_size = this.userData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.userData.get(row);
            this.user_model.addRow(row_data);
        }
    }

    //Fetch & Display Client Data
    private void setClientData() throws IOException {
        this.client_model = new DefaultTableModel(new Object[][]{}, this.client_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.clientTable.setModel(this.client_model);
        this.clientData = this.fd.fetchClientData();
        int list_size = this.clientData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.clientData.get(row);
            this.client_model.addRow(row_data);
        }
    }

    //Fetch & Display Appointment Data
    private void setApptData() throws IOException {
        this.appt_model = new DefaultTableModel(new Object[][]{}, this.appt_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.apptTable.setModel(this.appt_model);
        this.apptData = this.fd.fetchApptData();
        int list_size = this.apptData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.apptData.get(row);
            this.appt_model.addRow(row_data);
        }
    }

    //Fetch & Display Payment Data
    private void setPaymentData() throws IOException {
        this.payment_model = new DefaultTableModel(new Object[][]{}, this.payment_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.paymentTable.setModel(this.payment_model);
        this.paymentData = this.fd.fetchTransactionData();
        int list_size = this.paymentData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.paymentData.get(row);
            this.payment_model.addRow(row_data);
        }
    }

    //Fetch & Display Feedback Data
    private void setFeedbackData() throws IOException {
        this.feedback_model = new DefaultTableModel(new Object[][]{}, this.feedback_column) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        ;
        };

        this.feedbackTable.setModel(this.feedback_model);
        this.feedbackData = this.fd.fetchFeedbackData();
        int list_size = this.feedbackData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.feedbackData.get(row);
            this.feedback_model.addRow(row_data);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        overall = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        header_icon = new javax.swing.JPanel();
        btnMin = new javax.swing.JPanel();
        lblMin = new javax.swing.JLabel();
        btnClose = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        btnHome = new javax.swing.JPanel();
        lblHome = new javax.swing.JLabel();
        sidemenu = new javax.swing.JPanel();
        sidemenu_icon = new javax.swing.JPanel();
        userManagementLine = new javax.swing.JPanel();
        userManagement = new javax.swing.JPanel();
        btnUserManagement = new javax.swing.JLabel();
        apptManagement = new javax.swing.JPanel();
        btnApptManagement = new javax.swing.JLabel();
        apptManagementLine = new javax.swing.JPanel();
        paymentManagementLine = new javax.swing.JPanel();
        paymentManagement = new javax.swing.JPanel();
        btnPaymentManagement = new javax.swing.JLabel();
        logoutLine = new javax.swing.JPanel();
        logout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        feedbackManagementLine = new javax.swing.JPanel();
        feedbackManagement = new javax.swing.JPanel();
        btnFeedbackManagement = new javax.swing.JLabel();
        settings = new javax.swing.JPanel();
        btnSettings = new javax.swing.JLabel();
        settingsLine = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        generate_report_panel = new javax.swing.JPanel();
        lblPeriodicity = new javax.swing.JLabel();
        lblTypeOfReport = new javax.swing.JLabel();
        cbPeriodicity = new javax.swing.JComboBox<>();
        cbTypeOfReport = new javax.swing.JComboBox<>();
        btnGenerateReport = new javax.swing.JButton();
        lblTypeOfReport1 = new javax.swing.JLabel();
        lblMonth = new javax.swing.JLabel();
        cbMonth = new javax.swing.JComboBox<>();
        update_details_panel = new javax.swing.JPanel();
        update_details_tabs = new javax.swing.JTabbedPane();
        update_user_tab = new javax.swing.JPanel();
        update_user_content = new javax.swing.JPanel();
        lblUUName = new javax.swing.JLabel();
        lblUUEmail = new javax.swing.JLabel();
        lblUUAddress = new javax.swing.JLabel();
        tfUUName = new javax.swing.JTextField();
        tfUUEmail = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        tfUUAddress = new javax.swing.JTextArea();
        tfUUPhoneNumber = new javax.swing.JTextField();
        lblUUPhoneNumber = new javax.swing.JLabel();
        lblUUGender = new javax.swing.JLabel();
        lblUURole = new javax.swing.JLabel();
        btnUUUpdate = new javax.swing.JButton();
        cbUURole = new javax.swing.JComboBox<>();
        cbUUGender = new javax.swing.JComboBox<>();
        update_client_tab = new javax.swing.JPanel();
        update_client_content = new javax.swing.JPanel();
        lblUCName = new javax.swing.JLabel();
        tfUCName = new javax.swing.JTextField();
        lblUCEmail = new javax.swing.JLabel();
        tfUCEmail = new javax.swing.JTextField();
        tfUCPhoneNumber = new javax.swing.JTextField();
        lblUCPhoneNumber = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tfUCAddress = new javax.swing.JTextArea();
        lblUCAddress = new javax.swing.JLabel();
        btnUCUpdate = new javax.swing.JButton();
        cbUCGender = new javax.swing.JComboBox<>();
        lblUCGender = new javax.swing.JLabel();
        update_appt_tab = new javax.swing.JPanel();
        update_appt_content = new javax.swing.JPanel();
        lblUACustomerId = new javax.swing.JLabel();
        lblUAApptDate = new javax.swing.JLabel();
        lblUARoomNo = new javax.swing.JLabel();
        lblUATechId = new javax.swing.JLabel();
        lblUAPayId = new javax.swing.JLabel();
        lblUAJobStatus = new javax.swing.JLabel();
        lblUACreatedBy = new javax.swing.JLabel();
        lblUAFeedId = new javax.swing.JLabel();
        tfUACusId = new javax.swing.JTextField();
        tfUAApptDate = new javax.swing.JTextField();
        tfUARoomNo = new javax.swing.JTextField();
        tfUAApptTime = new javax.swing.JTextField();
        tfUATechId = new javax.swing.JTextField();
        tfUACreatedBy = new javax.swing.JTextField();
        btnUpdateAppt = new javax.swing.JButton();
        cbUAJobStatus = new javax.swing.JComboBox<>();
        cbUAAppliance = new javax.swing.JComboBox<>();
        update_pay_tab = new javax.swing.JPanel();
        update_pay_content = new javax.swing.JPanel();
        tfUPPayAmount = new javax.swing.JTextField();
        tfUPApptId = new javax.swing.JTextField();
        tfUPPayDate = new javax.swing.JTextField();
        lblUPPayAmount = new javax.swing.JLabel();
        lblUPPayStatus = new javax.swing.JLabel();
        lblUPPayDate = new javax.swing.JLabel();
        btnUpdatePayment = new javax.swing.JButton();
        lblUPPayUpdatedBy = new javax.swing.JLabel();
        tfUPPayUpdatedBy = new javax.swing.JTextField();
        lblUPApptId1 = new javax.swing.JLabel();
        cbUPPayStatus = new javax.swing.JComboBox<>();
        update_feed_tab = new javax.swing.JPanel();
        update_feed_content = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tfUFApptId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfUFFeedDate = new javax.swing.JTextField();
        btnUpdateFeedback = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tfUFFeedContent = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        tfUFFeedUpdatedBy = new javax.swing.JTextField();
        personal_details_panel = new javax.swing.JPanel();
        personal_details_tabs = new javax.swing.JTabbedPane();
        account_details_panel = new javax.swing.JPanel();
        account_details_content = new javax.swing.JPanel();
        lblADName = new javax.swing.JLabel();
        lblADPhoneNumber = new javax.swing.JLabel();
        lblADAddress = new javax.swing.JLabel();
        lblADGender = new javax.swing.JLabel();
        lblADEmail = new javax.swing.JLabel();
        btnUpdateAccDetails = new javax.swing.JButton();
        tfADEmail = new javax.swing.JTextField();
        tfADName = new javax.swing.JTextField();
        tfADPhoneNumber = new javax.swing.JTextField();
        cbADGender = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tfADAddress = new javax.swing.JTextArea();
        reset_pwd_panel = new javax.swing.JPanel();
        reset_pwd_content = new javax.swing.JPanel();
        lblRPpwd = new javax.swing.JLabel();
        lblRPreppwd = new javax.swing.JLabel();
        pfRPreppwd = new javax.swing.JPasswordField();
        pfRPpwd = new javax.swing.JPasswordField();
        btnResetPassword = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        manage_feedback_panel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        feedbackTable = new javax.swing.JTable();
        btnGetFBDetails = new javax.swing.JButton();
        btnFeedbackDelete = new javax.swing.JButton();
        manage_payment_panel = new javax.swing.JTabbedPane();
        view_payment_record = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        btnGetPayDetails = new javax.swing.JButton();
        btnPaymentDelete = new javax.swing.JButton();
        manage_appt_panel = new javax.swing.JTabbedPane();
        appt_list_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        apptTable = new javax.swing.JTable();
        btnGetApptDetails = new javax.swing.JButton();
        btnApptDelete = new javax.swing.JButton();
        create_appt_panel = new javax.swing.JPanel();
        create_appt_content = new javax.swing.JPanel();
        cbCreateClient = new javax.swing.JComboBox<>();
        tfCreateRoomNo = new javax.swing.JTextField();
        cbCreateApptTime = new javax.swing.JComboBox<>();
        btnCreateAppointment = new javax.swing.JButton();
        lblCreateApptTime = new javax.swing.JLabel();
        lblCreateTechnician = new javax.swing.JLabel();
        lblCreateClient = new javax.swing.JLabel();
        lblCreateRoomNo = new javax.swing.JLabel();
        lblCreateApptDate = new javax.swing.JLabel();
        tfCreateApptDate = new javax.swing.JTextField();
        cbCreateTechnician = new javax.swing.JComboBox<>();
        lblCreateAppliance = new javax.swing.JLabel();
        cbCreateAppliance = new javax.swing.JComboBox<>();
        manage_user_panel = new javax.swing.JTabbedPane();
        user_list_panel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        btnGetUserDetails = new javax.swing.JButton();
        btnUserDelete = new javax.swing.JButton();
        client_list_panel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        btnGetClientDetails = new javax.swing.JButton();
        btnClientDelete = new javax.swing.JButton();
        register_user_panel = new javax.swing.JPanel();
        register_panel_content = new javax.swing.JPanel();
        client_register_panel = new javax.swing.JPanel();
        cbClientGender = new javax.swing.JComboBox<>();
        btnRegisterClient = new javax.swing.JButton();
        lblClientAddress = new javax.swing.JLabel();
        tfClientName = new javax.swing.JTextField();
        tfClientPhoneNumber = new javax.swing.JTextField();
        tfClientEmail = new javax.swing.JTextField();
        lblClientName = new javax.swing.JLabel();
        lblClientPhoneNumber = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tfClientAddress = new javax.swing.JTextArea();
        lblClientEmail = new javax.swing.JLabel();
        lblClientGender = new javax.swing.JLabel();
        staff_register_panel = new javax.swing.JPanel();
        tfUsername = new javax.swing.JTextField();
        cbUserGender = new javax.swing.JComboBox<>();
        btnRegisterUser = new javax.swing.JButton();
        lblUserAddress = new javax.swing.JLabel();
        pfPassword = new javax.swing.JPasswordField();
        tfUserName = new javax.swing.JTextField();
        tfUserPhoneNumber = new javax.swing.JTextField();
        tfUserEmail = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        lblUserPhoneNumber = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfUserAddress = new javax.swing.JTextArea();
        lblUserEmail = new javax.swing.JLabel();
        lblUserGender = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        cbRole = new javax.swing.JComboBox<>();
        rbStaff = new javax.swing.JRadioButton();
        rbClient = new javax.swing.JRadioButton();
        home_panel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        ongoingApptTable = new javax.swing.JTable();
        lblOngoingApptTitle = new javax.swing.JLabel();
        btnGenerateReports = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        overall.setBackground(new java.awt.Color(0, 0, 0));
        overall.setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(102, 102, 255));
        header.setPreferredSize(new java.awt.Dimension(800, 50));
        header.setLayout(new java.awt.BorderLayout());

        header_icon.setBackground(new java.awt.Color(0, 0, 0));
        header_icon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMin.setBackground(new java.awt.Color(0, 0, 0));
        btnMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinMouseExited(evt);
            }
        });
        btnMin.setLayout(new java.awt.BorderLayout());

        lblMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minus_32px.png"))); // NOI18N
        btnMin.add(lblMin, java.awt.BorderLayout.CENTER);

        header_icon.add(btnMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 50, 50));

        btnClose.setBackground(new java.awt.Color(0, 0, 0));
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCloseMouseExited(evt);
            }
        });
        btnClose.setLayout(new java.awt.BorderLayout());

        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close_32px.png"))); // NOI18N
        btnClose.add(lblClose, java.awt.BorderLayout.CENTER);

        header_icon.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 50));

        btnHome.setBackground(new java.awt.Color(0, 0, 0));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        btnHome.setLayout(new java.awt.BorderLayout());

        lblHome.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        lblHome.setForeground(new java.awt.Color(239, 183, 183));
        lblHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHome.setText("AHHASC");
        btnHome.add(lblHome, java.awt.BorderLayout.CENTER);

        header_icon.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 130, 50));

        header.add(header_icon, java.awt.BorderLayout.LINE_END);

        overall.add(header, java.awt.BorderLayout.PAGE_START);

        sidemenu.setBackground(new java.awt.Color(0, 0, 0));
        sidemenu.setForeground(new java.awt.Color(0, 0, 51));
        sidemenu.setPreferredSize(new java.awt.Dimension(50, 450));
        sidemenu.setLayout(new java.awt.BorderLayout());

        sidemenu_icon.setBackground(new java.awt.Color(0, 0, 0));
        sidemenu_icon.setPreferredSize(new java.awt.Dimension(50, 450));
        sidemenu_icon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userManagementLine.setBackground(new java.awt.Color(255, 255, 255));
        userManagementLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout userManagementLineLayout = new javax.swing.GroupLayout(userManagementLine);
        userManagementLine.setLayout(userManagementLineLayout);
        userManagementLineLayout.setHorizontalGroup(
            userManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        userManagementLineLayout.setVerticalGroup(
            userManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(userManagementLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, -1));

        userManagement.setBackground(new java.awt.Color(0, 0, 0));
        userManagement.setToolTipText("User Management");
        userManagement.setLayout(new java.awt.BorderLayout());

        btnUserManagement.setBackground(new java.awt.Color(51, 51, 51));
        btnUserManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUserManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user_50px.png"))); // NOI18N
        btnUserManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUserManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUserManagementMouseExited(evt);
            }
        });
        userManagement.add(btnUserManagement, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(userManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 50, 50));

        apptManagement.setBackground(new java.awt.Color(0, 0, 0));
        apptManagement.setLayout(new java.awt.BorderLayout());

        btnApptManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnApptManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clock_50px.png"))); // NOI18N
        btnApptManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnApptManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnApptManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnApptManagementMouseExited(evt);
            }
        });
        apptManagement.add(btnApptManagement, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(apptManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 50, 50));

        apptManagementLine.setBackground(new java.awt.Color(255, 255, 255));
        apptManagementLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout apptManagementLineLayout = new javax.swing.GroupLayout(apptManagementLine);
        apptManagementLine.setLayout(apptManagementLineLayout);
        apptManagementLineLayout.setHorizontalGroup(
            apptManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        apptManagementLineLayout.setVerticalGroup(
            apptManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(apptManagementLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 50, -1));

        paymentManagementLine.setBackground(new java.awt.Color(255, 255, 255));
        paymentManagementLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout paymentManagementLineLayout = new javax.swing.GroupLayout(paymentManagementLine);
        paymentManagementLine.setLayout(paymentManagementLineLayout);
        paymentManagementLineLayout.setHorizontalGroup(
            paymentManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        paymentManagementLineLayout.setVerticalGroup(
            paymentManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(paymentManagementLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 50, -1));

        paymentManagement.setBackground(new java.awt.Color(0, 0, 0));
        paymentManagement.setLayout(new java.awt.BorderLayout());

        btnPaymentManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPaymentManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/money_50px.png"))); // NOI18N
        btnPaymentManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPaymentManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPaymentManagementMouseExited(evt);
            }
        });
        paymentManagement.add(btnPaymentManagement, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(paymentManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 115, 50, 50));

        logoutLine.setBackground(new java.awt.Color(255, 255, 255));
        logoutLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout logoutLineLayout = new javax.swing.GroupLayout(logoutLine);
        logoutLine.setLayout(logoutLineLayout);
        logoutLineLayout.setHorizontalGroup(
            logoutLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        logoutLineLayout.setVerticalGroup(
            logoutLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(logoutLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, 50, -1));

        logout.setBackground(new java.awt.Color(0, 0, 0));
        logout.setLayout(new java.awt.BorderLayout());

        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout_32px.png"))); // NOI18N
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        logout.add(btnLogout, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 50, 45));

        feedbackManagementLine.setBackground(new java.awt.Color(255, 255, 255));
        feedbackManagementLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout feedbackManagementLineLayout = new javax.swing.GroupLayout(feedbackManagementLine);
        feedbackManagementLine.setLayout(feedbackManagementLineLayout);
        feedbackManagementLineLayout.setHorizontalGroup(
            feedbackManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        feedbackManagementLineLayout.setVerticalGroup(
            feedbackManagementLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(feedbackManagementLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 165, 50, -1));

        feedbackManagement.setBackground(new java.awt.Color(0, 0, 0));
        feedbackManagement.setLayout(new java.awt.BorderLayout());

        btnFeedbackManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnFeedbackManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/feedback_45px.jpg"))); // NOI18N
        btnFeedbackManagement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFeedbackManagementMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFeedbackManagementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFeedbackManagementMouseExited(evt);
            }
        });
        feedbackManagement.add(btnFeedbackManagement, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(feedbackManagement, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 50, 50));

        settings.setBackground(new java.awt.Color(0, 0, 0));
        settings.setLayout(new java.awt.BorderLayout());

        btnSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/settings_32px.png"))); // NOI18N
        btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSettingsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSettingsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSettingsMouseExited(evt);
            }
        });
        settings.add(btnSettings, java.awt.BorderLayout.CENTER);

        sidemenu_icon.add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 225, 50, 50));

        settingsLine.setBackground(new java.awt.Color(255, 255, 255));
        settingsLine.setPreferredSize(new java.awt.Dimension(50, 5));

        javax.swing.GroupLayout settingsLineLayout = new javax.swing.GroupLayout(settingsLine);
        settingsLine.setLayout(settingsLineLayout);
        settingsLineLayout.setHorizontalGroup(
            settingsLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        settingsLineLayout.setVerticalGroup(
            settingsLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sidemenu_icon.add(settingsLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 50, -1));

        sidemenu.add(sidemenu_icon, java.awt.BorderLayout.LINE_START);

        overall.add(sidemenu, java.awt.BorderLayout.LINE_START);

        dashboard.setBackground(new java.awt.Color(0, 0, 0));
        dashboard.setForeground(new java.awt.Color(51, 51, 51));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        generate_report_panel.setBackground(new java.awt.Color(0, 0, 0));
        generate_report_panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generate_report_panelMouseMoved(evt);
            }
        });
        generate_report_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPeriodicity.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPeriodicity.setForeground(new java.awt.Color(255, 255, 255));
        lblPeriodicity.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPeriodicity.setText("Periodicity:");
        generate_report_panel.add(lblPeriodicity, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 110, 30));

        lblTypeOfReport.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTypeOfReport.setForeground(new java.awt.Color(255, 255, 255));
        lblTypeOfReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTypeOfReport.setText("GENERATE REPORTS");
        generate_report_panel.add(lblTypeOfReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, -1));

        cbPeriodicity.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbPeriodicity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monthly", "Yearly" }));
        cbPeriodicity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPeriodicityActionPerformed(evt);
            }
        });
        generate_report_panel.add(cbPeriodicity, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 260, 30));

        cbTypeOfReport.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbTypeOfReport.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Transaction Report", "Feedback Report", "Appointment Report", "User Details Report" }));
        cbTypeOfReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTypeOfReportActionPerformed(evt);
            }
        });
        generate_report_panel.add(cbTypeOfReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 260, 30));

        btnGenerateReport.setBackground(new java.awt.Color(51, 51, 51));
        btnGenerateReport.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnGenerateReport.setForeground(new java.awt.Color(0, 255, 255));
        btnGenerateReport.setText("GENERATE REPORT");
        btnGenerateReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerateReportMouseClicked(evt);
            }
        });
        generate_report_panel.add(btnGenerateReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 260, 50));

        lblTypeOfReport1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTypeOfReport1.setForeground(new java.awt.Color(255, 255, 255));
        lblTypeOfReport1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTypeOfReport1.setText("Type of Report:");
        generate_report_panel.add(lblTypeOfReport1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 150, 30));

        lblMonth.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMonth.setText("Month:");
        generate_report_panel.add(lblMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 110, 30));

        cbMonth.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        cbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        generate_report_panel.add(cbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 260, 30));

        dashboard.add(generate_report_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        update_details_panel.setLayout(new java.awt.BorderLayout());

        update_details_tabs.setBackground(new java.awt.Color(255, 255, 255));
        update_details_tabs.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N

        update_user_tab.setBackground(new java.awt.Color(0, 0, 0));
        update_user_tab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_user_content.setBackground(new java.awt.Color(51, 51, 51));
        update_user_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUUName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUUName.setForeground(new java.awt.Color(255, 255, 255));
        lblUUName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUUName.setText("Name:");
        update_user_content.add(lblUUName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, 30));

        lblUUEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUUEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblUUEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUUEmail.setText("Email:");
        update_user_content.add(lblUUEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, 30));

        lblUUAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUUAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblUUAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUUAddress.setText("Address:");
        update_user_content.add(lblUUAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 80, 30));

        tfUUName.setBackground(new java.awt.Color(0, 0, 0));
        tfUUName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUUName.setForeground(new java.awt.Color(255, 255, 255));
        update_user_content.add(tfUUName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 200, 30));

        tfUUEmail.setBackground(new java.awt.Color(0, 0, 0));
        tfUUEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUUEmail.setForeground(new java.awt.Color(255, 255, 255));
        update_user_content.add(tfUUEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 200, 30));

        tfUUAddress.setBackground(new java.awt.Color(0, 0, 0));
        tfUUAddress.setColumns(20);
        tfUUAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUUAddress.setForeground(new java.awt.Color(255, 255, 255));
        tfUUAddress.setRows(5);
        jScrollPane9.setViewportView(tfUUAddress);

        update_user_content.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 180, 550, 130));

        tfUUPhoneNumber.setBackground(new java.awt.Color(0, 0, 0));
        tfUUPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUUPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        update_user_content.add(tfUUPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 210, 30));

        lblUUPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUUPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblUUPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUUPhoneNumber.setText("Phone Number:");
        update_user_content.add(lblUUPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 110, 30));

        lblUUGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUUGender.setForeground(new java.awt.Color(255, 255, 255));
        lblUUGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUUGender.setText("Gender:");
        update_user_content.add(lblUUGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 110, 30));

        lblUURole.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUURole.setForeground(new java.awt.Color(255, 255, 255));
        lblUURole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUURole.setText("Role:");
        update_user_content.add(lblUURole, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 50, 30));

        btnUUUpdate.setBackground(new java.awt.Color(0, 0, 0));
        btnUUUpdate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnUUUpdate.setForeground(new java.awt.Color(0, 255, 255));
        btnUUUpdate.setText("Update");
        btnUUUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUUUpdateMouseClicked(evt);
            }
        });
        update_user_content.add(btnUUUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 550, 50));

        cbUURole.setBackground(new java.awt.Color(0, 0, 0));
        cbUURole.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUURole.setForeground(new java.awt.Color(255, 255, 255));
        cbUURole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Technician" }));
        update_user_content.add(cbUURole, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 200, 30));

        cbUUGender.setBackground(new java.awt.Color(0, 0, 0));
        cbUUGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUUGender.setForeground(new java.awt.Color(255, 255, 255));
        cbUUGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        update_user_content.add(cbUUGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 210, 30));

        update_user_tab.add(update_user_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 23, 700, 380));

        update_details_tabs.addTab("Update User", update_user_tab);

        update_client_tab.setBackground(new java.awt.Color(0, 0, 0));
        update_client_tab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_client_content.setBackground(new java.awt.Color(51, 51, 51));
        update_client_content.setForeground(new java.awt.Color(255, 255, 255));
        update_client_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUCName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUCName.setForeground(new java.awt.Color(255, 255, 255));
        lblUCName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUCName.setText("Name:");
        update_client_content.add(lblUCName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 80, 30));

        tfUCName.setBackground(new java.awt.Color(0, 0, 0));
        tfUCName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUCName.setForeground(new java.awt.Color(255, 255, 255));
        update_client_content.add(tfUCName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 200, 30));

        lblUCEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUCEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblUCEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUCEmail.setText("Email:");
        update_client_content.add(lblUCEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 80, 30));

        tfUCEmail.setBackground(new java.awt.Color(0, 0, 0));
        tfUCEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUCEmail.setForeground(new java.awt.Color(255, 255, 255));
        update_client_content.add(tfUCEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 200, 30));

        tfUCPhoneNumber.setBackground(new java.awt.Color(0, 0, 0));
        tfUCPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUCPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        update_client_content.add(tfUCPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 210, 30));

        lblUCPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUCPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblUCPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUCPhoneNumber.setText("Phone Number:");
        update_client_content.add(lblUCPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 110, 30));

        tfUCAddress.setBackground(new java.awt.Color(0, 0, 0));
        tfUCAddress.setColumns(20);
        tfUCAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUCAddress.setForeground(new java.awt.Color(255, 255, 255));
        tfUCAddress.setRows(5);
        jScrollPane11.setViewportView(tfUCAddress);

        update_client_content.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 550, 140));

        lblUCAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUCAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblUCAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUCAddress.setText("Address:");
        update_client_content.add(lblUCAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 80, 30));

        btnUCUpdate.setBackground(new java.awt.Color(0, 0, 0));
        btnUCUpdate.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnUCUpdate.setForeground(new java.awt.Color(0, 255, 255));
        btnUCUpdate.setText("Update");
        btnUCUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUCUpdateMouseClicked(evt);
            }
        });
        update_client_content.add(btnUCUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 550, 50));

        cbUCGender.setBackground(new java.awt.Color(0, 0, 0));
        cbUCGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUCGender.setForeground(new java.awt.Color(255, 255, 255));
        cbUCGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        update_client_content.add(cbUCGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, 210, 30));

        lblUCGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUCGender.setForeground(new java.awt.Color(255, 255, 255));
        lblUCGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUCGender.setText("Gender:");
        update_client_content.add(lblUCGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 110, 30));

        update_client_tab.add(update_client_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 22, 700, 380));

        update_details_tabs.addTab("Update Client", update_client_tab);

        update_appt_tab.setBackground(new java.awt.Color(0, 0, 0));
        update_appt_tab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_appt_content.setBackground(new java.awt.Color(51, 51, 51));
        update_appt_content.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUACustomerId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUACustomerId.setForeground(new java.awt.Color(255, 255, 255));
        lblUACustomerId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUACustomerId.setText("Customer ID:");
        update_appt_content.add(lblUACustomerId, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, 30));

        lblUAApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUAApptDate.setForeground(new java.awt.Color(255, 255, 255));
        lblUAApptDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUAApptDate.setText("Appointment Date:");
        update_appt_content.add(lblUAApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));

        lblUARoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUARoomNo.setForeground(new java.awt.Color(255, 255, 255));
        lblUARoomNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUARoomNo.setText("Room No:");
        update_appt_content.add(lblUARoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, 30));

        lblUATechId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUATechId.setForeground(new java.awt.Color(255, 255, 255));
        lblUATechId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUATechId.setText("Technician ID:");
        update_appt_content.add(lblUATechId, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 110, 30));

        lblUAPayId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUAPayId.setForeground(new java.awt.Color(255, 255, 255));
        lblUAPayId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUAPayId.setText("Appliance:");
        update_appt_content.add(lblUAPayId, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, 80, 30));

        lblUAJobStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUAJobStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblUAJobStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUAJobStatus.setText("Job Status:");
        update_appt_content.add(lblUAJobStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, -1, 30));

        lblUACreatedBy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUACreatedBy.setForeground(new java.awt.Color(255, 255, 255));
        lblUACreatedBy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUACreatedBy.setText("Created By:");
        update_appt_content.add(lblUACreatedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, -1, 30));

        lblUAFeedId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUAFeedId.setForeground(new java.awt.Color(255, 255, 255));
        lblUAFeedId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUAFeedId.setText("Appointment Time:");
        update_appt_content.add(lblUAFeedId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 30));

        tfUACusId.setBackground(new java.awt.Color(0, 0, 0));
        tfUACusId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUACusId.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUACusId, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 180, 30));

        tfUAApptDate.setBackground(new java.awt.Color(0, 0, 0));
        tfUAApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUAApptDate.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUAApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 180, 30));

        tfUARoomNo.setBackground(new java.awt.Color(0, 0, 0));
        tfUARoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUARoomNo.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUARoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 180, 30));

        tfUAApptTime.setBackground(new java.awt.Color(0, 0, 0));
        tfUAApptTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUAApptTime.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUAApptTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 180, 30));

        tfUATechId.setBackground(new java.awt.Color(0, 0, 0));
        tfUATechId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUATechId.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUATechId, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, 180, 30));

        tfUACreatedBy.setBackground(new java.awt.Color(0, 0, 0));
        tfUACreatedBy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUACreatedBy.setForeground(new java.awt.Color(255, 255, 255));
        update_appt_content.add(tfUACreatedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 200, 180, 30));

        btnUpdateAppt.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdateAppt.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnUpdateAppt.setForeground(new java.awt.Color(255, 153, 0));
        btnUpdateAppt.setText("Update");
        btnUpdateAppt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateApptMouseClicked(evt);
            }
        });
        update_appt_content.add(btnUpdateAppt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 590, 70));

        cbUAJobStatus.setBackground(new java.awt.Color(0, 0, 0));
        cbUAJobStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUAJobStatus.setForeground(new java.awt.Color(255, 255, 255));
        cbUAJobStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Completed", "Cancelled" }));
        update_appt_content.add(cbUAJobStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, 180, 30));

        cbUAAppliance.setBackground(new java.awt.Color(0, 0, 0));
        cbUAAppliance.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUAAppliance.setForeground(new java.awt.Color(255, 255, 255));
        cbUAAppliance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Washing Machine", "Refrigerator", "Water Heater", "Electronic Devices", "Air Conditioner", "Others" }));
        update_appt_content.add(cbUAAppliance, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 180, 30));

        update_appt_tab.add(update_appt_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 690, 380));

        update_details_tabs.addTab("Update Appt", update_appt_tab);

        update_pay_tab.setBackground(new java.awt.Color(0, 0, 0));
        update_pay_tab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_pay_content.setBackground(new java.awt.Color(51, 51, 51));
        update_pay_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfUPPayAmount.setBackground(new java.awt.Color(0, 0, 0));
        tfUPPayAmount.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUPPayAmount.setForeground(new java.awt.Color(255, 255, 255));
        update_pay_content.add(tfUPPayAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 220, 30));

        tfUPApptId.setBackground(new java.awt.Color(0, 0, 0));
        tfUPApptId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUPApptId.setForeground(new java.awt.Color(255, 255, 255));
        update_pay_content.add(tfUPApptId, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 220, 30));

        tfUPPayDate.setBackground(new java.awt.Color(0, 0, 0));
        tfUPPayDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUPPayDate.setForeground(new java.awt.Color(255, 255, 255));
        update_pay_content.add(tfUPPayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 220, 30));

        lblUPPayAmount.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUPPayAmount.setForeground(new java.awt.Color(255, 255, 255));
        lblUPPayAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUPPayAmount.setText("Payment Amount:");
        update_pay_content.add(lblUPPayAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 30));

        lblUPPayStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUPPayStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblUPPayStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUPPayStatus.setText("Payment Status:");
        update_pay_content.add(lblUPPayStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, -1, 30));

        lblUPPayDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUPPayDate.setForeground(new java.awt.Color(255, 255, 255));
        lblUPPayDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUPPayDate.setText("Payment Date:");
        update_pay_content.add(lblUPPayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 110, 30));

        btnUpdatePayment.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdatePayment.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnUpdatePayment.setForeground(new java.awt.Color(0, 255, 102));
        btnUpdatePayment.setText("Update");
        btnUpdatePayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdatePaymentMouseClicked(evt);
            }
        });
        update_pay_content.add(btnUpdatePayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 530, 60));

        lblUPPayUpdatedBy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUPPayUpdatedBy.setForeground(new java.awt.Color(255, 255, 255));
        lblUPPayUpdatedBy.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUPPayUpdatedBy.setText("Updated By:");
        update_pay_content.add(lblUPPayUpdatedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, 30));

        tfUPPayUpdatedBy.setBackground(new java.awt.Color(0, 0, 0));
        tfUPPayUpdatedBy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUPPayUpdatedBy.setForeground(new java.awt.Color(255, 255, 255));
        update_pay_content.add(tfUPPayUpdatedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 220, 30));

        lblUPApptId1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUPApptId1.setForeground(new java.awt.Color(255, 255, 255));
        lblUPApptId1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUPApptId1.setText("Appointment ID:");
        update_pay_content.add(lblUPApptId1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 30));

        cbUPPayStatus.setBackground(new java.awt.Color(0, 0, 0));
        cbUPPayStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUPPayStatus.setForeground(new java.awt.Color(255, 255, 255));
        cbUPPayStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Paid", "Refunded", "Cancelled" }));
        update_pay_content.add(cbUPPayStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 160, 30));

        update_pay_tab.add(update_pay_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 380));

        update_details_tabs.addTab("Update Payment", update_pay_tab);

        update_feed_tab.setBackground(new java.awt.Color(0, 0, 0));
        update_feed_tab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_feed_content.setBackground(new java.awt.Color(51, 51, 51));
        update_feed_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Appointment ID:");
        update_feed_content.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, 30));

        tfUFApptId.setBackground(new java.awt.Color(0, 0, 0));
        tfUFApptId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUFApptId.setForeground(new java.awt.Color(255, 255, 255));
        update_feed_content.add(tfUFApptId, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 220, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Feedback Content:");
        update_feed_content.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 30));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Feedback Date:");
        update_feed_content.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 30));

        tfUFFeedDate.setBackground(new java.awt.Color(0, 0, 0));
        tfUFFeedDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUFFeedDate.setForeground(new java.awt.Color(255, 255, 255));
        update_feed_content.add(tfUFFeedDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 220, 30));

        btnUpdateFeedback.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdateFeedback.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnUpdateFeedback.setForeground(new java.awt.Color(255, 255, 0));
        btnUpdateFeedback.setText("Update");
        btnUpdateFeedback.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateFeedbackMouseClicked(evt);
            }
        });
        update_feed_content.add(btnUpdateFeedback, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 440, 60));

        tfUFFeedContent.setBackground(new java.awt.Color(0, 0, 0));
        tfUFFeedContent.setColumns(20);
        tfUFFeedContent.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUFFeedContent.setForeground(new java.awt.Color(255, 255, 255));
        tfUFFeedContent.setRows(5);
        jScrollPane12.setViewportView(tfUFFeedContent);

        update_feed_content.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 440, 140));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Updated By:");
        update_feed_content.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, -1, 30));

        tfUFFeedUpdatedBy.setBackground(new java.awt.Color(0, 0, 0));
        tfUFFeedUpdatedBy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUFFeedUpdatedBy.setForeground(new java.awt.Color(255, 255, 255));
        update_feed_content.add(tfUFFeedUpdatedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 220, 30));

        update_feed_tab.add(update_feed_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 26, 690, 380));

        update_details_tabs.addTab("Update Feedback", update_feed_tab);

        update_details_panel.add(update_details_tabs, java.awt.BorderLayout.CENTER);
        update_details_tabs.getAccessibleContext().setAccessibleName("Update User");

        dashboard.add(update_details_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        personal_details_panel.setBackground(new java.awt.Color(0, 0, 0));
        personal_details_panel.setLayout(new java.awt.BorderLayout());

        account_details_panel.setBackground(new java.awt.Color(0, 0, 0));
        account_details_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        account_details_content.setBackground(new java.awt.Color(51, 51, 51));
        account_details_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblADName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblADName.setForeground(new java.awt.Color(255, 255, 255));
        lblADName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblADName.setText("Name:");
        account_details_content.add(lblADName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, -1, -1));

        lblADPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblADPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblADPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblADPhoneNumber.setText("Phone Number:");
        account_details_content.add(lblADPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 110, -1));

        lblADAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblADAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblADAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblADAddress.setText("Address:");
        account_details_content.add(lblADAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        lblADGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblADGender.setForeground(new java.awt.Color(255, 255, 255));
        lblADGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblADGender.setText("Gender:");
        account_details_content.add(lblADGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 60, -1));

        lblADEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblADEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblADEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblADEmail.setText("Email:");
        account_details_content.add(lblADEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, -1, -1));

        btnUpdateAccDetails.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdateAccDetails.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnUpdateAccDetails.setForeground(new java.awt.Color(0, 255, 255));
        btnUpdateAccDetails.setText("Update Account Details");
        btnUpdateAccDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateAccDetailsMouseClicked(evt);
            }
        });
        account_details_content.add(btnUpdateAccDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 450, 60));

        tfADEmail.setBackground(new java.awt.Color(0, 0, 0));
        tfADEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfADEmail.setForeground(new java.awt.Color(255, 255, 255));
        account_details_content.add(tfADEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 170, 40));

        tfADName.setBackground(new java.awt.Color(0, 0, 0));
        tfADName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfADName.setForeground(new java.awt.Color(255, 255, 255));
        account_details_content.add(tfADName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 170, 40));

        tfADPhoneNumber.setBackground(new java.awt.Color(0, 0, 0));
        tfADPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfADPhoneNumber.setForeground(new java.awt.Color(255, 255, 255));
        account_details_content.add(tfADPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 170, 40));

        cbADGender.setBackground(new java.awt.Color(0, 0, 0));
        cbADGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbADGender.setForeground(new java.awt.Color(255, 255, 255));
        cbADGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        account_details_content.add(cbADGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 170, 40));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Personal Details Page");
        account_details_content.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 710, 50));

        tfADAddress.setBackground(new java.awt.Color(0, 0, 0));
        tfADAddress.setColumns(20);
        tfADAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfADAddress.setForeground(new java.awt.Color(255, 255, 255));
        tfADAddress.setRows(5);
        jScrollPane10.setViewportView(tfADAddress);

        account_details_content.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 450, -1));

        account_details_panel.add(account_details_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 390));

        personal_details_tabs.addTab("Account Details", account_details_panel);

        reset_pwd_panel.setBackground(new java.awt.Color(0, 0, 0));
        reset_pwd_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reset_pwd_content.setBackground(new java.awt.Color(51, 51, 51));
        reset_pwd_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRPpwd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRPpwd.setForeground(new java.awt.Color(255, 255, 255));
        lblRPpwd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRPpwd.setText("Password:");
        reset_pwd_content.add(lblRPpwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 80, -1));

        lblRPreppwd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRPreppwd.setForeground(new java.awt.Color(255, 255, 255));
        lblRPreppwd.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRPreppwd.setText("Repeat Password:");
        reset_pwd_content.add(lblRPreppwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 130, -1));

        pfRPreppwd.setBackground(new java.awt.Color(0, 0, 0));
        pfRPreppwd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        pfRPreppwd.setForeground(new java.awt.Color(255, 255, 255));
        reset_pwd_content.add(pfRPreppwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 190, 40));

        pfRPpwd.setBackground(new java.awt.Color(0, 0, 0));
        pfRPpwd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        pfRPpwd.setForeground(new java.awt.Color(255, 255, 255));
        reset_pwd_content.add(pfRPpwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 190, 40));

        btnResetPassword.setBackground(new java.awt.Color(0, 0, 0));
        btnResetPassword.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnResetPassword.setForeground(new java.awt.Color(255, 153, 0));
        btnResetPassword.setText("Reset Password");
        btnResetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetPasswordMouseClicked(evt);
            }
        });
        reset_pwd_content.add(btnResetPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 190, 50));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reset Password");
        reset_pwd_content.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 700, -1));

        reset_pwd_panel.add(reset_pwd_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 12, 700, 400));

        personal_details_tabs.addTab("Reset Password", reset_pwd_panel);

        personal_details_panel.add(personal_details_tabs, java.awt.BorderLayout.CENTER);

        dashboard.add(personal_details_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        manage_feedback_panel.setBackground(new java.awt.Color(0, 0, 0));
        manage_feedback_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane7.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane7.setForeground(new java.awt.Color(51, 51, 51));

        feedbackTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        feedbackTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Feedback ID", "Appointment ID", "Feedback Content", "Feedback Date", "Updated By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(feedbackTable);

        manage_feedback_panel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 360));

        btnGetFBDetails.setBackground(new java.awt.Color(0, 0, 0));
        btnGetFBDetails.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGetFBDetails.setForeground(new java.awt.Color(255, 51, 204));
        btnGetFBDetails.setText("Update");
        btnGetFBDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGetFBDetailsMouseClicked(evt);
            }
        });
        manage_feedback_panel.add(btnGetFBDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 140, 40));

        btnFeedbackDelete.setBackground(new java.awt.Color(0, 0, 0));
        btnFeedbackDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnFeedbackDelete.setForeground(new java.awt.Color(255, 51, 204));
        btnFeedbackDelete.setText("Delete");
        btnFeedbackDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFeedbackDeleteMouseClicked(evt);
            }
        });
        manage_feedback_panel.add(btnFeedbackDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 140, 40));

        dashboard.add(manage_feedback_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        view_payment_record.setBackground(new java.awt.Color(0, 0, 0));
        view_payment_record.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setForeground(new java.awt.Color(51, 51, 51));

        paymentTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment ID", "Client ID", "Payment Date", "Payment Amount", "Payment Status", "Updated By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(paymentTable);

        view_payment_record.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 340));

        btnGetPayDetails.setBackground(new java.awt.Color(51, 51, 51));
        btnGetPayDetails.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGetPayDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnGetPayDetails.setText("Update");
        btnGetPayDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGetPayDetailsMouseClicked(evt);
            }
        });
        view_payment_record.add(btnGetPayDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 140, 40));

        btnPaymentDelete.setBackground(new java.awt.Color(51, 51, 51));
        btnPaymentDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnPaymentDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnPaymentDelete.setText("Delete");
        btnPaymentDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentDeleteMouseClicked(evt);
            }
        });
        view_payment_record.add(btnPaymentDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 140, 40));

        manage_payment_panel.addTab("Payment Records", view_payment_record);

        dashboard.add(manage_payment_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        appt_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        appt_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setForeground(new java.awt.Color(51, 51, 51));

        apptTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        apptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Appt ID", "Client ID", "Room No", "Appt Date", "Appt Time", "Appliance", "Technician ID", "Job Status", "Created By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(apptTable);

        appt_list_panel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 350));

        btnGetApptDetails.setBackground(new java.awt.Color(255, 255, 255));
        btnGetApptDetails.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGetApptDetails.setText("Update");
        btnGetApptDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGetApptDetailsMouseClicked(evt);
            }
        });
        appt_list_panel.add(btnGetApptDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 140, 40));

        btnApptDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnApptDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnApptDelete.setText("Delete");
        btnApptDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnApptDeleteMouseClicked(evt);
            }
        });
        appt_list_panel.add(btnApptDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 140, 40));

        manage_appt_panel.addTab("Appt List\n", appt_list_panel);

        create_appt_panel.setBackground(new java.awt.Color(0, 0, 0));
        create_appt_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create_appt_content.setBackground(new java.awt.Color(51, 51, 51));
        create_appt_content.setForeground(new java.awt.Color(51, 51, 51));
        create_appt_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbCreateClient.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateClient.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateClient.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(cbCreateClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 210, 30));

        tfCreateRoomNo.setBackground(new java.awt.Color(0, 0, 0));
        tfCreateRoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCreateRoomNo.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(tfCreateRoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 140, 30));

        cbCreateApptTime.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateApptTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateApptTime.setForeground(new java.awt.Color(255, 255, 255));
        cbCreateApptTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:00", "10:00", "12:00", "14:00", "16:00", "18:00" }));
        create_appt_content.add(cbCreateApptTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 140, 30));

        btnCreateAppointment.setBackground(new java.awt.Color(255, 255, 255));
        btnCreateAppointment.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnCreateAppointment.setText("CREATE APPOINTMENT");
        btnCreateAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateAppointmentActionPerformed(evt);
            }
        });
        create_appt_content.add(btnCreateAppointment, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 640, 60));

        lblCreateApptTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateApptTime.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateApptTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateApptTime.setText("Appointment Time:");
        create_appt_content.add(lblCreateApptTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 150, 30));

        lblCreateTechnician.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateTechnician.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateTechnician.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateTechnician.setText("Technician:");
        create_appt_content.add(lblCreateTechnician, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 100, 30));

        lblCreateClient.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateClient.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateClient.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateClient.setText("Client:");
        create_appt_content.add(lblCreateClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 100, 30));

        lblCreateRoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateRoomNo.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateRoomNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateRoomNo.setText("Room No:");
        create_appt_content.add(lblCreateRoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 100, 30));

        lblCreateApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateApptDate.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateApptDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateApptDate.setText("Appointment Date:");
        create_appt_content.add(lblCreateApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 150, 30));

        tfCreateApptDate.setBackground(new java.awt.Color(0, 0, 0));
        tfCreateApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCreateApptDate.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(tfCreateApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 140, 30));

        cbCreateTechnician.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateTechnician.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateTechnician.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(cbCreateTechnician, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 210, 30));

        lblCreateAppliance.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateAppliance.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateAppliance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateAppliance.setText("Appliance:");
        create_appt_content.add(lblCreateAppliance, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 90, 30));

        cbCreateAppliance.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateAppliance.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateAppliance.setForeground(new java.awt.Color(255, 255, 255));
        cbCreateAppliance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Washing Machine", "Water Heater", "Air Conditioner", "Electronic Devices", "Refrigerator" }));
        create_appt_content.add(cbCreateAppliance, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 170, 30));

        create_appt_panel.add(create_appt_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_appt_panel.addTab("Create\n", create_appt_panel);

        dashboard.add(manage_appt_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        manage_user_panel.setBackground(new java.awt.Color(255, 255, 255));
        manage_user_panel.setPreferredSize(new java.awt.Dimension(750, 450));

        user_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        user_list_panel.setPreferredSize(new java.awt.Dimension(775, 423));
        user_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane4.setForeground(new java.awt.Color(51, 51, 51));

        userTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Username", "Name", "Phone Number", "Address", "Email", "Gender", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(userTable);

        user_list_panel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 330));

        btnGetUserDetails.setBackground(new java.awt.Color(51, 51, 51));
        btnGetUserDetails.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGetUserDetails.setForeground(new java.awt.Color(0, 255, 255));
        btnGetUserDetails.setText("Update");
        btnGetUserDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGetUserDetailsMouseClicked(evt);
            }
        });
        user_list_panel.add(btnGetUserDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 140, 40));

        btnUserDelete.setBackground(new java.awt.Color(51, 51, 51));
        btnUserDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUserDelete.setForeground(new java.awt.Color(0, 255, 255));
        btnUserDelete.setText("Delete");
        btnUserDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserDeleteMouseClicked(evt);
            }
        });
        user_list_panel.add(btnUserDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 140, 40));

        manage_user_panel.addTab("User List", user_list_panel);

        client_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        client_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane5.setForeground(new java.awt.Color(51, 51, 51));

        clientTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client ID", "Name", "Phone Number", "Room No", "Email", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(clientTable);

        client_list_panel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 330));

        btnGetClientDetails.setBackground(new java.awt.Color(51, 51, 51));
        btnGetClientDetails.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGetClientDetails.setForeground(new java.awt.Color(0, 255, 255));
        btnGetClientDetails.setText("Update");
        btnGetClientDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGetClientDetailsMouseClicked(evt);
            }
        });
        client_list_panel.add(btnGetClientDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 140, 40));

        btnClientDelete.setBackground(new java.awt.Color(51, 51, 51));
        btnClientDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnClientDelete.setForeground(new java.awt.Color(0, 255, 255));
        btnClientDelete.setText("Delete");
        btnClientDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClientDeleteMouseClicked(evt);
            }
        });
        client_list_panel.add(btnClientDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 140, 40));

        manage_user_panel.addTab("Client List", client_list_panel);

        register_user_panel.setBackground(new java.awt.Color(0, 0, 0));
        register_user_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        register_panel_content.setBackground(new java.awt.Color(51, 51, 51));
        register_panel_content.setForeground(new java.awt.Color(51, 51, 51));
        register_panel_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        client_register_panel.setBackground(new java.awt.Color(204, 204, 204));
        client_register_panel.setForeground(new java.awt.Color(204, 204, 204));
        client_register_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbClientGender.setBackground(new java.awt.Color(0, 0, 0));
        cbClientGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbClientGender.setForeground(java.awt.Color.white);
        cbClientGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        client_register_panel.add(cbClientGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 130, 30));

        btnRegisterClient.setBackground(new java.awt.Color(51, 51, 51));
        btnRegisterClient.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnRegisterClient.setForeground(new java.awt.Color(0, 204, 204));
        btnRegisterClient.setText("Register");
        btnRegisterClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterClientActionPerformed(evt);
            }
        });
        client_register_panel.add(btnRegisterClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 190, 40));

        lblClientAddress.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblClientAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientAddress.setText("Address:");
        client_register_panel.add(lblClientAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 30));

        tfClientName.setBackground(new java.awt.Color(0, 0, 0));
        tfClientName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfClientName.setForeground(java.awt.Color.white);
        client_register_panel.add(tfClientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 130, 30));

        tfClientPhoneNumber.setBackground(new java.awt.Color(0, 0, 0));
        tfClientPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfClientPhoneNumber.setForeground(java.awt.Color.white);
        client_register_panel.add(tfClientPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 130, 30));

        tfClientEmail.setBackground(new java.awt.Color(0, 0, 0));
        tfClientEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfClientEmail.setForeground(java.awt.Color.white);
        tfClientEmail.setToolTipText("");
        client_register_panel.add(tfClientEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 130, 30));

        lblClientName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblClientName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientName.setText("Name:");
        client_register_panel.add(lblClientName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 40, 30));

        lblClientPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblClientPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientPhoneNumber.setText("Phone Number:");
        client_register_panel.add(lblClientPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        tfClientAddress.setBackground(new java.awt.Color(0, 0, 0));
        tfClientAddress.setColumns(20);
        tfClientAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfClientAddress.setForeground(java.awt.Color.white);
        tfClientAddress.setRows(5);
        jScrollPane6.setViewportView(tfClientAddress);

        client_register_panel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 420, 80));

        lblClientEmail.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblClientEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientEmail.setText("Email:");
        client_register_panel.add(lblClientEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 60, 30));

        lblClientGender.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblClientGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientGender.setText("Gender:");
        client_register_panel.add(lblClientGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 60, 30));

        register_panel_content.add(client_register_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 580, 320));

        staff_register_panel.setBackground(new java.awt.Color(204, 204, 204));
        staff_register_panel.setForeground(new java.awt.Color(204, 204, 204));
        staff_register_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tfUsername.setBackground(new java.awt.Color(0, 0, 0));
        tfUsername.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUsername.setForeground(java.awt.Color.white);
        staff_register_panel.add(tfUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 130, 30));

        cbUserGender.setBackground(new java.awt.Color(0, 0, 0));
        cbUserGender.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUserGender.setForeground(java.awt.Color.white);
        cbUserGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        staff_register_panel.add(cbUserGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 130, 30));

        btnRegisterUser.setBackground(new java.awt.Color(51, 51, 51));
        btnRegisterUser.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnRegisterUser.setForeground(new java.awt.Color(0, 204, 204));
        btnRegisterUser.setText("Register");
        btnRegisterUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterUserActionPerformed(evt);
            }
        });
        staff_register_panel.add(btnRegisterUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 190, 40));

        lblUserAddress.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUserAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserAddress.setText("Address:");
        staff_register_panel.add(lblUserAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 30));

        pfPassword.setBackground(new java.awt.Color(0, 0, 0));
        pfPassword.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        pfPassword.setForeground(java.awt.Color.white);
        staff_register_panel.add(pfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 130, 30));

        tfUserName.setBackground(new java.awt.Color(0, 0, 0));
        tfUserName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUserName.setForeground(java.awt.Color.white);
        staff_register_panel.add(tfUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 130, 30));

        tfUserPhoneNumber.setBackground(new java.awt.Color(0, 0, 0));
        tfUserPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUserPhoneNumber.setForeground(java.awt.Color.white);
        staff_register_panel.add(tfUserPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 130, 30));

        tfUserEmail.setBackground(new java.awt.Color(0, 0, 0));
        tfUserEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUserEmail.setForeground(java.awt.Color.white);
        staff_register_panel.add(tfUserEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 130, 30));

        lblUsername.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("Username:");
        staff_register_panel.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 30));

        lblPassword.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPassword.setText("Password:");
        staff_register_panel.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, 30));

        lblUserName.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserName.setText("Name:");
        staff_register_panel.add(lblUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 40, 30));

        lblUserPhoneNumber.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUserPhoneNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserPhoneNumber.setText("Phone Number:");
        staff_register_panel.add(lblUserPhoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 140, -1, 30));

        tfUserAddress.setBackground(new java.awt.Color(0, 0, 0));
        tfUserAddress.setColumns(20);
        tfUserAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfUserAddress.setForeground(new java.awt.Color(255, 255, 255));
        tfUserAddress.setRows(5);
        jScrollPane1.setViewportView(tfUserAddress);

        staff_register_panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 420, 80));

        lblUserEmail.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUserEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserEmail.setText("Email:");
        staff_register_panel.add(lblUserEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 60, 30));

        lblUserGender.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblUserGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserGender.setText("Gender:");
        staff_register_panel.add(lblUserGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 60, 30));

        lblRole.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRole.setText("Role:");
        staff_register_panel.add(lblRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 40, 30));

        cbRole.setBackground(new java.awt.Color(0, 0, 0));
        cbRole.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRole.setForeground(java.awt.Color.white);
        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Technician", "Manager" }));
        cbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRoleActionPerformed(evt);
            }
        });
        staff_register_panel.add(cbRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 130, 30));

        register_panel_content.add(staff_register_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 580, 320));

        rbStaff.setBackground(new java.awt.Color(51, 51, 51));
        rbStaff.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        rbStaff.setForeground(new java.awt.Color(255, 255, 255));
        rbStaff.setText("Staff");
        rbStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbStaffMouseClicked(evt);
            }
        });
        register_panel_content.add(rbStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, -1, -1));

        rbClient.setBackground(new java.awt.Color(51, 51, 51));
        rbClient.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        rbClient.setForeground(new java.awt.Color(255, 255, 255));
        rbClient.setText("Client");
        rbClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbClientMouseClicked(evt);
            }
        });
        register_panel_content.add(rbClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, -1, -1));

        register_user_panel.add(register_panel_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 32, 690, 360));

        manage_user_panel.addTab("Registration\n", register_user_panel);

        dashboard.add(manage_user_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        home_panel.setBackground(new java.awt.Color(0, 0, 0));
        home_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane8.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane8.setForeground(new java.awt.Color(51, 51, 51));

        ongoingApptTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ongoingApptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Appt ID", "Client ID", "Room No", "Appt Date", "Feedback ID", "Technician ID", "Payment ID", "Job Status", "Created By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(ongoingApptTable);
        if (ongoingApptTable.getColumnModel().getColumnCount() > 0) {
            ongoingApptTable.getColumnModel().getColumn(6).setResizable(false);
        }

        home_panel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 730, 370));

        lblOngoingApptTitle.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblOngoingApptTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblOngoingApptTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOngoingApptTitle.setText("Ongoing Appointments");
        home_panel.add(lblOngoingApptTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 490, 40));

        btnGenerateReports.setBackground(new java.awt.Color(51, 51, 51));
        btnGenerateReports.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnGenerateReports.setForeground(new java.awt.Color(0, 255, 255));
        btnGenerateReports.setText("Generate Reports");
        btnGenerateReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerateReportsMouseClicked(evt);
            }
        });
        home_panel.add(btnGenerateReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, -1));

        dashboard.add(home_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        overall.add(dashboard, java.awt.BorderLayout.CENTER);

        getContentPane().add(overall, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    private void changeFontColor(JLabel hover, Color rand) {
        hover.setForeground(rand);
    }

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        changeColor(btnClose, new Color(255, 105, 180));
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        changeColor(btnClose, new Color(0, 0, 0));
    }//GEN-LAST:event_btnCloseMouseExited

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Log Out?", "Goodbye? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Stay Safe and Goodbye Now! :')", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            al.logUserActivity(userCacheData[0], "Exited and Logged Out");
            login lg = new login();
            lg.logout();
            System.exit(0); //Exit 
        } else {
            //Remain in Application
        }
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseEntered
        changeColor(btnMin, new Color(239, 183, 183, 255));
    }//GEN-LAST:event_btnMinMouseEntered

    private void btnMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseExited
        changeColor(btnMin, new Color(0, 0, 0));
    }//GEN-LAST:event_btnMinMouseExited

    private void btnMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinMouseClicked

    private void btnUserManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseEntered
        changeColor(userManagementLine, new Color(255, 165, 0, 255));
        btnUserManagement.setToolTipText("User Management");
    }//GEN-LAST:event_btnUserManagementMouseEntered

    private void btnUserManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseExited
        changeColor(userManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnUserManagementMouseExited

    private void btnUserManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseClicked
        this.hideMainPanels();
        this.hideRegisterPanels();
        this.manage_user_panel.setVisible(true);
    }//GEN-LAST:event_btnUserManagementMouseClicked

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        this.changeFontColor(lblHome, new Color(255, 255, 255));
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        this.hideMainPanels();
        this.home_panel.setVisible(true);
        try {
            this.setAllData();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.resetAllForms();
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        this.changeFontColor(lblHome, new Color(239, 183, 183, 255));
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnApptManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseEntered
        changeColor(apptManagementLine, new Color(255, 105, 180));
        btnApptManagement.setToolTipText("Appointment Management");
    }//GEN-LAST:event_btnApptManagementMouseEntered

    private void btnApptManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseExited
        changeColor(apptManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnApptManagementMouseExited

    private void btnApptManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseClicked
        this.hideMainPanels();
        this.manage_appt_panel.setVisible(true);
    }//GEN-LAST:event_btnApptManagementMouseClicked

    private void btnPaymentManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseEntered
        changeColor(paymentManagementLine, new Color(255, 255, 0, 255));
        btnPaymentManagement.setToolTipText("Payment Management");
    }//GEN-LAST:event_btnPaymentManagementMouseEntered

    private void btnPaymentManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseExited
        changeColor(paymentManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnPaymentManagementMouseExited

    private void btnPaymentManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseClicked
        this.hideMainPanels();
        this.manage_payment_panel.setVisible(true);
    }//GEN-LAST:event_btnPaymentManagementMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        changeColor(logoutLine, new Color(247, 78, 105));
        btnLogout.setToolTipText("Logout");
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        changeColor(logoutLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        this.dispose();
        al.logUserActivity(userCacheData[0], "Logged Out");
        login lg = new login();
        lg.logout();
        login_page lgPage = new login_page();
        lgPage.setVisible(true);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnFeedbackManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFeedbackManagementMouseClicked
        this.hideMainPanels();
        this.manage_feedback_panel.setVisible(true);
    }//GEN-LAST:event_btnFeedbackManagementMouseClicked

    private void btnFeedbackManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFeedbackManagementMouseEntered
        changeColor(feedbackManagementLine, new Color(96, 15, 255));
        btnFeedbackManagement.setToolTipText("Feedback Management");
    }//GEN-LAST:event_btnFeedbackManagementMouseEntered

    private void btnFeedbackManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFeedbackManagementMouseExited
        changeColor(feedbackManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnFeedbackManagementMouseExited

    private void rbClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbClientMouseClicked
        if (rbClient.isSelected() == true) {
            this.client_register_panel.setVisible(true);
            this.staff_register_panel.setVisible(false);
            rbStaff.setSelected(false);
            this.resetClientRegistrationForm();
        } else {
            this.client_register_panel.setVisible(false);
            this.resetClientRegistrationForm();
        }
    }//GEN-LAST:event_rbClientMouseClicked

    private void rbStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbStaffMouseClicked
        if (rbStaff.isSelected() == true) {
            this.staff_register_panel.setVisible(true);
            this.client_register_panel.setVisible(false);
            rbClient.setSelected(false);
            this.resetUserRegistrationForm();
        } else {
            this.staff_register_panel.setVisible(false);
            this.resetUserRegistrationForm();
        }
    }//GEN-LAST:event_rbStaffMouseClicked

    private void btnSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseClicked
        this.hideMainPanels();
        this.personal_details_panel.setVisible(true);
    }//GEN-LAST:event_btnSettingsMouseClicked

    private void btnSettingsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseEntered
        changeColor(settingsLine, new Color(191, 64, 191));
        btnSettings.setToolTipText("Personal Details");
    }//GEN-LAST:event_btnSettingsMouseEntered

    private void btnSettingsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseExited
        changeColor(settingsLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnSettingsMouseExited

    //Register New Client
    private void btnRegisterClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterClientActionPerformed
        if (um.validateClientFields(tfClientName, tfClientEmail, tfClientPhoneNumber, tfClientAddress)) {
            um.addUserInformation(null, null, null, tfClientName.getText(), tfClientEmail.getText(), tfClientPhoneNumber.getText(), tfClientAddress.getText(), (String) cbClientGender.getSelectedItem(), "Customer");

            al.logUserActivity(userCacheData[0], "Registered New Client: " + tfClientName.getText());
            this.resetClientRegistrationForm();
            try {
                this.setClientData();
                this.manage_user_panel.setSelectedIndex(1);
                this.rbClient.setSelected(false);
                this.hideRegisterPanels();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnRegisterClientActionPerformed

    //Register New User 
    private void btnRegisterUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterUserActionPerformed
        if (um.validateUserFields(tfUsername, tfUserName, tfUserEmail, tfUserPhoneNumber, tfUserAddress, (String) cbRole.getSelectedItem()) && um.validatePasswordField(pfPassword)) {
            um.addUserInformation(tfUsername.getText(), pfPassword.getText(), pfPassword.getText(), tfUserName.getText(), tfUserAddress.getText(), tfUserEmail.getText(), tfUserPhoneNumber.getText(), (String) cbUserGender.getSelectedItem(), (String) cbRole.getSelectedItem());

            al.logUserActivity(userCacheData[0], "Registered New " + cbRole.getSelectedItem() + ": " + tfUserName.getText());
            this.resetUserRegistrationForm();
            try {
                this.setUserData();
                this.manage_user_panel.setSelectedIndex(0);
                this.rbStaff.setSelected(false);
                this.hideRegisterPanels();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnRegisterUserActionPerformed

    //Delete User Data
    private void btnUserDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserDeleteMouseClicked
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\user_details.txt";
        userID = String.valueOf(userTable.getValueAt(userTable.getSelectedRow(), 0));

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Delete this User: " + userID, "Delete User? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.deleteResponse = um.deleteUserData(saveDir, userID, 1, "\\|");
            JOptionPane.showMessageDialog(null, "Deletion in Progess...", "Deleting", JOptionPane.INFORMATION_MESSAGE);

            //React to Returned Response
            if (this.deleteResponse == true) {
                al.logUserActivity(userCacheData[0], "Deleted User: " + userID);
                JOptionPane.showMessageDialog(null, "Successfully Deleted User: " + userID, "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                try {
                    this.setUserData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUserDeleteMouseClicked

    //Delete Client Data
    private void btnClientDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientDeleteMouseClicked
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\customer_details.txt";
        clientID = String.valueOf(clientTable.getValueAt(clientTable.getSelectedRow(), 0));

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Delete this Customer: " + clientID, "Delete Client? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.deleteResponse = um.deleteUserData(saveDir, clientID, 1, "\\|");
            JOptionPane.showMessageDialog(null, "Deletion in Progess...", "Deleting", JOptionPane.INFORMATION_MESSAGE);

            //React to Returned Response
            if (this.deleteResponse == true) {
                al.logUserActivity(userCacheData[0], "Deleted Client: " + clientID);
                JOptionPane.showMessageDialog(null, "Successfully Deleted Customer: " + clientID, "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                try {
                    this.setClientData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnClientDeleteMouseClicked

    //Delete Appointment Data
    private void btnApptDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptDeleteMouseClicked
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\appointment_details.txt";
        apptID = String.valueOf(apptTable.getValueAt(apptTable.getSelectedRow(), 0));

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Delete this Appointment: " + apptID, "Delete Appointment? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.deleteResponse = am.deleteApptData(saveDir, apptID, 1, "\\|");
            JOptionPane.showMessageDialog(null, "Deletion in Progess...", "Deleting", JOptionPane.INFORMATION_MESSAGE);

            //React to Returned Response
            if (this.deleteResponse == true) {
                al.logUserActivity(userCacheData[0], "Deleted Appointment: " + apptID);
                JOptionPane.showMessageDialog(null, "Successfully Deleted Appointment: " + apptID, "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                try {
                    this.setApptData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnApptDeleteMouseClicked

    //Delete Payment Data
    private void btnPaymentDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentDeleteMouseClicked
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\transaction.txt";
        payID = String.valueOf(paymentTable.getValueAt(paymentTable.getSelectedRow(), 0));

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Delete this Payment: " + payID, "Delete Payment? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.deleteResponse = am.deleteApptData(saveDir, payID, 1, "\\|");
            JOptionPane.showMessageDialog(null, "Deletion in Progess...", "Deleting", JOptionPane.INFORMATION_MESSAGE);

            //React to Returned Response
            if (this.deleteResponse == true) {
                al.logUserActivity(userCacheData[0], "Deleted Payment: " + payID);
                JOptionPane.showMessageDialog(null, "Successfully Deleted Payment: " + payID, "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                try {
                    this.setPaymentData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnPaymentDeleteMouseClicked

    //Delete Feedback Data
    private void btnFeedbackDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFeedbackDeleteMouseClicked
        String saveDir = System.getProperty("user.dir") + "\\src\\database\\feedback.txt";
        feedbackID = String.valueOf(feedbackTable.getValueAt(feedbackTable.getSelectedRow(), 0));

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Delete this Feedback: " + feedbackID, "Delete Appointment? :(", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.deleteResponse = am.deleteApptData(saveDir, feedbackID, 1, "\\|");
            JOptionPane.showMessageDialog(null, "Deletion in Progess...", "Deleting", JOptionPane.INFORMATION_MESSAGE);

            //React to Returned Response
            if (this.deleteResponse == true) {
                al.logUserActivity(userCacheData[0], "Deleted Feedback: " + feedbackID);
                JOptionPane.showMessageDialog(null, "Successfully Deleted Feedback: " + feedbackID, "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                try {
                    this.setFeedbackData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnFeedbackDeleteMouseClicked

    //Update Own Account Details
    private void btnUpdateAccDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateAccDetailsMouseClicked
        this.updateResponse = false;
        boolean validate = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update Your Details?", "Update Profile?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (um.validateUserFields(null, tfADName, tfADEmail, tfADPhoneNumber, tfADAddress, this.userCacheData[2])) {
                    validate = true;
                    this.updateResponse = um.updateProfileData(
                            userCacheData[0],
                            tfADName.getText(),
                            tfADPhoneNumber.getText(),
                            tfADAddress.getText(),
                            tfADEmail.getText(),
                            this.cbADGender.getSelectedItem().toString());

                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    validate = false;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated His/Her Profile Details");
                try {
                    this.setUserData();
                    this.currentUserDataList = this.fd.fetchUserBasedId(userCacheData[0]);
                    this.currentUserData = this.currentUserDataList.toArray(new String[0]);
                    this.resetChangeAccDetailsForm();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Successfully Updated Profile Information", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUpdateAccDetailsMouseClicked

    //Reset Own Account Password
    private void btnResetPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetPasswordMouseClicked
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update Your Password?", "Update Password?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (this.um.validatePasswordField(this.pfRPpwd)) {
                if (this.pfRPpwd.getText().equals(this.pfRPreppwd.getText())) {
                    try {
                        this.updateResponse = um.updateUserPwd(
                                userCacheData[0],
                                pfRPreppwd.getText()
                        );
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);

                    //React to Returned Response
                    if (this.updateResponse == true) {
                        al.logUserActivity(userCacheData[0], "Resetted His/Her Account's Password");
                        this.resetResetPasswordForm();
                        JOptionPane.showMessageDialog(null, "Successfully Updated Your Password", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password is not the Same!", "Password and Confirm Password Different", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Password Validation Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnResetPasswordMouseClicked

    //Set Selected Details to Update Forms
    private void btnGetUserDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetUserDetailsMouseClicked
        try {
            this.fetchSelectedUserData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(0);

            tfUUName.setText(selectedUserData[2]);
            tfUUEmail.setText(selectedUserData[5]);
            tfUUPhoneNumber.setText(selectedUserData[3]);
            cbUUGender.setSelectedItem(selectedUserData[6]);
            cbUURole.setSelectedItem(selectedUserData[7]);
            tfUUAddress.setText(selectedUserData[4]);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetUserDetailsMouseClicked

    private void btnGetClientDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetClientDetailsMouseClicked
        try {
            this.fetchSelectedClientData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(1);

            tfUCName.setText(selectedClientData[1]);
            tfUCEmail.setText(selectedClientData[4]);
            tfUCPhoneNumber.setText(selectedClientData[2]);
            cbUCGender.setSelectedItem(selectedClientData[5]);
            tfUCAddress.setText(selectedClientData[3]);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetClientDetailsMouseClicked

    private void btnGetApptDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetApptDetailsMouseClicked
        try {
            this.fetchSelectedApptData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(2);

            tfUACusId.setText(selectedApptData[1]);
            tfUARoomNo.setText(selectedApptData[4]);
            tfUAApptDate.setText(selectedApptData[2]);
            cbUAJobStatus.setSelectedItem(selectedApptData[7]);
            tfUAApptTime.setText(selectedApptData[3]);
            tfUATechId.setText(selectedApptData[6]);
            cbUAAppliance.setSelectedItem(selectedApptData[5]);
            tfUACreatedBy.setText(selectedApptData[8]);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetApptDetailsMouseClicked

    private void btnGetPayDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetPayDetailsMouseClicked
        try {
            this.fetchSelectedPayData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(3);

            tfUPApptId.setText(selectedPayData[1]);
            tfUPPayDate.setText(selectedPayData[2]);
            tfUPPayAmount.setText(selectedPayData[3]);
            cbUPPayStatus.setSelectedItem(selectedPayData[4]);
            tfUPPayUpdatedBy.setText(selectedPayData[5]);
            
            apptDate = String.valueOf(this.fd.fetchApptBasedId(selectedPayData[1]).get(2));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetPayDetailsMouseClicked

    private void btnGetFBDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetFBDetailsMouseClicked
        try {
            this.fetchSelectedFeedbackData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(4);

            tfUFApptId.setText(selectedFeedbackData[1]);
            tfUFFeedDate.setText(selectedFeedbackData[3]);
            tfUFFeedContent.setText(selectedFeedbackData[2]);
            tfUFFeedUpdatedBy.setText(selectedFeedbackData[4]);
            
            apptDate = String.valueOf(this.fd.fetchApptBasedId(selectedFeedbackData[1]).get(2));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetFBDetailsMouseClicked

    //Update Functions for All Forms
    private void btnUUUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUUUpdateMouseClicked
        boolean validate = false;
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update the Details for User: " + selectedUserData[1] + "?", "Update User Data?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (um.validateUserFields(null, tfUUName, tfUUEmail, tfUUPhoneNumber, tfUUAddress, cbUURole.getSelectedItem().toString())) {
                    validate = true;
                    this.updateResponse = this.um.updateUserData(
                            selectedUserData[0],
                            tfUUName.getText(),
                            tfUUPhoneNumber.getText(),
                            tfUUAddress.getText(),
                            tfUUEmail.getText(),
                            cbUUGender.getSelectedItem().toString(),
                            cbUURole.getSelectedItem().toString()
                    );

                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    validate = false;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated User: " + selectedUserData[1] + "'s Data");
                this.resetUpdateUserForm();
                try {
                    this.setUserData();
                    this.currentUserDataList = this.fd.fetchUserBasedId(userCacheData[0]);
                    this.currentUserData = this.currentUserDataList.toArray(new String[0]);
                    this.resetChangeAccDetailsForm();

                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                this.update_details_panel.setVisible(false);
                this.manage_user_panel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Successfully Updated User: " + selectedUserData[1] + "'s Data!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUUUpdateMouseClicked

    //Update Specific Client Data
    private void btnUCUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUCUpdateMouseClicked
        boolean validate = false;
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update the Details for Client: " + selectedClientData[1] + "?", "Update User Data?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (um.validateClientFields(tfUCName, tfUCEmail, tfUCPhoneNumber, tfUCAddress)) {
                    validate = true;
                    this.updateResponse = this.um.updateClientData(
                            selectedClientData[0],
                            tfUCName.getText(),
                            tfUCPhoneNumber.getText(),
                            tfUCAddress.getText(),
                            tfUCEmail.getText(),
                            cbUCGender.getSelectedItem().toString()
                    );

                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Client: " + selectedClientData[1] + "'s Data");
                this.resetUpdateClientForm();
                try {
                    this.setClientData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                this.update_details_panel.setVisible(false);
                this.manage_user_panel.setVisible(true);
                this.manage_user_panel.setSelectedIndex(1);
                JOptionPane.showMessageDialog(null, "Successfully Updated Client: " + selectedClientData[1] + "'s Data!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUCUpdateMouseClicked

    //Update Specific Appointment Data
    private void btnUpdateApptMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateApptMouseClicked
        boolean validate = false;
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update the Details for Appt: " + selectedApptData[0] + "?", "Update User Data?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (this.am.validateApptFields(tfUARoomNo)) {
                    validate = true;
                    this.updateResponse = this.am.updateApptData(
                            selectedApptData[0],
                            tfUARoomNo.getText(),
                            cbUAJobStatus.getSelectedItem().toString(),
                            cbUAJobStatus.getSelectedItem().toString(),
                            userCacheData[0]
                    );
                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    validate = false;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Appointment: " + selectedApptData[1] + "'s Data");
                this.resetUpdateApptForm();
                try {
                    this.setApptData();
                    this.setPaymentData();
                    this.setOngoingApptData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                this.update_details_panel.setVisible(false);
                this.manage_appt_panel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Successfully Updated Appt: " + selectedApptData[1] + "'s Data!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUpdateApptMouseClicked

    //Update Specific Payment Data
    private void btnUpdatePaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdatePaymentMouseClicked
        boolean validate = false;
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update the Details for Payment: " + selectedPayData[0] + "?", "Update User Data?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (this.am.validatePaymentFields(tfUPPayDate, tfUPPayAmount, apptDate)) {
                    validate = true;
                    this.updateResponse = this.am.updatePayData(
                            selectedPayData[0],
                            tfUPPayDate.getText(),
                            tfUPPayAmount.getText(),
                            cbUPPayStatus.getSelectedItem().toString(),
                            userCacheData[0]
                    );

                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    validate = false;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Payment: " + selectedPayData[0] + "'s Data");
                this.resetUpdatePayForm();
                try {
                    this.setPaymentData();
                    this.setApptData();
                    this.setOngoingApptData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                this.update_details_panel.setVisible(false);
                this.manage_payment_panel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Successfully Updated Payment: " + selectedPayData[0] + "'s Data!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);

                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUpdatePaymentMouseClicked

    //Update Specific Feedback Data
    private void btnUpdateFeedbackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateFeedbackMouseClicked
        boolean validate = false;
        this.updateResponse = false;

        if (JOptionPane.showConfirmDialog(null, "Are You Sure You Want Update the Details for Feedback: " + selectedFeedbackData[0] + "?", "Update User Data?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                if (this.am.validateFeedbackFields(tfUFFeedDate, apptDate)) {
                    validate = true;
                    this.updateResponse = this.am.updateFeedbackData(
                            selectedFeedbackData[0],
                            tfUFFeedContent.getText(),
                            tfUFFeedDate.getText(),
                            userCacheData[0]
                    );

                    JOptionPane.showMessageDialog(null, "Update in Progess...", "Updating", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    validate = false;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Feedback: " + selectedFeedbackData[0] + "'s Data");
                this.resetUpdateFeedbackForm();
                try {
                    this.setFeedbackData();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                this.update_details_panel.setVisible(false);
                this.manage_feedback_panel.setVisible(true);
                JOptionPane.showMessageDialog(null, "Successfully Updated Feedback: " + selectedFeedbackData[0] + "'s Data!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!validate) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something Went Wrong, Please Try Again Later!", "Something Went Wrong!", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else {
            //Nothing Happens.
        }
    }//GEN-LAST:event_btnUpdateFeedbackMouseClicked

    //Creating New Appointment
    private void btnCreateAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateAppointmentActionPerformed
        // TODO add your handling code here:
        String technician = this.retrieveId((String) cbCreateTechnician.getSelectedItem(), "Technician");
        String client = this.retrieveId((String) cbCreateClient.getSelectedItem(), "Client");
        String appliance = (String) cbCreateAppliance.getSelectedItem();
        String roomNo = tfCreateRoomNo.getText();
        String date = tfCreateApptDate.getText();
        String time = (String) cbCreateApptTime.getSelectedItem();

        try {
            if (this.am.validateCreateApptFields(tfCreateRoomNo, tfCreateApptDate)) {
                this.am.addApptInformation(client, roomNo, date, time, appliance, technician, this.userCacheData[0]);
                al.logUserActivity(userCacheData[0], "Created and Appointment for client: " + client);
                try {
                    this.setApptData();
                    this.setOngoingApptData();
                    
                    this.resetCreateApptForm();
                    this.manage_appt_panel.setSelectedIndex(0);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCreateAppointmentActionPerformed

    private void btnGenerateReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerateReportsMouseClicked
        this.hideMainPanels();
        this.generate_report_panel.setVisible(true);
    }//GEN-LAST:event_btnGenerateReportsMouseClicked

    private void generate_report_panelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generate_report_panelMouseMoved
        // this.checkSelection();
    }//GEN-LAST:event_generate_report_panelMouseMoved

    private void btnGenerateReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerateReportMouseClicked
        // Transaction Report
        if (cbTypeOfReport.getSelectedIndex() == 0) {
            // Monthly
            if (cbPeriodicity.getSelectedIndex() == 0) {
                try {
                    gr.createTransactionReport("Monthly", cbMonth.getSelectedIndex());
                    al.logUserActivity(userCacheData[0], "Generated Monthly Transaction Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                // Yearly
                try {
                    gr.createTransactionReport("Yearly", Integer.parseInt((String) cbMonth.getSelectedItem()));
                    al.logUserActivity(userCacheData[0], "Generated Yearly Transaction Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }

        // Feedback Report
        if (cbTypeOfReport.getSelectedIndex() == 1) {
            // Monthly
            if (cbPeriodicity.getSelectedIndex() == 0) {
                try {
                    gr.createFeedbackReport("Monthly", cbMonth.getSelectedIndex());
                    al.logUserActivity(userCacheData[0], "Generated Monthly Feedback Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                // Yearly
                try {
                    gr.createFeedbackReport("Yearly", Integer.parseInt((String) cbMonth.getSelectedItem()));
                    al.logUserActivity(userCacheData[0], "Generated Yearly Feedback Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }

        // Appointment Report
        if (cbTypeOfReport.getSelectedIndex() == 2) {
            // Monthly
            if (cbPeriodicity.getSelectedIndex() == 0) {
                try {
                    gr.createApptReport("Monthly", cbMonth.getSelectedIndex());
                    al.logUserActivity(userCacheData[0], "Generated Monthly Appointment Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } else {
                // Yearly
                try {
                    gr.createApptReport("Yearly", Integer.parseInt((String) cbMonth.getSelectedItem()));
                    al.logUserActivity(userCacheData[0], "Generated Yearly Appointment Report");
                    JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }

        // User Details Report
        if (cbTypeOfReport.getSelectedIndex() == 3) {
            try {
                gr.createUserDetailsReport();
                al.logUserActivity(userCacheData[0], "Generated User Details Report");
                JOptionPane.showMessageDialog(null, "Report has been generated!", "Success: Report Generation", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btnGenerateReportMouseClicked

    private void cbRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRoleActionPerformed

    private void cbPeriodicityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPeriodicityActionPerformed
        // TODO add your handling code here:
        this.checkSelection();
    }//GEN-LAST:event_cbPeriodicityActionPerformed

    private void cbTypeOfReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTypeOfReportActionPerformed
        // TODO add your handling code here:
        this.checkSelection();
    }//GEN-LAST:event_cbTypeOfReportActionPerformed

    //Check and Disable/ Enable Fields
    private void checkSelection() {
        // User Details Report
        if (cbTypeOfReport.getSelectedIndex() == 3) {
            cbPeriodicity.setEnabled(false);
            cbMonth.setEnabled(false);
        } else if (cbPeriodicity.getSelectedIndex() == 1) {
            // Yearly Report
            cbMonth.setEnabled(true);
            changeMonthlyYearly("Year");
        } else {
            //Monthly Report
            cbPeriodicity.setEnabled(true);
            cbMonth.setEnabled(true);
            changeMonthlyYearly("Month");
        }
    }

    // Change Monthly / Yearly Selection Box
    private void changeMonthlyYearly(String type) {
        cbMonth.removeAllItems();
        
        if (type.equals("Month")) {
            lblMonth.setText("Month: ");
            
            cbMonth.addItem("January");
            cbMonth.addItem("February");
            cbMonth.addItem("March");
            cbMonth.addItem("April");
            cbMonth.addItem("May");
            cbMonth.addItem("June");
            cbMonth.addItem("July");
            cbMonth.addItem("August");
            cbMonth.addItem("September");
            cbMonth.addItem("October");
            cbMonth.addItem("November");
            cbMonth.addItem("December");
            
        } else if (type.equals("Year")) {
            lblMonth.setText("Year: ");
            
            cbMonth.addItem("2021");
            cbMonth.addItem("2022");
        }
    }
    
    //Reset Forms
    private void resetAllForms() {
        this.resetClientRegistrationForm();
        this.resetUserRegistrationForm();
        this.resetCreateApptForm();
        this.resetChangeAccDetailsForm();
        this.resetResetPasswordForm();
        this.resetUpdateUserForm();
        this.resetUpdateClientForm();
        this.resetUpdateApptForm();
        this.resetUpdatePayForm();
        this.resetUpdateFeedbackForm();
        this.resetGenerateReportForm();
    }

    private void resetClientRegistrationForm() {
        tfClientName.setText("");
        tfClientPhoneNumber.setText("");
        tfClientEmail.setText("");
        tfClientAddress.setText("");
        cbClientGender.setSelectedItem("Male");
    }

    private void resetUserRegistrationForm() {
        tfUsername.setText("");
        pfPassword.setText("");
        tfUserName.setText("");
        tfUserPhoneNumber.setText("");
        tfUserEmail.setText("");
        tfUserAddress.setText("");
        cbUserGender.setSelectedItem("Male");
        cbRole.setSelectedItem("Technician");
    }

    private void resetCreateApptForm() {
        cbCreateApptTime.setSelectedIndex(0);
        cbCreateClient.setSelectedIndex(0);
        tfCreateRoomNo.setText("");
        tfCreateApptDate.setText("");
        cbCreateAppliance.setSelectedIndex(0);
        cbCreateApptTime.setSelectedIndex(0);
    }

    private void resetChangeAccDetailsForm() {
        tfADName.setText(this.currentUserData[2]);
        tfADPhoneNumber.setText(this.currentUserData[3]);
        tfADAddress.setText(this.currentUserData[4]);
        cbADGender.setSelectedItem(this.currentUserData[6]);
        tfADEmail.setText(this.currentUserData[5]);
    }

    private void resetResetPasswordForm() {
        pfRPpwd.setText("");
        pfRPreppwd.setText("");
    }

    private void resetUpdateUserForm() {
        tfUUName.setText("");
        tfUUEmail.setText("");
        tfUUPhoneNumber.setText("");
        cbUUGender.setSelectedItem("Male");
        cbUURole.setSelectedItem("Manager");
        tfUUAddress.setText("");
    }

    private void resetUpdateClientForm() {
        tfUCName.setText("");
        tfUCPhoneNumber.setText("");
        tfUCAddress.setText("");
        tfUCEmail.setText("");
        cbUCGender.setSelectedItem("Male");
    }

    private void resetUpdateApptForm() {
        tfUACusId.setText("");
        tfUARoomNo.setText("");
        tfUAApptDate.setText("");
        cbUAJobStatus.setSelectedItem("Pending");
        tfUAApptTime.setText("");
        tfUATechId.setText("");
        cbUAAppliance.setSelectedItem("Washing Machine");
        tfUACreatedBy.setText("");
    }

    private void resetUpdatePayForm() {
        tfUPApptId.setText("");
        tfUPPayDate.setText("");
        tfUPPayAmount.setText("");
        cbUPPayStatus.setSelectedItem("Pending");
        tfUPPayUpdatedBy.setText("");
    }

    private void resetUpdateFeedbackForm() {
        tfUFApptId.setText("");
        tfUFFeedDate.setText("");
        tfUFFeedContent.setText("");
        tfUFFeedUpdatedBy.setText("");
    }

    private void resetGenerateReportForm() {
        cbMonth.setSelectedIndex(0);
        cbPeriodicity.setSelectedIndex(0);
        cbTypeOfReport.setSelectedIndex(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel account_details_content;
    private javax.swing.JPanel account_details_panel;
    private javax.swing.JPanel apptManagement;
    private javax.swing.JPanel apptManagementLine;
    private javax.swing.JTable apptTable;
    private javax.swing.JPanel appt_list_panel;
    private javax.swing.JButton btnApptDelete;
    private javax.swing.JLabel btnApptManagement;
    private javax.swing.JButton btnClientDelete;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnCreateAppointment;
    private javax.swing.JButton btnFeedbackDelete;
    private javax.swing.JLabel btnFeedbackManagement;
    private javax.swing.JButton btnGenerateReport;
    private javax.swing.JButton btnGenerateReports;
    private javax.swing.JButton btnGetApptDetails;
    private javax.swing.JButton btnGetClientDetails;
    private javax.swing.JButton btnGetFBDetails;
    private javax.swing.JButton btnGetPayDetails;
    private javax.swing.JButton btnGetUserDetails;
    private javax.swing.JPanel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JPanel btnMin;
    private javax.swing.JButton btnPaymentDelete;
    private javax.swing.JLabel btnPaymentManagement;
    private javax.swing.JButton btnRegisterClient;
    private javax.swing.JButton btnRegisterUser;
    private javax.swing.JButton btnResetPassword;
    private javax.swing.JLabel btnSettings;
    private javax.swing.JButton btnUCUpdate;
    private javax.swing.JButton btnUUUpdate;
    private javax.swing.JButton btnUpdateAccDetails;
    private javax.swing.JButton btnUpdateAppt;
    private javax.swing.JButton btnUpdateFeedback;
    private javax.swing.JButton btnUpdatePayment;
    private javax.swing.JButton btnUserDelete;
    private javax.swing.JLabel btnUserManagement;
    private javax.swing.JComboBox<String> cbADGender;
    private javax.swing.JComboBox<String> cbClientGender;
    private javax.swing.JComboBox<String> cbCreateAppliance;
    private javax.swing.JComboBox<String> cbCreateApptTime;
    private javax.swing.JComboBox<String> cbCreateClient;
    private javax.swing.JComboBox<String> cbCreateTechnician;
    private javax.swing.JComboBox<String> cbMonth;
    private javax.swing.JComboBox<String> cbPeriodicity;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JComboBox<String> cbTypeOfReport;
    private javax.swing.JComboBox<String> cbUAAppliance;
    private javax.swing.JComboBox<String> cbUAJobStatus;
    private javax.swing.JComboBox<String> cbUCGender;
    private javax.swing.JComboBox<String> cbUPPayStatus;
    private javax.swing.JComboBox<String> cbUUGender;
    private javax.swing.JComboBox<String> cbUURole;
    private javax.swing.JComboBox<String> cbUserGender;
    private javax.swing.JTable clientTable;
    private javax.swing.JPanel client_list_panel;
    private javax.swing.JPanel client_register_panel;
    private javax.swing.JPanel create_appt_content;
    private javax.swing.JPanel create_appt_panel;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel feedbackManagement;
    private javax.swing.JPanel feedbackManagementLine;
    private javax.swing.JTable feedbackTable;
    private javax.swing.JPanel generate_report_panel;
    private javax.swing.JPanel header;
    private javax.swing.JPanel header_icon;
    private javax.swing.JPanel home_panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblADAddress;
    private javax.swing.JLabel lblADEmail;
    private javax.swing.JLabel lblADGender;
    private javax.swing.JLabel lblADName;
    private javax.swing.JLabel lblADPhoneNumber;
    private javax.swing.JLabel lblClientAddress;
    private javax.swing.JLabel lblClientEmail;
    private javax.swing.JLabel lblClientGender;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientPhoneNumber;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCreateAppliance;
    private javax.swing.JLabel lblCreateApptDate;
    private javax.swing.JLabel lblCreateApptTime;
    private javax.swing.JLabel lblCreateClient;
    private javax.swing.JLabel lblCreateRoomNo;
    private javax.swing.JLabel lblCreateTechnician;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblMin;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblOngoingApptTitle;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPeriodicity;
    private javax.swing.JLabel lblRPpwd;
    private javax.swing.JLabel lblRPreppwd;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTypeOfReport;
    private javax.swing.JLabel lblTypeOfReport1;
    private javax.swing.JLabel lblUAApptDate;
    private javax.swing.JLabel lblUACreatedBy;
    private javax.swing.JLabel lblUACustomerId;
    private javax.swing.JLabel lblUAFeedId;
    private javax.swing.JLabel lblUAJobStatus;
    private javax.swing.JLabel lblUAPayId;
    private javax.swing.JLabel lblUARoomNo;
    private javax.swing.JLabel lblUATechId;
    private javax.swing.JLabel lblUCAddress;
    private javax.swing.JLabel lblUCEmail;
    private javax.swing.JLabel lblUCGender;
    private javax.swing.JLabel lblUCName;
    private javax.swing.JLabel lblUCPhoneNumber;
    private javax.swing.JLabel lblUPApptId1;
    private javax.swing.JLabel lblUPPayAmount;
    private javax.swing.JLabel lblUPPayDate;
    private javax.swing.JLabel lblUPPayStatus;
    private javax.swing.JLabel lblUPPayUpdatedBy;
    private javax.swing.JLabel lblUUAddress;
    private javax.swing.JLabel lblUUEmail;
    private javax.swing.JLabel lblUUGender;
    private javax.swing.JLabel lblUUName;
    private javax.swing.JLabel lblUUPhoneNumber;
    private javax.swing.JLabel lblUURole;
    private javax.swing.JLabel lblUserAddress;
    private javax.swing.JLabel lblUserEmail;
    private javax.swing.JLabel lblUserGender;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserPhoneNumber;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel logoutLine;
    private javax.swing.JTabbedPane manage_appt_panel;
    private javax.swing.JPanel manage_feedback_panel;
    private javax.swing.JTabbedPane manage_payment_panel;
    private javax.swing.JTabbedPane manage_user_panel;
    private javax.swing.JTable ongoingApptTable;
    private javax.swing.JPanel overall;
    private javax.swing.JPanel paymentManagement;
    private javax.swing.JPanel paymentManagementLine;
    private javax.swing.JTable paymentTable;
    private javax.swing.JPanel personal_details_panel;
    private javax.swing.JTabbedPane personal_details_tabs;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JPasswordField pfRPpwd;
    private javax.swing.JPasswordField pfRPreppwd;
    private javax.swing.JRadioButton rbClient;
    private javax.swing.JRadioButton rbStaff;
    private javax.swing.JPanel register_panel_content;
    private javax.swing.JPanel register_user_panel;
    private javax.swing.JPanel reset_pwd_content;
    private javax.swing.JPanel reset_pwd_panel;
    private javax.swing.JPanel settings;
    private javax.swing.JPanel settingsLine;
    private javax.swing.JPanel sidemenu;
    private javax.swing.JPanel sidemenu_icon;
    private javax.swing.JPanel staff_register_panel;
    private javax.swing.JTextArea tfADAddress;
    private javax.swing.JTextField tfADEmail;
    private javax.swing.JTextField tfADName;
    private javax.swing.JTextField tfADPhoneNumber;
    private javax.swing.JTextArea tfClientAddress;
    private javax.swing.JTextField tfClientEmail;
    private javax.swing.JTextField tfClientName;
    private javax.swing.JTextField tfClientPhoneNumber;
    private javax.swing.JTextField tfCreateApptDate;
    private javax.swing.JTextField tfCreateRoomNo;
    private javax.swing.JTextField tfUAApptDate;
    private javax.swing.JTextField tfUAApptTime;
    private javax.swing.JTextField tfUACreatedBy;
    private javax.swing.JTextField tfUACusId;
    private javax.swing.JTextField tfUARoomNo;
    private javax.swing.JTextField tfUATechId;
    private javax.swing.JTextArea tfUCAddress;
    private javax.swing.JTextField tfUCEmail;
    private javax.swing.JTextField tfUCName;
    private javax.swing.JTextField tfUCPhoneNumber;
    private javax.swing.JTextField tfUFApptId;
    private javax.swing.JTextArea tfUFFeedContent;
    private javax.swing.JTextField tfUFFeedDate;
    private javax.swing.JTextField tfUFFeedUpdatedBy;
    private javax.swing.JTextField tfUPApptId;
    private javax.swing.JTextField tfUPPayAmount;
    private javax.swing.JTextField tfUPPayDate;
    private javax.swing.JTextField tfUPPayUpdatedBy;
    private javax.swing.JTextArea tfUUAddress;
    private javax.swing.JTextField tfUUEmail;
    private javax.swing.JTextField tfUUName;
    private javax.swing.JTextField tfUUPhoneNumber;
    private javax.swing.JTextArea tfUserAddress;
    private javax.swing.JTextField tfUserEmail;
    private javax.swing.JTextField tfUserName;
    private javax.swing.JTextField tfUserPhoneNumber;
    private javax.swing.JTextField tfUsername;
    private javax.swing.JPanel update_appt_content;
    private javax.swing.JPanel update_appt_tab;
    private javax.swing.JPanel update_client_content;
    private javax.swing.JPanel update_client_tab;
    private javax.swing.JPanel update_details_panel;
    private javax.swing.JTabbedPane update_details_tabs;
    private javax.swing.JPanel update_feed_content;
    private javax.swing.JPanel update_feed_tab;
    private javax.swing.JPanel update_pay_content;
    private javax.swing.JPanel update_pay_tab;
    private javax.swing.JPanel update_user_content;
    private javax.swing.JPanel update_user_tab;
    private javax.swing.JPanel userManagement;
    private javax.swing.JPanel userManagementLine;
    private javax.swing.JTable userTable;
    private javax.swing.JPanel user_list_panel;
    private javax.swing.JPanel view_payment_record;
    // End of variables declaration//GEN-END:variables
}
