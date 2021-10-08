package chronoMod.relics;

import chronoMod.ChronoMod;
import chronoMod.actions.CardFromDeckToDiscardAction;
import chronoMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class BrokenClock extends CustomRelic {
    private static final String RELIC_ID = BrokenClock.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(RELIC_ID);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(RELIC_ID + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(RELIC_ID + ".png"));

    public BrokenClock() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.SOLID);
    }

    @Override
    public void onShuffle() {
        AbstractPlayer p = AbstractDungeon.player;
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new CardFromDeckToDiscardAction(1));
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
