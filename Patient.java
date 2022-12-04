import java.util.Random;

public class Patient extends Person {

    private String patient_id;
    
    //Constructor
    public Patient(){
       
        //generate a random ID for the patient id 
        Random randomObj = new Random();
        int randomNum = randomObj.nextInt((9999 - 1000)) + 10;

        this.patient_id = "P" + randomNum;         
    }

    //getters
    public String getPatientId(){
        return this.patient_id;
    }
       
}
