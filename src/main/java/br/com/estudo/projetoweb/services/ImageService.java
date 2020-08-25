package br.com.estudo.projetoweb.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.estudo.projetoweb.services.exception.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImagemFromFile(MultipartFile uploadedFile) {
		/*
		 * FilenameUtils -> ele vem da dependencia commons-io e ele está pegando a
		 * extensão do meu arquivo
		 */
		String extensaoArquivo = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if ("png".equals(extensaoArquivo) || "jpg".equals(extensaoArquivo)) {
			try {
				BufferedImage imagem = ImageIO.read(uploadedFile.getInputStream());
				if ("pgn".equals(extensaoArquivo)) {
					imagem = pgnToJpg(imagem);
				}
				return imagem;
			} catch (IOException e) {
				throw new FileException("Erro ao ler arquivo");
			}
		} else {
			throw new FileException("Somente imagens PNG ou JPG são permitidas");
		}
	}

	public BufferedImage pgnToJpg(BufferedImage imagem) {
		BufferedImage jpgImagem = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImagem.createGraphics().drawImage(imagem, 0, 0, Color.white, null);
		return jpgImagem;
	}

	/*
	 * Ele retorna um InputStream(que é o objeto que encapsula leitura) a partir de
	 * um BufferedImage
	 */
	public InputStream getInputStream(BufferedImage imagem, String extensao) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(imagem, extensao, outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage recortaImagem(BufferedImage sourceImagem) {
		int minimo = descobreTamanhoMinimoEntreAlturaAndLargura(sourceImagem);
		return Scalr.crop(sourceImagem, (sourceImagem.getWidth() / 2) - (minimo / 2),
				(sourceImagem.getHeight() / 2) - (minimo / 2), minimo, minimo);
	}

	private int descobreTamanhoMinimoEntreAlturaAndLargura(BufferedImage sourceImagem) {
		return (sourceImagem.getHeight() <= sourceImagem.getWidth()) ? sourceImagem.getHeight()
				: sourceImagem.getWidth();
	}
	
	public BufferedImage redimensionaImagem(BufferedImage sourceImage, int size) {
		return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
	}

}
