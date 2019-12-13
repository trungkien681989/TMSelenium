package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DataReader {
    public static DataReader get(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fileName), DataReader.class);
    }

    @JsonProperty("emptyIDNumber")
    String emptyIDNumber;

    @JsonProperty("emptyPassword")
    String emptyPassword;

    @JsonProperty("invalidIDNumber")
    String invalidIDNumber;

    @JsonProperty("invalidPassword")
    String invalidPassword;

    @JsonProperty("validMeterNumber")
    String validMeterNumber;

    @JsonProperty("invalidMeterNumber")
    String invalidMeterNumber;

    @JsonProperty("amountNumber")
    String amountNumber;

    @JsonProperty("idNumberLessThan13")
    String idNumberLessThan13;

    @JsonProperty("idNumberContainsChars")
    String idNumberContainsChars;

    public String getEmptyIDNumber() {
        return emptyIDNumber;
    }

    public String getEmptyPassword() {
        return emptyPassword;
    }

    public String getInvalidIDNumber() {
        return invalidIDNumber;
    }

    public String getInvalidPassword() { return invalidPassword; }

    public String getValidMeterNumber() {
        return validMeterNumber;
    }

    public String getInvalidMeterNumber() {
        return invalidMeterNumber;
    }

    public String getAmountNumber() {
        return amountNumber;
    }

    public String getIdNumberLessThan13() {
        return idNumberLessThan13;
    }

    public String getIdNumberContainsChars() {
        return idNumberContainsChars;
    }

}
