package razor.lib.comms;

public interface ICommsProviderFactory {
	ICommsProvider getCommsProvider(int serviceType);
}
