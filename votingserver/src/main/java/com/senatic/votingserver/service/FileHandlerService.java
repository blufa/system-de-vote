package com.senatic.votingserver.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.senatic.votingserver.controller.exceptionHandler.ApiExceptionHandler;
import com.senatic.votingserver.controller.exceptionHandler.exception.FileNotValidException;
import com.senatic.votingserver.model.dto.AprendizDTO;

import jakarta.xml.bind.DatatypeConverter;

@Service
public class FileHandlerService {

    public boolean isFormatValid(String filename, String endsWith) {
        if (!filename.isEmpty()) {
            if (filename.endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    public List<AprendizDTO> readCsvToAprendizDTO(MultipartFile csv) {
        BufferedReader br;
        List<AprendizDTO> aprendicesDTO = new ArrayList<>();
        try {
            String line;
            InputStream is = csv.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                // Manipulate the line
                String[] fields = line.split(",");
                if (fields.length == 9) {
                    String ficha = fields[0].trim().isEmpty() ? "NOT_PROVIDED" : fields[0].trim().replace("\"", "");
                    String programa = fields[1].trim().isEmpty() ? "NOT_PROVIDED" : fields[1].trim().replace("\"", "");
                    String tipoDocumento = fields[2].trim().isEmpty() ? "NA"
                            : fields[2].trim().toUpperCase().replace("\"", "");
                    String numeroDocumento = fields[3].trim().isEmpty() ? "NA" : fields[3].trim().replace("\"", "");
                    String nombre = fields[4].trim().isEmpty() ? "NOT_PROVIDED" : fields[4].trim().replace("\"", "");
                    String apellido = fields[5].trim().isEmpty() ? "NOT_PROVIDED" : fields[5].trim().replace("\"", "");
                    String celular = fields[6].trim().isEmpty() ? "NA" : fields[6].trim().replace("\"", "");
                    String correoElectronico = fields[7].trim().isEmpty() ? "NOT_PROVIDED"
                            : fields[7].trim().replace("\"", "");
                    String estado = fields[8].trim().isEmpty() ? "NOT_PROVIDED"
                            : fields[8].trim().toUpperCase().replace("\"", "");

                    AprendizDTO dto = AprendizDTO.builder()
                            .ficha(ficha)
                            .programa(programa)
                            .tipoDocumento(tipoDocumento)
                            .numeroDocumento(numeroDocumento)
                            .nombre(nombre)
                            .apellido(apellido)
                            .celular(celular)
                            .correoElectronico(correoElectronico)
                            .estado(estado)
                            .build();
                    aprendicesDTO.add(dto);
                } else {
                    throw new FileNotValidException(
                            "CSV column length is not valid. Required: 9. Having: " + fields.length);
                }
            }
            is.close();
        } catch (IOException e) {
            ApiExceptionHandler.logger.error(e.getMessage());
        }
        return aprendicesDTO;
    }

    public String dtoToPojo(MultipartFile dto) {
        String base64 = "";
        try {
            base64 = Base64.getEncoder().encodeToString(dto.getBytes());
        } catch (IOException e) {
            ApiExceptionHandler.logger.error(e.getMessage());
        }
        return base64;
    }

    public MultipartFile pojoToDto(String pojo) {
        byte[] data = DatatypeConverter.parseBase64Binary(pojo);
        MultipartFile file = new MockMultipartFile("file.jpg", data);
        return file;
    }

    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
