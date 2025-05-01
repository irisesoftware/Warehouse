package in.irise.soft.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.irise.soft.model.ShipmentType;
import in.irise.soft.util.AppUtil;

public class ShipmentTypeExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		//Modify File Name
		response.setHeader("Content-Disposition", "attachment;filename=SHIPMENTS-"+AppUtil.getCurrentDateTime()+".xlsx");
		
		//Read data from ModelAndView memory
		@SuppressWarnings("unchecked")
		List<ShipmentType> list = (List<ShipmentType>) model.get("list");
		

		//create one sheet using workbook
		Sheet sheet = workbook.createSheet("SHIPMENTS");
		
		//create one row.(1st row in excel-Row#0)
		setHead(sheet);
		setBody(sheet,list);

	}
	
	/**
	 * This is not pre-defined method.
	 * I wrote this method that indicates
	 * creating Row#0 calling as Head in Excel File
	 */
	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("MODE");
		row.createCell(2).setCellValue("CODE");
		row.createCell(3).setCellValue("ENABLE");
		row.createCell(4).setCellValue("GRADE");
		row.createCell(5).setCellValue("DESCRIPTION");
	}
	
	private void setBody(Sheet sheet, List<ShipmentType> list) {
		int rowNum = 1;
		for(ShipmentType st:list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(st.getId());
			row.createCell(1).setCellValue(st.getShipmentMode());
			row.createCell(2).setCellValue(st.getShipmentCode());
			row.createCell(3).setCellValue(st.getEnableShipment());
			row.createCell(4).setCellValue(st.getShipmentGrade());
			row.createCell(5).setCellValue(st.getShipmentDescription());
			//rowNum = rowNum + 1;
			//rowNum += 1;
			//rowNum++;
		}
	}
	
}
