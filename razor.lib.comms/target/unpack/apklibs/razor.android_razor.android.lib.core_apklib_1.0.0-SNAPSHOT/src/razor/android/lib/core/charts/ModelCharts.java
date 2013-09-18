package razor.android.lib.core.charts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/*
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import razor.android.lib.core.helpers.ChartHelper;
import razor.android.lib.core.interfaces.IChartableItem;
import razor.android.lib.core.interfaces.IChartableItem.eDataAttribute;
import razor.android.lib.core.interfaces.ICoreActivity;
import razor.android.lib.core.interfaces.IModelItem;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
*/
public class ModelCharts {
/*
	public static Intent BuildLineGraphIntent(Context context,IModelItem item, Collection<IModelItem> items) {

		ICoreActivity activity = (ICoreActivity) context;
		XYMultipleSeriesDataset dataSet = BuildDataSetFromitems(activity,items);
		XYMultipleSeriesRenderer renderer =  ChartHelper.buildRenderer(new int[]{Color.RED, Color.WHITE, Color.GREEN}, PointStyle.CIRCLE );		
		return ChartFactory.getLineChartIntent((Context) activity, dataSet , renderer , item.getTitle());

	}
	
	private static XYMultipleSeriesDataset BuildDataSetFromitems(ICoreActivity activity, Collection<IModelItem> items){
		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();		
		//addItemToDataSet(activity,dataSet,"Intensity",items,eDataAttribute.Intensity);
		return dataSet;
	}
	
	private static void addItemToDataSet(ICoreActivity activity, XYMultipleSeriesDataset dataSet, String title, Collection<IModelItem> items, eDataAttribute dataAttribute){
		List<Double> xValues = new ArrayList<Double>();
		List<Double> yValues = new ArrayList<Double>();

		for(IModelItem item: items){
			// a chartable item is also a model item
			IChartableItem chartItem = (IChartableItem)item;
			// get workout exercises parent workout
			//ModelBuilder.BuildConnectedModelItems(activity, item);
			// add intensity for each date
			//xValues.add( (double)DateHelper.DaysSince(we.Workout.WorkoutDate) );			
			xValues.add( (double)xValues.size() + 1 );
			yValues.add( chartItem.getChartableValue(dataAttribute) );
		}		
		dataSet.addSeries( ChartHelper.buildSeries(title, xValues, yValues));
	}
*/
}
