package razor.android.lib.core.interfaces;

public interface ICurrentDatabase {
	IModelProvider getProvider(Integer modelType);	
	void close();
}
