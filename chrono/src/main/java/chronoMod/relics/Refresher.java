package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class Refresher extends CustomRelic {
    private static final String RELIC_ID = Refresher.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    public Refresher() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.cost == -2) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new DrawCardAction(1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new Refresher();
    }
}
