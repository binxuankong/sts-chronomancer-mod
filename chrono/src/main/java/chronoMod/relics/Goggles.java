package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import chronoMod.actions.ApplyGogglesAction;
import chronoMod.actions.ReverseGogglesAction;
import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class Goggles extends CustomRelic {
    private static final String relic = "Goggles";
    public static final String ID = ChronoMod.makeID(relic);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(relic + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(relic + ".png"));

    public Goggles() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyGogglesAction());
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!this.grayscale) {
            this.addToBot(new ReverseGogglesAction());
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new Goggles();
    }
}
