package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TimeSinkPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("TimeSink");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TimeSinkPower(AbstractCreature owner, int numTurns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numTurns;
        this.isTurnBased = true;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.updateDescription();
        this.loadRegion("heatsink");
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new GainEnergyAction(1));
        this.addToBot(new DrawCardAction(1));
        if (this.amount <= 1) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new TimeSinkPower(this.owner, this.amount);
    }
}
