package cpu_scheduling;

import java.util.ArrayList;

//processes class

public class Processes {
	
	//variables
	String name;
	int arrivalTime;
	int priorityLevel;
	ArrayList<Integer> cpuBursts;
	ArrayList<Integer> ioBursts;
	int tracker;
	int startTime;
	int finishTime;
	int waitTime;
	int waitIOTime;
	String status;
	Boolean cpuFlag;
	
	//constructor
	public Processes(String name, int arrivalTime, int priorityLevel) {
		this.name =  name;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.cpuBursts = new ArrayList<Integer>();
		this.ioBursts = new ArrayList<Integer>();
		this.tracker = 0; //how many cycles have been completed .. correlates to burst array locations
		this.startTime = 0; //time that process first entered CPU
		this.finishTime = 0; //time that process finished
		this.waitTime = 0; //time spent in CPU queue
		this.waitIOTime = 0; //time spend in IO queue
		this.status = "Processing"; //Processing -> Arrived -> Started -> Finished
		this.cpuFlag = true; //true -> CPU .. false -> IO
	}
	
	//returns name
	public String getName() {
		return this.name;
	}
	//return arrivalTimr
	public String getArrivalTime() {
		return Integer.toString(arrivalTime);
	}
	
	//return priorityLevel
	public String getPriorityLevel() {
		return Integer.toString(priorityLevel);
	}
	
	//returns nice list of CPU bursts
	public String getCPUBursts() {
		String cpuBurstsClean = Integer.toString(this.cpuBursts.get(0));
		
		for (int i = 1; i < cpuBursts.size(); i++) {
			cpuBurstsClean += " " + Integer.toString(this.cpuBursts.get(i));
		}
		
		return cpuBurstsClean;
	}
	
	//returns nice list of IO bursts
	public String getIOBursts() {
		String ioBurstsClean = Integer.toString(this.ioBursts.get(0));
		
		for (int i = 1; i < ioBursts.size(); i++) {
			ioBurstsClean += " " + Integer.toString(this.ioBursts.get(i));
		}
		
		return ioBurstsClean;
	}
	
	//setters and getters for start, finish, wait, waitIO, and status
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getWaitIOTime() {
		return waitIOTime;
	}

	public void setWaitIOTime(int waitIOTime) {
		this.waitIOTime = waitIOTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	//returns current burst that is needed at CPU
	public int getCurrentCPUBurst() {
		return cpuBursts.get(this.tracker);
	}
	
	//returns current burst that is needed at IO
	public int getCurrentIOBuurst() {
		return ioBursts.get(tracker);
	}
	
	//call each time process is completed at IO before reentering CPU queue
	public void incrementTracker() {
		this.tracker++;
	}
	
	//toString
	@Override
	public String toString() {
		return name + " - " + arrivalTime + " - " + priorityLevel + " - CPU Bursts: " + cpuBursts + " - IO Bursts: " + ioBursts;
		
	}

}
