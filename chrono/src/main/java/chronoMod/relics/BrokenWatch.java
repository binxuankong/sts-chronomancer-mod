package chronoMod.relics;

import chronoMod.actions.ApplyBrokenWatchAction;
import chronoMod.DefaultMod;
import chronoMod.actions.ReverseBrokenWatchAction;
import chronoMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

import static chronoMod.DefaultMod.makeRelicOutlinePath;
import static chronoMod.DefaultMod.makeRelicPath;

public class BrokenWatch extends CustomRelic {
    public static final String ID = DefaultMod.makeID("BrokenWatch");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BrokenWatch() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new ApplyBrokenWatchAction());
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.addToBot(new ReverseBrokenWatchAction());
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
}
