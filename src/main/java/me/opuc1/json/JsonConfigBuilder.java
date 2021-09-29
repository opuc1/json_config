package me.opuc1.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import java.io.*;

@RequiredArgsConstructor
public class JsonConfigBuilder {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final File file;
    private final JsonConfig config;

    public void makeParent() {
        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();
    }

    public JsonConfig loadFile() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return GSON.fromJson(reader, config.getClass());
        } catch (IOException exception) {
            return createFile();
        }
    }

    public JsonConfig createFile() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(toJsonString());
            writer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

        return loadFile();
    }

    public String toJsonString() {
        return GSON.toJson(config);
    }
}
