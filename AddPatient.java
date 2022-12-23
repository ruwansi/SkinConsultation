import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.Font;


public class AddPatient extends JFrame implements ActionListener {

    final JTextField txt_fname, txt_suraname, txt_dob, txt_mobile;
    final JButton btn_cancel, btn_save;

    Patient patient = new Patient();

    public AddPatient() {
    
        //Main Frame --------------------------------------------------
        new JFrame();
        
        this.setTitle("Add Patient");
        this.setBounds(600,200,600,475);
        this.setLayout(null);
        this.setVisible(true);
        
        //Heading-------------------------------------------------------
        JLabel main_heading = new JLabel("Add Patient");
        main_heading.setBounds(55, 25, 100, 30);
        main_heading.setFont(new Font("Calibri", Font.BOLD, 18));
        this.add(main_heading);

        //ID-----------------------------------------------------
        JLabel lbl_id = new JLabel(patient.getPatientId(), JLabel.CENTER);
        lbl_id.setBounds(400, 25, 50, 30);
        lbl_id.setBackground(Color.GREEN);
        lbl_id.setOpaque(true);
        this.add(lbl_id);

        //Inner Frame----------------------------------------------------
        JPanel panel = new JPanel();
        panel.setBounds(40, 80, 500, 250);
        panel.setBackground(Color.white);           

        //First Name-----------------------------------------------------
        JLabel lbl_fname = new JLabel("First Name *");
        lbl_fname.setBounds(100, 100, 120, 30);
        lbl_fname.setFont(new Font("Calibri", Font.PLAIN, 16));
        this.add(lbl_fname);

        txt_fname = new JTextField("");
        txt_fname.setBounds(270, 100, 200, 30);
        this.add(txt_fname);

        //Surname--------------------------------------------------------
        JLabel lbl_suraname = new JLabel("Surname *");
        lbl_suraname.setBounds(100, 150, 120, 30);
        lbl_suraname.setFont(new Font("Calibri", Font.PLAIN, 16));
        this.add(lbl_suraname);

        txt_suraname = new JTextField("");
        txt_suraname.setBounds(270, 150, 200, 30);
        this.add(txt_suraname);

        //DOB------------------------------------------------------------
        JLabel lbl_dob = new JLabel("DOB (YYYY-MM-DD) *");
        lbl_dob.setBounds(100, 200, 150, 30);
        lbl_dob.setFont(new Font("Calibri", Font.PLAIN, 16));
        this.add(lbl_dob);

        txt_dob = new JTextField("");
        txt_dob.setBounds(270, 200, 200, 30);
        this.add(txt_dob);        
        
        //Mobile No------------------------------------------------------
        JLabel lbl_mobile = new JLabel("Mobile No");
        lbl_mobile.setBounds(100, 250, 120, 30);
        lbl_mobile.setFont(new Font("Calibri", Font.PLAIN, 16));
        this.add(lbl_mobile);

        txt_mobile = new JTextField("");
        txt_mobile.setBounds(270, 250, 200, 30);
        this.add(txt_mobile);        

        this.add(panel);

        //Save button ----------------------------------------------------
        btn_save = new JButton("Save");
        btn_save.setBounds(330, 350, 100, 30);
        btn_save.addActionListener(this);
        this.add(btn_save);

        //Cancel button ----------------------------------------------------
        btn_cancel = new JButton("Cancel");
        btn_cancel.setBounds(440, 350, 100, 30);
        btn_cancel.addActionListener(this);
        this.add(btn_cancel);

        this.setResizable(false); 
    }

    //--------------------------------------------------------------------------------
    // Event Handlers
    //--------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Save button on-click evemt
        if(e.getSource() == btn_save){
            
            //Form validation 
            if(Validation()==true){
                
                //fill the patient object
                patient.setName(txt_fname.getText());
                patient.setSurname(txt_suraname.getText());
                patient.setDOB(txt_dob.getText());
                patient.setMobile(txt_mobile.getText());

                WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();

                //save to the DB
                if(wm.AddPatient(patient))
                    JOptionPane.showMessageDialog(null, 
                                    "Patient added sucessfully", 
                                    "Add Patient", 
                                    JOptionPane.INFORMATION_MESSAGE);
                    
                    this.dispose(); 
            }

        }

        //Cancel button on-click evemt
        if(e.getSource() == btn_cancel){
            this.dispose();
        }
                
    }

    //--------------------------------------------------------------------------------
    // Form Validation
    //--------------------------------------------------------------------------------
    Boolean Validation(){

        Boolean isError = true;

        if(txt_fname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First Name cannot be empty", "Add Patient", JOptionPane.WARNING_MESSAGE);
            txt_fname.requestFocus();
            isError = false;
            
        }else if(txt_suraname.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Surname cannot be empty", "Add Patient", JOptionPane.WARNING_MESSAGE);
            txt_suraname.requestFocus();
            isError = false;

        }else if(txt_dob.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Date of Birth cannot be empty", "Add Patient", JOptionPane.WARNING_MESSAGE);
            txt_dob.requestFocus();
            isError = false;
        }    
       
        return isError;
    }

}

