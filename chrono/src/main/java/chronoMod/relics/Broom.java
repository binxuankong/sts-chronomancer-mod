package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class Broom extends CustomRelic {
    private static final String RELIC_ID = Broom.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    private static final int COUNT = 4;

    public Broom() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.costForTurn == 0) || (card.costForTurn <= -2) || ((card.costForTurn == -1) && (
                AbstractDungeon.player.energy.energy <= 0))) {
            this.counter++;
            if (this.counter == COUNT) {
                this.counter = 0;
                this.flash();
                this.pulse = false;
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new DrawCardAction(1));
            } else if (this.counter == COUNT - 1) {
                this.beginPulse();
                this.pulse = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        if (this.counter == COUNT - 1) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new Broom();
    }
}
