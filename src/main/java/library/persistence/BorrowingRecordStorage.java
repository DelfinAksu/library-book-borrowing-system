package library.persistence;
import library.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

public class BorrowingRecordStorage {
    private static final String FILE_PATH = "borrowing_records.json";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    // LocalDate -> "2025-12-06" gibi String yaz
                    return new JsonPrimitive(src.toString());
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    // "2025-12-06" -> LocalDate
                    return LocalDate.parse(json.getAsString());
                }
            })
            .setPrettyPrinting()
            .create();
    public static void saveRecords(List<BorrowingRecord> records) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(records, writer);
        } catch (IOException e) {
            System.out.println("Error saving borrowing records: " + e.getMessage());
        }
    }

    public static List<BorrowingRecord> loadRecords() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<BorrowingRecord>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Error loading borrowing records: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
