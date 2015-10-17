
public class SenderCounter {
    private String from;
    private int jobListCount;
    private int otherListCount;

    public SenderCounter(String from) {

        this.from = from;
        this.jobListCount = 0;
        this.otherListCount = 0;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getJobListCount() {
        return jobListCount;
    }

    public void addJobListCount() {
        this.jobListCount ++;
    }

    public int getOtherListCount() {
        return otherListCount;
    }

    public void addOtherListCount() {
        this.otherListCount ++;
    }


}
