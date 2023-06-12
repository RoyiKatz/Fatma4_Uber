import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Testing {
	
	static Scanner sc = new Scanner(System.in);


	public static void main(String[] args) {

		// getting user input
		System.out.println();
		System.out.print("Enter number of drivers: ");
		int num_of_drivers = sc.nextInt();

		System.out.println();
		System.out.print("Enter Car Officer work time (in seconds): ");
		double car_officer_work_time = sc.nextDouble();

		
		Company c = new Company(num_of_drivers, car_officer_work_time);
		
	}

}
