
public class Processor {
	
	//Used to execute as if it were a processor and subtract one for the processes current burst
	public static void execute(PCB process) {
		
		process.updateCurrBurst();
	}

}
