package in.irise.soft.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.irise.soft.model.WhUserType;

public class WhUserTypePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception
	{
		
		//provide file name for excel
		response.addHeader("Content-Disposition", "attachment;filename=WhUserTypes.pdf");

		@SuppressWarnings("unchecked")
		List<WhUserType> list = (List<WhUserType>) model.get("list");
		
		// create element
		Paragraph title = new Paragraph("WAREHOUSE USERS");

		PdfPTable table = new PdfPTable(6);
		table.addCell("USER ID");
		table.addCell("USER TYPE");
		table.addCell("USER CODE");
		table.addCell("USER FOR");
		table.addCell("EMAIL");
		table.addCell("CONTACT");
		
		for(WhUserType wh:list) {
			table.addCell(String.valueOf(wh.getId()));
			table.addCell(wh.getUserType());
			table.addCell(wh.getUserCode());
			table.addCell(wh.getUserFor());
			table.addCell(wh.getUserEmail());
			table.addCell(wh.getUserContact());
		}
		
		// add element to document
		document.add(title);
		document.add(table);
	}
}
