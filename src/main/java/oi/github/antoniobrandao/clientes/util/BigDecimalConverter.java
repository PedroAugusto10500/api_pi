package oi.github.antoniobrandao.clientes.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

@Component
public class BigDecimalConverter {

    public BigDecimal converter(String value) {
        if (value == null) {
            return null;
        }

        value = value.replace(",", "."); // Para garantir que está no formato correto
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP); // Ajuste a escala conforme necessário
    }
}