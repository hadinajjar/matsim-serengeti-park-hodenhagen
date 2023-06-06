package org.matsim.analysis;

import org.apache.commons.csv.CSVFormat;
import org.matsim.core.events.EventsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleAnalysis {

    private static final String eventsFile = "scenarios/serengeti-park-v1.0/output/output-serengeti-park-v1.0-run1/serengeti-park-v1.0-run1.output_events.xml.gz";
    private static final String outFile = "scenarios/serengeti-park-v1.0/output/volumes.csv";

    public static void main(String[] args) {

        var handler = new SimplePersonEventHandler();
        var manager = EventsUtils.createEventsManager();
        var linkHandler = new LinkEventHandler();

        manager.addHandler(handler);
        manager.addHandler(linkHandler);

        EventsUtils.readEvents(manager, eventsFile);

        var volumes = linkHandler.getVolumes();

        try (var writer = Files.newBufferedWriter(Paths.get(outFile)); var printer = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Hour", "Value").print(writer)) {

            for (var volume : volumes.entrySet()) {
                printer.printRecord(volume.getKey(), volume.getValue());
                printer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}