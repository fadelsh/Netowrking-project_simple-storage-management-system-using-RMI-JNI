import java.rmi.*;
import java.util.Random;
import java.rmi.server.*;

public class Agent{

     static class BeaconSender implements Runnable  { 
        @Override
        public  void run() { 
            try{ 
                long startTime=System.currentTimeMillis();
                Random rand=new Random();
                int ID=rand.nextInt(10000);
     for(;;){
         String registry = "localhost"; 
         String registration = "rmi://" + registry + "/Beacon";
         Remote remoteService = Naming.lookup ( registration );
         RemoteInterface  beacon = (RemoteInterface) remoteService;
        
         long latestRecTime=System.currentTimeMillis();
         Beacon beaconToDeposit=new Beacon (ID,startTime,registration,latestRecTime,60);

        beacon.deposit(beaconToDeposit);
        Thread.sleep(60000);
         
        }      
     } catch (Exception e) { 
        System.out.println(e.getClass());
        e.printStackTrace();
            }
        }
    }

    static class CmdRegister implements Runnable  { 
        @Override
        public  void run() { 
            try{ 
                RemoteInterfaceImpl cmd=new RemoteInterfaceImpl();
                RemoteRef location=cmd.getRef();
                System.out.println(location.remoteToString());
                String registry="localhost";
 
                String registration = "rmi://" + registry + "/Agent";
                Naming.rebind( registration, cmd);
      
      
          
           
     } catch (Exception e) { 
        System.out.println("Exception in CmdRegister");
            }
        }
    }

    public static void main(String args[]) throws Exception{

        Thread BeaconSender=new Thread(new BeaconSender());
         Thread CmdRegister=new Thread(new CmdRegister());


        CmdRegister.start();
        BeaconSender.start();
}

    }