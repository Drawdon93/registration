import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class PatientService {

    private List<Patient> patientList;

    public boolean isRegistered(BigInteger pesel) {
        boolean isRegistered = false;

        for (Patient patient : patientList) {
            if (patient.getPesel().equals(pesel)) {
                isRegistered = Boolean.TRUE;
            }
        }
        return isRegistered;
    }

    public boolean isRegistered(String name, String surname) {
        boolean isRegistered = false;

        for (Patient patient : patientList) {
            if (patient.getName().equals(name) && patient.getSurname().equals(surname)) {
                isRegistered = Boolean.TRUE;
            }
        }
        return isRegistered;
    }

    public Patient findPatientOrNull(String name, String surname) {
        Patient patientTemp = null;

        for (Patient patient : patientList) {
            if (patient.getName().equals(name) && patient.getSurname().equals(surname)) {
                patientTemp = patient;
            }
        }
        return patientTemp;
    }

    public Patient findPatientOrNull(BigInteger pesel) {
        Patient patientTemp = null;

        for (Patient patient : patientList) {
            if (patient.getPesel().equals(pesel)) {
                patientTemp = patient;
            }
        }

        return patientTemp;
    }

}
