package com.lisi.docviewer.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lisi.docviewer.bean.DocViewerBean;
import com.lisi.docviewer.model.DocViewer;
import com.lisi.docviewer.service.DocViewerService;
import com.crystaldecisions.reports.common.engine.ConfigurationManager;
import com.crystaldecisions.reports.common.engine.Engine;
import com.crystaldecisions.sdk.occa.report.application.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;

import com.crystaldecisions.sdk.occa.report.document.PrintReportOptions;
import com.crystaldecisions.sdk.occa.report.document.PrinterDuplex;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

/**
 * @author Venkat Gonepudi
 *
 */
@Controller
public class DocViewerController {
	
	private static final String TMP_PATH = "C:\\\\temp";
	public static final int BUFFER_SIZE = 32768;
	public static final int EOF = -1;
	
	@Autowired
	private DocViewerService docViewerService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("docviewer");
	}	
	
	/**
	 * 
	 * @param lisiIndexerBean
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchResults", method = RequestMethod.POST)
	public ModelAndView getLisiIndexerByName(@ModelAttribute("command")  DocViewerBean docViewerBean,
			BindingResult result) {
		
		Map<String, Object> model = new HashMap<String, Object>();
	
		
		model.put("lisiIndexer",  docViewerService.getLisiIndexerByName(docViewerBean.getTxtSearch()));
		model.put("lastSearchText", docViewerBean.getTxtSearch());
		
		return new ModelAndView("docviewer",model);
	}
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public ModelAndView downloadFile(@RequestParam(value = "id") int id,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<DocViewer> fileNamesList = docViewerService.getLisiIndexerById(id);
		Iterator<DocViewer> iterator = fileNamesList.iterator();
		if(fileNamesList != null && !fileNamesList.isEmpty()){
		for(DocViewer lisiIndexer : fileNamesList){
		   String name = lisiIndexer.getFileFullPath();
		  
		   try{
			  
			   	 ServletOutputStream out = response.getOutputStream();
				 out.flush();
				 response.setContentType("application/pdf");
				 FileInputStream in = new FileInputStream(name);
				 System.out.println(URLConnection.guessContentTypeFromStream(in));
				 byte[] buffer = new byte[1024];
				 int len = 0;
				 while(true){
					 len = in.read(buffer);
					 if(len < 0)
						 break;
					 out.write(buffer, 0, len);
				 }
				 in.close();
				 out.flush();
				 out.close();
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		}
		}
		return new ModelAndView("docviewer", model);
	}
	/**
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public void downloadByName(String fileName,HttpServletRequest request, HttpServletResponse response,String pdfName)throws IOException,IllegalStateException  {
			 
			 ServletOutputStream out = response.getOutputStream();
			
			 response.setContentType("application/pdf");
			 response.setHeader("Content-Disposition", "attachment; filename="+pdfName);
			 FileInputStream in = new FileInputStream(fileName);
			 byte[] buffer = new byte[1024];
			 int len = 0;
			 while(true){
				 len = in.read(buffer);
				 if(len < 0)
					 break;
				 out.write(buffer, 0, len);
			 }
			 in.close();
			 out.flush();
			 out.close();
			
			  			
		 }
	
	@RequestMapping(value = "/generateLabel", method = RequestMethod.POST)
	public String generateLabel(@RequestParam(value = "id") int id,@RequestParam(value="filePath") String filePath,HttpServletRequest request, HttpServletResponse response) throws ReportSDKException, IllegalStateException, IOException{
		
		System.out.println(" label generated id " + id);
		
		ReportClientDocument reportClientDoc = null;
		reportClientDoc = new ReportClientDocument();
		reportClientDoc
				.setReportAppServer(ReportClientDocument.inprocConnectionString);
System.out.println(filePath+ " filePath from post req.");
String modifiedFileName = filePath.replace("&", "%26");
System.out.println(modifiedFileName + " modified file name");
		reportClientDoc.open("C://DocViewerProperties//docViewerReport.rpt",0);
		ParameterFieldController paramFieldController = reportClientDoc
				.getDataDefController().getParameterFieldController();
		paramFieldController.setCurrentValue("", "pdfName",
				modifiedFileName);

		paramFieldController.setCurrentValue("", "id",
				String.valueOf(id));

		String path = TMP_PATH;
		
		String pdfName = generatePDFName(filePath);
		
		
		System.out.println(pdfName + " : pdfname");
		File pdfFile = null;
		try {
			pdfFile = createPDF(reportClientDoc, path, pdfName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String fullName = pdfFile.getAbsolutePath();
		
		downloadByName(fullName,request,response,pdfName);
		
		return "docviewer";
	}
	
	public String generatePDFName(String filePath) {
		StringBuilder builder = new StringBuilder();
		builder.append("APPEND_")
				.append(filePath);
		//		.append(".pdf");
		return builder.toString();
		
	}
	
	public File createPDF(ReportClientDocument document, String path,
			String pdfName) throws ReportSDKException, IOException {
		if (!createDirs(path)) {
			throw new IllegalArgumentException("Can't create directory " + path);
		}

		System.out.println("create pdf...");

		File file = new File(path + "////" + pdfName);
		/*if (!file.createNewFile()) {
			throw new IllegalArgumentException("File already exists");
		}*/
		byte[] buffer = new byte[BUFFER_SIZE];
		ByteArrayInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = (ByteArrayInputStream) document.getPrintOutputController()
					.export(ReportExportFormat.PDF);
			output = new BufferedOutputStream(new FileOutputStream(file, true));
			int bytesRead;
			while ((bytesRead = input.read(buffer)) != EOF) {
				output.write(buffer, 0, bytesRead);
			}
			output.flush();
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
		return file;
	}
	
	protected boolean createDirs(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			return true;
		}
		return dir.mkdirs();
	}
}
