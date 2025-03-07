package converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AttendanceStatusConverter implements AttributeConverter<AttendanceStatus, String> {

    @Override
    public String convertToDatabaseColumn(AttendanceStatus status) {
        if (status == null)
            return null;
        return status.name();
    }

    @Override
    public AttendanceStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        return AttendanceStatus.valueOf(dbData);
    }

}
