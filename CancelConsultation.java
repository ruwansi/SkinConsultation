import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;


public class CancelConsultation extends JFrame implements ActionListener {

    final JButton btn_ok;
    final JTextField txt_bookingID;
    private String booking_id;

    public CancelConsultation(){

        this.setTitle("Cancel Consultation");
        this.setBounds(650,400,560,200);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);

        JLabel lbl_title = new JLabel("Enter Booking No: ");
        lbl_title.setBounds(50, 50, 200, 30);
        lbl_title.setFont(new Font("Calibri", Font.PLAIN, 16));
        this.add(lbl_title);

        txt_bookingID = new JTextField("");
        txt_bookingID.setBounds(180, 50, 200, 30);
        this.add(txt_bookingID);        
        
        btn_ok = new JButton("OK");
        btn_ok.setBounds(400, 50, 100, 30);
        btn_ok.addActionListener(this);
        this.add(btn_ok);

    }


    //--------------------------------------------------------------------------------
    // Event Handlers
    //--------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == btn_ok){
            
            booking_id = txt_bookingID.getText().toString();

            if(Validation()==true){
                
                WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();

                //Update the DB
                if(wm.CancelConsultation(Integer.parseInt(booking_id)))
                {
                    JOptionPane.showMessageDialog(null, "Booking No " + booking_id +" removed from the system", "Cancel Consultation", 
                                                  JOptionPane.INFORMATION_MESSAGE);                    
                    this.dispose(); 
                }else{
                    JOptionPane.showMessageDialog(null, "Booking No does not exists, please re-enter.", "Cancel Consultation", 
                                                  JOptionPane.ERROR_MESSAGE);
                }    
            }          
        }        
    }


    //--------------------------------------------------------------------------------
    // Form Validation
    //--------------------------------------------------------------------------------
    Boolean Validation(){

        Boolean isError = true;
        String input = txt_bookingID.getText();

        if ( input.isEmpty()){
            JOptionPane.showMessageDialog(null, " Please enter the Booking No",  "Cancel Consultation", JOptionPane.WARNING_MESSAGE);
            txt_bookingID.requestFocus();
            isError = false;

        } else if( !input.matches("[0-9.]+")) {
            JOptionPane.showMessageDialog(null, "Booking No should be numeric, please re-enter.",  "Cancel Consultation", JOptionPane.WARNING_MESSAGE);
            txt_bookingID.requestFocus();
            isError = false;
        }

        return isError;
    }
    

}
