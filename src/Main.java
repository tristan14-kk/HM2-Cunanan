import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter employee number: ");
        int empNum = input.nextInt();

        EmployeeDataProcessor employeeDataProcessor = new EmployeeDataProcessor();
        boolean found = employeeDataProcessor.processEmployeeData(empNum);

        if (!found) {
            System.out.println(empNum + " does not exist.");
            return;
        }

        PayrollMenu payrollMenu = new PayrollMenu(empNum, employeeDataProcessor);
        payrollMenu.displayMenu();
    }
}


