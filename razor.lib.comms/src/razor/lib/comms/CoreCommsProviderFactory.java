package razor.lib.comms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class CoreCommsProviderFactory {

	public abstract String getCommsProviderName(int serviceType);

	/**
	 * Get a validation service.
	 * 
	 * @return The validation service.
	 */

	public ICommsProvider getCommsProvider(int serviceType) {

		String commsProvider = this.getCommsProviderName(serviceType);

		try {
			Class<?> c = Class.forName(commsProvider);
			Method m = c.getDeclaredMethod("getInstance", (Class[]) null);
			ICommsProvider comms = (ICommsProvider) m.invoke(null, (Object[]) null);
			return comms;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}
}
