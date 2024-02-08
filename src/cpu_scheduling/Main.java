package cpu_scheduling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//Testing
		ArrayList<Processes> processesList = load("ScenarioFileOne");
		
		for (int i = 0; i < processesList.size(); i++) {
			System.out.println(processesList.get(i));
		}
		
		new ProjectGUI();
	}
	
	//loader to load test file into an array list of process objects
	public static ArrayList<Processes> load(String location) {
		
		ArrayList<Processes> processesList = new ArrayList<Processes>();
		
		try {
			FileInputStream fis = new FileInputStream(location);
			Scanner sc = new Scanner(fis);
			while (sc.hasNextLine()) {
				String[] tokens = sc.nextLine().split(" ");
				String name = tokens[0].substring(1, tokens[0].length() - 1);
				int arrivalTime = Integer.valueOf(tokens[1].substring(1, tokens[1].length() - 1));
				int priorityLevel = Integer.valueOf(tokens[2].substring(1, tokens[2].length() - 1));
				Processes temp = new Processes(name, arrivalTime, priorityLevel);
				
				//add CPU and IO bursts to respective lists
				for (int x  = 3; x < tokens.length; x++) {
					if (x % 2 == 1) {
						temp.cpuBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
					} else {
						temp.ioBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
					}
				}
				
				processesList.add(temp);
				
			}
			//close scanner after reading simulation file
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return processesList;
		
	}
	
	public static void sortProcessesList(ArrayList<Processes> processesList) {
		
		
		
	}

}
