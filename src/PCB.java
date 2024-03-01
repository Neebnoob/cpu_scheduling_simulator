import java.util.ArrayList;

public class PCB {
	//variables
		String name;
		int arrivalTime;
		int priorityLevel;
		ArrayList<Integer> cpuBursts;
		ArrayList<Integer> ioBursts;
		int currBurst;
		int tracker;
		int startTime;
		int finishTime;
		int turnAroundTime;
		int waitTime;
		int waitIOTime;
		int queueTime;
		String status;
		Boolean cpuFlag;
		
		//constructor
		public PCB(String name, int arrivalTime, int priorityLevel) {
			this.name =  name;
			this.arrivalTime = arrivalTime;
			this.priorityLevel = priorityLevel;
			this.cpuBursts = new ArrayList<Integer>();
			this.ioBursts = new ArrayList<Integer>();
			this.tracker = 0; //how many cycles have been completed .. correlates to burst array locations
			this.startTime = -1; //time that process first entered CPU
			this.finishTime = -1; //time that process finished
			this.turnAroundTime = 0; //finishTime - startTime
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
		
		public int getPriority() {
			return this.priorityLevel;
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
			if (this.ioBursts.size() <= 0) {
				return "";
			}
			String ioBurstsClean = Integer.toString(this.ioBursts.get(0));
			
			for (int i = 1; i < ioBursts.size(); i++) {
				ioBurstsClean += " " + Integer.toString(this.ioBursts.get(i));
			}
			
			return ioBurstsClean;
		}
		
		//setters and getters for start, finish, wait, waitIO, status, and CPUFlag
		
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
		
		public void updateTurnAroundTime() {
			this.turnAroundTime = getFinishTime() - getStartTime(); 
		}
		
		public int getTurnAroundTime() {
			return this.turnAroundTime;
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
		
		public Boolean getCPUFlag() {
			return cpuFlag;
		}
		
		public void setCPUFlag(Boolean cpuFlag) {
			this.cpuFlag = cpuFlag;
		}
		
		//returns current burst that is needed at CPU
		public int getCurrentCPUBurst() {
			return cpuBursts.get(this.tracker);
		}
		
		//returns current burst that is needed at IO
		private int getCurrentIOBurst() {
			return ioBursts.get(this.tracker);
		}
		
		public void setCurrBurst(Boolean inCPU) {
			if (inCPU) {
				this.currBurst = getCurrentCPUBurst();
			} else {
				this.currBurst = getCurrentIOBurst();
			}
		}
		
		public int getCurrBurst() {
			return this.currBurst;
		}
		
		public void updateCurrBurst() {
			this.currBurst--;
		}
		
		//call each time process is completed at IO before reentering CPU queue
		public void incrementTracker() {
			this.tracker++;
		}
		
		//checks to see if process has completed all bursts
		public Boolean isDone() {
			return this.tracker >= this.cpuBursts.size() - 1;
		}
		
		//toString
		@Override
		public String toString() {
			return name + " - " + arrivalTime + " - " + priorityLevel + " - CPU Bursts: " + cpuBursts + " - IO Bursts: " + ioBursts;
			
		}
}
