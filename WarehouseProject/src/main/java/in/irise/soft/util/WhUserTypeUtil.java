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
public class WhUserTypeUtil {

	public void generatePieChart(String path,List<Object[]> list) {

		//1. Create Dataset and fill values
		DefaultPieDataset dataset = new DefaultPieDataset();

		for(Object[] ob:list) {
			//add data key(String), value(number)
			dataset.setValue( 
					String.valueOf(ob[0]), 
					Double.valueOf(String.valueOf(ob[1]))
					);
		}


		//2. Use ChartFactory and create JFreeChart Object
		//title, dataset--> JFreeChart
		JFreeChart chart = ChartFactory.createPieChart("Warehouse Users", dataset);

		//3. Convert JFreeChart object to Image Format
		try {
			ChartUtils.saveChartAsPNG(new File(path+"/whUserTypeA.png"), chart, 400, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void generateBarChart(String path,List<Object[]> list) {
		//1. Create Dataset and fill values
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for(Object[] ob:list) {
			//value(Number), Key(String), label(empty)
			dataset.setValue(
					Double.valueOf(ob[1].toString()), 
					ob[0].toString(), 
					""
					);
		}
		//2. Use ChartFactory and create JFreeChart Object
		//title, x-axis, y-axis, dataset
		JFreeChart chart = ChartFactory.createBarChart("Warehouse Users", "User Types", "COUNT", dataset);
		
		//3. Convert JFreeChart object to Image Format
		try {
			ChartUtils.saveChartAsPNG(new File(path+"/whUserTypeB.png"), chart, 600, 500);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
