package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.MementoAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MementoPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("Memento");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MementoPower(AbstractCreature owner, int numCards) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numCards;
        this.updateDescription();
        this.loadRegion("rebound");
        this.priority = 50;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.addToBot(new DrawCardAction(this.amount));
        this.addToBot(new MementoAction(this.amount));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4]
            + DESCRIPTIONS[6];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[5]
            + DESCRIPTIONS[6];
        }
    }

    public AbstractPower makeCopy() {
        return new MementoPower(this.owner, this.amount);
    }
}
