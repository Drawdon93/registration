import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static Scanner scanner;
    private static PatientService patientService;
    private static List<Patient> patientList;

    //TODO Dopisać możliwość usunięcia z rejestru pacjenta oraz dodać pole do Pacjenta z ceną wizyty

    public static void main(String[] args) {
        ApachePOIExcelRead apachePOIExcelRead = new ApachePOIExcelRead();
        patientList = apachePOIExcelRead.getPatientList();
        patientService = new PatientService(patientList);
        scanner = new Scanner(System.in);
        System.out.println("Wybierz akcje: \n0 - Zakończ działanie \n1 - Sprawdź czy pacjent jest zarejestrowany \n2 - Zarejestruj pacjenta");
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
                registerPatient();
                System.out.println("Udało się zarejestrować nowego pacjenta");
                System.out.println(patientList);
                break;
            default:
                break;
        }
    }

    private static void registerPatient() {
        //TODO dopisać rejestracje pacjenta
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
                //TODO W przypadku dopasowań więcej niż 1 rzucić użytkownikowi błąd
            case 2:
                System.out.println("Podaj pesel: ");
                String pesel = scanner.next();
                System.out.println(patientService.isRegistered(new BigInteger(pesel)));
                break;
            default:
                break;
        }
    }


}
