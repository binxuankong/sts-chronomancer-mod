package chronoMod;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chronoMod.cards.*;
import chronoMod.characters.Chronomancer;
import chronoMod.potions.PlaceholderPotion;
import chronoMod.relics.*;
import chronoMod.util.IDCheckDontTouchPls;
import chronoMod.util.TextureLoader;
import chronoMod.variables.DefaultCustomVariable;
import chronoMod.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


@SpireInitializer
public class ChronoMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ChronoMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Chronomancer Mod";
    private static final String AUTHOR = "Xaber";
    private static final String DESCRIPTION = "Mod for Chronomancer.";
    
    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color CHRONO_BLUE = CardHelper.getColor(0.0f, 20.0f, 40.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
  
    // Card backgrounds - The actual rectangular card.
    public static final String ATTACK_CHRONO = "chronoModResources/images/512/bg_attack_chrono.png";
    public static final String SKILL_CHRONO = "chronoModResources/images/512/bg_skill_chrono.png";
    public static final String POWER_CHRONO = "chronoModResources/images/512/bg_power_chrono.png";

    public static final String ENERGY_ORB_CHRONO = "chronoModResources/images/512/card_orb_chrono.png";
    public static final String CARD_ENERGY_ORB = "chronoModResources/images/512/card_small_orb_chrono.png";

    public static final String ATTACK_CHRONO_PORTRAIT = "chronoModResources/images/1024/bg_attack_chrono.png";
    public static final String SKILL_CHRONO_PORTRAIT = "chronoModResources/images/1024/bg_skill_chrono.png";
    public static final String POWER_CHRONO_PORTRAIT = "chronoModResources/images/1024/bg_power_chrono.png";
    public static final String ENERGY_ORB_CHRONO_PORTRAIT = "chronoModResources/images/1024/card_orb_chrono.png";

    // Character assets
    public static final String CHRONO_BUTTON = "chronoModResources/images/charSelect/ChronoButton.png";
    public static final String CHRONO_PORTRAIT = "chronoModResources/images/charSelect/ChronoPortraitBG.jpeg";
    
    // Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "chronoModResources/images/Badge.png";
    
    // MAKE IMAGE PATHS
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/beta/" + resourcePath;
        // return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /INPUT TEXTURE LOCATION/ =================

    // SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE
    public ChronoMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("chronoMod");
        logger.info("Done subscribing");
        logger.info("Creating the color " + Chronomancer.Enums.COLOR_BLUE.toString());
        BaseMod.addColor(Chronomancer.Enums.COLOR_BLUE, CHRONO_BLUE, CHRONO_BLUE, CHRONO_BLUE,
                CHRONO_BLUE, CHRONO_BLUE, CHRONO_BLUE, CHRONO_BLUE,
                ATTACK_CHRONO, SKILL_CHRONO, POWER_CHRONO, ENERGY_ORB_CHRONO,
                ATTACK_CHRONO_PORTRAIT, SKILL_CHRONO_PORTRAIT, POWER_CHRONO_PORTRAIT,
                ENERGY_ORB_CHRONO_PORTRAIT, CARD_ENERGY_ORB);
        logger.info("Done creating the color");
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }
    
    // NO EDIT AREA
    public static void setModID(String ID) {
        Gson coolG = new Gson();
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        InputStream in = ChronoMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    
    public static String getModID() {
        return modID;
    }
    
    private static void pathCheck() {
        Gson coolG = new Gson();
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        InputStream in = ChronoMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = ChronoMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }
    
    // YOU CAN EDIT AGAIN
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        ChronoMod defaultmod = new ChronoMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // LOAD THE CHARACTER
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Chronomancer.Enums.CHRONOMANCER.toString());
        BaseMod.addCharacter(new Chronomancer("the Default", Chronomancer.Enums.CHRONOMANCER),
                CHRONO_BUTTON, CHRONO_PORTRAIT, Chronomancer.Enums.CHRONOMANCER);
        receiveEditPotions();
        logger.info("Added " + Chronomancer.Enums.CHRONOMANCER.toString());
    }

    // POST-INITIALIZE
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        logger.info("Done loading badge Image and mod options");
    }
    
    // ADD POTIONS
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, Chronomancer.Enums.CHRONOMANCER);
        logger.info("Done editing potions");
    }

    // ADD RELICS
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        // Character specific relic
        BaseMod.addRelicToCustomPool(new BrokenWatch(), Chronomancer.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new PlaceholderRelic(), Chronomancer.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), Chronomancer.Enums.COLOR_BLUE);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), Chronomancer.Enums.COLOR_BLUE);
        // Shared relic
        BaseMod.addRelic(new Goggles(), RelicType.SHARED);
        // Mark relics as seen - makes it visible in the compendium immediately
        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }
    
    // ADD CARDS
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        logger.info("Adding cards");
        new AutoAdd("ChronoMod") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard.class)
            .setDefaultSeen(true)
            .cards();
        logger.info("Done adding cards!");
    }
    
    // LOAD THE TEXT
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/ChronoMod-Card-Strings.json");
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/ChronoMod-Power-Strings.json");
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/ChronoMod-Relic-Strings.json");
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/ChronoMod-Character-Strings.json");
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");
        logger.info("Done edittting strings");
    }
    
    // LOAD THE KEYWORDS
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/ChronoMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
