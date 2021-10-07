package chronoMod.powers;

import chronoMod.ChronoMod;
import chronoMod.actions.EternalFormAction;
import chronoMod.actions.MementoAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EternalFormPower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("EternalForm");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int costReduction;

    public EternalFormPower(AbstractCreature owner, int numCards, int costReduction) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numCards;
        this.costReduction = costReduction;
        this.updateDescription();
        this.loadRegion("time");
        this.priority = 25;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        this.addToBot(new EternalFormAction(this.amount, this.costReduction));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new EternalFormPower(this.owner, this.amount, this.costReduction);
    }
}
