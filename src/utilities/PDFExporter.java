package utilities;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

import entities.Arrear;
import entities.Doctor;
import entities.Work;

public class PDFExporter {

	/** Path to the resulting PDF file. */
    public static final String URL
        = "doctor.pdf";
    
    ArrayList<Work> works;
    Doctor doctor;
    Date fromDate;
    Date toDate;
    
    Document document;
    
    Font fontBig;

	Font fontSmall;
	Font fontSmallBold;
 
    /**
     * Creates a PDF file: hello.pdf
     * @param    args    no arguments needed
     */
    public static void main(String[] args)
    	throws DocumentException, IOException {
    	new PDFExporter().createPdf(URL);
    }
 
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public void createPdf(String filename)
	throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(createFirstTable());
        //document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();
    }
    
    /**
     * Creates our first table
     * @return our first table
     */
    public static PdfPTable createFirstTable() {
    	// a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }
    
    public void generateDoctorCheck(ArrayList<Work> works, Doctor doctor, Date fromDate, Date toDate) throws DocumentException, IOException{
    	
    	this.works = works;
    	this.doctor = doctor;
    	
    	this.fromDate = fromDate;
    	this.toDate = toDate;
    	
    	init();

    	// step 1
        document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(URL));
        // step 3
        document.open();
        // step 4
        createDoctorHeader();
        //document.add(createDoctorHeader());
        // step 4
        createDoctorCheckTable();
        // step 4
        createFooter();
        //document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();
        
        
        openFullScreen(URL);
    }

	private void init() {
		
		BaseFont helvetica;
		try {
			helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
			fontBig=new Font(helvetica,16);
			fontSmall=new Font(helvetica,10);
			fontSmallBold=new Font(helvetica,10);
			fontSmallBold.setStyle(Font.BOLD);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void createFooter() throws DocumentException {
		
		document.add( Chunk.NEWLINE );
		
		Paragraph p = new Paragraph("Laboratorium protetyczne Sil-Tech",fontSmall);
		p.setAlignment(Element.ALIGN_BOTTOM);
		p.setAlignment(Element.ALIGN_RIGHT);

		document.add(p);
		
		p = new Paragraph("Warszawa",fontSmall);
		p.setAlignment(Element.ALIGN_RIGHT);
		document.add(p);
		
		p = new Paragraph("Wêdkarska 2D/E2",fontSmall);
		p.setAlignment(Element.ALIGN_RIGHT);
		document.add(p);
		
		p = new Paragraph("tel. 501 287 640",fontSmall);
		p.setAlignment(Element.ALIGN_RIGHT);
		document.add(p);
	}

	private void createDoctorCheckTable() throws DocumentException {
		
		// a table with three columns
        PdfPTable table = new PdfPTable(7);
        float[] columnWidths = new float[]{12f,20f,20f,20f,20f,20f,16f};
        table.setWidths(columnWidths);
        table.setWidthPercentage(100);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        /*cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);*/
        cell = new PdfPCell(new Phrase("l.p.",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Nr ID",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Data oddania",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Nazwisko pacjenta",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Lekarz",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Typ pracy",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cena",fontSmallBold));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        int i = 1;
        double sum = 0;
        
        for (Work work : works) {
        	// lp
        	cell = new PdfPCell(new Phrase(String.valueOf(i++),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// numer id
        	cell = new PdfPCell(new Phrase(work.getPrintId(),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// data oddania
        	cell = new PdfPCell(new Phrase(new SimpleDateFormat("dd/MM/yy").format(work.getFinish_date()),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// nazwisko pacjenta
        	cell = new PdfPCell(new Phrase(work.getPacient(),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// lekarz
        	cell = new PdfPCell(new Phrase(work.getDoctor(),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// typ pracy
        	cell = new PdfPCell(new Phrase(work.getWork_type(),fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	// cena
        	cell = new PdfPCell(new Phrase(String.format("%s", work.getPrice())+" z³",fontSmall));
        	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        	table.addCell(cell);
        	
        	sum += work.getPrice();
		}
        
        // empty line
        cell = new PdfPCell(new Phrase(" ",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Razem",fontSmall));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("",fontSmall));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(String.format("%s", sum)+" z³",fontSmall));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        
        // now we add a cell with rowspan 2
        /*cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");*/
        
        document.add(table);
	}

	private void createDoctorHeader() throws DocumentException {
		
		
		
		Paragraph date = new Paragraph();
		date.setAlignment(Element.ALIGN_RIGHT);
		date.setFont(fontBig);
		date.add("Warszawa "+new SimpleDateFormat("dd/MM/yy").format(new Date()));
		
		document.add(date);
		
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		
		Paragraph title = new Paragraph();
		title.setAlignment(Element.ALIGN_CENTER);
		title.setFont(fontBig);
		
		title.add("Lekarz "+doctor.toString());
		document.add(title);
		
		document.add( Chunk.NEWLINE );
		
		Paragraph p = new Paragraph("Rozliczenie prac pacjentów za okres od "+
				new SimpleDateFormat("dd/MM").format(fromDate)+
				" do "+new SimpleDateFormat("dd/MM").format(toDate),fontBig);
		
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		
		document.add( Chunk.NEWLINE );

	}


	public void openFullScreen(String URL) throws IOException{
		
		Runtime.getRuntime().exec("cmd /c start /max doctor.pdf");
		
	}

}
