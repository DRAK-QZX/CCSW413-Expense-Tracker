package model;

public class PDFExporter implements ReportExporter {

    @Override
    public void exportReport() {
        System.out.println("PDF report generated successfully.");
    }
}