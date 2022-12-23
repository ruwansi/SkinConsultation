import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class GUI extends JFrame implements ActionListener{
    
    final JButton btn_addConsultation,btn_addPatient, btn_cancelConsultation, btn_exit;  
    final JPanel panelLeft, panelRight; //, panelCenter;
    JTable tbl_doctor, tbl_bookings, tbl_patients;

    WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();
    
    //Constructor
    public GUI() {
        
        //load existing data
        ViewDoctorDetails();
        ViewBookings();
        ViewPatients();

        new JFrame();        

        //Panels
        panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(780, 980));
        panelLeft.setBackground(Color.LIGHT_GRAY);

        panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(1200, 980));
        panelRight.setBackground(Color.LIGHT_GRAY);
        
        JPanel panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(1920, 150));
        panelTop.setLayout(null);
        panelTop.setBackground(Color.GREEN);

        JPanel panelBottom = new JPanel();
        panelBottom.setPreferredSize(new Dimension(1920, 150));
        panelBottom.setLayout(null);
        panelBottom.setBackground(Color.GRAY);

        // panelCenter = new JPanel();
        // panelCenter.setPreferredSize(new Dimension(600, 980));
        // panelCenter.setBackground(Color.WHITE);

        //components
        JLabel lbl_mainheading = new JLabel(" - Westminster Skin Consultation Manager - ");
        lbl_mainheading.setBounds(565, 30, 1000, 80);
        lbl_mainheading.setFont(new Font("Verdana", Font.BOLD, 32));

        btn_addPatient = new JButton("Add Patient");
        btn_addPatient.setBounds(460, 50, 200, 40);
        btn_addPatient.addActionListener(this);

        btn_addConsultation = new JButton("Add Consultation");
        btn_addConsultation.setBounds(690, 50, 200, 40);
        btn_addConsultation.addActionListener(this);

        btn_cancelConsultation = new JButton("Cancel a Consultation");
        btn_cancelConsultation.setBounds(910, 50, 200, 40);
        btn_cancelConsultation.addActionListener(this);

        btn_exit = new JButton("Exit");
        btn_exit.setBounds(1130, 50, 200, 40);
        btn_exit.addActionListener(this);

        JLabel lbl_doctor = new JLabel("Doctor Details");
        lbl_doctor.setBounds(50,30, 400, 30);
        lbl_doctor.setFont(new Font("Calibri", Font.BOLD, 20));

        //add components to the panels
        panelTop.add(lbl_mainheading);        
        panelBottom.add(btn_addPatient);
        panelBottom.add(btn_addConsultation);
        panelBottom.add(btn_cancelConsultation);
        panelBottom.add(btn_exit);        
        //panelLeft.add(lbl_doctor);
        panelLeft.add(new JScrollPane(tbl_doctor));
        panelRight.add(new JScrollPane(tbl_bookings));
        panelRight.add(new JScrollPane(tbl_patients));
        
        //add panels to the frame 
        this.setTitle("Main Dashboard");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panelLeft, BorderLayout.WEST);
        this.add(panelRight, BorderLayout.EAST);
        this.add(panelTop, BorderLayout.NORTH);
        this.add(panelBottom, BorderLayout.SOUTH);
        //this.add(panelCenter, BorderLayout.CENTER);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    
    }

    //--------------------------------------------------------------------------------------
    // Load Doctor Details Table
    //--------------------------------------------------------------------------------------
    void ViewDoctorDetails() {

        tbl_doctor = new JTable();        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] { "Name", "DOB", "Mobile", "Medical Lic No", "Specialization" } );
        tbl_doctor.setModel(model);
        tbl_doctor.setBounds(50, 10, 450, 400);
        tbl_doctor.setAutoCreateRowSorter(true);

        Object rowData[] = new Object[5];

        for (int i = 0; i < wm.doctors.size(); i++) {
            rowData[0] = wm.doctors.get(i).getName() + " " + wm.doctors.get(i).getSurname();
            rowData[1] = wm.doctors.get(i).getDOB();
            rowData[2] = wm.doctors.get(i).getMobile();
            rowData[3] = wm.doctors.get(i).getMedicalLicenceNumber();
            rowData[4] = wm.doctors.get(i).getspecialization();
           
            model.addRow(rowData);
        }   
             
    }

    //--------------------------------------------------------------------------------------
    // Load Consultation Booking Details Table
    //--------------------------------------------------------------------------------------
    void ViewBookings(){

        Encrypt en = new Encrypt();
        tbl_bookings = new JTable();        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] { "Booking No", "Patient ID", "Doctor ID", "Date", "Time", "Cost", "Notes", "Image" } );
        tbl_bookings.setModel(model);
        tbl_bookings.setBounds(50, 10, 750, 400);
        tbl_bookings.setAutoCreateRowSorter(true);

        try {
            ResultSet rs_bookings = wm.ViewConsultation("A");            
            Object rowData[] = new Object[8];

            while (rs_bookings.next() == true) {
                rowData[0] = rs_bookings.getString(1);
                rowData[1] = rs_bookings.getString(2);
                rowData[2] = rs_bookings.getString(3);
                rowData[3] = rs_bookings.getString(4);
                rowData[4] = rs_bookings.getString(5);
                rowData[5] = rs_bookings.getString(7);
                rowData[6] = en.decryptData(rs_bookings.getString(8));
                rowData[7] = rs_bookings.getString(9);

                model.addRow(rowData);
            }
        } catch (Exception e_) {                        
            System.out.println(e_);
        }
    }    

    //--------------------------------------------------------------------------------------
    // Load Patients Detail Table
    //--------------------------------------------------------------------------------------
    void ViewPatients(){

        tbl_patients = new JTable();        
        DefaultTableModel model = new DefaultTableModel();
        
        model.setColumnIdentifiers(new Object[] { "Paitent ID", "Name", "DOB", "Mobile" } );
        tbl_patients.setModel(model);
        tbl_patients.setBounds(850, 10, 500, 400);
        tbl_patients.setAutoCreateRowSorter(true);

        try {
            ResultSet rs_patients = wm.GetPatientData();          
            Object rowData[] = new Object[4];

            while (rs_patients.next() == true) {
                rowData[0] = rs_patients.getString(5);
                rowData[1] = rs_patients.getString(1) + " " + rs_patients.getString(2);
                rowData[2] = rs_patients.getString(3);
                rowData[3] = rs_patients.getString(4);               
               
                model.addRow(rowData);
            }
        } catch (Exception e_) {                        
            System.out.println(e_);
        }
    }    

    
    //--------------------------------------------------------------------------------------
    // Performed Action Listners
    //--------------------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e) {
        
        //btn_addPatient on-click
        if(e.getSource()==btn_addPatient) new AddPatient();
        
        //btn_addconsultation on-click
        if(e.getSource()==btn_addConsultation) new AddConsultaion();
        
        //btn_cancelConsultation on-click
        if(e.getSource()==btn_cancelConsultation) new CancelConsultation();

        //btn_exit on-click
        if(e.getSource()==btn_exit) {
            this.dispose();
            System.exit(0);
        }
    }   
    
}
