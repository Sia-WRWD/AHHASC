/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.activity_logger;
import common.fetch_data;
import common.login;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import common.appointment_management;
import common.user_management;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chinojen7
 */
public class technician_mm extends javax.swing.JFrame {

    //To Instantiate the Classes
    fetch_data fd = new fetch_data();
    user_management um = new user_management();
    appointment_management am = new appointment_management();
    activity_logger al = new activity_logger();

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
    String[] user_column = {"User ID", "Name", "Phone Number", "Address", "Email", "Gender", "Role"};
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
    boolean updateResponse = false;

    /**
     * Creates new form technician_mm
     */
    public technician_mm() throws IOException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.hideMainPanels();
        this.home_panel.setVisible(true);
        this.setTableSelectionMode();
        this.setAllData();
        this.resetChangeAccDetailsForm();
        this.disableUpdateTabs();
        this.disableUnUpdatableFields();
    }

    private void hideMainPanels() {
        this.home_panel.setVisible(false);
        this.manage_user_panel.setVisible(false);
        this.manage_appt_panel.setVisible(false);
        this.manage_payment_panel.setVisible(false);
        this.manage_feedback_panel.setVisible(false);
        this.personal_details_panel.setVisible(false);
        this.update_details_panel.setVisible(false);
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
    }

    private void disableUnUpdatableFields() {
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
        this.fetchSessionData();
        this.setOngoingApptData();
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
        this.ongoingApptData = this.fd.fetchOngoingApptDataBasedId(userCacheData[0]);
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
        this.userData = this.fd.fetchUserDataT();
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

        Overall = new javax.swing.JPanel();
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
        settingsLine = new javax.swing.JPanel();
        settings = new javax.swing.JPanel();
        btnSettings = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        update_details_panel = new javax.swing.JPanel();
        update_details_tabs = new javax.swing.JTabbedPane();
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
        manage_payment_panel = new javax.swing.JTabbedPane();
        view_payment_record = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        btnGetPayDetails = new javax.swing.JButton();
        manage_appt_panel = new javax.swing.JTabbedPane();
        appt_list_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        apptTable = new javax.swing.JTable();
        manage_user_panel = new javax.swing.JTabbedPane();
        user_list_panel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        client_list_panel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();
        home_panel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        ongoingApptTable = new javax.swing.JTable();
        lblOngoingApptTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Overall.setBackground(new java.awt.Color(0, 0, 0));
        Overall.setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(102, 102, 255));
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

        Overall.add(header, java.awt.BorderLayout.PAGE_START);

        sidemenu.setBackground(new java.awt.Color(0, 0, 0));
        sidemenu.setForeground(new java.awt.Color(0, 0, 51));
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

        sidemenu_icon.add(logoutLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 50, -1));

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

        sidemenu_icon.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 275, 50, 45));

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

        sidemenu.add(sidemenu_icon, java.awt.BorderLayout.LINE_START);

        Overall.add(sidemenu, java.awt.BorderLayout.LINE_START);

        dashboard.setBackground(new java.awt.Color(0, 0, 0));
        dashboard.setForeground(new java.awt.Color(51, 51, 51));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        update_details_panel.setLayout(new java.awt.BorderLayout());

        update_details_tabs.setBackground(new java.awt.Color(255, 255, 255));
        update_details_tabs.setForeground(new java.awt.Color(0, 0, 0));
        update_details_tabs.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N

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

        feedbackTable.setBackground(new java.awt.Color(255, 255, 255));
        feedbackTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        feedbackTable.setForeground(new java.awt.Color(0, 0, 0));
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
        manage_feedback_panel.add(btnGetFBDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 140, 40));

        dashboard.add(manage_feedback_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        view_payment_record.setBackground(new java.awt.Color(0, 0, 0));
        view_payment_record.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setForeground(new java.awt.Color(51, 51, 51));

        paymentTable.setBackground(new java.awt.Color(255, 255, 255));
        paymentTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        paymentTable.setForeground(new java.awt.Color(0, 0, 0));
        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment ID", "Appointment ID", "Payment Date", "Payment Amount", "Payment Status", "Updated By"
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
        view_payment_record.add(btnGetPayDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 140, 40));

        manage_payment_panel.addTab("Payment Records", view_payment_record);

        dashboard.add(manage_payment_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        appt_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        appt_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setForeground(new java.awt.Color(51, 51, 51));

        apptTable.setBackground(new java.awt.Color(255, 255, 255));
        apptTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        apptTable.setForeground(new java.awt.Color(0, 0, 0));
        apptTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(apptTable);

        appt_list_panel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_appt_panel.addTab("Appt List\n", appt_list_panel);

        dashboard.add(manage_appt_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        manage_user_panel.setBackground(new java.awt.Color(255, 255, 255));
        manage_user_panel.setForeground(new java.awt.Color(0, 0, 0));
        manage_user_panel.setPreferredSize(new java.awt.Dimension(750, 450));

        user_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        user_list_panel.setForeground(new java.awt.Color(0, 0, 0));
        user_list_panel.setPreferredSize(new java.awt.Dimension(775, 423));
        user_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane4.setForeground(new java.awt.Color(51, 51, 51));

        userTable.setBackground(new java.awt.Color(255, 255, 255));
        userTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        userTable.setForeground(new java.awt.Color(0, 0, 0));
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "Name", "Phone Number", "Address", "Email", "Gender", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(userTable);

        user_list_panel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_user_panel.addTab("User List", user_list_panel);

        client_list_panel.setBackground(new java.awt.Color(0, 0, 0));
        client_list_panel.setForeground(new java.awt.Color(0, 0, 0));
        client_list_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane5.setForeground(new java.awt.Color(51, 51, 51));

        clientTable.setBackground(new java.awt.Color(255, 255, 255));
        clientTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        clientTable.setForeground(new java.awt.Color(0, 0, 0));
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client ID", "Name", "Phone Number", "Room No", "Email", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(clientTable);

        client_list_panel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_user_panel.addTab("Client List", client_list_panel);

        dashboard.add(manage_user_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        home_panel.setBackground(new java.awt.Color(0, 0, 0));
        home_panel.setForeground(new java.awt.Color(0, 0, 0));
        home_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane6.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane6.setForeground(new java.awt.Color(51, 51, 51));

        ongoingApptTable.setBackground(new java.awt.Color(255, 255, 255));
        ongoingApptTable.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ongoingApptTable.setForeground(new java.awt.Color(0, 0, 0));
        ongoingApptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Appt ID", "Client ID", "Feedback ID", "Technician ID", "Room No", "Appt Date", "Job Status", "Pay Status", "Created By"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(ongoingApptTable);

        home_panel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 730, 370));

        lblOngoingApptTitle.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblOngoingApptTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblOngoingApptTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOngoingApptTitle.setText("Ongoing Appointments");
        home_panel.add(lblOngoingApptTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 730, 40));

        dashboard.add(home_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        Overall.add(dashboard, java.awt.BorderLayout.CENTER);

        getContentPane().add(Overall, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    private void changeFontColor(JLabel hover, Color rand) {
        hover.setForeground(rand);
    }

    private void btnMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMinMouseClicked

    private void btnMinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseEntered
        changeColor(btnMin, new Color(239, 183, 183, 255));
    }//GEN-LAST:event_btnMinMouseEntered

    private void btnMinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinMouseExited
        changeColor(btnMin, new Color(0, 0, 0));
    }//GEN-LAST:event_btnMinMouseExited

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

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        changeColor(btnClose, new Color(255, 105, 180));
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        changeColor(btnClose, new Color(0, 0, 0));
    }//GEN-LAST:event_btnCloseMouseExited

    //Logout via Exit Button at Top Right
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

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        this.changeFontColor(lblHome, new Color(255, 255, 255));
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        this.changeFontColor(lblHome, new Color(239, 183, 183, 255));
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnUserManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseClicked
        this.hideMainPanels();
        this.manage_user_panel.setVisible(true);
    }//GEN-LAST:event_btnUserManagementMouseClicked

    private void btnUserManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseEntered
        changeColor(userManagementLine, new Color(255, 165, 0, 255));
        btnUserManagement.setToolTipText("User Management");
    }//GEN-LAST:event_btnUserManagementMouseEntered

    private void btnUserManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserManagementMouseExited
        changeColor(userManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnUserManagementMouseExited

    private void btnApptManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseClicked
        this.hideMainPanels();
        this.manage_appt_panel.setVisible(true);
    }//GEN-LAST:event_btnApptManagementMouseClicked

    private void btnApptManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseEntered
        changeColor(apptManagementLine, new Color(255, 105, 180));
        btnApptManagement.setToolTipText("Appointment Management");
    }//GEN-LAST:event_btnApptManagementMouseEntered

    private void btnApptManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnApptManagementMouseExited
        changeColor(apptManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnApptManagementMouseExited

    private void btnPaymentManagementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseClicked
        this.hideMainPanels();
        this.manage_payment_panel.setVisible(true);
    }//GEN-LAST:event_btnPaymentManagementMouseClicked

    private void btnPaymentManagementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseEntered
        changeColor(paymentManagementLine, new Color(255, 255, 0, 255));
        btnPaymentManagement.setToolTipText("Payment Management");
    }//GEN-LAST:event_btnPaymentManagementMouseEntered

    private void btnPaymentManagementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentManagementMouseExited
        changeColor(paymentManagementLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnPaymentManagementMouseExited

    //Logout via Logout Button
    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        this.dispose();
        al.logUserActivity(userCacheData[0], "Logged Out");
        login lg = new login();
        lg.logout();
        login_page lgPage = new login_page();
        lgPage.setVisible(true);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        changeColor(logoutLine, new Color(247, 78, 105));
        btnLogout.setToolTipText("Logout");
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        changeColor(logoutLine, new Color(255, 255, 255));
    }//GEN-LAST:event_btnLogoutMouseExited

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

    //Update Feedback Info
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
                Logger.getLogger(technician_mm.class.getName()).log(Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Feedback: " + selectedFeedbackData[1] + "'s Data");
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

    //Update Payment Info
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
                Logger.getLogger(technician_mm.class.getName()).log(Level.SEVERE, null, ex);
            }

            //React to Returned Response
            if (this.updateResponse == true) {
                al.logUserActivity(userCacheData[0], "Updated Payment: " + selectedPayData[1] + "'s Data");
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

    //Fetch Specific Pay Details
    private void btnGetPayDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetPayDetailsMouseClicked
        try {
            this.fetchSelectedPayData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(0);

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

    //Fetch Specific Feedback Details
    private void btnGetFBDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGetFBDetailsMouseClicked
        try {
            this.fetchSelectedFeedbackData();
            this.hideMainPanels();
            this.update_details_panel.setVisible(true);
            this.update_details_tabs.setSelectedIndex(1);

            tfUFApptId.setText(selectedFeedbackData[1]);
            tfUFFeedDate.setText(selectedFeedbackData[3]);
            tfUFFeedContent.setText(selectedFeedbackData[2]);
            tfUFFeedUpdatedBy.setText(selectedFeedbackData[4]);
            
            apptDate = String.valueOf(this.fd.fetchApptBasedId(selectedFeedbackData[1]).get(2));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(manager_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGetFBDetailsMouseClicked

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
                    JOptionPane.showMessageDialog(null, "Please check your inputs again!", "Validation Error", JOptionPane.ERROR_MESSAGE);
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

    //Reset Forms
    private void resetAllForms() {
        this.resetChangeAccDetailsForm();
        this.resetResetPasswordForm();
        this.resetUpdatePayForm();
        this.resetUpdateFeedbackForm();
    }

    private void resetResetPasswordForm() {
        pfRPpwd.setText("");
        pfRPreppwd.setText("");
    }

    private void resetChangeAccDetailsForm() {
        tfADName.setText(this.currentUserData[2]);
        tfADPhoneNumber.setText(this.currentUserData[3]);
        tfADAddress.setText(this.currentUserData[4]);
        cbADGender.setSelectedItem(this.currentUserData[6]);
        tfADEmail.setText(this.currentUserData[5]);
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
            java.util.logging.Logger.getLogger(technician_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(technician_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(technician_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(technician_mm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Overall;
    private javax.swing.JPanel account_details_content;
    private javax.swing.JPanel account_details_panel;
    private javax.swing.JPanel apptManagement;
    private javax.swing.JPanel apptManagementLine;
    private javax.swing.JTable apptTable;
    private javax.swing.JPanel appt_list_panel;
    private javax.swing.JLabel btnApptManagement;
    private javax.swing.JPanel btnClose;
    private javax.swing.JLabel btnFeedbackManagement;
    private javax.swing.JButton btnGetFBDetails;
    private javax.swing.JButton btnGetPayDetails;
    private javax.swing.JPanel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JPanel btnMin;
    private javax.swing.JLabel btnPaymentManagement;
    private javax.swing.JButton btnResetPassword;
    private javax.swing.JLabel btnSettings;
    private javax.swing.JButton btnUpdateAccDetails;
    private javax.swing.JButton btnUpdateFeedback;
    private javax.swing.JButton btnUpdatePayment;
    private javax.swing.JLabel btnUserManagement;
    private javax.swing.JComboBox<String> cbADGender;
    private javax.swing.JComboBox<String> cbUPPayStatus;
    private javax.swing.JTable clientTable;
    private javax.swing.JPanel client_list_panel;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel feedbackManagement;
    private javax.swing.JPanel feedbackManagementLine;
    private javax.swing.JTable feedbackTable;
    private javax.swing.JPanel header;
    private javax.swing.JPanel header_icon;
    private javax.swing.JPanel home_panel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblADAddress;
    private javax.swing.JLabel lblADEmail;
    private javax.swing.JLabel lblADGender;
    private javax.swing.JLabel lblADName;
    private javax.swing.JLabel lblADPhoneNumber;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblMin;
    private javax.swing.JLabel lblOngoingApptTitle;
    private javax.swing.JLabel lblRPpwd;
    private javax.swing.JLabel lblRPreppwd;
    private javax.swing.JLabel lblUPApptId1;
    private javax.swing.JLabel lblUPPayAmount;
    private javax.swing.JLabel lblUPPayDate;
    private javax.swing.JLabel lblUPPayStatus;
    private javax.swing.JLabel lblUPPayUpdatedBy;
    private javax.swing.JPanel logout;
    private javax.swing.JPanel logoutLine;
    private javax.swing.JTabbedPane manage_appt_panel;
    private javax.swing.JPanel manage_feedback_panel;
    private javax.swing.JTabbedPane manage_payment_panel;
    private javax.swing.JTabbedPane manage_user_panel;
    private javax.swing.JTable ongoingApptTable;
    private javax.swing.JPanel paymentManagement;
    private javax.swing.JPanel paymentManagementLine;
    private javax.swing.JTable paymentTable;
    private javax.swing.JPanel personal_details_panel;
    private javax.swing.JTabbedPane personal_details_tabs;
    private javax.swing.JPasswordField pfRPpwd;
    private javax.swing.JPasswordField pfRPreppwd;
    private javax.swing.JPanel reset_pwd_content;
    private javax.swing.JPanel reset_pwd_panel;
    private javax.swing.JPanel settings;
    private javax.swing.JPanel settingsLine;
    private javax.swing.JPanel sidemenu;
    private javax.swing.JPanel sidemenu_icon;
    private javax.swing.JTextArea tfADAddress;
    private javax.swing.JTextField tfADEmail;
    private javax.swing.JTextField tfADName;
    private javax.swing.JTextField tfADPhoneNumber;
    private javax.swing.JTextField tfUFApptId;
    private javax.swing.JTextArea tfUFFeedContent;
    private javax.swing.JTextField tfUFFeedDate;
    private javax.swing.JTextField tfUFFeedUpdatedBy;
    private javax.swing.JTextField tfUPApptId;
    private javax.swing.JTextField tfUPPayAmount;
    private javax.swing.JTextField tfUPPayDate;
    private javax.swing.JTextField tfUPPayUpdatedBy;
    private javax.swing.JPanel update_details_panel;
    private javax.swing.JTabbedPane update_details_tabs;
    private javax.swing.JPanel update_feed_content;
    private javax.swing.JPanel update_feed_tab;
    private javax.swing.JPanel update_pay_content;
    private javax.swing.JPanel update_pay_tab;
    private javax.swing.JPanel userManagement;
    private javax.swing.JPanel userManagementLine;
    private javax.swing.JTable userTable;
    private javax.swing.JPanel user_list_panel;
    private javax.swing.JPanel view_payment_record;
    // End of variables declaration//GEN-END:variables
}
