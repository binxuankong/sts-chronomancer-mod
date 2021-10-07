package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class StarSurgePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("StarSurge");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int baseDamage;
    public int[] multiDamage;
    private DamageInfo.DamageType damageType;
    private static int idOffset;

    public StarSurgePower(AbstractCreature owner, int baseDamage, int[] multiDamage, DamageInfo.DamageType damageType) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.baseDamage = baseDamage;
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.updateDescription();
        this.loadRegion("doubleDamage");
        this.priority = 40;
    }

    @Override
    public void recallEffect () {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(this.owner, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction(this.owner, this.multiDamage, this.damageType,
                AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void atStartOfTurn() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.baseDamage + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new StarSurgePower(this.owner, this.baseDamage, this.multiDamage, this.damageType);
    }
}
