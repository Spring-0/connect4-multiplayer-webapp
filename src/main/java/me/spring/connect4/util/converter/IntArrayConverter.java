package me.spring.connect4.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IntArrayConverter implements AttributeConverter<int[][], String> {

    /**
     * Converter method for converting matrix into string
     *
     * @param attribute
     * @return
     */
    @Override
    public String convertToDatabaseColumn(int[][] attribute) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : attribute) {
            for (int val : row) {
                sb.append(val).append(",");
            }
            sb.setLength(sb.length() - 1); // Remove trailing comma
            sb.append(";");
        }
        sb.setLength(sb.length() - 1); // Remove trailing semicolon
        return sb.toString();
    }

    /**
     * Converter method for converting valid string into Matrix
     *
     * @param dbData
     * @return
     */
    @Override
    public int[][] convertToEntityAttribute(String dbData) {
        String[] rows = dbData.split(";");
        int[][] result = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] vals = rows[i].split(",");
            int[] row = new int[vals.length];
            for (int j = 0; j < vals.length; j++) {
                row[j] = Integer.parseInt(vals[j]);
            }
            result[i] = row;
        }
        return result;
    }
}
