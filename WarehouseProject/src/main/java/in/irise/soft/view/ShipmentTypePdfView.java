package in.irise.soft.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.irise.soft.model.ShipmentType;

public class ShipmentTypePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception 
	{
		//download
		//response.addHeader("Content-Disposition", "attachment;filename=SHIPMENT.pdf");
		Font f1 = new Font(Font.HELVETICA, 30, Font.BOLD, Color.ORANGE);

		Paragraph  title = new Paragraph("SHIPMENT TYPES",f1);
		title.setAlignment(Element.ALIGN_CENTER);

		document.add(title);

		//read db data
		@SuppressWarnings("unchecked")
		List<ShipmentType> list = (List<ShipmentType>)model.get("list");

		PdfPTable table = new PdfPTable(6);//no.of columns in a rows
		table.setSpacingBefore(20.0f);
		table.setSpacingAfter(10.0f);

		table.setWidths(new float[] {0.5f,1.0f,1.0f,1.2f,1.0f,1.8f});

		setHead(table);
		setBody(table,list);
		document.add(table);
		
		Font f2 = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(235, 56, 175));
		document.add(new Paragraph("GENERATED DATE & TIME :"+new Date().toString(),f2));

	}


	private void setHead(PdfPTable table) {
		Font font = new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.BLUE);
		table.addCell(new Phrase("ID", font));
		table.addCell(new Phrase("MODE", font));
		table.addCell(new Phrase("CODE", font));
		table.addCell(new Phrase("ENABLED", font));
		table.addCell(new Phrase("GRADE", font));
		table.addCell(new Phrase("DESCRIPTION", font));

	}

	private void setBody(PdfPTable table, List<ShipmentType> list) {
		Font font = new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.BLACK);
		for(ShipmentType st:list) {
			table.addCell(new Phrase(st.getId().toString(), font));
			table.addCell(new Phrase(st.getShipmentMode(), font));
			table.addCell(new Phrase(st.getShipmentCode(), font));
			table.addCell(new Phrase(st.getEnableShipment(), font));
			table.addCell(new Phrase(st.getShipmentGrade(), font));
			table.addCell(new Phrase(st.getShipmentDescription(), font));
		}
	}
}
