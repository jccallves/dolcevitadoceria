package com.dolcevitadoceria.controller.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${img.profile.size}")
	private Integer size;
	
	private String pathToStore;
	
	public void salvarFoto(MultipartFile foto) {
		
		this.salvar(this.clienteFolder, foto);
	}
	
	public String salvar(String clientFolder, MultipartFile arquivo) {
		
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado	!");
		}

		try {
			Path diretorioPath = Paths.get(this.path , clienteFolder);
			String extensaoDoArquivo = "." + FilenameUtils.getExtension(arquivo.getOriginalFilename());
			String newFileName = this.prefix + user.getId() + extensaoDoArquivo;
			String arquivoPath = diretorioPath.resolve(newFileName).toString();
			System.out.println("Novo arquivo com prefixo: " + arquivoPath);
			BufferedImage croppedImage = cropAndResizeImageSquare(arquivo.getBytes());
			String ext = FilenameUtils.getExtension(arquivo.getOriginalFilename());

			// Save the file locally
			File outputfile = new File(arquivoPath);
			ImageIO.write(croppedImage, ext, outputfile);
			this.pathToStore = arquivoPath;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return this.pathToStore;
	}
	
	private BufferedImage cropAndResizeImageSquare(byte[] image) throws IOException {
		  // Get a BufferedImage object from a byte array
		  InputStream in = new ByteArrayInputStream(image);
		  BufferedImage originalImage = ImageIO.read(in);
		  
		  // Get image dimensions
		  int height = originalImage.getHeight();
		  int width = originalImage.getWidth();
		  
		  // The image is already a square
		  if (height == width) {
		    return originalImage;
		  }
		  
		  // Compute the size of the square
		  int squareSize = (height > width ? width : height);
		  
		  // Coordinates of the image's middle
		  int xc = width / 2;
		  int yc = height / 2;
		  
		  // Crop
		  BufferedImage croppedImage = originalImage.getSubimage(
		      xc - (squareSize / 2), // x coordinate of the upper-left corner
		      yc - (squareSize / 2), // y coordinate of the upper-left corner
		      squareSize,            // widht
		      squareSize             // height
		  );
		  croppedImage = Scalr.resize(croppedImage, Scalr.Method.QUALITY, size);
		  return croppedImage;
		}

	
	public String getPathToStore() {
		return pathToStore;
	}

	public void setPathToStore(String pathToStore) {
		this.pathToStore = pathToStore;
	}

	
}
