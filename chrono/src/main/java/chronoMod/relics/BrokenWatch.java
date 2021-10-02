package chronoMod.relics;

import chronoMod.actions.ApplyGogglesAction;
import chronoMod.DefaultMod;
import chronoMod.actions.ReverseGogglesAction;
import chronoMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.DefaultMod.makeRelicOutlinePath;
import static chronoMod.DefaultMod.makeRelicPath;

public class BrokenWatch extends CustomRelic {
    public static final String ID = DefaultMod.makeID("BrokenWatch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BrokenWatch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyGogglesAction());
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.addToBot(new ReverseGogglesAction());
        this.grayscale = true;
    }

    public void onVictory() {
        this.grayscale = false;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy() {
        return new BrokenWatch();
    }
}
