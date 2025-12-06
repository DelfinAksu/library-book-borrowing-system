package library.persistence;
import library.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BorrowingRecordStorage {
    private static final String FILE_PATH = "borrowing_records.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
