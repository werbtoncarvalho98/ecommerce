package br.unitins.topicos1.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.topicos1.model.Nivel;

@Converter(autoApply = true)
public class NivelConverter implements AttributeConverter<Nivel, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Nivel nivel) {
        return nivel == null ? null : nivel.getId();
    }

    @Override
    public Nivel convertToEntityAttribute(Integer id) {
        return Nivel.valueOf(id);
    }
}