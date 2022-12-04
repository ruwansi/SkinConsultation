import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    
    //class variables
    protected ArrayList<Doctor> doctors; 

    protected final String doctorFilePath = "./data/DOCTORS.txt";
   
       
    //-------------------------------------------------------------------------------------
    //constructor
    //-------------------------------------------------------------------------------------
    public WestminsterSkinConsultationManager(){
       
        //initialization
        doctors = new ArrayList<Doctor>();
              
        //read existing data from the text files
        ReadFromFile(doctorFilePath);
    }
            
    //-------------------------------------------------------------------------------------
    // Add a doctor to the arraylist
    //-------------------------------------------------------------------------------------
    public void AddDoctor(Doctor doctor){    
        
        //add to the ArrayList, max 10 records
        if(doctors.size()<10)
            doctors.add(doctor); 
                  
    }
    
    //-------------------------------------------------------------------------------------
    // Delete a doctor from the arraylist 
    // Input parameter - med_licence_no (doctor's medical License no) 
    // Out - deleted doctor info 
    //-------------------------------------------------------------------------------------
    public String DeleteDoctor(String med_licence_no){
        
        String info="";

        //loop thruogh the array list and remove if found
        for(Doctor d: doctors){
            if(d.getMedicalLicenceNumber().compareTo(med_licence_no) == 0){
                doctors.remove(d);
                SaveToFile(doctorFilePath);
                info = d.getName() + " " + d.getSurname() + " (Lic No:" + d.getMedicalLicenceNumber() + ") deleted.\nRemaning count :" + doctors.size();
                break;
            }
        }
        return info;
    }

    //-------------------------------------------------------------------------------------
    // Save the list of Doctors to a text file 
    // Input parameter - name of the file & path 
    //-------------------------------------------------------------------------------------
    public Boolean SaveToFile(String filename){

        Boolean isSavedSucessfully = false;
        try{
            FileWriter file = new FileWriter(filename);
            file.write(DoctorsList());
            file.close();
            isSavedSucessfully = true;
    
        } catch (Exception e) {
            System.out.println(e);
        }     
        
        return isSavedSucessfully;
    }

    //-------------------------------------------------------------------------------------
    // Generate Doctors list and return as a String  
    //-------------------------------------------------------------------------------------
    public String PrintDoctorsList(){
        
        String docList="";

        //sort by surname (implemented in Person.java)
        Collections.sort(doctors);
                
        for(Doctor d: doctors){
            docList = docList + d.getName() + " " +
                                d.getSurname() + " - " +
                                d.getDOB() + " - " +
                                d.getMobile() + " - " +
                                d.getMedicalLicenceNumber()+ " - " +
                                d.getspecialization() + "\n";                                
        }           
        return docList;
    }

    //-------------------------------------------------------------------------------------
    // Generate Doctors list with "|" seperated String to save and read from the text file  
    //-------------------------------------------------------------------------------------
    private String DoctorsList(){
        
        String docList="";

        for(Doctor d: doctors){
            docList = docList + d.getName() + "|" +
                                d.getSurname() + "|" +
                                d.getDOB() + "|" +
                                d.getMobile() + "|" +
                                d.getMedicalLicenceNumber()+ "|" +
                                d.getspecialization() + "\n";
        }   
        return docList;
    }

    //-------------------------------------------------------------------------------------
    // Read Doctors list line by line from the saved text file and send for parsing 
    // Input parameters - file path name
    //-------------------------------------------------------------------------------------
    private void ReadFromFile(String filename){

        File file = new File(filename);

        if(file.exists()){
            Scanner sc = null;
            try {
                sc = new Scanner(file);
                
                // loop thru each line, 
                while(sc.hasNextLine()){
                   
                    Scanner sc1 = new Scanner(sc.nextLine());
                    sc1.useDelimiter("[|]");
                    
                    Doctor doc = new Doctor();

                    // Check if there is another line of input
                    while(sc1.hasNext()){

                        //assign values
                        doc.setName(sc1.next());
                        doc.setSurname(sc1.next());
                        doc.setDOB(sc1.next());
                        doc.setMobile(sc1.next()); 
                        doc.setMedicalLicenceNumber(sc1.next());
                        doc.setspecialization(sc1.next());
                        
                        AddDoctor(doc);   
                    }
                    
                    sc1.close();
                }
    
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            
            sc.close();
        }
    }

       
    //------------------------------------------------------------------------------------------------------
    //      CONSULTATION
    //------------------------------------------------------------------------------------------------------
    
    //--------------------------------------------------------------------------
    // Check Doctor's availablity
    // Input parameters - doctor's medical lic no, date and start time
    //--------------------------------------------------------------------------
    public Boolean CheckAvailability(String licno, String date, String time){

        Boolean isExists = false;
        String sql = "";

        if(licno.isEmpty() && (time.isEmpty()))
            sql = "SELECT * FROM `availability` WHERE date='"+ date +"'" ;  
        else if(date.isEmpty() && (time.isEmpty()) )
            sql = "SELECT * FROM `availability` WHERE lic_no='" + licno + "'" ; 
        else if(licno.isEmpty() && date.isEmpty())
            sql = "SELECT * FROM `availability` WHERE start='"+ time + "'" ;
        else if(time.isEmpty())
            sql = "SELECT * FROM `availability` WHERE lic_no='" + licno + "' AND date='"+ date + "'" ; 
        else if(date.isEmpty())
            sql = "SELECT * FROM `availability` WHERE lic_no='" + licno + "' AND start='"+ time + "'" ;
        else if(licno.isEmpty())
            sql = "SELECT * FROM `availability` WHERE date='" + date + "' AND start='"+ time + "'" ;
        else
            sql = "SELECT * FROM `availability` WHERE lic_no='" + licno + "' AND date='"+ date +"' AND start='" + time + "'" ; 

        SQLCon sc= new SQLCon();    
        ResultSet rs =  sc.GetData(sql);    
        
        try{
            while(rs.next()==true){  
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));  
                isExists=true;
            }
        }catch(Exception e){ 
            System.out.println(e);
        }
        
        return isExists;
    }

    //--------------------------------------------------------------------------
    // Add a patient to the DB patients
    // Input params - patient object
    //--------------------------------------------------------------------------
    public Boolean AddPatient(Patient patient){
        
        Boolean isAdded = false;

        try{

            String sql = "INSERT INTO `patients`(`name`, `surname`, `dob`, `mobile`, `pid`) VALUES ('" 
                        + patient.getName() + "','" + patient.getSurname() + "','" + patient.getDOB() + "','" 
                        + patient.getMobile() + "','" + patient.getPatientId() + "')";

            SQLCon sc= new SQLCon();    
            if(sc.UpdateData(sql)) isAdded=true;  
        
        }catch(Exception e){ 
            System.out.println(e);
        }  

        return isAdded;
    }

    //--------------------------------------------------------------------------
    //
    //--------------------------------------------------------------------------
    public Boolean AddConsultation(String doc_id, String p_id, Consultation consult){

        Boolean isAdded = false;

        try{
            String sql = "INSERT INTO `consultations`(`patient_id`, `doc_id`, `booked_date`, `start_time`, `end_time`, `cost`, `notes`, `image`, `status`) " +
                         "VALUES ('" + doc_id + "','" + p_id + "','" + consult.getDate() + "','" + consult.getStartTime() + "','" 
                        + consult.getEndTime() + "','" + consult.getCost() + "','" + consult.getNotes() + "','" 
                        + consult.getImageDate() + "','" + consult.getStatus() + "')";

            SQLCon sc= new SQLCon();    
            if(sc.UpdateData(sql)) isAdded=true;  
        
        }catch(Exception e){ 
            System.out.println(e);
        }  

        return isAdded;

    }

    //--------------------------------------------------------------------------
    // Cancel and existing consutaion booking by change its status 
    // status: C -> cancel, A -> active
    //--------------------------------------------------------------------------
    public Boolean CancelConsultation(int booking_id){

        Boolean isUpdated = false;

        try{
            String sql = "UPDATE consultations SET status =`C` WHERE booking_id =" + booking_id +"')";

            SQLCon sc= new SQLCon();    
            if(sc.UpdateData(sql)) isUpdated=true;  
        
        }catch(Exception e){ 
            System.out.println(e);
        }  

        return isUpdated;
    }

    

}
