package razor.android.pico.providers;

import razor.lib.comms.CommsServices;
import razor.lib.comms.ICommsProvider;
import razor.lib.comms.ICommsProviderFactory;
import razor.lib.comms.pico.factories.PicoCommsProviderFactory;

public class PicoProvider {

	private static ICommsProvider commsProvider;

	public static ICommsProvider instance(){
		if(commsProvider == null){
		    ICommsProviderFactory factory =  new PicoCommsProviderFactory();
		    commsProvider = factory.getCommsProvider(CommsServices.REST);
		}	
		return commsProvider;
	}
}
