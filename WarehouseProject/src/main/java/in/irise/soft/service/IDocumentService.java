package in.irise.soft.service;

import java.util.List;
import java.util.Optional;

import in.irise.soft.model.Document;

public interface IDocumentService {

	public void saveDocument(Document doc);
	public List<Object[]> getDocIdAndNames();
	public Optional<Document> getDocumentById(Integer id);
}
