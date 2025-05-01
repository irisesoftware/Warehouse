package in.irise.soft.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTypeUtil {

	public void generatePieChart(
			String path,List<Object[]> list)
	{
		// a. Create DataSet using List<Object[]>
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(Object[] ob:list) {
			dataset.setValue(
					ob[0].toString(), //key-String
					Double.valueOf(ob[1].toString()) //value-Double
					);
		}

		// b. Create JFreeChart object using ChartFactory
		//JFreeChart chart = ChartFactory.createPieChart("SHIPMENT MODES", dataset);
		JFreeChart chart = ChartFactory.createPieChart3D("SHIPMENT MODES", dataset);

		// c. Convert to Image Format using ChartUtils.
		try {
			ChartUtils.saveChartAsPNG(new File(path+"/shipmentA.png"), chart, 400, 250);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void generateBarChart(	
			String path,List<Object[]> list)
	{
		//  a. Create DataSet using List<Object[]>
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] ob:list ) {
			dataset.setValue(
					Double.valueOf(ob[1].toString()), //value
					ob[0].toString(),  //key
					""); //label
		}
		//  b. Create JFreeChart object using ChartFactory
		JFreeChart chart = ChartFactory.createBarChart("SHIPMENT MODE", "MODES", "COUNT", dataset);
		// c. Convert to Image Format using ChartUtils.
		try {
			ChartUtils.saveChartAsPNG(new File(path+"/shipmentB.png"), chart, 400, 450);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
