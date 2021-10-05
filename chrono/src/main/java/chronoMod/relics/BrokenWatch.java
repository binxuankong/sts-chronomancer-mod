package chronoMod.relics;

import chronoMod.ChronoMod;
import chronoMod.powers.JadePower;
import chronoMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class BrokenWatch extends CustomRelic {
    public static final String ID = ChronoMod.makeID("BrokenWatch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private boolean activate = false;

    public BrokenWatch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hasPower(JadePower.POWER_ID) && !this.grayscale) {
            this.activate = true;
        }
    }

    @Override
    public void atTurnStartPostDraw () {
        if (this.activate) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DrawCardAction(2));
            this.activate = false;
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        this.activate = false;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new BrokenWatch();
    }
}
