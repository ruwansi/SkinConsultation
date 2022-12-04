import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements ActionListener {
   
    JFrame frame;
    JButton viewDoctors;
    JButton viewBookings;
    JButton addPatient;
    
    JPanel panelmain;
    JPanel panelTop;

    public GUI(){

        new JFrame();

        JPanel panelleft = new JPanel();
        panelleft.setPreferredSize(new Dimension(100,300));
        panelleft.setBackground(Color.LIGHT_GRAY);

        panelTop = new JPanel();
        panelTop.setPreferredSize(new Dimension(700,300));
        panelTop.setBackground(Color.WHITE);

        panelmain = new JPanel();
        panelmain.setPreferredSize(new Dimension(800,200));
        panelmain.setBackground(Color.BLUE);


        viewDoctors = new JButton("View Doctors");
        viewDoctors.setSize(200, 100);
        
        viewBookings = new JButton("View Bookings");
        viewBookings.setSize(200, 50);
        viewBookings.addActionListener(this);
        
        addPatient = new JButton("Add Patient");
        addPatient.setSize(200, 50);
      

        panelleft.add(viewDoctors);
        panelleft.add(viewBookings);
        panelleft.add(addPatient);

        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panelleft, BorderLayout.WEST);
        this.add(panelmain, BorderLayout.SOUTH);
       // this.add(panelTop, BorderLayout.NORTH);
         
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setVisible(true);
        
        SwingTest();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==viewBookings){
           SwingTest();
        }
        
    }


    void SwingTest(){
    
        WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();
                
        JTable table = new JTable();
        JScrollPane scroll = new JScrollPane();  
        DefaultTableModel model = new DefaultTableModel();

        String headers[] = {"Name","DOB","Mobile","Medical Lic No","Specialization"};
        
        model.setColumnIdentifiers(headers);   
        table.setModel(model);
        table.setAutoCreateRowSorter(true);

        scroll = new JScrollPane(table);
        
        Object rowData[] = new Object[5];
        
        for(int i = 0; i < wm.doctors.size(); i++)
        {
            rowData[0] = wm.doctors.get(i).getName() + " " + wm.doctors.get(i).getSurname();
            rowData[1] = wm.doctors.get(i).getDOB();
            rowData[2] = wm.doctors.get(i).getMobile();
            rowData[3] = wm.doctors.get(i).getMedicalLicenceNumber();
            rowData[4] = wm.doctors.get(i).getspecialization();
            
            model.addRow(rowData);
            
        }
        
        
        panelmain.add(scroll);
        this.setVisible(true);

    }

}
