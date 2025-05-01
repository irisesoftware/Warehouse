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

import in.irise.soft.model.PurchaseDtl;
import in.irise.soft.model.PurchaseOrder;

public class VendorInvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception 
	{
		//set file name
		response.addHeader("Content-Disposition", "attachment;filename=VEN-INV.pdf");

		//read data
		PurchaseOrder po = (PurchaseOrder) model.get("po");
		List<PurchaseDtl> poDtls = (List<PurchaseDtl>) model.get("poDtls");
		
		double finalCost =0.0;
		/*for(PurchaseDtl dtl:poDtls) {
			finalCost += (dtl.getQty()* dtl.getPart().getPartBaseCost());
		}*/
		finalCost = 
				poDtls
				.stream()
				.mapToDouble(
						dtl->
						dtl.getQty()* dtl.getPart().getPartBaseCost()
						)
				.sum();
				
				
		//font = Font Family + size + Font Style + color
		Font titleFont = new Font(Font.HELVETICA, 40, Font.BOLD, Color.BLUE);
		
		// Paragraph = text + font
		Paragraph p = new Paragraph("VENDOR INVOICE PDF", titleFont);
		p.setAlignment(Element.ALIGN_CENTER);
		
		document.add(p);
		
		Font hFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.ORANGE);
		PdfPTable htable = new PdfPTable(4);
		
		p.setAlignment(Element.ALIGN_CENTER);
		//add space before the current table
		htable.setSpacingBefore(20.0f);
		//add after before the current table
		htable.setSpacingAfter(15.0f);
		
		htable.addCell("VENDOR CODE");
		htable.addCell(new Phrase(po.getVendor().getUserCode(),hFont));
		
		htable.addCell("ORDER CODE");
		htable.addCell(new Phrase(po.getOrderCode(),hFont));
		
		//Phrase = small text + font
		htable.addCell("FINAL COST");
		htable.addCell(new Phrase(String.valueOf(finalCost),hFont));
		
		htable.addCell("SHIPMENT CODE");
		htable.addCell(new Phrase(po.getShipment().getShipmentCode(),hFont));
		
		document.add(htable);
		
		Font dFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.MAGENTA);
		
		PdfPTable dtable = new PdfPTable(4);
		// pixel sizes for a table column
		dtable.setWidths(new float[] {3.5f,1.0f,1.0f,1.5f});
		dtable.setSpacingAfter(20.0f);
		
		dtable.addCell(new Phrase("CODE",dFont));
		dtable.addCell(new Phrase("BASE COST",dFont));
		dtable.addCell(new Phrase("QTY",dFont));
		dtable.addCell(new Phrase("TOTAL",dFont));
		
		for(PurchaseDtl dtl:poDtls) {
			dtable.addCell(dtl.getPart().getPartCode());
			//cell needs always string data -> double to String
			dtable.addCell(String.valueOf(dtl.getPart().getPartBaseCost()));
			dtable.addCell(String.valueOf(dtl.getQty()));
			dtable.addCell(
					String.valueOf(
					dtl.getPart().getPartBaseCost()
					*
					dtl.getQty())
					);
		}
		document.add(dtable);
		document.add(new Paragraph(new Date().toString()));
		
	}
}
