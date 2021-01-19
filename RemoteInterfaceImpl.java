import java.rmi.*;
import java.util.ArrayList;
public class RemoteInterfaceImpl extends java.rmi.server.UnicastRemoteObject implements RemoteInterface {


 static {
        System.loadLibrary("hello"); 
                                     
                                  
     }
   
     //private native void sayHello();
     private native String CppLocalTime(String text);
     private native String CppLocalOS(String text);


    ArrayList <Beacon> activeAgents=new ArrayList<Beacon>();
    private static final long serialVersionUID=1L;

    protected RemoteInterfaceImpl () throws RemoteException{
        super();
    }

   public void deposit (Beacon b) throws RemoteException{
       long t=System.currentTimeMillis();

        if(isIDInList(this.activeAgents, b.ID)==false){
          this.activeAgents.add(b);
          System.out.println("A new agent has joined"); 

        Thread clientAgent=new Thread(new clientAgent()); 
          clientAgent.start();
    }
    else{
        int index=findIdx(this.activeAgents, b.ID);
        this.activeAgents.get(index).latestRecTime=t;


    }
    System.out.println("Recieved a beacon with the following information [ID,StartUpTime,CmdAgentID]:[ "+ b.ID +","+b.StartUpTime+","+b.CmdAgentID+"]");
    System.out.println();

}

public String execute(String CmdID) throws Exception{

    if(CmdID.equals("GetLocalTime")){
       String time=new RemoteInterfaceImpl().CppLocalTime("GetLocalTime");
        return time;
    }
    else if(CmdID.equals("GetLocalOS")){
        String OS=new RemoteInterfaceImpl().CppLocalOS("GetLocalOS");
        return OS;
    }
   
    return "Not Valid Command";
 }



static class clientAgent implements Runnable  { 
    @Override
    public  void run() { 
        try{ 
            Thread.sleep(200);
            String registry = "localhost"; 
            String registration = "rmi://" + registry + "/Agent";
            Remote remoteService = Naming.lookup ( registration );
            RemoteInterface cmd = (RemoteInterface) remoteService;
            String cPPtime=cmd.execute("GetLocalTime");
            String cPPOS=cmd.execute("GetLocalOS");

            System.out.println("Local Time for this new agent is: "+ cPPtime);
            System.out.println("Local OS for this new agent is: "+ cPPOS);
            System.out.println();

     
    } catch (Exception e) { 
    System.out.println("OK "+ e.getClass());
    e.printStackTrace();
        }
    }
}



public int findIdx (ArrayList <Beacon> lst, int targetID){
    int i;
    for (i=0;  i<lst.size(); i++){
        if(lst.get(i).ID==targetID){
            break;
        }
    }
    return i;
}

    public boolean isIDInList(ArrayList <Beacon> lst, int id){
       for(int i=0; i<lst.size();i++){
           if(lst.get(i).ID ==id){
               return true;
           }
       }
       return false;

    }
}