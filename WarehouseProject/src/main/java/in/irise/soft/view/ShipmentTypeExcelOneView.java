package in.irise.soft.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.irise.soft.model.ShipmentType;
import in.irise.soft.util.AppUtil;

public class ShipmentTypeExcelOneView extends AbstractXlsxView{
 
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception {

		response.addHeader("Content-Disposition", "attachment;filename=SHIPMENT"+AppUtil.getCurrentDateTime()+".xlsx");
		
		//read data from model
		ShipmentType st = (ShipmentType) model.get("st");
		
		//create new Sheet
		Sheet sheet = workbook.createSheet("SHIPMENT TYPE");

		//create Row
		createRows(sheet,0,"ID",st.getId().toString());
		createRows(sheet,1,"MODE",st.getShipmentMode());
		createRows(sheet,2,"CODE",st.getShipmentCode());
		createRows(sheet,3,"ENABLE",st.getEnableShipment());
		createRows(sheet,4,"GRADE",st.getShipmentGrade());
		createRows(sheet,5,"DESCRIPTION",st.getShipmentDescription());

		createRows(sheet,8,"DATE AND TIME",AppUtil.getCurrentDateTime());
		createRows(sheet,9,"MODULE NAME","SHIPMENT TYPE");
		
		
	}

	private void createRows(
			Sheet sheet,int rowNum,
			String label,String value) 
	{
		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue(label);
		row.createCell(1).setCellValue(value);

	}
	
	
}
