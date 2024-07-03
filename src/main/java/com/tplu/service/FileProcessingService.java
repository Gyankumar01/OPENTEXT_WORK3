package com.tplu.service;
import com.tplu.model.JarInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);
    private static final String START_LINE = "The following dependencies in Dependency Management have newer versions:";
    private static final Pattern JAR_INFO_PATTERN = Pattern.compile("\\s*([^:]+)([^\\s]+)\\s+.*\\s([^\\s]+)\\s+->\\s+([^\\s]+)");

    public List<JarInfo> processFile(BufferedReader reader) {
        List<JarInfo> jarInfos = new ArrayList<>();
        Set<String> seenArtifacts = new HashSet<>();
        boolean startProcessing = false;
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[INFO]")) {
                    line = line.substring(6).trim();
                }

                if (line.contains(START_LINE)) {
                    startProcessing = true;
                    continue;
                }

                if (startProcessing) {
                    Matcher matcher = JAR_INFO_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        String artifact = matcher.group(1)+matcher.group(2);
                        String currentVersion = matcher.group(3);
                        String newVersion = matcher.group(4);

                        if (!seenArtifacts.contains(artifact)) {
                            seenArtifacts.add(artifact);
                            jarInfos.add(new JarInfo(artifact, currentVersion, newVersion));
                           // logger.info("Processed artifact: {}, Current Version: {}, New Version: {}", artifact, currentVersion, newVersion);
                        } else {
                            logger.info("Duplicate artifact: {}. Skipping entry.", artifact);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error reading file:", e);
            throw new RuntimeException("Error reading file", e);
        }

        return jarInfos;
    }
}
