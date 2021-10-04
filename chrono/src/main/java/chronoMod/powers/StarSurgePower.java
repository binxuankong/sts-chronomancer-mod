package chronoMod.powers;

import chronoMod.DefaultMod;
import chronoMod.patches.AbstractPowerEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class StarSurgePower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("StarSurge");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StarSurgePower(AbstractCreature owner, int damageAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damageAmt;
        this.type = AbstractPowerEnum.RECALL;
        this.updateDescription();
        this.loadRegion("doubleDamage");
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(this.owner, new CleaveEffect(), 0.1F));
        this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null,
                DamageInfo.createDamageMatrix(this.amount, false), DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new StarSurgePower(this.owner, this.amount);
    }
}
