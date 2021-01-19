import java.rmi.*;
import java.io.Serializable;

public class Beacon implements Serializable{
    
    private static final long serialVersionUID = 20120731125400L;
    public int ID;
    public long StartUpTime;
    public String CmdAgentID;
    public long latestRecTime;
    public int timeInterval;

    public Beacon(int ID,long StartUpTime,String CmdAgentID, long latestRecTime, int timeInterval){
        this.ID=ID;
        this.StartUpTime=StartUpTime;
        this.CmdAgentID=CmdAgentID;
        this.latestRecTime=latestRecTime;
        this.timeInterval=timeInterval;
    }
}