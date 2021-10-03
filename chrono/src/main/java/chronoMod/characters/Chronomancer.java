package chronoMod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import chronoMod.DefaultMod;
import chronoMod.cards.*;
import chronoMod.relics.*;
import static chronoMod.DefaultMod.*;
import static chronoMod.characters.Chronomancer.Enums.COLOR_BLUE;

public class Chronomancer extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    // CHARACTER ENUMERATORS
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass CHRONOMANCER;
        @SpireEnum(name = "CHRONO_BLUE_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_BLUE;
        @SpireEnum(name = "CHRONO_BLUE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // BASE STATS
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;

    // STRINGS
    private static final String ID = makeID("Chronomancer");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // IMAGES
    public static final String CHRONO_SHOULDER_1 = "chronoModResources/images/char/defaultCharacter/shoulder.png";
    public static final String CHRONO_SHOULDER_2 = "chronoModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String CHRONO_CORPSE = "chronoModResources/images/char/defaultCharacter/corpse.png";

    // Atlas and JSON files for the Animations
    public static final String CHRONO_SKELETON_ATLAS = "chronoModResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String CHRONO_SKELETON_JSON = "chronoModResources/images/char/defaultCharacter/skeleton.json";

    // CHARACTER CLASS START
    public Chronomancer(String name, PlayerClass setClass) {
        super(name, setClass, null, null, null,
                new SpriterAnimation("chronoModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));


        // TEXTURES, ENERGY, LOADOUT
        initializeClass(null, // required call to load textures and setup energy/loadout.
                CHRONO_SHOULDER_2, // campfire pose
                CHRONO_SHOULDER_1, // another campfire pose
                CHRONO_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F,
                new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // ANIMATIONS
        loadAnimation(CHRONO_SKELETON_ATLAS, CHRONO_SKELETON_JSON, 1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // TEXT BUBBLE LOCATION
        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values
    }

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, 0, STARTING_GOLD, HAND_SIZE, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(StrikeCHR.ID);
        retVal.add(StrikeCHR.ID);
        retVal.add(StrikeCHR.ID);
        retVal.add(StrikeCHR.ID);
        retVal.add(StrikeCHR.ID);
        retVal.add(DefendCHR.ID);
        retVal.add(DefendCHR.ID);
        retVal.add(DefendCHR.ID);
        retVal.add(DefendCHR.ID);
        retVal.add(DefendCHR.ID);
        retVal.add(MysticBlast.ID);
        retVal.add(TimeWarp.ID);

        retVal.add(EventHorizon.ID);
        retVal.add(ArcaneBarrage.ID);

        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BrokenWatch.ID);
        // Mark relics as seen - makes it visible in the compendium immediately
        UnlockTracker.markRelicAsSeen(BrokenWatch.ID);
        return retVal;
    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_BLUE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return chronoMod.DefaultMod.CHRONO_BLUE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new MysticBlast();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new Chronomancer(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return chronoMod.DefaultMod.CHRONO_BLUE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return chronoMod.DefaultMod.CHRONO_BLUE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
