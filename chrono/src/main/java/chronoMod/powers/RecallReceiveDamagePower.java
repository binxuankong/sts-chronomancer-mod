package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class RecallReceiveDamagePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("RecallReceiveDamage");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RecallReceiveDamagePower(AbstractCreature owner, int damageAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = damageAmt;
        this.updateDescription();
        this.loadRegion("brutality");
    }

    @Override
    public void recallEffect() {
        this.addToBot(new VFXAction(new ClashEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
        this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new RecallReceiveDamagePower(this.owner, this.amount);
    }
}
