import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
	
	//variables
	protected List<PCB> allProcs; // the initial list of processes
	protected List<PCB> cpuQueue; // ready queue of ready CPU processes
	protected List<PCB> ioQueue; // ready queue of ready IO processes
	protected List<PCB> finishedProcs; // list of terminated processes
	protected PCB currCPUProcess; // current selected process by the CPU scheduler
	protected PCB currIOProcess; // current selected process by the IO scheduler
	protected int systemTime; // system time or simulation time steps

	//constructor
	public Simulation(List<PCB> queue) {
		this.allProcs = queue;
		this.cpuQueue = new ArrayList<>();
		this.ioQueue = new ArrayList<>();
		this.finishedProcs = new ArrayList<>();
		this.systemTime = 0;
	}

	//default constructor
	public Simulation() {
	}

	//1 cycle or 1 system time
	public void schedule() {

		System.out.println("Current system time: " + systemTime);

		//checks through all processes that have not happened and adds those to queue that have arrived now
		for (int i = 0; i < allProcs.size(); i++) {
			if (Integer.parseInt(allProcs.get(i).getArrivalTime()) == systemTime) {
				PCB temp = allProcs.get(i);
				temp.setStatus("Arrived");
				cpuQueue.add(temp);
				allProcs.remove(i);
				i--;
			}
		}

		//if queue is not empty and no process at CPU 
		//add a process from queue to CPU
		if (!cpuQueue.isEmpty() && this.currCPUProcess == null) {
			changeProcess();
			newProcess();
		}
		
		//if there is a process at CPU
		if (currCPUProcess != null) {
			
			//check to see if it has finished
			if (currCPUProcess.getCurrBurst() == 0) {
				//check to see if it has completed all its bursts
				//if so update its info and move it into finished list
				if (currCPUProcess.isDone()) {
					currCPUProcess.setFinishTime(systemTime);
					currCPUProcess.setStatus("Finished");
					finishedProcs.add(currCPUProcess);
					//check to see if CPU queue is empty
					changeProcess();
				//if not done add the process to the IO queue and change its burst mode
				} else {
					ioQueue.add(currCPUProcess);
					currCPUProcess.setCurrBurst(false);
					//check to see if CPU queue is empty
					changeProcess();
				}
			//if process is not done execute and take 1 from its current burst
			} else {
				Processor.execute(currCPUProcess);
			}
		}
		
		//if processor is empty but queue has items add an item from queue into processor
		if (!ioQueue.isEmpty() && this.currIOProcess == null) {
			currIOProcess = ioQueue.get(0);
			ioQueue.remove(0);
		}
		
		//if processor has an item then
		if (currIOProcess != null) {

			//check to see if current burst is 0
			//if so move it back into the CPU queue and increment its burst tracker to know what burst it is on
			if (currIOProcess.getCurrBurst() == 0) {
				cpuQueue.add(currIOProcess);
				currIOProcess.incrementTracker();
				currIOProcess.setCurrBurst(true);
				//if queue is empty do nothing
				if (ioQueue.isEmpty()) {
					currIOProcess = null;
					//else take an item from queue and add to Processor
				} else {
					currIOProcess = ioQueue.get(0);
					ioQueue.remove(0);
					Processor.execute(currIOProcess);
					System.out.println("Current IO Process: " + currIOProcess);
				}
			//if item has burst time left execute and subtract one form time
			} else {
				Processor.execute(currIOProcess);
			}
		}
		
		//update the wait time of all processes in CPU queue
		if (!cpuQueue.isEmpty()) {
			updateCPUWaitTime();
		}

		//update the wait time of all processes in IO queue
		if (!ioQueue.isEmpty()) {
			updateIOWaitTime();
		}
		//increase system time by 1
		systemTime++;
		printTable();
		System.out.println();
		//prints our processor and queue visual
		visual();
		System.out.println();
	}
	
	//When a process is finished and has to go on to IO
	private void changeProcess() {
		//check to see if CPU queue is empty
		if (cpuQueue.isEmpty()) {
			currCPUProcess = null;
		//if not move it into CPU and check to see if it is new
		} else {
			int remove = pickNextProcessCPU();
			currCPUProcess = cpuQueue.get(remove);
			newProcess();
			cpuQueue.remove(remove);
			Processor.execute(currCPUProcess);
		}
	}
	
	//check to see if a process is new
	//if so update its info
	private void newProcess() {
		if (currCPUProcess.getStartTime() == -1) {
			currCPUProcess.setStatus("Started");
			currCPUProcess.setStartTime(systemTime);
			currCPUProcess.setCurrBurst(true);
		}
	}

	//Prints our table
	public void printTable() {

		//combines all lists and processes stored in CPU/IO into one list to sort
		ArrayList<PCB> temp = new ArrayList<PCB>();
		temp.addAll(this.allProcs);
		temp.addAll(cpuQueue);
		temp.addAll(finishedProcs);
		temp.addAll(ioQueue);
		if (this.currCPUProcess != null) {
			temp.add(currCPUProcess);
		}
		if (this.currIOProcess != null) {
			temp.add(currIOProcess);
		}
		Console.quickSort(temp, 0, temp.size() - 1);

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"|id\t|arrival\t|priority\t|cpu bursts\t|io bursts\t|start time\t|finish time\t|wait time\t|wait io time\t|status\t\t|");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");

		//for each item in list nicely prints out their information
		for (int i = 0; i < temp.size(); i++) {
			System.out.println("|" + temp.get(i).getName() + "\t|" + temp.get(i).getArrivalTime() + "\t\t|"
					+ temp.get(i).getPriorityLevel() + "\t\t|" + temp.get(i).getCPUBursts() + "\t\t|"
					+ temp.get(i).getIOBursts() + "\t\t|" + temp.get(i).getStartTime() + "\t\t|"
					+ temp.get(i).getFinishTime() + "\t\t|" + temp.get(i).getWaitTime() + "\t\t|"
					+ temp.get(i).getWaitIOTime() + "\t\t|" + temp.get(i).getStatus() + "\t|");
		}

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");

	}

	//called in GUI to see if the while loop that runs schedule needs to run again
	public Boolean scheduleDone() {
		return allProcs.isEmpty() && cpuQueue.isEmpty() && ioQueue.isEmpty() && currCPUProcess == null && currIOProcess == null;
	}

	public abstract int pickNextProcessCPU();

	//returns first process added to IO queue
	public PCB pickNextProcessIO() {
		return ioQueue.get(0);
	}

	//increase wait time by 1 for all processes in CPU queue
	private void updateCPUWaitTime() {
		for (int i = 0; i < cpuQueue.size(); i++) {
			cpuQueue.get(i).setWaitTime(cpuQueue.get(i).getWaitTime() + 1);
		}
	}
	
	//Calls on PCB function and updates wait IO time
	private void updateIOWaitTime() {
		for (int i = 0; i < ioQueue.size(); i++) {
			ioQueue.get(i).setWaitIOTime(ioQueue.get(i).getWaitIOTime() + 1);
		}
	}
	
	//Prints out our visual for the CPU, CPU queue, IO, and IO queue
	private void visual() {
		//IO and CPU generic idle text
		String cpuStat = "Idle";
		String ioStat = "Idle";
		//Checks to see if they have a process.  If so the text status is updated
		if (currCPUProcess != null) {
			cpuStat = currCPUProcess.getName();
		}
		if (currIOProcess != null) {
			ioStat = currIOProcess.getName();
		}
		//Alternates between CPU and CPU queue
		//CPU queue prints have logic involved as the size of them can vary
		System.out.print("---CPU-----------");
		System.out.print("---CPU-QUEUE----");
		//prints out extra space for each additional item in the queue above 2
		if (!cpuQueue.isEmpty()) {
			for (int i = 2; i < cpuQueue.size(); i++) {
				System.out.print("--------");
			}
			System.out.print("\n");
		} else {
			System.out.print("\n");
		}
		//Print CPU status
		System.out.print("|" + cpuStat + "\t\t|");
		//If queue is empty print default empty message
		if (cpuQueue.isEmpty()) {
			System.out.print("Empty" + "\t\t|\n");
		} else {
			for (int i = 0; i < cpuQueue.size(); i++) {
				System.out.print(cpuQueue.get(i).getName() + "\t");
			}
			if (cpuQueue.size() == 1)
				System.out.print("\t");
			System.out.print("|\n");
		}
		//Prints bottom part of boxes
		System.out.print("-----------------");
		System.out.print("----------------");
		//Prints additional bottom parts for queue box if needed
		if (!cpuQueue.isEmpty()) {
			for (int i = 2; i < cpuQueue.size(); i++) {
				System.out.print("--------");
			}
		} else {
			System.out.print("\n");
		}
		
		System.out.print("\n");
		
		//Alternates between IO and IO queue
		//IO queue prints have logic involved as the size of them can vary
		System.out.print("---IO------------");
		System.out.print("---IO-QUEUE-----");
		//Prints out extra space for each additional item in the queue above 2
		if (!ioQueue.isEmpty()) {
			for (int i = 2; i < ioQueue.size(); i++) {
				System.out.print("--------");
			}
			System.out.print("\n");
		} else {
			System.out.print("\n");
		}
		//Print IO status
		System.out.print("|" + ioStat + "\t\t|");
		//If queue is empty print default empty message
		if (ioQueue.isEmpty()) {
			System.out.print("Empty" + "\t\t|\n");
		} else {
			for (int i = 0; i < ioQueue.size(); i++) {
				System.out.print(ioQueue.get(i).getName() + "\t");
			}
			if (ioQueue.size() == 1)
				System.out.print("\t");
			System.out.print("|\n");
		}
		//Prints bottom part of boxes
		System.out.print("-----------------");
		System.out.print("----------------");
		//Prints additional bottom parts for queue box if needed
		if (!ioQueue.isEmpty()) {
			for (int i = 2; i < ioQueue.size(); i++) {
				System.out.print("--------");
			}
		} else {
			System.out.print("\n");
		}
	}
	
	//prints result for average wait time and turn around time
	public void getResults() {
		int turnAround = 0;
		int waitTime = 0;
		for (int i = 0; i < finishedProcs.size(); i++) {
			PCB temp = finishedProcs.get(i);
			turnAround += temp.getFinishTime() - Integer.parseInt(temp.getArrivalTime());
			waitTime += temp.getWaitTime();
		}
		
		System.out.println("Average turn around time: " + (double)(turnAround / finishedProcs.size()));
		System.out.println("Average wait time: " + (double)(waitTime / finishedProcs.size()));
	}

}
