package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class AncientClock extends CustomRelic {
    private static final String relic = "AncientClock";
    public static final String ID = ChronoMod.makeID(relic);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(relic + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(relic + ".png"));

    public AncientClock() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.counter = 3;
    }

    @Override
    public void onTrigger() {
        if (!this.grayscale) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new GainEnergyAction(1));
            this.counter--;
            if (this.counter == 0) {
                this.counter = -1;
                this.grayscale = true;
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BrokenClock.ID)) {
            instantObtain(AbstractDungeon.player, 0, false);
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BrokenClock.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new AncientClock();
    }
}
