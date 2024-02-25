package cpu_scheduling;

import java.util.ArrayList;

public abstract class Simulation {
	
	//Todo
	//Add 2d shape method
	//remove 2d shape method
	//
	
	//variables
	ArrayList<Processes> processesList; //list of all processes
	ArrayList<Processes> remainingProcesses; //remaining processes to enter system
	ArrayList<Processes> cpuQueue; //list of all processes in queue to access cpu
	ArrayList<Processes> ioQueue; //list of all processes in queue to access io
	ArrayList<Processes> finished;
	int systemTime;
	
	//constructor
	public Simulation(ArrayList<Processes> processesList) {
		this.processesList = processesList;
		this.remainingProcesses = processesList;
		this.cpuQueue = new ArrayList<Processes>();
		this.ioQueue = new ArrayList<Processes>();
		this.systemTime = 0;
		this.finished = new ArrayList<Processes>();
	}
	
	public abstract Processes pickNextProcess();
	
	public ArrayList<Processes> getRemianingProcessesList() {
		return remainingProcesses;
	}
	
	public void

}
