import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.io.IOException;
class EmployeeDataProcessor {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public boolean processEmployeeData(int empNum) {
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/Zarah/Downloads/mphEmpData.tsv"))) {
            String line;
            int employeeNumber;
            double basicSalary = 0;
            double riceSubsidy;
            double phoneAllowance;
            double clothingAllowance;
            double semiMonthlyRate;
            double hourlyRate = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                employeeNumber = Integer.parseInt(values[0]);

                if (employeeNumber == empNum) {
                    found = true;

                    EmployeeInfoPrinter infoPrinter = new EmployeeInfoPrinter();
                    infoPrinter.printEmployeeInfo(values);

                    SalaryCalculator salaryCalculator = new SalaryCalculator();
                    salaryCalculator.calculateSalary(values);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return found;
    }


    class EmployeeInfoPrinter {
        public void printEmployeeInfo(String[] values) {
            System.out.println("=====================================");
            System.out.println("         Employee Information");
            System.out.println("=====================================");
            System.out.println("Employee Number: " + values[0]);
            System.out.println("Name: " + values[1] + ", " + values[2]);

        }
    }

    class SalaryCalculator {
        public void calculateSalary(String[] values) {
            double basicSalary = Double.parseDouble(values[13]);
            double riceSubsidy = Double.parseDouble(values[14]);
            double phoneAllowance = Double.parseDouble(values[15]);
            double clothingAllowance = Double.parseDouble(values[16]);

        
            double semiMonthlyRate = basicSalary / 2;

            double hourlyRate = (basicSalary / 21) / 8;

            System.out.println("=====================================");
            System.out.println("           Salary Information");
            System.out.println("=====================================");
            System.out.println("Basic Salary: " + df.format(basicSalary));
            System.out.println("Rice Subsidy: " + df.format(riceSubsidy));
            System.out.println("Phone Allowance: " + df.format(phoneAllowance));
            System.out.println("Clothing Allowance: " + df.format(clothingAllowance));
            System.out.println("Semi-Monthly Rate: " + df.format(semiMonthlyRate));
            System.out.println("Hourly Rate: " + df.format(hourlyRate) + "\r\n");
        }
    }
}
