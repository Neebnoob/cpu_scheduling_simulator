import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {

	// Allen's file location -
	// /Users/uv9380gh/GitClones/cpu_scheduling_simulator/ScenarioFileOne
	
	public static void main(String[] args) {

		//runs though the console application until user stops it
		while (true) {
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Welcome to CPU Scheduling Simulator\n" + "Please input Simulation File location");
			ArrayList<PCB> simFile = loadSimulationFile(scan);
			//used for asking users requested speed and calls a function for it
			System.out.println("Please input the speed between 1 and 5\n" + "This is the time between frames in seconds");
			int speed = getSpeed(scan);
			//used for asking a users requested mode and calls function for it
			System.out.println("Please input the mode to run simulation\n" + "1 - Manual mode and 0 - Automatic mode");
			Boolean mode = getMode(scan);
			//used for asking a users requested scheduling algorithm and calls function for it
			System.out.println("Please input the mode you would like the simulation to run\n"
					+ "FCFS - First come first server\n" + "SJF - Shortest job first\n" + "Priority");
			Simulation sim = getAlgorithim(scan, simFile);
			System.out.println("For the simulation please enter a name for the log file that will be created");
			String userFileName = scan.nextLine();
			try {
				sim.createLogFile(userFileName);
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Used for if user selected manual mode
			if (mode) {
				while (!sim.scheduleDone()) {
					sim.schedule();
					try {
						Thread.sleep(speed * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//waits for user to type next before moving on
					waitForNext(scan);
				}
				//used for automatic mode
			} else {
				while (!sim.scheduleDone()) {
					sim.schedule();
					try {
						//sets sleep time based on user input of between 1 and 5 seconds per frame
						Thread.sleep(speed * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			sim.closeWriter();
			sim.getResults();
		}

	}
	
	//function to wait for user to type next in manual mode
	private static void waitForNext(Scanner scan) {
		System.out.println("Type Next to move on");
		Boolean flag = true;
		while (flag) {
			//Throws error if another data type is entered or if user spells next wrong
			try {
				String val= scan.nextLine();
				//not case sensitive
				if (val.toLowerCase().equals("next"))
					flag = false;
				else
					throw new IllegalArgumentException();
			} catch (IllegalArgumentException iae) {
				System.out.println("This is invalid. Please type next");
			} catch (Exception e) {
				System.out.println("This is invalid. Please type Next");
			}
		}
	}

	//used to create the desired algorithms object
	private static Simulation getAlgorithim(Scanner scan, ArrayList<PCB> simFile) {
		Boolean flag = true;
		String algo = "";
		scan.nextLine();
		while (flag) {
			//Throws error if another data type is entered or if user spells an option wrong
			try {
				algo = scan.nextLine();
				//not case sensitive
				if (algo.toUpperCase().equals("FCFS"))
					return new FCFS(simFile);
				else if (algo.toUpperCase().equals("SJF"))
					return new SJF(simFile);
				else if (algo.toUpperCase().equals("PRIORITY")) 
					return new Priority(simFile);
				else
					throw new IllegalArgumentException();
			} catch (IllegalArgumentException iae) {
				System.out.println("Please enter a valid input");
			} catch (Exception e) {
				System.out.println("Please enter a valid input");
			}
		}
		return null;
	}

	// returns mode user wishes to have simulation run in
	// true -> Manual
	// false -> Automatic
	private static Boolean getMode(Scanner scan) {
		Boolean flag = true;
		int val = -1;
		while (flag) {
			//Throws error if another data type is entered or if user tries a number not allowed
			try {
				val = scan.nextInt();
				if (val > 1 || val < 0)
					throw new IllegalArgumentException();
				flag = false;
			} catch (IllegalArgumentException iae) {
				System.out.println("This is not a valid number\n" + "Try again");
			} catch (Exception e) {
				System.out.println("This is not a valid number\n" + "Try again");
			}
		}
		if (val == 1) {
			return true;
		} else {
			return false;
		}
	}

	// returns speed from user input
	private static int getSpeed(Scanner scan) {
		Boolean flag = true;
		int val = -1;
		while (flag) {
			//Throws error if another data type is entered or if user tries a number not allowed
			try {
				val = scan.nextInt();
				if (val <= -1 || val > 5)
					throw new IllegalArgumentException();
				flag = false;
			} catch (IllegalArgumentException iae) {
				System.out.println("This is not a valid number\n" + "Try again");
			} catch (Exception e) {
				System.out.println("This is not a valid number\n" + "Try again");
			}
		}
		return val;
	}

	// Loads a ArrayList of PCB objects based on Simulation File that user presents
	private static ArrayList<PCB> loadSimulationFile(Scanner scan) {

		ArrayList<PCB> tempProcessesList = new ArrayList<PCB>();
		Boolean flag = true;
		while (flag) {
			try {
				String location = scan.nextLine();
				FileInputStream fis = new FileInputStream(location);
				flag = false;
				Scanner sc = new Scanner(fis);

				while (sc.hasNextLine()) {
					String[] tokens = sc.nextLine().split(" ");
					String name = tokens[0].substring(1, tokens[0].length() - 1);
					int arrivalTime = Integer.valueOf(tokens[1].substring(1, tokens[1].length() - 1));
					int priorityLevel = Integer.valueOf(tokens[2].substring(1, tokens[2].length() - 1));
					PCB temp = new PCB(name, arrivalTime, priorityLevel);

					// add CPU and IO bursts to respective lists
					for (int x = 3; x < tokens.length; x++) {
						if (x % 2 == 1) {
							temp.cpuBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
						} else {
							temp.ioBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
						}
					}

					tempProcessesList.add(temp);

				}
				// close scanner after reading simulation file
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println("Plase input a valid file location");
			}
			;
		}

		quickSort(tempProcessesList, 0, tempProcessesList.size() - 1);

		return tempProcessesList;

	}

	// Quick sort main method
	public static void quickSort(ArrayList<PCB> processesList, int low, int high) {

		if (low < high) {

			int pi = partition(processesList, low, high);

			quickSort(processesList, low, pi - 1);
			quickSort(processesList, pi + 1, high);

		}
	}

	// Quick sort method to break list into partitions
	private static int partition(ArrayList<PCB> processesList, int low, int high) {

		int pivot = Integer.valueOf(processesList.get(high).getArrivalTime());

		int i = low - 1;

		for (int j = low; j < high - 1; j++) {

			if (Integer.valueOf(processesList.get(j).getArrivalTime()) < pivot) {

				i++;
				swap(processesList, i, j);

			}
		}
		swap(processesList, i + 1, high);
		return (i + 1);
	}

	// Quick sort method to swap positions
	private static void swap(ArrayList<PCB> processesList, int i, int j) {
		PCB temp = processesList.get(i);
		processesList.set(i, processesList.get(j));
		processesList.set(j, temp);
	}

}
