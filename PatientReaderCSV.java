


public List<Patient> readAllLines() {                                                                           
                                                                                                                
    String line = "";                                                                                           
    String[] parts = {};                                                                                        
    List<Patient> patients = new ArrayList<>();                                                                 
                                                                                                                
    Patient patient = new Patient();                                                                            
    File file = new File("src/main/resources/17-contacts.csv");                                                 
    Scanner scanner = new Scanner(file);                                                                        
                                                                                                                
    scanner.nextLine();                                                                                         
    while (scanner.hasNext()){                                                                                  
        line = scanner.nextLine();                                                                              
        parts = line.split(";");                                                                                
                                                                                                                
         //pat_num_HD - pat_lastname - pat_firstname - pat_address - pat_tel - pat_insurance_id - pat_sub_date  
          *******************************************                                                           
                                                                                                                
          *******************************************                                                           
                                                                                                                
                                                                                                                
        patient.setPat_num_HD(parts[0]);                                                                        
        patient.setPat_lastname(parts[1]);                                                                      
        patient.setPat_firstname(parts[2]);                                                                     
        patient.setPat_address(parts[3]);                                                                       
        patient.setPat_tel(parts[4]);                                                                           
        patient.setPat_insurance_id(parts[5]);                                                                  
        patient.setPat_sub_date(parts[6]);                                                                      
        patients.add(patient)     ;                                                                             
        System.out.println(line);                                                                               
    }                                                                                                           
    return patients;                                                                                            
}                                                                                                               
