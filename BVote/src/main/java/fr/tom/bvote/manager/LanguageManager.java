package fr.tom.bvote.manager;

import fr.tom.bvote.BVote;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LanguageManager {

    private static final LanguageManager manager = new LanguageManager();
    File langFile;
    FileConfiguration languageConf;

    public static LanguageManager getInstance() {
        return manager;
    }

    public void setup() {
        BVote bVote = JavaPlugin.getPlugin(BVote.class);
        if (!bVote.getDataFolder().exists()) {
            bVote.getDataFolder().mkdirs();
        }
        langFile = new File(bVote.getDataFolder(), "lang.yml");
        try {
            if (langFile.createNewFile()) {
                // tout le stockage du yml
                languageConf = new YamlConfiguration();
                languageConf.set("inventory.title", "HCFrench");
                languageConf.set("main.welcomebeacon.title", "&f ");
                languageConf.set("main.welcomebeacon.lore", Arrays.asList("&f&l&m-------------------", "&9Bienvenue sur HCFrench &f!", "", "&fNous aimerions savoir comment", "&ftu as découvert le serveur !", "&f&l&m-------------------"));
                languageConf.set("main.choice.title", "&9Tu nous as découvert avec...");
                languageConf.set("main.choice.lore", Arrays.asList("&f&l&m-------------------", "%choice%", "", "&fSi oui, &9clic ici &f!", "", "&f&l&m-------------------"));
                languageConf.set("main.choice.1", "&9&lUn Youtuber ?");
                languageConf.set("main.choice.2", "&9&lUn Partenaire ?");
                languageConf.set("main.choice.3", "&9&lUne autre faction ?");
                languageConf.set("main.choice.other", "&9&lAutres ?");
                languageConf.set("second.title", "&fQuel %choice% ?");
                languageConf.set("second.lore", Arrays.asList("&f&l&m-------------------", "%subchoices%", "&f&l&m-------------------"));
                languageConf.set("second.choice1", "Youtubeur");
                languageConf.set("second.choice2", "Partenaire");
                languageConf.set("second.choice3", "Faction");
                languageConf.set("second.choice.other", "Autres");
                languageConf.set("second.choicetemplate", "%subchoice%");
                languageConf.set("second.main.title", "&cMenu principal");
                languageConf.set("second.main.lore", Arrays.asList("&f&l&m-------------------", "&9Tu t'es trompé ?", "&9Clic ici pour revenir au menu principal.", "&f&l&m-------------------"));
                languageConf.set("second.confirm.title", "&aFini ?");
                languageConf.set("second.confirm.lore", Arrays.asList("&f&l&m-------------------", "&9Tu as fait ton choix ? clic ici", "&9pour valider ton vote !", "&f&l&m-------------------"));
                languageConf.set("vote.counted", "&aTon vote à bien été enrengistré, merci de nous aider !");
                languageConf.set("vote.check", "&9%player% &fa voté pour &9%choice%");
                languageConf.set("vote.notfound", "&c%player% n'a pas encore voté");
                languageConf.set("vote.noperm", "&cNo Permission");
                languageConf.save(langFile);
            } else {
                languageConf = YamlConfiguration.loadConfiguration(langFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConf() {
        return languageConf;
    }
}
