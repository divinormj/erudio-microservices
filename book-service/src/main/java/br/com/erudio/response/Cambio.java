package br.com.erudio.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cambio implements Serializable {

	private static final long serialVersionUID = 3081622361930849472L;

	private Long id;
	
	private String from;
	
	private String to;
	
	private Double conversionFactor;

	private Double convertedValue;
	
	private String environment;
	
}
