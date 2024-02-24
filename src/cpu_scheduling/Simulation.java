package cpu_scheduling;

import java.util.ArrayList;

public abstract class Simulation {
	
	//Todo
	//Add 2d shape method
	//remove 2d shape method
	//
	
	//variables
	ArrayList<Processes> processesList; //list of all processes
	ArrayList<Processes> cpuQueue; //list of all processes in queue to access cpu
	ArrayList<Processes> ioQueue; //list of all processes in queue to access io
	ArrayList<Processes> finished;
	int quantum;
	int systemTime;
	
	//constructor
	public Simulation(ArrayList<Processes> processesList, int quantum) {
		this.processesList = processesList;
		this.cpuQueue = new ArrayList<Processes>();
		this.ioQueue = new ArrayList<Processes>();
		this.quantum = quantum;
		this.systemTime = 0;
		this.finished = new ArrayList<Processes>();
	}
	
	public abstract Processes pickNextProcess();

}
