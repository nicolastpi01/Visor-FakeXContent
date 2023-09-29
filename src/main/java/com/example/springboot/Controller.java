package com.example.springboot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	public static class Pojo {
	    private String documentId;
	    
	    public Pojo() {}

		public String getDocumentId() {
			return documentId;
		}

		public void setDocumentId(String documentId) {
			this.documentId = documentId;
		}
	}
	
	public static class Pojo2 implements Serializable {
		
		private static final long serialVersionUID = -3587309694671743678L;
		private String targetArray;
		private String imageId;
		
		public Pojo2() {}

		public String getTargetArray() {
			return targetArray;
		}

		public void setTargetArray(String targetArray) {
			this.targetArray = targetArray;
		}

		public String getImageId() {
			return imageId;
		}

		public void setImageId(String imageId) {
			this.imageId = imageId;
		}
	}

	@PostMapping(path= "/example", produces = "application/json")
	public String index(@RequestBody Pojo pojo) throws IOException {
		
		System.out.println("Document Id" + pojo.getDocumentId());
		// Traigo una imagen/documento cualquiera de algun path
		File file = new File("C:\\Users\\nicol\\OneDrive\\Desktop\\Ave.jpg"); // Simulo que traigo de db la imagen
		// Traigo en annotationFile de algun lado
		File annotationFile = new File("C:\\Users\\nicol\\OneDrive\\Desktop\\myFile.txt"); // Simulo que traigo de db las anotaciones
		// Genero el JSON object
		JSONObject json = new JSONObject();
		String documentByteArray = fileToByteArray(file);
		String annotationByteArray = fileToByteArray(annotationFile);
		
		json.put("document", documentByteArray); // Ambos en formato byte[]
		json.put("annotation", annotationByteArray);
		String body = json.toString();
		return body;
	}
	
	@PostMapping(path="/save", produces="application/json")
	public String save(@RequestBody Pojo2 pojo2) {
	
		System.out.println("Pojo2" +  pojo2);
	    JSONObject json = new JSONObject();
	    json.put("annotation", "");
		String body = json.toString();
		return body;
	}
	
	
	private String fileToByteArray(File file) throws IOException {
		int sizeRead = 0;
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream bff = new ByteArrayOutputStream();
		String byteArray = null;
		try (InputStream in = new FileInputStream(file)) {
			while ((sizeRead = in.read(buffer)) >= 0) { 
				bff.write(buffer, 0, sizeRead);
			}
			byteArray = Base64.getEncoder().encodeToString(bff.toByteArray());
		}
		catch(FileNotFoundException e) {
			System.out.println("No existe el File" + file.getName());
		}
		return byteArray;
	}

}
