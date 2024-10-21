package me.quickscythe.fluxcore.api.data;

import org.json.JSONArray;
import org.json.JSONObject;
import me.quickscythe.fluxcore.utils.CoreUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InternalStorage {

    private static final long RETENTION_TIME = TimeUnit.MINUTES.toMillis(30); // 30 minutes
    private final Map<String, Long> lastModified = new java.util.HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    JSONObject data = new JSONObject();

    public InternalStorage() {
        startGarbageCollector();
    }

    private void startGarbageCollector() {
        scheduler.scheduleAtFixedRate(this::runGarbageCollector, 5, 5, TimeUnit.MINUTES);
    }

    private void runGarbageCollector() {
        long currentTime = System.currentTimeMillis();
        for (String key : new HashSet<>(lastModified.keySet())) {
            if (currentTime - lastModified.get(key) > RETENTION_TIME) {
                saveAndRemove(key);
            }
        }
    }

    public void saveAndRemove(String key) {
        Object value = get(key);
        if (value != null) {
            write(key);
            remove(key);
        }
    }

    public void remove(String key) {
        String[] paths = key.split("\\.");
        JSONObject current = data;
        for (int i = 0; i < paths.length - 1; i++) {
            current = current.getJSONObject(paths[i]);
        }
        current.remove(paths[paths.length - 1]);
        lastModified.remove(key);
    }

    public void set(String path, Object value) {
        String[] paths = path.split("\\.");
        lastModified.put(path, System.currentTimeMillis());

        JSONObject current = data;
        for (int i = 0; i < paths.length; i++) {
            if (i == paths.length - 1) {
                current.put(paths[i], value);
            } else {
                if (!current.has(paths[i]) || !(current.get(paths[i]) instanceof JSONObject)) {
                    current.put(paths[i], new JSONObject());
                }
                current = current.getJSONObject(paths[i]);
            }
        }
    }


    public Object get(String path) {
//        path = StorageManager.getConfigFolder().getName() + "." + path;
        String[] paths = path.split("\\.");

        JSONObject current = data;
        for (int i = 0; i < paths.length; i++) {
            if (i == paths.length - 1) {
                return current.get(paths[i]);
            } else {
                if (!current.has(paths[i])) {
                    return null;

                }
                current = current.getJSONObject(paths[i]);
            }
        }
        return current;

    }

    public JSONObject root() {
        return data;
    }


    public void write(String path) {
        String fileName = path.replaceAll("\\.", "/") + ".json";

        try {
            File file = new File(StorageManager.getConfigFolder(), fileName);
            CoreUtils.getLoggerUtils().getLogger().info("Checking if file exists: {}", file.getAbsolutePath());
            if (!(file.getParentFile() == null)) if (!file.getParentFile().exists()) {
                CoreUtils.getLoggerUtils().getLogger().info("Creating directories {}: {}", file.getParentFile().getPath(), file.getParentFile().mkdirs());
            }
            if (!file.exists())
                CoreUtils.getLoggerUtils().getLogger().info("Creating file {}: {}", file.getName(), file.createNewFile());
            FileWriter f2 = new FileWriter(file, false);
            Object object = get(path);
            String context;
            if (object instanceof JSONObject) {
                context = ((JSONObject) object).toString(2);
            } else if (object instanceof JSONArray) {
                context = ((JSONArray) object).toString(2);
            } else {
                context = new JSONObject(object).toString(2);
            }

//            System.out.println(context);
            CoreUtils.getLoggerUtils().getLogger().info("Writing to file: {}", fileName);
            f2.write(context);
            f2.close();
            CoreUtils.getLoggerUtils().getLogger().info("File written: {}", fileName);
        } catch (Exception e) {
            CoreUtils.getLoggerUtils().getLogger().error("There was an error saving {}", fileName, e);
        }

    }

    public Object load(String path) {
        return load(new File(StorageManager.getConfigFolder(),path.replaceAll("\\.", "/") + ".json"));
    }

    public Object load(File file) {
        CoreUtils.getLoggerUtils().getLogger().info("Loading {}", file.getAbsolutePath());
        try {
            CoreUtils.getLoggerUtils().getLogger().info("File exists: {}", file.exists());
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) sb.append(scanner.nextLine());

            scanner.close();
            String key = file.getPath().split("\\.")[0].replaceAll("/", ".").replaceAll("\\\\", ".");
            if(key.startsWith("config")){
                key = key.substring(21);
            }
            CoreUtils.getLoggerUtils().getLogger().info("Loading {} into {}", file.getName(), key);
            String context = sb.toString();

            Object o = context.isEmpty() ? null : context;

            if (context.startsWith("{")) {
                o = new JSONObject(context);
            }
            if (context.startsWith("[")) {
                o = new JSONArray(context);
            }
            if (o != null) set(key, o);
            return o;
        } catch (IOException e) {
            if (file.isDirectory()) {
                CoreUtils.getLoggerUtils().getLogger().error("Can not write to folder: {}", file.getName(), e);
            } else CoreUtils.getLoggerUtils().getLogger().error("There was an error loading {}", file.getName(), e);
        }
        return null;
    }

    public boolean has(String path) {
        return get(path) != null;
    }


    //* Extra stuff

    public JSONObject getJsonObject(String path) {
        return (JSONObject) get(path);
    }

    public JSONArray getJsonArray(String path) {
        return (JSONArray) get(path);
    }

    public String getString(String path) {
        return (String) get(path);
    }

    public int getInt(String path) {
        return (int) get(path);
    }

    public float getFloat(String path) {
        return (float) get(path);
    }

    public double getDouble(String path) {
        return (double) get(path);
    }

    public boolean getBoolean(String path) {
        return (boolean) get(path);
    }
}