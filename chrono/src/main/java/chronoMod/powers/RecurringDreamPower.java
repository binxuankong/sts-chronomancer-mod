package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecurringDreamPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("RecurringDream");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int block;

    public RecurringDreamPower(AbstractCreature owner, int numTurns, int block) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numTurns;
        this.block = block;
        this.isTurnBased = true;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.updateDescription();
        this.loadRegion("shift");
        this.priority = 30;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new GainBlockAction(this.owner, this.block));
        if (this.amount <= 1) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.block + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    public AbstractPower makeCopy() {
        return new RecurringDreamPower(this.owner, this.amount, this.block);
    }
}
