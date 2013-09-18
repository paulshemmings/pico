package razor.android.lib.core.helpers;

/*
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
*/

public class ChartHelper {
/*
	
	public Intent BuildLineGraphIntent(Context context,XYMultipleSeriesDataset multiDataset,XYMultipleSeriesRenderer multiRenderer) {	
		return ChartFactory.getLineChartIntent(context, multiDataset , multiRenderer , "");
	}
	
	public GraphicalView BuildLineGraphView(Context context,XYMultipleSeriesDataset multiDataset,XYMultipleSeriesRenderer multiRenderer) {	
		return ChartFactory.getLineChartView(context, multiDataset , multiRenderer);
	}	
	
	public Intent BuildExamplePieChart(Context context) {
		int[] colors = new int[] { Color.RED, Color.YELLOW, Color.BLUE };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		 
		CategorySeries categorySeries = new CategorySeries("Vehicles Chart");
		categorySeries.add("cars ", 30);
		categorySeries.add("trucks", 20);
		categorySeries.add("bikes ", 60);
		
		return ChartFactory.getPieChartIntent(context, categorySeries, renderer,"huh");
	}
		 
	public static DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}
	
	public static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    int length = colors.length;
	    for (int i = 0; i < length; i++) {
	        renderer.addSeriesRenderer( buildRenderer(colors[i],styles[i]) );
	    }
	    return renderer;
	}	
	
	public static XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues,List<double[]> yValues) 
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) 
		{
			XYSeries series = ChartHelper.buildSeries(titles[i], xValues.get(i), yValues.get(i));
			dataset.addSeries(series);
		}
		return dataset;
	}	
	
	public static XYMultipleSeriesDataset buildDataset(String title, List<Double> xValues, List<Double> yValues) 
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(ChartHelper.buildSeries(title, xValues, yValues));
		return dataset;
	}
	
	public static XYMultipleSeriesDataset buildDataset(String title, double[] xValues, double[] yValues) 
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(ChartHelper.buildSeries(title, xValues, yValues));
		return dataset;
	}
	
	public static XYSeries buildSeries( String title, List<Double> xValues, List<Double> yValues) {
		XYSeries series = new XYSeries(title);
		int seriesLength = xValues.size();
		for (int k = 0; k < seriesLength; k++) {
			series.add( xValues.get(k), yValues.get(k) );
		}
		return series;
	}	
	
	public static XYSeries buildSeries( String title, double[] xValues, double[] yValues) {
		XYSeries series = new XYSeries(title);
		int seriesLength = xValues.length;
		for (int k = 0; k < seriesLength; k++) {
			series.add( xValues[k], yValues[k] );
		}
		return series;
	}
	
	public static XYSeriesRenderer buildRenderer(int color, PointStyle style){
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(color);
        r.setPointStyle(style);
        return r;
	}	
	
	public static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle style){
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		for (int k = 0; k < colors.length; k++) {
			renderer.addSeriesRenderer( buildRenderer(colors[k],style));
		}
        return renderer;
	}
*/
}

