package me.quickscythe.fluxcore.api;

import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.io.File;
import java.util.Collection;

public class JavaMod {

    String name;
    String id;
    String version;
    String description;
    Collection<Person> authors;
    ContactInformation contact;

    File dataFolder;

    public JavaMod(ModMetadata metadata){
        this.name = metadata.getName();
        this.id = metadata.getId();
        this.version = metadata.getVersion().getFriendlyString();
        this.description = metadata.getDescription();
        this.authors = metadata.getAuthors();
        this.contact = metadata.getContact();
        this.dataFolder = new File("config/" + name +"/");
        if(!dataFolder.exists()) dataFolder.mkdirs();
    }

    public String getName(){
        return name;
    }

    public File getDataFolder(){
        return dataFolder;
    }

    public String getId(){
        return id;
    }

    public String getVersion(){
        return version;
    }

    public String getDescription(){
        return description;
    }

    public Collection<Person> getAuthors(){
        return authors;
    }

    public ContactInformation getContact(){
        return contact;
    }


}
