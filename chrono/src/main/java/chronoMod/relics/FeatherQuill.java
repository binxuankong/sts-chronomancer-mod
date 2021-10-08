package chronoMod.relics;

import basemod.abstracts.CustomRelic;
import chronoMod.ChronoMod;
import chronoMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static chronoMod.ChronoMod.makeRelicOutlinePath;
import static chronoMod.ChronoMod.makeRelicPath;

public class FeatherQuill extends CustomRelic {
    private static final String relic = "FeatherQuill";
    public static final String ID = ChronoMod.makeID(relic);
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(relic + ".png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(relic + ".png"));

    public FeatherQuill() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onTrigger() {
        this.flash();
        this.addToBot(new DamageRandomEnemyAction(new DamageInfo((AbstractCreature)null, 2, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public CustomRelic makeCopy() {
        return new FeatherQuill();
    }
}
