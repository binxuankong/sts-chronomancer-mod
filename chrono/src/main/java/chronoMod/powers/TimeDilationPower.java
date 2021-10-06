package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class TimeDilationPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("TimeDilation");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TimeDilationPower(AbstractCreature owner, int debuffAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = debuffAmt;
        this.updateDescription();
        this.loadRegion("like_water");
    }

    @Override
    public void recallEffect () {
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var1.next();
            if (mo.getIntentBaseDmg() >= 0) {
                this.addToBot(new ApplyPowerAction(mo, this.owner, new WeakPower(mo, this.amount, false),
                        this.amount, true, AbstractGameAction.AttackEffect.NONE));
            } else {
                this.addToBot(new ApplyPowerAction(mo, this.owner, new VulnerablePower(mo, this.amount, false),
                        this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new TimeDilationPower(this.owner, this.amount);
    }
}
