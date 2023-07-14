package org.acme.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

    String salvarImagem(byte[] imagem, String nomeImagem) throws IOException;

    File download(String nomeArquivo);
}