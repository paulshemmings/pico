package razor.android.lib.core.interfaces;

public interface IChartableItem {
	public enum eDataAttribute { Intensity, Repetitions, Duration };
	public Double getChartableValue(eDataAttribute attribute);
}
