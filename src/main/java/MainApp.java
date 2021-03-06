import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;
import wyjatki.TooManyPatientException;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainApp {

    private static Scanner scanner;
    private static PatientService patientService;
    private static List<Patient> patientList;
    private static ApachePOIExcelWrite apachePOIExcelWrite;

    public static void main(String[] args) {
        ApachePOIExcelRead apachePOIExcelRead = new ApachePOIExcelRead();
        apachePOIExcelWrite = new ApachePOIExcelWrite();
        patientList = apachePOIExcelRead.getPatientList();
        patientService = new PatientService(patientList);
        scanner = new Scanner(System.in);
        System.out.println("Wybierz akcje: \n0 - Zakończ działanie \n1 - Sprawdź czy pacjent jest zarejestrowany \n2 - Zarejestruj pacjenta \n3 - Usuń pacjenta \n4 - Zrób badanie na Koronowirusa!");
        Integer action = scanner.nextInt();
        chooseTypeSearching(action);
    }

    private static void chooseTypeSearching(Integer typeNumber) {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                isRegistered();
                break;
            case 2:
                try {
                    registerPatient();
                } catch (TooManyPatientException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                deletingPatient();
            case 4:

                badankonaskierowanko();
            default:
                break;
        }
    }

    private static void badankonaskierowanko() {
        System.out.println("podaj cene badania: ");
        Double kwota = scanner.nextDouble();
        Random random = new Random();
        Boolean stanZdrowia = random.nextBoolean();

        for (Patient patient : patientList) {
            if (patient.getPortfel() <= kwota) {
                patient.setStanZdrowia("brak");

                System.out.println("Pacjent: " + patient + " Nie uzyskał badań, pieniądze nie zostały pobrane.");

            } else {
                double temp = patient.getPortfel();
                patient.setPortfel(temp - kwota);

                if (stanZdrowia == false) {
                    patient.setStanZdrowia("negatywny");
                } else {
                    patient.setStanZdrowia("pozytywny");
                }

                System.out.println("gratuluje: " + patient + " Zrobiłeś badanie.");

            }
        }
        apachePOIExcelWrite.createExcel(patientList);


    }

    private static void deletingPatient() {
        System.out.println(patientList);
        Patient patientTemp = findPatient();
        patientList.remove(patientTemp);
        System.out.println(patientList);
        apachePOIExcelWrite.createExcel(patientList);
    }

    private static void registerPatient() throws TooManyPatientException {
        System.out.println("Podaj imię: ");
        String name = scanner.next();
        System.out.println("Podaj nazwisko: ");
        String surname = scanner.next();
        System.out.println("Podaj PESEL: ");
        BigInteger pesel = scanner.nextBigInteger();
        System.out.println("Podaj kwotę wizyty: ");
        Double price = scanner.nextDouble();
        System.out.println("Zdrowy czy chory?");
        String stanZdrowia = scanner.next();

        if (patientService.isRegistered(pesel)) {
            throw new TooManyPatientException();
        }
        patientList.add(new Patient(name, surname, pesel, stanZdrowia, price));
        apachePOIExcelWrite.createExcel(patientList);

        System.out.println("Udało się zarejestrować nowego pacjenta");
        System.out.println(patientList);
    }

    private static void isRegistered() {
        System.out.println("Sprawdź czy pacjent jest zarejestrowany po: \n0 - Zakończ działanie \n1 - imieniu i nazwisku \n2 - numerze PESEL");
        Integer action = scanner.nextInt();

        switch (action) {
            case 0:
                break;
            case 1:
                System.out.println("Podaj imię: ");
                String name = scanner.next();
                System.out.println("Podaj nazwisko: ");
                String surname = scanner.next();
                System.out.println(patientService.isRegistered(name, surname));
                break;
            case 2:
                System.out.println("Podaj pesel: ");
                BigInteger pesel = scanner.nextBigInteger();
                System.out.println(patientService.isRegistered(pesel));
                break;
            default:
                break;
        }
    }

    private static Patient findPatient() {
        System.out.println("Wyszukaj pacjenta po: \n0 - Zakończ działanie \n1 - imieniu i nazwisku \n2 - numerze PESEL");
        Integer action = scanner.nextInt();

        Patient patientTemp = null;

        switch (action) {
            case 0:
                break;
            case 1:
                System.out.println("Podaj imię: ");
                String name = scanner.next();
                System.out.println("Podaj nazwisko: ");
                String surname = scanner.next();
                patientTemp = patientService.findPatientOrNull(name, surname);
                break;
            case 2:
                System.out.println("Podaj pesel: ");
                String pesel = scanner.next();
                patientTemp = patientService.findPatientOrNull(new BigInteger(pesel));
                break;
            default:
                break;
        }
        return patientTemp;
    }
}

