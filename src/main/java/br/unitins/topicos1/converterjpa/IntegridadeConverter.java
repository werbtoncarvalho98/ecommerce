package br.unitins.topicos1.converterjpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.unitins.topicos1.model.Integridade;

@Converter(autoApply = true)
public class IntegridadeConverter implements AttributeConverter<Integridade, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Integridade integridade) {
        return integridade == null ? null : integridade.getId();
    }

    @Override
    public Integridade convertToEntityAttribute(Integer id) {
        return Integridade.valueOf(id);
    }
}