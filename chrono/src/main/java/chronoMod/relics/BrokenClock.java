package chronoMod.relics;

import chronoMod.ChronoMod;
import chronoMod.powers.RecallDrawPower;
import chronoMod.powers.RecallEnergyPower;
import chronoMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class BrokenClock extends CustomRelic {
    private static final String relic = "BrokenClock";
    public static final String ID = ChronoMod.makeID(relic);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(relic + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(relic + ".png"));

    public BrokenClock() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p, new RecallEnergyPower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new RecallDrawPower(p, 1), 1));
        this.grayscale = true;
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
        return new BrokenClock();
    }
}
