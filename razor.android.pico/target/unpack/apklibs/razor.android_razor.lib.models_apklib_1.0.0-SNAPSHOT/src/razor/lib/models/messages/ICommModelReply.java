package razor.lib.models.messages;
import java.util.List;


public interface ICommModelReply<T> extends ICommReply {
	 List<T> getItems();
	 T getModel();
	 int addItem(T item);
	 int size();
	 Integer getTotalCount();
}
