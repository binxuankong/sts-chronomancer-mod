package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BorrowedTimePower extends AbstractPower {
    public static final String POWER_ID = ChronoMod.makeID("BorrowedTime");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean activate;

    public BorrowedTimePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.activate = false;
        this.updateDescription();
        this.loadRegion("corruption");
        this.priority = 50;
    }

    @Override
    public void atStartOfTurn() {
        this.activate = false;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.activate = true;
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && this.activate) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new RecallReceiveDamagePower(this.owner, damageAmount),
                    damageAmount));
            return 0;
        } else {
            return damageAmount;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public AbstractPower makeCopy() {
        return new BorrowedTimePower(this.owner);
    }
}
