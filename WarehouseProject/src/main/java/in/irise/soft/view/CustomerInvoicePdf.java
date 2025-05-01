package in.irise.soft.view;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.irise.soft.model.SaleDtl;
import in.irise.soft.model.SaleOrder;

public class CustomerInvoicePdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {


		//read po object from model
		SaleOrder so = (SaleOrder) model.get("so");	
		response.addHeader("Content-Disposition", "attachment;filename=SO-"+so.getOrderCode()+".pdf");

		Font font = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);
		Paragraph p = new Paragraph("CUSTOMER INVOICE CODE : "+so.getCustomer().getUserCode()+" - "+so.getId(),font);
		p.setAlignment(Element.ALIGN_CENTER);

		//add element to document
		document.add(p);
		
		List<SaleDtl> dtls = so.getDtls();

		double finalCost = 
				dtls.stream()
				.mapToDouble(
						ob->ob.getQty()*ob.getPart().getPartBaseCost())
				.sum();

		PdfPTable table = new PdfPTable(4);

		table.addCell("CUSTOMER CODE");
		table.addCell(so.getCustomer().getUserCode());
		table.addCell("ORDER CODE");
		table.addCell(so.getOrderCode());

		table.addCell("FINAL COST");
		table.addCell(String.valueOf(finalCost));
		table.addCell("SHIPMENT TYPE");
		table.addCell(so.getShipmentCode().getShipmentCode());

		document.add(new Paragraph("HEADER DETAILS"));
		document.add(table);
		document.add(new Paragraph("ITEM DETAILS"));
		PdfPTable items = new PdfPTable(4);
		items.addCell("PART CODE");
		items.addCell("BASE COST");
		items.addCell("QTY");
		items.addCell("LINE TOTAL");

		for(SaleDtl dtl:dtls) {
			items.addCell(dtl.getPart().getPartCode());
			items.addCell(dtl.getPart().getPartBaseCost().toString());
			items.addCell(dtl.getQty().toString());
			items.addCell(String.valueOf(
					dtl.getPart().getPartBaseCost()
					*dtl.getQty()
					)
					);
		}
		document.add(items);
		LocalDateTime dateTime=LocalDateTime.now();
		Integer day =dateTime.getDayOfMonth();
		Integer month= dateTime.getMonthValue();
		Integer year= dateTime.getYear();
		Integer hour= dateTime.getHour();
		Integer minute= dateTime.getMinute();
		Integer second= dateTime.getSecond();
		
		
		
		document.add(new Paragraph("Generated on: "+day+"/"+month+"/"+year+" "+ hour+":"+minute+":"+second));
	}

}
