package ramil.akberov.coursework3.serviсes;

import ramil.akberov.coursework3.exceptions.ExceptionApp;
import ramil.akberov.coursework3.model.Colors;
import ramil.akberov.coursework3.model.Size;
import ramil.akberov.coursework3.model.Socks;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksService {

    public long addSocks(Socks sock);

    Socks saleSocks(Colors color, Size size, Integer cotton) throws ExceptionApp;

    Integer getNumberOfSocks(Colors color, Size size, Integer cotton);

    Socks deleteDefectiveSocks(Colors color, Size size, Integer cotton) throws ExceptionApp;

    Path createReport() throws IOException;
}
