package cpu_scheduling;

//processes class 3.2

public class PCB {

    // variables
    Processes currentProcess;
    String status;
    String name;

    // contructor
    public PCB(String name){
        this.currentProcess = null;
        this.status = "Available";
        this.name = name;
    }

    // getters and setters
    public Processes getProcess() {
        return currentProcess;
    }

    public void setProcess(Processes process) {
        this.currentProcess = process;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}