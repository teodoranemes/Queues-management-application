package Model;

public class Task {

    private int ID;
    private int arrivalTime;
    private int serviceTime;
    private int completionTime;


    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void decrementServiceTime() { serviceTime--;}

    public int getWaitingTime(int currentTime) {

        if (completionTime == 0) {
            return currentTime - arrivalTime;
        } else {
            return completionTime - arrivalTime;
        }
    }

    public void setCompletionTime(int currentTime) {

        if (completionTime == 0) {

            completionTime = currentTime + serviceTime;
        }
    }


    public String toString(){
        return "(" + ID + "," + arrivalTime + "," + serviceTime + ")";
    }

}
