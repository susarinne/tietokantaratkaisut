package converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BalanceConverter implements AttributeConverter<Double, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Double balance) {
        if (balance == null)
            return null;
        return (int) (balance * 100);
    }

    @Override
    public Double convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;
        return dbData / 100.0;
    }

}
