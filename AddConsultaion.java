import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class AddConsultaion extends JFrame implements ActionListener {
    
    final JComboBox<String> cmb_doctor, cmb_patinet_name, cmb_datetime; 
    final JButton btn_search, btn_save, btn_cancel, btn_reset, btn_upload;
    final JTextField txt_date;
    final JTextArea txt_notes;
    final DefaultTableModel model;
    JLabel lbl_imagepath;

    private String image_path, image_destination="";   
    private String selected_doc_id="";
    
    WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();
        
    public AddConsultaion(){

        JPanel panelleft = new JPanel();
        panelleft.setPreferredSize(new Dimension(600, 300));
        panelleft.setLayout(null);
        panelleft.setBackground(Color.LIGHT_GRAY);

        JPanel panelRight = new JPanel();
        panelRight.setLayout(null);
        panelRight.setPreferredSize(new Dimension(750, 300));
        panelRight.setBackground(Color.GREEN);
       
        //left panel heading
        JLabel lbl_head = new JLabel("Search Doctor Aailability");
        lbl_head.setFont(new Font("Calibri", Font.BOLD, 18));
        lbl_head.setBounds(50, 50, 400, 30);

        //Doctor's drop down
        JLabel lbl_doctor = new JLabel("Doctor");
        lbl_doctor.setBounds(50, 80, 50, 30);
        lbl_doctor.setFont(new Font("Calibri", Font.PLAIN, 16));

        cmb_doctor = new JComboBox<String>();
        cmb_doctor.setBounds(50, 110, 200, 30);
        cmb_doctor.addActionListener(this);

        //Date
        JLabel lbl_date = new JLabel("Date (YYYY-MM-DD)");
        lbl_date.setBounds(270, 80, 150, 30);
        lbl_date.setFont(new Font("Calibri", Font.PLAIN, 16));
        
        txt_date = new JTextField("");
        txt_date.setBounds(270,110, 200, 30);

        //Go button 
        btn_search = new JButton("Go");
        btn_search.setBounds(490, 110, 60, 30);
        btn_search.addActionListener(this);
        
        // //availability table
        JTable tbl_doctor = new JTable();
        model = new DefaultTableModel();        
        model.setColumnIdentifiers(new Object[] { "Medical Licence No", "Available Date", "Time" });
        tbl_doctor.setBounds(50, 170, 500, 250);
        tbl_doctor.setModel(model);
        
        //Patient's drop down
        JLabel lbl_fname = new JLabel("Patient ID/Name *");
        lbl_fname.setBounds(200, 50, 150, 30);
        lbl_fname.setFont(new Font("Calibri", Font.PLAIN, 16));
       
        cmb_patinet_name = new JComboBox<String>();
        cmb_patinet_name.setBounds(350, 50, 200, 30);
       
        //Date-Time Label
        JLabel lbl_datetime = new JLabel("Date/Time *");
        lbl_datetime.setBounds(200, 100, 150, 30);
        lbl_datetime.setFont(new Font("Calibri", Font.PLAIN, 16));
       
        cmb_datetime = new JComboBox<String>();
        cmb_datetime.setBounds(350, 100, 200, 30);

        //Notes
        JLabel lbl_notes = new JLabel("Special Notes");
        lbl_notes.setBounds(200, 150, 200, 30);
        lbl_notes.setFont(new Font("Calibri", Font.PLAIN, 16));
       
        txt_notes = new JTextArea();
        txt_notes.setBounds(350, 150, 350, 100);
        txt_notes.setLineWrap(true);

        //upload image
        JLabel lbl_image = new JLabel("Upload an Image");
        lbl_image.setBounds(200, 290, 200, 30);
        lbl_image.setFont(new Font("Calibri", Font.PLAIN, 16));
       
        btn_upload = new JButton("Upload");
        btn_upload.setBounds(350, 290, 100, 30);
        btn_upload.addActionListener(this);

        lbl_imagepath = new JLabel("");
        lbl_imagepath.setBounds(350, 330, 500, 30);
        lbl_imagepath.setFont(new Font("Calibri", Font.BOLD, 12));
        

        //Save button ----------------------------------------------------
        btn_save = new JButton("Save");
        btn_save.setBounds(360, 380, 100, 30);
        btn_save.addActionListener(this);
        
        //Reset button ----------------------------------------------------
        btn_reset = new JButton("Reset");
        btn_reset.setBounds(480, 380, 100, 30);
        btn_reset.addActionListener(this);

        //Cancel button ----------------------------------------------------
        btn_cancel = new JButton("Exit");
        btn_cancel.setBounds(600, 380, 100, 30);
        btn_cancel.addActionListener(this);
     
        //Add components to the panels
        panelleft.add(lbl_head);
        panelleft.add(cmb_doctor);
        panelleft.add(lbl_doctor);
        panelleft.add(lbl_date);
        panelleft.add(txt_date);
        panelleft.add(btn_search);
        panelleft.add(tbl_doctor);
        
        panelRight.add(lbl_fname);
        panelRight.add(cmb_patinet_name);
        panelRight.add(lbl_datetime);
        panelRight.add(cmb_datetime);
        panelRight.add(lbl_notes);
        panelRight.add(txt_notes);
        panelRight.add(btn_save);
        panelRight.add(btn_cancel);
        panelRight.add(btn_reset);
        panelRight.add(lbl_image);
        panelRight.add(btn_upload);
        panelRight.add(lbl_imagepath);
                
        //Add panels to the frame
        this.setTitle("Add Consultation");
        this.setBounds(300,200,1200, 520);
        this.add(panelleft, BorderLayout.WEST);
        this.add(panelRight,BorderLayout.EAST);
        this.setResizable(false);      
        this.setVisible(true); 

        //load initial data
        Load_Data();        

    }


    //--------------------------------------------------------------------------------
    // Event Listners
    //--------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == cmb_doctor){
            String doc_name = cmb_doctor.getSelectedItem().toString();
            GetDoctorAvailability(doc_name);
        }

        //Search(Go) button on-click
        if(e.getSource() == btn_search){
            GetDoctorAvailability(cmb_doctor.getSelectedItem().toString());
        }

        //Upload button on-click
        if(e.getSource() == btn_upload){
            SelectUploadImage();            
        }

        //Reset button on-click
        if(e.getSource() == btn_reset){
            cmb_patinet_name.setSelectedIndex(-1);
            cmb_datetime.setSelectedIndex(-1);
            txt_notes.setText("");           
        }

        //Cancel button on-click event - close the window
        if(e.getSource() == btn_cancel){
            this.dispose();
        }

        //Save button on-click event - save the data
        if(e.getSource() == btn_save){            
            if (Validation() == true){
                SaveConsultation();
            }
        }
    }

    //-------------------------------------------------------------------------------------------------
    // Load combo box values
    //-------------------------------------------------------------------------------------------------
    private void Load_Data(){
              
        //Load Doctor combo box
        cmb_doctor.addItem("");
        for (int i = 0; i < wm.doctors.size(); i++) {
            String name = wm.doctors.get(i).getName() + " " + wm.doctors.get(i).getSurname();
            cmb_doctor.addItem(name);
        }
        
        //Load Patient combo box
        try {
            ResultSet dt_rows = wm.GetPatientData();
            while (dt_rows.next()) {
                cmb_patinet_name.addItem(dt_rows.getString(5) + " - " + dt_rows.getString(1) + " " + dt_rows.getString(2));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        //set default position to non-selected
        cmb_patinet_name.setSelectedIndex(-1);
    }

    //-------------------------------------------------------------------------------------------------
    // Load availability data table
    //-------------------------------------------------------------------------------------------------
    void GetDoctorAvailability(String doc_name){
        
        selected_doc_id = "";
        model.setRowCount(0);
        cmb_datetime.removeAllItems();
       
        for (int i = 0; i < wm.doctors.size(); i++) {
            if (doc_name.equals(wm.doctors.get(i).getName() + " " + wm.doctors.get(i).getSurname())) {
                selected_doc_id = wm.doctors.get(i).getMedicalLicenceNumber();       
            }            
        }
        
        try {
            ResultSet rs_availability = wm.CheckAvailability(selected_doc_id, txt_date.getText());
            Object rowData[] = new Object[4];
            
            cmb_datetime.addItem("");
            while (rs_availability.next() == true) {
                rowData[0] = rs_availability.getString(2);
                rowData[1] = rs_availability.getString(3);
                rowData[2] = rs_availability.getString(4);
                rowData[3] = rs_availability.getString(5);

                model.addRow(rowData);

                //load cmb_datetime with the same data
                cmb_datetime.addItem(rs_availability.getString(3) + "_" + rs_availability.getString(4));
                
            }
        } catch (Exception e_) {                        
            System.out.println(e_);
        }
    }
  

    //--------------------------------------------------------------------------------
    // Form Validation
    //--------------------------------------------------------------------------------
    Boolean Validation(){

        Boolean isError = true;

        if( (cmb_doctor.getSelectedIndex()==0) && (txt_date.getText().isEmpty()) ) {
            JOptionPane.showMessageDialog(null, "Please enter a Date.", "Add Consultation", JOptionPane.WARNING_MESSAGE);
            txt_date.requestFocus();
            isError = false;
        
        }else if(cmb_patinet_name.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please select the Patient ID", "Add Consultation", JOptionPane.WARNING_MESSAGE);
            cmb_patinet_name.requestFocus();
            isError = false;
            
        }else if( (cmb_doctor.getSelectedIndex()>0 ) && (cmb_datetime.getSelectedIndex() == 0)){
            JOptionPane.showMessageDialog(null, "Pease select a Date/Time.", "Add Consultation", JOptionPane.WARNING_MESSAGE);
            cmb_datetime.requestFocus();
            isError = false;
        }    
         
        return isError;
    }


    //--------------------------------------------------------------------------------
    // choose the image to upload
    //--------------------------------------------------------------------------------
    void SelectUploadImage(){
        
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);

        // Make sure that a file was chosen, else exit
        if (result != JFileChooser.APPROVE_OPTION) {
            return;            
        }

        // Get file path
        image_path = fc.getSelectedFile().getAbsolutePath();
        image_destination = "./images/" + fc.getSelectedFile().getName();
        lbl_imagepath.setText(image_path);
    }

    // --------------------------------------------------------------------------
    // upload file to a destination folder
    // --------------------------------------------------------------------------
    void UploadImage(){
        
        try {
             // Copy file from source to destination
             FileChannel source = new FileInputStream(image_path).getChannel();
             FileChannel dest = new FileOutputStream(image_destination).getChannel();
             dest.transferFrom(source, 0, source.size());
             
             Encrypt en = new Encrypt();
             en.EncryptDecryptImage(image_destination);

             source.close();
             dest.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------------
    // Add consutation to the DB
    //--------------------------------------------------------------------------------
    void SaveConsultation(){
        
        String date ="";
        String time ="";

        //if there's no doctor seleted, the auto-assigned a doctor
        if ( selected_doc_id.isEmpty() ){
            
            date = txt_date.getText();

            ArrayList<String> availability_list = new ArrayList<String>();
            ResultSet availability = wm.CheckAvailability("", date );
            
            try {
                 //convert to RS an ArrayList
                 while(availability.next()) 
                    availability_list.add(availability.getString(2) + "_" + availability.getString(4));

            } catch (Exception e) {
                System.out.println(e);
            }

             //get the randomly selected doctor id
            selected_doc_id = wm.AutoAssignDoctor(availability_list).split("_")[0];
            time = wm.AutoAssignDoctor(availability_list).split("_")[1];           
            
        }else{
            date = cmb_datetime.getSelectedItem().toString().split("_")[0];
            time = cmb_datetime.getSelectedItem().toString().split("_")[1];
        }
       
        String patient_id = cmb_patinet_name.getSelectedItem().toString();
        patient_id = patient_id.split("-")[0];

       
        //Save to the DB                                
        try {
            Consultation consult = new Consultation();
           
            consult.setDate(date);
            consult.setStartTime(time);
            consult.setEndTime("00:00");
            consult.setNotes(new Encrypt().encryptData(txt_notes.getText()));
            consult.setImageData(image_destination);
            consult.setStatus("A");
            consult.setCost(wm.CalculateCost(patient_id));
        
            wm.AddConsultation(selected_doc_id , patient_id ,consult);
            
            if(!image_destination.isEmpty()){
                //upload the image to the destination
                UploadImage();
            }
            
            JOptionPane.showMessageDialog(null, "Booking is added sucessfully.",
                                        "Add Consultation", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose(); 
                                                
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
