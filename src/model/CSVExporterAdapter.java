package model;

public class CSVExporterAdapter implements ReportExporter {

    private OldCSVExporter oldCSVExporter;

    public CSVExporterAdapter(OldCSVExporter oldCSVExporter) {
        this.oldCSVExporter = oldCSVExporter;
    }

    @Override
    public void exportReport() {
        oldCSVExporter.generateCSVFile();
        System.out.println("CSV report exported using Adapter Pattern.");
    }
}