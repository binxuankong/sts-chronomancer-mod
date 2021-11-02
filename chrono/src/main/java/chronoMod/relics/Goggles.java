package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class Goggles extends CustomRelic {
    private static final String RELIC_ID = Goggles.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    public Goggles() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!this.grayscale) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if (card.cost == -1 && !card.freeToPlayOnce) {
                this.addToBot(new GainEnergyAction(card.energyOnUse));
            } else {
                this.addToBot(new GainEnergyAction(card.costForTurn));
            }
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
