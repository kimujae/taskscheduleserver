package com.yeonieum.scheduledTeskserver.global.converter;

import com.yeonieum.scheduledTeskserver.global.enums.ActiveStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ActiveStatusConverter implements AttributeConverter<ActiveStatus, String> {

    @Override
    public String convertToDatabaseColumn(ActiveStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }


    @Override
    public ActiveStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ActiveStatus.fromCode(dbData);
    }
}