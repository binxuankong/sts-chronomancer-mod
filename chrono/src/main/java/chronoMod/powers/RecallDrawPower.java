package chronoMod.powers;

import chronoMod.DefaultMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecallDrawPower extends RecallPower {
    public static final String POWER_ID = DefaultMod.makeID("RecallEnergy");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RecallDrawPower(AbstractCreature owner, int drawAmt) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID;
        this.amount = drawAmt;
        this.updateDescription();
        this.loadRegion("carddraw");
        this.priority = 20;
    }

    @Override
    public void recallEffect() {
        this.addToBot(new DrawCardAction(this.owner, this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public AbstractPower makeCopy() {
        return new RecallDrawPower(this.owner, this.amount);
    }
}