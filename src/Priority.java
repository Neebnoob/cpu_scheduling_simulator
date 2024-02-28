import java.util.List;

public class Priority extends Simulation{

	public Priority(List<PCB> queue) {
		super(queue);
	}

	//Picks the process with the highest priority (0 > 9)
	@Override
	public PCB pickNextProcessCPU() {
		return null;
	}

}
