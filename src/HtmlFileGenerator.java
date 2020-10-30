import java.io.*;
import java.util.*;

public class HtmlFileGenerator {

    static final String fileToBeRead = "resource//paragraph.txt";
    static final String htmlReportToBeCreated = "resource//report.html";

    public static void main(String[] args) throws IOException {
        readFromTextFile(fileToBeRead);
    }

    static void readFromTextFile(String path) {
        try (final BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            Map<String, Integer> map = new TreeMap<>();
            List<String> lines = new ArrayList<>();
            System.out.println("Reading from paragraph.txt...");
            while ((line = br.readLine()) != null) {
                lines.add(line);
                Arrays.stream(line.split("\\s")).filter(w -> !w.isEmpty())
                        .map(w -> w.replaceAll("[^A-Za-z]", ""))
                        .forEach(w -> map.put(w, map.getOrDefault(w, 0) + 1));
            }

            createAndWriteToHtmlFile(lines, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createAndWriteToHtmlFile(List<String> lines, Map<String, Integer> map) throws IOException {
        File htmlReport = new File(htmlReportToBeCreated);
        htmlReport.delete();
        if (htmlReport.createNewFile()) {
            System.out.println("report.html created");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(htmlReport))) {
            bw.write("<html><body><h1>Input File name " + htmlReport.getName() + "</h1>");
            bw.write("<h1>File Contents</h1>");
            for (String line : lines) {
                bw.write(line);
            }
            bw.write("<br>");
            bw.write("<table  border=\"1\">");
            bw.write("<tr>");
            bw.write("<th>");
            bw.write("<h1>Word</h1>");
            bw.write("</th>");
            bw.write("<th>");
            bw.write("<h1>Occurrences</h1>");
            bw.write("</th>");
            bw.write("</tr>");
            bw.write("<tbody>");
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                bw.write("<tr><td>" + entry.getKey() + "</td><td><b>"
                        + entry.getValue() + "</b></td>");
            }
            bw.write("</tbody>");
            bw.write("/<table>");
            bw.write("</body></html>");
        }
    }
}

