/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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

/**
 *
 * @author chinojen7
 */
public class technician_mm extends javax.swing.JFrame {

    fetch_data fd = new fetch_data();
    ArrayList<String[]> userData = new ArrayList<String[]>();
    ArrayList<String[]> clientData = new ArrayList<String[]>();
    ArrayList<String[]> apptData = new ArrayList<String[]>();
    ArrayList<String[]> paymentData = new ArrayList<String[]>();
    ArrayList<String[]> feedbackData = new ArrayList<String[]>();

    DefaultTableModel user_model;
    DefaultTableModel client_model;
    DefaultTableModel appt_model;
    DefaultTableModel payment_model;
    DefaultTableModel feedback_model;

    String[] user_column = {"User ID", "Username", "Name", "Phone Number", "Address", "Email", "Gender", "Role"};
    String[] client_column = {"Client ID", "Name", "Phone Number", "Room No", "Email", "Gender"};
    String[] appt_column = {"Appt ID", "Client ID", "Appt Date", "Room No", "Feedback ID", "Technician ID", "Payment ID", "Job Status", "Created By"};
    String[] payment_column = {"Payment ID", "Appt ID", "Payment Date", "Payment Amount", "Payment Status"};
    String[] feedback_column = {"Feedback ID", "Client ID", "Feedback Content", "Feedback Date"};

    /**
     * Creates new form technician_mm
     */
    public technician_mm() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.hideMainPanels();
        this.home_panel.setVisible(true);
    }

    public void hideMainPanels() {
        this.home_panel.setVisible(false);
        this.manage_user_panel.setVisible(false);
        this.manage_appt_panel.setVisible(false);
        this.manage_payment_panel.setVisible(false);
        this.manage_feedback_panel.setVisible(false);
        this.personal_details_panel.setVisible(false);
    }

    public void setAllData() throws IOException {
        this.setUserData();
        this.setClientData();
        this.setApptData();
        this.setPaymentData();
        this.setFeedbackData();
    }

    //Fetch User Data
    public void setUserData() throws IOException {
        this.user_model = new DefaultTableModel(new Object[][]{}, this.user_column);

        this.userTable.setModel(this.user_model);
        this.userData = this.fd.fetchUserData();
        int list_size = this.userData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.userData.get(row);
            this.user_model.addRow(row_data);
        }
    }

    //Fetch Client Data
    public void setClientData() throws IOException {
        this.client_model = new DefaultTableModel(new Object[][]{}, this.client_column);

        this.clientTable.setModel(this.client_model);
        this.clientData = this.fd.fetchClientData();
        int list_size = this.clientData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.clientData.get(row);
            this.client_model.addRow(row_data);
        }
    }

    //Fetch Appointment Data
    public void setApptData() throws IOException {
        this.appt_model = new DefaultTableModel(new Object[][]{}, this.appt_column);

        this.apptTable.setModel(this.appt_model);
        this.apptData = this.fd.fetchApptData();
        int list_size = this.apptData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.apptData.get(row);
            this.appt_model.addRow(row_data);
        }
    }

    //Fetch Payment Data
    public void setPaymentData() throws IOException {
        this.payment_model = new DefaultTableModel(new Object[][]{}, this.payment_column);

        this.paymentTable.setModel(this.payment_model);
        this.paymentData = this.fd.fetchTransactionData();
        int list_size = this.paymentData.size();

        for (int row = 0; row < list_size; row++) {
            String[] row_data = this.paymentData.get(row);
            this.payment_model.addRow(row_data);
        }
    }

    //Fetch Feedback Data
    public void setFeedbackData() throws IOException {
        this.feedback_model = new DefaultTableModel(new Object[][]{}, this.feedback_column);

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
        personal_details_panel = new javax.swing.JPanel();
        manage_feedback_panel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        feedbackTable = new javax.swing.JTable();
        btnFeedbackUpdate = new javax.swing.JButton();
        manage_payment_panel = new javax.swing.JTabbedPane();
        view_payment_record = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        btnPaymentUpdate = new javax.swing.JButton();
        manage_appt_panel = new javax.swing.JTabbedPane();
        appt_list_panel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        apptTable = new javax.swing.JTable();
        create_appt_panel = new javax.swing.JPanel();
        create_appt_content = new javax.swing.JPanel();
        cbCreateTechnician = new javax.swing.JComboBox<>();
        tfCreateApptDate = new javax.swing.JTextField();
        tfCreateRoomNo = new javax.swing.JTextField();
        cbCreateClient = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        lblCreateApptDate = new javax.swing.JLabel();
        lblCreateClient = new javax.swing.JLabel();
        lblCreateTechnician = new javax.swing.JLabel();
        lblCreateRoomNo = new javax.swing.JLabel();
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

        personal_details_panel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout personal_details_panelLayout = new javax.swing.GroupLayout(personal_details_panel);
        personal_details_panel.setLayout(personal_details_panelLayout);
        personal_details_panelLayout.setHorizontalGroup(
            personal_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        personal_details_panelLayout.setVerticalGroup(
            personal_details_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

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
        feedbackTable.setRowHeight(20);
        jScrollPane7.setViewportView(feedbackTable);

        manage_feedback_panel.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 360));

        btnFeedbackUpdate.setBackground(new java.awt.Color(0, 0, 0));
        btnFeedbackUpdate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnFeedbackUpdate.setForeground(new java.awt.Color(255, 51, 204));
        btnFeedbackUpdate.setText("Update");
        btnFeedbackUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFeedbackUpdateMouseClicked(evt);
            }
        });
        manage_feedback_panel.add(btnFeedbackUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 140, 40));

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
        paymentTable.setRowHeight(20);
        jScrollPane2.setViewportView(paymentTable);

        view_payment_record.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 710, 340));

        btnPaymentUpdate.setBackground(new java.awt.Color(51, 51, 51));
        btnPaymentUpdate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnPaymentUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnPaymentUpdate.setText("Update");
        btnPaymentUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentUpdateMouseClicked(evt);
            }
        });
        view_payment_record.add(btnPaymentUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 140, 40));

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
        apptTable.setRowHeight(20);
        jScrollPane3.setViewportView(apptTable);

        appt_list_panel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_appt_panel.addTab("Appt List\n", appt_list_panel);

        create_appt_panel.setBackground(new java.awt.Color(0, 0, 0));
        create_appt_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        create_appt_content.setBackground(new java.awt.Color(51, 51, 51));
        create_appt_content.setForeground(new java.awt.Color(51, 51, 51));
        create_appt_content.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbCreateTechnician.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateTechnician.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateTechnician.setForeground(new java.awt.Color(255, 255, 255));
        cbCreateTechnician.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(1) Muhammad Irfan Zafri", " " }));
        create_appt_content.add(cbCreateTechnician, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 210, 30));

        tfCreateApptDate.setBackground(new java.awt.Color(0, 0, 0));
        tfCreateApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCreateApptDate.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(tfCreateApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 140, 30));

        tfCreateRoomNo.setBackground(new java.awt.Color(0, 0, 0));
        tfCreateRoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCreateRoomNo.setForeground(new java.awt.Color(255, 255, 255));
        create_appt_content.add(tfCreateRoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 140, 30));

        cbCreateClient.setBackground(new java.awt.Color(0, 0, 0));
        cbCreateClient.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbCreateClient.setForeground(new java.awt.Color(255, 255, 255));
        cbCreateClient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(1) JCN64", " " }));
        create_appt_content.add(cbCreateClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 140, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("CREATE APPOINTMENT");
        create_appt_content.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 310, 50));

        lblCreateApptDate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateApptDate.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateApptDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateApptDate.setText("Appointment Date:");
        create_appt_content.add(lblCreateApptDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 150, 30));

        lblCreateClient.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateClient.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateClient.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateClient.setText("Client:");
        create_appt_content.add(lblCreateClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 100, 30));

        lblCreateTechnician.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateTechnician.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateTechnician.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateTechnician.setText("Technician:");
        create_appt_content.add(lblCreateTechnician, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 100, 30));

        lblCreateRoomNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCreateRoomNo.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateRoomNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCreateRoomNo.setText("Room No:");
        create_appt_content.add(lblCreateRoomNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 100, 30));

        create_appt_panel.add(create_appt_content, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 400));

        manage_appt_panel.addTab("Create\n", create_appt_panel);

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
                "User ID", "Username", "Name", "Phone Number", "Address", "Email", "Gender", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.setRowHeight(20);
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
        clientTable.setRowHeight(20);
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
        ongoingApptTable.setRowHeight(20);
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

    public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);
    }

    public void changeFontColor(JLabel hover, Color rand) {
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

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        this.hideMainPanels();
        this.home_panel.setVisible(true);
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

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        this.dispose();
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

    private void btnFeedbackUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFeedbackUpdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFeedbackUpdateMouseClicked

    private void btnPaymentUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentUpdateMouseClicked

    }//GEN-LAST:event_btnPaymentUpdateMouseClicked

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
                new technician_mm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Overall;
    private javax.swing.JPanel apptManagement;
    private javax.swing.JPanel apptManagementLine;
    private javax.swing.JTable apptTable;
    private javax.swing.JPanel appt_list_panel;
    private javax.swing.JLabel btnApptManagement;
    private javax.swing.JPanel btnClose;
    private javax.swing.JLabel btnFeedbackManagement;
    private javax.swing.JButton btnFeedbackUpdate;
    private javax.swing.JPanel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JPanel btnMin;
    private javax.swing.JLabel btnPaymentManagement;
    private javax.swing.JButton btnPaymentUpdate;
    private javax.swing.JLabel btnSettings;
    private javax.swing.JLabel btnUserManagement;
    private javax.swing.JComboBox<String> cbCreateClient;
    private javax.swing.JComboBox<String> cbCreateTechnician;
    private javax.swing.JTable clientTable;
    private javax.swing.JPanel client_list_panel;
    private javax.swing.JPanel create_appt_content;
    private javax.swing.JPanel create_appt_panel;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel feedbackManagement;
    private javax.swing.JPanel feedbackManagementLine;
    private javax.swing.JTable feedbackTable;
    private javax.swing.JPanel header;
    private javax.swing.JPanel header_icon;
    private javax.swing.JPanel home_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCreateApptDate;
    private javax.swing.JLabel lblCreateClient;
    private javax.swing.JLabel lblCreateRoomNo;
    private javax.swing.JLabel lblCreateTechnician;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblMin;
    private javax.swing.JLabel lblOngoingApptTitle;
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
    private javax.swing.JPanel settings;
    private javax.swing.JPanel settingsLine;
    private javax.swing.JPanel sidemenu;
    private javax.swing.JPanel sidemenu_icon;
    private javax.swing.JTextField tfCreateApptDate;
    private javax.swing.JTextField tfCreateRoomNo;
    private javax.swing.JPanel userManagement;
    private javax.swing.JPanel userManagementLine;
    private javax.swing.JTable userTable;
    private javax.swing.JPanel user_list_panel;
    private javax.swing.JPanel view_payment_record;
    // End of variables declaration//GEN-END:variables
}
