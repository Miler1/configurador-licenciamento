package com.configuradorlicencimento.configuracao.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoUtils {

    public static File criaDiretorio(String diretorioSalvarComNomeArquivo) throws IOException {

        Path pathArquivoRetorno = Paths.get(diretorioSalvarComNomeArquivo);

        File arquivoRetorno = pathArquivoRetorno.toFile();

        if(!arquivoRetorno.exists()) {
            Files.createDirectories(pathArquivoRetorno.getParent());
        }

        return arquivoRetorno;

    }

    public static File salvaArquivoDiretorio(MultipartFile multipartFile, String diretorioSalvarComNomeArquivo) throws Exception {

        File arquivoRetorno = criaDiretorio(diretorioSalvarComNomeArquivo);

        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), arquivoRetorno);

        return arquivoRetorno;

    }

    public static File salvaArquivoDiretorio(File file, String diretorioSalvarComNomeArquivo) throws Exception {

        File arquivoRetorno = criaDiretorio(diretorioSalvarComNomeArquivo);

        FileUtils.copyInputStreamToFile(new FileInputStream(file), arquivoRetorno);

        return arquivoRetorno;

    }

}
