import java.rmi.*;
import java.rmi.server.*;

public class Manager{


   static RemoteInterfaceImpl beacon;

    static class Manage implements Runnable  { 
        @Override
        public  void run() { 
      while(true){
          try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        
                  for(int i=0;i<beacon.activeAgents.size();i++){
                    long beaconLstUpdate=beacon.activeAgents.get(i).latestRecTime;
                    int interval=beacon.activeAgents.get(i).timeInterval;
                      if((System.currentTimeMillis()-beaconLstUpdate) > 1.5 *interval*1000){
                        System.out.println ("An agent with ID "+ beacon.activeAgents.get(i).ID+ " has DIED because it was not active for a time that's 2 times (2 minutes) of the timeInterval that is specified in its beacon! ");
                        beacon.activeAgents.remove(i);
                     
                        System.out.println();
                      }
                  }
                }   
    }
  }

    static class BeaconListenerRegister implements Runnable  { 
        @Override
        public  void run() { 
            try{ 
                RemoteRef location=beacon.getRef();
                System.out.println(location.remoteToString());
                System.out.println();
                String registry="localhost";
        
                String registration = "rmi://" + registry + "/Beacon";
                Naming.rebind( registration, beacon);
      
          
           
     } catch (Exception e) { 
         System.out.println(e.getClass());
        System.out.println("Exception in BeaconListenerRegister");
            }
        }
    }
    

    public static void main (String[]args) throws Exception {
    
        beacon=new RemoteInterfaceImpl();
        Thread Manage=new Thread(new Manage());
        Thread beaconListnerReg=new Thread(new BeaconListenerRegister());

         beaconListnerReg.start();
         Manage.start();

    }



}