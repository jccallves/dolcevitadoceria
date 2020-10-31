package com.dolcevitadoceria.controller.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dolcevitadoceria.security.UserSS;
import com.dolcevitadoceria.services.UserService;
import com.dolcevitadoceria.services.exceptions.AuthorizationException;

@Component
public class UploadImage {

	
	private String path = System.getenv("resources.path");;
	
	@Value("${client.image.folder}")
	private String clienteFolder;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	
	public void salvarFoto(MultipartFile foto) {
		
		this.salvar(this.clienteFolder, foto);
	}
	
//	public void salvar(String diretorio, MultipartFile arquivo) {
//		Path diretorioPath = Paths.get(this.path, diretorio);
//		Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());
//		
//		try {
//			Files.createDirectories(diretorioPath);
//			arquivo.transferTo(arquivoPath.toFile());			
//		} catch (IOException e) {
//			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
//		}		
//	}

	
	public Path salvar(String clientFolder, MultipartFile arquivo) {
		//String caminho = getClass().getResource("").getPath().substring(0,59) + "/src/main/resources/clientes";
		
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado	!");
		}

		Path diretorioPath = Paths.get(this.path , clienteFolder);
		String extensaoDoArquivo = "." + FilenameUtils.getExtension(arquivo.getOriginalFilename());
		String newFileName = this.prefix + user.getId() + extensaoDoArquivo;
		Path arquivoPath = diretorioPath.resolve(newFileName);
		
		System.out.println("Novo arquivo com prefixo: " + arquivoPath);
		try {
			Files.createDirectories(diretorioPath);
			arquivo.transferTo(arquivoPath.toFile());
			return arquivoPath;
		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
		}		
	}
}
