package razor.lib.comms.pico.factories;

import razor.lib.comms.CommsServices;
import razor.lib.comms.CoreCommsProviderFactory;
import razor.lib.comms.ICommsProviderFactory;

public class PicoCommsProviderFactory extends CoreCommsProviderFactory implements ICommsProviderFactory {

	@Override
	public String getCommsProviderName(int serviceType) {
		return "razor.lib.comms.pico." + CommsServices.getServiceName(serviceType) + ".CommsProvider";
	}
}
