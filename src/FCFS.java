import java.util.List;

public class FCFS extends Simulation{

	public FCFS(List<PCB> queue) {
		super(queue);
	}

	//Picks the first process that was added to the cpuQueue
	@Override
	public int pickNextProcessCPU() {
		return 0;
	}

}
