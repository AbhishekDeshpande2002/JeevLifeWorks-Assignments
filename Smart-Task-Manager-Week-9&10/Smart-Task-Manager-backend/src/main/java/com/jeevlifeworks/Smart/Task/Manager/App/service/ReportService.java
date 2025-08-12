package com.jeevlifeworks.Smart.Task.Manager.App.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jeevlifeworks.Smart.Task.Manager.App.Entity.Task;
import com.jeevlifeworks.Smart.Task.Manager.App.dto.ReportRequestDto;
import com.jeevlifeworks.Smart.Task.Manager.App.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for generating task reports in PDF or Excel format.
 * Fetches tasks from the repository, filters them based on request criteria,
 * and formats them into downloadable byte arrays for file export.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
	
	private final TaskRepository taskRepository;

	/**
     * Generates a report based on filtering criteria and requested format.
     *
     * Accepts parameter req Report request containing filter parameters (status, assigneeId, date range)
     * Accepts parameter format Output format ("pdf" or "excel")
     * return Byte array representing the file content
     */
    public byte[] generateReport(ReportRequestDto req, String format) {
    	log.info("Generating {} report with filters - Status: {}, AssigneeId: {}, StartDate: {}, EndDate: {}",
                format, req.getStatus(), req.getAssigneeId(), req.getStartDate(), req.getEndDate());
    	// Fetch tasks from DB based on provided filter criteria
        List<Task> tasks = taskRepository.findByFilterCriteria(
                req.getStatus(), null, req.getAssigneeId(), null
        ).stream()
        		// Apply additional date filtering (start date and end date)
         .filter(t -> filterByDate(t, req.getStartDate(), req.getEndDate()))
         .collect(Collectors.toList());
        
        log.debug("Filtered tasks count: "+ tasks.size());

     // Choose the appropriate format generator
        if ("pdf".equalsIgnoreCase(format)) {
            return buildPdf(tasks);
        } else if ("excel".equalsIgnoreCase(format)) {
            return buildExcel(tasks);
        }
        log.error("Unsupported report format requested: "+ format);
        throw new RuntimeException("Unsupported format");
    }
    
    /**
     * Filters a task based on start and end date constraints.
     *
     * Accepts parameter t Task object
     * Accepts parameter start Start date filter
     * Accepts parameter end   End date filter
     * return true if the task falls within the date range, false otherwise
     */
    private boolean filterByDate(Task t, LocalDate start, LocalDate end) {
        if (start != null && t.getDueDate().isBefore(start)) return false;
        if (end != null && t.getDueDate().isAfter(end)) return false;
        return true;
    }

    /**
     * Builds a PDF report containing task details.
     *
     * Accepts parameter tasks List of tasks to include in the PDF
     * return Byte array of the generated PDF file
     */
    private byte[] buildPdf(List<Task> tasks) {
    	log.info("Building PDF report with "+ tasks.size() +" tasks");
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, out);
            doc.open();
            doc.add(new Paragraph("Task Report"));
            PdfPTable table = new PdfPTable(4); // 4 columns: Title, Status, Due Date, Priority
            table.addCell("Title"); table.addCell("Status"); table.addCell("Due Date"); table.addCell("Priority");
         // Populate table rows with task data
            for (Task t : tasks) {
                table.addCell(t.getTitle());
                table.addCell(t.getStatus());
                table.addCell(t.getDueDate().toString());
                table.addCell(t.getPriority());
            }
            doc.add(table);
            doc.close();
            log.debug("PDF report generation completed");
            return out.toByteArray();
        } catch (Exception e) { 
        	 log.error("Error generating PDF report", e);
        	throw new RuntimeException(e); 
        	}
    }

    /**
     * Builds an Excel report containing task details.
     *
     * Accepts parameter tasks List of tasks to include in the Excel file
     * return Byte array of the generated Excel file
     */
    private byte[] buildExcel(List<Task> tasks) {
    	log.info("Building Excel report with "+  tasks.size() +" tasks");
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Tasks");
         // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Title");
            header.createCell(1).setCellValue("Status");
            header.createCell(2).setCellValue("Due Date");
            header.createCell(3).setCellValue("Priority");

         // Populate rows with task data
            int r = 1;
            for (Task t : tasks) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(t.getTitle());
                row.createCell(1).setCellValue(t.getStatus());
                row.createCell(2).setCellValue(t.getDueDate().toString());
                row.createCell(3).setCellValue(t.getPriority());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            log.debug("Excel report generation completed");
            return out.toByteArray();
        } catch (Exception e) { 
        	log.error("Error generating Excel report", e);
        	throw new RuntimeException(e); 
        	}
    }
}
