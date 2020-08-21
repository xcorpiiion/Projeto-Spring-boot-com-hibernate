package br.com.estudo.projetoweb.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodificaParam(String valorParaDecodificar) {
		try {
			return URLDecoder.decode(valorParaDecodificar, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Long> convertStringInLongList(String valorParaConverter) {
		return Arrays.asList(valorParaConverter.split(",")).stream()
				.map(valoresConvertidos -> Long.parseLong(valoresConvertidos)).collect(Collectors.toList());
	}

}
