package ramil.akberov.coursework3.serviсes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ramil.akberov.coursework3.exceptions.ExceptionApp;
import ramil.akberov.coursework3.model.Colors;
import ramil.akberov.coursework3.model.Size;
import ramil.akberov.coursework3.model.Socks;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    private ArrayList<Socks> socksList = new ArrayList<>();
    private final FilesService filesService;

    @Value("${path.to.data.dir}")
    private String dataFilePath;
    @Value("${socks.data.file}")
    private String dataFileName;

    public SocksServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public long addSocks(Socks socks) {
        socksList.add(socks);
        saveToFile();
        return 0;
    }


    @Override
    public Socks saleSocks(Colors color, Size size, Integer cotton) throws ExceptionApp {
        for (Socks socks1 : socksList) {
            if (socks1.getColors().equals(color) && socks1.getSize().equals(size) && socks1.getCottonPart().equals(cotton)) {
                socksList.remove(socks1);
                saveToFile();
            }
        }

        return null;
    }

    @Override
    public Integer getNumberOfSocks(Colors color, Size size, Integer cotton) {
        Integer numderOfSocks = 1;

        for (Socks socks1 : socksList) {
            if (socks1.getColors().equals(color) && socks1.getSize().equals(size) && socks1.getCottonPart().equals(cotton)) {
                numderOfSocks++;
                saveToFile();
            }
        }

        return numderOfSocks;
    }

    @Override
    public Socks deleteDefectiveSocks(Colors color, Size size, Integer cotton) throws ExceptionApp {
        for (Socks socks1 : socksList) {
            if (socks1.getColors().equals(color) && socks1.getSize().equals(size) && socks1.getCottonPart().equals(cotton)) {
                socksList.remove(socks1);
                saveToFile();
            }
        }

        return null;
    }


    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile(socksList);
            String json = new ObjectMapper().writeValueAsString(dataFile);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFromFile() {
        File file = new File(dataFileName);
        if (file.exists()) {
            String json = filesService.readFromFile();
            try {
                socksList = new ObjectMapper().readValue(json, new TypeReference<ArrayList<Socks>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Path createReport() throws IOException {
        Path socksText = filesService.createTempFile("Socks_text");
        for (Socks socks : socksList) {
            try (Writer writer = Files.newBufferedWriter(socksText, StandardOpenOption.APPEND)) {
                writer.append("Тип(ADD,REMOVE,GET): " + ", цвет -  " + socks.getColors() + " ,размер   -   " + socks.getSize() + " , количество  -  "
                        + socks.getQuantity() + " ,процентное содержание хлопка   -   " + socks.getCottonPart());
                writer.append("\n");
            }
        }
        return socksText;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DataFile {

        private ArrayList<Socks> socksMap;
    }
}




