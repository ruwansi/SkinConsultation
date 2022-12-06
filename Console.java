
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class Console {
    public static void main(String[] args) throws SQLException {

        WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();
       
        while (true) {
        
            //call the main menu 
            DisplayMainMenu(); 

            Scanner value = new Scanner(System.in); 
            System.out.println("Enter options 1 to 11: ");
            
            switch (value.nextInt()) {
                
				case 1: 
                    
                    Doctor doctor = new Doctor();

                    System.out.println("Enter First Name: ");
                    doctor.setName(new Scanner(System.in).next());

                    System.out.println("Enter Surname: ");
                    doctor.setSurname(new Scanner(System.in).next());

                    System.out.println("Enter Medical Licence No: ");
                    doctor.setMedicalLicenceNumber(new Scanner(System.in).next());
                    
                    System.out.println("Date of Birth (yyyy-mm-dd): ");
                    doctor.setDOB(new Scanner(System.in).next());
                    
                    System.out.println("Mobile No: ");
                    doctor.setMobile(new Scanner(System.in).next());

                    System.out.println("Specialization (Cosmetic/Medical/Pediatric dermatology): ");
                    doctor.setspecialization(new Scanner(System.in).nextLine());

                    wm.AddDoctor(doctor);
                    System.out.println("///////////// Doctor is sdded sucessfully /////////////");

                    break;

                case 2:
                    System.out.println("Enter Medical Licence No: ");
                    System.out.println("Doctor " + wm.DeleteDoctor(new Scanner(System.in).nextLine()) );
                    break;
                
                case 3:
                    System.out.println(wm.PrintDoctorsList());
                    break;

                case 4:
                    if(wm.SaveToFile(wm.doctorFilePath)) 
                        System.out.println("///////////// Saved the file sucessfully /////////////");
                    
                    break;
                
                case 5:
                    
                    Patient patient = new Patient();
                    
                    System.out.println("Enter First Name: ");
                    patient.setName(new Scanner(System.in).next());

                    System.out.println("Enter Surname: ");
                    patient.setSurname(new Scanner(System.in).next());
                    
                    System.out.println("Date of Birth (yyyy-mm-dd): ");
                    patient.setDOB(new Scanner(System.in).next());
                    
                    System.out.println("Mobile No: ");
                    patient.setMobile(new Scanner(System.in).next());

                    if(wm.AddPatient(patient))
                        System.out.println("///////////// Patient added sucessfully /////////////");
                    
                    break;

                case 6:
                    
                    //String patient_id = "P8782";
                    String doctor_id = "";

                    System.out.println("Enter Patient Id (P8782): ");
                    String patient_id = new Scanner(System.in).next();

                    System.out.println("Enter Doctor's Medical Licence No: ");
                    String lic_no = new Scanner(System.in).nextLine();

                    System.out.println("Enter Date (yyyy-mm-dd): ");
                    String date = new Scanner(System.in).nextLine();
                    
                    //check the availability
                    ResultSet availability = wm.CheckAvailability(lic_no, date);
                    
                    ArrayList<String> availability_list = new ArrayList<String>();
                    
                    while(availability.next()){  
                         
                        String row = availability.getString(1)+ "\t" +availability.getString(2)+ "\t" +
                                     availability.getString(3)+ "\t" +availability.getString(4) + "\t" +availability.getString(5);
                        
                        //create a list
                        availability_list.add(row);
                        System.out.println(row);  
                    }
                    
                    System.out.println("\nDo you want to proceed (Y/N)? : ");
                    String reply = new Scanner(System.in).nextLine().trim().toUpperCase();
                    
                    if(reply.compareTo("Y")==0){
                           
                        //get doctor assigned 
                        String assigned_doctor = wm.AutoAssignDoctor(availability_list);
                        
                        Scanner s = new Scanner(assigned_doctor);
                        s.useDelimiter("[\t]");

                        Consultation consult = new Consultation();

                        //set parameters to add
                        while(s.hasNext()){
                            s.next();
                            doctor_id = s.next();
                            consult.setDate(s.next());
                            consult.setStartTime(s.next());
                            consult.setEndTime(s.next());
                            consult.setNotes("no cash refundable if you are late");
                            consult.setImageData("");
                            consult.setStatus("A");
                            consult.setCost(wm.CalculateCost(patient_id));
                        }

                        //add to the consultation table
                        if(wm.AddConsultation(doctor_id, patient_id, consult))
                            System.out.println("///////////// Booking added sucessfully /////////////");
                            System.out.println("\nAssigned_doctor: " + doctor_id);
                        
                            s.close();
                    }
 
                    break;
                
                case 7:

                    System.out.println("Enter Booking_ID: ");
                    
                    if(wm.CancelConsultation(new Scanner(System.in).nextInt()))
                        System.out.println("///////////// Booking is Canceled....!!!! /////////////");

                    break;

                case 8:
                    
                    //Print reults
                    ResultSet bookings = wm.ViewConsultation("A");
                    
                    while(bookings.next()==true){  
                        System.out.println(bookings.getString(1)+ "\t" + bookings.getString(2)+ "\t" +bookings.getString(3) + "\t" + 
                                           bookings.getString(4)+ "\t" + bookings.getString(5)+ "\t" +bookings.getString(6) + "\t" + 
                                           bookings.getString(7)+ "\t" + bookings.getString(8)+ "\t" +bookings.getString(9) + "\t" +
                                           bookings.getString(10));  
                    }
                    
                    break;
                
                case 9:
                    new GUI();
                    break;    
                
                case 10:
                    System.exit(0);    
                
                default:
					System.out.println("///////////// Option not found, please re-enter /////////////");
                    break;
            }          

        }
             
    }

    //---------------------------------------------------------------
    // Main menu
    //---------------------------------------------------------------
    static void DisplayMainMenu() {
		System.out.println("\n");
		System.out.println("---------------------------------");
		System.out.println("     Consultants Manager						    ");
		System.out.println("---------------------------------");
		System.out.println("1. Add a new doctor");
        System.out.println("2. Delete a doctor");
        System.out.println("3. Print the list of the doctors");
        System.out.println("4. Save in a file");
        System.out.println("---------------------------------");
	    System.out.println("5. Add a patient");
        System.out.println("6. Add Consultation");
        System.out.println("7. Cancel Consultation");
        System.out.println("8. View Consultation");       
        System.out.println("9. GUI");
        System.out.println("10. Exit");
        
        System.out.println("\n");
	}

}   
