
public interface SkinConsultationManager {

    public void AddDoctor(Doctor doctor);
    public String DeleteDoctor(String med_licence_no);
    public Boolean SaveToFile(String filename);
    public String PrintDoctorsList();
    
    public Boolean CheckAvailability(String licno, String date, String time);
    public Boolean AddPatient(Patient patient);
    public Boolean AddConsultation(String doc_id, String p_id, Consultation consult);
    public Boolean CancelConsultation(int booking_id);

}
