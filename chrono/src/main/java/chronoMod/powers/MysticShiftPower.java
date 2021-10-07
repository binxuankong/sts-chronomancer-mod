package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MysticShiftPower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("MysticShift");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int idOffset;

    public MysticShiftPower(AbstractCreature owner) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.updateDescription();
        this.loadRegion("modeShift");
        this.priority = 50;
    }

    @Override
    public void recallEffect () {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null,
                    DamageInfo.createDamageMatrix(this.owner.currentBlock, true), DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    public void atStartOfTurn() {
        this.triggerRecall();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public AbstractPower makeCopy() {
        return new MysticShiftPower(this.owner);
    }
}
