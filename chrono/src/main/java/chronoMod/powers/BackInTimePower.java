package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class BackInTimePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("BackInTime");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static int idOffset;

    public BackInTimePower(AbstractCreature owner, int hpAmount) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.amount = hpAmount;
        this.updateDescription();
        this.loadRegion("afterImage");
    }

    @Override
    public void recallEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        p.currentHealth = this.amount;
        //if (p.currentHealth < this.amount) {
        //    p.heal(this.amount - p.currentHealth);
        //} else if (p.currentHealth > this.amount) {
        //    this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.HP_LOSS),
        //            AbstractGameAction.AttackEffect.NONE));
        //}
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new BackInTimePower(this.owner, this.amount);
    }
}
