import java.util.List;

public class SJF extends Simulation{

	public SJF(List<PCB> queue) {
		super(queue);
	}

	//Picks the process with the shortest cpu burst
	@Override
	public PCB pickNextProcessCPU() {
		return null;
	}

}
