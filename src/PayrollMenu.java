import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.Scanner;

public class PayrollMenu {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final int empNum;
    private final EmployeeDataProcessor employeeDataProcessor;
    private double hourlyRate;
    private double basicSalary;

    public PayrollMenu(int empNum, EmployeeDataProcessor employeeDataProcessor) {
        this.empNum = empNum;
        this.employeeDataProcessor = employeeDataProcessor;
    }

    public void displayMenu() {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Hours Worked by Date");
            System.out.println("2. Net Income");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Date search and work hours calculation
                    System.out.println("Enter date to search (YYYY-MM-DD): ");
                    String dateToSearch = input.next();
                    double totalHoursWorked = 0;
                    double totalDayPay;
                    double totalWeekPay;

                    // Read Employee Attendance Record file
                    String line;
                    int employeeNumber;

                    try (BufferedReader br2 = new BufferedReader(new FileReader("Users/Zarah/Downloads/AttendanceRecord.csv"))) {
                        while ((line = br2.readLine()) != null) {
                            String[] values = line.split(",");
                            employeeNumber = Integer.parseInt(values[0]);

                            if (employeeNumber == empNum && values[3].equals(dateToSearch)) {
                                LocalTime startTime = LocalTime.parse(values[4]);
                                LocalTime endTime = LocalTime.parse(values[5]);

                                Duration duration = Duration.between((Temporal) startTime, (Temporal) endTime);
                                totalHoursWorked = duration.toHours();
                                totalDayPay = totalHoursWorked * hourlyRate;

                                // Calculate week pay
                                totalWeekPay = totalDayPay * 5;

                                // Display total hours worked for the searched date
                                System.out.println("Employee Number: " + empNum);
                                System.out.println("--------------------------");
                                System.out.println("Total hours worked for " + dateToSearch + ": " + df.format(totalHoursWorked) + " hours");

                                // Calculate total pay for searched date
                                System.out.println("Total pay for " + dateToSearch + ": " + df.format(totalDayPay) + "\r\n");

                                // Print total week pay
                                System.out.println("Total week pay: " + df.format(totalWeekPay) + "\r\n");
                            }
                        }

                        // Verify if work hours have been completed
                        if (totalHoursWorked >= 8) {
                            System.out.println("Required work hours completed." + "\r\n");
                        } else {
                            System.out.println("Required work hours not completed." + "\r\n");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    double sssDeduct = 0;
                    double philDeduct = 0;
                    double pagibigDeduct = 0;
                    double taxDeduct = 0;
                    double totalDeduct = 0;
                    double netBeforeTax = 0;
                    double netSalary = 0;

                    // Read deductions data file
                    try (BufferedReader br2 = new BufferedReader(new FileReader("./src/com/group7/mphDeductions.csv"))) {
                        while ((line = br2.readLine()) != null) {
                            String[] values = line.split(",");
                            employeeNumber = Integer.parseInt(values[0]);

                            if (employeeNumber == empNum) {
                                sssDeduct = Double.parseDouble(values[2]);
                                philDeduct = Double.parseDouble(values[3]);
                                pagibigDeduct = Double.parseDouble(values[4]);

                                // Calculate total deductions before tax
                                totalDeduct = sssDeduct + philDeduct + pagibigDeduct;

                                // Calculate net before tax
                                netBeforeTax = basicSalary - totalDeduct;

                                // Conditions for tax amount
                                if (basicSalary >= 20833 && basicSalary < 33333) {
                                    taxDeduct = (netBeforeTax - 20833) * 0.2;
                                } else if (basicSalary >= 33333 && basicSalary < 66667) {
                                    taxDeduct = ((netBeforeTax - 33333) * 0.25) + 2500;
                                } else if (basicSalary >= 66667 && basicSalary < 166667) {
                                    taxDeduct = ((netBeforeTax - 66667) * 0.3) + 10833;
                                } else if (basicSalary >= 166667 && basicSalary < 666667) {
                                    taxDeduct = ((netBeforeTax - 166667) * 0.32) + 40833.33;
                                } else if (basicSalary >= 666667) {
                                    taxDeduct = ((netBeforeTax - 166667) * 0.35) + 200833.33;
                                }

                                // Calculate net income after taxes
                                netSalary = netBeforeTax - taxDeduct;

                                // Print net income
                                System.out.println("Net Income: " + df.format(netSalary) + "\r\n");
                            }
                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }

    private static class LocalTime {
        public static LocalTime parse(String value) {
            return LocalTime.parse(value);
        }
    }
}