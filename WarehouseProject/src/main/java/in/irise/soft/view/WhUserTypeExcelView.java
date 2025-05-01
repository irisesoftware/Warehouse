package in.irise.soft.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import in.irise.soft.model.WhUserType;

public class WhUserTypeExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		
		//provide file name for excel
		response.addHeader("Content-Disposition", "attachment;filename=WhUserTypes.xlsx");

		//read data from controller
		@SuppressWarnings("unchecked")
		List<WhUserType> list =  (List<WhUserType>) model.get("list");
		
		//create new sheet
		Sheet sheet = workbook.createSheet("WH USERS LIST");
		
		setHead(sheet);
		setBody(sheet,list);
		
	}
	//Row#0 creation with labels
	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("USER TYPE");
		row.createCell(2).setCellValue("USER CODE");
		row.createCell(3).setCellValue("USER FOR");
		row.createCell(4).setCellValue("USER EMAIL");
		row.createCell(5).setCellValue("USER CONTACT");
		
	}
	//Row#1 onwards actual data..
	private void setBody(Sheet sheet, List<WhUserType> list) {
		int rownum = 1;
		for(WhUserType wh:list) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(wh.getId());
			row.createCell(1).setCellValue(wh.getUserType());
			row.createCell(2).setCellValue(wh.getUserCode());
			row.createCell(3).setCellValue(wh.getUserFor());
			row.createCell(4).setCellValue(wh.getUserEmail());
			row.createCell(5).setCellValue(wh.getUserContact());
		}
	}
}
