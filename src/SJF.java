import java.util.List;

public class SJF extends Simulation{

	public SJF(List<PCB> queue) {
		super(queue);
	}

	//Picks the process with the shortest cpu burst
	@Override
	public int pickNextProcessCPU() {
		int ans = 0;
		for (int i = 1; i < cpuQueue.size(); i++) {
			if (cpuQueue.get(i).getCurrentCPUBurst() < cpuQueue.get(ans).getCurrentCPUBurst()) {
				ans = i;
			}
		}
		return ans;
	}

}
