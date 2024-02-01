package cpu_scheduling;

//processes class

public class Processes {
	
	//variables
	String name;
	int arrivalTime;
	int priorityLevel;
	Node head;
	
	//constructor
	public Processes(String name, int arrivalTime, int priorityLevel) {
		this.name =  name;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		
		//head is a place holder for the start of our process times linked list
		//the nodes in this list will alternate starting with CPU and then I/O
		//but will always end on a CPU node
		this.head = new Node(-1);
	}

}
