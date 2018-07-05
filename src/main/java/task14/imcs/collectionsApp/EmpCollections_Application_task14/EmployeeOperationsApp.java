package task14.imcs.collectionsApp.EmpCollections_Application_task14;

import java.util.Scanner;

import task14.imcs.empCollections.EmpCollections_Library.Employee;
import task14.imcs.empCollections.EmpCollections_Library.EmployeeNotFoundException;
import task14.imcs.empCollections.EmpCollections_Library.EmployeeServices;
import task14.imcs.empCollections.EmpCollections_Library.EmployeeServicesImpl;
import task14.imcs.empCollections.EmpCollections_Library.EmployeeUtil;
import task14.imcs.empCollections.EmpCollections_Library.SalaryException;

public class EmployeeOperationsApp {
	public static Scanner scan = new Scanner(System.in);
	public static Employee empTemp = new Employee();
	public static EmployeeServices empServ = new EmployeeServicesImpl();

	public static void main(String[] args) {
		int option;
		EmployeeUtil.getDepartment();
		System.out.println("----------------Employee System-------------------");
		empServ.readFromFile();

		do {
			System.out.println("\n\n|||Menu|||");
			System.out.println(
					"1.Create Employee\n2.Read(View) Employee.\n3.View all Employees\n4.Update Employee\n5.Delete Employee\n6.Calculate HRA\n7.Calculate Gross Salary\n8.Exit");
			System.out.println("Select your choice: ");
			option = scan.nextInt();

			switch (option) {
			case 1:
				System.out.println("Enter Employee Id: ");
				int id = scan.nextInt();
				try {
					empTemp = EmployeeUtil.createEmpObj(id);
					empServ.addEmployee(empTemp);
					System.out.println("Employee created");
				} catch (SalaryException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				System.out.println("Enter Employee ID you want to view: ");
				int view = scan.nextInt();
					String result = empServ.viewEmployee(view);
					if (result != null) {
						System.out.println("The Employee Details are as follows: \n" + result);
					} else
						System.out.println("Employee doesn't exists with the given ID.");
				break;
			case 3:
				String vAllStr = empServ.viewAllEmployees();
				if (vAllStr != null) {
					System.out.println(vAllStr);
				} else {
					System.out.println("No elements in the arrray");
				}
				break;
			case 4:
				System.out.println("Enter Employee Id you want to Update: ");
				int idUpd = scan.nextInt();
				try {
					boolean update = empServ.isExists(idUpd);
					if (update) {
						try {
							empTemp = EmployeeUtil.createEmpObj(idUpd);
							empServ.updateEmployee(idUpd, empTemp);
						} catch (SalaryException e) {
							System.out.println("Salary Excpetion occurred");
							e.printStackTrace();
						}
						System.out.println("Updated the Employee details");
					} 
				} catch (EmployeeNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Enter ID of Employee you want to delete: ");
				int delId = scan.nextInt();
				boolean delStatus = empServ.deleteEmployee(delId);
					if (delStatus) {
						System.out.println("Employee deleted.");
					} else
						System.out.println("Given ID doesn't exists.");
				break;
			case 6:
				System.out.println("Enter ID of Employee to calculate HRA: ");
				int hraId = scan.nextInt();
				double hra = empServ.getHRASrvc(hraId);
				if (hra != 0) {
					System.out.println("HRA is: " + hra);
				} else
					System.out.println("Given ID doesn't exists. Please try again.");
				break;
			case 7:
				System.out.println("Enter ID of Employee to calculate Gross Salary: ");
				int grossId = scan.nextInt();
				double gross = empServ.getGrossSrvc(grossId);
				if (gross != 0) {
					System.out.println("Gross Salary is: " + gross);
				} else
					System.out.println("Given ID doesn't exists. Please try again.");
				break;
			default:
				if (option != 8)
					System.out.println("Invalid value. Please try again");
				break;
			}
		} while (option != 8);

		if (empServ.writeToFile())
			System.out.println("Updates written to file");
		else
			System.out.println("Updated Not Written");
	}

}
