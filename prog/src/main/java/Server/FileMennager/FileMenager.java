package Server.FileMennager;

import Server.DeveloperKit.SimpleIOMenager;
import Server.Model.Organization;
import Server.transfer.FileRequest;
import Server.transfer.IORequest;
import Server.transfer.Response;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;
import java.util.Stack;

/**
 * FileMenager - класс, предназначенный для работы с файлами
 * Имеет поле saveFileName - путь к файлу в формате xml, в который сохраняются элементы коллекции
 */
public class FileMenager {
    String saveFileName;
    private boolean isLogsON;
    private SimpleIOMenager ioMenager;

    /**
     * FileMenager(String saveFileName) - конструктор класса FileMenager
     */
    public FileMenager(boolean isLogON, SimpleIOMenager simpleIOMenager) {
        this.saveFileName = System.getenv("save");
        setDirectory();
    }

    /**
     * checkScript(String fileName) - метод, предназначенный для считывания скрипта из файла
     * @param fileName - путь к файлу, в котором хранится скрипт
     * @return res - список строк
     */
    public List<String[]> checkScript(String fileName) {

        List<String[]> res = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            String command = null;

            while ((line = reader.readLine()) != null) {
                res.add(line.split(" "));
            }

        } catch (IOException e) {
            return res;
        }

        return res;
    }

    /**
     * addOrgsFromEnv(String filePath) - метод, предназначенный для считывания коллекции из файла
     * @param filePath - путь к файлу, в котором хранится коллекция
     * @return res - список организаций, извлечённых из файла
     */
    public ArrayList<Organization> addOrgsFromEnv(String filePath){
        ArrayList<Organization> res = new ArrayList<>();

        if (filePath == null) {
            return null;
        }

        Stack<Long> notFreeID = new Stack<>();
        Long maxID = 0L;

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader reader = new BufferedReader(fileReader)) {

            JAXBContext context = JAXBContext.newInstance(Organization.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            unmarshaller.setEventHandler(event -> {
                throw new RuntimeException("Ошибка валидации XML", event.getLinkedException());
            });

            StringBuilder currentOrg = new StringBuilder();
            String line;
            boolean inOrganization = false;
            boolean isStart = false;
            boolean isFinish = false;

            while ((line = reader.readLine()) != null) {
                isStart = true;
                line = line.trim();

                if (line.startsWith("<organization>")) {
                    inOrganization = true;
                    currentOrg = new StringBuilder();
                }

                if (inOrganization) {
                    currentOrg.append(line);

                    if (line.endsWith("</organization>")) {
                        inOrganization = false;
                        String orgXml = currentOrg.toString();

                        try {
                            Organization org = (Organization) unmarshaller.unmarshal(
                                    new StreamSource(new StringReader(orgXml))
                            );

                            if (!org.isAllSet()) {
                                this.ioMenager.sendLog(new IORequest("Ошибка валидации XML\nОтсутствуют некоторые элементы организации",
                                        2));
                                System.exit(2);
                            }

                            res.add(org);
                            Long orgID = org.getID();
                            notFreeID.push(orgID);
                            if (maxID < orgID) {
                                maxID = orgID;
                            }
                            isFinish = true;

                        } catch (RuntimeException e) {
                            this.ioMenager.sendLog(new IORequest("Ошибка валидации XML", 2));
                            System.exit(2);
                        } catch (JAXBException e) {
                            this.ioMenager.sendLog(new IORequest("Ошибка парсинга XML", 2));
                            System.exit(2);
                        }
                    }
                }
            }

            if (!isFinish) {
                this.ioMenager.sendLog(new IORequest("Файл содержит не полную информацию!", 2));
                System.exit(2);
            }

        } catch (IOException e) {
            this.ioMenager.sendLog(new IORequest("Ошибка чтения файла: " + e.getMessage(), 2));
            System.exit(2);
        } catch (JAXBException e) {
            this.ioMenager.sendLog(new IORequest("Ошибка парсинга файла: " + e.getMessage(), 2));
            System.exit(2);
        } catch (Exception e) {
            this.ioMenager.sendLog(new IORequest("Ошибка: " + e.getMessage(), 2));
            System.exit(2);
        }

        for (long freeID = 0L; freeID < maxID; freeID++) {
            if (!notFreeID.contains(freeID)) {
                setToFile("Data/saveFreeID.txt", String.valueOf(freeID));
            }
        }
        setToFile("Data/saveID.txt", String.valueOf(maxID + 1));

        return res;
    }

    /**
     * save(FileRequest req) - метод, предназначенный для считывания коллекции из файла
     * @param req - запрос
     * @return Response - ответ о завершении сохранения
     */
    public Response save(FileRequest req) {

        try (PrintWriter writer = new PrintWriter(this.saveFileName)) {

            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<organizations>");

            JAXBContext context = JAXBContext.newInstance(Organization.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            for (Organization organization: req.stack()) {
                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(organization, stringWriter);
                writer.println(stringWriter.toString());
                writer.flush();
            }
            writer.println("</organizations>");

        } catch (IOException e) {
            this.ioMenager.sendLog(new IORequest("Ошибка сохранения", 1));
        } catch (JAXBException e) {
            this.ioMenager.sendLog(new IORequest("Ошибка записи", 1));
        }
        return new Response("Данные сохранены", null);
    }

    /**
     *  setDirectory() - метод, предназначенный для задания директории и файлов, необходимых для работы программы
     */
    private void setDirectory() {
        try {
            Files.createDirectories(Paths.get("Data"));
            try {
                Files.createFile(Path.of("Data/saveID.txt"));
            } catch (FileAlreadyExistsException ignore) {}

            try {
                Files.createFile(Path.of("Data/saveFreeID.txt"));
            } catch (FileAlreadyExistsException ignore) {}

            try {
                Files.createFile(Path.of("Data/save.xml"));
            } catch (FileAlreadyExistsException ignore) {}
        } catch (IOException e) {
            this.ioMenager.sendLog(new IORequest("Ошибка: " + e.getMessage(), 1));
        }
    }

    /**
     * getIDs() - метод, предназначенный для считывания свободного ID из файла и записи следующего (ID + 1)
     * @return ID - следующий свободный ID
     */
    public Long getIDs() {
        try (FileReader fileReader = new FileReader("Data/saveID.txt");
             BufferedReader reader = new BufferedReader(fileReader)){
             Long ID;
             String line = reader.readLine();

             if (line == null) {
                 ID = 0L;
             }
             else {
                 ID = Long.parseLong(line);
             }

             setToFile("Data/saveID.txt", String.valueOf((ID + 1)));

             return ID;
        } catch (IOException e) {
            return 0L;
        }
    }

    /**
     * getIDs() - метод, предназначенный для считывания свободного ID из файла и "удаления" его из файла
     * @return ID - свободный ID
     */
    public Long getFreeID() {
        try (FileReader fileReader = new FileReader("Data/saveFreeID.txt");
             BufferedReader reader = new BufferedReader(fileReader)){
            ArrayList<String> lines = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if (lines.isEmpty() || lines.get(0).isEmpty()) {
                return null;
            }

            Long ID = Long.parseLong(lines.get(0));
            lines.remove(0);

            if (lines.isEmpty()) {
                setToFile("Data/saveFreeID.txt", "");
            } else {
                for (String newLine : lines) {
                    setToFile("Data/saveFreeID.txt", newLine);
                }
            }

            return ID;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * setToFile(String filePath, String line) - метод, предназначенный для записи строки в файл
     * @param filePath - путь к файлу
     * @param line - строка для записи
     */
    private void setToFile(String filePath, String line) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(line);
        } catch (FileNotFoundException ignored) {}
    }

    /**
     * getIDs() - метод, предназначенный для считывания свободного ID из файла и "удаления" его из файла
     * @param line - строка для записи
     */
    public void addNewFreeID(String line) {
        setToFile("Data/saveFreeID.txt", line);
    }

}