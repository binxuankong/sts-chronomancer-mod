package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TemperedFatePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("TemperedFate");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TemperedFatePower(AbstractCreature owner, int bufferAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = bufferAmt;
        this.updateDescription();
        this.loadRegion("heartDef");
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public int onLoseHp(int hpLost) {
        if (hpLost > 0) {
            this.addToTop(new GainJadeAction(1));
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
        return 1;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }

    public AbstractPower makeCopy() {
        return new TemperedFatePower(this.owner, this.amount);
    }
}
