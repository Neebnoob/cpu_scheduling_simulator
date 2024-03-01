import java.util.List;

public class Priority extends Simulation{

	public Priority(List<PCB> queue) {
		super(queue);
	}

	//Picks the process with the highest priority (0 > 9)
	@Override
	public int pickNextProcessCPU() {
		int ans = 0;
		for (int i = 0; i < cpuQueue.size(); i++) {
			if (cpuQueue.get(i).getPriority() < cpuQueue.get(ans).getPriority()) {
				ans = i;
			}
		}
		return ans;
	}

}
