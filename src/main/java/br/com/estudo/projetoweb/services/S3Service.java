package br.com.estudo.projetoweb.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.estudo.projetoweb.services.exception.FileException;

@Service
public class S3Service {

	private Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${s3.bucket}")
	private String bucketName;

	/* O MultipartFile é o tipo de arquivo que é enviado na requisição */
	/*
	 * O método a baixo irá retornar o endereço web do novo recurso que foi gerado
	 */
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream inputStream;
			inputStream = multipartFile.getInputStream();
			/*
			 * O contentType irá receber o tipo de informação do arquivo enviado (Por que
			 * ele pode ser um img, um txt, etc...)
			 */
			String contentType = multipartFile.getContentType();
			return uploadFile(inputStream, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(contentType);
			LOGGER.info("Iniciando Upload");
			amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata));
			LOGGER.info("Upload finalizado");
			return amazonS3.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URl para URI");
		}
	}

}
