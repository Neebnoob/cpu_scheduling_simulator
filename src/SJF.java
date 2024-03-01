import java.util.List;

public class SJF extends Simulation{

	public SJF(List<PCB> queue) {
		super(queue);
	}

	//Picks the process with the shortest cpu burst
	@Override
	public PCB pickNextProcessCPU() {
		PCB temp = cpuQueue.get(0);
		for (int i = 1; i < cpuQueue.size(); i++) {
			if (cpuQueue.get(i).getCurrentCPUBurst() < temp.getCurrentCPUBurst()) {
				temp = cpuQueue.get(i);
			}
		}
		return temp;
	}

}
