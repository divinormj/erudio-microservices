package br.com.erudio.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "cambio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cambio implements Serializable {

	private static final long serialVersionUID = 3081622361930849472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "from_currency", nullable = false, length = 3)
	private String from;
	
	@Column(name = "to_currency", nullable = false, length = 3)
	private String to;
	
	@Column(nullable = false)
	private BigDecimal conversionFactor;

	@Transient
	private BigDecimal convertedValue;
	
	@Transient
	private String environment;
	
	public void calcConvertedValue(final BigDecimal amount) {
		var convertedValue = this.getConversionFactor().multiply(amount);
		this.convertedValue = convertedValue.setScale(2, RoundingMode.CEILING);
	}
	
}
