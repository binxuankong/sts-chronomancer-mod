package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.actions.CardFromDeckToDiscardAction;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class AncientClock extends CustomRelic {
    private static final String RELIC_ID = AncientClock.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    public AncientClock() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onShuffle() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToTop(new CardFromDeckToDiscardAction(1));
        this.addToTop(new RelicAboveCreatureAction(p, this));
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
