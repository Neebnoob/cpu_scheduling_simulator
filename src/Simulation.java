import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
	protected List<PCB> allProcs; // the initial list of processes
	protected List<PCB> cpuQueue; // ready queue of ready CPU processes
	protected List<PCB> ioQueue; // ready queue of ready IO processes
	protected List<PCB> finishedProcs; // list of terminated processes
	protected PCB curCPUProcess; // current selected process by the CPU scheduler
	protected PCB curIOProcess; // current selected process by the IO scheduler
	protected int systemTime; // system time or simulation time steps

	public Simulation(List<PCB> queue) {
		this.allProcs = queue;
		this.cpuQueue = new ArrayList<>();
		this.ioQueue = new ArrayList<>();
		this.finishedProcs = new ArrayList<>();
		this.systemTime = 0;
	}
	
	public Simulation() {
	}

	public void schedule() {

		// - while (allProcs is not empty or readyQueue is not empty) {
		// - Print the current system time -> Update system time in UI
		System.out.println("Current system time: " + systemTime);
		// - Move arrived processes from allProcs to readyQueue (arrivalTime =
		// systemTime)
		for (int i = 0; i < allProcs.size(); i++) {
			if (Integer.parseInt(allProcs.get(i).getArrivalTime()) == systemTime) {
				cpuQueue.add(allProcs.get(i));
				allProcs.remove(i);
			} else {
				break;
			}
		}
		if (!cpuQueue.isEmpty()) {
			// - curProcess = pickNextProcess() //call pickNextProcess() to choose next
			// process
			PCB currCPUProcess = pickNextProcessCPU();
			System.out.println(currCPUProcess);
			// - call print() to print simulation events: CPU, ready queue, ..
			// - update the start time of the selected process (curProcess)
			if (currCPUProcess.getStartTime() == -1) {
				currCPUProcess.setStartTime(systemTime);
				currCPUProcess.setCurrBurst(true);
			}
			// - Call CPU.execute() to let the CPU execute 1 CPU burst unit time of
			// curProcess
			// Finish the execute function in the class Processor
			Processor.execute(currCPUProcess);
			// - Increase 1 to the waiting time of other processes in the CPU ready queue
			updateCPUWaitTime();
			System.out.println(currCPUProcess.getCurrBurst());
			if (currCPUProcess.getCurrBurst() == 0) {
				System.out.println("This hits");
				if (currCPUProcess.isDone()) {
					currCPUProcess.setFinishTime(systemTime + 1);
					System.out.println(curCPUProcess);
					cpuQueue.remove(0);
				} else {
					ioQueue.add(currCPUProcess);
					currCPUProcess.setCurrBurst(false);
					cpuQueue.remove(0);
				}
			}
		}
		// - Do the above but for the IO
		// - Select cur IO process
		if (!ioQueue.isEmpty()) {
			PCB currIOProcess = pickNextProcessIO();
			// - Call .execute on currIOProcess
			Processor.execute(currIOProcess);
			// - Increase 1 to the wait time of other processes in the IO ready queue
			updateIOWaitTime();

			if (currIOProcess.getCurrBurst() == 0) {
				cpuQueue.add(currIOProcess);
				currIOProcess.incrementTracker();
				currIOProcess.setCurrBurst(true);
				ioQueue.remove(0);
			}
		}
		// - Increase systemTime by 1
		systemTime++;
		// - Check if the remaining CPU burst of curProcess = 0
		// - Update finishTime of curProcess
		// - remove curProcess from readyQueue
		// - add curProcess to the finished queue (finishedProcs)
		// - Print to console a message displaying process name, terminated time,
		// startTime, turnaroundTime, waitingTime
		// - Print a new line
		System.out.println();
	}

	// for sanity check prints what happened
	public void print() {
		// ToDo
	}

	// called in GUI to see if the while loop that runs schedule needs to run again
	public Boolean scheduleDone() {
		return allProcs.isEmpty() && cpuQueue.isEmpty() && ioQueue.isEmpty();
	}

	public abstract PCB pickNextProcessCPU();

	// returns first process added to IO queue
	public PCB pickNextProcessIO() {
		return ioQueue.get(0);
	}

	// increase wait time by 1 for all processes in CPU queue
	private void updateCPUWaitTime() {
		for (int i = 1; i < cpuQueue.size(); i++) {
			cpuQueue.get(i).setWaitTime(cpuQueue.get(i).getWaitTime() + 1);
		}
	}

	private void updateIOWaitTime() {
		for (int i = 1; i < ioQueue.size(); i++) {
			ioQueue.get(i).setWaitTime(ioQueue.get(i).getWaitTime() + 1);
		}
	}

}
