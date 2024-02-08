package cpu_scheduling;

import java.util.ArrayList;

//processes class 3.2

public class PCB {

    // variables
    Processes currentProcess;
    String status;
    String name;

    // contructor
    public PCB(Processes currentProcess, String status, String name){
        this.currentProcess = currentProcess;
        this.status = status;
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