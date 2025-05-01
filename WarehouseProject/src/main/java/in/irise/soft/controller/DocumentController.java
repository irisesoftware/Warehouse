package in.irise.soft.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.irise.soft.model.Document;
import in.irise.soft.service.IDocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {
	
	@Autowired
	private IDocumentService service;
	
	//1. show Documents Screen
	@GetMapping("/show")
	public String showDocs(Model model) {
		List<Object[]> list = service.getDocIdAndNames();
		model.addAttribute("list", list);
		return "Documents";
	}
	
	//2. On click upload button
	@PostMapping("/upload")
	public String uploadDoc(
			@RequestParam("docId")Integer docId,
			@RequestParam("docOb")MultipartFile file
			) 
	{
		if(file!=null) {
			Document doc = new Document();
			doc.setDocId(docId);
			doc.setDocName(file.getOriginalFilename());
			try {
				doc.setDocData(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			service.saveDocument(doc);
		}
		return "redirect:show";
	}

	//3. Download
	@GetMapping("/download")
	public void downloadDoc(
			@RequestParam("id")Integer docId,
			HttpServletResponse response
			) 
	{
		Optional<Document> opt = service.getDocumentById(docId);
		if(opt.isPresent()) {
			//read object
			Document doc=opt.get();
			
			//add head section
			response.addHeader(
					"Content-Disposition", 
					"attachment;filename="+doc.getDocName());
			
			//copy data to outputstream from docData
			try {
				//from, to
				FileCopyUtils.copy(
						doc.getDocData(), 
						response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
