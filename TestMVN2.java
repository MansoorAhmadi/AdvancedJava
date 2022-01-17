 public static void main(String[] args) throws FileNotFoundException {

        //Calling the instance of PatientReader and InsuranceReader

        //Objects
        Patient patient = new Patient();
        Insurance insurance = new Insurance();

        //Reader classes objects
        PatientReader patientReader = new PatientReader();
        InsuranceReader insuranceReader = new InsuranceReader();

        //@methods
//        patientReader.readAll();
        insuranceReader.readAll();

    }
