package razor.lib.comms;

public class CommsServices {

	public static final int SOAP = 1;
	public static final int REST = 2;
	public static final int TEST = 3;
	
	public static String getServiceName(int serviceType){
		String serviceName = "";
		switch(serviceType){
		case SOAP: 
			serviceName = "soap";
			break;
		case REST: 
			serviceName = "rest";
			break;
		case TEST: 
			serviceName = "test";
			break;			
		}
		return serviceName;
	}
	
}
