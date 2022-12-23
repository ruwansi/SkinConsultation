import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    
    //class variables
    protected ArrayList<Doctor> doctors; 

    protected final String doctorFilePath = "./DOCTORS.txt";   
       
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
    public ResultSet CheckAvailability(String lic_no, String date){

        String sql = "";

        if((lic_no.isEmpty())&&(!date.isEmpty()))
            sql = "SELECT * FROM `availability` WHERE date='" + date + "'" ;  
        else if((date.isEmpty())&&(!lic_no.isEmpty()))
            sql = "SELECT * FROM `availability` WHERE lic_no='" + lic_no + "'" ; 
        else if((lic_no.isEmpty()) && (date.isEmpty()))
            sql = "SELECT * FROM `availability`";
        else
            sql = "SELECT * FROM `availability` WHERE lic_no='" + lic_no + "' AND date='" + date + "'" ; 

        SQLCon sc= new SQLCon();    
        ResultSet rs =  sc.GetData(sql);    

        return rs;
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
    // Add consultaion (booking details)
    //--------------------------------------------------------------------------
    public Boolean AddConsultation(String doc_id, String p_id, Consultation consult){

        Boolean isAdded = false;

        try{
            String sql = "INSERT INTO `consultation`(`patient_id`, `doctor_id`, `booked_date`, `start_time`, `end_time`, `cost`, `notes`, `image_data`, `status`) " +
                         "VALUES ('" + p_id + "','" + doc_id + "','" + consult.getDate() + "','" + consult.getStartTime() + "','" 
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
            String sql = "UPDATE `consultation` SET `status`= 'C' WHERE booking_id = '" + booking_id + "'";

            SQLCon sc= new SQLCon();    
            if(sc.UpdateData(sql)) isUpdated=true;  
        
        }catch(Exception e){ 
            System.out.println(e);
        }  
        return isUpdated;
    }


    //--------------------------------------------------------------------------
    // View bookings 
    // Input param - status: A -> Active  C-> cancelled 
    //--------------------------------------------------------------------------
    public ResultSet ViewConsultation(String status){

        ResultSet rs=null;

        try{
            String sql = "SELECT * FROM `consultation` WHERE status='" + status +"'";  
        
            SQLCon sc= new SQLCon();    
            rs =  sc.GetData(sql);    
        
        }catch(Exception e){ 
            System.out.println(e);
        }  
        return rs;
    }
    
    //--------------------------------------------------------------------------
    // Calculate the consultation cost based on history 
    // For the fisrt consultation -> £15, else -> £25 
    //--------------------------------------------------------------------------
    public double CalculateCost(String patientId){

        int cost=15;

        try{
            String sql = "SELECT * FROM consultation WHERE patient_id = '" + patientId +"'";

            SQLCon sc= new SQLCon();    
            ResultSet rs = sc.GetData(sql); 
            
            //if petient exist 
            if(rs.next())
                cost = 25;
        
        }catch(Exception e){ 
            System.out.println(e);
        }  

        return cost;
    }

    //--------------------------------------------------------------------------
    // If doctor is not available, auto assign the next available doctor  
    // picking the doctor will be done randomly
    //--------------------------------------------------------------------------
    public String AutoAssignDoctor(ArrayList<String> rs){

         int i = 0;
         String row_data = "";
         int row_count = rs.size();

        //generate a random no in between the size of arraylist
        Random random = new Random();
        int size = random.nextInt(row_count);

        //loop through the row set until it matches the random value
        for(String row: rs){            
            if(size==i){
                row_data = row;
                break;
            }else{
                i = i +1;
            }    
        }
        
        return row_data;        
        
    }

    // --------------------------------------------------------------------------
    // Get Patient information from theh database
    // --------------------------------------------------------------------------
    public ResultSet GetPatientData() {

        ResultSet rs = null;
        String sql = "SELECT * FROM `patients`";

        try {
            SQLCon sc = new SQLCon();
            rs = sc.GetData(sql);

        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

}
