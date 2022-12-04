
import java.util.Scanner;

public class Console {
    public static void main(String[] args) {

        WestminsterSkinConsultationManager wm = new WestminsterSkinConsultationManager();
       
        Scanner input = new Scanner(System.in);

        while (true) {
        
            //call the main menu 
            DisplayMainMenu(); 

            //get the user option5
            System.out.println("Enter options 1 to 4: ");
		    String value = input.next();
            
            switch (value) {
                
				case "1": 
                    
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

                    // wm.AddDoctor("Ruwan", "Silva", "1972-05-15", "071 405 2829", "M12345", "Cosmetic dermatology");
                    // wm.AddDoctor("Sonal", "Maleesha", "2005-04-27", "077 777 8900" , "M11111", "Medical dermatology");
                    // wm.AddDoctor("Onesha", "Hashini", "2008-12-19", "070 345 1234", "M22222", "Pediatric dermatology");
                    break;

                case "2":
                    System.out.println("Enter Medical Licence No: ");
                    System.out.println("Doctor " + wm.DeleteDoctor(new Scanner(System.in).nextLine()) );
                    break;
                
                case "3":
                    System.out.println(wm.PrintDoctorsList());
                    break;

                case "4":
                    if(wm.SaveToFile(wm.doctorFilePath)) 
                        System.out.println("///////////// Saved the file sucessfully /////////////");
                    
                    break;
                
                case "5":
                    System.out.println("Enter Medical Licence No: ");
                    String lic_no = new Scanner(System.in).nextLine();

                    System.out.println("Enter Date (yyyy-mm-dd): ");
                    String date = new Scanner(System.in).nextLine();
                    
                    System.out.println("Enter Time (hh:mm): ");
                    String time = new Scanner(System.in).nextLine();
                    
                    if(wm.CheckAvailability(lic_no, date, time)==false) 
                        System.out.println("///////////// No doctor is Available /////////////");
                    
                    break;

                case "6":
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

                case "7":
                    break;

                case "8":
                    break;

                case "9":
                    new GUI();
                    break;    
                
                case "10":
                    System.exit(0);    
                
                default:
					System.out.println("///////////// Option not found, please re-enter /////////////");
                    break;
            }          

            //input.close();
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
		System.out.println("5. Check Doctor Availability");
        System.out.println("6. Add a patient");
        System.out.println("7. Add Consultation");
        System.out.println("8. Cancel Consultation");       
        System.out.println("9. GUI");
        System.out.println("10. Exit");
        
        System.out.println("\n");
	}

}   
