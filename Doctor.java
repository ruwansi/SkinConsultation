
public class Doctor extends Person{
    
    private String medical_licence_number;
    private String specialization;

    //Setters
    public void setMedicalLicenceNumber(String licenceno){
        this.medical_licence_number = licenceno;        
    }
    
    public void setspecialization(String specialization){
        this.specialization = specialization;        
    }

    //Getters
    public String getMedicalLicenceNumber(){
        return this.medical_licence_number;        
    }
    
    public String getspecialization(){
        return this.specialization;        
    }

}
